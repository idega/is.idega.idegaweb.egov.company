package is.idega.idegaweb.egov.company.data;


import com.idega.user.data.Group;
import java.sql.Date;
import com.idega.data.IDOStoreException;
import com.idega.company.data.Company;
import com.idega.data.IDOEntity;

public interface Course extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getGroup
	 */
	public Group getGroup();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getPeriod
	 */
	public Period getPeriod();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getCompany
	 */
	public Company getCompany();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getName
	 */
	public String getName();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getPrice
	 */
	public float getPrice();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getCost
	 */
	public float getCost();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getStartDate
	 */
	public Date getStartDate();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getEndDate
	 */
	public Date getEndDate();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getNumberOfHours
	 */
	public int getNumberOfHours();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#isApproved
	 */
	public boolean isApproved();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#isClosed
	 */
	public boolean isClosed();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getMaxMale
	 */
	public int getMaxMale();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#getMaxFemale
	 */
	public int getMaxFemale();

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setPeriod
	 */
	public void setPeriod(Period period);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setCompany
	 */
	public void setCompany(Company company);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setPrice
	 */
	public void setPrice(float price);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setCost
	 */
	public void setCost(float cost);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setStartDate
	 */
	public void setStartDate(Date date);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setEndDate
	 */
	public void setEndDate(Date date);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setNumberOfHours
	 */
	public void setNumberOfHours(int numberOfHours);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setApproved
	 */
	public void setApproved(boolean isApproved);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setClosed
	 */
	public void setClosed(boolean isClosed);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setMaxMale
	 */
	public void setMaxMale(int max);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#setMaxFemale
	 */
	public void setMaxFemale(int max);

	/**
	 * @see is.idega.idegaweb.egov.company.data.CourseBMPBean#store
	 */
	public void store() throws IDOStoreException;
}