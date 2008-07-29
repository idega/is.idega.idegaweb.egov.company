package is.idega.idegaweb.egov.fsk.data;


import com.idega.user.data.Group;
import java.sql.Date;
import com.idega.data.IDOStoreException;
import com.idega.company.data.Company;
import com.idega.data.IDOEntity;

public interface Course extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getGroup
	 */
	public Group getGroup();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getPeriod
	 */
	public Period getPeriod();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getCompany
	 */
	public Company getCompany();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getName
	 */
	public String getName();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getPrice
	 */
	public float getPrice();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getCost
	 */
	public float getCost();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getStartDate
	 */
	public Date getStartDate();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getEndDate
	 */
	public Date getEndDate();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getNumberOfHours
	 */
	public int getNumberOfHours();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#isApproved
	 */
	public boolean isApproved();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#isClosed
	 */
	public boolean isClosed();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getMaxMale
	 */
	public int getMaxMale();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#getMaxFemale
	 */
	public int getMaxFemale();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setPeriod
	 */
	public void setPeriod(Period period);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setCompany
	 */
	public void setCompany(Company company);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setPrice
	 */
	public void setPrice(float price);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setCost
	 */
	public void setCost(float cost);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setStartDate
	 */
	public void setStartDate(Date date);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setEndDate
	 */
	public void setEndDate(Date date);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setNumberOfHours
	 */
	public void setNumberOfHours(int numberOfHours);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setApproved
	 */
	public void setApproved(boolean isApproved);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setClosed
	 */
	public void setClosed(boolean isClosed);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setMaxMale
	 */
	public void setMaxMale(int max);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#setMaxFemale
	 */
	public void setMaxFemale(int max);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.CourseBMPBean#store
	 */
	public void store() throws IDOStoreException;
}