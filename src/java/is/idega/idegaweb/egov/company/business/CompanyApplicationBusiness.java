package is.idega.idegaweb.egov.company.business;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.FinderException;

import is.idega.idegaweb.egov.application.business.ApplicationBusiness;
import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.company.data.CompanyApplication;
import is.idega.idegaweb.egov.company.data.CompanyApplicationHome;

import com.idega.company.data.Company;
import com.idega.core.accesscontrol.business.LoginCreateException;
import com.idega.presentation.IWContext;
import com.idega.user.data.Group;
import com.idega.user.data.User;

public interface CompanyApplicationBusiness extends ApplicationBusiness {

	public static final String SPRING_BEAN_IDENTIFIER = "companyApplicationBusiness";

	public CompanyApplication getApplication(String applicationId);

	public CompanyApplication getApplication(Company company);

	public boolean approveApplication(String applicationId);

	public boolean rejectApplication(String applicationId, String explanationText);

	public boolean isCompanyAdministrator(IWContext iwc);

	public boolean isCompanyEmployee(IWContext iwc);

	public CompanyApplicationHome getCompanyApplicationHome();

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
	public boolean createLogginForUser(IWContext iwc, User user, String phoneHome, String phoneWork, String email, String roleKey, boolean addToRootCitizenGroup ) throws LoginCreateException, RemoteException;
	
	/**
	 * return all applications that can be viewed by user
	 * 
	 * @param iwc
	 * @param user
	 * @return 
	 * @throws FinderException
	 */
	public Collection<Application> getUserApplications(IWContext iwc, User user) throws FinderException;
	
	public boolean sendEmail(String email, String subject, String text);
	
	public Group getUserCompany(IWContext iwc, User user);
}
