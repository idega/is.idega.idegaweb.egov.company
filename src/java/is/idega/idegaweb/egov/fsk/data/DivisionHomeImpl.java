package is.idega.idegaweb.egov.fsk.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.IDOEntity;
import com.idega.user.data.GroupHomeImpl;

public class DivisionHomeImpl extends GroupHomeImpl implements DivisionHome {

	public Class getEntityInterfaceClass() {
		return Division.class;
	}

	public Collection findAll() throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((DivisionBMPBean) entity).ejbFindAll();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}