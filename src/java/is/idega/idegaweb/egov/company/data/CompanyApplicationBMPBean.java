package is.idega.idegaweb.egov.company.data;

import is.idega.idegaweb.egov.application.data.ApplicationCategory;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.ejb.FinderException;

import com.idega.block.process.data.AbstractCaseBMPBean;
import com.idega.block.process.data.CaseCode;
import com.idega.block.text.data.LocalizedText;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.file.data.ICFile;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.data.query.InCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.user.data.Group;
import com.idega.user.data.User;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.1 
 *
 * Last modified: Seo 23, 2008 by Author: Anton 
 *
 */

public class CompanyApplicationBMPBean extends AbstractCaseBMPBean implements CompanyApplication {
	
	private static final long serialVersionUID = -7638155652762635917L;

	private static final String ENTITY_NAME = "COMPANY_APPLICATION";

	private static final String COLUMN_TYPE = "company_type";
	private static final String COLUMN_COMPANY = "company_id";

	private static final String COLUMN_ADMIN_USER = "admin_user";
	private static final String COLUMN_APPLICANT_USER = "applicant_user";
	
	private static final String CATEGORY = "application_category_id";
	private static final String CASE_CODE = "case_code";
	private static final String CONTRACT_FILE = "contract_file"; 

	private static final String EGOV_APPLICATION_GROUP = "EGOV_APPLICATION_GROUP";

	@Override
	public String getCaseCodeDescription() {
		return "Application for company portal";
	}

	@Override
	public String getCaseCodeKey() {
		return EgovCompanyConstants.CASE_CODE_KEY;
	}

	@Override
	public String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	public void initializeAttributes() {
		addGeneralCaseRelation();

		addManyToOneRelationship(COLUMN_TYPE, CompanyType.class);
		addManyToOneRelationship(COLUMN_ADMIN_USER, User.class);
		addManyToOneRelationship(COLUMN_APPLICANT_USER, User.class);
		addManyToOneRelationship(COLUMN_COMPANY, Company.class);
		addManyToOneRelationship(CATEGORY, ApplicationCategory.class);
		addManyToOneRelationship(CASE_CODE, CaseCode.class);
		addManyToManyRelationShip(Group.class, EGOV_APPLICATION_GROUP);
		addOneToOneRelationship(CONTRACT_FILE, ICFile.class);
	}
	
	// Getters
	public CompanyType getType() {
		return (CompanyType) getColumnValue(COLUMN_TYPE);
	}

	public User getAdminUser() {
		return (User) getColumnValue(COLUMN_ADMIN_USER);
	}
	
	public User getApplicantUser() {
		return (User) getColumnValue(COLUMN_APPLICANT_USER);
	}

	public Company getCompany() {
		return (Company) getColumnValue(COLUMN_COMPANY);
	}
	
	public ICFile getContract() {
		return (ICFile) getColumnValue(CONTRACT_FILE);
	}

	// Setters
	public void setType(CompanyType type) {
		setColumn(COLUMN_TYPE, type);
	}

	public void setAdminUser(User user) {
		setColumn(COLUMN_ADMIN_USER, user);
	}
	
	public void setApplicantUser(User user) {
		setColumn(COLUMN_APPLICANT_USER, user);
	}

	public void setCompany(Company company) {
		setColumn(COLUMN_COMPANY, company);
	}
	
	public void setContract(ICFile contract) {
		setColumn(CONTRACT_FILE, contract);
	}

	public void addGroup(Group group) throws IDOAddRelationshipException {
		this.idoAddTo(group);
	}
	
	public void removeGroup(Group group) throws IDORemoveRelationshipException {
		super.idoRemoveFrom(group);
	}

	public void addLocalizedName(LocalizedText text) throws IDOAddRelationshipException {
	  	idoAddTo(text);
	}
	
	public Object ejbFindByCompany(Company company) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COMPANY), MatchCriteria.EQUALS, company));

		return idoFindOnePKByQuery(query);
	}
	
	@SuppressWarnings("unchecked")
	public Collection ejbFindAllByCaseCodes(String[] caseCodes) throws FinderException {
		Table applicationTable = new Table(this);
		
		SelectQuery query = new SelectQuery(applicationTable);
		query.addColumn(applicationTable.getColumn(getIDColumnName()));
		query.addCriteria(new InCriteria(applicationTable.getColumn(CASE_CODE), caseCodes));
		query.addGroupByColumn(applicationTable, getIDColumnName());
		
		return idoFindPKsByQuery(query);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Group> getGroups() {
		try {
			return idoGetRelatedEntities(Group.class);
		} catch (IDORelationshipException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setCaseCode(CaseCode caseCode) {
		setColumn(CASE_CODE, caseCode);
	}

	@Override
	public CaseCode getCaseCode() {
		return (CaseCode) getColumnValue(CASE_CODE);
	}
	
	
	
	public void setPriority(Integer priority) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public Integer getPriority() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public void setAgeFrom(int age) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public int getAgeFrom() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public void setAgeTo(int age) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public int getAgeTo() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public int getTimesClicked() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public void setTimesClicked(int clicked) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public void setCategory(ApplicationCategory category) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public ApplicationCategory getCategory() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public void setElectronic(boolean isElectronic) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public boolean getElectronic() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public void setRequiresLogin(boolean requiresLogin) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public boolean getRequiresLogin() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public void setVisible(boolean visible) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public boolean getVisible() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	@SuppressWarnings("unchecked")
	public void setLocalizedUrls(Map localizedEntries, boolean isNewApplication){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	@SuppressWarnings("unchecked")
	public void setLocalizedNames(Map localizedEntries, boolean isNewApplication){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	} 
	
	public String getLoginPageURL() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public void setLoginPageURL(String url) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public String getAppType() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public boolean getHiddenFromGuests() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public LocalizedText getLocalizedText(int icLocaleId) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public String getLocalizedUrl(Locale locale){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");			
	}
	
	public String getNameByLocale(Locale locale){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public String getNameByLocale(){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public boolean getOpensInNewWindow() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public String getQueryForMiddleTable(String tableName, int applicationId){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	@SuppressWarnings("unchecked")
	public String getQueryForTxLocalizedText(int localeId, Collection localNamesIds){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public String getUrlByLocale(){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public String getUrlByLocale(Locale locale){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public void insertLocalizedTextEntry(int localeId, String headline, boolean settingNames){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public boolean removeLocalizedEntries(){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
	
	public void setAppType(String appType) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public void setHiddenFromGuests(boolean hiddenFromGuests) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public void setOpensInNewWindow(boolean opensInNew) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public void setUrl(String url) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public void updateLocalizedTextEntry(Object primaryKey, String headline){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}

	public String getLocalizedName(Locale locale){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeUser() not supported.");
	}
}
