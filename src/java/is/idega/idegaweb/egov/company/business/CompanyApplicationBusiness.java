package is.idega.idegaweb.egov.company.business;


import is.idega.idegaweb.egov.application.business.ApplicationBusiness;
import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.company.bean.AdminUser;
import is.idega.idegaweb.egov.company.bean.CompanyInfo;
import is.idega.idegaweb.egov.company.data.CompanyApplication;
import is.idega.idegaweb.egov.company.data.CompanyApplicationHome;
import is.idega.idegaweb.egov.message.business.CommuneMessageBusiness;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.block.pdf.business.PrintingService;
import com.idega.business.IBOLookupException;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.accesscontrol.business.LoginCreateException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.presentation.IWContext;
import com.idega.slide.business.IWSlideService;
import com.idega.user.data.Group;
import com.idega.user.data.User;

public interface CompanyApplicationBusiness extends ApplicationBusiness {
	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getApplication
	 */
	public CompanyApplication getApplication(String applicationId)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getApplication
	 */
	public CompanyApplication getApplication(Company company)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getBundle
	 */
	public IWBundle getBundle() throws RemoteException;
	
	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#storeApplication
	 */
	public Application storeApplication(IWContext iwc, User admin, CompanyType companyType, Company company, User currentUser) throws CreateException,
		RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#approveApplication
	 */
	public String approveApplication(IWContext iwc, String applicationId)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#rejectApplication
	 */
	public boolean rejectApplication(IWApplicationContext iwac, String applicationId,
			String explanationText) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#reactivateApplication
	 */
	public boolean requestInformation(IWApplicationContext iwac, String applicationId,
			String explanationText) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#sendEmail
	 */
	public boolean sendEmail(String email, String subject, String text)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#isCompanyAdministrator
	 */
	public boolean isCompanyAdministrator(IWContext iwc) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#isCompanyEmployee
	 */
	public boolean isCompanyEmployee(IWContext iwc) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getCompanyApplicationHome
	 */
	public CompanyApplicationHome getCompanyApplicationHome()
			throws RemoteException;

	/**
	 * Creates a logging(personal-id) for user and sends email with user name and password
	 * 
	 * @param iwc
	 * @param user
	 * @param phoneHome
	 * @param phoneWork
	 * @param email
	 * @param addToRootCitizenGroup - if true user and his role is added to root citizen group
	 * @param roleKey
	 * 
	 * @throws LoginCreateException
	 * @throws RemoteException
	 * 
	 * @return true if user login was created successfully, false otherwise
	 */
	public boolean createLogginForUser(IWContext iwc, User user,
			String phoneHome, String phoneWork, String email, String roleKey,
			boolean addToRootCitizenGroup) throws LoginCreateException,
			RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getUserApplications
	 */
	public Collection<Application> getAvailableApplicationsForUser(IWContext iwc, User user)
			throws FinderException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getUserCompany
	 */
	public Group getUserCompany(IWContext iwc, User user)
			throws RemoteException;
	
	public boolean makeUserCompanyAdmin(IWApplicationContext iwac, User companyAdmin, Group company);
	
	public boolean makeUserCommonEmployee(IWApplicationContext iwac, User companyAdmin, Group company);
	
	public String getLoginCreatedInfo(IWContext iwc, String login, String password);
	
	public Collection<Application> getUserApplications(IWContext iwc, User user);
	
	public CommuneMessageBusiness getMessageBusiness() throws RemoteException;
	
	public Collection<Group> getAllUserCompanies(IWContext iwc, User user) throws RemoteException;
	
	public PrintingService getPrintingService();
	
	public IWSlideService getIWSlideService() throws IBOLookupException;
	
	public boolean reopenAccount(IWContext iwc, String applicationId);
	
	public String generateContract(String applicationId);
	
	public boolean isAccountOpen(CompanyApplication application);
	
	public AdminUser getUser(String personalId);
	
	public CompanyInfo getCompany(String companyUniqueId, String companyPhone, String companyFax, String companyEmail, String companyWebpage,
			String companyBankAccount);
	
	public Collection<CompanyApplication> getUnhandledApplications(String[] caseCodes);
	
	public Collection<CompanyApplication> getApprovedApplications(String[] caseCodes);
	
	public Collection<CompanyApplication> getRejectedApplications(String[] caseCodes);
	
	public List<CompanyApplication> getApplicationsByCaseCodesAndStatuses(String[] caseCodes, List<String> caseStatuses);
}