package is.idega.idegaweb.egov.company.business;

import is.idega.block.nationalregister.webservice.client.business.SkyrrClient;
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
import java.util.Locale;

import javax.ejb.CreateException;

import com.idega.block.pdf.business.PrintingService;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.accesscontrol.business.LoginCreateException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.presentation.IWContext;
import com.idega.user.data.Group;
import com.idega.user.data.User;

public interface CompanyApplicationBusiness extends ApplicationBusiness {
	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getApplication
	 */
	@Override
	public CompanyApplication getApplication(String applicationId)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getApplication
	 */
	@Override
	public CompanyApplication getApplication(Object primaryKey)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getApplication
	 */
	public CompanyApplication getApplication(Company company)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getCompanyContact
	 */
	public User getCompanyContact(Company company)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#setCompanyContact
	 */
	public void setCompanyContact(Company company, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getBundle
	 */
	public IWBundle getBundle() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#approveApplication
	 */
	public List<String> approveApplication(IWContext iwc, String applicationId)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#approveApplication
	 */
	public List<String> approveApplication(IWContext iwc,
			CompanyApplication compApp) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#rejectApplication
	 */
	public boolean rejectApplication(IWContext iwc, String applicationId,
			String explanationText) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#requestInformation
	 */
	public boolean requestInformation(IWApplicationContext iwac,
			String applicationId, String explanationText)
			throws RemoteException;

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
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#makeUserCompanyAdmin
	 */
	public boolean makeUserCompanyAdmin(IWApplicationContext iwac,
			User companyAdmin, Group companyGroup) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#makeUserCommonEmployee
	 */
	public boolean makeUserCommonEmployee(IWApplicationContext iwac,
			User companyAdmin, Group company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#createLogginForUser
	 */
	public boolean createLogginForUser(IWContext iwc, User user,
			String phoneHome, String phoneWork, String email, String roleKey,
			boolean addToRootCitizenGroup) throws LoginCreateException,
			RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getPrintingService
	 */
	public PrintingService getPrintingService() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#addCommonCompanyPortalServices
	 */
	public boolean addCommonCompanyPortalServices(IWContext iwc)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getAssignedServices
	 */
	public Collection<Application> getAssignedServices(IWContext iwc, User user)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getUserApplications
	 */
	public Collection<Application> getUserApplications(IWContext iwc, User user)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getMessageBusiness
	 */
	public CommuneMessageBusiness getMessageBusiness() throws RemoteException,
			RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#storeApplication
	 */
	public CompanyApplication storeApplication(IWContext iwc, User admin,
			CompanyType companyType, Company company, User performer)
			throws CreateException, RemoteException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getApplicationsByCaseCodesAndStatuses
	 */
	public List<CompanyApplication> getApplicationsByCaseCodesAndStatuses(
			String[] caseCodes, List<String> caseStatuses)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getUnhandledApplications
	 */
	public Collection<CompanyApplication> getUnhandledApplications(
			String[] caseCodes);

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getApprovedApplications
	 */
	public Collection<CompanyApplication> getApprovedApplications(
			String[] caseCodes);

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getRejectedApplications
	 */
	public Collection<CompanyApplication> getRejectedApplications(
			String[] caseCodes);

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getApplicationName
	 */
	@Override
	public String getApplicationName(Application app, Locale locale);

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#closeAccount
	 */
	public boolean closeAccount(IWContext iwc, String applicationId);

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#reopenAccount
	 */
	public boolean reopenAccount(IWContext iwc, String applicationId);

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#reopenAccount
	 */
	public boolean reopenAccount(IWContext iwc, CompanyApplication compApp);

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#isAccountOpen
	 */
	public boolean isAccountOpen(CompanyApplication application)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#generateContract
	 */
	public String generateContract(String applicationId) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getUser
	 */
	public AdminUser getUser(String personalId) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getCompany
	 */
	public CompanyInfo getCompany(String companyUniqueId, String companyPhone,
			String companyFax, String companyEmail, String companyWebpage,
			String companyBankAccount) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#isInstitutionAdministration
	 */
	public boolean isInstitutionAdministration(IWContext iwc)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusinessBean#getSkyrrClient
	 */
	public SkyrrClient getSkyrrClient() throws RemoteException;
}