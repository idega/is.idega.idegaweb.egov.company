package is.idega.idegaweb.egov.company.presentation.company;

import is.idega.idegaweb.egov.company.IWBundleStarter;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;
import is.idega.idegaweb.egov.fsk.presentation.CompanyApplication;

import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.builder.data.ICPage;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.PhoneTypeBMPBean;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Link;
import com.idega.presentation.ui.Form;
import com.idega.user.data.User;
import com.idega.util.EmailValidator;
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

public class CompanyAdminApplication extends CompanyApplication {
	
	protected static final int iNumberOfPhases = 4;
	protected static final int ACTION_OVERVIEW = 3;
	protected static final int ACTION_SAVE = 4;

	public String getBundleIdentifier() {
		return IWBundleStarter.BUNDLE_IDENTIFIER;
	}
	
	protected void present(IWContext iwc) {
		this.iwrb = getResourceBundle(iwc);

		try {
			switch (parseAction(iwc)) {
				case ACTION_PHASE_1:
					showPhaseOne(iwc, ACTION_PHASE_2, iNumberOfPhases);
					break;

				case ACTION_PHASE_2:
					showPhaseTwo(iwc, ACTION_PHASE_1, ACTION_OVERVIEW, iNumberOfPhases);
					break;

				case ACTION_OVERVIEW:
					showOverview(iwc, ACTION_PHASE_2, ACTION_OVERVIEW, ACTION_SAVE, iNumberOfPhases);
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
	
	@Override
	protected void showOverview(IWContext iwc, int prevPhase, int currentPhase, int nextPhase, int iNumberOfPhases) throws RemoteException {
		Form form = createForm(ACTION_OVERVIEW);
		
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
				application = getCompanyApplicationBusiness(iwc).getCompanyApplicationHome().findByCompany(company);
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
			showPhaseTwo(iwc, ACTION_PHASE_1, ACTION_OVERVIEW, iNumberOfPhases);
			return;
		} else {
			super.showOverview(iwc, ACTION_PHASE_2, ACTION_OVERVIEW, ACTION_SAVE, iNumberOfPhases);
		}
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
				is.idega.idegaweb.egov.company.data.CompanyApplication application = getCompanyApplicationBusiness(iwc).getCompanyApplicationHome().create();
				
				application.setAdminUser(admin);
				application.setCompany(company);
				application.setType(companyType);
				
				//TODO maybe set data from Application interface
				
				application.store();
				
				
			} catch (CreateException e) {
				// TODO Auto-generated catch block
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
	
	protected CompanyApplicationBusiness getCompanyApplicationBusiness(IWApplicationContext iwac) {
		try {
			return (CompanyApplicationBusiness) IBOLookup.getServiceInstance(iwac, CompanyApplicationBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}
