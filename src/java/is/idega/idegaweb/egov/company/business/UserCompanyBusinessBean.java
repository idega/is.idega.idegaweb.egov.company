package is.idega.idegaweb.egov.company.business;

import java.rmi.RemoteException;
import java.util.Collection;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.presentation.IWContext;
import com.idega.user.business.UserBusiness;
import com.idega.user.business.UserCompanyBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;

@Scope("singleton")
@Service("userCompanyBusinessBean")
public class UserCompanyBusinessBean implements UserCompanyBusiness {

	public Collection<Group> getUsersCompanies(IWContext iwc, User user) throws RemoteException {
		CompanyApplicationBusiness business = getCompanyApplicationBusiness(iwc);
		return business.getAllUserCompanies(iwc, user);
	}

	public void setPreferedCompanyForCurrentUser(String companyId) throws RemoteException {
		IWContext iwc = IWContext.getCurrentInstance();
		getUserBusiness(iwc).setPreferedCompany(companyId, iwc.getCurrentUser());
	}

	public Group getPreferedCompanyForUser(IWContext iwc, User user) throws RemoteException {
		return getUserBusiness(iwc).getPreferedCompany(user);
	}

	protected CompanyApplicationBusiness getCompanyApplicationBusiness(IWContext iwc) throws IBOLookupException {
		return (CompanyApplicationBusiness) IBOLookup.getServiceInstance(iwc, CompanyApplicationBusiness.class);
	}

	protected UserBusiness getUserBusiness(IWContext iwc) throws IBOLookupException {
		return (UserBusiness) IBOLookup.getServiceInstance(iwc, UserBusiness.class);
	}
}
