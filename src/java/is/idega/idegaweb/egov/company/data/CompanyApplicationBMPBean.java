package is.idega.idegaweb.egov.company.data;

import is.idega.idegaweb.egov.application.data.ApplicationCategory;
import is.idega.idegaweb.egov.fsk.FSKConstants;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.FinderException;

import com.idega.block.process.data.AbstractCaseBMPBean;
import com.idega.block.process.data.CaseCode;
import com.idega.block.text.data.LocalizedText;
import com.idega.block.text.data.LocalizedTextBMPBean;
import com.idega.block.text.data.LocalizedTextHome;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.localisation.business.ICLocaleBusiness;
import com.idega.core.localisation.data.ICLocale;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.data.IDOStoreException;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.presentation.IWContext;
import com.idega.user.data.Group;
import com.idega.user.data.User;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 19, 2008 by Author: Anton 
 *
 */

public class CompanyApplicationBMPBean extends AbstractCaseBMPBean implements CompanyApplication {
	
	private static final long serialVersionUID = -7638155652762635917L;

	private static final String ENTITY_NAME = "COMPANY_APPLICATION";

	private static final String COLUMN_TYPE = "company_type";
	private static final String COLUMN_COMPANY = "company_id";

	private static final String COLUMN_ADMIN_USER = "admin_user";
	
	private static final String NAME = "application_name";
	private static final String CATEGORY = "application_category_id";
	private static final String CASE_CODE = "case_code";
	private static final String URL = "application_url";
	private static final String ELECTRONIC = "is_electronic";
	private static final String APP_TYPE = "app_type";
	private static final String REQUIRES_LOGIN = "requires_login";
	private static final String VISIBLE = "is_visible";
	private static final String AGE_FROM = "age_from";
	private static final String AGE_TO = "age_to";
	private static final String TIMES_CLICKED = "times_clicked";
	private static final String OPENS_IN_NEW_WINDOW = "opens_in_new_window";
	private static final String HIDDEN_FROM_GUESTS = "hidden_from_guests";
	private static final String PRIORITY = "app_priority";
	private static final String COLUMN_LOGIN_PAGE_URL = "login_page_url";

	private static final String EGOV_APPLICATION_NAME_LOC_TEXT = "EGOV_APPLICATION_NAME";
	private static final String EGOV_APPLICATION_URL_LOC_TEXT = "EGOV_APPLICATION_URL_LOC_TEXT";
	private static final String EGOV_APPLICATION_ID = "EGOV_APPLICATION_ID";
	private static final String TX_LOCALIZED_TEXT = "TX_LOCALIZED_TEXT";
	private static final String TX_LOCALIZED_TEXT_ID = "TX_LOCALIZED_TEXT_ID";
	private static final String IC_LOCALE_ID = "IC_LOCALE_ID";

	@Override
	public String getCaseCodeDescription() {
		return "Application for company access";
	}

	@Override
	public String getCaseCodeKey() {
		return FSKConstants.CASE_CODE_KEY;
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
		addManyToOneRelationship(COLUMN_COMPANY, Company.class);
		addManyToOneRelationship(CATEGORY, ApplicationCategory.class);
		addManyToOneRelationship(CASE_CODE, CaseCode.class);
	}
	
	// Getters
	public CompanyType getType() {
		return (CompanyType) getColumnValue(COLUMN_TYPE);
	}

	public User getAdminUser() {
		return (User) getColumnValue(COLUMN_ADMIN_USER);
	}

	public Company getCompany() {
		return (Company) getColumnValue(COLUMN_COMPANY);
	}

	// Setters
	public void setType(CompanyType type) {
		setColumn(COLUMN_TYPE, type);
	}

	public void setAdminUser(User user) {
		setColumn(COLUMN_ADMIN_USER, user);
	}

	public void setCompany(Company company) {
		setColumn(COLUMN_COMPANY, company);
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
	
	public void setPriority(Integer priority) {
		setColumn(PRIORITY, priority);
	}
	
	public Integer getPriority() {
		return (Integer) getColumnValue(PRIORITY);
	}

	public void setAgeFrom(int age) {
		setColumn(AGE_FROM, age);
	}

	public int getAgeFrom() {
		return getIntColumnValue(AGE_FROM);
	}

	public void setAgeTo(int age) {
		setColumn(AGE_TO, age);
	}

	public int getAgeTo() {
		return getIntColumnValue(AGE_TO);
	}
	
	public int getTimesClicked() {
		return getIntColumnValue(TIMES_CLICKED);
	}
	
	public void setTimesClicked(int clicked) {
		setColumn(TIMES_CLICKED, clicked);
	}

	public void setCategory(ApplicationCategory category) {
		setColumn(CATEGORY, category);
	}

	public ApplicationCategory getCategory() {
		return (ApplicationCategory) getColumnValue(CATEGORY);
	}

	@Override
	public void setCaseCode(CaseCode caseCode) {
		setColumn(CASE_CODE, caseCode);
	}

	@Override
	public CaseCode getCaseCode() {
		return (CaseCode) getColumnValue(CASE_CODE);
	}

	public void setElectronic(boolean isElectronic) {
		setColumn(ELECTRONIC, isElectronic);
	}

	public boolean getElectronic() {
		return getBooleanColumnValue(ELECTRONIC, false);
	}
	
	public void setRequiresLogin(boolean requiresLogin) {
		if (requiresLogin) {
			setElectronic(true);
		}
		setColumn(REQUIRES_LOGIN, requiresLogin);
	}

	public boolean getRequiresLogin() {
		return getBooleanColumnValue(REQUIRES_LOGIN, false);
	}
	
	public void setVisible(boolean visible) {
		setColumn(VISIBLE, visible);
	}
	
	public boolean getVisible() {
		return getBooleanColumnValue(VISIBLE, true);
	}
	
	private void saveAllLocalizedEntries(Map localizedEntries, boolean settingNames){
		if(settingNames){
			for (Iterator iter = localizedEntries.keySet().iterator(); iter.hasNext();) {
				ICLocale icLocale = (ICLocale) iter.next();
				insertLocalizedTextEntry(icLocale.getLocaleID(), (String)localizedEntries.get(icLocale), settingNames);			
			}
		}
		else{
			for (Iterator iter = localizedEntries.keySet().iterator(); iter.hasNext();) {
				ICLocale icLocale = (ICLocale) iter.next();
				insertLocalizedTextEntry(icLocale.getLocaleID(), (String)localizedEntries.get(icLocale), settingNames);			
			}
		}
	}
	
	public void setLocalizedUrls(Map localizedEntries, boolean isNewApplication){
		setLocalizedNamesOrUrls(localizedEntries, isNewApplication, false);
	}
	
	public void setLocalizedNames(Map localizedEntries, boolean isNewApplication){
		setLocalizedNamesOrUrls(localizedEntries, isNewApplication, true);
	} 
	
	private void setLocalizedNamesOrUrls(Map localizedEntries, boolean isNewApplication, boolean settingNames){
		if(isNewApplication){	//if we are saving new application there are no localization still
			saveAllLocalizedEntries(localizedEntries, settingNames);
		}
		else{
			String queryForLocalizedEntriesIds = null;
			if(settingNames){
				queryForLocalizedEntriesIds = getQueryForMiddleTable(EGOV_APPLICATION_NAME_LOC_TEXT, getID());
			}
			else{
				queryForLocalizedEntriesIds = getQueryForMiddleTable(EGOV_APPLICATION_URL_LOC_TEXT, getID());
			}
			
			Collection localNamesIds = null;
			try {
				localNamesIds = idoGetRelatedEntitiesBySQL(LocalizedText.class, queryForLocalizedEntriesIds);
			} catch (IDORelationshipException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			if(localNamesIds == null || localNamesIds.isEmpty()){
				//there are no localized text entries for that application
				saveAllLocalizedEntries(localizedEntries, settingNames);
			}
			else{
				// there are several localized text entries, so we have to check which of them are updated and which are new
				for (Iterator iter = localizedEntries.keySet().iterator(); iter.hasNext();) {
					ICLocale icLocale = (ICLocale)iter.next();

					String queryForLocalizedEntry = getQueryForTxLocalizedText(icLocale.getLocaleID(), localNamesIds);
					Collection localizedEntry = null;
					try {
						localizedEntry = idoGetRelatedEntitiesBySQL(LocalizedText.class, queryForLocalizedEntry);
					} catch (IDORelationshipException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
					if(localizedEntry == null || localizedEntry.isEmpty()){
						//insert entry
						insertLocalizedTextEntry(icLocale.getLocaleID(), (String)localizedEntries.get(icLocale), settingNames);
					}
					else{
						//update entry
						
//						if(!((LocalizedText)(localizedEntry.toArray()[0])).getHeadline().equals((String)localizedEntries.get(icLocale))){
							// localized text entry has been changed
							updateLocalizedTextEntry(((LocalizedText)(localizedEntry.toArray()[0])).getPrimaryKey(), (String)localizedEntries.get(icLocale));
//						}
					}
				}
			}
		}		
	}
	
	public String getLoginPageURL() {
		return getStringColumnValue(COLUMN_LOGIN_PAGE_URL);
	}
	
	public void setLoginPageURL(String url) {
		setColumn(COLUMN_LOGIN_PAGE_URL, url);
	}
	
	public String getAppType() {
		return getStringColumnValue(APP_TYPE);
	}

	@SuppressWarnings("unchecked")
	public Collection<Group> getGroups() {
		try {
			return super.idoGetRelatedEntities(Group.class);
		} catch (IDORelationshipException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean getHiddenFromGuests() {
		return getBooleanColumnValue(HIDDEN_FROM_GUESTS, false);
	}

	public LocalizedText getLocalizedText(int icLocaleId) {
		Collection<LocalizedText> result = null;
		try {
			result = idoGetRelatedEntities(LocalizedText.class);
		} catch(IDORelationshipException e) {
			e.printStackTrace();
		}
		if(result != null) {
			for(Iterator<LocalizedText> it = result.iterator(); it.hasNext(); ) {
				LocalizedText temp = it.next();
				if(temp.getLocaleId() == icLocaleId) {
					return temp;
				}
			}
		}
		return null;
	}

	//	returns url by current locale, if local url is null, then returns empty string
	public String getLocalizedUrl(Locale locale){
		String localizedName = getNameOrUrlByLocale(EGOV_APPLICATION_URL_LOC_TEXT, locale);
		if(localizedName != null){
			return localizedName;
		}
		else {
			return "";
		}				
	}
	
	private String getNameOrUrlByLocale(String table, Locale currentLocale){
		Collection localNamesIds = null;
		Collection localizedName = null;
		
		try { //getting ids of entries representing selected headline
			
			String sqlQuery = "select * from " + table +" where "+EGOV_APPLICATION_ID+" = "+getID();
			localNamesIds = idoGetRelatedEntitiesBySQL(LocalizedText.class, sqlQuery);
			if(localNamesIds == null || localNamesIds.isEmpty())
				return null;
		} catch (IDORelationshipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		try { //getting localized headline
			
			localizedName = idoGetRelatedEntitiesBySQL(LocalizedTextBMPBean.class, getQueryForTxLocalizedText(ICLocaleBusiness.getLocaleId(currentLocale), localNamesIds));
			if (localizedName == null || localizedName.isEmpty()) {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return ((LocalizedTextBMPBean)(localizedName.toArray()[0])).getHeadline();
	}

	public String getNameByLocale(Locale locale){
		String localizedName = getNameOrUrlByLocale(EGOV_APPLICATION_NAME_LOC_TEXT, locale);
		if(localizedName != null){
			return localizedName;
		}
		else {
			return getStringColumnValue(NAME);
		}		
	}

	//returns name by current locale, if localized name is null, then returns default name
	public String getNameByLocale(){
		IWContext iwc = IWContext.getInstance();
		return getNameByLocale(iwc.getLocale());
	}

	public boolean getOpensInNewWindow() {
		return getBooleanColumnValue(OPENS_IN_NEW_WINDOW, false);
	}

	public String getQueryForMiddleTable(String tableName, int applicationId){
		return "select * from "+tableName+" where "+EGOV_APPLICATION_ID+"="+applicationId;
	}

	public String getQueryForTxLocalizedText(int localeId, Collection localNamesIds){
		String queryForLocalizedEntry = "select * from "+TX_LOCALIZED_TEXT+" where "+IC_LOCALE_ID+" = "+localeId+" AND (";
		for (Iterator localNameIdsIterator = localNamesIds.iterator(); localNameIdsIterator.hasNext();) {
			LocalizedText element = (LocalizedText) localNameIdsIterator.next();
			if(localNameIdsIterator.hasNext()){
				queryForLocalizedEntry += TX_LOCALIZED_TEXT_ID+" = "+element.getPrimaryKey()+ " OR ";
			}
			else{
				queryForLocalizedEntry += TX_LOCALIZED_TEXT_ID+" = "+element.getPrimaryKey()+")";
			}
		}
		return queryForLocalizedEntry;
	}

	//	returns url by current locale, if localized url is null, then returns default url
	public String getUrlByLocale(){
		IWContext iwc = IWContext.getInstance();
		return getUrlByLocale(iwc.getLocale());
	}

	public String getUrlByLocale(Locale locale){
		String localizedName = getNameOrUrlByLocale(EGOV_APPLICATION_URL_LOC_TEXT, locale);
		if(localizedName != null){
			return localizedName;
		}
		else {
			return getStringColumnValue(URL);
		}		
	}

	public void insertLocalizedTextEntry(int localeId, String headline, boolean settingNames){
		if(headline == null || headline.equals("")){
			return;
		}
		LocalizedText localizedText = ((LocalizedTextHome) com.idega.data.IDOLookup.getHomeLegacy(LocalizedText.class)).createLegacy();
		localizedText.setHeadline(headline);
		localizedText.setLocaleId(localeId);
		
		try {
			localizedText.store();
		} catch (IDOStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String queryForMiddleTable = null;
		if(settingNames){
			queryForMiddleTable = "insert into "+EGOV_APPLICATION_NAME_LOC_TEXT+" ("+EGOV_APPLICATION_ID+", "+TX_LOCALIZED_TEXT_ID+") values ("+getID()+", "+localizedText.getID()+")";
		}
		else{
			queryForMiddleTable = "insert into "+EGOV_APPLICATION_URL_LOC_TEXT+" ("+EGOV_APPLICATION_ID+", "+TX_LOCALIZED_TEXT_ID+") values ("+getID()+", "+localizedText.getID()+")";			
		}
		try {
			idoExecuteGlobalUpdate(queryForMiddleTable);
		} catch (IDOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean removeLocalizedEntries(){

		//remove Ids of localized names
		Collection localizedNamesIds = null;
		String query = getQueryForMiddleTable(EGOV_APPLICATION_NAME_LOC_TEXT, getID());
		try {
			localizedNamesIds = idoGetRelatedEntitiesBySQL(LocalizedText.class, query);
		} catch (IDORelationshipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		query = "delete from "+EGOV_APPLICATION_NAME_LOC_TEXT+" where "+EGOV_APPLICATION_ID+" = "+getID();
		try {
			idoExecuteGlobalUpdate(query);
		} catch (IDOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		//remove Ids of localized names
		Collection localizedURLsIds = null;
		query = getQueryForMiddleTable(EGOV_APPLICATION_URL_LOC_TEXT, getID());
		try {
			localizedURLsIds = idoGetRelatedEntitiesBySQL(LocalizedText.class, query);
		} catch (IDORelationshipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		query = "delete from "+EGOV_APPLICATION_URL_LOC_TEXT+" where "+EGOV_APPLICATION_ID+" = "+getID();
		try {
			idoExecuteGlobalUpdate(query);
		} catch (IDOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		query = getQueryForDeletingLocalizedTextEntries(localizedNamesIds, localizedURLsIds);
		System.out.println(query);		
		try {
			idoExecuteGlobalUpdate(query);
		} catch (IDOException e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	private String getQueryForDeletingLocalizedTextEntries(Collection localizedNamesIds, Collection localizedUrlIds){
		String query = null;
		
		if(localizedNamesIds != null && !localizedNamesIds.isEmpty()){
			query = "delete from "+TX_LOCALIZED_TEXT+" where ";
		
			for (Iterator localNameIdsIterator = localizedNamesIds.iterator(); localNameIdsIterator.hasNext();) {
				LocalizedText element = (LocalizedText) localNameIdsIterator.next();
				if(localNameIdsIterator.hasNext()){
					query += TX_LOCALIZED_TEXT_ID+" = "+element.getPrimaryKey()+ " OR ";
				}
				else{
					query += TX_LOCALIZED_TEXT_ID+" = "+element.getPrimaryKey();
				}
			}
			
			if(localizedUrlIds == null || localizedUrlIds.isEmpty()){
				return query;
			}
			else{
				query += " OR ";
			}
		}
		if(localizedUrlIds != null && !localizedUrlIds.isEmpty()){
			if(query == null){
				query = "delete from "+TX_LOCALIZED_TEXT+" where ";
			}				
			for (Iterator localNameIdsIterator = localizedUrlIds.iterator(); localNameIdsIterator.hasNext();) {
				LocalizedText element = (LocalizedText) localNameIdsIterator.next();
				if(localNameIdsIterator.hasNext()){
					query += TX_LOCALIZED_TEXT_ID+" = "+element.getPrimaryKey()+ " OR ";
				}
				else{
					query += TX_LOCALIZED_TEXT_ID+" = "+element.getPrimaryKey();
				}
			}
		}
		return query;
	}

	public void setAppType(String appType) {
		setColumn(APP_TYPE, appType);
	}

	public void setHiddenFromGuests(boolean hiddenFromGuests) {
		setColumn(HIDDEN_FROM_GUESTS, hiddenFromGuests);
	}

	public void setOpensInNewWindow(boolean opensInNew) {
		setColumn(OPENS_IN_NEW_WINDOW, opensInNew);
	}

	public void setUrl(String url) {
		setColumn(URL, url);
	}

	public void updateLocalizedTextEntry(Object primaryKey, String headline){
		LocalizedText localizedText = null;
		try {
			localizedText = ((LocalizedTextHome) com.idega.data.IDOLookup.getHomeLegacy(LocalizedText.class)).findByPrimaryKey(primaryKey);
		} catch (EJBException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		}
		localizedText.setHeadline(headline);
		localizedText.store();
	}

	public String getLocalizedName(Locale locale){
		String localizedName = getNameOrUrlByLocale(EGOV_APPLICATION_NAME_LOC_TEXT, locale);
		if(localizedName != null){
			return localizedName;
		}
		else {
			return "";
		}		
	}
	
	public Object ejbFindByCompany(Company company) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COMPANY), MatchCriteria.EQUALS, company));

		return idoFindOnePKByQuery(query);
	}
}
