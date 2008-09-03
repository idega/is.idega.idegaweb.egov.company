package is.idega.idegaweb.egov.company.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.user.data.Group;
import com.idega.user.data.User;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 28, 2008 by Author: Anton 
 *
 */

public interface CompanyEmployeeHome  extends com.idega.data.IDOHome {
	 public CompanyEmployee create() throws javax.ejb.CreateException;
	 public CompanyEmployee findByPrimaryKey(Object pk) throws FinderException;
	 public CompanyEmployee findByUser(User user) throws javax.ejb.FinderException;
	 public Collection<CompanyEmployee> findByGroup(Group userGroup) throws javax.ejb.FinderException;
	 public Collection<CompanyEmployee> findAll() throws FinderException;
}
