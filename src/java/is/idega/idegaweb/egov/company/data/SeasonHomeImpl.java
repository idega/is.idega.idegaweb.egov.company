package is.idega.idegaweb.egov.company.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.sql.Date;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class SeasonHomeImpl extends IDOFactory implements SeasonHome {

	public Class getEntityInterfaceClass() {
		return Season.class;
	}

	public Season create() throws CreateException {
		return (Season) super.createIDO();
	}

	public Season findByPrimaryKey(Object pk) throws FinderException {
		return (Season) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAll() throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((SeasonBMPBean) entity).ejbFindAll();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Season findByDate(Date date) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((SeasonBMPBean) entity).ejbFindByDate(date);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public int getNumberOfSeasonsInPeriod(Object seasonPK, Date startDate, Date endDate) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((SeasonBMPBean) entity).ejbHomeGetNumberOfSeasonsInPeriod(seasonPK, startDate, endDate);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}
}