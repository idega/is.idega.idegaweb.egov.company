package is.idega.idegaweb.egov.company.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import java.sql.Date;

public interface SeasonHome extends IDOHome {

	public Season create() throws CreateException;

	public Season findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAll() throws FinderException;

	public Season findByDate(Date date) throws FinderException;

	public int getNumberOfSeasonsInPeriod(Object seasonPK, Date startDate, Date endDate) throws IDOException;
}