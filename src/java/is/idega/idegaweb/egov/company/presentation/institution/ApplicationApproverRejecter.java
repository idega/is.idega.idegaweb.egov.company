package is.idega.idegaweb.egov.company.presentation.institution;

import is.idega.idegaweb.egov.application.business.ApplicationBusiness;
import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.application.presentation.ApplicationCreator;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.FinderException;
import javax.faces.component.UIComponent;

import com.idega.builder.bean.AdvancedProperty;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.CSSSpacer;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableBodyRowGroup;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Heading2;
import com.idega.presentation.text.Heading3;
import com.idega.presentation.text.Heading4;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextArea;
import com.idega.util.ListUtil;
import com.idega.util.PresentationUtil;
import com.idega.util.StringUtil;

public class ApplicationApproverRejecter extends CompanyBlock {

	private static final String VIEW_APPLICATION_PARAMETER = "prm_view_application_in_applications_approver";
	private static final String APPROVE_APPLICATION_PARAMETER = "prm_approve_application";
	private static final String REJECT_APPLICATION_PARAMETER = "prm_reject_application";
	private static final String SEND_EXPLANATION_AND_REJECT_APPLICATION_PARAMETER = "prm_send_explanation_and_reject_application";
	
	private static final String REJECTION_EXPLANATION_TEXT = "application_rejection_explanation_text";
	
	private String caseCode;
	
	private IWBundle bundle;
	private IWResourceBundle iwrb;
	private String applicationHandlingResultMessage;
	
	@Override
	public void present(IWContext iwc) throws Exception {
		bundle = getBundle(iwc);
		iwrb = bundle.getResourceBundle(iwc);
		
		PresentationUtil.addStyleSheetToHeader(iwc, bundle.getVirtualPathWithFileNameString("style/egov_company.css"));
		
		if (iwc.isParameterSet(VIEW_APPLICATION_PARAMETER)) {
			viewApplication(iwc);
			return;
		}
		if (iwc.isParameterSet(APPROVE_APPLICATION_PARAMETER)) {
			approveApplication(iwc);
		}
		else if (iwc.isParameterSet(REJECT_APPLICATION_PARAMETER)) {
			showRejectionForm(iwc);
			return;
		}
		else if (iwc.isParameterSet(SEND_EXPLANATION_AND_REJECT_APPLICATION_PARAMETER)) {
			rejectApplication(iwc);
		}
		
		listApplications(iwc);
	}
	
	private void viewApplication(IWContext iwc) {
		ServicesRegister servicesRegister = new ServicesRegister();
		add(servicesRegister);
		servicesRegister.setAddSaveButton(false);
	}
	
	private void approveApplication(IWContext iwc) {
		CompanyApplicationBusiness compAppBusiness = getCompanyBusiness();
		if (compAppBusiness.approveApplication(iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER))) {
			applicationHandlingResultMessage = iwrb.getLocalizedString("application_successfully_approved", "Application was successfully approved!");
		}
		else {
			applicationHandlingResultMessage = iwrb.getLocalizedString("application_was_not_approved", "Application was not approved! Some error occurred.");
		}
	}
	
	private void showRejectionForm(IWContext iwc) {
		Form form = new Form();
		add(form);
		form.setStyleClass("adminForm");
		form.add(getComponentLabel());
		
		if (!iwc.isParameterSet(ApplicationCreator.APPLICATION_ID_PARAMETER)) {
			form.add(getNoApplicationSelectedLabel());
		}
		else {
			Object primaryKey = iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER);
			Application app = null;
			try {
				app = getApplicationBusiness(iwc).getApplication(primaryKey);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (FinderException e) {
				e.printStackTrace();
			}
			if (app == null) {
				form.add(getNoApplicationSelectedLabel());
			}
			else {
				form.add(new Heading3(iwrb.getLocalizedString("rejection_of", "Rejection of:") + " " + getApplicationName(app, iwc.getCurrentLocale())));
				
				Map<String, List<UIComponent>> formSectionItems = new HashMap<String, List<UIComponent>>();
				List<UIComponent> explanationTextSection = new ArrayList<UIComponent>();
				TextArea explanationText = new TextArea(REJECTION_EXPLANATION_TEXT);
				explanationTextSection.add(new Label(iwrb.getLocalizedString("explanation_text", "Explanation"), explanationText));
				explanationTextSection.add(explanationText);
				formSectionItems.put("explanationTextSection", explanationTextSection);
				Layer formSection = getFormSection(iwrb.getLocalizedString("reason_to_reject", "Discribe reason of rejection"), formSectionItems);
				form.add(formSection);
			}
		}
		
		Layer buttonLayer = new Layer();
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);
		SubmitButton backButton = new SubmitButton(iwrb.getLocalizedString("back", "Back"));
		buttonLayer.add(backButton);
		backButton.setToolTip(iwrb.getLocalizedString("back_to_applications_list", "Do not reject and go back to applications list"));
		
		if (iwc.isParameterSet(ApplicationCreator.APPLICATION_ID_PARAMETER)) {
			form.addParameter(ApplicationCreator.APPLICATION_ID_PARAMETER, iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER));
			
			SubmitButton rejectButton = new SubmitButton(iwrb.getLocalizedString("reject", "Reject"), SEND_EXPLANATION_AND_REJECT_APPLICATION_PARAMETER,
					Boolean.TRUE.toString());
			
			buttonLayer.add(rejectButton);
			rejectButton.setToolTip(iwrb.getLocalizedString("send_explanation_and_reject", "Send explanation and reject application"));
			StringBuilder action = new StringBuilder("if (!window.confirm('").append(iwrb.getLocalizedString("are_you_sure", "Are you sure?"));
			action.append("')) {return false;}");
			rejectButton.setOnClick(action.toString());
		}
	}
	
	private void rejectApplication(IWContext iwc) {
		CompanyApplicationBusiness compAppBusiness = getCompanyBusiness();
		if (compAppBusiness.rejectApplication(iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER), iwc.getParameter(REJECTION_EXPLANATION_TEXT))) {
			applicationHandlingResultMessage = iwrb.getLocalizedString("application_successfully_rejected", "Application was successfully rejected!");
		}
		else {
			applicationHandlingResultMessage = iwrb.getLocalizedString("application_was_not_rejected", "Application was not rejected! Some error occurred.");
		}
	}
	
	private Heading3 getNoApplicationSelectedLabel() {
		Heading3 label = new Heading3(iwrb.getLocalizedString("no_application_selected", "Select application first!"));
		label.setStyleClass("noApplicationSelectedStyle");
		return label;
	}
	
	private void listApplications(IWContext iwc) {
		Layer container = new Layer();
		add(container);
		
		container.add(getComponentLabel());
		if (!StringUtil.isEmpty(applicationHandlingResultMessage)) {
			Heading2 message = new Heading2(applicationHandlingResultMessage);
			container.add(message);
			message.setStyleClass("applicationHandlingResultMessageStyle");
		}
		
		if (StringUtil.isEmpty(caseCode)) {
			container.add(new Heading3(iwrb.getLocalizedString("case_codes_property_missing", "Set case codes property firstly!")));
			return;
		}
		
		Form form = new Form();
		container.add(form);
		form.setStyleClass("adminForm");
		
		Locale locale = iwc.getCurrentLocale();
		String[] caseCodes = new String[] {caseCode};
		ApplicationBusiness appBusiness = getApplicationBusiness(iwc);
		
		form.add(getApplicationsSection(iwrb.getLocalizedString("unhandled_applications", "Unhandled applications") + ":", "unhandledApplicationsStyle",
				appBusiness.getUnhandledApplications(caseCodes), locale, true));
		
		form.add(getApplicationsSection(iwrb.getLocalizedString("approved_applications", "Approved applications") + ":", "approvedApplicationsStyle",
				appBusiness.getApprovedApplications(caseCodes), locale, false));
		
		form.add(getApplicationsSection(iwrb.getLocalizedString("rejected_applications", "Rejected applications") + ":", "rejectedApplicationsStyle",
				appBusiness.getRejectedApplications(caseCodes), locale, false));
	}
	
	private Layer getApplicationsSection(String label, String styleName, Collection<Application> applications, Locale locale, boolean canDoActions) {
		Layer container = new Layer();
		container.setStyleClass(styleName);
		
		Heading3 labelContainer = new Heading3(label);
		container.add(labelContainer);
		labelContainer.setStyleClass("applicationsTypeLabelStyle");
		
		if (ListUtil.isEmpty(applications)) {
			container.add(getNoApplicationsHeading());
			return container;
		}
		
		container.add(getApplicationsTable(applications, locale, canDoActions));
		container.add(new CSSSpacer());
		return container;
	}
	
	private Table2 getApplicationsTable(Collection<Application> applications, Locale locale, boolean canDoActions) {
		Table2 table = new Table2();
		
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");
		table.setWidth("100%");
		table.setCellpadding(0);
		table.setCellspacing(0);

		TableRowGroup headerRows = table.createHeaderRowGroup();
		TableRow row = headerRows.createRow();
		getRowCell(row, iwrb.getLocalizedString("application", "Application"), "firstColumn applicationNameHeader", true);
		getRowCell(row, iwrb.getLocalizedString("view", "View"), canDoActions ? "applicationViewHeader" : "lastColumn applicationViewHeader", true);
		if (canDoActions) {
			getRowCell(row, iwrb.getLocalizedString("approve", "Approve"), "applicationApproveHeader", true);
			getRowCell(row, iwrb.getLocalizedString("reject", "Reject"), "lastColumn applicationRejectHeader", true);
		}
		
		TableCell2 cell = null;
		TableBodyRowGroup bodyRows = table.createBodyRowGroup();
		String applicationId = null;
		String viewImageUri = "images/view.png";
		String viewImageTooltip = iwrb.getLocalizedString("view_application", "View application");
		String approveImageUri = "images/accept.png";
		String approveImageTooltip = iwrb.getLocalizedString("approve_application", "Approve application");
		String rejectImageUri = "images/reject.png";
		String rejectImageTooltip = iwrb.getLocalizedString("reject_application", "Reject application");
		List<AdvancedProperty> parameters = null;
		int rowIndex = 0;
		for (Application app: applications) {
			applicationId = app.getPrimaryKey().toString();
			row = bodyRows.createRow();
			
			getRowCell(row, getApplicationName(app, locale), "firstColumn applicationNameBody", false);
			
			cell = getRowCell(row, null, canDoActions ? "applicationViewBody" : "lastColumn applicationViewBody", false);
			parameters = new ArrayList<AdvancedProperty>();
			parameters.add(new AdvancedProperty(VIEW_APPLICATION_PARAMETER, Boolean.TRUE.toString()));
			parameters.add(new AdvancedProperty(ApplicationCreator.ACTION, ApplicationCreator.EDIT_ACTION));
			cell.add(getLink(viewImageUri, viewImageTooltip, applicationId, parameters));
			
			if (canDoActions) {
				cell = getRowCell(row, null, "applicationApproveBody", false);
				parameters = new ArrayList<AdvancedProperty>();
				parameters.add(new AdvancedProperty(APPROVE_APPLICATION_PARAMETER, Boolean.TRUE.toString()));
				cell.add(getLink(approveImageUri, approveImageTooltip, applicationId, parameters));
				
				cell = getRowCell(row, null, "lastColumn applicationRejectBody", false);
				parameters = new ArrayList<AdvancedProperty>();
				parameters.add(new AdvancedProperty(REJECT_APPLICATION_PARAMETER, Boolean.TRUE.toString()));
				cell.add(getLink(rejectImageUri, rejectImageTooltip, applicationId, parameters));
			}
			
			if (rowIndex % 2 == 0) {
				row.setStyleClass("evenRow");
			}
			else {
				row.setStyleClass("oddRow");
			}
			rowIndex++;
		}
		
		return table;
	}
	
	private String getApplicationName(Application application, Locale locale) {
		return getCompanyBusiness().getApplicationName(application, locale);
	}
	
	private Link getLink(String imageUri, String imageTooltip, String applicationId, List<AdvancedProperty> parameters) {
		Image view = bundle.getImage(imageUri);
		view.setToolTip(imageTooltip);
		
		Link link = new Link(view);
		link.setParameter(ApplicationCreator.APPLICATION_ID_PARAMETER, applicationId);
		if (!ListUtil.isEmpty(parameters)) {
			for (AdvancedProperty parameter: parameters) {
				link.setParameter(parameter.getId(), parameter.getValue());
			}
		}
		
		return link;
	}
	
	private TableCell2 getRowCell(TableRow row, String content, String styleName, boolean headerRow) {
		TableCell2 cell = headerRow ? row.createHeaderCell() : row.createCell();
		if (!StringUtil.isEmpty(styleName)) {
			cell.setStyleClass(styleName);
		}
		if (!StringUtil.isEmpty(content)) {
			cell.add(new Text(content));
		}
		return cell;
	}
	
	private Heading1 getComponentLabel() {
		return new Heading1(iwrb.getLocalizedString("applications_approver", "Applications approver"));
	}
	
	private Heading4 getNoApplicationsHeading() {
		Heading4 label = new Heading4(iwrb.getLocalizedString("there_are_no_applications_of_this_type", "There are no applications of this type"));
		label.setStyleClass("noApplicationsOfThisTypeStyle");
		return label;
	}
	
	@Override
	public String getBundleIdentifier() {
		return EgovCompanyConstants.IW_BUNDLE_IDENTIFIER;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	
}
