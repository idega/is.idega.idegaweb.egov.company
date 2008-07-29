package is.idega.idegaweb.egov.company.data;


import com.idega.user.data.User;
import com.idega.data.IDOEntity;

public interface ParticipantDiscount extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.company.data.ParticipantDiscountBMPBean#getParticipant
	 */
	public Participant getParticipant();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ParticipantDiscountBMPBean#getCourse
	 */
	public Course getCourse();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ParticipantDiscountBMPBean#getDiscount
	 */
	public float getDiscount();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ParticipantDiscountBMPBean#isPercentage
	 */
	public boolean isPercentage();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ParticipantDiscountBMPBean#isAmount
	 */
	public boolean isAmount();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ParticipantDiscountBMPBean#setParticipant
	 */
	public void setParticipant(User user);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ParticipantDiscountBMPBean#setCourse
	 */
	public void setCourse(Course course);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ParticipantDiscountBMPBean#setDiscount
	 */
	public void setDiscount(float discount);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ParticipantDiscountBMPBean#setAsPercentage
	 */
	public void setAsPercentage();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ParticipantDiscountBMPBean#setAsAmount
	 */
	public void setAsAmount();
}