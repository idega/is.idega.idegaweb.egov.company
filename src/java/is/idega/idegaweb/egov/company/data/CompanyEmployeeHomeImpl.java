package is.idega.idegaweb.egov.company.data;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOEntity;
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

public class CompanyEmployeeHomeImpl extends com.idega.data.IDOFactory implements CompanyEmployeeHome {

	private static final long serialVersionUID = -774105305864586052L;

	@Override
	protected Class getEntityInterfaceClass() {
		return CompanyEmployee.class;
	}

	public CompanyEmployee create() throws CreateException {
		return (CompanyEmployee) super.createIDO();
	}
	
	public CompanyEmployee findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
		return (CompanyEmployee) super.findByPrimaryKeyIDO(pk);
	}

	public CompanyEmployee findByUser(User user) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CompanyEmployeeBMPBean) entity).ejbFindByUser(user);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
	
	public Collection findAll() throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CompanyEmployeeBMPBean) entity).ejbFindAll();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection<CompanyEmployee> findByGroup(Group userGroup)
			throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CompanyEmployeeBMPBean) entity).ejbFindByGroup(userGroup);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

}
