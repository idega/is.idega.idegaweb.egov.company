package is.idega.idegaweb.egov.company.data;


import java.sql.Date;
import com.idega.data.IDOEntity;

public interface Constant extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.company.data.ConstantBMPBean#getPeriod
	 */
	public Period getPeriod();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ConstantBMPBean#getType
	 */
	public String getType();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ConstantBMPBean#getStartDate
	 */
	public Date getStartDate();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ConstantBMPBean#getEndDate
	 */
	public Date getEndDate();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ConstantBMPBean#setPeriod
	 */
	public void setPeriod(Period period);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ConstantBMPBean#setType
	 */
	public void setType(String type);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ConstantBMPBean#setStartDate
	 */
	public void setStartDate(Date date);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ConstantBMPBean#setEndDate
	 */
	public void setEndDate(Date date);
}