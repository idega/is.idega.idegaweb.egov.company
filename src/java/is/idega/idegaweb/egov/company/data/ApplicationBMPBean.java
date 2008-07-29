/*
 * $Id: ApplicationBMPBean.java,v 1.1 2008/07/29 12:57:42 anton Exp $ Created on Jun 9, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.data;

import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.AttachmentComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.idega.block.process.data.AbstractCaseBMPBean;
import com.idega.block.process.data.Case;
import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.file.data.ICFile;
import com.idega.core.location.data.PostalCode;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOException;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.data.query.InCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.user.data.User;

public class ApplicationBMPBean extends AbstractCaseBMPBean implements Application, Case {

	private static final String ENTITY_NAME = "fsk_application";

	private static final String COLUMN_TYPE = "company_type";
	private static final String COLUMN_COMPANY = "company_id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_PERSONAL_ID = "personal_id";
	private static final String COLUMN_ADDRESS = "address";
	private static final String COLUMN_POSTAL_CODE = "postal_code";
	private static final String COLUMN_CITY = "city";
	private static final String COLUMN_PHONE_NUMBER = "phone_number";
	private static final String COLUMN_FAX_NUMBER = "fax_number";
	private static final String COLUMN_WEB_PAGE = "web_page";
	private static final String COLUMN_EMAIL = "email";
	private static final String COLUMN_BANK_ACCOUNT = "bank_account";
	private static final String COLUMN_COMMENTS = "comments";
	private static final String COLUMN_ADMIN_USER = "admin_user";
	private static final String COLUMN_EXTRA_ADMIN_USER = "extra_admin_user";
	private static final String COLUMN_CONTRACT_FILE = "contract_file_id";
	private static final String COLUMN_CONTACT_PERSON = "contact_user";

	public String getCaseCodeDescription() {
		return "Application for company access";
	}

	public String getCaseCodeKey() {
		return FSKConstants.CASE_CODE_KEY;
	}

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addGeneralCaseRelation();

		addManyToOneRelationship(COLUMN_TYPE, CompanyType.class);

		addAttribute(COLUMN_NAME, "Name", String.class);
		addAttribute(COLUMN_PERSONAL_ID, "Personal ID", String.class, 10);
		addAttribute(COLUMN_ADDRESS, "Address", String.class);
		addAttribute(COLUMN_POSTAL_CODE, "Postal code", String.class);
		addAttribute(COLUMN_CITY, "City", String.class);
		addAttribute(COLUMN_PHONE_NUMBER, "Phone number", String.class);
		addAttribute(COLUMN_FAX_NUMBER, "Fax number", String.class);
		addAttribute(COLUMN_WEB_PAGE, "Web page", String.class);
		addAttribute(COLUMN_EMAIL, "E-mail", String.class);
		addAttribute(COLUMN_BANK_ACCOUNT, "Bank account", String.class);
		addAttribute(COLUMN_COMMENTS, "Comments", String.class, 4000);

		addManyToOneRelationship(COLUMN_ADMIN_USER, User.class);
		addManyToOneRelationship(COLUMN_EXTRA_ADMIN_USER, User.class);
		addManyToOneRelationship(COLUMN_COMPANY, Company.class);
		addManyToOneRelationship(COLUMN_CONTRACT_FILE, ICFile.class);
		addManyToOneRelationship(COLUMN_CONTACT_PERSON, User.class);

		addManyToManyRelationShip(PostalCode.class, "fsk_application_postal_codes");
	}

	public void insertStartData() {
		super.insertStartData();
	}

	// Getters
	public CompanyType getType() {
		return (CompanyType) getColumnValue(COLUMN_TYPE);
	}

	public User getAdminUser() {
		return (User) getColumnValue(COLUMN_ADMIN_USER);
	}

	public User getExtraAdminUser() {
		return (User) getColumnValue(COLUMN_EXTRA_ADMIN_USER);
	}
	
	public User getContactPerson() {
		return (User) getColumnValue(COLUMN_CONTACT_PERSON);		
	}

	public String getName() {
		return getStringColumnValue(COLUMN_NAME);
	}

	public String getPersonalID() {
		return getStringColumnValue(COLUMN_PERSONAL_ID);
	}

	public String getAddress() {
		return getStringColumnValue(COLUMN_ADDRESS);
	}

	public String getPostalCode() {
		return getStringColumnValue(COLUMN_POSTAL_CODE);
	}

	public String getCity() {
		return getStringColumnValue(COLUMN_CITY);
	}

	public String getPhoneNumber() {
		return getStringColumnValue(COLUMN_PHONE_NUMBER);
	}

	public String getFaxNumber() {
		return getStringColumnValue(COLUMN_FAX_NUMBER);
	}

	public String getWebPage() {
		return getStringColumnValue(COLUMN_WEB_PAGE);
	}

	public String getEmail() {
		return getStringColumnValue(COLUMN_EMAIL);
	}

	public String getBankAccount() {
		return getStringColumnValue(COLUMN_BANK_ACCOUNT);
	}

	public String getComments() {
		return getStringColumnValue(COLUMN_COMMENTS);
	}

	public Company getCompany() {
		return (Company) getColumnValue(COLUMN_COMPANY);
	}

	public ICFile getContractFile() {
		return (ICFile) getColumnValue(COLUMN_CONTRACT_FILE);
	}

	// Setters
	public void setType(CompanyType type) {
		setColumn(COLUMN_TYPE, type);
	}

	public void setAdminUser(User user) {
		setColumn(COLUMN_ADMIN_USER, user);
	}

	public void setExtraAdminUser(User user) {
		setColumn(COLUMN_EXTRA_ADMIN_USER, user);
	}

	public void setContactPerson(User user) {
		setColumn(COLUMN_CONTACT_PERSON, user);
	}
	
	public void setName(String name) {
		setColumn(COLUMN_NAME, name);
	}

	public void setPersonalID(String personalID) {
		setColumn(COLUMN_PERSONAL_ID, personalID);
	}

	public void setAddress(String address) {
		setColumn(COLUMN_ADDRESS, address);
	}

	public void setPostalCode(String postalCode) {
		setColumn(COLUMN_POSTAL_CODE, postalCode);
	}

	public void setCity(String city) {
		setColumn(COLUMN_CITY, city);
	}

	public void setPhoneNumber(String phoneNumber) {
		setColumn(COLUMN_PHONE_NUMBER, phoneNumber);
	}

	public void setFaxNumber(String faxNumber) {
		setColumn(COLUMN_FAX_NUMBER, faxNumber);
	}

	public void setWebPage(String webPage) {
		setColumn(COLUMN_WEB_PAGE, webPage);
	}

	public void setEmail(String email) {
		setColumn(COLUMN_EMAIL, email);
	}

	public void setBankAccount(String bankAccount) {
		setColumn(COLUMN_BANK_ACCOUNT, bankAccount);
	}

	public void setComments(String comments) {
		setColumn(COLUMN_COMMENTS, comments);
	}

	public void setCompany(Company company) {
		setColumn(COLUMN_COMPANY, company);
	}

	public void setContractFile(ICFile file) {
		setColumn(COLUMN_CONTRACT_FILE, file);
	}

	// Postal codes
	public void addPostalCode(PostalCode code) throws IDOAddRelationshipException {
		idoAddTo(code);
	}

	public void addPostalCode(Object postalCodePK) throws IDOAddRelationshipException {
		idoAddTo(PostalCode.class, postalCodePK);
	}

	public void removePostalCode(PostalCode code) throws IDORemoveRelationshipException {
		idoRemoveFrom(code);
	}

	public void removeAllPostalCodes() throws IDORemoveRelationshipException {
		idoRemoveFrom(PostalCode.class);
	}

	public Collection getPostalCodes() throws IDORelationshipException {
		return idoGetRelatedEntities(PostalCode.class);
	}

	// Files
	private ApplicationFilesHome getFilesHome() {
		try {
			return (ApplicationFilesHome) getIDOHome(ApplicationFiles.class);
		}
		catch (IDOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	public Collection getFiles() {
		try {
			List files = new ArrayList(getFilesHome().findByApplication(this));
			Collections.sort(files, new AttachmentComparator());

			return files;
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public ApplicationFiles getFile(String type) {
		try {
			return getFilesHome().findByApplicationAndType(this, type);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean hasRequiredFiles() {
		try {
			String[] tariff = { FSKConstants.FILE_TYPE_TARIFF };
			if (getFilesHome().getNumberOfFiles(this, tariff) == 0) {
				return false;
			}

			String[] schedule = { FSKConstants.FILE_TYPE_SCHEDULE };
			if (getFilesHome().getNumberOfFiles(this, schedule) == 0) {
				return false;
			}

			return true;
		}
		catch (IDOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void addFile(ICFile file, String type) throws CreateException {
		addFile(file, type, null);
	}

	public void addFile(ICFile file, String type, Season season) throws CreateException {
		ApplicationFiles files = getFilesHome().create();
		files.setAppliation(this);
		files.setFile(file);
		files.setType(type);
		files.setSeason(season);
		files.store();
	}

	public void removeFile(ApplicationFiles file) throws RemoveException {
		file.remove();
	}

	public void removeAllFiles() throws RemoveException {
		removeAllFiles(null);
	}

	public void removeAllFiles(String type) throws RemoveException {
		try {
			Collection files = getFilesHome().findAllByApplicationAndType(this, type);
			Iterator iterator = files.iterator();
			while (iterator.hasNext()) {
				ApplicationFiles file = (ApplicationFiles) iterator.next();
				file.remove();
			}
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
	}

	// Finders
	public Collection ejbFindByStatus(String[] caseStatus) throws FinderException {
		Table table = new Table(this);
		Table process = new Table(Case.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		try {
			query.addJoin(table, process);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new InCriteria(process.getColumn("case_status"), caseStatus));
		query.addOrder(process, "created", false);

		return idoFindPKsByQuery(query);
	}

	public Object ejbFindByCompany(Company company) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COMPANY), MatchCriteria.EQUALS, company));

		return idoFindOnePKByQuery(query);
	}
}