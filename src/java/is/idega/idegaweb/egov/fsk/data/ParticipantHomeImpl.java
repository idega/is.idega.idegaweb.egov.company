package is.idega.idegaweb.egov.fsk.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.sql.Date;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class ParticipantHomeImpl extends IDOFactory implements ParticipantHome {

	public Class getEntityInterfaceClass() {
		return Participant.class;
	}

	public Participant create() throws CreateException {
		return (Participant) super.createIDO();
	}

	public Participant findByPrimaryKey(Object pk) throws FinderException {
		return (Participant) super.findByPrimaryKeyIDO(pk);
	}

	public int getCount(Collection courses, Division division, Period period, Date fromDate, Date toDate, boolean markedEntries) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((ParticipantBMPBean) entity).ejbHomeGetCount(courses, division, period, fromDate, toDate, markedEntries);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}
}