package is.idega.idegaweb.egov.fsk.business;


import com.idega.block.process.business.CaseBusiness;
import is.idega.idegaweb.egov.fsk.data.ParticipantDiscount;
import javax.ejb.CreateException;
import is.idega.idegaweb.egov.fsk.data.DivisionName;
import com.idega.company.data.Company;
import is.idega.idegaweb.egov.fsk.data.Season;
import is.idega.idegaweb.egov.fsk.data.Division;
import com.idega.core.location.data.PostalCode;
import is.idega.idegaweb.egov.fsk.data.Constant;
import com.idega.business.IBOService;
import com.idega.company.data.CompanyType;
import is.idega.idegaweb.egov.fsk.data.Application;
import java.io.File;
import is.idega.idegaweb.egov.fsk.data.Allocation;
import com.idega.idegaweb.IWUserContext;
import com.idega.io.UploadFile;
import is.idega.idegaweb.egov.fsk.data.PaymentAllocation;
import java.util.Map;
import is.idega.idegaweb.egov.message.business.CommuneMessageBusiness;
import is.idega.idegaweb.egov.accounting.business.CitizenBusiness;
import com.idega.user.data.User;
import java.sql.Date;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import com.idega.user.data.Group;
import java.util.Locale;
import is.idega.idegaweb.egov.fsk.data.Period;
import java.util.Collection;
import is.idega.idegaweb.egov.fsk.data.Participant;
import javax.ejb.FinderException;
import com.idega.company.business.CompanyBusiness;
import is.idega.idegaweb.egov.fsk.data.Course;

public interface FSKBusiness extends IBOService, CaseBusiness {

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getUserBusiness
	 */
	public CitizenBusiness getUserBusiness() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getMessageBusiness
	 */
	public CommuneMessageBusiness getMessageBusiness() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCompanyBusiness
	 */
	public CompanyBusiness getCompanyBusiness() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getParticipant
	 */
	public Participant getParticipant(User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getParticipantByPK
	 */
	public Participant getParticipantByPK(Object participantPK) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCustodian
	 */
	public User getCustodian(User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#activateParticipants
	 */
	public Collection activateParticipants(String[] participantPKs, User performer) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#deactivateParticipants
	 */
	public Collection deactivateParticipants(String[] participantPKs, User performer) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#deleteParticipants
	 */
	public Map deleteParticipants(String[] participantPKs, User performer) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDiscount
	 */
	public ParticipantDiscount getDiscount(User user, Course course) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeDiscount
	 */
	public boolean storeDiscount(User user, Course course, String discount) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCompanyTypes
	 */
	public Collection getCompanyTypes() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCompanyType
	 */
	public CompanyType getCompanyType(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCompany
	 */
	public Company getCompany(User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getSeason
	 */
	public Season getSeason(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCurrentSeason
	 */
	public Season getCurrentSeason() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllSeasons
	 */
	public Collection getAllSeasons() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeSeason
	 */
	public Season storeSeason(Object pk, String name, Date startDate, Date endDate) throws FinderException, CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#canStoreSeason
	 */
	public boolean canStoreSeason(Object seasonPK, Date startDate, Date endDate) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#deleteSeason
	 */
	public boolean deleteSeason(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getPeriod
	 */
	public Period getPeriod(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllPeriods
	 */
	public Collection getAllPeriods() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllPeriods
	 */
	public Collection getAllPeriods(Season season) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storePeriod
	 */
	public Period storePeriod(Object pk, Object seasonPK, String name, Date startDate, Date endDate, int minimumWeeks, float costAmount) throws FinderException, CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#canStorePeriod
	 */
	public boolean canStorePeriod(Object periodPK, Season season, Date startDate, Date endDate) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#deletePeriod
	 */
	public boolean deletePeriod(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getConstant
	 */
	public Constant getConstant(Period period, String type) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeConstant
	 */
	public Constant storeConstant(Period period, String type, Date startDate, Date endDate) throws CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#deleteConstant
	 */
	public boolean deleteConstant(Period period, String type) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocation
	 */
	public Allocation getAllocation(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocation
	 */
	public Allocation getAllocation(Season season, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocations
	 */
	public Collection getAllocations() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#canStoreAllocation
	 */
	public boolean canStoreAllocation(Object allocationPK, Season season, int birthyearFrom, int birthyearTo) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocationAmount
	 */
	public float getAllocationAmount(Season season, User child) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#hasPaymentAllocations
	 */
	public boolean hasPaymentAllocations(Allocation allocation) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getNumberOfChildren
	 */
	public int getNumberOfChildren(int birthyearFrom, int birthyearTo) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeAllocation
	 */
	public void storeAllocation(Object pk, Season season, int birthyearFrom, int birthyearTo, float amount) throws FinderException, CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#deleteAllocation
	 */
	public boolean deleteAllocation(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getTotalAmount
	 */
	public float getTotalAmount(Season season, User child) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getNumberOfAllocations
	 */
	public int getNumberOfAllocations(Period period, User child) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getRemainder
	 */
	public float getRemainder(Season season, User child) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocations
	 */
	public Collection getAllocations(Season season, User child) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocations
	 */
	public Collection getAllocations(Course course, User child) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getLatestAllocation
	 */
	public PaymentAllocation getLatestAllocation(Course course, User child) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getLatestAllocation
	 */
	public PaymentAllocation getLatestAllocation(Division division, User child) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocations
	 */
	public Collection getAllocations(Period period, User child) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocation
	 */
	public float getAllocation(Course course) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocation
	 */
	public float getAllocation(Course course, Date fromDate, Date toDate) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocation
	 */
	public float getAllocation(Course course, Date fromDate, Date toDate, boolean costMarked) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocation
	 */
	public float getAllocation(Course course, User child) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeParentAllocations
	 */
	public void storeParentAllocations(User child, Season season, Object[] coursePKs, String[] amounts) throws RemoteException;
	
	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeParentAllocations
	 */
	public void storeParentTransferAllocations(User child, Season season, Object[] coursePKs, String[] amounts) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getApplication
	 */
	public Application getApplication(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getApplication
	 */
	public Application getApplication(Company company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getApprovedApplications
	 */
	public Collection getApprovedApplications() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getUnhandledApplications
	 */
	public Collection getUnhandledApplications() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getRejectedApplications
	 */
	public Collection getRejectedApplications() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getReminderDate
	 */
	public Timestamp getReminderDate(Application application) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getPostalCode
	 */
	public PostalCode getPostalCode(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getPostalCodes
	 */
	public Collection getPostalCodes() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getFileTypes
	 */
	public Collection getFileTypes() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeApplication
	 */
	public Application storeApplication(Object pk, User owner, User contactPerson, Map files, CompanyType type, String personalID, String phoneNumber, String faxNumber, String webPage, String email, String bankAccount, String comments, Object[] postalCodePKs, User performer) throws CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeChanges
	 */
	public void storeChanges(Application application, String phoneNumber, String faxNumber, String email, String webPage, String bankAccount, Object userPK) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#attachFiles
	 */
	public void attachFiles(Application application, Object seasonPK, Map files) throws CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#approveApplication
	 */
	public void approveApplication(Application application, User performer, Locale locale) throws CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#rejectApplication
	 */
	public void rejectApplication(Application application, User performer, Locale locale, String body) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#reactivateApplication
	 */
	public void reactivateApplication(Application application, User performer, Locale locale) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#requestInformation
	 */
	public void requestInformation(Application application, User performer, Locale locale, String message) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#reopenApplication
	 */
	public void reopenApplication(Application application, User performer, Locale locale) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#closeApplication
	 */
	public void closeApplication(Application application, User performer, Locale locale, String message) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#sendReminder
	 */
	public void sendReminder(User performer, Locale locale) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getNonApprovedCourses
	 */
	public Collection getNonApprovedCourses() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getApprovedCourses
	 */
	public Collection getApprovedCourses(Season season) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getRejectedCourses
	 */
	public Collection getRejectedCourses(Season season) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCourses
	 */
	public Collection getCourses(Company company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCourses
	 */
	public Collection getCourses(Object seasonPK, Object periodPK, Object companyPK, Object divisionPK, Object groupPK, Object subGroupPK) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getApprovedCourses
	 */
	public Collection getApprovedCourses(Season season, Company company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getRejectedCourses
	 */
	public Collection getRejectedCourses(Season season, Company company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCourse
	 */
	public Course getCourse(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCourse
	 */
	public Course getCourse(Group group) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getParticipants
	 */
	public Collection getParticipants(Course course) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#isPlacedAtCompany
	 */
	public boolean isPlacedAtCompany(Company company, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#isPlacedInCourse
	 */
	public boolean isPlacedInCourse(Course course, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCoursesForChild
	 */
	public Collection getCoursesForChild(User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCoursesForChild
	 */
	public Collection getCoursesForChild(Season season, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCoursesForChild
	 */
	public Collection getCoursesForChild(Company company, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCoursesForChild
	 */
	public Collection getCoursesForChild(Company company, Season season, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeCourse
	 */
	public void storeCourse(Object pk, Company company, Object periodPK, String name, Object divisionPK, Object groupPK, Object subGroupPK, float price, float cost, Date startDate, Date endDate, int numberOfHours, int maxMale, int maxFemale, User performer) throws CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#deleteCourse
	 */
	public boolean deleteCourse(Object pk, User performer) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#closeCourses
	 */
	public void closeCourses(Object[] courses, Locale locale) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#openCourses
	 */
	public void openCourses(Object[] courses, Locale locale) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#approveCourses
	 */
	public void approveCourses(Object[] courses, Locale locale) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#rejectCourses
	 */
	public void rejectCourses(Object[] courses, Locale locale) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getNumberOfRegistrations
	 */
	public int getNumberOfRegistrations(Course course) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivision
	 */
	public Division getDivision(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivisions
	 */
	public Collection getDivisions() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getNonApprovedDivisions
	 */
	public Collection getNonApprovedDivisions(Company company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getApprovedDivisions
	 */
	public Collection getApprovedDivisions(Company company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getRejectedDivisions
	 */
	public Collection getRejectedDivisions(Company company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllDivisionsByCompany
	 */
	public Collection getAllDivisionsByCompany(IWUserContext iwuc, Company company, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivisions
	 */
	public Collection getDivisions(IWUserContext iwuc, Company company, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivisions
	 */
	public Collection getDivisions(IWUserContext iwuc, Group group, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivisions
	 */
	public Collection getDivisions(IWUserContext iwuc, Group group, User user, boolean showAll) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivisionsGroup
	 */
	public Group getDivisionsGroup(Company company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivisionsGroup
	 */
	public Group getDivisionsGroup(Group group) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivisionName
	 */
	public DivisionName getDivisionName(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivisionName
	 */
	public DivisionName getDivisionName(String name, CompanyType type) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivisionNames
	 */
	public Collection getDivisionNames(CompanyType type) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivisionNames
	 */
	public Collection getDivisionNames(Company company, Division division) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeDivisionName
	 */
	public boolean storeDivisionName(Object pk, String name, CompanyType type) throws CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#removeDivisionName
	 */
	public void removeDivisionName(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeDivision
	 */
	public void storeDivision(Object pk, String name, Company company) throws CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#approveDivisions
	 */
	public void approveDivisions(Object[] divisions, Locale locale) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#rejectDivisions
	 */
	public void rejectDivisions(Object[] divisions, Locale locale) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getGroup
	 */
	public Group getGroup(Object pk) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getGroups
	 */
	public Collection getGroups(Company company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getGroups
	 */
	public Collection getGroups(Group division) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeGroup
	 */
	public void storeGroup(Object pk, String name, Object divisionPK, Company company, User performer) throws FinderException, CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeSubGroup
	 */
	public void storeSubGroup(Object pk, String name, Object parentGroupPK, Company company, User performer) throws FinderException, CreateException, RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#removeGroup
	 */
	public boolean removeGroup(Object pk, User performer) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getSubGroups
	 */
	public Collection getSubGroups(Company company) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getSubGroups
	 */
	public Collection getSubGroups(Group group) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getDivision
	 */
	public Group getDivision(Course course) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getGroup
	 */
	public Group getGroup(Course course) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getSubGroup
	 */
	public Group getSubGroup(Course course) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#registerParticipants
	 */
	public Map registerParticipants(String[] personalIDs, Object coursePK) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#importExcelFile
	 */
	public Map importExcelFile(UploadFile file, Object coursePK, int column) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllGroups
	 */
	public Map getAllGroups(String groupPK, String groupType, String country) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getPeriods
	 */
	public Map getPeriods(String seasonPK, String country) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getUserName
	 */
	public String getUserName(String personalID) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getUser
	 */
	public AdminUser getUser(String personalID, String country) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getCompanyInfo
	 */
	public CompanyDWR getCompanyInfo(String personalID, String companyPhone, String companyFax, String companyEmail, String companyWebpage, String companyBankAccount, String country) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllCourses
	 */
	public Map getAllCourses(String companyPK, String seasonPK, String periodPK, String divisionPK, String groupPK, String subGroupPK, String country) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#sendMessage
	 */
	public void sendMessage(Application application, String subject, String body, User sender, User receiver, File attachment, String contentCode) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#storeUserInformation
	 */
	public void storeUserInformation(Object userPK, String workPhone, String mobilePhone, String email) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#fixPermissions
	 */
	public void fixPermissions() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getPaidEntries
	 */
	public Map getPaidEntries(IWUserContext iwuc, Company company, Date fromDate, Date toDate, Season season, Period period, Division division, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getParticipants
	 */
	public Map getParticipants(IWUserContext iwuc, Company company, Date fromDate, Date toDate, Season season, Period period, Division division, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getUnpaidEntries
	 */
	public Map getUnpaidEntries(IWUserContext iwuc, Company company, Date fromDate, Date toDate, Season season, Period period, Division division, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#markUnpaidEntries
	 */
	public void markUnpaidEntries(Collection entries) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#markUnpaidCostEntries
	 */
	public void markUnpaidCostEntries(Map entries, Period period) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getTotalAllocationValues
	 */
	public CompanyHolder getTotalAllocationValues(CompanyHolder holder, Season season, Period period, Date fromDate, Date toDate) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getTotalCostsValues
	 */
	public CompanyHolder getTotalCostsValues(IWUserContext iwuc, CompanyHolder holder, Season season, Period period, Date fromDate, Date toDate, User user) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getNumberOfAllocations
	 */
	public int getNumberOfAllocations(Collection courses, Date fromDate, Date toDate, boolean markedEntries) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getAllocationAmount
	 */
	public float getAllocationAmount(Collection courses, Date fromDate, Date toDate, boolean markedEntries) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.FSKBusinessBean#getInceptionYear
	 */
	public int getInceptionYear() throws RemoteException;
}