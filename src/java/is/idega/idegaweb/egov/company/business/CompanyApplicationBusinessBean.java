package is.idega.idegaweb.egov.company.business;

import is.idega.idegaweb.egov.application.business.ApplicationBusinessBean;
import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.data.CompanyApplication;
import is.idega.idegaweb.egov.company.data.CompanyApplicationHome;

import java.rmi.RemoteException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.FinderException;
import javax.mail.MessagingException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.company.data.Company;
import com.idega.core.accesscontrol.business.NotLoggedOnException;
import com.idega.core.contact.data.Email;
import com.idega.data.IDOLookup;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWMainApplicationSettings;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.idegaweb.IWUserContext;
import com.idega.presentation.IWContext;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.CoreUtil;
import com.idega.util.SendMail;
import com.idega.util.StringUtil;

@Scope("singleton")
@Service(CompanyApplicationBusiness.SPRING_BEAN_IDENTIFIER)
public class CompanyApplicationBusinessBean extends ApplicationBusinessBean implements CompanyApplicationBusiness {

	private static final long serialVersionUID = 2473252235079303894L;
	private static final Logger logger = Logger.getLogger(CompanyApplicationBusinessBean.class.getName());
	
	//	It's a Spring bean, use ELUtil to get instance of it!
	private CompanyApplicationBusinessBean() {}
	
	@Override
	public CompanyApplication getApplication(String applicationId) {
		if (StringUtil.isEmpty(applicationId)) {
			return null;
		}
		
		Object primaryKey = applicationId;
		Application app = null;
		try {
			app = getApplication(primaryKey);
		} catch (FinderException e) {
			logger.log(Level.SEVERE, "Error getting application by ID: " + applicationId, e);
		}
		if (!(app instanceof CompanyApplication)) {
			logger.log(Level.SEVERE, "Application " + app + " is not company application!");
			return null;
		}
		
		return (CompanyApplication) app;
	}
	
	public CompanyApplication getApplication(Company company) {
		CompanyApplication app;
		try {
			app = getCompanyApplicationHome().findByCompany(company);
		} catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
		return app;
	}
	
	@Override
	public IWBundle getBundle() {
		return IWMainApplication.getDefaultIWMainApplication().getBundle(EgovCompanyConstants.IW_BUNDLE_IDENTIFIER);
	}
	
	public boolean approveApplication(String applicationId) {
		if (!setStatusToCompanyApplication(applicationId, getCaseStatusGranted().getStatus())) {
			return false;
		}
		
		CompanyApplication compApp = getApplication(applicationId);
		if (compApp == null) {
			return false;
		}
		
		IWResourceBundle iwrb = getResourceBundle();
		StringBuilder subject = new StringBuilder(getMailSubjectStart(compApp));
		subject.append(iwrb.getLocalizedString("application_approved_mail_subject", "application was approved"));
		String text = iwrb.getLocalizedString("application_approved_mail_text", "Application was approved.");
		
		//	TODO: Make account for company, send email with "random" password
		
		return sendMail(compApp, subject.toString(), text);
	}

	public boolean rejectApplication(String applicationId, String explanationText) {
		if (!setStatusToCompanyApplication(applicationId, getCaseStatusDenied().getStatus())) {
			return false;
		}
		
		CompanyApplication compApp = getApplication(applicationId);
		if (compApp == null) {
			return false;
		}
		
		IWResourceBundle iwrb = getResourceBundle();
		StringBuilder subject = new StringBuilder(getMailSubjectStart(compApp));
		subject.append(iwrb.getLocalizedString("application_rejected_mail_subject", "application was rejected"));
		return sendMail(compApp, subject.toString(), explanationText);
	}
	
	private boolean setStatusToCompanyApplication(String applicationId, String status) {
		CompanyApplication compApp = getApplication(applicationId);
		if (compApp == null) {
			return false;
		}
		
		compApp.setStatus(status);
		compApp.store();
		
		return true;
	}
	
	private boolean sendMail(CompanyApplication compApp, String subject, String text) {
		if (compApp == null || StringUtil.isEmpty(text)) {
			return false;
		}
		IWMainApplicationSettings settings = IWMainApplication.getDefaultIWMainApplication().getSettings();
		if (settings == null) {
			return false;
		}
		
		Company company = compApp.getCompany();
		if (company == null) {
			return false;
		}
		Email email = company.getEmail();
		if (email == null) {
			return false;
		}
		
		String from = settings.getProperty(CoreConstants.PROP_SYSTEM_MAIL_FROM_ADDRESS);
		String host = settings.getProperty(CoreConstants.PROP_SYSTEM_SMTP_MAILSERVER);
		if (StringUtil.isEmpty(from) || StringUtil.isEmpty(host)) {
			logger.log(Level.WARNING, "Cann't send email from: " + from + " via: " + host + ". Set properties for application!");
			return false;
		}
		try {
			SendMail.send(from, email.getEmailAddress(), null, null, host, subject, text);
		} catch (MessagingException e) {
			logger.log(Level.WARNING, "Error sending mail!", e);
			return false;
		}
		
		return true;
	}
	
	private IWResourceBundle getResourceBundle() {
		return getBundle().getResourceBundle(CoreUtil.getIWContext());
	}
	
	private String getMailSubjectStart(CompanyApplication compApp) {
		Locale locale = null;
		IWContext iwc = CoreUtil.getIWContext();
		if (iwc != null) {
			locale = iwc.getCurrentLocale();
		}
		if (locale == null) {
			locale = Locale.ENGLISH;
		}
		
		return new StringBuilder(getApplicationName(compApp, locale)).append(CoreConstants.COLON).append(CoreConstants.SPACE).toString();
	}
	
	private boolean isUserLogged(IWUserContext iwuc) {
		if (iwuc == null) {
			return false;
		}
		
		User user = null;
		try {
			user = iwuc.getCurrentUser();
		} catch(NotLoggedOnException e) {
			logger.log(Level.SEVERE, "User is not logged!", e);
		}
		
		return user == null ? false : true;
	}

	public boolean isCompanyAdministrator(IWContext iwc) {
		try {
			return isUserLogged(iwc) && iwc.getAccessController().hasRole(EgovCompanyConstants.COMPANY_ADMIN_ROLE, iwc);
		} catch(NotLoggedOnException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isCompanyEmployee(IWContext iwc) {
		try {
			return isCompanyAdministrator(iwc) || iwc.getAccessController().hasRole(EgovCompanyConstants.COMPANY_EMPLOYEE_ROLE, iwc);
		} catch(NotLoggedOnException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public CompanyApplicationHome getCompanyApplicationHome() {
		try {
			return (CompanyApplicationHome) IDOLookup.getHome(CompanyApplication.class);
		}
		catch (RemoteException rme) {
			throw new RuntimeException(rme.getMessage());
		}
	}
}
