package is.idega.idegaweb.egov.fsk.data;


import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import com.idega.user.data.User;

public interface ParticipantDiscountHome extends IDOHome {

	public ParticipantDiscount create() throws CreateException;

	public ParticipantDiscount findByPrimaryKey(Object pk) throws FinderException;

	public ParticipantDiscount findByParticipantAndCourse(User user, Course course) throws FinderException;
}