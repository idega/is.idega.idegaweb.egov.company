/*
 * $Id: CompanyApplicationViewer.java,v 1.1 2008/09/23 12:57:41 anton
 * 
 * Copyright (C) 2008 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation.company;

import is.idega.idegaweb.egov.application.presentation.ApplicationCreator;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.data.CompanyApplication;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;
import is.idega.idegaweb.egov.company.presentation.institution.ApplicationApproverRejecter;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import javax.ejb.FinderException;

import com.idega.block.process.data.Case;
import com.idega.block.process.message.data.Message;
import com.idega.company.data.CompanyType;
import com.idega.core.builder.data.ICPage;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.presentation.CSSSpacer;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Span;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Label;
import com.idega.user.business.NoEmailFoundException;
import com.idega.user.business.NoPhoneFoundException;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
import com.idega.util.ListUtil;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.Name;

public class CompanyApplicationViewer extends CompanyBlock {

	private ICPage backPage;
	
	@Override
	public void present(IWContext iwc) throws Exception {
		super.present(iwc);
		
		if (!getCompanyBusiness().isCompanyAdministrator(iwc)) {
			showInsufficientRightsMessage(iwrb.getLocalizedString("insufficient_rights_to_view_application",
					"You have insufficient rights to view application!"));
			return;
		}

		CompanyApplication application = null;
		if (iwc.isParameterSet(ApplicationCreator.APPLICATION_ID_PARAMETER)) {
			application = getCompanyBusiness().getApplication(iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER));
		}
		
		if (application == null) {
			showMessage(iwrb.getLocalizedString("select_application", "Select application!"));
			return;
		}

		switch (parseAction(iwc)) {
			case ApplicationApproverRejecter.ACTION_VIEW:
				getViewerForm(iwc, application);
				break;
		}
	}

	private int parseAction(IWContext iwc) {
		int action = ApplicationApproverRejecter.ACTION_VIEW;
		if (iwc.isParameterSet(ApplicationCreator.ACTION)) {
			action = Integer.parseInt(iwc.getParameter(ApplicationCreator.ACTION));
		}

		return action;
	}

	@SuppressWarnings("unchecked")
	private void getViewerForm(IWContext iwc, CompanyApplication application) throws RemoteException {
		Locale locale = iwc.getCurrentLocale();
		
		Form form = new Form();
		form.addParameter(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_VIEW));
		form.maintainParameter(ApplicationCreator.APPLICATION_ID_PARAMETER);
		add(form);

		Layer header = new Layer(Layer.DIV);
		header.setStyleClass("header");
		form.add(header);

		Heading1 heading = new Heading1(iwrb.getLocalizedString("application.overview", "Application overview"));
		header.add(heading);

		form.add(getCompanyInfo(iwc, application.getCompany()));

		CompanyType type = application.getType();
		if (type != null) {
			heading = new Heading1(iwrb.getLocalizedString("application.type_information_overview", "Type information"));
			heading.setStyleClass("subHeader");
			heading.setStyleClass("topSubHeader");
			form.add(heading);
	
			Layer section = new Layer(Layer.DIV);
			section.setStyleClass("formSection");
			form.add(section);
	
			Layer formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			Label label = new Label();
			label.setLabel(iwrb.getLocalizedString("company_type", "Company type"));
			formItem.add(label);
			Span span = new Span(new Text(type.getLocalizedName(iwc, locale)));
			formItem.add(span);
			section.add(formItem);
			
			section.add(new CSSSpacer());
		}

		heading = new Heading1(iwrb.getLocalizedString("application.company_information_overview", "Company information"));
		heading.setStyleClass("subHeader");
		form.add(heading);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label();
		label.setLabel(iwrb.getLocalizedString("personal_id", "Personal ID"));
		formItem.add(label);
		Span span = new Span(new Text(PersonalIDFormatter.format(application.getCompany().getPersonalID(), locale)));
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("name", "Name"));
		formItem.add(label);
		span = new Span(new Text(application.getCompany().getName()));
		formItem.add(span);
		section.add(formItem);

		section.add(new CSSSpacer());

		User user = application.getApplicantUser();
		if (user != null) {
			heading = new Heading1(iwrb.getLocalizedString("application.admin_information", "Admin information"));
			heading.setStyleClass("subHeader");
			form.add(heading);

			section = new Layer(Layer.DIV);
			section.setStyleClass("formSection");
			form.add(section);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label();
			label.setLabel(iwrb.getLocalizedString("personal_id", "Personal ID"));
			formItem.add(label);
			span = new Span(new Text(PersonalIDFormatter.format(user.getPersonalID(), locale)));
			formItem.add(span);
			section.add(formItem);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label();
			label.setLabel(iwrb.getLocalizedString("name", "Name"));
			formItem.add(label);
			span = new Span(new Text(new Name(user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(locale)));
			formItem.add(span);
			section.add(formItem);

			Phone workPhone = null;
			try {
				workPhone = getUserBusiness(iwc).getUsersWorkPhone(user);
			}
			catch (NoPhoneFoundException e) {
				//No work phone found...
			}

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label();
			label.setLabel(iwrb.getLocalizedString("work_phone", "Work phone"));
			formItem.add(label);
			span = new Span(new Text(workPhone != null ? workPhone.getNumber() : Text.NON_BREAKING_SPACE));
			formItem.add(span);
			section.add(formItem);

			Phone mobilePhone = null;
			try {
				mobilePhone = getUserBusiness(iwc).getUsersMobilePhone(user);
			}
			catch (NoPhoneFoundException e) {
				//No mobile phone found...
			}

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label();
			label.setLabel(iwrb.getLocalizedString("mobile_phone", "Mobile phone"));
			formItem.add(label);
			span = new Span(new Text(mobilePhone != null ? mobilePhone.getNumber() : Text.NON_BREAKING_SPACE));
			formItem.add(span);
			section.add(formItem);

			Email email = null;
			try {
				email = getUserBusiness(iwc).getUsersMainEmail(user);
			}
			catch (NoEmailFoundException e) {
				//No email found...
			}

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label();
			label.setLabel(iwrb.getLocalizedString("email", "E-mail"));
			formItem.add(label);
			span = new Span(new Text(email != null ? email.getEmailAddress() : Text.NON_BREAKING_SPACE));
			formItem.add(span);
			section.add(formItem);

			section.add(new CSSSpacer());
		}

		Collection<Case> children = application.getChildren();
		if (!ListUtil.isEmpty(children)) {
			Collection<Message> messages = new ArrayList<Message>();
			for (Case theCase: children) {
				try {
					Message message = getCompanyBusiness().getMessageBusiness().getMessage(theCase.getPrimaryKey());
					if (message.getContentCode() != null && message.getContentCode().equals(EgovCompanyConstants.CONTENT_CODE_REQUEST_INFORMATION)) {
						messages.add(message);
					}
				}
				catch (FinderException e) {
					e.printStackTrace();
				}
			}

			if (!ListUtil.isEmpty(messages)) {
				for (Message message: messages) {
					User receiver = message.getOwner();
					User sender = message.getSender();
					IWTimestamp created = new IWTimestamp(message.getCreated());
					
					heading = new Heading1(iwrb.getLocalizedString("application.request_information_message", "Request information message"));
					heading.setStyleClass("subHeader");
					form.add(heading);
					
					section = new Layer(Layer.DIV);
					section.setStyleClass("formSection");
					form.add(section);

					if (sender != null) {
						formItem = new Layer(Layer.DIV);
						formItem.setStyleClass("formItem");
						label = new Label();
						label.setLabel(iwrb.getLocalizedString("sender", "Sender"));
						formItem.add(label);
						span = new Span(new Text(new Name(sender.getFirstName(), sender.getMiddleName(), sender.getLastName()).getName(locale)));
						formItem.add(span);
						section.add(formItem);
					}

					if (receiver != null) {
						formItem = new Layer(Layer.DIV);
						formItem.setStyleClass("formItem");
						label = new Label();
						label.setLabel(iwrb.getLocalizedString("receiver", "Receiver"));
						formItem.add(label);
						span = new Span(new Text(new Name(receiver.getFirstName(), receiver.getMiddleName(), receiver.getLastName()).getName(locale)));
						formItem.add(span);
						section.add(formItem);
					}

					formItem = new Layer(Layer.DIV);
					formItem.setStyleClass("formItem");
					label = new Label();
					label.setLabel(iwrb.getLocalizedString("sent_date", "Sent date"));
					formItem.add(label);
					span = new Span(new Text(created.getLocaleDateAndTime(locale, IWTimestamp.SHORT, IWTimestamp.SHORT)));
					formItem.add(span);
					section.add(formItem);

					formItem = new Layer(Layer.DIV);
					formItem.setStyleClass("formItem");
					label = new Label();
					label.setLabel(iwrb.getLocalizedString("message", "Message"));
					formItem.add(label);
					span = new Span(new Text(message.getBody()));
					formItem.add(span);
					section.add(formItem);

					section.add(new CSSSpacer());
				}
			}
		}

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link home = getButtonLink(iwrb.getLocalizedString("back", "Back"));
		home.setStyleClass("buttonHome");
		if (getBackPage() != null) {
			home.setPage(getBackPage());
		}
		bottom.add(home);

		String status = application.getCaseStatus() == null ? null : application.getCaseStatus().getStatus();
		if (Arrays.asList(getCompanyBusiness().getStatusesForOpenCases()).contains(status)) {
			Link approve = getButtonLink(iwrb.getLocalizedString("approve", "Approve"));
			approve.addParameter(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_APPROVE));
			approve.setClickConfirmation(iwrb.getLocalizedString("application.approve_confirmation", "Are you sure you want to approve this application?"));
			bottom.add(approve);

			Link reject = getButtonLink(iwrb.getLocalizedString("reject", "Reject"));
			reject.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_REJECTION_FORM));
			reject.setToFormSubmit(form);
			bottom.add(reject);
		}
		if (Arrays.asList(getCompanyBusiness().getStatusesForRejectedCases()).contains(status)) {
			Link reactivate = getButtonLink(iwrb.getLocalizedString("reactivate", "Reactivate"));
			reactivate.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_REACTIVATE));
			reactivate.setToFormSubmit(form);
			bottom.add(reactivate);
		}
		if (status.equals(getCompanyBusiness().getCaseStatusGranted())) {
			Link close = getButtonLink(iwrb.getLocalizedString("close_account", "Close account"));
			close.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_CLOSING_FORM));
			close.setToFormSubmit(form);
			bottom.add(close);
		}
		if (status.equals(getCompanyBusiness().getCaseStatusCancelled())) {
			Link open = getButtonLink(iwrb.getLocalizedString("open_account", "Open account"));
			open.addParameter(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_OPEN));
			open.setClickConfirmation(iwrb.getLocalizedString("open_account_confirmation", "Are you sure you want to reopen account for this application?"));
			bottom.add(open);
		}
		
		Link contract = getButtonLink(iwrb.getLocalizedString("company_application_contract", "Contract"));
		StringBuffer javaScript = new StringBuffer("showLoadingMessage('")
										.append(iwrb.getLocalizedString("downloading", "Downloading..."))
										.append("'); CompanyApplicationBusiness.generateContract('")
										.append(String.valueOf(application.getPrimaryKey()))
										.append("', function(linkToPdf) { closeAllLoadingMessages(); if (linkToPdf == null || '' == linkToPdf) { return false; } if (linkToPdf.indexOf('.pdf') != -1) { window.location.href = linkToPdf; } else { alert(linkToPdf); }} );");
		
		contract.setOnClick(javaScript.toString());
		contract.setURL("javascript:void(0)");
		
		bottom.add(contract);
	}

	public ICPage getBackPage() {
		return backPage;
	}

	public void setBackPage(ICPage backPage) {
		this.backPage = backPage;
	}
}