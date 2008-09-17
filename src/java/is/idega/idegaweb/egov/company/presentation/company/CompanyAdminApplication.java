package is.idega.idegaweb.egov.company.presentation.company;

import is.idega.idegaweb.egov.application.presentation.ApplicationForm;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;
import is.idega.idegaweb.egov.fsk.FSKConstants;
import is.idega.idegaweb.egov.fsk.business.AdminUser;
import is.idega.idegaweb.egov.fsk.business.ApplicationSession;
import is.idega.idegaweb.egov.fsk.business.CompanyDWR;
import is.idega.idegaweb.egov.fsk.business.FSKBusiness;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.builder.data.ICPage;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.PhoneTypeBMPBean;
import com.idega.core.file.data.ICFile;
import com.idega.core.file.data.ICFileHome;
import com.idega.data.IDOLookup;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.idegaweb.IWUserContext;
import com.idega.io.UploadFile;
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
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.TextInput;
import com.idega.user.business.NoEmailFoundException;
import com.idega.user.business.NoPhoneFoundException;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.EmailValidator;
import com.idega.util.FileUtil;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.PresentationUtil;
import com.idega.util.text.Name;
import com.idega.util.text.SocialSecurityNumber;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Jul 30, 2008 by Author: Anton 
 *
 */

public class CompanyAdminApplication extends ApplicationForm {
	
	protected static final int iNumberOfPhases = 3;

	protected static final int ACTION_PHASE_1 = 1;
	protected static final int ACTION_OVERVIEW = 2;
	protected static final int ACTION_SAVE = 3;
	
	protected static final String PARAMETER_TYPE = "prm_type";
	
	protected static final String PARAMETER_FILE_TYPE = "prm_file_type";
	protected static final String PARAMETER_ACTION = "prm_action";
	protected static final String PARAMETER_AGREEMENT = "prm_agreement";
	
	protected static final String PARAMETER_PERSONAL_ID = "prm_personal_id";
	protected static final String PARAMETER_NAME = "prm_name";
	protected static final String PARAMETER_ADDRESS = "prm_address";
	protected static final String PARAMETER_POSTAL_CODE = "prm_postal_code";
	protected static final String PARAMETER_CITY = "prm_city";
	protected static final String PARAMETER_PHONE = "prm_phone";
	protected static final String PARAMETER_FAX = "prm_fax";
	protected static final String PARAMETER_WEB_PAGE = "prm_web_page";
	protected static final String PARAMETER_EMAIL = "prm_email";
	protected static final String PARAMETER_BANK_ACCOUNT = "prm_bank_account";

	protected static final String PARAMETER_ADMIN_PK = "prm_admin_pk";
	protected static final String PARAMETER_ADMIN_PERSONAL_ID = "prm_admin_personal_id";
	protected static final String PARAMETER_ADMIN_NAME = "prm_admin_name";
	protected static final String PARAMETER_WORK_PHONE = "prm_work_phone";
	protected static final String PARAMETER_MOBILE_PHONE = "prm_mobile_phone";
	protected static final String PARAMETER_ADMIN_EMAIL = "prm_admin_email";
	
	protected static final String BANK_ACCOUNT_DEFAULT = "0000-00-000000";
	
	private String requiredFieldLocalizationKey = "this_field_is_required";
	private String requiredFieldLocalizationValue = "This field is required!";
	
	protected IWResourceBundle iwrb;

	@Override
	public String getBundleIdentifier() {
		return EgovCompanyConstants.IW_BUNDLE_IDENTIFIER;
	}
	
	@Override
	protected void present(IWContext iwc) {
		this.iwrb = getResourceBundle(iwc);

		try {
			switch (parseAction(iwc)) {
				case ACTION_PHASE_1:
					showPhaseOne(iwc, ACTION_OVERVIEW, iNumberOfPhases);
					break;

				case ACTION_OVERVIEW:
					showOverview(iwc, ACTION_PHASE_1, ACTION_OVERVIEW, ACTION_SAVE, iNumberOfPhases);
					break;

				case ACTION_SAVE:
					save(iwc);
					break;
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}
	
	protected void showPhaseOne(IWContext iwc, int nextPhase, int iNumberOfPhases) throws RemoteException {
		Form form = createForm(ACTION_PHASE_1);
		addErrors(iwc, form);
		
		List<String> scripts = new ArrayList<String>();
		scripts.add(CoreConstants.DWR_ENGINE_SCRIPT);
		scripts.add(CoreConstants.DWR_UTIL_SCRIPT);
		scripts.add("/dwr/interface/FSKDWRUtil.js");
		scripts.add(iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getVirtualPathWithFileNameString("js/application.js"));
		PresentationUtil.addJavaScriptSourcesLinesToHeader(iwc, scripts);

		form.add(getPhasesHeader(this.iwrb.getLocalizedString("application.company_information", "Company information"), ACTION_PHASE_1, iNumberOfPhases));

		Layer info = new Layer(Layer.DIV);
		info.setStyleClass("info");
		form.add(info);

		Heading1 heading = new Heading1(this.iwrb.getLocalizedString("application.enter_company_info", "Enter company info"));
		heading.setStyleClass("subHeader");
		heading.setStyleClass("topSubHeader");
		form.add(heading);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(this.iwrb.getLocalizedString("application.company_help", "Please fill out all the inputs on the left.")));
		section.add(helpLayer);

		CompanyDWR dwr = null;
		if (iwc.isParameterSet(PARAMETER_PERSONAL_ID)) {
			dwr = getBusiness(iwc).getCompanyInfo(iwc.getParameter(PARAMETER_PERSONAL_ID), iwc.getParameter(PARAMETER_PHONE), iwc.getParameter(PARAMETER_FAX), iwc.getParameter(PARAMETER_EMAIL), iwc.getParameter(PARAMETER_WEB_PAGE), iwc.getParameter(PARAMETER_BANK_ACCOUNT), iwc.getLocale().getCountry());
		}

		TextInput personalID = new TextInput(PARAMETER_PERSONAL_ID);
		personalID.setID("companyPersonalID");
		personalID.setOnKeyUp("readCompany();");
		personalID.keepStatusOnAction(true);

		TextInput name = new TextInput(PARAMETER_NAME);
		name.setID("companyName");
		//name.keepStatusOnAction(true);
		name.setDisabled(true);
		if (dwr != null && dwr.getCompanyName() != null) {
			name.setContent(dwr.getCompanyName());
		}

		TextInput address = new TextInput(PARAMETER_ADDRESS);
		address.setID("companyAddress");
		//address.keepStatusOnAction(true);
		address.setDisabled(true);
		if (dwr != null && dwr.getCompanyAddress() != null) {
			address.setContent(dwr.getCompanyAddress());
		}

		TextInput postalCode = new TextInput(PARAMETER_POSTAL_CODE);
		postalCode.setID("companyPostalCode");
		//postalCode.keepStatusOnAction(true);
		postalCode.setDisabled(true);
		if (dwr != null && dwr.getCompanyPostalCode() != null) {
			postalCode.setContent(dwr.getCompanyPostalCode());
		}

		TextInput city = new TextInput(PARAMETER_CITY);
		city.setID("companyCity");
		//city.keepStatusOnAction(true);
		city.setDisabled(true);
		if (dwr != null && dwr.getCompanyCity() != null) {
			city.setContent(dwr.getCompanyCity());
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

		TextInput bankAccount = new TextInput(PARAMETER_BANK_ACCOUNT);
		bankAccount.setID("companyBankAccount");
		bankAccount.setContent(BANK_ACCOUNT_DEFAULT);
		bankAccount.keepStatusOnAction(true);

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.setStyleClass("required");
		if (hasError(PARAMETER_PERSONAL_ID)) {
			formItem.setStyleClass("hasError");
		}
		Label label = new Label(new Span(new Text(iwrb.getLocalizedString("personal_id", "Personal ID"))), personalID);
		formItem.add(label);
		formItem.add(personalID);
		addRequiredFieldMark(formItem);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(new Span(new Text(iwrb.getLocalizedString("name", "Name"))), name);
		formItem.add(label);
		formItem.add(name);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(new Span(new Text(iwrb.getLocalizedString("address", "Address"))), address);
		formItem.add(label);
		formItem.add(address);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(new Span(new Text(iwrb.getLocalizedString("postal_code", "Postal code"))), postalCode);
		formItem.add(label);
		formItem.add(postalCode);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(new Span(new Text(iwrb.getLocalizedString("city", "City"))), city);
		formItem.add(label);
		formItem.add(city);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.setStyleClass("required");
		if (hasError(PARAMETER_PHONE)) {
			formItem.setStyleClass("hasError");
		}
		label = new Label(new Span(new Text(iwrb.getLocalizedString("phone", "Phone"))), phone);
		formItem.add(label);
		formItem.add(phone);
		addRequiredFieldMark(formItem);
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
		formItem.setStyleClass("required");
		if (hasError(PARAMETER_EMAIL)) {
			formItem.setStyleClass("hasError");
		}
		label = new Label(new Span(new Text(iwrb.getLocalizedString("email", "E-mail"))), email);
		formItem.add(label);
		formItem.add(email);
		addRequiredFieldMark(formItem);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.setStyleClass("required");
		if (hasError(PARAMETER_BANK_ACCOUNT)) {
			formItem.setStyleClass("hasError");
		}
		label = new Label(new Span(new Text(iwrb.getLocalizedString("bank_account", "Bank account"))), bankAccount);
		formItem.add(label);
		formItem.add(bankAccount);
		addRequiredFieldMark(formItem);
		section.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		section.add(clearLayer);

		heading = new Heading1(this.iwrb.getLocalizedString("application.contact_person_information_overview", "Enter contact person info"));
		heading.setStyleClass("subHeader");
		form.add(heading);

		section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(this.iwrb.getLocalizedString("application.admin_user_help", "Please fill out all the inputs on the left.")));
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

		if (iwc.isParameterSet(PARAMETER_ADMIN_PERSONAL_ID)) {
			AdminUser user = getBusiness(iwc).getUser(iwc.getParameter(PARAMETER_ADMIN_PERSONAL_ID), iwc.getCurrentLocale().getCountry());
			adminName.setContent(user.getUserName());
		}

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.setStyleClass("required");
		if (hasError(PARAMETER_ADMIN_PERSONAL_ID)) {
			formItem.setStyleClass("hasError");
		}
		label = new Label(new Span(new Text(iwrb.getLocalizedString("personal_id", "Personal ID"))), adminPersonalID);
		formItem.add(label);
		formItem.add(adminPersonalID);
		formItem.add(adminPK);
		addRequiredFieldMark(formItem);
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
		if (hasError(PARAMETER_WORK_PHONE)) {
			formItem.setStyleClass("hasError");
		}
		label = new Label(new Span(new Text(iwrb.getLocalizedString("work_phone", "Work phone"))), workPhone);
		formItem.add(label);
		formItem.add(workPhone);
		addRequiredFieldMark(formItem);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.setStyleClass("required");
		if (hasError(PARAMETER_MOBILE_PHONE)) {
			formItem.setStyleClass("hasError");
		}
		label = new Label(new Span(new Text(iwrb.getLocalizedString("mobile_phone", "Mobile phone"))), mobilePhone);
		formItem.add(label);
		formItem.add(mobilePhone);
		addRequiredFieldMark(formItem);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.setStyleClass("required");
		if (hasError(PARAMETER_PHONE)) {
			formItem.setStyleClass("hasError");
		}
		label = new Label(new Span(new Text(iwrb.getLocalizedString("email", "E-mail"))), adminEmail);
		formItem.add(label);
		formItem.add(adminEmail);
		addRequiredFieldMark(formItem);
		section.add(formItem);
		
		section.add(clearLayer);

		heading = new Heading1(this.iwrb.getLocalizedString("application.agreement_info", "Agreement information"));
		heading.setStyleClass("subHeader");
		form.add(heading);

		section = new Layer(Layer.DIV);
		addRequiredFieldMark(section);
		section.setStyleClass("formSection");
		form.add(section);

		CheckBox agree = new CheckBox(PARAMETER_AGREEMENT, Boolean.TRUE.toString());
		agree.setStyleClass("checkbox");
		agree.keepStatusOnAction(true);

		Paragraph paragraph = new Paragraph();
		paragraph.setStyleClass("agreement");
		paragraph.add(new Text(this.iwrb.getLocalizedString("application.agreement", "Agreement text")));
		section.add(paragraph);
		
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.setStyleClass("radioButtonItem");
		formItem.setStyleClass("required");
		if (hasError(PARAMETER_AGREEMENT)) {
			formItem.setStyleClass("hasError");
		}
		label = new Label(new Span(new Text(this.iwrb.getLocalizedString("application.agree_terms", "Yes, I agree"))), agree);
		formItem.add(agree);
		formItem.add(label);
		section.add(formItem);

		form.add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(this.iwrb.getLocalizedString("next", "Next"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(nextPhase));
		next.setToFormSubmit(form);
		bottom.add(next);

		add(form);
	}
	
	protected void showOverview(IWContext iwc, int prevPhase, int currentPhase, int nextPhase, int iNumberOfPhases) throws RemoteException {
		if (!iwc.isParameterSet(PARAMETER_AGREEMENT)) {
			setError(PARAMETER_AGREEMENT, this.iwrb.getLocalizedString("application_error.must_agree_terms", "You have to agree to the terms."));
		}
		if (!iwc.isParameterSet(PARAMETER_PERSONAL_ID)) {
			setError(PARAMETER_PERSONAL_ID, this.iwrb.getLocalizedString("application_error.must_enter_personal_id", "You have to enter a personal ID."));
		}
		else if (!SocialSecurityNumber.isValidSocialSecurityNumber(iwc.getParameter(PARAMETER_PERSONAL_ID), iwc.getCurrentLocale())) {
			setError(PARAMETER_PERSONAL_ID, this.iwrb.getLocalizedString("application_error.invalid_company_personal_id", "The personal ID you have entered is not a valid company personal ID."));
		}
		else {
			Company company = null;
			try {
				company = getCompanyBusiness(iwc).getCompany(iwc.getParameter(PARAMETER_PERSONAL_ID));
			}
			catch (FinderException e) {
				setError(PARAMETER_PERSONAL_ID, this.iwrb.getLocalizedString("application_error.company_not_found", "The personal ID you have entered is not in the company register."));
			}
			
			is.idega.idegaweb.egov.company.data.CompanyApplication application = null;
			
			try {
				application = getCompanyApplicationBusiness().getCompanyApplicationHome().findByCompany(company);
			} catch (FinderException e) {
				e.printStackTrace();
			}
			if (application != null) {
				setError(PARAMETER_PERSONAL_ID, this.iwrb.getLocalizedString("application_error.company_already_applied", "The company has already applied for an account."));
			}
		}
		if (!iwc.isParameterSet(PARAMETER_PHONE)) {
			setError(PARAMETER_PHONE, this.iwrb.getLocalizedString("application_error.must_enter_phone", "You have to enter a phone number."));
		}
		if (!iwc.isParameterSet(PARAMETER_EMAIL)) {
			setError(PARAMETER_EMAIL, this.iwrb.getLocalizedString("application_error.must_enter_email", "You have to enter an e-mail address."));
		}
		else if (!EmailValidator.getInstance().validateEmail(iwc.getParameter(PARAMETER_EMAIL))) {
			setError(PARAMETER_EMAIL, this.iwrb.getLocalizedString("application_error.invalid_email", "You have entered an invalid e-mail address."));
		}
		if (!iwc.isParameterSet(PARAMETER_BANK_ACCOUNT) || iwc.getParameter(PARAMETER_BANK_ACCOUNT).equals(BANK_ACCOUNT_DEFAULT)) {
			setError(PARAMETER_BANK_ACCOUNT, this.iwrb.getLocalizedString("application_error.must_enter_bank_account", "You have to enter a bank account."));
		}
		else {
			String bankAccount = iwc.getParameter(PARAMETER_BANK_ACCOUNT);
			Pattern pat = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{6}$");
			Matcher matcher = pat.matcher(bankAccount);
			if (!matcher.find()) {
				setError(PARAMETER_BANK_ACCOUNT, this.iwrb.getLocalizedString("application_error.invalid_bank_account_number", "You have entered an invalid bank account number."));
			}
		}

		if (!iwc.isParameterSet(PARAMETER_ADMIN_PK)) {
			setError(PARAMETER_ADMIN_PERSONAL_ID, this.iwrb.getLocalizedString("application_error.invalid_user", "You have to select an admin user."));
		}
		if (!iwc.isParameterSet(PARAMETER_WORK_PHONE)) {
			setError(PARAMETER_WORK_PHONE, this.iwrb.getLocalizedString("application_error.must_enter_work_phone", "You have to enter work phone."));
		}
		if (!iwc.isParameterSet(PARAMETER_MOBILE_PHONE)) {
			setError(PARAMETER_MOBILE_PHONE, this.iwrb.getLocalizedString("application_error.must_enter_mobile_phone", "You have to enter mobile phone."));
		}
		if (!iwc.isParameterSet(PARAMETER_ADMIN_EMAIL)) {
			setError(PARAMETER_ADMIN_EMAIL, this.iwrb.getLocalizedString("application_error.must_enter_admin_email", "You have to enter an email address."));
		}
		else if (!EmailValidator.getInstance().validateEmail(iwc.getParameter(PARAMETER_ADMIN_EMAIL))) {
			setError(PARAMETER_ADMIN_EMAIL, this.iwrb.getLocalizedString("application_error.invalid_email", "You have entered an invalid e-mail address."));
		}

		if (hasErrors()) {
			showPhaseOne(iwc, ACTION_OVERVIEW, iNumberOfPhases);
			return;
		}
		
		Form form = createForm(currentPhase);
		form.add(getPhasesHeader(this.iwrb.getLocalizedString("application.overview_information", "Overview information"), currentPhase, iNumberOfPhases));

		form.add(getCompanyInfo(iwc));

		CompanyType type = null;
		if (iwc.isParameterSet(PARAMETER_TYPE)) {
			type = getBusiness(iwc).getCompanyType(iwc.getParameter(PARAMETER_TYPE));
		}

		Heading1 heading = new Heading1(this.iwrb.getLocalizedString("application.type_information_overview", "Type information"));
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

		heading = new Heading1(this.iwrb.getLocalizedString("application.company_information_overview", "Company information"));
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
		span = new Span(new Text(iwc.isParameterSet(PARAMETER_PERSONAL_ID) ? iwc.getParameter(PARAMETER_PERSONAL_ID) : Text.NON_BREAKING_SPACE));
		formItem.add(span);
		section.add(formItem);

		CompanyDWR company = null;
		String personalID = iwc.getParameter(PARAMETER_PERSONAL_ID);
		try {
			company = this.getBusiness(iwc).getCompanyInfo(personalID, iwc.getParameter(PARAMETER_PHONE), iwc.getParameter(PARAMETER_FAX), iwc.getParameter(PARAMETER_EMAIL), iwc.getParameter(PARAMETER_WEB_PAGE), iwc.getParameter(PARAMETER_BANK_ACCOUNT), "IS");
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("name", "Name"));
		formItem.add(label);
		if (company != null) {
			span = new Span(new Text(company.getCompanyName()));
		}
		else {
			span = new Span(new Text(Text.NON_BREAKING_SPACE));
		}
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("address", "Address"));
		formItem.add(label);
		if (company != null) {
			span = new Span(new Text(company.getCompanyAddress()));
		}
		else {
			span = new Span(new Text(Text.NON_BREAKING_SPACE));
		}
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("postal_code", "Postal code"));
		formItem.add(label);
		if (company != null) {
			span = new Span(new Text(company.getCompanyPostalCode()));
		}
		else {
			span = new Span(new Text(Text.NON_BREAKING_SPACE));
		}
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("city", "City"));
		formItem.add(label);
		if (company != null) {
			span = new Span(new Text(company.getCompanyCity()));
		}
		else {
			span = new Span(new Text(Text.NON_BREAKING_SPACE));
		}
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("phone", "Phone"));
		formItem.add(label);
		span = new Span(new Text(iwc.isParameterSet(PARAMETER_PHONE) ? iwc.getParameter(PARAMETER_PHONE) : Text.NON_BREAKING_SPACE));
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("fax", "Fax"));
		formItem.add(label);
		span = new Span(new Text(iwc.isParameterSet(PARAMETER_FAX) ? iwc.getParameter(PARAMETER_FAX) : Text.NON_BREAKING_SPACE));
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("web_page", "Web page"));
		formItem.add(label);
		span = new Span(new Text(iwc.isParameterSet(PARAMETER_WEB_PAGE) ? iwc.getParameter(PARAMETER_WEB_PAGE) : Text.NON_BREAKING_SPACE));
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("email", "E-mail"));
		formItem.add(label);
		span = new Span(new Text(iwc.isParameterSet(PARAMETER_EMAIL) ? iwc.getParameter(PARAMETER_EMAIL) : Text.NON_BREAKING_SPACE));
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("bank_account", "Bank account"));
		formItem.add(label);
		span = new Span(new Text(iwc.isParameterSet(PARAMETER_BANK_ACCOUNT) ? iwc.getParameter(PARAMETER_BANK_ACCOUNT) : Text.NON_BREAKING_SPACE));
		formItem.add(span);
		section.add(formItem);

		section.add(clearLayer);

		User user = null;
		try {
			user = getUserBusiness(iwc).getUser(iwc.getParameter(PARAMETER_ADMIN_PERSONAL_ID));
		} catch (FinderException e1) {
			e1.printStackTrace();
		}
		
		if (user != null) {
			heading = new Heading1(this.iwrb.getLocalizedString("application.contact_person_information", "Contact person information"));
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

		section.add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(this.iwrb.getLocalizedString("send", "Send"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(nextPhase));
		next.setToFormSubmit(form);
		bottom.add(next);

		Link back = getButtonLink(this.iwrb.getLocalizedString("previous", "Previous"));
		back.setValueOnClick(PARAMETER_ACTION, String.valueOf(prevPhase));
		back.setToFormSubmit(form);
		bottom.add(back);

		add(form);
	}

	protected void save(IWContext iwc) throws RemoteException {
		CompanyType companyType = iwc.isParameterSet(PARAMETER_TYPE) ? getBusiness(iwc).getCompanyType(iwc.getParameter(PARAMETER_TYPE)) : null;

		String companyPersonalID = iwc.isParameterSet(PARAMETER_PERSONAL_ID) ? iwc.getParameter(PARAMETER_PERSONAL_ID) : null;
		String companyPhone = iwc.isParameterSet(PARAMETER_PHONE) ? iwc.getParameter(PARAMETER_PHONE) : null;
		String companyFax = iwc.isParameterSet(PARAMETER_FAX) ? iwc.getParameter(PARAMETER_FAX) : null;
		String companyWebPage = iwc.isParameterSet(PARAMETER_WEB_PAGE) ? iwc.getParameter(PARAMETER_WEB_PAGE) : null;
		String companyEmail = iwc.isParameterSet(PARAMETER_EMAIL) ? iwc.getParameter(PARAMETER_EMAIL) : null;
		String companyBankAccount = iwc.isParameterSet(PARAMETER_BANK_ACCOUNT) ? iwc.getParameter(PARAMETER_BANK_ACCOUNT) : null;

		String adminPersonalID = iwc.isParameterSet(PARAMETER_ADMIN_PERSONAL_ID) ? iwc.getParameter(PARAMETER_ADMIN_PERSONAL_ID) : null;
		String adminWorkPhone = iwc.isParameterSet(PARAMETER_WORK_PHONE) ? iwc.getParameter(PARAMETER_WORK_PHONE) : null;
		String adminMobilePhone = iwc.isParameterSet(PARAMETER_MOBILE_PHONE) ? iwc.getParameter(PARAMETER_MOBILE_PHONE) : null;
		String adminEmail = iwc.isParameterSet(PARAMETER_ADMIN_EMAIL) ? iwc.getParameter(PARAMETER_ADMIN_EMAIL) : null;

		try {
			User admin = getUserBusiness(iwc).getUser(adminPersonalID);
			getUserBusiness(iwc).updateUserWorkPhone(admin, adminWorkPhone);
			getUserBusiness(iwc).updateUserMobilePhone(admin, adminMobilePhone);
			try {
				getUserBusiness(iwc).updateUserMail(admin, adminEmail);
			} catch (CreateException e) {
				e.printStackTrace();
			}
			
			Company company = getBusiness(iwc).getCompanyBusiness().getCompany(companyPersonalID);
			
			try {
				Phone phone = getUserBusiness(iwc).getPhoneHome().create();
				phone.setPhoneTypeId(PhoneTypeBMPBean.HOME_PHONE_ID);
				phone.setNumber(companyPhone);
				company.updatePhone(phone);
			} catch (CreateException e) {
				e.printStackTrace();
			}
		
			try {
				Phone fax = getUserBusiness(iwc).getPhoneHome().create();
				fax.setPhoneTypeId(PhoneTypeBMPBean.FAX_NUMBER_ID);
				fax.setNumber(companyFax);
				company.updateFax(fax);
			} catch (CreateException e) {
				e.printStackTrace();
			}
		
			try {
				Email email = getUserBusiness(iwc).getEmailHome().create();
				email.setEmailAddress(companyEmail);
				company.updateEmail(email);
			} catch (CreateException e) {
				e.printStackTrace();
			}
			
			company.setWebPage(companyWebPage);
			company.setBankAccount(companyBankAccount);
			company.store();
			
			try {
				getCompanyApplicationBusiness().storeApplication(admin, companyType, company, iwc.getCurrentUser());
			} catch (CreateException e) {
				e.printStackTrace();
			}
			
			getSession(iwc).clear();

			addPhasesReceipt(iwc, this.iwrb.getLocalizedString("application.receipt", "Application receipt"), this.iwrb.getLocalizedString("application.receipt_subject", "Application sent"), this.iwrb.getLocalizedString("application.receipt_body", "Your application has been received."), ACTION_SAVE, iNumberOfPhases);

			add(getPhasesHeader(this.iwrb.getLocalizedString("application.submit_failed", "Application submit failed"), ACTION_SAVE, iNumberOfPhases));
			add(getStopLayer(this.iwrb.getLocalizedString("application.submit_failed", "Application submit failed"), this.iwrb.getLocalizedString("application.submit_failed_info", "Application submit failed")));
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		add(bottom);

		if (iwc.isLoggedOn()) {
			try {
				ICPage page = getUserBusiness(iwc).getHomePageForUser(iwc.getCurrentUser());
				Link link = getButtonLink(this.iwrb.getLocalizedString("my_page", "My page"));
				link.setStyleClass("homeButton");
				link.setPage(page);
				bottom.add(link);
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
		}
		else {
			Link link = getButtonLink(this.iwrb.getLocalizedString("back", "Back"));
			link.setStyleClass("homeButton");
			link.setURL("/pages/");
			bottom.add(link);
		}
	}
	
	protected Layer getCompanyInfo(IWContext iwc) {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("info");

		String name = "";
		String address = "";
		String postal = "";

		String personalID = iwc.getParameter(PARAMETER_PERSONAL_ID);
		try {
			CompanyDWR company = this.getBusiness(iwc).getCompanyInfo(personalID, iwc.getParameter(PARAMETER_PHONE), iwc.getParameter(PARAMETER_FAX), iwc.getParameter(PARAMETER_EMAIL), iwc.getParameter(PARAMETER_WEB_PAGE), iwc.getParameter(PARAMETER_BANK_ACCOUNT), "IS");
			name = company.getCompanyName();
			address = company.getCompanyAddress();
			postal = company.getCompanyPostalCode() + " " + company.getCompanyCity();
		}
		catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Layer personInfo = new Layer(Layer.DIV);
		personInfo.setStyleClass("personInfo");
		personInfo.setID("name");
		personInfo.add(new Text(name));
		layer.add(personInfo);

		personInfo = new Layer(Layer.DIV);
		personInfo.setStyleClass("personInfo");
		personInfo.setID("personalID");
		personInfo.add(new Text(PersonalIDFormatter.format(personalID, iwc.getCurrentLocale())));
		layer.add(personInfo);

		personInfo = new Layer(Layer.DIV);
		personInfo.setStyleClass("personInfo");
		personInfo.setID("address");
		personInfo.add(new Text(address));
		layer.add(personInfo);

		personInfo = new Layer(Layer.DIV);
		personInfo.setStyleClass("personInfo");
		personInfo.setID("postal");
		personInfo.add(new Text(postal));
		layer.add(personInfo);

		return layer;
	}
	
	protected CompanyApplicationBusiness getCompanyApplicationBusiness() {
		try {
			return (CompanyApplicationBusiness) IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CompanyApplicationBusiness.class);
		} catch (IBOLookupException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected ApplicationSession getSession(IWUserContext iwuc) {
		try {
			return (ApplicationSession) IBOLookup.getSessionInstance(iwuc, ApplicationSession.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
	
	protected FSKBusiness getBusiness(IWApplicationContext iwac) {
		try {
			return (FSKBusiness) IBOLookup.getServiceInstance(iwac, FSKBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
	
	protected CompanyBusiness getCompanyBusiness(IWApplicationContext iwac) {
		try {
			return (CompanyBusiness) IBOLookup.getServiceInstance(iwac, CompanyBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
	
	private void addRequiredFieldMark(Layer container) {
		Span requiredText = new Span(new Text("*"));
		requiredText.setStyleClass("requiredFieldUserApp");
		requiredText.setToolTip(iwrb.getLocalizedString(requiredFieldLocalizationKey, requiredFieldLocalizationValue));
		container.add(requiredText);
	}	
	
	protected int parseAction(IWContext iwc) {
		UploadFile uploadFile = iwc.getUploadedFile();
		if (uploadFile != null && uploadFile.getName() != null && uploadFile.getName().length() > 0) {
			try {
				String fileType = iwc.getParameter(PARAMETER_FILE_TYPE);
				if (fileType != null) {
					FileInputStream input = new FileInputStream(uploadFile.getRealPath());

					ICFile attachment = ((ICFileHome) IDOLookup.getHome(ICFile.class)).create();
					attachment.setName(uploadFile.getName());
					attachment.setMimeType(uploadFile.getMimeType());
					attachment.setFileValue(input);
					attachment.setFileSize((int) uploadFile.getSize());
					attachment.store();

					getSession(iwc).addFile(attachment, fileType);

					uploadFile.setId(((Integer) attachment.getPrimaryKey()).intValue());
				}
				else {
					setError(PARAMETER_FILE_TYPE, iwrb.getLocalizedString("application_error.must_select_file_type", "You have to select a file type"));
				}

				try {
					FileUtil.delete(uploadFile);
				}
				catch (Exception ex) {
					System.err.println("MediaBusiness: deleting the temporary file at " + uploadFile.getRealPath() + " failed.");
				}
			}
			catch (RemoteException e) {
				e.printStackTrace(System.err);
				uploadFile.setId(-1);
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (CreateException ce) {
				ce.printStackTrace();
			}
		}

		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_PHASE_1;
	}
	
	protected Form createForm(int phase) {
		Form form = new Form();
		form.setID("companyApplication");
		form.add(new HiddenInput(PARAMETER_ACTION, String.valueOf(phase)));
		if (phase != ACTION_PHASE_1) {
			form.maintainParameter(PARAMETER_AGREEMENT);
			
			form.maintainParameter(PARAMETER_ADDRESS);
			form.maintainParameter(PARAMETER_BANK_ACCOUNT);
			form.maintainParameter(PARAMETER_CITY);
			form.maintainParameter(PARAMETER_EMAIL);
			form.maintainParameter(PARAMETER_FAX);
			form.maintainParameter(PARAMETER_NAME);
			form.maintainParameter(PARAMETER_PERSONAL_ID);
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
