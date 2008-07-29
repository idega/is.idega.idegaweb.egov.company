/*
 * $Id: CompanyApplication.java,v 1.1 2008/07/29 12:57:41 anton Exp $ Created on Jun 11, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.application.presentation.ApplicationForm;
import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.AdminUser;
import is.idega.idegaweb.egov.company.business.ApplicationSession;
import is.idega.idegaweb.egov.company.business.CompanyDWR;
import is.idega.idegaweb.egov.company.business.FSKBusiness;
import is.idega.idegaweb.egov.company.data.Application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
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
import com.idega.core.file.data.ICFile;
import com.idega.core.file.data.ICFileHome;
import com.idega.core.location.data.PostalCode;
import com.idega.data.IDOLookup;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.idegaweb.IWUserContext;
import com.idega.io.UploadFile;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Span;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.ListItem;
import com.idega.presentation.text.Lists;
import com.idega.presentation.text.Paragraph;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.FileInput;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.RadioButton;
import com.idega.presentation.ui.SelectionBox;
import com.idega.presentation.ui.TextArea;
import com.idega.presentation.ui.TextInput;
import com.idega.user.business.NoEmailFoundException;
import com.idega.user.business.NoPhoneFoundException;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.EmailValidator;
import com.idega.util.FileUtil;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.Name;
import com.idega.util.text.SocialSecurityNumber;

public class CompanyApplication extends ApplicationForm {

	private static final String PARAMETER_ACTION = "prm_action";

	private static final String PARAMETER_AGREEMENT = "prm_agreement";
	private static final String PARAMETER_TYPE = "prm_type";

	private static final String PARAMETER_PERSONAL_ID = "prm_personal_id";
	private static final String PARAMETER_NAME = "prm_name";
	private static final String PARAMETER_ADDRESS = "prm_address";
	private static final String PARAMETER_POSTAL_CODE = "prm_postal_code";
	private static final String PARAMETER_CITY = "prm_city";
	private static final String PARAMETER_PHONE = "prm_phone";
	private static final String PARAMETER_FAX = "prm_fax";
	private static final String PARAMETER_WEB_PAGE = "prm_web_page";
	private static final String PARAMETER_EMAIL = "prm_email";
	private static final String PARAMETER_BANK_ACCOUNT = "prm_bank_account";

	private static final String PARAMETER_ADMIN_PK = "prm_admin_pk";
	private static final String PARAMETER_ADMIN_PERSONAL_ID = "prm_admin_personal_id";
	private static final String PARAMETER_ADMIN_NAME = "prm_admin_name";
	private static final String PARAMETER_WORK_PHONE = "prm_work_phone";
	private static final String PARAMETER_MOBILE_PHONE = "prm_mobile_phone";
	private static final String PARAMETER_ADMIN_EMAIL = "prm_admin_email";

	private static final String PARAMETER_POSTAL_CODES = "prm_postal_codes";

	private static final String PARAMETER_FILE_TYPE = "prm_file_type";
	private static final String PARAMETER_COMMENTS = "prm_comments";

	private static final int ACTION_PHASE_1 = 1;
	private static final int ACTION_PHASE_2 = 2;
	private static final int ACTION_PHASE_3 = 3;
	private static final int ACTION_PHASE_4 = 4;
	private static final int ACTION_OVERVIEW = 5;
	private static final int ACTION_SAVE = 6;

	private static final String BANK_ACCOUNT_DEFAULT = "0000-00-000000";

	private IWResourceBundle iwrb;

	private static final int iNumberOfPhases = 6;

	public String getBundleIdentifier() {
		return FSKConstants.IW_BUNDLE_IDENTIFIER;
	}

	protected String getCaseCode() {
		return FSKConstants.CASE_CODE_KEY;
	}

	protected void present(IWContext iwc) {
		this.iwrb = getResourceBundle(iwc);

		try {
			switch (parseAction(iwc)) {
				case ACTION_PHASE_1:
					showPhaseOne(iwc);
					break;

				case ACTION_PHASE_2:
					showPhaseTwo(iwc);
					break;

				case ACTION_PHASE_3:
					showPhaseThree(iwc);
					break;

				case ACTION_PHASE_4:
					showPhaseFour(iwc);
					break;

				case ACTION_OVERVIEW:
					showOverview(iwc);
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

	private int parseAction(IWContext iwc) {
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
			form.maintainParameter(PARAMETER_TYPE);
			form.maintainParameter(PARAMETER_AGREEMENT);
		}
		if (phase != ACTION_PHASE_2) {
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
		if (phase != ACTION_PHASE_3) {
			form.maintainParameter(PARAMETER_POSTAL_CODES);
		}
		if (phase != ACTION_PHASE_4) {
			form.maintainParameter(PARAMETER_COMMENTS);
		}

		return form;
	}

	private void showPhaseOne(IWContext iwc) throws RemoteException {
		Form form = createForm(ACTION_PHASE_1);

		addErrors(iwc, form);

		form.add(getPhasesHeader(this.iwrb.getLocalizedString("application.type_information", "Type information"), ACTION_PHASE_1, iNumberOfPhases));

		Layer info = new Layer(Layer.DIV);
		info.setStyleClass("info");
		form.add(info);

		Heading1 heading = new Heading1(this.iwrb.getLocalizedString("application.choose_type", "Choose type"));
		heading.setStyleClass("subHeader");
		heading.setStyleClass("topSubHeader");
		form.add(heading);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(this.iwrb.getLocalizedString("application.type_help", "Select the company's type from the list to the left.")));
		section.add(helpLayer);

		Layer formItem;
		Label label;

		Collection types = getBusiness(iwc).getCompanyTypes();
		Iterator iterator = types.iterator();
		while (iterator.hasNext()) {
			CompanyType type = (CompanyType) iterator.next();

			RadioButton radio = new RadioButton(PARAMETER_TYPE, type.getPrimaryKey().toString());
			radio.setStyleClass("radiobutton");
			radio.keepStatusOnAction(true);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			formItem.setStyleClass("radioButtonItem");
			label = new Label(type.getLocalizedName(iwc, iwc.getCurrentLocale()), radio);
			formItem.add(radio);
			formItem.add(label);
			section.add(formItem);
		}

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		section.add(clearLayer);

		heading = new Heading1(this.iwrb.getLocalizedString("application.agreement_info", "Agreement information"));
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

		section.add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(this.iwrb.getLocalizedString("next", "Next"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_PHASE_2));
		next.setToFormSubmit(form);
		bottom.add(next);

		add(form);
	}

	private void showPhaseTwo(IWContext iwc) throws RemoteException {
		Form form = createForm(ACTION_PHASE_2);

		if (!iwc.isParameterSet(PARAMETER_TYPE)) {
			setError(PARAMETER_TYPE, this.iwrb.getLocalizedString("application_error.must_select_company_type", "You have to select a company type."));
		}
		if (!iwc.isParameterSet(PARAMETER_AGREEMENT)) {
			setError(PARAMETER_AGREEMENT, this.iwrb.getLocalizedString("application_error.must_agree_terms", "You have to agree to the terms."));
		}
		if (hasErrors()) {
			showPhaseOne(iwc);
			return;
		}

		super.getParentPage().addJavascriptURL("/dwr/interface/FSKDWRUtil.js");
		super.getParentPage().addJavascriptURL(CoreConstants.DWR_ENGINE_SCRIPT);
		super.getParentPage().addJavascriptURL("/dwr/util.js");
		super.getParentPage().addJavascriptURL(getBundle(iwc).getResourcesVirtualPath() + "/js/application.js");

		addErrors(iwc, form);

		form.add(getPhasesHeader(this.iwrb.getLocalizedString("application.company_information", "Company information"), ACTION_PHASE_2, iNumberOfPhases));

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
		section.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		section.add(clearLayer);

		heading = new Heading1(this.iwrb.getLocalizedString("application.enter_admin_info", "Enter admin info"));
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
		section.add(formItem);

		form.add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(this.iwrb.getLocalizedString("next", "Next"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_PHASE_3));
		next.setToFormSubmit(form);
		bottom.add(next);

		Link back = getButtonLink(this.iwrb.getLocalizedString("previous", "Previous"));
		back.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_PHASE_1));
		back.setToFormSubmit(form);
		bottom.add(back);

		add(form);
	}

	private void showPhaseThree(IWContext iwc) throws RemoteException {
		Form form = createForm(ACTION_PHASE_3);

		if (!iwc.isParameterSet(PARAMETER_PERSONAL_ID)) {
			setError(PARAMETER_PERSONAL_ID, this.iwrb.getLocalizedString("application_error.must_enter_personal_id", "You have to enter a personal ID."));
		}
		else if (!SocialSecurityNumber.isValidSocialSecurityNumber(iwc.getParameter(PARAMETER_PERSONAL_ID), iwc.getCurrentLocale())) {
			setError(PARAMETER_PERSONAL_ID, this.iwrb.getLocalizedString("application_error.invalid_company_personal_id", "The personal ID you have entered is not a valid company personal ID."));
		}
		else {
			try {
				Company company = getCompanyBusiness(iwc).getCompany(iwc.getParameter(PARAMETER_PERSONAL_ID));
				Application application = getBusiness(iwc).getApplication(company);
				if (application != null) {
					setError(PARAMETER_PERSONAL_ID, this.iwrb.getLocalizedString("application_error.company_already_applied", "The company has already applied for an account."));
				}
			}
			catch (FinderException e) {
				setError(PARAMETER_PERSONAL_ID, this.iwrb.getLocalizedString("application_error.company_not_found", "The personal ID you have entered is not in the company register."));
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
			showPhaseTwo(iwc);
			return;
		}
		else {
			String workPhone = iwc.isParameterSet(PARAMETER_WORK_PHONE) ? iwc.getParameter(PARAMETER_WORK_PHONE) : null;
			String mobilePhone = iwc.isParameterSet(PARAMETER_MOBILE_PHONE) ? iwc.getParameter(PARAMETER_MOBILE_PHONE) : null;
			String email = iwc.isParameterSet(PARAMETER_ADMIN_EMAIL) ? iwc.getParameter(PARAMETER_ADMIN_EMAIL) : null;

			getBusiness(iwc).storeUserInformation(iwc.getParameter(PARAMETER_ADMIN_PK), workPhone, mobilePhone, email);
		}

		addErrors(iwc, form);

		form.add(getPhasesHeader(this.iwrb.getLocalizedString("application.postal_information", "Postal information"), ACTION_PHASE_3, iNumberOfPhases));

		form.add(getCompanyInfo(iwc));

		Heading1 heading = new Heading1(this.iwrb.getLocalizedString("application.choose_postal_codes", "Choose postal_codes"));
		heading.setStyleClass("subHeader");
		heading.setStyleClass("topSubHeader");
		form.add(heading);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(this.iwrb.getLocalizedString("application.postal_help", "Please select all postal codes where the company operates.")));
		section.add(helpLayer);

		SelectionBox box = new SelectionBox(PARAMETER_POSTAL_CODES);
		box.setStyleClass("bigSelectionBox");
		box.keepStatusOnAction(true);

		Collection codes = getBusiness(iwc).getPostalCodes();
		Iterator iterator = codes.iterator();
		while (iterator.hasNext()) {
			PostalCode code = (PostalCode) iterator.next();
			box.addMenuElement(code.getPrimaryKey().toString(), code.getPostalAddress());
		}

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.setStyleClass("required");
		if (hasError(PARAMETER_POSTAL_CODES)) {
			formItem.setStyleClass("hasError");
		}
		Label label = new Label(new Span(new Text(iwrb.getLocalizedString("postal_codes", "Postal codes"))), box);
		formItem.add(label);
		formItem.add(box);
		section.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		section.add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(this.iwrb.getLocalizedString("next", "Next"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_PHASE_4));
		next.setToFormSubmit(form);
		bottom.add(next);

		Link back = getButtonLink(this.iwrb.getLocalizedString("previous", "Previous"));
		back.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_PHASE_2));
		back.setToFormSubmit(form);
		bottom.add(back);

		add(form);
	}

	private void showPhaseFour(IWContext iwc) throws RemoteException {
		Form form = createForm(ACTION_PHASE_4);
		form.setMultiPart();

		if (!iwc.isParameterSet(PARAMETER_POSTAL_CODES)) {
			setError(PARAMETER_POSTAL_CODES, this.iwrb.getLocalizedString("application_error.must_select_postal_code", "You have to select at least one postal code."));
		}
		if (hasErrors()) {
			showPhaseThree(iwc);
			return;
		}

		addErrors(iwc, form);

		form.add(getPhasesHeader(this.iwrb.getLocalizedString("application.attachment_information", "Attachment information"), ACTION_PHASE_4, iNumberOfPhases));

		form.add(getCompanyInfo(iwc));

		Heading1 heading = new Heading1(this.iwrb.getLocalizedString("application.choose_attachments", "Choose attachments"));
		heading.setStyleClass("subHeader");
		heading.setStyleClass("topSubHeader");
		form.add(heading);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(this.iwrb.getLocalizedString("application.attachment_help", "Please select all the attachments you want to send.")));
		section.add(helpLayer);

		FileInput fileInput = new FileInput();

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(iwrb.getLocalizedString("attachment", "Attachment"), fileInput);
		formItem.add(label);
		formItem.add(fileInput);
		section.add(formItem);

		Collection types = getBusiness(iwc).getFileTypes();
		Iterator iterator = types.iterator();
		while (iterator.hasNext()) {
			String type = (String) iterator.next();

			RadioButton radio = new RadioButton(PARAMETER_FILE_TYPE, type);
			radio.setStyleClass("radiobutton");

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			formItem.setStyleClass("radioButtonItem");
			label = new Label(iwrb.getLocalizedString("file_type." + type, type), radio);
			formItem.add(radio);
			formItem.add(label);
			section.add(formItem);
		}

		Layer editLayer = new Layer(Layer.DIV);
		editLayer.setStyleClass("editLayer");
		section.add(editLayer);

		Link editLink = new Link(new Span(new Text(this.iwrb.getLocalizedString("more_files", "More files"))));
		editLink.setToFormSubmit(form);
		editLayer.add(editLink);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		section.add(clearLayer);

		Map files = getSession(iwc).getFiles();
		if (!files.isEmpty()) {
			heading = new Heading1(this.iwrb.getLocalizedString("application.uploaded_attachments_info", "Uploaded attachments information"));
			heading.setStyleClass("subHeader");
			form.add(heading);

			section = new Layer(Layer.DIV);
			section.setStyleClass("formSection");
			form.add(section);

			iterator = files.keySet().iterator();
			while (iterator.hasNext()) {
				ICFile file = (ICFile) iterator.next();
				String fileType = (String) files.get(file);
				IWTimestamp created = new IWTimestamp(file.getCreationDate());

				Link link = new Link(file.getName());
				link.setFile(file);
				link.setTarget(Link.TARGET_BLANK_WINDOW);

				formItem = new Layer(Layer.DIV);
				formItem.setStyleClass("formItem");
				label = new Label();
				label.add(iwrb.getLocalizedString("file_type_info." + fileType, fileType));
				formItem.add(label);
				section.add(formItem);

				Span span = new Span();
				span.add(link);
				span.add(Text.getNonBrakingSpace());
				span.add("(" + created.getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT) + ")");
				formItem.add(span);
			}

			section.add(clearLayer);
		}

		heading = new Heading1(this.iwrb.getLocalizedString("application.comments_info", "Comments information"));
		heading.setStyleClass("subHeader");
		form.add(heading);

		section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(this.iwrb.getLocalizedString("application.comments_help", "Please put in other comments, if any.")));
		section.add(helpLayer);

		TextArea area = new TextArea(PARAMETER_COMMENTS);
		area.keepStatusOnAction(true);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(iwrb.getLocalizedString("comments", "Comments"), area);
		formItem.add(label);
		formItem.add(area);
		section.add(formItem);

		section.add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(this.iwrb.getLocalizedString("next", "Next"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_OVERVIEW));
		next.setToFormSubmit(form);
		bottom.add(next);

		Link back = getButtonLink(this.iwrb.getLocalizedString("previous", "Previous"));
		back.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_PHASE_3));
		back.setToFormSubmit(form);
		bottom.add(back);

		add(form);
	}

	private void showOverview(IWContext iwc) throws RemoteException {
		Form form = createForm(ACTION_OVERVIEW);
		form.add(getPhasesHeader(this.iwrb.getLocalizedString("application.overview_information", "Overview information"), ACTION_OVERVIEW, iNumberOfPhases));

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

		User user = getUserBusiness(iwc).getUser(new Integer(iwc.getParameter(PARAMETER_ADMIN_PK)));
		if (user != null) {
			heading = new Heading1(this.iwrb.getLocalizedString("application.admin_information", "Admin information"));
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

		heading = new Heading1(this.iwrb.getLocalizedString("application.postal_information_overview", "Postal information"));
		heading.setStyleClass("subHeader");
		form.add(heading);

		section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Lists list = new Lists();
		list.setStyleClass("postalCodes");
		section.add(list);

		Object[] postalCodePKs = iwc.getParameterValues(PARAMETER_POSTAL_CODES);
		if (postalCodePKs != null) {
			for (int i = 0; i < postalCodePKs.length; i++) {
				Object postalCodePK = postalCodePKs[i];
				PostalCode code = getBusiness(iwc).getPostalCode(postalCodePK);

				ListItem item = new ListItem();
				item.add(new Text(code.getPostalAddress()));
				list.add(item);
			}
		}

		section.add(clearLayer);

		Map files = getSession(iwc).getFiles();
		if (!files.isEmpty()) {
			heading = new Heading1(this.iwrb.getLocalizedString("application.uploaded_attachments_info", "Uploaded attachments information"));
			heading.setStyleClass("subHeader");
			form.add(heading);

			section = new Layer(Layer.DIV);
			section.setStyleClass("formSection");
			form.add(section);

			Iterator iterator = files.keySet().iterator();
			while (iterator.hasNext()) {
				ICFile file = (ICFile) iterator.next();
				String fileType = (String) files.get(file);

				Link link = new Link(file.getName());
				link.setFile(file);
				link.setTarget(Link.TARGET_BLANK_WINDOW);

				formItem = new Layer(Layer.DIV);
				formItem.setStyleClass("formItem");
				label = new Label();
				label.add(iwrb.getLocalizedString("file_type_info." + fileType, fileType));
				formItem.add(label);
				section.add(formItem);

				span = new Span();
				span.add(link);
				formItem.add(span);
			}

			section.add(clearLayer);
		}

		String comments = iwc.isParameterSet(PARAMETER_COMMENTS) ? iwc.getParameter(PARAMETER_COMMENTS) : null;
		if (comments != null) {
			heading = new Heading1(this.iwrb.getLocalizedString("application.comments_info", "Comments information"));
			heading.setStyleClass("subHeader");
			form.add(heading);

			section = new Layer(Layer.DIV);
			section.setStyleClass("formSection");
			form.add(section);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label();
			label.setLabel(iwrb.getLocalizedString("comments", "Comments"));
			formItem.add(label);
			span = new Span(new Text(comments));
			formItem.add(span);
			section.add(formItem);

			section.add(clearLayer);
		}

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(this.iwrb.getLocalizedString("send", "Send"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_SAVE));
		next.setToFormSubmit(form);
		bottom.add(next);

		Link back = getButtonLink(this.iwrb.getLocalizedString("previous", "Previous"));
		back.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_PHASE_4));
		back.setToFormSubmit(form);
		bottom.add(back);

		add(form);
	}

	private void save(IWContext iwc) throws RemoteException {
		CompanyType type = iwc.isParameterSet(PARAMETER_TYPE) ? getBusiness(iwc).getCompanyType(iwc.getParameter(PARAMETER_TYPE)) : null;
		String personalID = iwc.isParameterSet(PARAMETER_PERSONAL_ID) ? iwc.getParameter(PARAMETER_PERSONAL_ID) : null;
		//String name = iwc.isParameterSet(PARAMETER_NAME) ? iwc.getParameter(PARAMETER_NAME) : null;
		//String address = iwc.isParameterSet(PARAMETER_ADDRESS) ? iwc.getParameter(PARAMETER_ADDRESS) : null;
		//String postalCode = iwc.isParameterSet(PARAMETER_POSTAL_CODE) ? iwc.getParameter(PARAMETER_POSTAL_CODE) : null;
		//String city = iwc.isParameterSet(PARAMETER_CITY) ? iwc.getParameter(PARAMETER_CITY) : null;
		String phone = iwc.isParameterSet(PARAMETER_PHONE) ? iwc.getParameter(PARAMETER_PHONE) : null;
		String fax = iwc.isParameterSet(PARAMETER_FAX) ? iwc.getParameter(PARAMETER_FAX) : null;
		String webPage = iwc.isParameterSet(PARAMETER_WEB_PAGE) ? iwc.getParameter(PARAMETER_WEB_PAGE) : null;
		String email = iwc.isParameterSet(PARAMETER_EMAIL) ? iwc.getParameter(PARAMETER_EMAIL) : null;
		String bankAccount = iwc.isParameterSet(PARAMETER_BANK_ACCOUNT) ? iwc.getParameter(PARAMETER_BANK_ACCOUNT) : null;
		String comments = iwc.isParameterSet(PARAMETER_COMMENTS) ? iwc.getParameter(PARAMETER_COMMENTS) : null;
		Object[] postalCodePKs = iwc.getParameterValues(PARAMETER_POSTAL_CODES);
		Object userPK = iwc.getParameter(PARAMETER_ADMIN_PK);

		try {
			User user = getUserBusiness(iwc).getUser(new Integer(userPK.toString()));
			getBusiness(iwc).storeApplication(null, user, user, getSession(iwc).getFiles(), type, personalID, phone, fax, webPage, email, bankAccount, comments, postalCodePKs, iwc.isLoggedOn() ? iwc.getCurrentUser() : null);
			getSession(iwc).clear();

			addPhasesReceipt(iwc, this.iwrb.getLocalizedString("application.receipt", "Application receipt"), this.iwrb.getLocalizedString("application.receipt_subject", "Application sent"), this.iwrb.getLocalizedString("application.receipt_body", "Your application has been received."), ACTION_SAVE, iNumberOfPhases);
		}
		catch (CreateException e) {
			e.printStackTrace();

			add(getPhasesHeader(this.iwrb.getLocalizedString("application.submit_failed", "Application submit failed"), ACTION_SAVE, iNumberOfPhases));
			add(getStopLayer(this.iwrb.getLocalizedString("application.submit_failed", "Application submit failed"), this.iwrb.getLocalizedString("application.submit_failed_info", "Application submit failed")));
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

	protected FSKBusiness getBusiness(IWApplicationContext iwac) {
		try {
			return (FSKBusiness) IBOLookup.getServiceInstance(iwac, FSKBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private CompanyBusiness getCompanyBusiness(IWApplicationContext iwac) {
		try {
			return (CompanyBusiness) IBOLookup.getServiceInstance(iwac, CompanyBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected ApplicationSession getSession(IWUserContext iwuc) {
		try {
			return (ApplicationSession) IBOLookup.getSessionInstance(iwuc, ApplicationSession.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}