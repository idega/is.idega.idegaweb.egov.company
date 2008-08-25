package is.idega.idegaweb.egov.company.data;

import com.idega.block.process.data.Case;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.user.data.User;

import is.idega.idegaweb.egov.application.data.Application;

public interface CompanyApplication extends Case, Application {
	
	public Company getCompany();
	
	public void setCompany(Company company);
	
	public User getAdminUser();
	
	public void setAdminUser(User user);
	
	public String getAppType();
	
	public CompanyType getType();
	
	public void setType(CompanyType type);
}
