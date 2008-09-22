package is.idega.idegaweb.egov.company.data;

import java.util.Collection;

import javax.ejb.FinderException;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 26, 2008 by Author: Anton 
 *
 */

public interface EmployeeFieldHome extends com.idega.data.IDOHome {
	 public EmployeeField findByPrimaryKey(Object pk) throws FinderException;
	 public Collection<EmployeeField> findByMultiplePrimaryKey(Collection<String> pk) throws FinderException;
	 public EmployeeField create() throws javax.ejb.CreateException;
	 public EmployeeField findByServiceCode(java.lang.String p0)throws javax.ejb.FinderException;
	 public Collection<EmployeeField> findAll() throws FinderException;
}
