package is.idega.idegaweb.egov.company.data;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

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

public class EmployeeFieldHomeImpl extends com.idega.data.IDOFactory implements EmployeeFieldHome {

	private static final long serialVersionUID = 9169189257730605528L;

	public EmployeeField create() throws CreateException {
		return (EmployeeField) super.createIDO();
	}

	public EmployeeField findByServiceCode(String p0) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((EmployeeFieldBMPBean)entity).ejbFindByServiceCode(p0);
		this.idoCheckInPooledEntity(entity);
		return (EmployeeField) super.findByPrimaryKeyIDO(pk);
	}
	
	public EmployeeField findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
		return (EmployeeField) super.findByPrimaryKeyIDO(pk);
	}
	
	public Collection<EmployeeField> findByMultiplePrimaryKey(Collection pks) throws javax.ejb.FinderException{
		Collection<EmployeeField> fields = new ArrayList<EmployeeField>();
		
		for(String pk : (Collection<String>)pks) {
			fields.add(findByPrimaryKey(pk));
		}
		
		return fields;
	}	

	@Override
	protected Class getEntityInterfaceClass() {
		return EmployeeField.class;
	}
	
	public Collection<EmployeeField> findAll() throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((EmployeeFieldBMPBean) entity).ejbFindAll();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

}
