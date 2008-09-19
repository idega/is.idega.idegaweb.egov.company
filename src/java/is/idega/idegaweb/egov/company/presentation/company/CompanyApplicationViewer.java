/*
 * $Id: CompanyApplicationViewer.java,v 1.1 2008/07/29 12:57:41 anton
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
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.FinderException;

import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseStatus;
import com.idega.block.process.message.data.Message;
import com.idega.company.data.CompanyType;
import com.idega.core.builder.data.ICPage;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
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
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.Name;

public class CompanyApplicationViewer extends CompanyBlock {
//	private static final String PARAMETER_MESSAGE = "prm_message";

//	private static final int ACTION_VIEW = 1;
//	private static final int ACTION_APPROVE = 2;
//	private static final int ACTION_REJECTION_FORM = 3;
//	private static final int ACTION_REJECT = 4;
//	private static final int ACTION_REQUEST_FORM = 5;
//	private static final int ACTION_REQUEST_INFO = 6;
//	private static final int ACTION_REACTIVATE_FORM = 7;
//	private static final int ACTION_REACTIVATE = 8;
//	private static final int ACTION_OPEN = 9;
//	private static final int ACTION_CLOSING_FORM = 10;
//	private static final int ACTION_CLOSE = 11;
//	private static final int ACTION_EDIT_FORM = 12;
//	private static final int ACTION_CONTRACT = 13;
//	private static final int ACTION_LIST = 14;

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
//			case ACTION_APPROVE:
//				approve(iwc, application);
//				break;
//			case ACTION_REJECTION_FORM:
//				getRejectionForm(iwc, application);
//				break;
//			case ACTION_REJECT:
//				reject(iwc, application);
//				break;
//			case ACTION_REQUEST_FORM:
//				getRequestForm(iwc, application);
//				break;
//			case ACTION_REQUEST_INFO:
//				requestInfo(iwc, application);
//				break;
//			case ACTION_REACTIVATE:
//				reactivate(iwc, application);
//				break;
//			case ACTION_OPEN:
//				reopen(iwc, application);
//				break;
//			case ACTION_CLOSING_FORM:
//				getClosingForm(iwc, application);
//				break;
//			case ACTION_CLOSE:
//				close(iwc, application);
//				break;
////TODO		finish contract pdf
////			case ACTION_CONTRACT:
////				close(iwc, application);
////				break;
//			
//				
//		  /*case ACTION_EDIT_FORM:
//				getEditForm(iwc, application);
//				break;
//			case ACTION_STORE:
//				if (!store(iwc, application)) {
//					getEditForm(iwc, application);
//				}
//				break;*/
		}
	}

	private int parseAction(IWContext iwc) {
		int action = ApplicationApproverRejecter.ACTION_VIEW;
		if (iwc.isParameterSet(ApplicationCreator.ACTION)) {
			action = Integer.parseInt(iwc.getParameter(ApplicationCreator.ACTION));
		}

		return action;
	}

	private void getViewerForm(IWContext iwc, CompanyApplication application) throws RemoteException {
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
		Span span = new Span(new Text(type != null ? type.getLocalizedName(iwc, iwc.getCurrentLocale()) : ""));
		formItem.add(span);
		section.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		section.add(clearLayer);

		heading = new Heading1(iwrb.getLocalizedString("application.company_information_overview", "Company information"));
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
		span = new Span(new Text(PersonalIDFormatter.format(application.getCompany().getPersonalID(), iwc.getCurrentLocale())));
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

		section.add(clearLayer);

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
			span = new Span(new Text(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale())));
			formItem.add(span);
			section.add(formItem);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label();
			label.setLabel(iwrb.getLocalizedString("name", "Name"));
			formItem.add(label);
			span = new Span(new Text(new Name(user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale())));
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

			section.add(clearLayer);
		}


			Iterator iter = application.getChildrenIterator();
			if (iter != null) {
				Collection messages = new ArrayList();

				while (iter.hasNext()) {
					Case theCase = (Case) iter.next();
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

				if (!messages.isEmpty()) {
					Iterator iterator = messages.iterator();
					while (iterator.hasNext()) {
						Message message = (Message) iterator.next();
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
							span = new Span(new Text(new Name(sender.getFirstName(), sender.getMiddleName(), sender.getLastName()).getName(iwc.getCurrentLocale())));
							formItem.add(span);
							section.add(formItem);
						}

						if (receiver != null) {
							formItem = new Layer(Layer.DIV);
							formItem.setStyleClass("formItem");
							label = new Label();
							label.setLabel(iwrb.getLocalizedString("receiver", "Receiver"));
							formItem.add(label);
							span = new Span(new Text(new Name(receiver.getFirstName(), receiver.getMiddleName(), receiver.getLastName()).getName(iwc.getCurrentLocale())));
							formItem.add(span);
							section.add(formItem);
						}

						formItem = new Layer(Layer.DIV);
						formItem.setStyleClass("formItem");
						label = new Label();
						label.setLabel(iwrb.getLocalizedString("sent_date", "Sent date"));
						formItem.add(label);
						span = new Span(new Text(created.getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT)));
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

						section.add(clearLayer);
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

		application.getChildrenIterator();

			CaseStatus status = application.getCaseStatus();

			if (status.equals(getCompanyBusiness().getCaseStatusOpen()) || status.equals(getCompanyBusiness().getCaseStatusReview())) {
				Link approve = getButtonLink(iwrb.getLocalizedString("approve", "Approve"));
				approve.addParameter(ApplicationCreator.APPLICATION_ID_PARAMETER, application.getPrimaryKey().toString());
				approve.addParameter(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_APPROVE));
				approve.setClickConfirmation(iwrb.getLocalizedString("application.approve_confirmation", "Are you sure you want to approve this application?"));
				bottom.add(approve);

				Link reject = getButtonLink(iwrb.getLocalizedString("reject", "Reject"));
				reject.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_REJECTION_FORM));
				reject.setToFormSubmit(form);
				bottom.add(reject);

//				Link requestInfo = getButtonLink(iwrb.getLocalizedString("request_info", "Request info"));
//				requestInfo.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ACTION_REQUEST_FORM));
//				requestInfo.setToFormSubmit(form);
//				bottom.add(requestInfo);
			}
			else if (status.equals(getCompanyBusiness().getCaseStatusDenied())) {
				Link reactivate = getButtonLink(iwrb.getLocalizedString("reactivate", "Reactivate"));
				reactivate.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_REACTIVATE));
				reactivate.setToFormSubmit(form);
				bottom.add(reactivate);
			}
			else if (status.equals(getCompanyBusiness().getCaseStatusGranted())) {
				Link close = getButtonLink(iwrb.getLocalizedString("close_account", "Close account"));
				close.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_CLOSING_FORM));
				close.setToFormSubmit(form);
				bottom.add(close);
			}
//			else if (status.equals(getCompanyBusiness().getCaseStatusCancelled())) {
//				Link open = getButtonLink(iwrb.getLocalizedString("open_account", "Open account"));
//				open.addParameter(ApplicationCreator.APPLICATION_ID_PARAMETER, application.getPrimaryKey().toString());
//				open.addParameter(ApplicationCreator.ACTION, String.valueOf(ACTION_OPEN));
//				open.setClickConfirmation(iwrb.getLocalizedString("application.open_confirmation", "Are you sure you want to reopen this application?"));
//				bottom.add(open);
//			}

//		Link edit = getButtonLink(iwrb.getLocalizedString("edit", "Edit"));
//		edit.addParameter(ApplicationCreator.APPLICATION_ID_PARAMETER, application.getPrimaryKey().toString());
//		edit.addParameter(ApplicationCreator.ACTION, String.valueOf(ACTION_EDIT_FORM));
//		bottom.add(edit);
		
		Link contract = getButtonLink(iwrb.getLocalizedString("contract.file_name", "Contract"));
		contract.addParameter(ApplicationCreator.APPLICATION_ID_PARAMETER, application.getPrimaryKey().toString());
		contract.addParameter(ApplicationCreator.ACTION, String.valueOf(ApplicationApproverRejecter.ACTION_CONTRACT));
		bottom.add(contract);
	}

//	private void getRejectionForm(IWContext iwc, CompanyApplication application) throws RemoteException {
//		Form form = new Form();
//		form.addParameter(ApplicationCreator.ACTION, String.valueOf(ACTION_REJECTION_FORM));
//		form.maintainParameter(ApplicationCreator.APPLICATION_ID_PARAMETER);
//		add(form);
//
//		Layer header = new Layer(Layer.DIV);
//		header.setStyleClass("header");
//		form.add(header);
//
//		Heading1 heading = new Heading1(iwrb.getLocalizedString("application.overview", "Application overview"));
//		header.add(heading);
//
//		form.add(getCompanyInfo(iwc, application.getCompany()));
//
//		heading = new Heading1(iwrb.getLocalizedString("application.rejection_info", "Rejection info"));
//		heading.setStyleClass("subHeader");
//		heading.setStyleClass("topSubHeader");
//		form.add(heading);
//
//		Layer section = new Layer(Layer.DIV);
//		section.setStyleClass("formSection");
//		form.add(section);
//
//		Layer helpLayer = new Layer(Layer.DIV);
//		helpLayer.setStyleClass("helperText");
//		helpLayer.add(new Text(iwrb.getLocalizedString("application.rejection_help", "Please enter a message that will be sent to the sender of the application about the rejection.")));
//		section.add(helpLayer);
//
//		Object[] arguments = { application.getName(), application.getCompany().getPersonalID() };
//
//		TextArea message = new TextArea(PARAMETER_MESSAGE);
//		message.setStyleClass("messageArea");
//		message.setContent(MessageFormat.format(iwrb.getLocalizedString("application.rejected_message_body", "Your application for company account for {0}, {1} has been rejected."), arguments));
//
//		Layer formItem = new Layer(Layer.DIV);
//		formItem.setStyleClass("formItem");
//		Label label = new Label(iwrb.getLocalizedString("application.rejection_message", "Rejection message"), message);
//		formItem.add(label);
//		formItem.add(message);
//		section.add(formItem);
//
//		Layer clearLayer = new Layer(Layer.DIV);
//		clearLayer.setStyleClass("Clear");
//		section.add(clearLayer);
//
//		Layer bottom = new Layer(Layer.DIV);
//		bottom.setStyleClass("bottom");
//		form.add(bottom);
//
//		Link send = getButtonLink(iwrb.getLocalizedString("send", "Send"));
//		send.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ACTION_REJECT));
//		send.setToFormSubmit(form);
//		bottom.add(send);
//
//		Link back = getButtonLink(iwrb.getLocalizedString("back", "Back"));
//		back.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ACTION_VIEW));
//		back.setToFormSubmit(form);
//		bottom.add(back);
//	}
//
//	public void getRequestForm(IWContext iwc, CompanyApplication application) throws RemoteException {
//		Form form = new Form();
//		form.addParameter(ApplicationCreator.ACTION, String.valueOf(ACTION_REQUEST_FORM));
//		form.maintainParameter(ApplicationCreator.APPLICATION_ID_PARAMETER);
//		add(form);
//
//		Layer header = new Layer(Layer.DIV);
//		header.setStyleClass("header");
//		form.add(header);
//
//		Heading1 heading = new Heading1(iwrb.getLocalizedString("application.overview", "Application overview"));
//		header.add(heading);
//
//		form.add(getCompanyInfo(iwc, application.getCompany()));
//
//		heading = new Heading1(iwrb.getLocalizedString("application.request_info", "Request further info"));
//		heading.setStyleClass("subHeader");
//		heading.setStyleClass("topSubHeader");
//		form.add(heading);
//
//		Layer section = new Layer(Layer.DIV);
//		section.setStyleClass("formSection");
//		form.add(section);
//
//		Layer helpLayer = new Layer(Layer.DIV);
//		helpLayer.setStyleClass("helperText");
//		helpLayer.add(new Text(iwrb.getLocalizedString("application.request_info_help", "Please enter a message that will be sent to the sender of the application stating what is required.")));
//		section.add(helpLayer);
//
//		TextArea message = new TextArea(PARAMETER_MESSAGE);
//		message.setStyleClass("messageArea");
//
//		Layer formItem = new Layer(Layer.DIV);
//		formItem.setStyleClass("formItem");
//		Label label = new Label(iwrb.getLocalizedString("application.request_info_message", "Request info message"), message);
//		formItem.add(label);
//		formItem.add(message);
//		section.add(formItem);
//
//		Layer clearLayer = new Layer(Layer.DIV);
//		clearLayer.setStyleClass("Clear");
//		section.add(clearLayer);
//
//		Layer bottom = new Layer(Layer.DIV);
//		bottom.setStyleClass("bottom");
//		form.add(bottom);
//
//		Link send = getButtonLink(iwrb.getLocalizedString("send", "Send"));
//		send.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ACTION_REQUEST_INFO));
//		send.setToFormSubmit(form);
//		bottom.add(send);
//
//		Link back = getButtonLink(iwrb.getLocalizedString("back", "Back"));
//		back.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ACTION_VIEW));
//		back.setToFormSubmit(form);
//		bottom.add(back);
//	}
//
//	private void getClosingForm(IWContext iwc, CompanyApplication application) throws RemoteException {
//		Form form = new Form();
//		form.addParameter(ApplicationCreator.ACTION, String.valueOf(ACTION_CLOSING_FORM));
//		form.maintainParameter(ApplicationCreator.APPLICATION_ID_PARAMETER);
//		add(form);
//
//		Layer header = new Layer(Layer.DIV);
//		header.setStyleClass("header");
//		form.add(header);
//
//		Heading1 heading = new Heading1(iwrb.getLocalizedString("application.overview", "Application overview"));
//		header.add(heading);
//
//		form.add(getCompanyInfo(iwc, application.getCompany()));
//
//		heading = new Heading1(iwrb.getLocalizedString("application.closing_info", "Closing info"));
//		heading.setStyleClass("subHeader");
//		heading.setStyleClass("topSubHeader");
//		form.add(heading);
//
//		Layer section = new Layer(Layer.DIV);
//		section.setStyleClass("formSection");
//		form.add(section);
//
//		Layer helpLayer = new Layer(Layer.DIV);
//		helpLayer.setStyleClass("helperText");
//		helpLayer.add(new Text(iwrb.getLocalizedString("application.closing_help", "Please enter a message that will be sent to the sender of the application about the closing.")));
//		section.add(helpLayer);
//
//		Object[] arguments = { application.getName(), application.getCompany().getPersonalID() };
//
//		TextArea message = new TextArea(PARAMETER_MESSAGE);
//		message.setStyleClass("messageArea");
//		message.setContent(MessageFormat.format(iwrb.getLocalizedString("application.closing_message_body", "Your application for company account for {0}, {1} has been closed."), arguments));
//
//		Layer formItem = new Layer(Layer.DIV);
//		formItem.setStyleClass("formItem");
//		Label label = new Label(iwrb.getLocalizedString("application.closing_message", "Closing message"), message);
//		formItem.add(label);
//		formItem.add(message);
//		section.add(formItem);
//
//		Layer clearLayer = new Layer(Layer.DIV);
//		clearLayer.setStyleClass("Clear");
//		section.add(clearLayer);
//
//		Layer bottom = new Layer(Layer.DIV);
//		bottom.setStyleClass("bottom");
//		form.add(bottom);
//
//		Link send = getButtonLink(iwrb.getLocalizedString("send", "Send"));
//		send.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ACTION_CLOSE));
//		send.setToFormSubmit(form);
//		bottom.add(send);
//
//		Link back = getButtonLink(iwrb.getLocalizedString("back", "Back"));
//		back.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ACTION_VIEW));
//		back.setToFormSubmit(form);
//		bottom.add(back);
//	}

	/*private void getEditForm(IWContext iwc, CompanyApplication application) throws RemoteException {
		super.getParentPage().addJavascriptURL("/dwr/interface/FSKDWRUtil.js");
		super.getParentPage().addJavascriptURL(CoreConstants.DWR_ENGINE_SCRIPT);
		super.getParentPage().addJavascriptURL("/dwr/util.js");
		super.getParentPage().addJavascriptURL(getBundle(iwc).getResourcesVirtualPath() + "/js/application.js");

		Form form = new Form();
		form.addParameter(ApplicationCreator.ACTION, String.valueOf(ACTION_EDIT_FORM));
		form.maintainParameter(ApplicationCreator.APPLICATION_ID_PARAMETER);
		add(form);

		Layer header = new Layer(Layer.DIV);
		header.setStyleClass("header");
		form.add(header);

		Heading1 heading = new Heading1(iwrb.getLocalizedString("application.overview", "Application overview"));
		header.add(heading);

		Company company = application.getCompany();
		form.add(getCompanyInfo(iwc, company));

		heading = new Heading1(iwrb.getLocalizedString("application.enter_company_info", "Enter company info"));
		heading.setStyleClass("subHeader");
		heading.setStyleClass("topSubHeader");
		form.add(heading);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(iwrb.getLocalizedString("application.change_company_info_help", "Please fill out all the inputs on the left.")));
		section.add(helpLayer);

		Phone iPhone = company.getPhone();
		TextInput phone = new TextInput(PARAMETER_PHONE);
		phone.setID("companyPhone");
		phone.keepStatusOnAction(true);
		if (iPhone != null && iPhone.getNumber() != null) {
			phone.setContent(iPhone.getNumber());
		}

		Phone iFax = company.getFax();
		TextInput fax = new TextInput(PARAMETER_FAX);
		fax.setID("companyFax");
		fax.keepStatusOnAction(true);
		if (iFax != null && iFax.getNumber() != null) {
			fax.setContent(iFax.getNumber());
		}

		TextInput webPage = new TextInput(PARAMETER_WEB_PAGE);
		webPage.setID("companyWebPage");
		webPage.keepStatusOnAction(true);
		if (company.getWebPage() != null) {
			webPage.setContent(company.getWebPage());
		}

		Email iEmail = company.getEmail();
		TextInput email = new TextInput(PARAMETER_EMAIL);
		email.setID("companyEmail");
		email.keepStatusOnAction(true);
		if (iEmail != null && iEmail.getEmailAddress() != null) {
			email.setContent(iEmail.getEmailAddress());
		}
		email.setDisabled(!iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc));

		TextInput bankAccount = new TextInput(PARAMETER_BANK_ACCOUNT);
		bankAccount.setID("companyBankAccount");
		bankAccount.setContent(BANK_ACCOUNT_DEFAULT);
		bankAccount.keepStatusOnAction(true);
		if (company.getBankAccount() != null) {
			bankAccount.setContent(company.getBankAccount());
		}
		bankAccount.setDisabled(!iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc));

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(new Span(new Text(iwrb.getLocalizedString("phone", "Phone"))), phone);
		formItem.add(label);
		formItem.add(phone);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(iwrb.getLocalizedString("fax", "Fax"), fax);
		formItem.add(label);
		formItem.add(fax);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(iwrb.getLocalizedString("web_page", "Web page"), webPage);
		formItem.add(label);
		formItem.add(webPage);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(new Span(new Text(iwrb.getLocalizedString("email", "E-mail"))), email);
		formItem.add(label);
		formItem.add(email);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(new Span(new Text(iwrb.getLocalizedString("bank_account", "Bank account"))), bankAccount);
		formItem.add(label);
		formItem.add(bankAccount);
		section.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		section.add(clearLayer);

		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			heading = new Heading1(iwrb.getLocalizedString("application.enter_admin_info", "Enter admin info"));
			heading.setStyleClass("subHeader");
			form.add(heading);

			section = new Layer(Layer.DIV);
			section.setStyleClass("formSection");
			form.add(section);

			helpLayer = new Layer(Layer.DIV);
			helpLayer.setStyleClass("helperText");
			helpLayer.add(new Text(iwrb.getLocalizedString("application.admin_user_help", "Please fill out all the inputs on the left.")));
			section.add(helpLayer);

			HiddenInput adminPK = new HiddenInput(PARAMETER_ADMIN_PK);
			adminPK.setID("userPK");
			adminPK.keepStatusOnAction(true);

			TextInput adminPersonalID = new TextInput(PARAMETER_ADMIN_PERSONAL_ID);
			adminPersonalID.setID("userPersonalID");
			adminPersonalID.keepStatusOnAction(true);
			adminPersonalID.setOnKeyUp("readUser();");

			TextInput adminName = new TextInput(PARAMETER_ADMIN_NAME);
			adminName.setID("userName");
			adminName.setDisabled(true);

			TextInput workPhone = new TextInput(PARAMETER_WORK_PHONE);
			workPhone.setID("userWorkPhone");
			workPhone.keepStatusOnAction(true);

			TextInput mobilePhone = new TextInput(PARAMETER_MOBILE_PHONE);
			mobilePhone.setID("userMobilePhone");
			mobilePhone.keepStatusOnAction(true);

			TextInput adminEmail = new TextInput(PARAMETER_ADMIN_EMAIL);
			adminEmail.setID("userEmail");
			adminEmail.keepStatusOnAction(true);

			if (application != null) {
				User contact = application.getContactPerson();
				if (contact != null) {
					adminPersonalID.setContent(contact.getPersonalID());
					adminName.setContent(new Name(contact.getFirstName(), contact.getMiddleName(), contact.getLastName()).getName(iwc.getCurrentLocale()));
					adminPK.setValue(contact.getPrimaryKey().toString());

					try {
						Phone contactWorkPhone = getUserBusiness(iwc).getUsersWorkPhone(contact);
						workPhone.setContent(contactWorkPhone.getNumber());
					}
					catch (NoPhoneFoundException e) {
						//No phone found...
					}

					try {
						Phone contactMobilePhone = getUserBusiness(iwc).getUsersMobilePhone(contact);
						mobilePhone.setContent(contactMobilePhone.getNumber());
					}
					catch (NoPhoneFoundException e) {
						//No phone found...
					}

					try {
						Email contactEmail = getUserBusiness(iwc).getUsersMainEmail(contact);
						adminEmail.setContent(contactEmail.getEmailAddress());
					}
					catch (NoEmailFoundException e) {
						//No email found...
					}
				}
			}

			if (iwc.isParameterSet(PARAMETER_ADMIN_PERSONAL_ID)) {
				AdminUser user = getCompanyBusiness().getUser(iwc.getParameter(PARAMETER_ADMIN_PERSONAL_ID), iwc.getCurrentLocale().getCountry());
				adminName.setContent(user.getUserName());
			}

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			formItem.setStyleClass("required");
			label = new Label(new Span(new Text(iwrb.getLocalizedString("personal_id", "Personal ID"))), adminPersonalID);
			formItem.add(label);
			formItem.add(adminPersonalID);
			formItem.add(adminPK);
			section.add(formItem);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label(new Span(new Text(iwrb.getLocalizedString("name", "Name"))), adminName);
			formItem.add(label);
			formItem.add(adminName);
			section.add(formItem);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			formItem.setStyleClass("required");
			label = new Label(new Span(new Text(iwrb.getLocalizedString("work_phone", "Work phone"))), workPhone);
			formItem.add(label);
			formItem.add(workPhone);
			section.add(formItem);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			formItem.setStyleClass("required");
			label = new Label(new Span(new Text(iwrb.getLocalizedString("mobile_phone", "Mobile phone"))), mobilePhone);
			formItem.add(label);
			formItem.add(mobilePhone);
			section.add(formItem);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			formItem.setStyleClass("required");
			label = new Label(new Span(new Text(iwrb.getLocalizedString("email", "E-mail"))), adminEmail);
			formItem.add(label);
			formItem.add(adminEmail);
			section.add(formItem);

			form.add(clearLayer);
		}

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link send = getButtonLink(iwrb.getLocalizedString("store", "Store"));
		send.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ACTION_STORE));
		send.setToFormSubmit(form);
		bottom.add(send);

		Link back = getButtonLink(iwrb.getLocalizedString("back", "Back"));
		back.setValueOnClick(ApplicationCreator.ACTION, String.valueOf(ACTION_VIEW));
		back.setToFormSubmit(form);
		bottom.add(back);
	}*/

//	private void approve(IWContext iwc, CompanyApplication application) throws RemoteException {
//		getCompanyBusiness().approveApplication(iwc, application.getId());
//
//		Company company = application.getCompany();
//		Object[] arguments = { company.getName(), PersonalIDFormatter.format(company.getPersonalID(), iwc.getCurrentLocale()) };
//
//		showReceipt(iwc, application, iwrb.getLocalizedString("application.overview", "Application overview"), iwrb.getLocalizedString("application.accepted_heading", "Application accepted"), MessageFormat.format(iwrb.getLocalizedString("application.accepted_text", "Your application for company account has been accepted."), arguments));
//	}
//
//	private void reject(IWContext iwc, CompanyApplication application) throws RemoteException {
//		getCompanyBusiness().rejectApplication(iwc, application.getId(), iwc.getParameter(PARAMETER_MESSAGE));
//		showReceipt(iwc, application, iwrb.getLocalizedString("application.rejected_text", "Your application for company account has been rejected"), iwrb.getLocalizedString("application.rejected_heading", "Application rejected"), iwrb.getLocalizedString("application.rejected_text", "Your application for company account has been rejected."));
//	}
//
//	private void requestInfo(IWContext iwc, CompanyApplication application) throws RemoteException {
//		getCompanyBusiness().requestInformation(iwc, application.getId(), iwc.getParameter(PARAMETER_MESSAGE));
//		showReceipt(iwc, application, iwrb.getLocalizedString("application.request_info", "Request further info"), iwrb.getLocalizedString("application.request_info_heading", "Request for further info sent"), iwrb.getLocalizedString("application.request_info_text", "A request for further information has been sent to the application's owner."));
//	}
//
//	private void reactivate(IWContext iwc, CompanyApplication application) throws RemoteException {
//		getCompanyBusiness().reactivateApplication(iwc, application.getId(), iwc.getParameter(PARAMETER_MESSAGE));
//		showReceipt(iwc, application, iwrb.getLocalizedString("application.overview", "Application overview"), iwrb.getLocalizedString("application.reactivated_heading", "Application reactivate"), iwrb.getLocalizedString("application.reactivated_text", "Application has been reactivated."));
//	}
//
//	private void reopen(IWContext iwc, CompanyApplication application) throws RemoteException {
////		getCompanyBusiness().reopenApplication(application, iwc.getCurrentUser(), iwc.getCurrentLocale());
////		showReceipt(iwc, application, iwrb.getLocalizedString("application.overview", "Application overview"), iwrb.getLocalizedString("application.reopen_heading", "Application reopened"), iwrb.getLocalizedString("application.reopen_text", "Application has been reopened."));
//	}
//
//	private void close(IWContext iwc, CompanyApplication application) throws RemoteException {
////		getCompanyBusiness().closeApplication(application, iwc.getCurrentUser(), iwc.getCurrentLocale(), iwc.getParameter(PARAMETER_MESSAGE));
////		showReceipt(iwc, application, iwrb.getLocalizedString("application.overview", "Application overview"), iwrb.getLocalizedString("application.closed_heading", "Application closed"), iwrb.getLocalizedString("application.closed_text", "Application has been closed."));
//	}

	/*private boolean store(IWContext iwc, CompanyApplication application) throws RemoteException {
		if (!iwc.isParameterSet(PARAMETER_PHONE)) {
			getParentPage().setAlertOnLoad(iwrb.getLocalizedString("application_error.must_enter_phone", "You have to enter a phone number."));
			return false;
		}

		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			if (!iwc.isParameterSet(PARAMETER_EMAIL)) {
				getParentPage().setAlertOnLoad(iwrb.getLocalizedString("application_error.must_enter_email", "You have to enter an e-mail address."));
				return false;
			}
			else if (!EmailValidator.getInstance().validateEmail(iwc.getParameter(PARAMETER_EMAIL))) {
				getParentPage().setAlertOnLoad(iwrb.getLocalizedString("application_error.invalid_email", "You have entered an invalid e-mail address."));
				return false;
			}
			if (!iwc.isParameterSet(PARAMETER_BANK_ACCOUNT) || iwc.getParameter(PARAMETER_BANK_ACCOUNT).equals(BANK_ACCOUNT_DEFAULT)) {
				getParentPage().setAlertOnLoad(iwrb.getLocalizedString("application_error.must_enter_bank_account", "You have to enter a bank account."));
				return false;
			}
			else {
				String bankAccount = iwc.getParameter(PARAMETER_BANK_ACCOUNT);
				Pattern pat = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{6}$");
				Matcher matcher = pat.matcher(bankAccount);
				if (!matcher.find()) {
					getParentPage().setAlertOnLoad(iwrb.getLocalizedString("application_error.invalid_bank_account_number", "You have entered an invalid bank account number."));
					return false;
				}
			}

			if (!iwc.isParameterSet(PARAMETER_ADMIN_PK)) {
				getParentPage().setAlertOnLoad(iwrb.getLocalizedString("application_error.invalid_user", "You have to select an admin user."));
				return false;
			}
			if (!iwc.isParameterSet(PARAMETER_WORK_PHONE)) {
				getParentPage().setAlertOnLoad(iwrb.getLocalizedString("application_error.must_enter_work_phone", "You have to enter work phone."));
				return false;
			}
			if (!iwc.isParameterSet(PARAMETER_MOBILE_PHONE)) {
				getParentPage().setAlertOnLoad(iwrb.getLocalizedString("application_error.must_enter_mobile_phone", "You have to enter mobile phone."));
				return false;
			}
			if (!iwc.isParameterSet(PARAMETER_ADMIN_EMAIL)) {
				getParentPage().setAlertOnLoad(iwrb.getLocalizedString("application_error.must_enter_admin_email", "You have to enter an email address."));
				return false;
			}
			else if (!EmailValidator.getInstance().validateEmail(iwc.getParameter(PARAMETER_ADMIN_EMAIL))) {
				getParentPage().setAlertOnLoad(iwrb.getLocalizedString("application_error.invalid_email", "You have entered an invalid e-mail address."));
				return false;
			}

			String workPhone = iwc.isParameterSet(PARAMETER_WORK_PHONE) ? iwc.getParameter(PARAMETER_WORK_PHONE) : null;
			String mobilePhone = iwc.isParameterSet(PARAMETER_MOBILE_PHONE) ? iwc.getParameter(PARAMETER_MOBILE_PHONE) : null;
			String email = iwc.isParameterSet(PARAMETER_ADMIN_EMAIL) ? iwc.getParameter(PARAMETER_ADMIN_EMAIL) : null;

			getCompanyBusiness().storeUserInformation(iwc.getParameter(PARAMETER_ADMIN_PK), workPhone, mobilePhone, email);
		}

		getCompanyBusiness().storeChanges(application, iwc.getParameter(PARAMETER_PHONE), iwc.getParameter(PARAMETER_FAX), iwc.getParameter(PARAMETER_EMAIL), iwc.getParameter(PARAMETER_WEB_PAGE), iwc.getParameter(PARAMETER_BANK_ACCOUNT), iwc.getParameter(PARAMETER_ADMIN_PK));
		showReceipt(iwc, application, iwrb.getLocalizedString("application.overview", "Application overview"), iwrb.getLocalizedString("application.application_changes_stored_heading", "Application changes stored"), iwrb.getLocalizedString("application.application_changes_stored_text", "Application changes have been stored."));
		return true;
	}*/

//	private void showReceipt(IWContext iwc, CompanyApplication application, String title, String subject, String body) throws RemoteException {
//		Form form = new Form();
//		add(form);
//
//		Layer header = new Layer(Layer.DIV);
//		header.setStyleClass("header");
//		form.add(header);
//
//		Heading1 heading = new Heading1(title);
//		header.add(heading);
//
//		Layer layer = new Layer(Layer.DIV);
//		layer.setStyleClass("receipt");
//		form.add(layer);
//
//		Layer image = new Layer(Layer.DIV);
//		image.setStyleClass("receiptImage");
//		layer.add(image);
//
//		heading = new Heading1(subject);
//		layer.add(heading);
//
//		Paragraph paragraph = new Paragraph();
//		paragraph.add(new Text(body));
//		layer.add(paragraph);
//
//		Layer bottom = new Layer(Layer.DIV);
//		bottom.setStyleClass("bottom");
//		form.add(bottom);
//
//		Link home = getButtonLink(iwrb.getLocalizedString("back", "Back"));
//		home.setStyleClass("buttonHome");
//		if (getBackPage() != null) {
//			home.setPage(getBackPage());
//		}
//		else {
//			home.addParameter(ApplicationCreator.ACTION, String.valueOf(ACTION_VIEW));
//			home.addParameter(ApplicationCreator.APPLICATION_ID_PARAMETER, application.getPrimaryKey().toString());
//		}
//		bottom.add(home);
//	}
//
	public ICPage getBackPage() {
		return backPage;
	}

	public void setBackPage(ICPage backPage) {
		this.backPage = backPage;
	}
}