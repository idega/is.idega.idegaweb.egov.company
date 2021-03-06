package is.idega.idegaweb.egov.company.business;

import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.data.CompanyApplication;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.FinderException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.CompanyConstants;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.data.Company;
import com.idega.core.builder.data.ICPage;
import com.idega.core.business.DefaultSpringBean;
import com.idega.core.data.ICTreeNode;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.user.business.GroupBusiness;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.user.util.UserComparator;
import com.idega.util.CoreConstants;
import com.idega.util.CoreUtil;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

@Scope(BeanDefinition.SCOPE_SINGLETON)
@Service(CompanyPortalBusiness.SPRING_BEAN_IDENTIFIER)
public class CompanyPortalBusinessBean extends DefaultSpringBean implements CompanyPortalBusiness {

	private static final Logger logger = Logger.getLogger(CompanyPortalBusinessBean.class.getName());

	@Override
	public Group getCompanyPortalRootGroup(IWApplicationContext iwac) throws RemoteException {
		Collection<Group> groups = getGroupBusiness(iwac).getGroupsByGroupName(EgovCompanyConstants.COMPANY_PORTAL_ROOT_GROUP);
		if (ListUtil.isEmpty(groups)) {
			return null;
		}

		//	Root group for Company Portal already exists
		return groups.iterator().next();
	}

	@Override
	public Group getAllCompaniesAdminsGroup(IWApplicationContext iwac) {
		Group allAdminsGroup = null;
		try {
			allAdminsGroup = getGroupByName(getCompanyPortalRootGroup(iwac), EgovCompanyConstants.COMPANY_ADMINS_GROUP_IN_COMPANY_PORTAL, null);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Error getting company admins group", e);
		}
		if (allAdminsGroup == null) {
			return null;
		}

		String homePageKey = iwac.getApplicationSettings().getProperty(EgovCompanyConstants.COMPANY_PORTAL_HOME_PAGE_APPLICATION_PROPERTY);
		setHomePage(iwac, allAdminsGroup, homePageKey);
		return allAdminsGroup;
	}

	@Override
	public Group getCompanySuperAdminsGroup(IWApplicationContext iwac) {
		try {
			return getGroupByName(getCompanyPortalRootGroup(iwac), EgovCompanyConstants.COMPANY_SUPER_ADMINS_GROUP_IN_COMPANY_PORTAL, null);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Error getting company super admins group", e);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Group getGroupByName(Group parentGroup, String name, String personalID) {
		if (parentGroup == null || StringUtil.isEmpty(name)) {
			return null;
		}

		Collection<Group> childGroups = parentGroup.getChildGroups();
		if (ListUtil.isEmpty(childGroups)) {
			return null;
		}

		boolean foundGroup = false;
		Group group = null;
		for (Iterator<Group> childGroupsIter = childGroups.iterator(); childGroupsIter.hasNext();) {
			group = childGroupsIter.next();

			if (name.equals(group.getName())) {
				if (personalID != null) {
					String companySSN = group.getMetaData(EgovCompanyConstants.COMPANY_PERSONAL_ID_METADATA);
					if (companySSN != null && personalID.equals(companySSN)) {
						foundGroup = true;
						break;
					}
				} else {
					foundGroup = true;
					break;
				}
			}

		}

		if (foundGroup) {
			return group;
		}

		return null;
	}

	@Override
	public Group createCompanyGroup(IWApplicationContext iwac, String companyName, String personalID) {
		try {
			return createCompanyGroup(iwac, getCompanyPortalRootGroup(iwac), companyName, personalID);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Error creating group for company: " + companyName, e);
		}
		return null;
	}

	@Override
	public Group createCompanyGroup(IWApplicationContext iwac, Group companyPortal, String companyName, String personalID) {
		GroupBusiness groupBusiness = getGroupBusiness(iwac);
		if (groupBusiness == null) {
			return null;
		}

		Group company = null;
		try {
			company = groupBusiness.createGroupUnder(companyName, null, CompanyConstants.GROUP_TYPE_COMPANY, companyPortal);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error creating group for company '" + companyName + "' under: " + companyPortal.getName(), e);
		}
		if (company == null) {
			return null;
		}

		company.setMetaData(EgovCompanyConstants.COMPANY_PERSONAL_ID_METADATA, personalID);
		company.store();

		IWResourceBundle iwrb = getResourceBundle(iwac);
		String adminsGroupName = "Administrators of";
		adminsGroupName = iwrb == null ? adminsGroupName : iwrb.getLocalizedString("company_portal.administrators_of", adminsGroupName);
		try {
			Group adminGroup = groupBusiness.createGroupUnder(new StringBuilder(adminsGroupName).append(CoreConstants.SPACE).append(companyName).toString(), null,
					EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS, company);
			String homePageKey = iwac.getApplicationSettings().getProperty(EgovCompanyConstants.COMPANY_PORTAL_HOME_PAGE_APPLICATION_PROPERTY);
			setHomePage(iwac, adminGroup, homePageKey);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error creating administrators group for company: " + companyName, e);
		}

		String staffGroupName = "Employees of";
		staffGroupName = iwrb == null ? staffGroupName : iwrb.getLocalizedString("company_portal.employees_of", staffGroupName);
		Group companyStaff = null;
		try {
			companyStaff = groupBusiness.createGroupUnder(new StringBuilder(staffGroupName).append(CoreConstants.SPACE).append(companyName).toString(), null,
					EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF, company);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error creating staff group for company: " + companyName, e);
		}
		if (companyStaff != null) {
			String homePageKey = iwac.getApplicationSettings().getProperty(EgovCompanyConstants.COMPANY_STAFF_HOME_PAGE_APPLICATION_PROPERTY);
			iwac.getIWMainApplication().getAccessController().addRoleToGroup(EgovCompanyConstants.COMPANY_EMPLOYEE_ROLE, companyStaff, iwac);
			setHomePage(iwac, companyStaff, homePageKey);
		}

		return company;
	}

	@Override
	public void setHomePage(IWApplicationContext iwac, Group group, String homePageKey) {
		if (group == null) {
			return;
		}

		if (StringUtil.isEmpty(homePageKey)) {
			return;
		}

		ICPage currentHomePage = group.getHomePage();
		if (currentHomePage != null && homePageKey.equals(currentHomePage.getId())) {
			return;
		}

		try {
			group.setHomePageID(Integer.valueOf(homePageKey));
		} catch(NumberFormatException e) {
			logger.log(Level.SEVERE, "Error setting home page of Company Portal to: " + group.getName(), e);
		}
		group.store();
	}

	@Override
	public void setHomePageToGroups(IWApplicationContext iwac) {
		//	All administrators of companies
		String homePageKey = iwac.getApplicationSettings().getProperty(EgovCompanyConstants.COMPANY_PORTAL_HOME_PAGE_APPLICATION_PROPERTY);
		setHomePage(iwac, getAllCompaniesAdminsGroup(iwac), homePageKey);

		//	Companies
		List<Group> companies = null;
		try {
			companies = getAllChildGroupsByType(getCompanyPortalRootGroup(iwac), CompanyConstants.GROUP_TYPE_COMPANY);
		} catch (RemoteException e) {
			logger.log(Level.WARNING, "Unable to get Company Portal root group", e);
		}
		if (ListUtil.isEmpty(companies)) {
			return;
		}

		List<Group> companyStaffGroups = null;
		for (Group company: companies) {
			companyStaffGroups = getAllChildGroupsByType(company, EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF);

			//	Company staff
			if (!ListUtil.isEmpty(companyStaffGroups)) {
				homePageKey = iwac.getApplicationSettings().getProperty(EgovCompanyConstants.COMPANY_STAFF_HOME_PAGE_APPLICATION_PROPERTY);
				for (Group companyStaff: companyStaffGroups) {
					setHomePage(iwac, companyStaff, homePageKey);
				}
			}
		}
	}

	@Override
	public Group getCompanyGroupByUser(User user) {
		Object groupId = null;
		Group selectedGroup = null;

		IWContext iwc = CoreUtil.getIWContext();
		if (iwc != null && (groupId = iwc.getSessionAttribute(EgovCompanyConstants.COMPANY_PORTAL_CURRENT_COMPANY_ATTRIBUTE)) != null) {
			if (groupId instanceof String) {
				try {
					selectedGroup = getGroupBusiness(iwc).getGroupByGroupID(Integer.valueOf(groupId.toString()));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (FinderException e) {
					e.printStackTrace();
				}
			}
		}
		if (selectedGroup !=  null) {
			return selectedGroup;
		}

		List<Group> userCompanies = getAllUserCompanies(user);
		return ListUtil.isEmpty(userCompanies) ? null : userCompanies.get(0);
	}

	@Override
	public Group getCompanyStaffGroupByUser(User user) {
		return getChildGroupByType(getCompanyGroupByUser(user), EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF);
	}

	@Override
	public Group getCompanyAdminsGroupByUser(User user) {
		return getChildGroupByType(getCompanyGroupByUser(user), EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS);
	}

	private Collection<Group> getUserGroups(User user) {
		if (user == null) {
			return null;
		}

		UserBusiness userBusiness = null;
		try {
			userBusiness = (UserBusiness) IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), UserBusiness.class);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (userBusiness == null) {
			return null;
		}

		Collection<Group> groups = null;
		try {
			groups = userBusiness.getUserGroups(user);
		} catch (EJBException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (ListUtil.isEmpty(groups)) {
			logger.log(Level.INFO, "There are no groups for user: " + user.getName());
		}
		return groups;
	}

	@Override
	public Group getCompanyGroup(IWApplicationContext iwac, String companyName, String personalID) {
		Group companyPortal = null;
		try {
			companyPortal = getCompanyPortalRootGroup(iwac);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Error getting Company Portal root group", e);
		}
		if (companyPortal == null) {
			return null;
		}
		Group company = getGroupByName(companyPortal, companyName, personalID);
		if (company == null) {
			company = createCompanyGroup(iwac, companyPortal, companyName, personalID);
		}

		String homePageKey = iwac.getApplicationSettings().getProperty(EgovCompanyConstants.COMPANY_PORTAL_HOME_PAGE_APPLICATION_PROPERTY);
		setHomePage(iwac, company, homePageKey);
		return company;
	}

	@Override
	public Group getCompanyAdminsGroup(IWApplicationContext iwac, String companyName, String personalID) {
		return getChildGroupByType(getCompanyGroup(iwac, companyName, personalID), EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS);
	}

	@Override
	public Group getCompanyStaffGroup(IWApplicationContext iwac, String companyName, String personalID) {
		Group companyStaffGroup = getChildGroupByType(getCompanyGroup(iwac, companyName, personalID), EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF);
		if (companyStaffGroup == null) {
			return null;
		}
		String homePageKey = iwac.getApplicationSettings().getProperty(EgovCompanyConstants.COMPANY_STAFF_HOME_PAGE_APPLICATION_PROPERTY);
		setHomePage(iwac, companyStaffGroup, homePageKey);
		return companyStaffGroup;
	}

	@Override
	public Group getChildGroupByType(Group group, String type) {
		List<Group> groupsByType = getAllChildGroupsByType(group, type);
		return ListUtil.isEmpty(groupsByType) ? null : groupsByType.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Group> getAllChildGroupsByType(Group group, String type) {
		if (group == null || StringUtil.isEmpty(type)) {
			return null;
		}

		return group.getChildGroups(new String[] {type}, true);
	}

	private GroupBusiness getGroupBusiness(IWApplicationContext iwac) {
		try {
			return (GroupBusiness) IBOLookup.getServiceInstance(iwac, GroupBusiness.class);
		} catch (IBOLookupException e) {
			logger.log(Level.SEVERE, "Error getting " + GroupBusiness.class.getName(), e);
		}
		return null;
	}

	private IWResourceBundle getResourceBundle(IWApplicationContext iwac) {
		try {
			return iwac.getIWMainApplication().getBundle(EgovCompanyConstants.IW_BUNDLE_IDENTIFIER)
																	.getResourceBundle(iwac instanceof IWContext ? (IWContext) iwac : CoreUtil.getIWContext());
		} catch(Exception e) {
			logger.log(Level.SEVERE, "Error getting resource bundle", e);
		}
		return null;
	}

	@Override
	public List<User> getAllCompanyUsers(Group company) {
		return getAllCompanyUsers(company, true);
	}

	private List<User> getAllCompanyUsers(Group company, boolean sortUsers) {
		List<User> allUsers = new ArrayList<User>();

		Collection<User> companyGroupUsers = getUsersInGroup(company);
		if (!ListUtil.isEmpty(companyGroupUsers)) {
			allUsers.addAll(companyGroupUsers);
		}

		Group companyAdmins = getChildGroupByType(company, EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS);
		Collection<User> adminsGroupUsers = getUsersInGroup(companyAdmins);
		if (ListUtil.isEmpty(adminsGroupUsers)) {
			logger.log(Level.INFO, "No users in company's admin group: " + companyAdmins);
		}
		else {
			for (User user: adminsGroupUsers) {
				if (!allUsers.contains(user)) {
					allUsers.add(user);
				}
			}
		}

		Group companyStaff = getChildGroupByType(company, EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF);
		Collection<User> staffGroupUsers = getUsersInGroup(companyStaff);
		if (ListUtil.isEmpty(staffGroupUsers)) {
			logger.log(Level.INFO, "No users in company's staff group: " + companyStaff);
		}
		else {
			for (User user: staffGroupUsers) {
				if (!allUsers.contains(user)) {
					allUsers.add(user);
				}
			}
		}

		if (ListUtil.isEmpty(allUsers)) {
			logger.log(Level.WARNING, "No users found in company: " + company);
		}

		if (sortUsers) {
			return getSortedUsers(allUsers, null);
		}

		return allUsers;
	}

	private List<User> getSortedUsers(List<User> users, Locale locale) {
		if (ListUtil.isEmpty(users)) {
			return null;
		}
		if (locale == null) {
			IWContext iwc = CoreUtil.getIWContext();
			if (iwc != null) {
				locale = iwc.getCurrentLocale();
			}
		}
		if (locale == null) {
			locale = Locale.ENGLISH;
		}

		Collections.sort(users, new UserComparator(locale));
		return users;
	}

	@Override
	public List<User> getAllCompanyUsers(CompanyApplication application) {
		if (application == null) {
			return null;
		}

		User companyPerson = application.getAdminUser();
		if (companyPerson == null) {
			companyPerson = application.getApplicantUser();
		}
		if (companyPerson == null) {
			return null;
		}
		List<Group> allUserCompanies = getAllUserCompanies(companyPerson);
		if (ListUtil.isEmpty(allUserCompanies)) {
			return null;
		}

		List<User> temp = null;
		List<User> companyUsers = new ArrayList<User>();
		for (Group userCompany: allUserCompanies) {
			temp = getAllCompanyUsers(userCompany);
			if (!ListUtil.isEmpty(temp)) {
				for (User user: temp) {
					if (companyUsers.contains(user)) {
						companyUsers.add(user);
					}
				}
			}
		}

		if (ListUtil.isEmpty(companyUsers)) {
			logger.log(Level.WARNING, "No users found for company application: " + application.getName());
			return null;
		}

		return getSortedUsers(companyUsers, null);
	}

	private Collection<User> getUsersInGroup(Group group) {
		if (group == null) {
			return null;
		}
		UserBusiness userBusiness = getUserBusiness();
		if (userBusiness == null) {
			return null;
		}

		try {
			return userBusiness.getUsersInGroup(group);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}

	private UserBusiness getUserBusiness() {
		try {
			return (UserBusiness) IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), UserBusiness.class);
		} catch (IBOLookupException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Group> getAllCompanyGroups(Group company) {
		if (company == null) {
			return null;
		}

		List<Group> allGroups = new ArrayList<Group>();
		allGroups.add(company);

		Group admins = getChildGroupByType(company, EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS);
		if (admins != null) {
			allGroups.add(admins);
		}

		Group staff = getChildGroupByType(company, EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF);
		if (staff != null) {
			allGroups.add(staff);
		}

		return allGroups;
	}

	@Override
	public List<Group> getAllUserCompanies(User user) {
		Collection<Group> userGroups = getUserGroups(user);
		if (ListUtil.isEmpty(userGroups)) {
			return null;
		}

		Group company = null;
		String groupType = null;
		List<Group> userCompanies = new ArrayList<Group>();
		for (Group group: userGroups) {
			groupType = group.getGroupType();
			company = null;

			if (CompanyConstants.GROUP_TYPE_COMPANY.equals(groupType)) {
				if (getParentGroup(group, null, EgovCompanyConstants.COMPANY_PORTAL_ROOT_GROUP) != null) {
					company = group;
				}
			}
			else if (EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS.equals(groupType) || EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF.equals(groupType)) {
				company = getParentGroup(group, CompanyConstants.GROUP_TYPE_COMPANY, null);
			}

			if (company == null) {
			}
			else {
				if (!userCompanies.contains(company)) {
					userCompanies.add(company);
				}
			}
		}

		return userCompanies;
	}

	private Group getParentGroup(Group group, String type, String name) {
		if (group == null) {
			return null;
		}

		ICTreeNode parentGroupNode = group.getParentNode();
		if (parentGroupNode == null) {
			return null;
		}

		Group parentGroup = null;
		try {
			parentGroup = getGroupBusiness(IWMainApplication.getDefaultIWApplicationContext()).getGroupByGroupID(Integer.valueOf(parentGroupNode.getId()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		}

		if (parentGroup == null) {
			return null;
		}
		if (!StringUtil.isEmpty(type) && !type.equals(parentGroup.getGroupType())) {
			return null;
		}
		if (!StringUtil.isEmpty(name) && !name.equals(parentGroup.getName())) {
			return null;
		}
		return parentGroup;
	}

	@Override
	public boolean isMemberOfCompany(IWApplicationContext iwac, Group group, User user) {
		if (group == null || user == null) {
			return false;
		}
		List<Group> allUserCompanies = getAllUserCompanies(user);
		if (ListUtil.isEmpty(allUserCompanies)) {
			return false;
		}

		String groupType = group.getGroupType();
		if (StringUtil.isEmpty(groupType)) {
			return false;
		}

		if (CompanyConstants.GROUP_TYPE_COMPANY.equals(groupType)) {
			return allUserCompanies.contains(group);
		}

		Group companyGroup = null;
		if (EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF.equals(groupType) || EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS.equals(groupType)) {
			companyGroup = getParentGroup(group, CompanyConstants.GROUP_TYPE_COMPANY, null);
		}

		if (companyGroup == null) {
			return false;
		}
		return allUserCompanies.contains(companyGroup);
	}

	@Override
	public Company getCompanyForUser(User user) {
		if (user == null) {
			logger.warning("User is not provided, unable to resolve companies");
			return null;
		}

		List<Group> companies = getAllUserCompanies(user);
		if (companies != null && companies.size() > 0) {
			Group group = companies.iterator().next();

			String personalID = group.getMetaData(EgovCompanyConstants.COMPANY_PERSONAL_ID_METADATA);
			if (personalID != null) {
				try {
					return getCompanyBusiness().getCompany(personalID);
				} catch (FinderException fe) {
					fe.printStackTrace();
				} catch (RemoteException re) {
					throw new IBORuntimeException(re);
				}
			}
		}

		return null;
	}

	private CompanyBusiness companyBusiness = null;

	private CompanyBusiness getCompanyBusiness() {
		if (this.companyBusiness != null) {
			return this.companyBusiness;
		}

		try {
			this.companyBusiness = IBOLookup.getServiceInstance(
					IWMainApplication.getDefaultIWApplicationContext(),
					CompanyBusiness.class);
		} catch(IBOLookupException e) {
			logger.log(Level.WARNING, "Unable to find " + CompanyBusiness.class);
		}

		return this.companyBusiness;
	}

	private Company getCompany(String personalId) {
		CompanyRegisterService crs = ELUtil.getInstance().getBean(CompanyRegisterService.BEAN_NAME);
		return crs.getCompany(personalId);
	}

	@Override
	public Company getCompanyByPersonalIdOrUserId(String personalId) {
		if (StringUtil.isEmpty(personalId)) {
			getLogger().warning("Personal ID is not provided");
			return null;
		}

		// Checking if it is personal ID of company.
		Company company = getCompany(personalId);

		if (company == null) {
			getLogger().info("Did not find company by ID (" + personalId + "), will look for the user by this ID");

			//	Company not found, trying to find user by personal ID and company for the user
			User user = null;
			try {
				user = getUserBusiness().getUser(personalId);
			} catch (FinderException e) {
			} catch (Exception e) {
				getLogger().log(Level.WARNING, "Unable to get user by personal ID: " + personalId, e);
			}

			// Getting company for a user
			if (user == null) {
				getLogger().warning("User does not exist by ID " + personalId);
			} else {
				getLogger().info("Found user (" + user + ") by personal ID (" + personalId + "), will look for the company for this user");
				company = getCompanyForUser(user);
				if (company == null)
					getLogger().warning("Company does not exist for the user " + user);
				else
					getLogger().info("Found company (" + company + ") for user " + user);
			}
		} else {
			getLogger().info("Found company (" + company + ") by ID " + personalId);
		}

		if (company == null)
			getLogger().warning("Unable to find company for the personal ID: " + personalId);

		return company;
	}
}
