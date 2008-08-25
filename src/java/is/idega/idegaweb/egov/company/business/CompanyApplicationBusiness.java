package is.idega.idegaweb.egov.company.business;

import is.idega.idegaweb.egov.application.business.ApplicationBusiness;
import is.idega.idegaweb.egov.company.data.CompanyApplication;
import is.idega.idegaweb.egov.company.data.CompanyApplicationHome;

import com.idega.company.data.Company;
import com.idega.presentation.IWContext;

public interface CompanyApplicationBusiness extends ApplicationBusiness {

	public static final String SPRING_BEAN_IDENTIFIER = "companyApplicationBusiness";
	
	public CompanyApplication getApplication(String applicationId);
	
	public CompanyApplication getApplication(Company company);
	
	public boolean approveApplication(String applicationId);

	public boolean rejectApplication(String applicationId, String explanationText);
	
	public boolean isCompanyAdministrator(IWContext iwc);
	
	public boolean isCompanyEmployee(IWContext iwc);
	
	public CompanyApplicationHome getCompanyApplicationHome();
}
