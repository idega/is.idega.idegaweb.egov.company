package is.idega.idegaweb.egov.company.data;


import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.company.data.CompanyType;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class DivisionNameHomeImpl extends IDOFactory implements DivisionNameHome {

	public Class getEntityInterfaceClass() {
		return DivisionName.class;
	}

	public DivisionName create() throws CreateException {
		return (DivisionName) super.createIDO();
	}

	public DivisionName findByPrimaryKey(Object pk) throws FinderException {
		return (DivisionName) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAllByType(CompanyType type) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((DivisionNameBMPBean) entity).ejbFindAllByType(type);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public DivisionName findByNameAndType(String name, CompanyType type) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((DivisionNameBMPBean) entity).ejbFindByNameAndType(name, type);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}