package is.idega.idegaweb.egov.fsk.data;


import com.idega.user.data.User;
import com.idega.data.IDOEntity;

public interface ParticipantDiscount extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantDiscountBMPBean#getParticipant
	 */
	public Participant getParticipant();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantDiscountBMPBean#getCourse
	 */
	public Course getCourse();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantDiscountBMPBean#getDiscount
	 */
	public float getDiscount();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantDiscountBMPBean#isPercentage
	 */
	public boolean isPercentage();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantDiscountBMPBean#isAmount
	 */
	public boolean isAmount();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantDiscountBMPBean#setParticipant
	 */
	public void setParticipant(User user);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantDiscountBMPBean#setCourse
	 */
	public void setCourse(Course course);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantDiscountBMPBean#setDiscount
	 */
	public void setDiscount(float discount);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantDiscountBMPBean#setAsPercentage
	 */
	public void setAsPercentage();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ParticipantDiscountBMPBean#setAsAmount
	 */
	public void setAsAmount();
}