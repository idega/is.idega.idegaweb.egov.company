package is.idega.idegaweb.egov.company.presentation.institution;

import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.application.presentation.ApplicationCreator;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;
import is.idega.idegaweb.egov.company.data.CompanyApplication;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;
import is.idega.idegaweb.egov.company.presentation.company.CompanyApplicationViewer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.FinderException;
import javax.faces.component.UIComponent;

import com.idega.builder.bean.AdvancedProperty;
import com.idega.core.builder.data.ICPage;
import com.idega.presentation.CSSSpacer;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableBodyRowGroup;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Break;
import com.idega.presentation.text.Heading3;
import com.idega.presentation.text.Heading4;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextArea;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

public class ApplicationApproverRejecter extends CompanyBlock {
	
	private static final String CLOSURE_TEXT = "application_closure_explanation_text";
	private static final String REJECTION_EXPLANATION_TEXT = "application_rejection_explanation_text";
	
	public static final int ACTION_VIEW = 1;
	public static final int ACTION_APPROVE = 2;
	public static final int ACTION_REJECTION_FORM = 3;
	public static final int ACTION_REJECT = 4;
	public static final int ACTION_REQUEST_FORM = 5;
	public static final int ACTION_REQUEST_INFO = 6;
	public static final int ACTION_REACTIVATE_FORM = 7;
	public static final int ACTION_REACTIVATE = 8;
	public static final int ACTION_OPEN = 9;
	public static final int ACTION_CLOSING_FORM = 10;
	public static final int ACTION_CLOSE = 11;
	public static final int ACTION_EDIT_FORM = 12;
	public static final int ACTION_LIST = 14;
	public static final int ACTION_CLOSE_ACCOUNT = 15;
	
	private static final int NO_ACTIONS = -1;
	private static final int ALL_ACTIONS = 0;
	private static final int APPROVE_ACTION = 1;
	private static final int REJECT_ACTION = 2;
	
	private String caseCode;
	private Integer applicationType;
	
	private List<String> applicationHandlingResultMessage;
	
	private ICPage backPage;
	
	@Override
	protected void present(IWContext iwc) throws Exception {
		super.present(iwc);
		
		boolean hasRight = getCompanyBusiness().isInstitutionAdministration(iwc) || getCompanyBusiness().isCompanyAdministrator(iwc);
		if (!hasRight) {
			showInsufficientRightsMessage(iwrb.getLocalizedString("insufficient_rigths_to_manage_applications",
					"You have insufficient rights to approve/reject applications!"));
			return;
		}
		
		switch (parseAction(iwc)) {
			case ACTION_VIEW:
				viewApplication(iwc);
				break;
			case ACTION_APPROVE:
				approveApplication(iwc);
				break;
			case ACTION_REJECTION_FORM:
				showRejectionForm(iwc);
				break;
			case ACTION_REJECT:
				rejectApplication(iwc);
				break;
			case ACTION_REACTIVATE_FORM:
				showReactivationForm(iwc);
				break;
			case ACTION_REACTIVATE:
				approveApplication(iwc);
				break;
			case ACTION_OPEN:
				reopenAccount(iwc);
				break;
			case ACTION_CLOSING_FORM:
				showClosingForm(iwc);
				break;
			case ACTION_CLOSE:
				rejectApplication(iwc);
				break;
			case ACTION_LIST:
				listApplications(iwc);
				break;
			case ACTION_CLOSE_ACCOUNT:
				closeAccount(iwc);
				break;
			default:
				listApplications(iwc);
		}
	}
	
	protected int parseAction(IWContext iwc) {
		if (iwc.isParameterSet(ApplicationCreator.ACTION)) {
			return Integer.parseInt(iwc.getParameter(ApplicationCreator.ACTION));
		}
		return ACTION_LIST;
	}
	
	private void viewApplication(IWContext iwc) {
		Layer container = new Layer();
		add(container);
		
		CompanyApplicationViewer appViewer = new CompanyApplicationViewer();
		container.add(appViewer);
	}
	
	private void approveApplication(IWContext iwc) {
		CompanyApplicationBusiness compAppBusiness = getCompanyBusiness();
		try {
			applicationHandlingResultMessage = compAppBusiness.approveApplication(iwc, iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (ListUtil.isEmpty(applicationHandlingResultMessage)) {
			applicationHandlingResultMessage = Arrays.asList(iwrb.getLocalizedString("application_was_not_approved", "Application was not approved! Some error occurred."));
		}
		
		listApplications(iwc);
	}
	
	private void showReactivationForm(IWContext iwc) {
		Form form = new Form();
		add(form);
		form.setStyleClass("adminForm");
		
		if (!iwc.isParameterSet(ApplicationCreator.APPLICATION_ID_PARAMETER)) {
			form.add(getNoApplicationSelectedLabel());
		}
		else {
			Object primaryKey = iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER);
			Application app = null;
			try {
				app = getCompanyBusiness().getApplication(primaryKey);
			} catch (RemoteException e) {
				e.printStackTrace();
			} 
			if (app == null) {
				form.add(getNoApplicationSelectedLabel());
			}
			else {
				form.add(new Heading3(iwrb.getLocalizedString("reactivation_of", "Reactivation of:") + " " + getApplicationName(app, iwc.getCurrentLocale())));
				
				Map<String, List<UIComponent>> formSectionItems = new HashMap<String, List<UIComponent>>();
				List<UIComponent> explanationTextSection = new ArrayList<UIComponent>();
				TextArea explanationText = new TextArea(REJECTION_EXPLANATION_TEXT);
				explanationTextSection.add(new Label(iwrb.getLocalizedString("explanation_text", "Explanation"), explanationText));
				explanationTextSection.add(explanationText);
				formSectionItems.put("REACTIVATION_EXPLANATION_TEXT", explanationTextSection);
				Layer formSection = getFormSection(iwrb.getLocalizedString("reason_to_reactivate", "Discribe reason of reactivation"), formSectionItems);
				form.add(formSection);
			}
		}
		
		Layer buttonLayer = new Layer();
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);
		SubmitButton backButton = new SubmitButton(iwrb.getLocalizedString("back", "Back"));
		buttonLayer.add(backButton);
		backButton.setToolTip(iwrb.getLocalizedString("back_to_applications_list", "Do not reactivate and go back to applications list"));
		
		if (iwc.isParameterSet(ApplicationCreator.APPLICATION_ID_PARAMETER)) {
			form.addParameter(ApplicationCreator.APPLICATION_ID_PARAMETER, iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER));
			
			SubmitButton rejectButton = new SubmitButton(iwrb.getLocalizedString("reactivate", "Reactivate"), ApplicationCreator.ACTION, ACTION_REACTIVATE + "");
			
			buttonLayer.add(rejectButton);
			rejectButton.setToolTip(iwrb.getLocalizedString("send_explanation_and_reactivate", "Send explanation and reactivate application"));
			StringBuilder action = new StringBuilder("if (!window.confirm('").append(iwrb.getLocalizedString("are_you_sure", "Are you sure?"));
			action.append("')) {return false;}");
			rejectButton.setOnClick(action.toString());
		}
	}
	
	private void reopenAccount(IWContext iwc) {
		applicationHandlingResultMessage = getCompanyBusiness().reopenAccount(iwc, iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER)) ?
				Arrays.asList(iwrb.getLocalizedString("account_for_application_was_opened", "Account for application was successfully opened!")) :
				Arrays.asList(iwrb.getLocalizedString("account_for_application_was_not_opened", "Account for application was not opened! Some error occurred."));
		
		listApplications(iwc);
	}
	
	private void closeAccount(IWContext iwc) {
		applicationHandlingResultMessage = getCompanyBusiness().closeAccount(iwc, iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER)) ?
				Arrays.asList(iwrb.getLocalizedString("account_for_application_was_closed", "Account for application was successfully closed!")) :
				Arrays.asList(iwrb.getLocalizedString("account_for_application_was_not_closed", "Account for application was not closed! Some error occurred."));
				
		listApplications(iwc);
	}
	
	private void showRejectionForm(IWContext iwc) {
		Form form = new Form();
		add(form);
		form.setStyleClass("adminForm");
		
		if (!iwc.isParameterSet(ApplicationCreator.APPLICATION_ID_PARAMETER)) {
			form.add(getNoApplicationSelectedLabel());
		}
		else {
			Object primaryKey = iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER);
			Application app = null;
			try {
				app = getCompanyBusiness().getApplication(primaryKey);
			} catch (RemoteException e) {
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
			
			SubmitButton rejectButton = new SubmitButton(iwrb.getLocalizedString("reject", "Reject"), ApplicationCreator.ACTION, String.valueOf(ACTION_REJECT));
			
			buttonLayer.add(rejectButton);
			rejectButton.setToolTip(iwrb.getLocalizedString("send_explanation_and_reject", "Send explanation and reject application"));
		}
	}
	
	private void rejectApplication(IWContext iwc) {
		boolean result = false;
		CompanyApplicationBusiness compAppBusiness = getCompanyBusiness();
		try {
			result = compAppBusiness.rejectApplication(iwc, iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER),
					iwc.getParameter(REJECTION_EXPLANATION_TEXT));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		applicationHandlingResultMessage = result ? Arrays.asList(iwrb.getLocalizedString("application_successfully_rejected", "Application was successfully rejected!")) :
			Arrays.asList(iwrb.getLocalizedString("application_was_not_rejected", "Application was not rejected! Some error occurred."));
		
		listApplications(iwc);
	}
	
	private void showClosingForm(IWContext iwc) {
		Form form = new Form();
		add(form);
		form.setStyleClass("adminForm");
		
		if (!iwc.isParameterSet(ApplicationCreator.APPLICATION_ID_PARAMETER)) {
			form.add(getNoApplicationSelectedLabel());
		}
		else {
			Object primaryKey = iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER);
			Application app = null;
			try {
				app = getCompanyBusiness().getApplication(primaryKey);
			} catch (RemoteException e) {
				e.printStackTrace();
			} 
			if (app == null) {
				form.add(getNoApplicationSelectedLabel());
			}
			else {
				form.add(new Heading3(iwrb.getLocalizedString("closure_of", "Closure of:") + " " + getApplicationName(app, iwc.getCurrentLocale())));
				
				Map<String, List<UIComponent>> formSectionItems = new HashMap<String, List<UIComponent>>();
				List<UIComponent> explanationTextSection = new ArrayList<UIComponent>();
				TextArea explanationText = new TextArea(CLOSURE_TEXT);
				explanationTextSection.add(new Label(iwrb.getLocalizedString("explanation_text", "Explanation"), explanationText));
				explanationTextSection.add(explanationText);
				formSectionItems.put("explanationTextSection", explanationTextSection);
				Layer formSection = getFormSection(iwrb.getLocalizedString("reason_to_close", "Discribe reason of closure"), formSectionItems);
				form.add(formSection);
			}
		}
		
		Layer buttonLayer = new Layer();
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);
		SubmitButton backButton = new SubmitButton(iwrb.getLocalizedString("back", "Back"));
		buttonLayer.add(backButton);
		backButton.setToolTip(iwrb.getLocalizedString("back_to_applications_list", "Do not close and go back to applications list"));
		
		if (iwc.isParameterSet(ApplicationCreator.APPLICATION_ID_PARAMETER)) {
			form.addParameter(ApplicationCreator.APPLICATION_ID_PARAMETER, iwc.getParameter(ApplicationCreator.APPLICATION_ID_PARAMETER));
			
			SubmitButton rejectButton = new SubmitButton(iwrb.getLocalizedString("close", "Close"), ApplicationCreator.ACTION, String.valueOf(ACTION_CLOSE));
			
			buttonLayer.add(rejectButton);
			rejectButton.setToolTip(iwrb.getLocalizedString("send_explanation_and_close", "Send explanation and close application"));
			StringBuilder action = new StringBuilder("if (!window.confirm('").append(iwrb.getLocalizedString("are_you_sure", "Are you sure?"));
			action.append("')) {return false;}");
			rejectButton.setOnClick(action.toString());
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

		if (!ListUtil.isEmpty(applicationHandlingResultMessage)) {
			Layer messageContainer = new Layer();
			messageContainer.setStyleClass("applicationHandlingResultMessageStyle");
			container.add(messageContainer);
			
			for (String line: applicationHandlingResultMessage) {
				messageContainer.add(getMessage(line, "applicationHandlingResultMessageLineStyle", true));
				messageContainer.add(new Break());
			}
		}
		
		if (StringUtil.isEmpty(caseCode)) {
			container.add(new Heading3(iwrb.getLocalizedString("case_codes_property_missing", "Set case codes property firstly!")));
			return;
		}
		if (applicationType == null) {
			container.add(new Heading4(iwrb.getLocalizedString("we_recommend_select_application_type_first", "We recommend select application type firstly.")));
		}
		
		Form form = new Form();
		container.add(form);
		form.setStyleClass("adminForm");
		
		Locale locale = iwc.getCurrentLocale();
		String[] caseCodes = new String[] {caseCode};
		CompanyApplicationBusiness compAppBusiness = getCompanyBusiness();
		
		boolean showAllApplications = applicationType == null;
		if (showAllApplications || EgovCompanyConstants.APPLICATION_TYPE_UNHANDLED.equals(applicationType)) {
			form.add(getApplicationsSection(iwrb.getLocalizedString("unhandled_applications", "Unhandled applications") + ":", "unhandledApplicationsStyle",
				compAppBusiness.getUnhandledApplications(caseCodes), locale, ALL_ACTIONS));
		}
		if (showAllApplications || EgovCompanyConstants.APPLICATION_TYPE_APPROVED.equals(applicationType)) {
			form.add(getApplicationsSection(iwrb.getLocalizedString("approved_applications", "Approved applications") + ":", "approvedApplicationsStyle",
				compAppBusiness.getApprovedApplications(caseCodes), locale, REJECT_ACTION));
		}
		
		if (showAllApplications || EgovCompanyConstants.APPLICATION_TYPE_REJECTED.equals(applicationType)) {
			form.add(getApplicationsSection(iwrb.getLocalizedString("rejected_applications", "Rejected applications") + ":", "rejectedApplicationsStyle",
				compAppBusiness.getRejectedApplications(caseCodes), locale, APPROVE_ACTION));
		}
	}
	
	private Layer getApplicationsSection(String label, String styleName, Collection<CompanyApplication> applications, Locale locale, int actionCode) {
		Layer container = new Layer();
		container.setStyleClass(styleName);
		
		Heading3 labelContainer = new Heading3(label);
		container.add(labelContainer);
		labelContainer.setStyleClass("applicationsTypeLabelStyle");
		
		if (ListUtil.isEmpty(applications)) {
			container.add(getNoApplicationsHeading());
			return container;
		}
		
		container.add(getApplicationsTable(applications, locale, actionCode));
		container.add(new CSSSpacer());
		return container;
	}
	
	private Table2 getApplicationsTable(Collection<CompanyApplication> applications, Locale locale, int actionCode) {
		boolean canDoActions = actionCode != NO_ACTIONS;
		
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
			switch (actionCode) {
			case ALL_ACTIONS:
				getRowCell(row, iwrb.getLocalizedString("approve", "Approve"), "applicationApproveHeader", true);
				getRowCell(row, iwrb.getLocalizedString("reject", "Reject"), "lastColumn applicationRejectHeader", true);
				break;
			case APPROVE_ACTION:
				getRowCell(row, iwrb.getLocalizedString("approve", "Approve"), "lastColumn applicationApproveHeader", true);
				break;
			case REJECT_ACTION:
				getRowCell(row, iwrb.getLocalizedString("reject", "Reject"), "lastColumn applicationRejectHeader", true);
				break;
			default:
				break;
			}
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
			parameters.add(new AdvancedProperty(ApplicationCreator.ACTION, String.valueOf(ACTION_VIEW)));
			parameters.add(new AdvancedProperty(ApplicationCreator.ACTION, String.valueOf(ACTION_VIEW)));
			cell.add(getLink(viewImageUri, viewImageTooltip, applicationId, parameters));
			
			if (canDoActions) {
				switch (actionCode) {
				case ALL_ACTIONS:
					cell = getRowCell(row, null, "applicationApproveBody", false);
					parameters = new ArrayList<AdvancedProperty>();
					parameters.add(new AdvancedProperty(ApplicationCreator.ACTION, String.valueOf(ACTION_APPROVE)));
					cell.add(getLink(approveImageUri, approveImageTooltip, applicationId, parameters));
					
					cell = getRowCell(row, null, "lastColumn applicationRejectBody", false);
					parameters = new ArrayList<AdvancedProperty>();
					parameters.add(new AdvancedProperty(ApplicationCreator.ACTION, String.valueOf(ACTION_REJECTION_FORM)));
					cell.add(getLink(rejectImageUri, rejectImageTooltip, applicationId, parameters));
					break;
				case APPROVE_ACTION:
					cell = getRowCell(row, null, "applicationApproveBody", false);
					parameters = new ArrayList<AdvancedProperty>();
					parameters.add(new AdvancedProperty(ApplicationCreator.ACTION, String.valueOf(ACTION_APPROVE)));
					cell.add(getLink(approveImageUri, approveImageTooltip, applicationId, parameters));
					break;
				case REJECT_ACTION:
					cell = getRowCell(row, null, "lastColumn applicationRejectBody", false);
					parameters = new ArrayList<AdvancedProperty>();
					parameters.add(new AdvancedProperty(ApplicationCreator.ACTION, String.valueOf(ACTION_REJECTION_FORM)));
					cell.add(getLink(rejectImageUri, rejectImageTooltip, applicationId, parameters));
					break;
				default:
					break;
				}
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
	
	private Heading4 getNoApplicationsHeading() {
		Heading4 label = new Heading4(iwrb.getLocalizedString("there_are_no_applications_of_this_type", "There are no applications of this type"));
		label.setStyleClass("noApplicationsOfThisTypeStyle");
		return label;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public Integer getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(Integer applicationType) {
		this.applicationType = applicationType;
	}
	
	public ICPage getBackPage() {
		return backPage;
	}

	public void setBackPage(ICPage backPage) {
		this.backPage = backPage;
	}
}
