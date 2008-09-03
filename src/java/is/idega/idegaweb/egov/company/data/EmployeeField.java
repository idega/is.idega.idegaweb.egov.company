package is.idega.idegaweb.egov.company.data;

import com.idega.data.IDOEntity;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 26, 2008 by Author: Anton 
 *
 */

public interface EmployeeField extends IDOEntity {
	public String getServiceCode();
	
	public void setServiceCode(String serviceCode);
	
	public String getServiceDescription();
	
	public void setServiceDescription(String serviceDesription);
}
