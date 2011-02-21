package is.idega.idegaweb.egov.company.business;


import is.idega.idegaweb.egov.company.data.CompanyApplication;

import java.rmi.RemoteException;
import java.util.List;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.user.data.Group;
import com.idega.user.data.User;

public interface CompanyPortalBusiness {
	
	public String SPRING_BEAN_IDENTIFIER = "companyPortalBusiness";
	
	public Group getCompanyPortalRootGroup(IWApplicationContext iwac) throws RemoteException;
	
	public Group getAllCompaniesAdminsGroup(IWApplicationContext iwac);
	
	public Group getCompanyAdminsGroup(IWApplicationContext iwac, String companyName, String personalID);
	public Group getCompanyStaffGroup(IWApplicationContext iwac, String companyName, String personalID);
	
	public Group getCompanySuperAdminsGroup(IWApplicationContext iwac);
	
	public Group getGroupByName(Group parentGroup, String name);
	public Group getChildGroupByType(Group group, String type);
	public List<Group> getAllChildGroupsByType(Group group, String type);
	
	public Group createCompanyGroup(IWApplicationContext iwac, String companyName, String personalID);
	public Group createCompanyGroup(IWApplicationContext iwac, Group companyPortal, String companyName, String personalID);
	
	public Group getCompanyGroup(IWApplicationContext iwac, String companyName, String personalID);
	
	public List<Group> getAllUserCompanies(User user);
	
	public Group getCompanyGroupByUser(User user);
	public Group getCompanyStaffGroupByUser(User user);
	public Group getCompanyAdminsGroupByUser(User user);
	
	public void setHomePage(IWApplicationContext iwac, Group group, String homePageKey);
	public void setHomePageToGroups(IWApplicationContext iwac);
	
	public List<User> getAllCompanyUsers(Group company);
	public List<User> getAllCompanyUsers(CompanyApplication application);
	
	public List<Group> getAllCompanyGroups(Group company);
	
	public boolean isMemberOfCompany(IWApplicationContext iwac, Group group, User user);
	
}