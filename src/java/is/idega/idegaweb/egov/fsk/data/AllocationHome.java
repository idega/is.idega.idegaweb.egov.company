package is.idega.idegaweb.egov.fsk.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;

public interface AllocationHome extends IDOHome {

	public Allocation create() throws CreateException;

	public Allocation findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAll() throws FinderException;

	public Collection findAll(Season season) throws FinderException;

	public Allocation findByBirthyear(Season season, int year) throws FinderException;

	public int getNumberOfAllocationsInPeriod(Object allocationPK, Season season, int birthyearFrom, int birthyearTo) throws IDOException;
}