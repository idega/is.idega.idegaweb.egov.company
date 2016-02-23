package is.idega.idegaweb.egov.company.data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.ejb.FinderException;

import com.idega.block.process.data.AbstractCaseBMPBean;
import com.idega.block.process.data.CaseCode;
import com.idega.block.text.data.LocalizedText;
import com.idega.block.text.model.LocalizedTextModel;
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

import is.idega.idegaweb.egov.application.data.ApplicationCategory;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;

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

		super.initializeAttributes();
	}

	// Getters
	@Override
	public CompanyType getType() {
		return (CompanyType) getColumnValue(COLUMN_TYPE);
	}

	@Override
	public User getAdminUser() {
		return (User) getColumnValue(COLUMN_ADMIN_USER);
	}

	@Override
	public User getApplicantUser() {
		return (User) getColumnValue(COLUMN_APPLICANT_USER);
	}

	@Override
	public Company getCompany() {
		return (Company) getColumnValue(COLUMN_COMPANY);
	}

	@Override
	public ICFile getContract() {
		return (ICFile) getColumnValue(CONTRACT_FILE);
	}

	// Setters
	@Override
	public void setType(CompanyType type) {
		setColumn(COLUMN_TYPE, type);
	}

	@Override
	public void setAdminUser(User user) {
		setColumn(COLUMN_ADMIN_USER, user);
	}

	@Override
	public void setApplicantUser(User user) {
		setColumn(COLUMN_APPLICANT_USER, user);
	}

	@Override
	public void setCompany(Company company) {
		setColumn(COLUMN_COMPANY, company);
	}

	@Override
	public void setContract(ICFile contract) {
		setColumn(CONTRACT_FILE, contract);
	}

	@Override
	public void addGroup(Group group) throws IDOAddRelationshipException {
		this.idoAddTo(group);
	}

	@Override
	public void removeGroup(Group group) throws IDORemoveRelationshipException {
		super.idoRemoveFrom(group);
	}

	@Override
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

	@Override
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



	@Override
	public void setPriority(Integer priority) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setPriority() not supported.");
	}

	@Override
	public Integer getPriority() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getPriority() not supported.");
	}

	@Override
	public void setAgeFrom(int age) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setAgeFrom() not supported.");
	}

	@Override
	public int getAgeFrom() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getAgeFrom() not supported.");
	}

	@Override
	public void setAgeTo(int age) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setAgeTo() not supported.");
	}

	@Override
	public int getAgeTo() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getAgeTo() not supported.");
	}

	@Override
	public int getTimesClicked() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getTimesClicked() not supported.");
	}

	@Override
	public void setTimesClicked(int clicked) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setTimesClicked() not supported.");
	}

	@Override
	public void setCategory(ApplicationCategory category) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setCategory() not supported.");
	}

	@Override
	public ApplicationCategory getCategory() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getCategory() not supported.");
	}

	@Override
	public void setElectronic(boolean isElectronic) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setElectronic() not supported.");
	}

	@Override
	public boolean getElectronic() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getElectronic() not supported.");
	}

	@Override
	public void setRequiresLogin(boolean requiresLogin) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setRequiresLogin() not supported.");
	}

	@Override
	public boolean getRequiresLogin() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getRequiresLogin() not supported.");
	}

	@Override
	public void setVisible(boolean visible) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setVisible() not supported.");
	}

	@Override
	public boolean getVisible() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getVisible() not supported.");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setLocalizedUrls(Map localizedEntries, boolean isNewApplication){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setLocalizedUrls() not supported.");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setLocalizedNames(Map localizedEntries, boolean isNewApplication){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setLocalizedNames() not supported.");
	}

	@Override
	public String getLoginPageURL() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getLoginPageURL() not supported.");
	}

	@Override
	public void setLoginPageURL(String url) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setLoginPageURL() not supported.");
	}

	@Override
	public String getAppType() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getAppType() not supported.");
	}

	@Override
	public boolean getHiddenFromGuests() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getHiddenFromGuests() not supported.");
	}

	@Override
	public LocalizedText getLocalizedText(int icLocaleId) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getLocalizedText() not supported.");
	}

	@Override
	public String getLocalizedUrl(Locale locale){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getLocalizedUrl() not supported.");
	}

	@Override
	public String getNameByLocale(Locale locale){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getNameByLocale() not supported.");
	}

	@Override
	public String getNameByLocale(){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getNameByLocale() not supported.");
	}

	@Override
	public boolean getOpensInNewWindow() {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getOpensInNewWindow() not supported.");
	}

	@Override
	public String getQueryForMiddleTable(String tableName, int applicationId){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getQueryForMiddleTable() not supported.");
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getQueryForTxLocalizedText(int localeId, Collection localNamesIds){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getQueryForTxLocalizedText() not supported.");
	}

	@Override
	public String getUrlByLocale(){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getUrlByLocale() not supported.");
	}

	@Override
	public String getUrlByLocale(Locale locale){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getUrlByLocale() not supported.");
	}

	@Override
	public void insertLocalizedTextEntry(int localeId, String headline, boolean settingNames){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method insertLocalizedTextEntry() not supported.");
	}

	@Override
	public boolean removeLocalizedEntries(){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method removeLocalizedEntries() not supported.");
	}

	@Override
	public void setAppType(String appType) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setAppType() not supported.");
	}

	@Override
	public void setHiddenFromGuests(boolean hiddenFromGuests) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setHiddenFromGuests() not supported.");
	}

	@Override
	public void setOpensInNewWindow(boolean opensInNew) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setOpensInNewWindow() not supported.");
	}

	@Override
	public void setUrl(String url) {
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method setUrl() not supported.");
	}

	@Override
	public void updateLocalizedTextEntry(Object primaryKey, String headline){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method updateLocalizedTextEntry() not supported.");
	}

	@Override
	public String getLocalizedName(Locale locale){
		/**@todo: Implement this is.idega.idegaweb.egov.application.data.Application method*/
		throw new java.lang.UnsupportedOperationException("Method getLocalizedName() not supported.");
	}

	@Override
	public Timestamp getEnabledFrom() {
		throw new java.lang.UnsupportedOperationException("Method getEnabledFrom not supported.");
	}

	@Override
	public void setEnabledFrom(Timestamp enabledFrom) {
		throw new java.lang.UnsupportedOperationException("Method setEnabledFrom not supported.");
	}

	@Override
	public Timestamp getEnabledTo() {
		throw new java.lang.UnsupportedOperationException("Method getEnabledTo not supported.");
	}

	@Override
	public void setEnabledTo(Timestamp enabledTo) {
		throw new java.lang.UnsupportedOperationException("Method setEnabledTo not supported.");
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void setPaymentRequired(boolean isPaymentRequired) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public boolean isPaymentRequired() {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public <T extends LocalizedTextModel, L extends Collection<T>> L getLocalizedTexts() {
		return null;
	}

	@Override
	public String getLocalizedName() {
		return null;
	}

	@Override
	public String getLocalizedName(int icLocaleId) {
		return null;
	}

}