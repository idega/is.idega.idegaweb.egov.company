package is.idega.idegaweb.egov.fsk.data;


import java.sql.Date;
import com.idega.data.IDOEntity;

public interface Season extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.SeasonBMPBean#getName
	 */
	public String getName();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.SeasonBMPBean#getStartDate
	 */
	public Date getStartDate();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.SeasonBMPBean#getEndDate
	 */
	public Date getEndDate();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.SeasonBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.SeasonBMPBean#setStartDate
	 */
	public void setStartDate(Date date);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.SeasonBMPBean#setEndDate
	 */
	public void setEndDate(Date date);
}