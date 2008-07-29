package is.idega.idegaweb.egov.company.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import java.sql.Date;

public interface PeriodHome extends IDOHome {

	public Period create() throws CreateException;

	public Period findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAll() throws FinderException;

	public Collection findAllBySeason(Season season) throws FinderException;

	public Collection findAllBetween(Date startDate, Date endDate) throws FinderException;

	public int getNumberOfPeriodsInPeriod(Object periodPK, Season season, Date startDate, Date endDate) throws IDOException;
}