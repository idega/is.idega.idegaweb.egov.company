package is.idega.idegaweb.egov.company.data;


import com.idega.company.data.CompanyType;
import com.idega.data.IDOEntity;

public interface DivisionName extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.company.data.DivisionNameBMPBean#getType
	 */
	public CompanyType getType();

	/**
	 * @see is.idega.idegaweb.egov.company.data.DivisionNameBMPBean#getName
	 */
	public String getName();

	/**
	 * @see is.idega.idegaweb.egov.company.data.DivisionNameBMPBean#isValid
	 */
	public boolean isValid();

	/**
	 * @see is.idega.idegaweb.egov.company.data.DivisionNameBMPBean#setType
	 */
	public void setType(CompanyType type);

	/**
	 * @see is.idega.idegaweb.egov.company.data.DivisionNameBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see is.idega.idegaweb.egov.company.data.DivisionNameBMPBean#setValid
	 */
	public void setValid(boolean valid);
}