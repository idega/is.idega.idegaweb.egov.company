package is.idega.idegaweb.egov.company.business.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.company.business.CompanyBusiness;
import com.idega.company.data.Company;
import com.idega.core.accesscontrol.business.LoginDBHandler;
import com.idega.core.business.DefaultSpringBean;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.user.business.GroupBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

import is.idega.idegaweb.egov.citizen.business.UserEntityService;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;
import is.idega.idegaweb.egov.company.business.CompanyPortalBusiness;

@Service("companyEntityUserService")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CompanyEntityUserService extends DefaultSpringBean implements UserEntityService {

	@Override
	public User getUserForEntity(String entityPersonalId) {
		if (StringUtil.isEmpty(entityPersonalId)) {
			return null;
		}

		User user = null;
		try {
			CompanyBusiness companyBusiness = getCompanyBusiness(getIWApplicationContext());

			//Get the company by ssn
			Company company = companyBusiness.getCompany(entityPersonalId);

			//Get the user for the company
			if (company != null) {
				Collection<User> companyUsers = companyBusiness.getOwnersForCompanies(Arrays.asList(company));
				if (!ListUtil.isEmpty(companyUsers)) {
					for (User companyUser: companyUsers) {
						if (companyUser != null && LoginDBHandler.getUserLogin(companyUser) != null) {
							user = companyUser;
							break;
						}
					}
				}
			}

			//If user is not found, search the company admin user
			if (user == null && company != null) {
				Group adminsGroupForCompany = getCompanyPortalBusiness().getCompanyAdminsGroup(getIWApplicationContext(), company.getName(), company.getPersonalID());
				if (adminsGroupForCompany != null) {
					GroupBusiness groupBusiness = getGroupBusiness(getIWApplicationContext());
					Collection<User> adminUsers = groupBusiness.getUsers(adminsGroupForCompany);
					if (!ListUtil.isEmpty(adminUsers)) {
						for (User adminUser : adminUsers) {
							if (adminUser != null && LoginDBHandler.getUserLogin(adminUser) != null) {
								user = adminUser;
								break;
							}
						}
					}
				}
			}

			//If user was not found, get the company contact/admin user
			if (user == null && company != null) {
				User companyContactUser = getCompanyApplicationBusiness(getIWApplicationContext()).getCompanyContact(company);
				if (companyContactUser != null && LoginDBHandler.getUserLogin(companyContactUser) != null) {
					user = companyContactUser;
				}
			}
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting user for entity with personal ID: " + entityPersonalId, e);
		}

		return user;
	}

	private CompanyBusiness getCompanyBusiness(IWApplicationContext iwac) {
		return getServiceInstance(iwac, CompanyBusiness.class);
	}

	private GroupBusiness getGroupBusiness(IWApplicationContext iwac) {
		return getServiceInstance(iwac, GroupBusiness.class);
	}

	private CompanyApplicationBusiness getCompanyApplicationBusiness(IWApplicationContext iwac) {
		return getServiceInstance(iwac, CompanyApplicationBusiness.class);
	}

	private CompanyPortalBusiness getCompanyPortalBusiness() {
		try {
			return ELUtil.getInstance().getBean(CompanyPortalBusiness.SPRING_BEAN_IDENTIFIER);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error getting bean " + CompanyPortalBusiness.SPRING_BEAN_IDENTIFIER, e);
		}
		return null;
	}

}