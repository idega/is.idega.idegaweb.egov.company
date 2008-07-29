package is.idega.idegaweb.egov.company.data;


import java.sql.Date;
import com.idega.data.IDOEntity;

public interface Season extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.company.data.SeasonBMPBean#getName
	 */
	public String getName();

	/**
	 * @see is.idega.idegaweb.egov.company.data.SeasonBMPBean#getStartDate
	 */
	public Date getStartDate();

	/**
	 * @see is.idega.idegaweb.egov.company.data.SeasonBMPBean#getEndDate
	 */
	public Date getEndDate();

	/**
	 * @see is.idega.idegaweb.egov.company.data.SeasonBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see is.idega.idegaweb.egov.company.data.SeasonBMPBean#setStartDate
	 */
	public void setStartDate(Date date);

	/**
	 * @see is.idega.idegaweb.egov.company.data.SeasonBMPBean#setEndDate
	 */
	public void setEndDate(Date date);
}