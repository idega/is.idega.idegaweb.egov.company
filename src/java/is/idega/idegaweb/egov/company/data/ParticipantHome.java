package is.idega.idegaweb.egov.company.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import java.sql.Date;

public interface ParticipantHome extends IDOHome {

	public Participant create() throws CreateException;

	public Participant findByPrimaryKey(Object pk) throws FinderException;

	public int getCount(Collection courses, Division division, Period period, Date fromDate, Date toDate, boolean markedEntries) throws IDOException;
}