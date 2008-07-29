package is.idega.idegaweb.egov.fsk.data;


import java.sql.Date;
import com.idega.data.IDOEntity;

public interface Allocation extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.AllocationBMPBean#getSeason
	 */
	public Season getSeason();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.AllocationBMPBean#getCreationDate
	 */
	public Date getCreationDate();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.AllocationBMPBean#getAmount
	 */
	public float getAmount();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.AllocationBMPBean#getBirthyearFrom
	 */
	public int getBirthyearFrom();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.AllocationBMPBean#getBirthyearTo
	 */
	public int getBirthyearTo();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.AllocationBMPBean#setSeason
	 */
	public void setSeason(Season season);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.AllocationBMPBean#setCreationDate
	 */
	public void setCreationDate(Date date);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.AllocationBMPBean#setAmount
	 */
	public void setAmount(float amount);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.AllocationBMPBean#setBirthyearFrom
	 */
	public void setBirthyearFrom(int year);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.AllocationBMPBean#setBirthyearTo
	 */
	public void setBirthyearTo(int year);
}