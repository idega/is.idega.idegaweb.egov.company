package is.idega.idegaweb.egov.company.data;

import is.idega.idegaweb.egov.application.data.Application;

import java.util.Collection;

import com.idega.data.IDOEntity;
import com.idega.user.data.User;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 27, 2008 by Author: Anton 
 *
 */

public interface CompanyEmployee extends IDOEntity {
	public User getUser();
	
	public void setUser(User user);
	
	public Collection<EmployeeField> getFieldsInRvk();
	
	public Collection<Integer> getFieldsInRvkPKs();
	
	public void setFieldsInRvk(Collection<EmployeeField> fields);
	
	public Collection<Application> getServices();
	
	public Collection<Integer> getServicesPKs();
	
	public void setServices(Collection<Application> fields);
	
	public boolean isCompanyAdministrator();
	
	public void setCompanyAdministrator(boolean companyAdmin);
	
	public void removeAllFields();
	
	public void removeAllServices();	
}
