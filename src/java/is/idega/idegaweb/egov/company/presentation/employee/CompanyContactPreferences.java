package is.idega.idegaweb.egov.company.presentation.employee;

import is.idega.idegaweb.egov.citizen.presentation.CitizenAccountPreferences;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;
import is.idega.idegaweb.egov.company.business.CompanyPortalBusiness;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.FinderException;

import org.springframework.beans.factory.annotation.Autowired;

import com.idega.block.web2.business.Web2Business;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.data.Company;
import com.idega.event.IWPageEventListener;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWException;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.TextInput;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.PresentationUtil;
import com.idega.util.expression.ELUtil;

public class CompanyContactPreferences extends CitizenAccountPreferences implements IWPageEventListener {

	@Autowired
	private CompanyPortalBusiness companyBusiness;

	public CompanyContactPreferences() {
		super();
		setToShowNameAndPersonalID(true);
		setNameAndPersonalIDDisabled(true);
	}

	@Override
	protected User getUser(IWContext iwc) {
		try {
			return getCompanyApplicationBusiness(iwc).getCompanyContact(getCompanyForUser(iwc, iwc.getCurrentUser()));
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	@Override
	protected void getUserInputs(IWContext iwc, Form form, Layer section) {
		//	Scripts
		Web2Business web2 = ELUtil.getInstance().getBean(Web2Business.SPRING_BEAN_IDENTIFIER);
		List<String> scripts = new ArrayList<String>();
		scripts.add(CoreConstants.DWR_ENGINE_SCRIPT);
		scripts.add(CoreConstants.DWR_UTIL_SCRIPT);
		scripts.add("/dwr/interface/CompanyApplicationBusiness.js");
		scripts.add(iwc.getIWMainApplication().getBundle(EgovCompanyConstants.IW_BUNDLE_IDENTIFIER).getVirtualPathWithFileNameString("javascript/CompanyContactPreferences.js"));
		scripts.add(web2.getBundleURIToJQueryLib());
		scripts.add(web2.getBundleUriToHumanizedMessagesScript());
		PresentationUtil.addJavaScriptSourcesLinesToHeader(iwc, scripts);

		//	CSS
		PresentationUtil.addStyleSheetToHeader(iwc, web2.getBundleUriToHumanizedMessagesStyleSheet());

		form.setEventListener(this.getClass());

		TextInput name = new TextInput(PARAMETER_NAME, user.getName());
		name.setID("userNameInput");
		createFormItem(this.iwrb.getLocalizedString("name", "Name"), name, section);

		if (isNameAndPersonalIDDisabled()) {
			name.setDisabled(true);

			HiddenInput hidden = new HiddenInput(PARAMETER_NAME, user.getName());
			hidden.setID("userNameHidden");
			section.add(hidden);
		}

		TextInput ssn = new TextInput(PARAMETER_SSN, user.getPersonalID());
		ssn.setID("userSSNInput");
		createFormItem(this.iwrb.getLocalizedString("social_security_number", "Social security number"), ssn, section);
	}

	private Company getCompanyForUser(IWContext iwc, User user) {
		List<Group> companies = getCompanyPortalBusiness().getAllUserCompanies(user);
		if (companies != null && companies.size() > 0) {
			Group group = companies.iterator().next();

			String personalID = group.getMetaData(EgovCompanyConstants.COMPANY_PERSONAL_ID_METADATA);
			if (personalID != null) {
				try {
					return getCompanyBusiness(iwc).getCompany(personalID);
				}
				catch (FinderException fe) {
					fe.printStackTrace();
				}
				catch (RemoteException re) {
					throw new IBORuntimeException(re);
				}
			}
		}

		return null;
	}

	private CompanyBusiness getCompanyBusiness(IWApplicationContext iwac) {
		try {
			return (CompanyBusiness) IBOLookup.getServiceInstance(iwac, CompanyBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private CompanyPortalBusiness getCompanyPortalBusiness() {
		if (this.companyBusiness == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.companyBusiness;
	}

	protected CompanyApplicationBusiness getCompanyApplicationBusiness(IWApplicationContext iwac) {
		try {
			return (CompanyApplicationBusiness) IBOLookup.getServiceInstance(iwac, CompanyApplicationBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private UserBusiness getUserBusiness(IWApplicationContext iwac) {
		try {
			return (UserBusiness) IBOLookup.getServiceInstance(iwac, UserBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	@Override
	public boolean actionPerformed(IWContext iwc) throws IWException {
		if (iwc.isParameterSet(PARAMETER_SSN)) {
			try {
				User user = getUserBusiness(iwc).getUser(iwc.getParameter(PARAMETER_SSN));
				getCompanyApplicationBusiness(iwc).setCompanyContact(getCompanyForUser(iwc, iwc.getCurrentUser()), user);
			}
			catch (RemoteException re) {
				re.printStackTrace();
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
		}
		return false;
	}
}