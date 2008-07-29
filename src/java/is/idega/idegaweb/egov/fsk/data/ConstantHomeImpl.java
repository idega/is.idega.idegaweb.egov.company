package is.idega.idegaweb.egov.fsk.data;


import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class ConstantHomeImpl extends IDOFactory implements ConstantHome {

	public Class getEntityInterfaceClass() {
		return Constant.class;
	}

	public Constant create() throws CreateException {
		return (Constant) super.createIDO();
	}

	public Constant findByPrimaryKey(Object pk) throws FinderException {
		return (Constant) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAll(Period period) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ConstantBMPBean) entity).ejbFindAll(period);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Constant findByType(Period period, String type) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ConstantBMPBean) entity).ejbFindByType(period, type);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}