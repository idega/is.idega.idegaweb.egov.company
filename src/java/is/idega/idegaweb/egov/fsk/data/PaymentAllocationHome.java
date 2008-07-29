package is.idega.idegaweb.egov.fsk.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import java.sql.Date;
import com.idega.user.data.User;

public interface PaymentAllocationHome extends IDOHome {

	public PaymentAllocation create() throws CreateException;

	public PaymentAllocation findByPrimaryKey(Object pk) throws FinderException;

	public Collection findByUser(User user) throws FinderException;

	public Collection findByUser(Season season, User user) throws FinderException;

	public Collection findByUser(Course course, User user) throws FinderException;

	public PaymentAllocation findLatestByUser(Course course, User user) throws FinderException;

	public Collection findByUser(Period period, User user) throws IDOException, FinderException;

	public Collection findPaymentsByCriteria(Collection courses, Date fromDate, Date toDate, boolean hasDateSet) throws FinderException;

	public Collection findCostsByCriteria(Collection courses, Date fromDate, Date toDate, boolean hasDateSet) throws FinderException;

	public int getTotalAmount(Season season, User user) throws IDOException;

	public int getAmount(Course course) throws IDOException;

	public int getAmount(Course course, Date fromDate, Date toDate) throws IDOException;

	public int getAmount(Course course, Date fromDate, Date toDate, boolean costMarked) throws IDOException;

	public int getAmount(Course course, User user) throws IDOException;

	public int getAmount(Course course, User user, Date fromDate, Date toDate, Boolean costMarked) throws IDOException;

	public int getAmount(Collection courses, Date fromDate, Date toDate, boolean markedEntries) throws IDOException;

	public int getCount(Collection courses, Date fromDate, Date toDate, boolean markedEntries) throws IDOException;

	public int getNumberOfAllocations(Period period, User user) throws IDOException;

	public int getNumberOfAllocations(Allocation allocation) throws IDOException;

	public int getNumberOfCostMarkedAllocations(Collection courses, User user) throws IDOException;
}