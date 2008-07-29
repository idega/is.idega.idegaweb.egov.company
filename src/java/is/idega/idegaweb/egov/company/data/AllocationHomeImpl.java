package is.idega.idegaweb.egov.company.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class AllocationHomeImpl extends IDOFactory implements AllocationHome {

	public Class getEntityInterfaceClass() {
		return Allocation.class;
	}

	public Allocation create() throws CreateException {
		return (Allocation) super.createIDO();
	}

	public Allocation findByPrimaryKey(Object pk) throws FinderException {
		return (Allocation) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAll() throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((AllocationBMPBean) entity).ejbFindAll();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAll(Season season) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((AllocationBMPBean) entity).ejbFindAll(season);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Allocation findByBirthyear(Season season, int year) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((AllocationBMPBean) entity).ejbFindByBirthyear(season, year);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public int getNumberOfAllocationsInPeriod(Object allocationPK, Season season, int birthyearFrom, int birthyearTo) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((AllocationBMPBean) entity).ejbHomeGetNumberOfAllocationsInPeriod(allocationPK, season, birthyearFrom, birthyearTo);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}
}