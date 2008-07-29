package is.idega.idegaweb.egov.fsk.data;


import java.sql.Date;
import com.idega.data.IDOEntity;

public interface Period extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#getSeason
	 */
	public Season getSeason();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#getName
	 */
	public String getName();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#getStartDate
	 */
	public Date getStartDate();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#getEndDate
	 */
	public Date getEndDate();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#getMinimumWeeks
	 */
	public int getMinimumWeeks();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#getCostAmount
	 */
	public float getCostAmount();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#setSeason
	 */
	public void setSeason(Season season);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#setStartDate
	 */
	public void setStartDate(Date date);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#setEndDate
	 */
	public void setEndDate(Date date);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#setMinimumWeeks
	 */
	public void setMinimumWeeks(int weeks);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.PeriodBMPBean#setCostAmount
	 */
	public void setCostAmount(float amount);
}