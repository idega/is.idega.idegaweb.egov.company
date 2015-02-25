package is.idega.idegaweb.egov.company.presentation.company;

import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.application.presentation.ApplicationForm;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;
import is.idega.idegaweb.egov.company.data.CompanyApplication;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.FinderException;

import com.idega.block.web2.business.Web2Business;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.accesscontrol.business.NotLoggedOnException;
import com.idega.core.builder.data.ICPage;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.PhoneTypeBMPBean;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.PostalCode;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.CSSSpacer;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Span;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Paragraph;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.InterfaceObject;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.TextInput;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.EmailValidator;
import com.idega.util.PresentationUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

/**
 * Application (and case) for company creator
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Jul 30, 2008 by Author: Anton 
 *
 */

public class CompanyApplicationCreator extends ApplicationForm {
	
	protected static final int iNumberOfPhases = 2;

	protected static final int ACTION_PHASE_1 = 1;
//	private static final int ACTION_OVERVIEW = 2;
	protected static final int ACTION_SAVE = 2;
	
	protected static final String PARAMETER_TYPE = "prm_type";
	protected static final String PARAMETER_ACTION = "prm_action";
	protected static final String PARAMETER_AGREEMENT = "prm_agreement";
	protected static final String PARAMETER_COMPANY_PERSONAL_ID = "prm_personal_id";
	protected static final String PARAMETER_NAME = "prm_name";
	protected static final String PARAMETER_ADDRESS = "prm_address";
	protected static final String PARAMETER_POSTAL_CODE = "prm_postal_code";
	protected static final String PARAMETER_CITY = "prm_city";
	protected static final String PARAMETER_PHONE = "prm_phone";
	protected static final String PARAMETER_FAX = "prm_fax";
	protected static final String PARAMETER_WEB_PAGE = "prm_web_page";
	protected static final String PARAMETER_EMAIL = "prm_email";
	//protected static final String PARAMETER_BANK_ACCOUNT = "prm_bank_account";

	protected static final String PARAMETER_ADMIN_PK = "prm_admin_pk";
	protected static final String PARAMETER_ADMIN_PERSONAL_ID = "prm_admin_personal_id";
	protected static final String PARAMETER_ADMIN_NAME = "prm_admin_name";
	protected static final String PARAMETER_WORK_PHONE = "prm_work_phone";
	protected static final String PARAMETER_MOBILE_PHONE = "prm_mobile_phone";
	protected static final String PARAMETER_ADMIN_EMAIL = "prm_admin_email";
	
	protected static final String BANK_ACCOUNT_DEFAULT = "0000-00-000000";
	
//	private String requiredFieldLocalizationKey = "this_field_is_required";
//	private String requiredFieldLocalizationValue = "This field is required!";
	
	protected IWResourceBundle iwrb;

	@Override
	public String getBundleIdentifier() {
		return EgovCompanyConstants.IW_BUNDLE_IDENTIFIER;
	}
	
	@Override
	protected void present(IWContext iwc) {
		iwrb = getResourceBundle(iwc);

		try {
			switch (parseAction(iwc)) {
				case ACTION_PHASE_1:
					showPhaseOne(iwc, ACTION_SAVE, iNumberOfPhases);
					break;

//				case ACTION_OVERVIEW:
//					showOverview(iwc, ACTION_PHASE_1, ACTION_OVERVIEW, ACTION_SAVE, iNumberOfPhases);
//					break;

				case ACTION_SAVE:
					save(iwc);
					break;
			}
		}
		catch (RemoteException re) {
			re.printStackTrace();
		}
	}
	
	protected void showPhaseOne(IWContext iwc, int nextPhase, int iNumberOfPhases) throws RemoteException {
		Company company = null;
		if (iwc.isParameterSet(PARAMETER_COMPANY_PERSONAL_ID)) {
			try {
				company = getCompanyBusiness(iwc).getCompany(iwc.getParameter(PARAMETER_COMPANY_PERSONAL_ID));
			} catch (FinderException e) {}
		}
		
		User user = null;
		if (iwc.isParameterSet(PARAMETER_ADMIN_PERSONAL_ID)) {
			try {
				user = getUserBusiness(iwc).getUser(iwc.getParameter(PARAMETER_ADMIN_PERSONAL_ID));
			} catch (FinderException e) {}
		}
		
		Form form = getMainForm(iwc, company, user, iwrb.getLocalizedString("application.company_information", "Company information"), ACTION_PHASE_1,
				iNumberOfPhases);
		
		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(iwrb.getLocalizedString("send", "Send"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(nextPhase));
		next.setToFormSubmit(form);
		bottom.add(next);
		add(form);
	}
	
//	private void showOverview(IWContext iwc, int prevPhase, int currentPhase, int nextPhase, int iNumberOfPhases) throws RemoteException {
//		Company company = null;
//		
//		if (!iwc.isParameterSet(PARAMETER_AGREEMENT)) {
//			setError(PARAMETER_AGREEMENT, iwrb.getLocalizedString("application_error.must_agree_terms", "You have to agree to the terms."));
//		}
//		if (!iwc.isParameterSet(PARAMETER_COMPANY_PERSONAL_ID)) {
//			setError(PARAMETER_COMPANY_PERSONAL_ID, iwrb.getLocalizedString("application_error.must_enter_personal_id",
//					"You have to enter a personal ID."));
//		}
//		else {
//			try {
//				company = getCompanyBusiness(iwc).getCompany(iwc.getParameter(PARAMETER_COMPANY_PERSONAL_ID));
//			}
//			catch (FinderException e) {
//				setError(PARAMETER_COMPANY_PERSONAL_ID, iwrb.getLocalizedString("application_error.company_not_found",
//						"The personal ID you have entered is not in the company register."));
//			}
//			
//			CompanyApplication application = null;
//			try {
//				application = getCompanyApplicationBusiness().getCompanyApplicationHome().findByCompany(company);
//			} catch (Exception e) {}
//			if (application != null) {
//				setError(PARAMETER_COMPANY_PERSONAL_ID, iwrb.getLocalizedString("application_error.company_already_applied",
//						"The company has already applied for an account."));
//			}
//		}
//		if (!iwc.isParameterSet(PARAMETER_PHONE)) {
//			setError(PARAMETER_PHONE, iwrb.getLocalizedString("application_error.must_enter_phone", "You have to enter a phone number."));
//		}
//		if (!iwc.isParameterSet(PARAMETER_EMAIL)) {
//			setError(PARAMETER_EMAIL, iwrb.getLocalizedString("application_error.must_enter_email", "You have to enter an e-mail address."));
//		}
//		else if (!EmailValidator.getInstance().validateEmail(iwc.getParameter(PARAMETER_EMAIL))) {
//			setError(PARAMETER_EMAIL, iwrb.getLocalizedString("application_error.invalid_email", "You have entered an invalid e-mail address."));
//		}
//		if (!iwc.isParameterSet(PARAMETER_BANK_ACCOUNT) || iwc.getParameter(PARAMETER_BANK_ACCOUNT).equals(BANK_ACCOUNT_DEFAULT)) {
//			setError(PARAMETER_BANK_ACCOUNT, iwrb.getLocalizedString("application_error.must_enter_bank_account", "You have to enter a bank account."));
//		}
//		else {
//			String bankAccount = iwc.getParameter(PARAMETER_BANK_ACCOUNT);
//			Pattern pat = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{6}$");
//			Matcher matcher = pat.matcher(bankAccount);
//			if (!matcher.find()) {
//				setError(PARAMETER_BANK_ACCOUNT, iwrb.getLocalizedString("application_error.invalid_bank_account_number",
//						"You have entered an invalid bank account number."));
//			}
//		}
//		
//		User user = null;
//		if (iwc.isParameterSet(PARAMETER_ADMIN_PERSONAL_ID)) {
//			try {
//				user = getUserBusiness(iwc).getUser(iwc.getParameter(PARAMETER_ADMIN_PERSONAL_ID));
//			} catch (Exception e) {}
//			if (user == null) {
//				log(Level.INFO, "User not found by provided ID: " + iwc.getParameter(PARAMETER_ADMIN_PERSONAL_ID));
//				setError(PARAMETER_ADMIN_PERSONAL_ID, iwrb.getLocalizedString("application_error.invalid_user", "You have to select an admin user."));
//			}
//		}
//		else {
//			setError(PARAMETER_ADMIN_PERSONAL_ID, iwrb.getLocalizedString("application_error.invalid_user", "You have to select an admin user."));
//		}
//		if (!iwc.isParameterSet(PARAMETER_WORK_PHONE)) {
//			setError(PARAMETER_WORK_PHONE, iwrb.getLocalizedString("application_error.must_enter_work_phone", "You have to enter work phone."));
//		}
//		if (!iwc.isParameterSet(PARAMETER_MOBILE_PHONE)) {
//			setError(PARAMETER_MOBILE_PHONE, iwrb.getLocalizedString("application_error.must_enter_mobile_phone", "You have to enter mobile phone."));
//		}
//		if (!iwc.isParameterSet(PARAMETER_ADMIN_EMAIL)) {
//			setError(PARAMETER_ADMIN_EMAIL, iwrb.getLocalizedString("application_error.must_enter_admin_email", "You have to enter an email address."));
//		}
//		else if (!EmailValidator.getInstance().validateEmail(iwc.getParameter(PARAMETER_ADMIN_EMAIL))) {
//			setError(PARAMETER_ADMIN_EMAIL, iwrb.getLocalizedString("application_error.invalid_email", "You have entered an invalid e-mail address."));
//		}
//		if (hasErrors()) {
//			showPhaseOne(iwc, ACTION_OVERVIEW, iNumberOfPhases);
//			return;
//		}
//
//		Form form = getMainForm(iwc, company, user, iwrb.getLocalizedString("company_information_overview", "Company information overview"),
//				currentPhase, iNumberOfPhases);
//
//		Layer bottom = new Layer(Layer.DIV);
//		bottom.setStyleClass("bottom");
//		form.add(bottom);
//
//		Link next = getButtonLink(iwrb.getLocalizedString("send", "Send"));
//		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(nextPhase));
//		next.setToFormSubmit(form);
//		bottom.add(next);
//
//		Link back = getButtonLink(iwrb.getLocalizedString("previous", "Previous"));
//		back.setValueOnClick(PARAMETER_ACTION, String.valueOf(prevPhase));
//		back.setToFormSubmit(form);
//		bottom.add(back);
//		
//		add(form);
//	}
	
	private void addFormItem(Layer container, String labelText, InterfaceObject interfaceObject, String parameter, String styleClass, boolean required) {
		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		if (!StringUtil.isEmpty(styleClass)) {
			formItem.setStyleClass(styleClass);
		}
		
		Label label = new Label(new Span(new Text(labelText)), interfaceObject);
		formItem.add(label);
		formItem.add(interfaceObject);
		
		if (required) {
			formItem.setStyleClass("required");
//			addRequiredFieldMark(formItem);
			
			if (hasError(parameter)) {
				formItem.setStyleClass("hasError");
			}
		}
		
		container.add(formItem);
	}
	
	protected void addFormItem(Layer container, String labelText, InterfaceObject interfaceObject, String parameter, boolean required) {
		addFormItem(container, labelText, interfaceObject, parameter, null, required);
	}
	
	protected void addFormItem(Layer container, String labelText, InterfaceObject interfaceObject) {
		addFormItem(container, labelText, interfaceObject, null, false);
	}
	
	protected void addFormItems(Layer section, TextInput personalID, TextInput name, TextInput address, TextInput postalCode, TextInput city, TextInput phone, TextInput fax, TextInput webPage, TextInput email) {
		addFormItem(section, iwrb.getLocalizedString("personal_id", "Personal ID"), personalID, PARAMETER_COMPANY_PERSONAL_ID, true);
		
		addFormItem(section, iwrb.getLocalizedString("name", "Name"), name);

		addFormItem(section, iwrb.getLocalizedString("address", "Address"), address);

		addFormItem(section, iwrb.getLocalizedString("postal_code", "Postal code"), postalCode);

		addFormItem(section, iwrb.getLocalizedString("city", "City"), city);

		addFormItem(section, iwrb.getLocalizedString("phone", "Phone"), phone, PARAMETER_PHONE, true);

		addFormItem(section, iwrb.getLocalizedString("fax", "Fax"), fax);

		addFormItem(section, iwrb.getLocalizedString("web_page", "Web page"), webPage);

		addFormItem(section, iwrb.getLocalizedString("email", "E-mail"), email, PARAMETER_EMAIL, true);

		//addFormItem(section, iwrb.getLocalizedString("bank_account", "Bank account"), bankAccount, PARAMETER_BANK_ACCOUNT, true);		
	}
	
	protected Form getMainForm(IWContext iwc, Company company, User contactPerson, String phaseHeader, int phaseNumber, int iNumberOfPhases) {
		Form form = createForm(phaseNumber);
		form.setOnSubmit(new StringBuilder("showLoadingMessage('").append(iwrb.getLocalizedString("loading", "Loading...")).append("'); return false;")
				.toString());
		addErrors(iwc, form);
		
		//	Scripts
		List<String> scripts = new ArrayList<String>();
		scripts.add(CoreConstants.DWR_ENGINE_SCRIPT);
		scripts.add(CoreConstants.DWR_UTIL_SCRIPT);
		scripts.add("/dwr/interface/CompanyApplicationBusiness.js");
		scripts.add(getBundle(iwc).getVirtualPathWithFileNameString("javascript/CompanyApplicationCreatorHelper.js"));
		Web2Business web2 = ELUtil.getInstance().getBean(Web2Business.SPRING_BEAN_IDENTIFIER);
		scripts.add(web2.getBundleURIToJQueryLib());
		scripts.add(web2.getBundleUriToHumanizedMessagesScript());
		PresentationUtil.addJavaScriptSourcesLinesToHeader(iwc, scripts);

		//	CSS
		PresentationUtil.addStyleSheetToHeader(iwc, web2.getBundleUriToHumanizedMessagesStyleSheet());
		
		form.add(getPhasesHeader(phaseHeader, phaseNumber, iNumberOfPhases));

		Layer info = new Layer(Layer.DIV);
		info.setStyleClass("info");
		form.add(info);

		Heading1 heading = new Heading1(iwrb.getLocalizedString("application.enter_company_info2", "Enter company info"));
		heading.setStyleClass("subHeader");
		heading.setStyleClass("topSubHeader");
		form.add(heading);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(iwrb.getLocalizedString("application.company_help", "Please fill out all the inputs on the left.")));
		section.add(helpLayer);

		TextInput personalID = new TextInput(PARAMETER_COMPANY_PERSONAL_ID);
//		personalID.setID("companyPersonalID");
		personalID.setOnKeyUp(new StringBuilder("CompanyApplicationCreator.getCompanyInfo(event, '").append(personalID.getId())
								.append("', '").append(iwrb.getLocalizedString("company_not_found", "Sorry, unable to find company by provided ID"))
								.append("', '").append(iwrb.getLocalizedString("loading", "Loading..."))
								.append("');").toString());
		personalID.keepStatusOnAction(true);

		TextInput name = new TextInput(PARAMETER_NAME);
		name.setID("companyName");
		name.setDisabled(true);
		if (company != null) {
			String compName = company.getName();
			name.setContent(StringUtil.isEmpty(compName) ? CoreConstants.EMPTY : compName);
		}

		TextInput address = new TextInput(PARAMETER_ADDRESS);
		address.setID("companyAddress");
		address.setDisabled(true);
		PostalCode compPostalCode = null;
		String compCity = null;
		if (company != null) {
			Address compAddress = company.getAddress();
			if (compAddress != null) {
				compPostalCode = compAddress.getPostalCode();
				compCity = compAddress.getCity();
				
				String compStreetAddress = compAddress.getStreetAddress();
				address.setContent(StringUtil.isEmpty(compStreetAddress) ? CoreConstants.EMPTY : compStreetAddress);
			}
		}

		TextInput postalCode = new TextInput(PARAMETER_POSTAL_CODE);
		postalCode.setID("companyPostalCode");
		postalCode.setDisabled(true);
		if (compPostalCode != null) {
			String compPostalCodeValue = compPostalCode.getPostalCode();
			postalCode.setContent(StringUtil.isEmpty(compPostalCodeValue) ? CoreConstants.EMPTY : compPostalCodeValue);
		}

		TextInput city = new TextInput(PARAMETER_CITY);
		city.setID("companyCity");
		city.setDisabled(true);
		if (!StringUtil.isEmpty(compCity)) {
			city.setContent(compCity);
		}

		TextInput phone = new TextInput(PARAMETER_PHONE);
		phone.setID("companyPhone");
		phone.keepStatusOnAction(true);

		TextInput fax = new TextInput(PARAMETER_FAX);
		fax.setID("companyFax");
		fax.keepStatusOnAction(true);

		TextInput webPage = new TextInput(PARAMETER_WEB_PAGE);
		webPage.setID("companyWebPage");
		webPage.keepStatusOnAction(true);

		TextInput email = new TextInput(PARAMETER_EMAIL);
		email.setID("companyEmail");
		email.keepStatusOnAction(true);

//		TextInput bankAccount = new TextInput(PARAMETER_BANK_ACCOUNT);
//		bankAccount.setID("companyBankAccount");
//		String bankAccountNumber = BANK_ACCOUNT_DEFAULT;
//		if (company != null) {
//			bankAccountNumber = company.getBankAccount();
//		}
//		bankAccount.setContent(StringUtil.isEmpty(bankAccountNumber) ? BANK_ACCOUNT_DEFAULT : bankAccountNumber);
//		bankAccount.keepStatusOnAction(true);

		addFormItems(section, personalID, name, address, postalCode, city, phone, fax, webPage, email);

		section.add(new CSSSpacer());

		heading = new Heading1(iwrb.getLocalizedString("application.contact_person_information_overview", "Enter contact person info"));
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
//		adminPersonalID.setID("userPersonalID");
		adminPersonalID.keepStatusOnAction(true);
		adminPersonalID.setOnKeyUp(new StringBuilder("CompanyApplicationCreator.getContactPersonInformation(event, '").append(adminPersonalID.getId())
										.append("', '").append(iwrb.getLocalizedString("user_not_found", "Sorry, unable to find user by provided ID"))
										.append("', '").append(iwrb.getLocalizedString("loading", "Loading..."))
										.append("');").toString());

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

		if (contactPerson != null) {
			adminName.setContent(contactPerson.getName());
		}

		addFormItem(section, iwrb.getLocalizedString("personal_id", "Personal ID"), adminPersonalID, PARAMETER_ADMIN_PERSONAL_ID, true);

		addFormItem(section, iwrb.getLocalizedString("name", "Name"), adminName);

		addFormItem(section, iwrb.getLocalizedString("work_phone", "Work phone"), workPhone, PARAMETER_WORK_PHONE, true);

		addFormItem(section, iwrb.getLocalizedString("mobile_phone", "Mobile phone"), mobilePhone, PARAMETER_MOBILE_PHONE, true);

		addFormItem(section, iwrb.getLocalizedString("email", "E-mail"), adminEmail, PARAMETER_ADMIN_EMAIL, true);
		
		section.add(new CSSSpacer());

		heading = new Heading1(iwrb.getLocalizedString("application.agreement_info", "Agreement information"));
		heading.setStyleClass("subHeader");
		form.add(heading);

		section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		CheckBox agree = new CheckBox(PARAMETER_AGREEMENT, Boolean.TRUE.toString());
		agree.setStyleClass("checkbox");
		agree.keepStatusOnAction(true);

		Paragraph paragraph = new Paragraph();
		paragraph.setStyleClass("agreement");
		paragraph.add(new Text(iwrb.getLocalizedString("application.agreement", "Agreement text")));
		section.add(paragraph);
		
		addFormItem(section, iwrb.getLocalizedString("application.agree_terms", "Yes, I agree"), agree, PARAMETER_AGREEMENT, "radioButtonItem", true);

		form.add(adminPK);
		form.add(new CSSSpacer());
		
		return form;
	}

	protected void save(IWContext iwc) throws RemoteException {
		boolean success = true;
		Company company = null;
		
		if (!iwc.isParameterSet(PARAMETER_AGREEMENT)) {
			setError(PARAMETER_AGREEMENT, iwrb.getLocalizedString("application_error.must_agree_terms", "You have to agree to the terms."));
		}
		if (!iwc.isParameterSet(PARAMETER_COMPANY_PERSONAL_ID)) {
			setError(PARAMETER_COMPANY_PERSONAL_ID, iwrb.getLocalizedString("application_error.must_enter_personal_id",
					"You have to enter a personal ID."));
		}
		else {
			try {
				company = getCompanyBusiness(iwc).getCompany(iwc.getParameter(PARAMETER_COMPANY_PERSONAL_ID));
			}
			catch (FinderException e) {
				setError(PARAMETER_COMPANY_PERSONAL_ID, iwrb.getLocalizedString("application_error.company_not_found",
						"The personal ID you have entered is not in the company register."));
			}
			
			CompanyApplication application = null;
			try {
				application = getCompanyApplicationBusiness().getCompanyApplicationHome().findByCompany(company);
			} catch (Exception e) {}
			if (application != null) {
				setError(PARAMETER_COMPANY_PERSONAL_ID, iwrb.getLocalizedString("application_error.company_already_applied",
						"The company has already applied for an account."));
			}
		}
		if (!iwc.isParameterSet(PARAMETER_PHONE)) {
			setError(PARAMETER_PHONE, iwrb.getLocalizedString("application_error.must_enter_phone", "You have to enter a phone number."));
		}
		if (!iwc.isParameterSet(PARAMETER_EMAIL)) {
			setError(PARAMETER_EMAIL, iwrb.getLocalizedString("application_error.must_enter_email", "You have to enter an e-mail address."));
		}
		else if (!EmailValidator.getInstance().validateEmail(iwc.getParameter(PARAMETER_EMAIL))) {
			setError(PARAMETER_EMAIL, iwrb.getLocalizedString("application_error.invalid_email", "You have entered an invalid e-mail address."));
		}
		/*if (!iwc.isParameterSet(PARAMETER_BANK_ACCOUNT) || iwc.getParameter(PARAMETER_BANK_ACCOUNT).equals(BANK_ACCOUNT_DEFAULT)) {
			setError(PARAMETER_BANK_ACCOUNT, iwrb.getLocalizedString("application_error.must_enter_bank_account", "You have to enter a bank account."));
		}
		else {
			String bankAccount = iwc.getParameter(PARAMETER_BANK_ACCOUNT);
			Pattern pat = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{6}$");
			Matcher matcher = pat.matcher(bankAccount);
			if (!matcher.find()) {
				setError(PARAMETER_BANK_ACCOUNT, iwrb.getLocalizedString("application_error.invalid_bank_account_number",
						"You have entered an invalid bank account number."));
			}
		}*/
		
		User user = null;
		if (iwc.isParameterSet(PARAMETER_ADMIN_PERSONAL_ID)) {
			try {
				user = getUserBusiness(iwc).getUser(iwc.getParameter(PARAMETER_ADMIN_PERSONAL_ID));
			} catch (Exception e) {}
			if (user == null) {
				log(Level.INFO, "User not found by provided ID: " + iwc.getParameter(PARAMETER_ADMIN_PERSONAL_ID));
				setError(PARAMETER_ADMIN_PERSONAL_ID, iwrb.getLocalizedString("application_error.invalid_user", "You have to select contact person."));
			}
		}
		else {
			setError(PARAMETER_ADMIN_PERSONAL_ID, iwrb.getLocalizedString("application_error.invalid_user", "You have to select contact person."));
		}
		if (!iwc.isParameterSet(PARAMETER_WORK_PHONE)) {
			setError(PARAMETER_WORK_PHONE, iwrb.getLocalizedString("application_error.must_enter_work_phone", "You have to enter work phone."));
		}
		if (!iwc.isParameterSet(PARAMETER_MOBILE_PHONE)) {
			setError(PARAMETER_MOBILE_PHONE, iwrb.getLocalizedString("application_error.must_enter_mobile_phone", "You have to enter mobile phone."));
		}
		if (!iwc.isParameterSet(PARAMETER_ADMIN_EMAIL)) {
			setError(PARAMETER_ADMIN_EMAIL, iwrb.getLocalizedString("application_error.must_enter_admin_email", "You have to enter an email address."));
		}
		else if (!EmailValidator.getInstance().validateEmail(iwc.getParameter(PARAMETER_ADMIN_EMAIL))) {
			setError(PARAMETER_ADMIN_EMAIL, iwrb.getLocalizedString("application_error.invalid_email", "You have entered an invalid e-mail address."));
		}
		if (hasErrors()) {
			showPhaseOne(iwc, ACTION_SAVE, iNumberOfPhases);
			return;
		}
		
		CompanyType companyType = null;
		if (iwc.isParameterSet(PARAMETER_TYPE)) {
			try {
				companyType = getCompanyBusiness(iwc).getCompanyType(iwc.getParameter(PARAMETER_TYPE));
			} catch (FinderException e) {
				e.printStackTrace();
			}
		}

//		String companyPersonalID = iwc.isParameterSet(PARAMETER_COMPANY_PERSONAL_ID) ? iwc.getParameter(PARAMETER_COMPANY_PERSONAL_ID) : null;
		String companyPhone = iwc.isParameterSet(PARAMETER_PHONE) ? iwc.getParameter(PARAMETER_PHONE) : null;
		String companyFax = iwc.isParameterSet(PARAMETER_FAX) ? iwc.getParameter(PARAMETER_FAX) : null;
		String companyWebPage = iwc.isParameterSet(PARAMETER_WEB_PAGE) ? iwc.getParameter(PARAMETER_WEB_PAGE) : null;
		String companyEmail = iwc.isParameterSet(PARAMETER_EMAIL) ? iwc.getParameter(PARAMETER_EMAIL) : null;
		//String companyBankAccount = iwc.isParameterSet(PARAMETER_BANK_ACCOUNT) ? iwc.getParameter(PARAMETER_BANK_ACCOUNT) : null;

//		String adminPersonalID = iwc.isParameterSet(PARAMETER_ADMIN_PERSONAL_ID) ? iwc.getParameter(PARAMETER_ADMIN_PERSONAL_ID) : null;
		String adminWorkPhone = iwc.isParameterSet(PARAMETER_WORK_PHONE) ? iwc.getParameter(PARAMETER_WORK_PHONE) : null;
		String adminMobilePhone = iwc.isParameterSet(PARAMETER_MOBILE_PHONE) ? iwc.getParameter(PARAMETER_MOBILE_PHONE) : null;
		String adminEmail = iwc.isParameterSet(PARAMETER_ADMIN_EMAIL) ? iwc.getParameter(PARAMETER_ADMIN_EMAIL) : null;

		UserBusiness userBusiness = getUserBusiness(iwc);
//		User admin = null;
//		if (!StringUtil.isEmpty(adminPersonalID)) {
//			try {
//				admin = userBusiness.getUser(adminPersonalID);
//			} catch (FinderException e) {
//				e.printStackTrace();
//			}
//		}
//		if (admin == null) {
//			success = false;
//		}
//		else {
			try {
				userBusiness.updateUserWorkPhone(user, adminWorkPhone);
				userBusiness.updateUserMobilePhone(user, adminMobilePhone);
				userBusiness.updateUserMail(user, adminEmail);
			} catch(Exception e) {
				e.printStackTrace();
			}
//		}
			
//		if (company != null && !StringUtil.isEmpty(companyPersonalID)) {
//			try {
//				company = getCompanyBusiness(iwc).getCompany(companyPersonalID);
//			} catch (FinderException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		if (company == null) {
//			success = false;
//		}
//		else if (success) {
//			company.getPhone();
			try {
				Phone phone = userBusiness.getPhoneHome().create();
				phone.setPhoneTypeId(PhoneTypeBMPBean.HOME_PHONE_ID);
				phone.setNumber(companyPhone);
				company.updatePhone(phone);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				Phone fax = userBusiness.getPhoneHome().create();
				fax.setPhoneTypeId(PhoneTypeBMPBean.FAX_NUMBER_ID);
				fax.setNumber(companyFax);
				company.updateFax(fax);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				Email email = userBusiness.getEmailHome().create();
				email.setEmailAddress(companyEmail);
				company.updateEmail(email);
			} catch(Exception e) {
				e.printStackTrace();
			}
				
			company.setWebPage(companyWebPage);
//			company.setBankAccount(companyBankAccount);
			company.store();
			
			User currentUser = null;
			try {
				currentUser = iwc.getCurrentUser();
			} catch (NotLoggedOnException e) {
				log(Level.INFO, "User is not logged, some settings for application might be not set!");
			}
			CompanyApplication application = null;
			try {
				application = getCompanyApplicationBusiness().storeApplication(iwc, user, companyType, company, currentUser);
				getCompanyApplicationBusiness().approveApplication(iwc, application);
			} catch (Exception e) {
				success = false;
				e.printStackTrace();
			}
			
			if (application == null) {
				success = false;
			}
//		}
		if (success) {
			addPhasesReceipt(iwc, iwrb.getLocalizedString("application.receipt", "Application receipt"),
					iwrb.getLocalizedString("application.receipt_subject", "Application sent"),
					iwrb.getLocalizedString("application.receipt_body", "Your application has been received."), ACTION_SAVE, iNumberOfPhases);
		}
		else {
			add(getPhasesHeader(iwrb.getLocalizedString("application.submit_failed", "Application submit failed"), ACTION_SAVE, iNumberOfPhases));
			add(getStopLayer(iwrb.getLocalizedString("application.submit_failed", "Application submit failed"),
					iwrb.getLocalizedString("application.submit_failed_info", "Application submit failed")));
		}

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		add(bottom);

		if (iwc.isLoggedOn()) {
			try {
				ICPage page = userBusiness.getHomePageForUser(iwc.getCurrentUser());
				Link link = getButtonLink(iwrb.getLocalizedString("my_page", "My page"));
				link.setStyleClass("homeButton");
				link.setPage(page);
				bottom.add(link);
			}
			catch (FinderException fe) {}
		}
		else {
			Link link = getButtonLink(iwrb.getLocalizedString("back", "Back"));
			link.setStyleClass("homeButton");
			link.setURL("/pages/");
			bottom.add(link);
		}
	}
	
	protected CompanyApplicationBusiness getCompanyApplicationBusiness() {
		try {
			return (CompanyApplicationBusiness) IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(),
					CompanyApplicationBusiness.class);
		} catch (IBOLookupException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected CompanyBusiness getCompanyBusiness(IWApplicationContext iwac) {
		try {
			return (CompanyBusiness) IBOLookup.getServiceInstance(iwac, CompanyBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
	
//	private void addRequiredFieldMark(Layer container) {
//		Span requiredText = new Span(new Text("*"));
//		requiredText.setStyleClass("requiredFieldUserApp");
//		requiredText.setToolTip(iwrb.getLocalizedString(requiredFieldLocalizationKey, requiredFieldLocalizationValue));
//		container.add(requiredText);
//	}	
	
	private int parseAction(IWContext iwc) {
		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_PHASE_1;
	}
	
	private Form createForm(int phase) {
		Form form = new Form();
		form.setID("companyApplication");
		form.add(new HiddenInput(PARAMETER_ACTION, String.valueOf(phase)));
		if (phase != ACTION_PHASE_1) {
			form.maintainParameter(PARAMETER_AGREEMENT);
			
			form.maintainParameter(PARAMETER_ADDRESS);
			//form.maintainParameter(PARAMETER_BANK_ACCOUNT);
			form.maintainParameter(PARAMETER_CITY);
			form.maintainParameter(PARAMETER_EMAIL);
			form.maintainParameter(PARAMETER_FAX);
			form.maintainParameter(PARAMETER_NAME);
			form.maintainParameter(PARAMETER_COMPANY_PERSONAL_ID);
			form.maintainParameter(PARAMETER_PHONE);
			form.maintainParameter(PARAMETER_POSTAL_CODE);
			form.maintainParameter(PARAMETER_WEB_PAGE);

			form.maintainParameter(PARAMETER_ADMIN_PK);
			form.maintainParameter(PARAMETER_ADMIN_PERSONAL_ID);
			form.maintainParameter(PARAMETER_ADMIN_NAME);
			form.maintainParameter(PARAMETER_WORK_PHONE);
			form.maintainParameter(PARAMETER_MOBILE_PHONE);
			form.maintainParameter(PARAMETER_ADMIN_EMAIL);
		}
		return form;
	}

	@Override
	protected String getCaseCode() {
		return EgovCompanyConstants.CASE_CODE_KEY;
	}
}
