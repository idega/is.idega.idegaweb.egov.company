package is.idega.idegaweb.egov.company.data;

import javax.ejb.FinderException;

import com.idega.company.data.Company;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 20, 2008 by Author: Anton 
 *
 */

public interface CompanyApplicationHome extends com.idega.data.IDOHome{
	 public CompanyApplication create() throws javax.ejb.CreateException;
	 public CompanyApplication findByPrimaryKey(Object pk) throws javax.ejb.FinderException;
	 public CompanyApplication findByPersonalID(java.lang.String p0)throws javax.ejb.FinderException;
	 public CompanyApplication findByCompany(Company company) throws FinderException;
}
