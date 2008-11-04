package is.idega.idegaweb.egov.company.business;

import is.idega.idegaweb.egov.company.EgovCompanyConstants;

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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.company.CompanyConstants;
import com.idega.core.builder.data.ICPage;
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

@Scope("singleton")
@Service(CompanyPortalBusiness.SPRING_BEAN_IDENTIFIER)
public class CompanyPortalBusinessBean implements CompanyPortalBusiness {

	private static final long serialVersionUID = -3548980819753114719L;
	
	private static final Logger logger = Logger.getLogger(CompanyPortalBusinessBean.class.getName());
	
	@SuppressWarnings("unchecked")
	public Group getCompanyPortalRootGroup(IWApplicationContext iwac) throws RemoteException {
		Collection<Group> groups = getGroupBusiness(iwac).getGroupsByGroupName(EgovCompanyConstants.COMPANY_PORTAL_ROOT_GROUP);
		if (ListUtil.isEmpty(groups)) {
			return null;
		}
		
		//	Root group for Company Portal already exists
		return groups.iterator().next();
	}
	
	public Group getAllCompaniesAdminsGroup(IWApplicationContext iwac) {
		Group allAdminsGroup = null;
		try {
			allAdminsGroup = getGroupByName(getCompanyPortalRootGroup(iwac), EgovCompanyConstants.COMPANY_ADMINS_GROUP_IN_COMPANY_PORTAL);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Error getting company admins group", e);
		}
		if (allAdminsGroup == null) {
			return null;
		}
		
		setHomePage(iwac, allAdminsGroup);
		return allAdminsGroup;
	}
	
	public Group getCompanySuperAdminsGroup(IWApplicationContext iwac) {
		try {
			return getGroupByName(getCompanyPortalRootGroup(iwac), EgovCompanyConstants.COMPANY_SUPER_ADMINS_GROUP_IN_COMPANY_PORTAL);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Error getting company super admins group", e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Group getGroupByName(Group parentGroup, String name) {
		if (parentGroup == null || StringUtil.isEmpty(name)) {
			return null;
		}
		
		Collection<Group> childGroups = parentGroup.getChildGroups();
		if (ListUtil.isEmpty(childGroups)) {
			return null;
		}
		
		Group group = null;
		for (Iterator<Group> childGroupsIter = childGroups.iterator(); (childGroupsIter.hasNext() && group == null);) {
			group = childGroupsIter.next();
			
			if (!name.equals(group.getName())) {
				group = null;
			}
		}
		
		return group;
	}
	
	public Group createCompanyGroup(IWApplicationContext iwac, String companyName) {
		try {
			return createCompanyGroup(iwac, getCompanyPortalRootGroup(iwac), companyName);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Error creating group for company: " + companyName, e);
		}
		return null;
	}
	
	public Group createCompanyGroup(IWApplicationContext iwac, Group companyPortal, String companyName) {
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
		setHomePage(iwac, company);
		
		IWResourceBundle iwrb = getResourceBundle(iwac);
		String adminsGroupName = "Administrators of";
		adminsGroupName = iwrb == null ? adminsGroupName : iwrb.getLocalizedString("company_portal.administrators_of", adminsGroupName);
		try {
			groupBusiness.createGroupUnder(new StringBuilder(adminsGroupName).append(CoreConstants.SPACE).append(companyName).toString(), null,
					EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS, company);
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
			iwac.getIWMainApplication().getAccessController().addRoleToGroup(EgovCompanyConstants.COMPANY_EMPLOYEE_ROLE, companyStaff, iwac);
			setHomePage(iwac, companyStaff);
		}
		
		return company;
	}
	
	public void setHomePage(IWApplicationContext iwac, Group group) {
		if (group == null) {
			return;
		}
		
		String homePageKey = iwac.getApplicationSettings().getProperty(EgovCompanyConstants.COMPANY_PORTAL_HOME_PAGE_APPLICATION_PROPERTY);
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
	
	public void setHomePageToGroups(IWApplicationContext iwac) {
		//	All administrators of companies
		setHomePage(iwac, getAllCompaniesAdminsGroup(iwac));
		
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
				for (Group companyStaff: companyStaffGroups) {
					setHomePage(iwac, companyStaff);
				}
			}
		}
	}
	
	public Group getCompanyGroupByUser(User user) {
		List<Group> userCompanies = getAllUserCompanies(user);
		return ListUtil.isEmpty(userCompanies) ? null : userCompanies.get(0);
	}
	
	public Group getCompanyStaffGroupByUser(User user) {
		return getChildGroupByType(getCompanyGroupByUser(user), EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF);
	}
	
	public Group getCompanyAdminsGroupByUser(User user) {
		return getChildGroupByType(getCompanyGroupByUser(user), EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS);
	}
	
	@SuppressWarnings("unchecked")
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
		
		try {
			return userBusiness.getUserGroups(user);
		} catch (EJBException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Group getCompanyGroup(IWApplicationContext iwac, String companyName) {
		Group companyPortal = null;
		try {
			companyPortal = getCompanyPortalRootGroup(iwac);
		} catch (RemoteException e) {
			logger.log(Level.SEVERE, "Error getting Company Portal root group", e);
		}
		if (companyPortal == null) {
			return null;
		}
		Group company = getGroupByName(companyPortal, companyName);
		if (company == null) {
			company = createCompanyGroup(iwac, companyPortal, companyName);
		}
		
		setHomePage(iwac, company);
		return company;
	}
	
	public Group getCompanyAdminsGroup(IWApplicationContext iwac, String companyName) {
		return getChildGroupByType(getCompanyGroup(iwac, companyName), EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS);
	}
	
	public Group getCompanyStaffGroup(IWApplicationContext iwac, String companyName) {
		Group companyStaffGroup = getChildGroupByType(getCompanyGroup(iwac, companyName), EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF);
		if (companyStaffGroup == null) {
			return null;
		}
		setHomePage(iwac, companyStaffGroup);
		return companyStaffGroup;
	}
	
	public Group getChildGroupByType(Group group, String type) {
		List<Group> groupsByType = getAllChildGroupsByType(group, type);
		return ListUtil.isEmpty(groupsByType) ? null : groupsByType.get(0);
	}
	
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

	public List<User> getAllCompanyUsers(Group company) {
		List<User> allUsers = new ArrayList<User>();
		
		Collection<User> companyGroupUsers = getUsersInGroup(company);
		if (!ListUtil.isEmpty(companyGroupUsers)) {
			allUsers.addAll(companyGroupUsers);
		}
		
		Collection<User> adminsGroupUsers = getUsersInGroup(getChildGroupByType(company, EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS));
		if (!ListUtil.isEmpty(adminsGroupUsers)) {
			for (User user: adminsGroupUsers) {
				if (!allUsers.contains(user)) {
					allUsers.add(user);
				}
			}
		}
		
		Collection<User> staffGroupUsers = getUsersInGroup(getChildGroupByType(company, EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF));
		if (!ListUtil.isEmpty(staffGroupUsers)) {
			for (User user: staffGroupUsers) {
				if (!allUsers.contains(user)) {
					allUsers.add(user);
				}
			}
		}
		
		if (!ListUtil.isEmpty(allUsers)) {
			Locale locale = null;
			IWContext iwc = CoreUtil.getIWContext();
			if (iwc != null) {
				locale = iwc.getCurrentLocale();
			}
			if (locale == null) {
				locale = Locale.ENGLISH;
			}
			Collections.sort(allUsers, new UserComparator(locale));
		}
		
		return allUsers;
	}
	
	@SuppressWarnings("unchecked")
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

	public List<Group> getAllUserCompanies(User user) {
		Collection<Group> userGroups = getUserGroups(user);
		if (ListUtil.isEmpty(userGroups)) {
			return null;
		}
		
		List<Group> userCompanies = new ArrayList<Group>();
		for (Group group: userGroups) {
			if (CompanyConstants.GROUP_TYPE_COMPANY.equals(group.getGroupType())) {
				setHomePage(IWMainApplication.getDefaultIWApplicationContext(), group);
				
				if (!userCompanies.contains(group)) {
					userCompanies.add(group);
				}
			}
		}
		
		return userCompanies;
	}

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
			try {
				companyGroup = getGroupBusiness(iwac).getGroupByGroupID(Integer.valueOf(group.getParentNode().getId()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (FinderException e) {
				e.printStackTrace();
			}
			if (companyGroup == null || !CompanyConstants.GROUP_TYPE_COMPANY.equals(companyGroup.getGroupType())) {
				return false;
			}
		}
		
		return allUserCompanies.contains(companyGroup);
	}

}
