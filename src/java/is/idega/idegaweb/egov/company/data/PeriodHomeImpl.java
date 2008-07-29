package is.idega.idegaweb.egov.company.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.sql.Date;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class PeriodHomeImpl extends IDOFactory implements PeriodHome {

	public Class getEntityInterfaceClass() {
		return Period.class;
	}

	public Period create() throws CreateException {
		return (Period) super.createIDO();
	}

	public Period findByPrimaryKey(Object pk) throws FinderException {
		return (Period) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAll() throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PeriodBMPBean) entity).ejbFindAll();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllBySeason(Season season) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PeriodBMPBean) entity).ejbFindAllBySeason(season);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllBetween(Date startDate, Date endDate) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PeriodBMPBean) entity).ejbFindAllBetween(startDate, endDate);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public int getNumberOfPeriodsInPeriod(Object periodPK, Season season, Date startDate, Date endDate) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PeriodBMPBean) entity).ejbHomeGetNumberOfPeriodsInPeriod(periodPK, season, startDate, endDate);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}
}