package is.idega.idegaweb.egov.company.data;


import com.idega.user.data.User;
import java.sql.Timestamp;
import com.idega.data.IDOEntity;

public interface PaymentAllocation extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#getAllocation
	 */
	public Allocation getAllocation();

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#getUser
	 */
	public User getUser();

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#getCourse
	 */
	public Course getCourse();

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#getAmount
	 */
	public float getAmount();

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#isLocked
	 */
	public boolean isLocked();
	
	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#isTransfer
	 */
	public boolean isTransfer();

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#getEntryDate
	 */
	public Timestamp getEntryDate();

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#getPaymentDate
	 */
	public Timestamp getPaymentDate();

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#getCostDate
	 */
	public Timestamp getCostDate();

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#getCostAmount
	 */
	public float getCostAmount();

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#setAllocation
	 */
	public void setAllocation(Allocation allocation);

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#setUser
	 */
	public void setUser(User user);

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#setCourse
	 */
	public void setCourse(Course course);

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#setAmount
	 */
	public void setAmount(float amount);

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#setLocked
	 */
	public void setLocked(boolean locked);
	
	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#setTransfer
	 */
	public void setTransfer(boolean transfer);

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#setEntryDate
	 */
	public void setEntryDate(Timestamp date);

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#setPaymentDate
	 */
	public void setPaymentDate(Timestamp date);

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#setCostDate
	 */
	public void setCostDate(Timestamp date);

	/**
	 * @see is.idega.idegaweb.egov.company.data.PaymentAllocationBMPBean#setCostAmount
	 */
	public void setCostAmount(float amount);
}