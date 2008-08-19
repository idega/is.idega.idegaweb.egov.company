package is.idega.idegaweb.egov.company.business;

import com.idega.presentation.IWContext;

import is.idega.idegaweb.egov.application.business.ApplicationBusiness;
import is.idega.idegaweb.egov.company.data.CompanyApplication;

public interface CompanyApplicationBusiness extends ApplicationBusiness {

	public static final String SPRING_BEAN_IDENTIFIER = "companyApplicationBusiness";
	
	public CompanyApplication getApplication(String applicationId);
	
	public boolean approveApplication(String applicationId);

	public boolean rejectApplication(String applicationId, String explanationText);
	
	public boolean isCompanyAdministrator(IWContext iwc);
	
	public boolean isCompanyEmployee(IWContext iwc);
}