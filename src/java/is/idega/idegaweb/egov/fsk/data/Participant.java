package is.idega.idegaweb.egov.fsk.data;


import com.idega.user.data.User;
import java.sql.Timestamp;
import com.idega.data.IDOEntity;

public interface Participant extends IDOEntity, User {

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantBMPBean#setCourse
	 */
	public void setCourse(Course course);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantBMPBean#getCourse
	 */
	public Course getCourse();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantBMPBean#setActive
	 */
	public void setActive(Course course, boolean active);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantBMPBean#isActive
	 */
	public boolean isActive(Course course);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantBMPBean#setCost
	 */
	public void setCost(Division division, Period period, float cost);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantBMPBean#getCost
	 */
	public float getCost(Division division, Period period);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantBMPBean#setCostDate
	 */
	public void setCostDate(Division division, Period period, Timestamp timestamp);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantBMPBean#getCostDate
	 */
	public Timestamp getCostDate(Division division, Period period);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantBMPBean#hasCostMarking
	 */
	public boolean hasCostMarking(Division division, Period period);
}