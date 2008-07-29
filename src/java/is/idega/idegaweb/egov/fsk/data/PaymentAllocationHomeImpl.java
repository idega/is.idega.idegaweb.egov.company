package is.idega.idegaweb.egov.fsk.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.sql.Date;
import com.idega.user.data.User;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class PaymentAllocationHomeImpl extends IDOFactory implements PaymentAllocationHome {

	public Class getEntityInterfaceClass() {
		return PaymentAllocation.class;
	}

	public PaymentAllocation create() throws CreateException {
		return (PaymentAllocation) super.createIDO();
	}

	public PaymentAllocation findByPrimaryKey(Object pk) throws FinderException {
		return (PaymentAllocation) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findByUser(User user) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PaymentAllocationBMPBean) entity).ejbFindByUser(user);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findByUser(Season season, User user) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PaymentAllocationBMPBean) entity).ejbFindByUser(season, user);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findByUser(Course course, User user) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PaymentAllocationBMPBean) entity).ejbFindByUser(course, user);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public PaymentAllocation findLatestByUser(Course course, User user) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((PaymentAllocationBMPBean) entity).ejbFindLatestByUser(course, user);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public Collection findByUser(Period period, User user) throws IDOException, FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PaymentAllocationBMPBean) entity).ejbFindByUser(period, user);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findPaymentsByCriteria(Collection courses, Date fromDate, Date toDate, boolean hasDateSet) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PaymentAllocationBMPBean) entity).ejbFindPaymentsByCriteria(courses, fromDate, toDate, hasDateSet);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findCostsByCriteria(Collection courses, Date fromDate, Date toDate, boolean hasDateSet) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((PaymentAllocationBMPBean) entity).ejbFindCostsByCriteria(courses, fromDate, toDate, hasDateSet);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public int getTotalAmount(Season season, User user) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetTotalAmount(season, user);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getAmount(Course course) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetAmount(course);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getAmount(Course course, Date fromDate, Date toDate) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetAmount(course, fromDate, toDate);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getAmount(Course course, Date fromDate, Date toDate, boolean costMarked) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetAmount(course, fromDate, toDate, costMarked);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getAmount(Course course, User user) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetAmount(course, user);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getAmount(Course course, User user, Date fromDate, Date toDate, Boolean costMarked) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetAmount(course, user, fromDate, toDate, costMarked);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getAmount(Collection courses, Date fromDate, Date toDate, boolean markedEntries) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetAmount(courses, fromDate, toDate, markedEntries);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getCount(Collection courses, Date fromDate, Date toDate, boolean markedEntries) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetCount(courses, fromDate, toDate, markedEntries);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getNumberOfAllocations(Period period, User user) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetNumberOfAllocations(period, user);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getNumberOfAllocations(Allocation allocation) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetNumberOfAllocations(allocation);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}

	public int getNumberOfCostMarkedAllocations(Collection courses, User user) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((PaymentAllocationBMPBean) entity).ejbHomeGetNumberOfCostMarkedAllocations(courses, user);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}
}