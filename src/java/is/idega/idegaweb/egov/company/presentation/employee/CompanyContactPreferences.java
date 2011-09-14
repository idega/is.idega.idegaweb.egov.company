package is.idega.idegaweb.egov.company.presentation.employee;

import is.idega.idegaweb.egov.citizen.presentation.CitizenAccountPreferences;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;
import is.idega.idegaweb.egov.company.business.CompanyPortalBusiness;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.FinderException;

import org.springframework.beans.factory.annotation.Autowired;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.data.Company;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.presentation.IWContext;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.expression.ELUtil;

public class CompanyContactPreferences extends CitizenAccountPreferences {

	@Autowired
	private CompanyPortalBusiness companyBusiness;
	
	public CompanyContactPreferences() {
		super();
		setToShowNameAndPersonalID(true);
		setNameAndPersonalIDDisabled(false);
	}

	protected User getUser(IWContext iwc) {
		try {
			return getCompanyApplicationBusiness(iwc).getCompanyContact(getCompanyForUser(iwc, iwc.getCurrentUser()));
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private Company getCompanyForUser(IWContext iwc, User user) {
		List<Group> companies = getCompanyPortalBusiness().getAllUserCompanies(user);
		if (companies != null && companies.size() > 0) {
			Group group = companies.iterator().next();
			
			String personalID = group.getMetaData("COMPANY_PERSONAL_ID");
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
}