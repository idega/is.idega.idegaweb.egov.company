package is.idega.idegaweb.egov.company.business;

import is.idega.block.nationalregister.webservice.client.business.CompanyHolder;
import is.idega.block.nationalregister.webservice.client.business.SkyrrClient;
import is.idega.block.nationalregister.webservice.client.business.UserHolder;
import is.idega.idegaweb.egov.accounting.business.CitizenBusiness;
import is.idega.idegaweb.egov.application.business.ApplicationBusiness;
import is.idega.idegaweb.egov.application.business.ApplicationBusinessBean;
import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.citizen.business.WSCitizenAccountBusinessBean;
import is.idega.idegaweb.egov.citizen.wsclient.arion.BirtingurLocator;
import is.idega.idegaweb.egov.citizen.wsclient.arion.BirtingurSoap;
import is.idega.idegaweb.egov.citizen.wsclient.landsbankinn.SendLoginDataBusiness;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.bean.AdminUser;
import is.idega.idegaweb.egov.company.bean.CompanyInfo;
import is.idega.idegaweb.egov.company.data.CompanyApplication;
import is.idega.idegaweb.egov.company.data.CompanyApplicationHome;
import is.idega.idegaweb.egov.company.data.CompanyEmployee;
import is.idega.idegaweb.egov.company.data.CompanyEmployeeHome;
import is.idega.idegaweb.egov.message.business.CommuneMessageBusiness;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.mail.MessagingException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.rpc.ServiceException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.springframework.beans.factory.annotation.Autowired;

import com.idega.block.pdf.business.PrintingContext;
import com.idega.block.pdf.business.PrintingService;
import com.idega.block.process.data.CaseStatus;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.companyregister.business.CompanyRegisterBusiness;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.accesscontrol.business.AccessController;
import com.idega.core.accesscontrol.business.LoginCreateException;
import com.idega.core.accesscontrol.business.LoginDBHandler;
import com.idega.core.accesscontrol.business.NotLoggedOnException;
import com.idega.core.accesscontrol.data.LoginInfo;
import com.idega.core.accesscontrol.data.LoginTable;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.core.file.data.ICFile;
import com.idega.core.file.data.ICFileHome;
import com.idega.core.file.util.MimeTypeUtil;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.PostalCode;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWMainApplicationSettings;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.idegaweb.IWUserContext;
import com.idega.io.MemoryFileBuffer;
import com.idega.io.MemoryInputStream;
import com.idega.io.MemoryOutputStream;
import com.idega.presentation.IWContext;
import com.idega.user.business.GroupBusiness;
import com.idega.user.business.NoEmailFoundException;
import com.idega.user.business.NoPhoneFoundException;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.ArrayUtil;
import com.idega.util.CoreConstants;
import com.idega.util.CoreUtil;
import com.idega.util.EncryptionType;
import com.idega.util.FileUtil;
import com.idega.util.IWTimestamp;
import com.idega.util.ListUtil;
import com.idega.util.SendMail;
import com.idega.util.StringHandler;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;
import com.idega.util.text.Name;
import com.idega.util.text.SocialSecurityNumber;

public class CompanyApplicationBusinessBean extends ApplicationBusinessBean
		implements CompanyApplicationBusiness, CallbackHandler {

	private static final long serialVersionUID = 2473252235079303894L;
	private static final Logger logger = Logger
			.getLogger(CompanyApplicationBusinessBean.class.getName());

	private static String CONTRACT_SLIDE_PATH = CoreConstants.PUBLIC_PATH
			+ "/company_contracts/";

	private static final String USE_WEBSERVICE_FOR_COMPANY_LOOKUP = "COMPANY_WS_LOOKUP";

	private static final String ALLOW_INDIVIDUALS_FOR_COMPANY_LOOKUP = "COMPANY_WS_INDIVIDUAL";

	protected static final String BANK_SENDER_PIN = "COMPANY_BANK_SENDER_PIN";

	protected static final String BANK_SENDER_USER_ID = "COMPANY_BANK_SENDER_USER_ID";

	protected static final String BANK_SENDER_USER_PASSWORD = "COMPANY_BANK_SENDER_USER_PW";

	protected static final String BANK_SENDER_TYPE = "COMPANY_BANK_SENDER_TYPE";

	protected static final String BANK_SENDER_TYPE_VERSION = "COMPANY_BANK_SENDER_TYPE_VERSION";

	protected static final String BANK_SENDER_URL = "COMPANY_BANK_SENDER_URL";

	protected static final String SERVICE_URL = "https://www.arionbanki.is/Netbanki4/StandardServices/Birtingur.asmx";

	protected static final String COMPANY_BANK = "COMPANY_BANK";

	@Autowired
	private SkyrrClient skyrrClient;

	@Override
	public CompanyApplication getApplication(String applicationId) {
		if (StringUtil.isEmpty(applicationId)) {
			return null;
		}

		Object primaryKey = applicationId;
		Application app = null;
		app = getApplication(primaryKey);

		if (!(app instanceof CompanyApplication)) {
			logger.log(Level.SEVERE, "Application " + app
					+ " is not company application!");
			return null;
		}

		return (CompanyApplication) app;
	}

	@Override
	public CompanyApplication getApplication(Object primaryKey) {
		CompanyApplication app;
		try {
			app = getCompanyApplicationHome().findByPrimaryKey(primaryKey);
		} catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
		return app;
	}

	@Override
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
	public User getCompanyContact(Company company) {
		CompanyApplication app = getApplication(company);
		if (app != null) {
			return app.getApplicantUser();
		}
		return null;
	}

	@Override
	public void setCompanyContact(Company company, User user) {
		CompanyApplication app = getApplication(company);
		if (app != null) {
			app.setApplicantUser(user);
			app.store();
		}
	}

	@Override
	public IWBundle getBundle() {
		return IWMainApplication.getDefaultIWMainApplication().getBundle(
				EgovCompanyConstants.IW_BUNDLE_IDENTIFIER);
	}

	@Override
	public List<String> approveApplication(IWContext iwc, String applicationId) {
		CompanyApplication compApp = getApplication(applicationId);

		return approveApplication(iwc, compApp);
	}

	@Override
	public List<String> approveApplication(IWContext iwc,
			CompanyApplication compApp) {
		if (compApp == null) {
			return null;
		}

		boolean sendToBank = getIWApplicationContext().getApplicationSettings()
				.getBoolean("COMPANY_REG_TO_BANK", false);

		String currentStatus = compApp.getStatus();
		if (!setStatusToCompanyApplication(compApp, getCaseStatusGranted()
				.getStatus())) {
			return null;
		}

		IWResourceBundle iwrb = getResourceBundle(iwc);
		StringBuilder subject = new StringBuilder(getMailSubjectStart(compApp));
		subject.append(iwrb
				.getLocalizedString("application_approved_mail_subject",
						"application was approved"));
		String text = iwrb.getLocalizedString(
				"application_successfully_approved",
				"Application was successfully approved!");

		String adminPassword = makeAccountsForCompanyAdmins(iwc, compApp);
		if (StringUtil.isEmpty(adminPassword)) {
			logger.log(Level.INFO, "Error approving application: "
					+ compApp.getPrimaryKey().toString()
					+ ", can not create account for company admin");
			setStatusToCompanyApplication(compApp, currentStatus);
			return null;
		}

		Company company = compApp.getCompany();
		company.setValid(true);
		company.setOpen(true);
		company.store();

		compApp.setCompany(company);
		compApp.store();

		if (adminPassword.equals(CoreConstants.MINUS)) {
			// Company admin already exists
			if (!reopenAccount(iwc, compApp)) {
				setStatusToCompanyApplication(compApp, currentStatus);
				logger.log(
						Level.WARNING,
						"Can not reopen accounts for company: "
								+ company.getName());
				return null;
			}
			return Arrays.asList(text);
		}

		Email email = null;
		try {
			email = getUserBusiness().getUsersMainEmail(
					compApp.getApplicantUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (email == null) {
			return null;
		}

		List<String> texts = new ArrayList<String>();
		texts.add(text);
		LoginTable login = LoginDBHandler.getUserLogin(compApp.getAdminUser());
		text = new StringBuilder(text).append("\n\r").toString();
		List<String> loginInfoTexts = getLoginCreatedInfo(iwc,
				login.getUserLogin(), adminPassword);
		texts.addAll(loginInfoTexts);
		StringBuilder emailText = new StringBuilder();
		for (String loginText : loginInfoTexts) {
			emailText.append(loginText).append("\n\r");
		}

		if (sendToBank) {
			sendLoginInfoToBank(compApp, login.getUserLogin(), adminPassword);
		} else {
			sendMail(email, subject.toString(),
					new StringBuilder(text).append(emailText.toString())
							.toString());
		}

		return texts;
	}

	private void sendLoginInfoToBank(CompanyApplication compApp, String login,
			String password) {
		String ssn = getIWApplicationContext().getApplicationSettings()
				.getProperty(BANK_SENDER_PIN);
		String user3 = getIWApplicationContext().getApplicationSettings()
				.getProperty(BANK_SENDER_TYPE);
		String user3version = getIWApplicationContext()
				.getApplicationSettings().getProperty(BANK_SENDER_TYPE_VERSION,
						"001");

		String bank = getIWApplicationContext().getApplicationSettings()
				.getProperty(COMPANY_BANK, "arion");

		boolean sendToAdmin = getIWApplicationContext()
				.getApplicationSettings().getBoolean("COMPANY_REG_TO_ADMIN",
						false);
		String receiverSSN = null;
		if (sendToAdmin) {
			receiverSSN = compApp.getApplicantUser().getPersonalID();
		} else {
			receiverSSN = compApp.getCompany().getPersonalID();
		}

		String xml = getXML(compApp.getCompany().getName(), login, password,
				ssn, compApp.getCompany().getPrimaryKey().toString(),
				receiverSSN, user3, user3version);

		StringBuffer filename = new StringBuffer(ssn);
		filename.append("PW_");
		filename.append(IWTimestamp.RightNow().getDateString("ddMMyyyy"));
		filename.append("_");
		filename.append(receiverSSN);
		filename.append(".xml");

		if ("arion".equals(bank)) {
			encodeAndSendXMLArion(xml, filename.toString());
		} if ("landsbanki".equals(bank)) {
			try {
				SendLoginDataBusiness send_data = getServiceInstance(SendLoginDataBusiness.class);
				send_data.send(xml);
			} catch (IBOLookupException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	private void encodeAndSendXMLArion(String xml, String filename) {
		String userId = getIWApplicationContext().getApplicationSettings()
				.getProperty(BANK_SENDER_USER_ID);

		String url = getIWApplicationContext().getApplicationSettings()
				.getProperty(BANK_SENDER_URL, SERVICE_URL);

		try {
			File file = FileUtil.getFileFromWorkspace(getResourceRealPath(
					getBundle(getIWApplicationContext()), null)
					+ "deploy_client.wsdd");

			EngineConfiguration config = new FileProvider(new FileInputStream(
					file));
			BirtingurLocator locator = new BirtingurLocator(config);
			BirtingurSoap port = locator.getBirtingurSoap(new URL(url));

			Stub stub = (Stub) port;
			stub._setProperty(WSHandlerConstants.ACTION,
					WSHandlerConstants.USERNAME_TOKEN);
			stub._setProperty(WSHandlerConstants.PASSWORD_TYPE,
					WSConstants.PW_TEXT);
			stub._setProperty(WSHandlerConstants.USER, userId);
			stub._setProperty(WSHandlerConstants.PW_CALLBACK_CLASS, this
					.getClass().getName());
			stub._setProperty(WSHandlerConstants.ADD_UT_ELEMENTS,
					"Nonce Created");
			stub._setProperty(WSHandlerConstants.TIMESTAMP,
					IWTimestamp.getTimestampRightNow());

			port.sendDocument(xml.getBytes("iso-8859-1"), filename);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private IWBundle getBundle(IWApplicationContext iwac) {
		return iwac.getIWMainApplication().getBundle(
				EgovCompanyConstants.IW_BUNDLE_IDENTIFIER);
	}

	protected String getResourceRealPath(IWBundle iwb, Locale locale) {
		if (locale != null) {
			return iwb.getResourcesRealPath(locale) + "/";
		} else {
			return iwb.getResourcesRealPath() + "/";
		}
	}

	private String getXML(String name, String login, String password,
			String senderPin, String xkey, String user1, String user3,
			String user3version) {

		String pageLink = getIWApplicationContext().getApplicationSettings().getProperty(WSCitizenAccountBusinessBean.BANK_SENDER_PAGELINK);
		String logo = getIWApplicationContext().getApplicationSettings().getProperty(WSCitizenAccountBusinessBean.BANK_SENDER_LOGOLINK);
		String mayor = getIWApplicationContext().getApplicationSettings().getProperty(WSCitizenAccountBusinessBean.CITIZEN_MAYOR_NAME);
		String signature = getIWApplicationContext().getApplicationSettings().getProperty(WSCitizenAccountBusinessBean.CITIZEN_MAYOR_SIGNATURE_URL);

		String definitionName = "idega.is";
		String acct = senderPin + user1;
		if (user3version == null || user3version.equals("")) {
			user3version = "001";
		}
		user3 = user3 + "-" + user3version;
		String user4 = acct + xkey;

		String encoding = "iso-8859-1";

		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"");
		xml.append(encoding);
		xml.append("\"?>\n");
		xml.append("<!DOCTYPE XML-S SYSTEM \"XML-S.dtd\"[]>\n");
		xml.append("<XML-S>\n");
		xml.append("\t<Statement Acct=\"");
		xml.append(acct);
		xml.append("\" Date=\"");
		xml.append(IWTimestamp.RightNow().getDateString("yyyy/MM/dd"));
		xml.append("\" XKey=\"");
		xml.append(xkey);
		xml.append("\">\n");
		xml.append("\t\t<?bgls.BlueGill.com DefinitionName=");
		xml.append(definitionName);
		xml.append("?>\n");
		xml.append("\t\t<?bgls.BlueGill.com User1=");
		xml.append(user1);
		xml.append("?>\n");
		xml.append("\t\t<?bgls.BlueGill.com User3=");
		xml.append(user3);
		xml.append("?>\n");
		xml.append("\t\t<?bgls.BlueGill.com User4=");
		xml.append(user4);
		xml.append("?>\n");
		xml.append("\t\t<Section Name=\"IDEGA\" Occ=\"1\">\n");
		xml.append("\t\t\t<Field Name=\"Company\">");
		xml.append(true);
		xml.append("</Field>\n");
		xml.append("\t\t\t<Field Name=\"Name\">");
		xml.append(name);
		xml.append("</Field>\n");
		xml.append("\t\t\t<Field Name=\"UserName\">");
		xml.append(login);
		xml.append("</Field>\n");
		xml.append("\t\t\t<Field Name=\"Password\">");
		xml.append(password);
		xml.append("</Field>\n");
		xml.append("\t\t\t<Field Name=\"PageLink\">");
		if (pageLink != null)
			xml.append(pageLink);
		xml.append("</Field>\n");
		xml.append("\t\t\t<Field Name=\"Logo\">");
		if (logo != null)
			xml.append(logo);
		xml.append("</Field>\n");
		xml.append("\t\t\t<Field Name=\"Mayor\">");
		if (mayor != null)
			xml.append(mayor);
		xml.append("</Field>\n");
		xml.append("\t\t\t<Field Name=\"Signature\">");
		if (signature != null)
			xml.append(signature);
		xml.append("</Field>\n");
		xml.append("\t\t</Section>\n");
		xml.append("\t</Statement>\n");
		xml.append("</XML-S>");

		return xml.toString();
	}

	private List<String> getLoginCreatedInfo(IWContext iwc, String login,
			String password) {
		String portNumber = new StringBuilder(":").append(
				String.valueOf(iwc.getServerPort())).toString();
		String serverLink = StringHandler.replace(iwc.getServerURL(),
				portNumber, CoreConstants.EMPTY);

		IWResourceBundle iwrb = getResourceBundle(iwc);

		List<String> texts = new ArrayList<String>();
		texts.add(new StringBuilder(iwrb.getLocalizedString("login_here",
				"Login here")).append(": ").append(serverLink).toString());
		texts.add(new StringBuilder(iwrb.getLocalizedString("your_user_name",
				"Your user name"))
				.append(": ")
				.append(login)
				.append(", ")
				.append(iwrb.getLocalizedString("your_password",
						"your password")).append(": ").append(password)
				.append(".").toString());
		texts.add(iwrb.getLocalizedString(
				"we_recommend_to_change_password_after_login",
				"We recommend to change password after login!"));

		return texts;
	}

	private String makeAccountsForCompanyAdmins(IWApplicationContext iwac,
			CompanyApplication compApp) {
		Company company = compApp.getCompany();
		if (company == null) {
			return null;
		}

		User companyAdmin = compApp.getAdminUser();
		if (companyAdmin != null) {
			logger.log(Level.INFO, "Application " + compApp.getName()
					+ " already has admin: " + companyAdmin.getName());
			return CoreConstants.MINUS;
		}

		String companyName = company.getName();
		User applicant = compApp.getApplicantUser();
		if (applicant == null) {
			logger.log(Level.SEVERE, "Unkown contact person for company: "
					+ companyName);
			return null;
		}

		UserBusiness userBusiness = null;
		try {
			userBusiness = getUserBusiness();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (userBusiness == null) {
			return null;
		}

		CompanyPortalBusiness companyPortalBusiness = getCompanyPortalBusiness(iwac);
		if (companyPortalBusiness == null) {
			return null;
		}

		Group rootGroupForCompany = companyPortalBusiness.getCompanyGroup(iwac,
				companyName, company.getPersonalID());
		if (rootGroupForCompany == null) {
			logger.log(Level.INFO, "Can not find group for company: "
					+ companyName);
			return null;
		}
		Group adminsGroupForCompany = companyPortalBusiness
				.getCompanyAdminsGroup(iwac, companyName,
						company.getPersonalID());
		if (adminsGroupForCompany == null) {
			logger.log(Level.INFO, "Can not find admins group for company: "
					+ companyName);
			return null;
		}
		Group allAdminsGroup = companyPortalBusiness
				.getAllCompaniesAdminsGroup(iwac);
		if (allAdminsGroup == null) {
			logger.log(Level.INFO,
					"Can not find group for all companies admins");
			return null;
		}

		String personalId = company.getPersonalID();
		try {
			companyAdmin = userBusiness.getUser(personalId);
		} catch (RemoteException e) {
		} catch (FinderException e) {
		}
		String password = LoginDBHandler.getGeneratedPasswordForUser();
		if (companyAdmin == null) {
			try {
				companyAdmin = userBusiness.createUserWithLogin(companyName,
						null, null, personalId, companyName, null, null, null,
						Integer.valueOf(allAdminsGroup.getId()), personalId,
						password, true, null, -1, null, true, true,
						EncryptionType.MD5);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (CreateException e) {
				e.printStackTrace();
			}
			if (companyAdmin == null) {
				return null;
			}

			companyAdmin.store();
		} else {
			if (companyAdmin.getFirstName() == null
					|| "".equals(companyAdmin.getFirstName())) {
				companyAdmin.setFullName(companyName);
				companyAdmin.store();
			}
			LoginTable login = LoginDBHandler.getUserLogin(companyAdmin);
			if (login == null) {
				try {
					login = LoginDBHandler.createLogin(companyAdmin,
							personalId, password, true, null, -1, null, true,
							true, EncryptionType.MD5);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (login == null) {
					return null;
				}
			}

			// Add to all admins group if not there
			try {
				GroupBusiness groupBusiness = getGroupBusiness(iwac);
				Collection<User> users = groupBusiness.getUsers(allAdminsGroup);
				if (users == null || !users.contains(companyAdmin)) {
					groupBusiness.addUser(
							Integer.valueOf(allAdminsGroup.getId()),
							companyAdmin);
				}
				companyAdmin.setPrimaryGroup(allAdminsGroup);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (FinderException e) {
				e.printStackTrace();
			} catch (EJBException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		// Copying email
		try {
			userBusiness.updateUserMail(companyAdmin, userBusiness
					.getUsersMainEmail(applicant).getEmailAddress());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		} catch (NoEmailFoundException e) {
			e.printStackTrace();
		}
		// Copying mobile phone
		try {
			userBusiness.updateUserMobilePhone(companyAdmin, userBusiness
					.getUsersMobilePhone(applicant).getNumber());
		} catch (EJBException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NoPhoneFoundException e) {
			e.printStackTrace();
		}
		// Copying work phone
		try {
			userBusiness.updateUserWorkPhone(companyAdmin, userBusiness
					.getUsersWorkPhone(applicant).getNumber());
		} catch (EJBException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NoPhoneFoundException e) {
			e.printStackTrace();
		}
		// Copying address
		Address companyAddress = company.getAddress();
		if (companyAddress != null) {
			try {
				userBusiness.updateUsersMainAddressOrCreateIfDoesNotExist(
						companyAdmin, companyAddress.getStreetAddress(),
						companyAddress.getPostalCode(),
						companyAddress.getCountry(), companyAddress.getCity(),
						companyAddress.getProvince(),
						companyAddress.getPOBox(),
						companyAddress.getCommuneID());
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (CreateException e) {
				e.printStackTrace();
			}
		}

		companyAdmin.setJuridicalPerson(true);
		companyAdmin.store();

		// Adding company admin user to company admins group
		try {
			GroupBusiness groupBusiness = getGroupBusiness(iwac);
			Collection<User> users = groupBusiness
					.getUsers(adminsGroupForCompany);
			if (users == null || !users.contains(companyAdmin)) {
				groupBusiness.addUser(
						Integer.valueOf(adminsGroupForCompany.getId()),
						companyAdmin);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (EJBException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		compApp.setAdminUser(companyAdmin);
		compApp.store();

		return makeUserCompanyAdmin(iwac, companyAdmin, rootGroupForCompany) ? password
				: null;
	}

	@Override
	public boolean rejectApplication(IWContext iwc, String applicationId,
			String explanationText) {
		CompanyApplication compApp = getApplication(applicationId);
		if (compApp == null) {
			return false;
		}

		String currentStatus = compApp.getStatus();
		if (!setStatusToCompanyApplication(compApp, getCaseStatusDenied()
				.getStatus())) {
			return false;
		}

		if (!closeAccount(iwc, applicationId)) {
			setStatusToCompanyApplication(compApp, currentStatus);
			return false;
		}

		Company company = compApp.getCompany();
		if (company == null) {
			setStatusToCompanyApplication(compApp, currentStatus);
			return false;
		}
		company.setOpen(false);
		company.setValid(false);

		Email email = null;
		try {
			email = getUserBusiness().getUsersMainEmail(
					compApp.getApplicantUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (email == null) {
			return false;
		}

		IWResourceBundle iwrb = getResourceBundle();
		StringBuilder subject = new StringBuilder(getMailSubjectStart(compApp));
		subject.append(iwrb.getLocalizedString(
				"application.closed_message_subject",
				"application was canceled"));
		sendMail(email, subject.toString(), explanationText);

		return true;
	}

	@Override
	public boolean requestInformation(IWApplicationContext iwac,
			String applicationId, String explanationText) {
		CompanyApplication compApp = getApplication(applicationId);
		if (compApp == null) {
			return false;
		}

		Email email = null;
		try {
			email = getUserBusiness().getUsersMainEmail(
					compApp.getApplicantUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (email == null) {
			return false;
		}

		IWResourceBundle iwrb = getResourceBundle();
		StringBuilder subject = new StringBuilder(getMailSubjectStart(compApp));
		subject.append(iwrb.getLocalizedString(
				"application.request_info_message_subject",
				"Further information requested"));
		return sendMail(email, subject.toString(), explanationText);
	}

	private boolean setStatusToCompanyApplication(CompanyApplication compApp,
			String status) {
		if (compApp == null) {
			return false;
		}

		compApp.setStatus(status);
		compApp.store();

		return true;
	}

	@Override
	public boolean sendEmail(String email, String subject, String text) {
		IWMainApplicationSettings settings = IWMainApplication
				.getDefaultIWMainApplication().getSettings();
		if (settings == null) {
			return false;
		}

		String from = settings
				.getProperty(CoreConstants.PROP_SYSTEM_MAIL_FROM_ADDRESS);
		String host = settings
				.getProperty(CoreConstants.PROP_SYSTEM_SMTP_MAILSERVER);
		if (StringUtil.isEmpty(from) || StringUtil.isEmpty(host)) {
			logger.log(Level.WARNING, "Cann't send email from: " + from
					+ " via: " + host + ". Set properties for application!");
			return false;
		}
		try {
			SendMail.send(from, email, null, null, host, subject, text);
		} catch (MessagingException e) {
			logger.log(Level.WARNING, "Error sending mail!", e);
			return false;
		}

		return true;
	}

	private boolean sendMail(Email email, String subject, String text) {
		if (email == null || StringUtil.isEmpty(text)) {
			return false;
		}

		return sendEmail(email.getEmailAddress(), subject, text);
	}

	private IWResourceBundle getResourceBundle() {
		return getResourceBundle(null);
	}

	private IWResourceBundle getResourceBundle(IWContext iwc) {
		return getBundle().getResourceBundle(
				iwc == null ? CoreUtil.getIWContext() : iwc);
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

		return new StringBuilder(getApplicationName(compApp, locale))
				.append(CoreConstants.COLON).append(CoreConstants.SPACE)
				.toString();
	}

	private boolean isUserLogged(IWUserContext iwuc) {
		if (iwuc == null) {
			return false;
		}

		User user = null;
		try {
			user = iwuc.getCurrentUser();
		} catch (NotLoggedOnException e) {
			logger.log(Level.SEVERE, "User is not logged!");
		}

		return user == null ? false : true;
	}

	@Override
	public boolean isCompanyAdministrator(IWContext iwc) {
		try {
			return (isInstitutionAdministration(iwc) || iwc
					.getAccessController().hasRole(
							EgovCompanyConstants.COMPANY_ADMIN_ROLE, iwc)
					&& isUserAccountEnabledForCompanyPortal(iwc));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isCompanyEmployee(IWContext iwc) {
		try {
			return (isCompanyAdministrator(iwc) || iwc.getAccessController()
					.hasRole(EgovCompanyConstants.COMPANY_EMPLOYEE_ROLE, iwc))
					&& isUserAccountEnabledForCompanyPortal(iwc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean isUserAccountEnabledForCompanyPortal(IWContext iwc) {
		User currentUser = null;
		try {
			currentUser = iwc.getCurrentUser();
		} catch (NotLoggedOnException e) {
			e.printStackTrace();
		}
		if (currentUser == null) {
			return false;
		}

		String accountMetadata = currentUser
				.getMetaData(EgovCompanyConstants.USER_LOGIN_METADATA);
		if (StringUtil.isEmpty(accountMetadata)) {
			// By default - no value set that means account is not closed
			return true;
		}

		return Boolean.valueOf(accountMetadata);
	}

	@Override
	public CompanyApplicationHome getCompanyApplicationHome() {
		try {
			return (CompanyApplicationHome) IDOLookup
					.getHome(CompanyApplication.class);
		} catch (RemoteException rme) {
			throw new RuntimeException(rme.getMessage());
		}
	}

	@Override
	public boolean makeUserCompanyAdmin(IWApplicationContext iwac,
			User companyAdmin, Group companyGroup) {
		if (companyAdmin == null || companyGroup == null) {
			return false;
		}

		companyGroup.setModerator(companyAdmin);
		companyGroup.store();

		AccessController accessController = iwac.getIWMainApplication().getAccessController();
		accessController.addRoleToGroup(EgovCompanyConstants.COMPANY_ADMIN_ROLE, companyAdmin, iwac);

		return true;
	}

	@Override
	public boolean makeUserCommonEmployee(IWApplicationContext iwac,
			User companyAdmin, Group company) {
		if (company == null) {
			return false;
		}
		company.setModerator(null);
		company.store();

		AccessController accessController = iwac.getIWMainApplication().getAccessController();
		accessController.removeRoleFromGroup(EgovCompanyConstants.COMPANY_ADMIN_ROLE, Integer.valueOf(companyAdmin.getId()), iwac);

		return true;
	}

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness#createLogginForUser(com.idega.presentation.IWContext,
	 *      com.idega.user.data.User, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public boolean createLogginForUser(IWContext iwc, User user,
			String phoneHome, String phoneWork, String email, String roleKey,
			boolean addToRootCitizenGroup) throws LoginCreateException {
		String password = null;
		LoginTable loginTable = null;
		try {
			UserBusiness userBusiness = getUserBusiness();

			password = LoginDBHandler.getGeneratedPasswordForUser();
			loginTable = LoginDBHandler.createLogin(user, user.getPersonalID(),
					password);
			LoginDBHandler.changeNextTime(loginTable, true);
			userBusiness.updateUserHomePhone(user, phoneHome);
			userBusiness.updateUserWorkPhone(user, phoneWork);
		} catch (RemoteException e) {
			if (loginTable != null) {
				logger.log(Level.SEVERE,
						"Error updating user information on login creating for user : "
								+ user.getId(), e);
				logger.log(Level.SEVERE, "Deleting created login: "
						+ loginTable.getUserLogin());
				LoginDBHandler.deleteLogin(loginTable);
			} else {
				logger.log(Level.SEVERE, "Error creating user login for user: "
						+ user.getId(), e);
			}
			return false;
		}

		if (addToRootCitizenGroup) {
			try {
				Group acceptedCitizens = getCitizenBusiness()
						.getRootAcceptedCitizenGroup();
				acceptedCitizens.addGroup(user,
						IWTimestamp.getTimestampRightNow());
				if (user.getPrimaryGroup() == null) {
					user.setPrimaryGroup(acceptedCitizens);
					user.store();
				}
				AccessController accessController = iwc.getIWMainApplication()
						.getAccessController();
				accessController.addRoleToGroup(roleKey, acceptedCitizens, iwc);
			} catch (Exception e) {
				logger.log(Level.SEVERE,
						"Error updating user group information on login creation for user: "
								+ user.getId(), e);
				if (loginTable != null) {
					logger.log(Level.SEVERE, "Deleting created login: "
							+ loginTable.getUserLogin());
					LoginDBHandler.deleteLogin(loginTable);
				}
				return false;
			}
		}

		sendEmail(
				email,
				getBundle().getLocalizedString("account_details",
						"Account details"),
				getBundle().getLocalizedString("user_name", "User name")
						+ ": "
						+ loginTable.getUserLogin()
						+ "\n"
						+ getBundle()
								.getLocalizedString("password", "Password")
						+ ": " + password);
		return true;
	}

	/**
	 * @see is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness
	 *      #getAvailableApplicationsForUser(com.idega.presentation.IWContext,
	 *      com.idega.user.data.User)
	 */
	private Collection<Application> getAvailableApplicationsForUser(
			IWContext iwc, User user) throws FinderException {
		Collection<Application> allApplications = getApplicationHome()
				.findAllWithAssignedGroups();
		if (ListUtil.isEmpty(allApplications)) {
			return null;
		}

		CompanyPortalBusiness companyPortalBusiness = getCompanyPortalBusiness(iwc);

		Collection<Group> appGroups = null;
		boolean superAdmin = iwc.isSuperAdmin();
		Collection<Application> userApplicationList = new ArrayList<Application>();
		Group companyPortalRootGroup = null;
		try {
			companyPortalRootGroup = companyPortalBusiness
					.getCompanyPortalRootGroup(iwc);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		String companyPortalRootGroupId = companyPortalRootGroup == null ? String
				.valueOf(-1) : companyPortalRootGroup.getId();
		for (Application app : allApplications) {
			boolean appAdded = false;
			appGroups = app.getGroups();
			appGroups = ListUtil.isEmpty(appGroups) ? null
					: new ArrayList<Group>(appGroups);
			if (ListUtil.isEmpty(appGroups)) {
				logger.log(Level.INFO, "Application " + app.getName()
						+ " has no groups!");
			} else {
				for (Group group : appGroups) {
					if (!appAdded
							&& (superAdmin
									|| group.getId().equals(
											companyPortalRootGroupId) || companyPortalBusiness
									.isMemberOfCompany(iwc, group, user))) {
						if (!userApplicationList.contains(app)) {
							userApplicationList.add(app);
						}
						appAdded = true;
					}
				}
			}
		}
		return userApplicationList;
	}

	@Override
	public PrintingService getPrintingService() {
		try {
			return getServiceInstance(PrintingService.class);
		} catch (RemoteException e) {
			throw new IBORuntimeException(e.getMessage());
		}
	}

	private UserBusiness getUserBusiness() throws RemoteException {
		return getServiceInstance(UserBusiness.class);
	}

	private GroupBusiness getGroupBusiness(IWApplicationContext iwac)
			throws RemoteException {
		return getServiceInstance(GroupBusiness.class);
	}

	private CitizenBusiness getCitizenBusiness() throws RemoteException {
		return getServiceInstance(CitizenBusiness.class);
	}

	private Collection<Application> getCommonCompanyPortalServices(IWContext iwc) {
		try {
			return getApplicationHome().findAllByGroups(
					Arrays.asList(getCompanyPortalBusiness(iwc)
							.getCompanyPortalRootGroup(iwc).getId()));
		} catch (Exception e) {
			logger.log(Level.INFO,
					"No services found for root Company Portal group", e);
		}
		return null;
	}

	private boolean addCommonCompanyPortalServices(
			CompanyEmployee compEmployee,
			Collection<Application> rootGroupServices) {
		if (compEmployee == null) {
			return false;
		}

		if (ListUtil.isEmpty(rootGroupServices)) {
			return false;
		}

		boolean needSaveEmployee = false;
		Collection<Application> currentServices = compEmployee.getServices();
		List<Application> allUserApplications = ListUtil
				.isEmpty(currentServices) ? new ArrayList<Application>()
				: new ArrayList<Application>(currentServices);
		for (Application application : rootGroupServices) {
			if (!allUserApplications.contains(application)) {
				needSaveEmployee = true;
				allUserApplications.add(application);
			}
		}

		if (needSaveEmployee) {
			compEmployee.setServices(allUserApplications);
			compEmployee.store();
		}

		return true;
	}

	@Override
	public boolean addCommonCompanyPortalServices(IWContext iwc) {
		CompanyEmployeeHome compEmplHome = null;
		try {
			compEmplHome = (CompanyEmployeeHome) IDOLookup
					.getHome(CompanyEmployee.class);
		} catch (IDOLookupException e) {
			e.printStackTrace();
		}
		if (compEmplHome == null) {
			return false;
		}

		Collection<CompanyEmployee> allEmployees = null;
		try {
			allEmployees = compEmplHome.findAll();
		} catch (FinderException e) {
			e.printStackTrace();
		}
		if (ListUtil.isEmpty(allEmployees)) {
			return false;
		}

		Collection<Application> rootGroupServices = getCommonCompanyPortalServices(iwc);
		for (CompanyEmployee compEmployee : allEmployees) {
			addCommonCompanyPortalServices(compEmployee, rootGroupServices);
		}

		return true;
	}

	@Override
	public Collection<Application> getAssignedServices(IWContext iwc, User user) {
		CompanyEmployeeHome compEmplHome = null;
		try {
			compEmplHome = (CompanyEmployeeHome) IDOLookup
					.getHome(CompanyEmployee.class);
		} catch (IDOLookupException e) {
			e.printStackTrace();
		}
		if (compEmplHome == null) {
			return null;
		}

		CompanyEmployee compEmployee = null;
		try {
			compEmployee = compEmplHome.findByUser(user);
		} catch (FinderException e) {
			logger.log(Level.INFO, "There are no services assigned for user: "
					+ user.getName());
		}
		if (compEmployee == null) {
			return null;
		}

		// TODO: maybe it's OK?
		// addCommonCompanyPortalServices(compEmployee,
		// getCommonCompanyPortalServices(iwc));

		return compEmployee.getServices();
	}

	@Override
	public Collection<Application> getUserApplications(IWContext iwc, User user) {
		Collection<Application> userApplications = null;
		try {
			userApplications = getAvailableApplicationsForUser(iwc, user);
		} catch (FinderException e) {
			e.printStackTrace();
		}

		if (!(iwc.isSuperAdmin() || isInstitutionAdministration(iwc) || isCompanyAdministrator(iwc))) {
			// Common user
			Collection<Application> assignedServices = getAssignedServices(iwc,
					user);
			if (ListUtil.isEmpty(assignedServices)) {
				return userApplications;
			}
		}

		return userApplications;
	}

	@Override
	public CommuneMessageBusiness getMessageBusiness() throws RemoteException {
		return IBOLookup.getServiceInstance(
				IWMainApplication.getDefaultIWApplicationContext(),
				CommuneMessageBusiness.class);
	}

	@Override
	public CompanyApplication storeApplication(IWContext iwc, User admin,
			CompanyType companyType, Company company, User performer)
			throws CreateException, RemoteException {
		try {
			CompanyApplication application = getCompanyApplicationHome()
					.create();

			application.setApplicantUser(admin);
			application.setCompany(company);
			application.setType(companyType);
			application.setCaseCode(getApplicationBusiness(iwc).getCaseCode(
					application.getCaseCodeKey()));

			application.store();

			if (performer != null) {
				changeCaseStatus(application, getCaseStatusOpen(), performer);
			}

			return application;
		} catch (CreateException e) {
			e.printStackTrace();
			return null;
		} catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected ApplicationBusiness getApplicationBusiness(IWContext iwc) {
		try {
			return IBOLookup.getServiceInstance(iwc,
					ApplicationBusiness.class);
		} catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	@Override
	public List<CompanyApplication> getApplicationsByCaseCodesAndStatuses(
			String[] caseCodes, List<String> caseStatuses) {
		if (ArrayUtil.isEmpty(caseCodes)) {
			return null;
		}

		Collection<CompanyApplication> applications = null;
		try {
			applications = getCompanyApplicationHome().findByCaseCodes(
					caseCodes);
		} catch (FinderException e) {
			e.printStackTrace();
		}
		if (ListUtil.isEmpty(applications)) {
			return null;
		}

		if (ListUtil.isEmpty(caseStatuses)) {
			return new ArrayList<CompanyApplication>(applications);
		}

		CaseStatus status = null;
		List<CompanyApplication> filteredApps = new ArrayList<CompanyApplication>();
		for (CompanyApplication compApp : applications) {
			status = compApp.getCaseStatus();
			if (status != null && caseStatuses.contains(status.getStatus())
					&& !filteredApps.contains(compApp)) {
				filteredApps.add(compApp);
			}
		}

		return filteredApps;
	}

	@Override
	public Collection<CompanyApplication> getUnhandledApplications(
			String[] caseCodes) {
		return getApplicationsByCaseCodesAndStatuses(caseCodes,
				Arrays.asList(getStatusesForOpenCases()));
	}

	@Override
	public Collection<CompanyApplication> getApprovedApplications(
			String[] caseCodes) {
		return getApplicationsByCaseCodesAndStatuses(caseCodes,
				Arrays.asList(getStatusesForApprovedCases()));
	}

	@Override
	public Collection<CompanyApplication> getRejectedApplications(
			String[] caseCodes) {
		return getApplicationsByCaseCodesAndStatuses(caseCodes,
				Arrays.asList(getStatusesForRejectedCases()));
	}

	@Override
	public String getApplicationName(Application app, Locale locale) {
		if (app instanceof CompanyApplication) {
			Company comp = ((CompanyApplication) app).getCompany();
			if (comp != null) {
				return comp.getName();
			} else {
				return getResourceBundle().getLocalizedString(
						"company_portal.unknown_application_name", "Unkown");
			}
		}
		return super.getApplicationName(app, locale);
	}

	@Override
	public boolean closeAccount(IWContext iwc, String applicationId) {
		CompanyApplication compApp = getApplication(applicationId);
		if (compApp == null) {
			return false;
		}
		List<User> companyPersons = getCompanyPortalBusiness(iwc)
				.getAllCompanyUsers(compApp);
		if (ListUtil.isEmpty(companyPersons)) {
			return true;
		}

		for (User user : companyPersons) {
			// This will not allow to see Company Portal
			manageUserAccountForCompanyPortal(user, false);
		}
		return true;
	}

	@Override
	public boolean reopenAccount(IWContext iwc, String applicationId) {
		CompanyApplication compApp = getApplication(applicationId);

		return reopenAccount(iwc, compApp);
	}

	@Override
	public boolean reopenAccount(IWContext iwc, CompanyApplication compApp) {
		if (compApp == null) {
			return false;
		}
		List<User> companyPersons = getCompanyPortalBusiness(iwc)
				.getAllCompanyUsers(compApp);
		if (ListUtil.isEmpty(companyPersons)) {
			return true;
		}

		for (User user : companyPersons) {
			// This will allow to see Company Portal
			manageUserAccountForCompanyPortal(user, true);
		}
		return true;
	}

	// TODO: is this right?
	private void manageUserAccountForCompanyPortal(User user,
			boolean enableAccount) {
		if (user.isJuridicalPerson()) {
			// Setting property only for "juridical" persons
			try {
				LoginInfo loginInfo = LoginDBHandler
						.getLoginInfo(LoginDBHandler.getUserLogin(user));
				loginInfo.setAccountEnabled(enableAccount);
				loginInfo.store();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Adding metadata for users that Company Portal account is closed
		user.setMetaData(EgovCompanyConstants.USER_LOGIN_METADATA,
				String.valueOf(enableAccount));
		user.store();
	}

	@Override
	public boolean isAccountOpen(CompanyApplication application) {
		if (application == null) {
			return false;
		}

		User adminUser = application.getAdminUser();
		if (adminUser == null) {
			logger.log(Level.WARNING,
					"Admin user not found for company application: "
							+ application.getName());
			return false;
		}

		try {
			return LoginDBHandler.getLoginInfo(
					LoginDBHandler.getUserLogin(adminUser)).getAccountEnabled();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String generateContract(String applicationId) {
		IWContext iwc = CoreUtil.getIWContext();
		if (iwc == null) {
			return null;
		}

		try {
			Application app = getApplication(applicationId);
			ICFile contract = ((CompanyApplication) app).getContract();

			if (contract == null) {
				Locale locale = iwc.getCurrentLocale();
				contract = createContract(new CompanyContractPrintingContext(
						iwc, app, locale), app, locale);
				((CompanyApplication) app).setContract(contract);
				app.store();
			}

			return contract.getFileUri();
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"Error generating contract for application: "
							+ applicationId, e);
		}

		return getResourceBundle(iwc).getLocalizedString(
				"error_downloading_contract",
				"Sorry, contract can not be downloaded - some error occurred.");
	}

	private ICFile createContract(PrintingContext pcx, Application application,
			Locale locale) throws CreateException {
		try {
			MemoryFileBuffer buffer = new MemoryFileBuffer();
			OutputStream mos = new MemoryOutputStream(buffer);
			InputStream mis = new MemoryInputStream(buffer);

			pcx.setDocumentStream(mos);

			getPrintingService().printDocument(pcx);

			return createFile(pcx.getFileName() != null ? pcx.getFileName()
					: "contract", mis, buffer.length(),
					String.valueOf(application.getPrimaryKey()));
		} catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private ICFile createFile(String fileName, InputStream is, int length,
			String applicationId) throws CreateException {
		try {
			ICFileHome home = (ICFileHome) getIDOHome(ICFile.class);
			ICFile file = home.create();

			if (!fileName.endsWith(".pdf") && !fileName.endsWith(".PDF")) {
				fileName += ".pdf";
			}

			file.setFileValue(null);
			file.setMimeType(MimeTypeUtil.MIME_TYPE_PDF_2);

			file.setName(fileName);
			file.setFileSize(length);

			String fileSlideUri = saveToSlide(file, is, applicationId);
			file.setFileUri(fileSlideUri);

			file.store();
			return file;
		} catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private String saveToSlide(ICFile contractFile, InputStream contractIs,
			String applicationId) {
		try {
			getRepositoryService().uploadFileAndCreateFoldersFromStringAsRoot(
					CONTRACT_SLIDE_PATH, contractFile.getName(), contractIs,
					contractFile.getMimeType());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return new StringBuilder(CoreConstants.WEBDAV_SERVLET_URI)
				.append(CONTRACT_SLIDE_PATH).append(contractFile.getName())
				.toString();
	}

	@Override
	public AdminUser getUser(String personalId) {
		if (StringUtil.isEmpty(personalId)) {
			return null;
		}

		IWContext iwc = CoreUtil.getIWContext();
		if (iwc == null) {
			return null;
		}
		Locale locale = iwc.getCurrentLocale();
		if (locale == null) {
			locale = Locale.ENGLISH;
		}

		UserBusiness userBusiness = null;
		try {
			userBusiness = getUserBusiness();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (userBusiness == null) {
			return null;
		}

		boolean useWS = IWMainApplication.getDefaultIWApplicationContext()
				.getApplicationSettings()
				.getBoolean(USE_WEBSERVICE_FOR_COMPANY_LOOKUP, false);

		User user = null;

		if (useWS) {
			try {
				user = userBusiness.getUser(personalId);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (FinderException e) {
				user = null;
			}

			if (user == null || user.getName() == null
					|| "".equals(user.getName())) {
				UserHolder holder = getSkyrrClient().getUser(personalId);
				if (holder != null) {
					IWTimestamp t = new IWTimestamp(
							SocialSecurityNumber
									.getDateFromSocialSecurityNumber(holder
											.getPersonalID()));

					try {
						user = userBusiness
								.createUserByPersonalIDIfDoesNotExist(
										holder.getName(),
										holder.getPersonalID(), null, t);
						StringBuilder address = new StringBuilder(
								holder.getAddress());
						address.append(";");
						address.append(holder.getPostalCode());
						address.append(" ");
						address.append(";Iceland:is_IS;N/A");
						userBusiness.updateUsersMainAddressByFullAddressString(
								user, address.toString());
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (CreateException e) {
						e.printStackTrace();
					}
				}
			}
		}

		try {
			if (user == null) {
				user = userBusiness.getUser(personalId);
			}

			Name name = new Name(user.getFirstName(), user.getMiddleName(),
					user.getLastName());

			Phone workPhone = null;
			try {
				workPhone = userBusiness.getUsersWorkPhone(user);
			} catch (NoPhoneFoundException e) {
				// No phone found...
			}

			Phone mobilePhone = null;
			try {
				mobilePhone = userBusiness.getUsersMobilePhone(user);
			} catch (NoPhoneFoundException e) {
				// No phone found...
			}

			Email email = null;
			try {
				email = userBusiness.getUsersMainEmail(user);
			} catch (NoEmailFoundException e) {
				// No email found...
			}

			AdminUser adminUser = new AdminUser();
			adminUser.setPK(user.getPrimaryKey().toString());
			adminUser.setPersonalID(user.getPersonalID());
			adminUser.setName(name.getName(locale));
			if (workPhone != null) {
				adminUser.setWorkPhone(workPhone.getNumber());
			}
			if (mobilePhone != null) {
				adminUser.setMobilePhone(mobilePhone.getNumber());
			}
			if (email != null) {
				adminUser.setEmail(email.getEmailAddress());
			}

			return adminUser;
		} catch (FinderException fe) {
			logger.log(Level.INFO, "User was not found by provided ID: "
					+ personalId);
			return null;
		} catch (RemoteException re) {
			re.printStackTrace();
		}

		return null;
	}

	@Override
	public CompanyInfo getCompany(String companyUniqueId, String companyPhone,
			String companyFax, String companyEmail, String companyWebpage,
			String companyBankAccount) {
		CompanyInfo companyInfo = new CompanyInfo();
		companyInfo.setPersonalID(companyUniqueId);

		CompanyBusiness companyBusiness = null;
		try {
			companyBusiness = getServiceInstance(CompanyBusiness.class);
		} catch (IBOLookupException e) {
			e.printStackTrace();
		}
		if (companyBusiness == null) {
			return null;
		}

		boolean useWS = IWMainApplication.getDefaultIWApplicationContext()
				.getApplicationSettings()
				.getBoolean(USE_WEBSERVICE_FOR_COMPANY_LOOKUP, false);

		boolean useIndividualWS = IWMainApplication
				.getDefaultIWApplicationContext().getApplicationSettings()
				.getBoolean(ALLOW_INDIVIDUALS_FOR_COMPANY_LOOKUP, false);

		Company company = null;

		if (useWS) {
			try {
				company = companyBusiness.getCompany(companyUniqueId);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (FinderException e) {
				company = null;
			}

			if (company == null) {
				CompanyHolder holder = getSkyrrClient().getCompany(
						companyUniqueId);
				if (holder != null) {
					try {
						getCompanyRegisterBusiness().updateEntry(
								holder.getPersonalID(), null,
								holder.getPostalCode(), null, null,
								holder.getName(), holder.getAddress(),
								holder.getPersonalID(), "", null,
								holder.getVatNumber(), holder.getAddress(), "",
								null, null, null, null, null, "", null);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else if (useIndividualWS) {
					UserHolder userHolder = getSkyrrClient().getUser(
							companyUniqueId);
					if (userHolder != null) {
						try {
							getCompanyRegisterBusiness().updateEntry(
									userHolder.getPersonalID(), null,
									userHolder.getPostalCode(), null, null,
									userHolder.getName(),
									userHolder.getAddress(),
									userHolder.getPersonalID(), "", null, null,
									userHolder.getAddress(), "", null, null,
									null, null, null, "", null);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		try {
			if (company == null) {
				company = companyBusiness.getCompany(companyUniqueId);
			}
			Address address = company.getAddress();
			PostalCode code = address != null ? address.getPostalCode() : null;
			Phone phone = company.getPhone();
			Phone fax = company.getFax();
			Email email = company.getEmail();

			companyInfo.setPK(company.getPrimaryKey().toString());
			companyInfo.setName(company.getName());
			if (address != null) {
				companyInfo.setAddress(address.getStreetAddress());
			}
			if (code != null) {
				companyInfo.setPostalCode(code.getPostalCode());
				companyInfo.setCity(code.getName());
			}

			if (companyPhone != null && companyPhone.length() > 0) {
				companyInfo.setPhone(companyPhone);
			} else if (phone != null) {
				companyInfo.setPhone(phone.getNumber());
			}

			if (companyFax != null && companyFax.length() > 0) {
				companyInfo.setFax(companyFax);
			} else if (fax != null) {
				companyInfo.setFax(fax.getNumber());
			}

			if (companyEmail != null && companyEmail.length() > 0) {
				companyInfo.setEmail(companyEmail);
			} else if (email != null) {
				companyInfo.setEmail(email.getEmailAddress());
			}

			if (companyWebpage != null && companyWebpage.length() > 0) {
				companyInfo.setWebPage(companyWebpage);
			} else {
				companyInfo.setWebPage(company.getWebPage());
			}

			if (companyBankAccount != null && companyBankAccount.length() > 0) {
				companyInfo.setBankAccount(companyBankAccount);
			} else {
				companyInfo.setBankAccount(company.getBankAccount());
			}
		} catch (FinderException fe) {
			logger.log(Level.INFO, "Company was not found by provided ID: "
					+ companyUniqueId);
			return null;
		} catch (RemoteException re) {
			re.printStackTrace();
			return null;
		}

		return companyInfo;
	}

	private CompanyPortalBusiness getCompanyPortalBusiness(
			IWApplicationContext iwac) {
		try {
			return ELUtil.getInstance().getBean(
					CompanyPortalBusiness.SPRING_BEAN_IDENTIFIER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private CompanyRegisterBusiness getCompanyRegisterBusiness() {
		try {
			return getServiceInstance(CompanyRegisterBusiness.class);
		} catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	@Override
	public boolean isInstitutionAdministration(IWContext iwc) {
		return isUserLogged(iwc)
				&& (iwc.isSuperAdmin() || iwc.getAccessController().hasRole(
						EgovCompanyConstants.COMPANY_SUPER_ADMIN_ROLE, iwc));
	}

	@Override
	public SkyrrClient getSkyrrClient() {
		if (skyrrClient == null) {
			ELUtil.getInstance().autowire(this);
		}
		return skyrrClient;
	}

	@Override
	public void handle(Callback[] callbacks)
			throws UnsupportedCallbackException {
		String userId = getIWApplicationContext().getApplicationSettings()
				.getProperty(BANK_SENDER_USER_ID);
		String passwd = getIWApplicationContext().getApplicationSettings()
				.getProperty(BANK_SENDER_USER_PASSWORD);

		for (int i = 0; i < callbacks.length; i++) {
			if (callbacks[i] instanceof WSPasswordCallback) {
				WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
				if (pc.getIdentifer().equals(userId)) {
					pc.setPassword(passwd);
				}
			} else {
				throw new UnsupportedCallbackException(callbacks[i],
						"Unrecognized Callback");
			}
		}
	}

}