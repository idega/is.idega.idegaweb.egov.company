package is.idega.idegaweb.egov.company.data;


import com.idega.core.file.data.ICFile;
import com.idega.block.process.data.Case;
import javax.ejb.CreateException;
import com.idega.user.data.User;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.company.data.Company;
import com.idega.core.location.data.PostalCode;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDORelationshipException;
import java.util.Collection;
import com.idega.company.data.CompanyType;
import javax.ejb.RemoveException;
import com.idega.data.IDOEntity;

public interface Application extends IDOEntity, Case {
	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getCaseCodeDescription
	 */
	public String getCaseCodeDescription();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getCaseCodeKey
	 */
	public String getCaseCodeKey();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getType
	 */
	public CompanyType getType();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getAdminUser
	 */
	public User getAdminUser();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getExtraAdminUser
	 */
	public User getExtraAdminUser();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getContactPerson
	 */
	public User getContactPerson();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getName
	 */
	public String getName();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getPersonalID
	 */
	public String getPersonalID();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getAddress
	 */
	public String getAddress();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getPostalCode
	 */
	public String getPostalCode();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getCity
	 */
	public String getCity();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getPhoneNumber
	 */
	public String getPhoneNumber();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getFaxNumber
	 */
	public String getFaxNumber();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getWebPage
	 */
	public String getWebPage();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getEmail
	 */
	public String getEmail();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getBankAccount
	 */
	public String getBankAccount();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getComments
	 */
	public String getComments();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getCompany
	 */
	public Company getCompany();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getContractFile
	 */
	public ICFile getContractFile();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setType
	 */
	public void setType(CompanyType type);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setAdminUser
	 */
	public void setAdminUser(User user);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setExtraAdminUser
	 */
	public void setExtraAdminUser(User user);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setContactPerson
	 */
	public void setContactPerson(User user);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setPersonalID
	 */
	public void setPersonalID(String personalID);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setAddress
	 */
	public void setAddress(String address);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setPostalCode
	 */
	public void setPostalCode(String postalCode);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setCity
	 */
	public void setCity(String city);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setPhoneNumber
	 */
	public void setPhoneNumber(String phoneNumber);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setFaxNumber
	 */
	public void setFaxNumber(String faxNumber);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setWebPage
	 */
	public void setWebPage(String webPage);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setEmail
	 */
	public void setEmail(String email);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setBankAccount
	 */
	public void setBankAccount(String bankAccount);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setComments
	 */
	public void setComments(String comments);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setCompany
	 */
	public void setCompany(Company company);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#setContractFile
	 */
	public void setContractFile(ICFile file);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#addPostalCode
	 */
	public void addPostalCode(PostalCode code)
			throws IDOAddRelationshipException;

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#addPostalCode
	 */
	public void addPostalCode(Object postalCodePK)
			throws IDOAddRelationshipException;

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#removePostalCode
	 */
	public void removePostalCode(PostalCode code)
			throws IDORemoveRelationshipException;

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#removeAllPostalCodes
	 */
	public void removeAllPostalCodes() throws IDORemoveRelationshipException;

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getPostalCodes
	 */
	public Collection getPostalCodes() throws IDORelationshipException;

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getFiles
	 */
	public Collection getFiles();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#getFile
	 */
	public ApplicationFiles getFile(String type);

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#hasRequiredFiles
	 */
	public boolean hasRequiredFiles();

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#addFile
	 */
	public void addFile(ICFile file, String type) throws CreateException;

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#addFile
	 */
	public void addFile(ICFile file, String type, Season season)
			throws CreateException;

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#removeFile
	 */
	public void removeFile(ApplicationFiles file) throws RemoveException;

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#removeAllFiles
	 */
	public void removeAllFiles() throws RemoveException;

	/**
	 * @see is.idega.idegaweb.egov.company.data.ApplicationBMPBean#removeAllFiles
	 */
	public void removeAllFiles(String type) throws RemoveException;
}