package is.idega.idegaweb.egov.company.presentation.employee;

import is.idega.idegaweb.egov.citizen.business.WSCitizenAccountBusiness;
import is.idega.idegaweb.egov.citizen.presentation.CitizenAccountApplication;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import com.idega.business.IBOLookup;
import com.idega.core.accesscontrol.business.AccessController;
import com.idega.core.accesscontrol.business.LoginCreateException;
import com.idega.core.accesscontrol.data.ICRole;
import com.idega.core.accesscontrol.data.LoginTable;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Label;
import com.idega.user.data.User;
import com.idega.util.text.SocialSecurityNumber;

public class CompanyEmployeeRegistrator extends CitizenAccountApplication {

	private static final String ROLES_DROP_DOWN_KEY = "roles_drop_down_key";


	@Override
	public void present(IWContext iwc) {
		super.present(iwc);

	}

	@Override
	protected void renderComponentsBeforeTermOfUseAggreement(Layer layer) throws RemoteException {

		// Groups DropDown Menu

		Layer formItem;
		Label label;
		IWContext iwc = IWContext.getCurrentInstance();

		AccessController accessController = iwc.getIWMainApplication().getAccessController();

		DropdownMenu rolesDropdownMenu = new DropdownMenu(ROLES_DROP_DOWN_KEY);
		Collection<ICRole> allRoles = accessController.getAllRolesLegacy();
		for (ICRole role : allRoles) {
			rolesDropdownMenu.addMenuElement(role.getRoleKey(), iwc.getIWMainApplication().getBundle(CORE_IW_BUNDLE_IDENTIFIER).getResourceBundle(iwc).getLocalizedString(role.getRoleNameLocalizableKey(), role.getRoleNameLocalizableKey()));
		}
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.setStyleClass("required");
		label = new Label(this.getResourceBundle(iwc).getLocalizedString("select_role", "Select role"), rolesDropdownMenu);
		formItem.add(label);
		formItem.add(rolesDropdownMenu);
		layer.add(formItem);

	}

	@Override
	protected void submitSimpleForm(IWContext iwc) throws RemoteException {
		boolean hasErrors = false;
		Collection<String> errors = new ArrayList<String>();

		if (iForwardToURL) {
			if (iwc.isParameterSet(COMMUNE_KEY)) {
				String URL = iwc.getParameter(COMMUNE_KEY);
				StringBuffer query = new StringBuffer();
				Enumeration<?> enumeration = iwc.getParameterNames();
				if (enumeration != null) {
					query.append("?");

					while (enumeration.hasMoreElements()) {
						String element = (String) enumeration.nextElement();
						query.append(element).append("=").append(iwc.getParameter(element));
						if (enumeration.hasMoreElements()) {
							query.append("&");
						}
					}
				}
				iwc.sendRedirect(URL + query.toString());
				return;
			} else {
				errors.add(this.getResourceBundle(iwc).getLocalizedString("must_select_commune", "You have to select a commune."));
				hasErrors = true;
			}
		}

		String ssn = iwc.getParameter(SSN_KEY);

		if (ssn == null || ssn.length() == 0) {
			errors.add(this.getResourceBundle(iwc).getLocalizedString("must_provide_personal_id", "You have to enter a personal ID."));
			hasErrors = true;
		} else if (!SocialSecurityNumber.isValidIcelandicSocialSecurityNumber(ssn)) {
			errors.add(this.getResourceBundle(iwc).getLocalizedString("not_a_valid_personal_id", "The personal ID you've entered is not valid."));
			hasErrors = true;
		}
		if (!isValidAge(iwc, ssn)) {
			Object[] arguments = { iwc.getApplicationSettings().getProperty(ATTRIBUTE_VALID_ACCOUNT_AGE, String.valueOf(18)) };
			errors.add(MessageFormat.format(this.getResourceBundle(iwc).getLocalizedString(NOT_VALID_ACCOUNT_AGE_KEY, NOT_VALID_ACCOUNT_AGE_DEFAULT), arguments));
			hasErrors = true;
		}

		String email = iwc.getParameter(EMAIL_KEY);
		if (email == null || email.length() == 0) {
			errors.add(this.getResourceBundle(iwc).getLocalizedString("email_can_not_be_empty", "You must provide a valid e-mail address"));
			hasErrors = true;
		}

		boolean agreementAccepted = iwc.getParameter(APP_AGREEMENT_PARAM) != null && iwc.getParameter(APP_AGREEMENT_PARAM).length() > 0;

		if (!agreementAccepted) {
			errors.add(this.getResourceBundle(iwc).getLocalizedString(APP_AGREEMENT_NOTAGREED_KEY, APP_AGREEMENT_NOTAGREED_DEFAULT));
			hasErrors = true;
		}

		String emailRepeat = iwc.getParameter(EMAIL_KEY_REPEAT);
		String phoneHome = iwc.getParameter(PHONE_HOME_KEY);
		String phoneWork = iwc.getParameter(PHONE_CELL_KEY);
		String preferredLocale = iwc.getParameter(PARAMETER_PREFERRED_LOCALE);

		WSCitizenAccountBusiness business = getBusiness(iwc);
		User user = business.getUserIcelandic(ssn);

		boolean userHasLogin = false;
		Collection<String> userLoginError = new ArrayList<String>();
		if (user == null) {
			errors.add(this.getResourceBundle(iwc).getLocalizedString(UNKNOWN_CITIZEN_KEY, UNKNOWN_CITIZEN_DEFAULT));
			hasErrors = true;
		} else {

			try {
				Collection<LoginTable> logins = new ArrayList<LoginTable>();
				logins.addAll(getLoginTableHome().findLoginsForUser(user));
				if (!logins.isEmpty()) {
					userLoginError.add(this.getResourceBundle(iwc).getLocalizedString(USER_ALLREADY_HAS_A_LOGIN_KEY, USER_ALLREADY_HAS_A_LOGIN_DEFAULT));
					hasErrors = true;
					userHasLogin = true;
				}
			} catch (Exception e) {
				// no problem, no login found
			}

			if (email != null && email.length() > 0) {
				if (emailRepeat == null || !email.equals(emailRepeat)) {
					errors.add(this.getResourceBundle(iwc).getLocalizedString(ERROR_EMAILS_DONT_MATCH, ERROR_EMAILS_DONT_MATCH_DEFAULT));
					hasErrors = true;
				}
			}

			String selectedRoleKey = iwc.getParameter(ROLES_DROP_DOWN_KEY);
			if (selectedRoleKey != null) {
				AccessController accessController = iwc.getIWMainApplication().getAccessController();
				try {
					accessController.getRoleByRoleKey(selectedRoleKey);
				} catch (Exception e) {
					errors.add(this.getResourceBundle(iwc).getLocalizedString("role_selection_error", "Selected role is not valid, try again, or select different role"));
					hasErrors = true;
					e.printStackTrace();
				}

			}

			// roles validation ends here
			if (!hasErrors) {
				try {

					boolean wasLoginCreatedSuccessfully = getCompanyBusiness(iwc).createLogginForUser(iwc, user, phoneHome, phoneWork, emailRepeat, selectedRoleKey, true);
					if (!wasLoginCreatedSuccessfully) {
						hasErrors = true;
						errors.add(this.getResourceBundle(iwc).getLocalizedString("account_creation_error", "Error occurred while creating account"));
					}

				} catch (LoginCreateException e) {
					hasErrors = true;
					errors.add(this.getResourceBundle(iwc).getLocalizedString("account_creation_error", "Error occurred while creating account"));
					e.printStackTrace();
				}
			}
		}

		if (!hasErrors && preferredLocale != null) {
			getUserBusiness(iwc).setUsersPreferredLocale(user, preferredLocale, true);

		}

		if (hasErrors) {
			showErrors(iwc, userHasLogin ? userLoginError : errors);
			viewSimpleApplicationForm(iwc);
		} else {
			Form form = new Form();
			form.setID("accountApplicationForm");
			form.setStyleClass("citizenForm");

			Layer header = new Layer(Layer.DIV);
			header.setStyleClass("header");
			form.add(header);

			Heading1 heading = new Heading1(this.getResourceBundle(iwc).getLocalizedString("citizen_registration", "Citizen registration"));
			header.add(heading);

			Layer layer = new Layer(Layer.DIV);
			layer.setStyleClass("receipt");

			Layer image = new Layer(Layer.DIV);
			image.setStyleClass("receiptImage");
			layer.add(image);

			String serverName = iwc.getApplicationSettings().getProperty("server_name", "");

			heading = new Heading1(this.getResourceBundle(iwc).getLocalizedString(TEXT_APPLICATION_SUBMITTED_KEY + (serverName.length() > 0 ? ("_" + serverName) : ""), TEXT_APPLICATION_SUBMITTED_DEFAULT));
			layer.add(heading);

			layer.add(new Text(this.getResourceBundle(iwc).getLocalizedString("successfull_registration_message", "Your application for access has been received. A username and password will be immediately sent to you by e-mail.")));

			form.add(layer);
			add(form);

			if (this.iPage != null) {
				iwc.forwardToIBPage(getParentPage(), this.iPage, this.iRedirectDelay, false);
			} else if (getRedirectUrlOnSubmit() != null) {

				try {
					iwc.getResponse().sendRedirect(getRedirectUrlOnSubmit());
				} catch (IOException io) {
					// the response is most likely been committed, i.e. written
					// output to already
					iwc.forwardToURL(getParentPage(), getRedirectUrlOnSubmit(), this.iRedirectDelay, false);
				}

			}
		}

	}

	protected CompanyApplicationBusiness getCompanyBusiness(IWContext iwc) throws RemoteException{
		return (CompanyApplicationBusiness)IBOLookup.getServiceInstance(iwc, CompanyApplicationBusiness.class);
	}

}
