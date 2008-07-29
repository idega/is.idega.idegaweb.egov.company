/*
 * $Id: FSKBusinessBean.java,v 1.1 2008/07/29 12:57:44 anton Exp $ Created on Jun 8, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package is.idega.idegaweb.egov.company.business;

import is.idega.block.family.business.FamilyLogic;
import is.idega.block.family.data.FamilyMember;
import is.idega.idegaweb.egov.accounting.business.CitizenBusiness;
import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.data.Allocation;
import is.idega.idegaweb.egov.company.data.AllocationHome;
import is.idega.idegaweb.egov.company.data.Application;
import is.idega.idegaweb.egov.company.data.ApplicationHome;
import is.idega.idegaweb.egov.company.data.Constant;
import is.idega.idegaweb.egov.company.data.ConstantHome;
import is.idega.idegaweb.egov.company.data.Course;
import is.idega.idegaweb.egov.company.data.CourseHome;
import is.idega.idegaweb.egov.company.data.Division;
import is.idega.idegaweb.egov.company.data.DivisionHome;
import is.idega.idegaweb.egov.company.data.DivisionName;
import is.idega.idegaweb.egov.company.data.DivisionNameHome;
import is.idega.idegaweb.egov.company.data.Participant;
import is.idega.idegaweb.egov.company.data.ParticipantDiscount;
import is.idega.idegaweb.egov.company.data.ParticipantDiscountHome;
import is.idega.idegaweb.egov.company.data.ParticipantHome;
import is.idega.idegaweb.egov.company.data.PaymentAllocation;
import is.idega.idegaweb.egov.company.data.PaymentAllocationHome;
import is.idega.idegaweb.egov.company.data.Period;
import is.idega.idegaweb.egov.company.data.PeriodHome;
import is.idega.idegaweb.egov.company.data.Season;
import is.idega.idegaweb.egov.company.data.SeasonHome;
import is.idega.idegaweb.egov.message.business.CommuneMessageBusiness;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.transaction.UserTransaction;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.idega.block.pdf.business.PrintingContext;
import com.idega.block.pdf.business.PrintingService;
import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.business.CaseBusinessBean;
import com.idega.block.process.data.CaseLog;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.CompanyConstants;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.accesscontrol.business.AccessControl;
import com.idega.core.accesscontrol.business.AccessController;
import com.idega.core.accesscontrol.business.LoginCreateException;
import com.idega.core.accesscontrol.data.ICPermission;
import com.idega.core.accesscontrol.data.LoginTable;
import com.idega.core.accesscontrol.data.PasswordNotKnown;
import com.idega.core.builder.data.ICDomain;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.EmailHome;
import com.idega.core.contact.data.EmailType;
import com.idega.core.contact.data.EmailTypeHome;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.PhoneBMPBean;
import com.idega.core.contact.data.PhoneHome;
import com.idega.core.file.data.ICFile;
import com.idega.core.file.data.ICFileHome;
import com.idega.core.location.business.AddressBusiness;
import com.idega.core.location.business.CommuneBusiness;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.Commune;
import com.idega.core.location.data.PostalCode;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOCreateException;
import com.idega.data.IDOException;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.idegaweb.IWUserContext;
import com.idega.io.MemoryFileBuffer;
import com.idega.io.MemoryInputStream;
import com.idega.io.MemoryOutputStream;
import com.idega.io.UploadFile;
import com.idega.user.business.NoEmailFoundException;
import com.idega.user.business.NoPhoneFoundException;
import com.idega.user.data.Group;
import com.idega.user.data.GroupDomainRelation;
import com.idega.user.data.GroupDomainRelationHome;
import com.idega.user.data.GroupRelation;
import com.idega.user.data.GroupRelationHome;
import com.idega.user.data.User;
import com.idega.user.data.UserHome;
import com.idega.util.Age;
import com.idega.util.FileUtil;
import com.idega.util.IWTimestamp;
import com.idega.util.ListUtil;
import com.idega.util.text.Name;
import com.idega.util.text.SocialSecurityNumber;
import com.idega.util.text.TextSoap;

public class FSKBusinessBean extends CaseBusinessBean implements FSKBusiness, CaseBusiness {

	protected String getBundleIdentifier() {
		return FSKConstants.IW_BUNDLE_IDENTIFIER;
	}

	// IBOLookup
	public CitizenBusiness getUserBusiness() {
		try {
			return (CitizenBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), CitizenBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private FamilyLogic getFamilyLogic() {
		try {
			return (FamilyLogic) IBOLookup.getServiceInstance(getIWApplicationContext(), FamilyLogic.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private AddressBusiness getAddressBusiness() {
		try {
			return (AddressBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), AddressBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private CommuneBusiness getCommuneBusiness() {
		try {
			return (CommuneBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), CommuneBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	public CommuneMessageBusiness getMessageBusiness() {
		try {
			return (CommuneMessageBusiness) this.getServiceInstance(CommuneMessageBusiness.class);
		}
		catch (RemoteException e) {
			throw new IBORuntimeException(e.getMessage());
		}
	}

	public CompanyBusiness getCompanyBusiness() {
		try {
			return (CompanyBusiness) IBOLookup.getServiceInstance(getIWApplicationContext(), CompanyBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private PrintingService getPrintingService() {
		try {
			return (PrintingService) getServiceInstance(PrintingService.class);
		}
		catch (RemoteException e) {
			throw new IBORuntimeException(e.getMessage());
		}
	}

	// IDOLookup
	private SeasonHome getSeasonHome() {
		try {
			return (SeasonHome) getIDOHome(Season.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private PeriodHome getPeriodHome() {
		try {
			return (PeriodHome) getIDOHome(Period.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private ConstantHome getConstantHome() {
		try {
			return (ConstantHome) getIDOHome(Constant.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private AllocationHome getAllocationHome() {
		try {
			return (AllocationHome) getIDOHome(Allocation.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private PaymentAllocationHome getPaymentAllocationHome() {
		try {
			return (PaymentAllocationHome) getIDOHome(PaymentAllocation.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private ApplicationHome getApplicationHome() {
		try {
			return (ApplicationHome) getIDOHome(Application.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private CourseHome getCourseHome() {
		try {
			return (CourseHome) getIDOHome(Course.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private DivisionHome getDivisionHome() {
		try {
			return (DivisionHome) getIDOHome(Division.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private DivisionNameHome getDivisionNameHome() {
		try {
			return (DivisionNameHome) getIDOHome(DivisionName.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private ParticipantHome getParticipantHome() {
		try {
			return (ParticipantHome) getIDOHome(Participant.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private ParticipantDiscountHome getParticipantDiscountHome() {
		try {
			return (ParticipantDiscountHome) getIDOHome(ParticipantDiscount.class);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	//Participant
	public Participant getParticipant(User user) {
		try {
			return getParticipantHome().findByPrimaryKey(user.getPrimaryKey());
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Participant getParticipantByPK(Object participantPK) {
		try {
			return getParticipantHome().findByPrimaryKey(new Integer(participantPK.toString()));
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getCustodian(User user) {
		try {
			try {
				FamilyMember familyMember = getFamilyLogic().getFamilyMember(user);
				return getUserBusiness().getUser(familyMember.getFamilyNr());
			}
			catch (FinderException fe) {
				return getUserBusiness().getCustodianForChild(user);
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public Collection activateParticipants(String[] participantPKs, User performer) {
		Collection collection = new ArrayList();

		for (int i = 0; i < participantPKs.length; i++) {
			String participantPK = participantPKs[i].substring(0, participantPKs[i].indexOf("|"));
			String coursePK = participantPKs[i].substring(participantPKs[i].indexOf("|") + 1);

			Course course = getCourse(coursePK);

			try {
				Participant participant = getParticipantHome().findByPrimaryKey(new Integer(participantPK));
				participant.setActive(course, true);
				participant.store();

				collection.add(new ParticipantHolder(participant, course));
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
		}

		return collection;
	}

	public Collection deactivateParticipants(String[] participantPKs, User performer) {
		Collection collection = new ArrayList();

		for (int i = 0; i < participantPKs.length; i++) {
			String participantPK = participantPKs[i].substring(0, participantPKs[i].indexOf("|"));
			String coursePK = participantPKs[i].substring(participantPKs[i].indexOf("|") + 1);

			Course course = getCourse(coursePK);

			try {
				Participant participant = getParticipantHome().findByPrimaryKey(new Integer(participantPK));
				participant.setActive(course, false);
				participant.store();

				collection.add(new ParticipantHolder(participant, course));
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
		}

		return collection;
	}

	public Map deleteParticipants(String[] participantPKs, User performer) {
		Map map = new HashMap();

		Collection removed = new ArrayList();
		Collection nonRemoved = new ArrayList();

		for (int i = 0; i < participantPKs.length; i++) {
			String participantPK = participantPKs[i].substring(0, participantPKs[i].indexOf("|"));
			String coursePK = participantPKs[i].substring(participantPKs[i].indexOf("|") + 1);

			Course course = getCourse(coursePK);
			Group group = course.getGroup();

			try {
				Participant participant = getParticipantHome().findByPrimaryKey(new Integer(participantPK));

				if (getAllocation(course, participant) > 0) {
					nonRemoved.add(new ParticipantHolder(participant, course));
				}
				else {
					group.removeGroup(participant, performer);
					removed.add(new ParticipantHolder(participant, course));
				}
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
		}

		map.put(FSKConstants.DELETE_PARTICIPANTS_REMOVED, removed);
		map.put(FSKConstants.DELETE_PARTICIPANTS_NON_REMOVED, nonRemoved);

		return map;
	}

	public ParticipantDiscount getDiscount(User user, Course course) {
		try {
			return getParticipantDiscountHome().findByParticipantAndCourse(user, course);
		}
		catch (FinderException fe) {
			//No discount found...
		}

		return null;
	}

	public boolean storeDiscount(User user, Course course, String discount) {
		ParticipantDiscount courseDiscount = getDiscount(user, course);
		if (courseDiscount == null) {
			try {
				courseDiscount = getParticipantDiscountHome().create();
			}
			catch (CreateException e) {
				e.printStackTrace();
				return false;
			}
		}

		if ((discount == null || discount.length() == 0) && courseDiscount != null) {
			try {
				courseDiscount.remove();
			}
			catch (RemoveException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		float amount = 0;
		boolean percentage = false;
		if (discount.indexOf("%") != -1) {
			discount = TextSoap.findAndCut(discount, "%");
			percentage = true;
		}
		else {
			discount = TextSoap.findAndCut(discount, ".");
		}

		if (discount.indexOf(",") != -1) {
			discount = TextSoap.findAndReplace(discount, ",", ".");
		}

		try {
			amount = Float.parseFloat(discount);
		}
		catch (NumberFormatException nfe) {
			log(nfe);
			return false;
		}

		if (percentage) {
			amount = amount / 100;
			if (amount > 1) {
				return false;
			}
		}
		else {
			if (amount > course.getPrice()) {
				return false;
			}
		}

		courseDiscount.setParticipant(user);
		courseDiscount.setCourse(course);
		courseDiscount.setDiscount(amount);
		if (percentage) {
			courseDiscount.setAsPercentage();
		}
		else {
			courseDiscount.setAsAmount();
		}
		courseDiscount.store();

		return true;
	}

	// Company
	public Collection getCompanyTypes() {
		try {
			return getCompanyBusiness().getTypes();
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public CompanyType getCompanyType(Object pk) {
		try {
			return getCompanyBusiness().getCompanyType(pk);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Company getCompany(User user) {
		try {
			String[] types = { CompanyConstants.GROUP_TYPE_COMPANY };
			Collection groups = getUserBusiness().getUserGroups(user, types, true);
			if (groups != null) {
				Iterator iterator = groups.iterator();
				while (iterator.hasNext()) {
					Group group = (Group) iterator.next();
					return getCompanyBusiness().getCompany(group);
				}
			}
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		catch (FinderException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Group getRootCompanyGroup() throws CreateException, FinderException, RemoteException {
		Group rootGroup = null;
		String groupId = getIWApplicationContext().getApplicationSettings().getProperty(FSKConstants.PROPERTY_COMPANY_GROUP);
		if (groupId != null) {
			rootGroup = getUserBusiness().getGroupHome().findByPrimaryKey(new Integer(groupId));
		}
		else {
			System.err.println("Trying to store root company group");

			String name = getLocalizedString("company_group.name", "Company group", getIWApplicationContext().getApplicationSettings().getDefaultLocale());
			String description = getLocalizedString("company_group.description", "The root company group", getIWApplicationContext().getApplicationSettings().getDefaultLocale());

			rootGroup = getUserBusiness().getGroupBusiness().createGroup(name, description);
			getIWApplicationContext().getApplicationSettings().setProperty(FSKConstants.PROPERTY_COMPANY_GROUP, rootGroup.getPrimaryKey().toString());
		}
		return rootGroup;
	}

	private Group getRootFSKGroup() throws FinderException, RemoteException {
		Group rootGroup = null;
		String groupId = getIWApplicationContext().getApplicationSettings().getProperty(FSKConstants.PROPERTY_FSK_GROUP);
		if (groupId != null) {
			rootGroup = getUserBusiness().getGroupHome().findByPrimaryKey(new Integer(groupId));
		}
		return rootGroup;
	}

	// Season
	public Season getSeason(Object pk) {
		if (pk != null) {
			try {
				return getSeasonHome().findByPrimaryKey(new Integer(pk.toString()));
			}
			catch (FinderException fe) {
				log(fe);
			}
		}
		return null;
	}

	public Season getCurrentSeason() {
		try {
			IWTimestamp stamp = new IWTimestamp();
			return getSeasonHome().findByDate(stamp.getDate());
		}
		catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Collection getAllSeasons() {
		try {
			return getSeasonHome().findAll();
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Season storeSeason(Object pk, String name, Date startDate, Date endDate) throws FinderException, CreateException {
		Season season = null;
		if (pk != null) {
			season = getSeason(pk);
		}
		else {
			season = getSeasonHome().create();
		}

		season.setName(name);
		season.setStartDate(startDate);
		season.setEndDate(endDate);
		season.store();

		return season;
	}

	public boolean canStoreSeason(Object seasonPK, Date startDate, Date endDate) {
		try {
			return getSeasonHome().getNumberOfSeasonsInPeriod(seasonPK, startDate, endDate) == 0;
		}
		catch (IDOException e) {
			e.printStackTrace();
			return true;
		}
	}

	public boolean deleteSeason(Object pk) {
		try {
			Season season = getSeasonHome().findByPrimaryKey(new Integer(pk.toString()));
			season.remove();

			return true;
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
		catch (RemoveException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Period
	public Period getPeriod(Object pk) {
		if (pk != null) {
			try {
				return getPeriodHome().findByPrimaryKey(new Integer(pk.toString()));
			}
			catch (FinderException fe) {
				log(fe);
			}
		}
		return null;
	}

	public Collection getAllPeriods() {
		try {
			return getPeriodHome().findAll();
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getAllPeriods(Season season) {
		try {
			return getPeriodHome().findAllBySeason(season);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Period storePeriod(Object pk, Object seasonPK, String name, Date startDate, Date endDate, int minimumWeeks, float costAmount) throws FinderException, CreateException {
		Period period = null;
		if (pk != null) {
			period = getPeriod(pk);
		}
		else {
			period = getPeriodHome().create();
		}

		period.setSeason(getSeason(seasonPK));
		period.setName(name);
		period.setStartDate(startDate);
		period.setEndDate(endDate);
		period.setMinimumWeeks(minimumWeeks);
		period.setCostAmount(costAmount);
		period.store();

		return period;
	}

	public boolean canStorePeriod(Object periodPK, Season season, Date startDate, Date endDate) {
		try {
			return getPeriodHome().getNumberOfPeriodsInPeriod(periodPK, season, startDate, endDate) == 0;
		}
		catch (IDOException e) {
			e.printStackTrace();
			return true;
		}
	}

	public boolean deletePeriod(Object pk) {
		try {
			Period period = getPeriodHome().findByPrimaryKey(new Integer(pk.toString()));
			period.remove();

			return true;
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
		catch (RemoveException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Constants
	public Constant getConstant(Period period, String type) {
		try {
			return getConstantHome().findByType(period, type);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Constant storeConstant(Period period, String type, Date startDate, Date endDate) throws CreateException {
		Constant constant = null;
		try {
			constant = getConstantHome().findByType(period, type);
		}
		catch (FinderException fe) {
			constant = getConstantHome().create();
			constant.setPeriod(period);
			constant.setType(type);
		}

		constant.setStartDate(startDate);
		constant.setEndDate(endDate);
		constant.store();

		return constant;
	}

	public boolean deleteConstant(Period period, String type) {
		try {
			Constant constant = getConstantHome().findByType(period, type);
			constant.remove();

			return true;
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
		catch (RemoveException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Allocations
	public Allocation getAllocation(Object pk) {
		if (pk != null) {
			try {
				return getAllocationHome().findByPrimaryKey(new Integer(pk.toString()));
			}
			catch (FinderException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Allocation getAllocation(Season season, User user) {
		if (user.getDateOfBirth() != null) {
			IWTimestamp birthDate = new IWTimestamp(user.getDateOfBirth());

			try {
				return getAllocationHome().findByBirthyear(season, birthDate.getYear());
			}
			catch (FinderException fe) {
				log(fe);
			}
		}
		return null;
	}

	public Collection getAllocations() {
		try {
			return getAllocationHome().findAll();
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public boolean canStoreAllocation(Object allocationPK, Season season, int birthyearFrom, int birthyearTo) {
		try {
			return getAllocationHome().getNumberOfAllocationsInPeriod(allocationPK, season, birthyearFrom, birthyearTo) == 0;
		}
		catch (IDOException e) {
			e.printStackTrace();
			return true;
		}
	}

	public float getAllocationAmount(Season season, User child) {
		try {
			IWTimestamp stamp = new IWTimestamp(child.getDateOfBirth());
			Allocation allocation = getAllocationHome().findByBirthyear(season, stamp.getYear());
			return allocation.getAmount();
		}
		catch (FinderException fe) {
			log(fe);
			return 0;
		}
	}

	public boolean hasPaymentAllocations(Allocation allocation) {
		try {
			return getPaymentAllocationHome().getNumberOfAllocations(allocation) > 0;
		}
		catch (IDOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*public void lockAllocations(Object coursePK, Object[] userPKs) {
		try {
			Course course = getCourse(coursePK);

			for (int i = 0; i < userPKs.length; i++) {
				Object userPK = userPKs[i];
				User user = getUserBusiness().getUser(new Integer(userPK.toString()));

				try {
					PaymentAllocation allocation = getPaymentAllocationHome().findByUserAndCourse(user, course);
					allocation.setLocked(true);
					allocation.store();
				}
				catch (FinderException fe) {
					fe.printStackTrace();
				}
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}*/

	public int getNumberOfChildren(int birthyearFrom, int birthyearTo) {
		try {
			Commune commune = getCommuneBusiness().getDefaultCommune();

			UserHome home = (UserHome) getIDOHome(User.class);
			return home.getCountByBirthYearAndCommune(birthyearFrom, birthyearTo, commune);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		catch (IDOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void storeAllocation(Object pk, Season season, int birthyearFrom, int birthyearTo, float amount) throws FinderException, CreateException {
		Allocation allocation = null;
		if (pk != null) {
			allocation = getAllocationHome().findByPrimaryKey(pk);
		}
		else {
			allocation = getAllocationHome().create();
		}

		allocation.setCreationDate(new IWTimestamp().getDate());
		allocation.setSeason(season);
		allocation.setBirthyearFrom(birthyearFrom);
		allocation.setBirthyearTo(birthyearTo);
		allocation.setAmount(amount);
		allocation.store();
	}

	public boolean deleteAllocation(Object pk) {
		try {
			Allocation allocation = getAllocationHome().findByPrimaryKey(pk);
			allocation.remove();

			return true;
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
		catch (RemoveException e) {
			e.printStackTrace();
		}
		return false;
	}

	// Parent allocations
	public float getTotalAmount(Season season, User child) {
		try {
			return getPaymentAllocationHome().getTotalAmount(season, child);
		}
		catch (IDOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getNumberOfAllocations(Period period, User child) {
		try {
			return getPaymentAllocationHome().getNumberOfAllocations(period, child);
		}
		catch (IDOException ie) {
			ie.printStackTrace();
			return 0;
		}
	}

	public float getRemainder(Season season, User child) {
		Allocation allocation = getAllocation(season, child);
		if (allocation != null) {
			return allocation.getAmount() - getTotalAmount(season, child);
		}
		return 0;
	}

	public Collection getAllocations(Season season, User child) {
		try {
			return getPaymentAllocationHome().findByUser(season, child);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getAllocations(Course course, User child) {
		try {
			return getPaymentAllocationHome().findByUser(course, child);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public PaymentAllocation getLatestAllocation(Course course, User child) {
		try {
			return getPaymentAllocationHome().findLatestByUser(course, child);
		}
		catch (FinderException e) {
			return null;
		}
	}

	public PaymentAllocation getLatestAllocation(Division division, User child) {
		Company company = division.getCompany();
		PaymentAllocation currentAllocation = null;

		Collection courses = getCoursesForChild(company, child);
		Iterator iter = courses.iterator();
		while (iter.hasNext()) {
			Course course = (Course) iter.next();
			Group courseDivision = getDivision(course);
			if (courseDivision.getPrimaryKey().equals(division.getPrimaryKey())) {
				try {
					PaymentAllocation allocation = getPaymentAllocationHome().findLatestByUser(course, child);
					if (currentAllocation != null) {
						if (allocation.getEntryDate().getTime() > currentAllocation.getEntryDate().getTime()) {
							currentAllocation = allocation;
						}
					}
					else {
						currentAllocation = allocation;
					}
				}
				catch (FinderException fe) {
					//Nothing found...
				}
			}
		}

		return currentAllocation;
	}

	public Collection getAllocations(Period period, User child) {
		try {
			return getPaymentAllocationHome().findByUser(period, child);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public float getAllocation(Course course) {
		try {
			return getPaymentAllocationHome().getAmount(course);
		}
		catch (IDOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public float getAllocation(Course course, Date fromDate, Date toDate) {
		try {
			return getPaymentAllocationHome().getAmount(course, fromDate, toDate);
		}
		catch (IDOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public float getAllocation(Course course, Date fromDate, Date toDate, boolean costMarked) {
		try {
			return getPaymentAllocationHome().getAmount(course, fromDate, toDate, costMarked);
		}
		catch (IDOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public float getAllocation(Course course, User child) {
		try {
			return getPaymentAllocationHome().getAmount(course, child);
		}
		catch (IDOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*public boolean isPaymentLocked(Course course, User child) {
		try {
			PaymentAllocation allocation = getPaymentAllocationHome().findByUserAndCourse(child, course);
			return allocation.isLocked();
		}
		catch (FinderException fe) {
			//fe.printStackTrace();
			return false;
		}
	}*/

	public void storeParentAllocations(User child, Season season, Object[] coursePKs, String[] amounts) {
		Allocation allocation = getAllocation(season, child);
		for (int i = 0; i < coursePKs.length; i++) {
			Object coursePK = coursePKs[i];
			String amount = TextSoap.findAndReplace(amounts[i], ".", "");

			if (amount != null && amount.length() > 0) {
				Course course = getCourse(coursePK);
				try {
					storeParentAllocation(child, allocation, course, Float.parseFloat(amount));
				}
				catch (CreateException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void storeParentTransferAllocations(User child, Season season, Object[] coursePKs, String[] amounts) {
		Allocation allocation = getAllocation(season, child);
		for (int i = 0; i < coursePKs.length; i++) {
			Object coursePK = coursePKs[i];
			String amount = TextSoap.findAndReplace(amounts[i], ".", "");

			if (amount != null && amount.length() > 0) {
				Course course = getCourse(coursePK);
				try {
					storeParentTransferAllocation(child, allocation, course, Float.parseFloat(amount));
				}
				catch (CreateException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void storeParentTransferAllocation(User user, Allocation allocation, Course course, float amount) throws CreateException {
		float remainder = getRemainder(allocation.getSeason(), user);

		if (amount <= remainder && user.hasRelationTo(course.getGroup())) {
			PaymentAllocation paymentAllocation = getPaymentAllocationHome().create();

			paymentAllocation.setAllocation(allocation);
			paymentAllocation.setUser(user);
			paymentAllocation.setCourse(course);
			paymentAllocation.setAmount(amount);
			paymentAllocation.setTransfer(true);
			paymentAllocation.setLocked(true);
			paymentAllocation.setEntryDate(IWTimestamp.getTimestampRightNow());
			paymentAllocation.store();
		}
	}

	private void storeParentAllocation(User user, Allocation allocation, Course course, float amount) throws CreateException {
		float remainder = getRemainder(allocation.getSeason(), user);

		if (amount <= remainder && user.hasRelationTo(course.getGroup())) {
			PaymentAllocation paymentAllocation = getPaymentAllocationHome().create();

			paymentAllocation.setAllocation(allocation);
			paymentAllocation.setUser(user);
			paymentAllocation.setCourse(course);
			paymentAllocation.setAmount(amount);
			paymentAllocation.setLocked(true);
			paymentAllocation.setEntryDate(IWTimestamp.getTimestampRightNow());
			paymentAllocation.store();
		}
	}

	// Application
	public Application getApplication(Object pk) {
		try {
			return getApplicationHome().findByPrimaryKey(new Integer(pk.toString()));
		}
		catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Application getApplication(Company company) {
		try {
			return getApplicationHome().findByCompany(company);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Collection getApprovedApplications() {
		try {
			String[] status = { getCaseStatusGranted().getStatus(), getCaseStatusCancelled().getStatus() };
			return getApplicationHome().findByStatus(status);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getUnhandledApplications() {
		try {
			String[] status = { getCaseStatusOpen().getStatus(), getCaseStatusReview().getStatus() };
			return getApplicationHome().findByStatus(status);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getRejectedApplications() {
		try {
			String[] status = { getCaseStatusDenied().getStatus() };
			return getApplicationHome().findByStatus(status);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Timestamp getReminderDate(Application application) {
		if (application.getCaseStatus().equals(getCaseStatusReview())) {
			try {
				CaseLog log = getCaseLogHome().findLastCaseLogForCase(application);
				return log.getTimeStamp();
			}
			catch (FinderException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public PostalCode getPostalCode(Object pk) {
		try {
			return getAddressBusiness().getPostalCodeHome().findByPrimaryKey(new Integer(pk.toString()));
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Collection getPostalCodes() {
		try {
			List codes = ListUtil.convertStringArrayToList(FSKConstants.DEFAULT_POSTAL_CODES);
			String codeString = getIWApplicationContext().getApplicationSettings().getProperty(FSKConstants.PROPERTY_POSTAL_CODES, ListUtil.convertListOfStringsToCommaseparatedString(codes));
			codes = ListUtil.convertCommaSeparatedStringToList(codeString);
			return getAddressBusiness().getPostalCodeHome().findByPostalCode(codes);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getFileTypes() {
		Collection types = new ArrayList();
		types.add(FSKConstants.FILE_TYPE_TARIFF);
		types.add(FSKConstants.FILE_TYPE_SCHEDULE);
		types.add(FSKConstants.FILE_TYPE_OTHER);

		return types;
	}

	public Application storeApplication(Object pk, User owner, User contactPerson, Map files, CompanyType type, String personalID, String phoneNumber, String faxNumber, String webPage, String email, String bankAccount, String comments, Object[] postalCodePKs, User performer) throws CreateException {
		UserTransaction trans = getSessionContext().getUserTransaction();

		try {
			trans.begin();

			Application application = null;
			if (pk != null) {
				application = getApplication(pk);
				try {
					application.removeAllFiles();
				}
				catch (RemoveException e) {
					e.printStackTrace();
				}
				try {
					application.removeAllPostalCodes();
				}
				catch (IDORemoveRelationshipException e) {
					e.printStackTrace();
				}
			}
			if (application == null) {
				application = getApplicationHome().create();
			}

			application.setOwner(owner);
			application.setType(type);
			application.setContactPerson(contactPerson);
			application.setCreator(performer);

			CompanyDWR companyDWR = getCompanyInfo(personalID, phoneNumber, faxNumber, email, webPage, bankAccount, "IS");

			application.setName(companyDWR.getCompanyName());
			application.setPersonalID(personalID);
			application.setAddress(companyDWR.getCompanyAddress());
			application.setPostalCode(companyDWR.getCompanyPostalCode());
			application.setCity(companyDWR.getCompanyCity());
			application.setPhoneNumber(phoneNumber);
			application.setFaxNumber(faxNumber);
			application.setWebPage(webPage);
			application.setEmail(email);
			application.setBankAccount(bankAccount);
			application.setComments(comments);
			application.store();

			User companyUser = this.getUserBusiness().createUserByPersonalIDIfDoesNotExist(companyDWR.getCompanyName(), application.getPersonalID(), null, null);
			this.getUserBusiness().addNewUserEmail(((Integer) companyUser.getPrimaryKey()).intValue(), application.getEmail());
			application.setAdminUser(companyUser);

			Company company = storeCompany(application, getRootFSKGroup());
			application.setCompany(company);

			changeCaseStatus(application, getCaseStatusOpen(), performer);
			createLoginAndSendMessage(companyUser, contactPerson, getDefaultLocale());

			if (files != null) {
				Season season = getCurrentSeason();
				Iterator iterator = files.keySet().iterator();
				while (iterator.hasNext()) {
					ICFile file = (ICFile) iterator.next();
					String fileType = (String) files.get(file);

					application.addFile(file, fileType, season);
				}
			}

			if (postalCodePKs != null) {
				for (int i = 0; i < postalCodePKs.length; i++) {
					Object postalCodePK = postalCodePKs[i];
					try {
						application.addPostalCode(postalCodePK);
					}
					catch (IDOAddRelationshipException e) {
						e.printStackTrace();
					}
				}
			}

			String subject = getLocalizedString("application.new_application_subject", "New application sent in", getDefaultLocale());
			String body = getLocalizedString("application.new_application_body", "A new application has been sent in by {0}, {1}.", getDefaultLocale());

			Group fskGroup = getRootFSKGroup();
			Collection users = getUserBusiness().getUsersInGroup(fskGroup);
			Iterator iterator = users.iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();
				sendMessage(application, subject, body, null, user, null, null);
			}

			trans.commit();
			return application;
		}
		catch (Exception ex) {
			try {
				trans.rollback();
			}
			catch (javax.transaction.SystemException e) {
				throw new IDOCreateException(e.getMessage());
			}
			ex.printStackTrace();
			throw new IDOCreateException(ex);
		}
	}

	public void storeChanges(Application application, String phoneNumber, String faxNumber, String email, String webPage, String bankAccount, Object userPK) {
		Company company = application.getCompany();
		if (phoneNumber != null && phoneNumber.length() > 0) {
			application.setPhoneNumber(phoneNumber);

			Phone phone = company.getPhone();
			phone.setNumber(phoneNumber);
			phone.store();
		}

		if (faxNumber != null && faxNumber.length() > 0) {
			application.setFaxNumber(faxNumber);

			Phone phone = company.getFax();
			phone.setNumber(faxNumber);
			phone.store();
		}

		if (email != null && email.length() > 0) {
			application.setEmail(email);

			Email mail = company.getEmail();
			mail.setEmailAddress(email);
			mail.store();
		}

		if (webPage != null && webPage.length() > 0) {
			application.setWebPage(webPage);
			company.setWebPage(webPage);
		}

		if (bankAccount != null && bankAccount.length() > 0) {
			application.setBankAccount(bankAccount);
			company.setBankAccount(bankAccount);
		}

		if (userPK != null) {
			try {
				User user = getUserBusiness().getUser(new Integer(userPK.toString()));
				application.setContactPerson(user);
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}

		application.store();
		company.store();
	}

	public void attachFiles(Application application, Object seasonPK, Map files) throws CreateException {
		if (files != null) {
			Season season = getSeason(seasonPK);

			Iterator iterator = files.keySet().iterator();
			while (iterator.hasNext()) {
				ICFile file = (ICFile) iterator.next();
				String fileType = (String) files.get(file);

				application.addFile(file, fileType, season);
			}
		}
	}

	public void approveApplication(Application application, User performer, Locale locale) throws CreateException {
		UserTransaction trans = getSessionContext().getUserTransaction();

		try {
			trans.begin();

			Company company = application.getCompany();
			company.setValid(true);
			company.setOpen(true);
			company.store();

			ICFile contractFile = createContract(new ContractPrintingContext(getIWApplicationContext(), application, locale), application, locale);

			application.setContractFile(contractFile);
			application.setCompany(company);
			changeCaseStatus(application, getCaseStatusGranted(), performer);

			File file = FileUtil.streamToFile(contractFile.getFileValue(), getBundle().getResourcesRealPath(), contractFile.getName());

			String subject = getLocalizedString("application.approved_message_subject", "Company account application approved", locale);
			String body = getLocalizedString("application.approved_message_body", "Your application for company account for {0}, {1} has been approved.", locale);
			sendMessage(application, subject, body, null, application.getOwner(), file, FSKConstants.CONTENT_CODE_APPROVE_APPLICATION);
			sendMessage(application, subject, body, null, application.getAdminUser(), file, FSKConstants.CONTENT_CODE_APPROVE_APPLICATION);
			file.delete();

			trans.commit();
		}
		catch (Exception ex) {
			try {
				trans.rollback();
			}
			catch (javax.transaction.SystemException e) {
				throw new IDOCreateException(e.getMessage());
			}
			ex.printStackTrace();
			throw new IDOCreateException(ex);
		}
	}

	private Company storeCompany(Application application, Group adminGroup) throws CreateException, FinderException {
		try {
			Company company = null;
			try {
				company = getCompanyBusiness().getCompany(application.getPersonalID());
			}
			catch (FinderException fe) {
				fe.printStackTrace();

				return null;
				//company = getCompanyBusiness().storeCompany(application.getName(), application.getPersonalID());
			}
			company.setType(application.getType());
			company.setBankAccount(application.getBankAccount());
			company.setWebPage(application.getWebPage());
			company.setOpen(false);
			company.store();

			Group rootCompanyGroup = getRootCompanyGroup();
			Group companyGroup = company.getGroup();
			getIWApplicationContext().getIWMainApplication().getAccessController().addRoleToGroup(FSKConstants.ROLE_KEY_FSK_COMPANY, companyGroup, getIWApplicationContext());
			companyGroup.setHomePageID(rootCompanyGroup.getHomePageID());
			companyGroup.store();

			rootCompanyGroup.addGroup(companyGroup);
			if (companyGroup.getChildCount() <= 0) {
				createSubGroups(companyGroup, application.getAdminUser());
			}
			setPermissions(adminGroup, companyGroup);

			/*Address address = company.getAddress();
			if (address == null) {
				address = getAddressBusiness().getAddressHome().create();
			}
			PostalCode code = getAddressBusiness().getPostalCodeAndCreateIfDoesNotExist(application.getPostalCode(), application.getCity());
			address.setAddressType(getAddressBusiness().getMainAddressType());
			address.setPostalCode(code);
			address.setCommune(code.getCommune());
			address.setCountry(code.getCountry());
			address.setStreetName(getAddressBusiness().getStreetNameFromAddressString(application.getAddress()));
			address.setStreetNumber(getAddressBusiness().getStreetNumberFromAddressString(application.getAddress()));
			address.store();
			try {
				companyGroup.addAddress(address);
			}
			catch (IDOAddRelationshipException e) {
				log(e);
			}*/

			PhoneHome home = (PhoneHome) getIDOHome(Phone.class);
			Phone phone = company.getPhone();
			if (phone == null) {
				phone = home.create();
			}
			phone.setPhoneTypeId(PhoneBMPBean.getHomeNumberID());
			phone.setNumber(application.getPhoneNumber());
			phone.store();
			try {
				companyGroup.addPhone(phone);
			}
			catch (IDOAddRelationshipException e) {
				log(e);
			}

			phone = company.getFax();
			if (phone == null) {
				phone = home.create();
			}
			phone.setPhoneTypeId(PhoneBMPBean.getFaxNumberID());
			phone.setNumber(application.getFaxNumber());
			phone.store();
			try {
				companyGroup.addPhone(phone);
			}
			catch (IDOAddRelationshipException e) {
				log(e);
			}

			EmailHome eHome = (EmailHome) getIDOHome(Email.class);
			Email email = company.getEmail();
			if (email == null) {
				email = eHome.create();
			}
			email.setEmailType(((EmailTypeHome) getIDOHome(EmailType.class)).findMainEmailType());
			email.setEmailAddress(application.getEmail());
			email.store();
			try {
				companyGroup.addEmail(email);
			}
			catch (IDOAddRelationshipException e) {
				log(e);
			}

			return company;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private void createLoginAndSendMessage(User company, User contactPerson, Locale locale) throws RemoteException, CreateException, LoginCreateException {
		try {

			String messageBody = null;
			String messageSubject = null;
			if (getUserBusiness().hasUserLogin(company)) {
				messageBody = getLocalizedString("company_account.has_account_body", "Your company account is now activated.  You can log in with your current login for the server", locale);
				messageSubject = getLocalizedString("company_account.has_account_subject", "Company account activated", locale);
			}
			else {
				LoginTable lt = getUserBusiness().generateUserLogin(company);
				String login = lt.getUserLogin();
				String password = lt.getUnencryptedUserPassword();

				Object[] arguments = { login, password };

				messageBody = MessageFormat.format(getLocalizedString("company_account.new_account_body", "Your company account is now activated.  You can log in with the following login info. Login: {0}, password: {1}.", locale), arguments);
				messageSubject = getLocalizedString("company_account.new_account_subject", "Company account activated", locale);
			}

			this.getMessageBusiness().createUserMessage(company, messageSubject, messageBody);
			this.getMessageBusiness().createUserMessage(contactPerson, messageSubject, messageBody);
		}
		catch (PasswordNotKnown e) {
			throw new IDOCreateException(e);
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void createSubGroups(Group group, User adminUser) {
		AccessController access = getIWApplicationContext().getIWMainApplication().getAccessController();

		try {
			String name = getLocalizedString("company_group.admin_name", "Admin", getIWApplicationContext().getApplicationSettings().getDefaultLocale());
			String description = getLocalizedString("company_group.admin_description", "Admin users", getIWApplicationContext().getApplicationSettings().getDefaultLocale());

			Group adminGroup = getUserBusiness().getGroupBusiness().createGroup(name, description, getUserBusiness().getGroupBusiness().getGroupTypeHome().getPermissionGroupTypeString(), group.getHomePageID(), -1, false, group);
			access.addRoleToGroup(FSKConstants.ROLE_KEY_FSK_COMPANY_ADMIN, adminGroup, getIWApplicationContext());
			access.addRoleToGroup(FSKConstants.ROLE_KEY_FSK_COMPANY, adminGroup, getIWApplicationContext());
			if (adminUser != null) {
				adminGroup.addGroup(adminUser);
				if (adminUser.getPrimaryGroupID() < 0) {
					adminUser.setPrimaryGroup(adminGroup);
					adminUser.store();
				}
			}

			name = getLocalizedString("company_group.general_name", "General", getIWApplicationContext().getApplicationSettings().getDefaultLocale());
			description = getLocalizedString("company_group.general_description", "General users", getIWApplicationContext().getApplicationSettings().getDefaultLocale());
			Group generalGroup = getUserBusiness().getGroupBusiness().createGroup(name, description, getUserBusiness().getGroupBusiness().getGroupTypeHome().getPermissionGroupTypeString(), group.getHomePageID(), -1, false, group);
			access.addRoleToGroup(FSKConstants.ROLE_KEY_FSK_COMPANY, generalGroup, getIWApplicationContext());

			name = getLocalizedString("company_group.divisions_name", "Divisions", getIWApplicationContext().getApplicationSettings().getDefaultLocale());
			description = getLocalizedString("company_group.divisons_description", "Divisions group", getIWApplicationContext().getApplicationSettings().getDefaultLocale());
			Group divisionGroup = getUserBusiness().getGroupBusiness().createGroup(name, description, FSKConstants.GROUP_TYPE_DIVISIONS, -1, -1, false, group);

			String[] adminTypes = { "view", "edit", "create", "delete" };
			String[] generalTypes = { "view", "edit", "create" };
			String[] limitedTypes = { "view" };

			addPermissions(adminGroup, group, generalTypes);
			addPermissions(adminGroup, adminGroup, generalTypes);
			addPermissions(adminGroup, generalGroup, generalTypes);
			addPermissions(adminGroup, divisionGroup, adminTypes);

			addPermissions(generalGroup, group, limitedTypes);
			addPermissions(generalGroup, divisionGroup, adminTypes);
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		catch (CreateException e) {
			e.printStackTrace();
		}
	}

	private void addPermissions(Group group, Group permissionGroup, String[] types) {
		AccessController access = getIWApplicationContext().getIWMainApplication().getAccessController();

		for (int a = 0; a < types.length; a++) {
			try {
				access.setPermission(AccessController.CATEGORY_GROUP_ID, getIWApplicationContext(), group.getPrimaryKey().toString(), permissionGroup.getPrimaryKey().toString(), types[a], Boolean.TRUE);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setPermissions(Group adminGroup, Group companyGroup) {
		String[] adminTypes = { "view", "edit", "create", "delete", "permit" };

		addPermissions(adminGroup, companyGroup, adminTypes);
		Collection groups = companyGroup.getChildGroups();
		Iterator iterator = groups.iterator();
		while (iterator.hasNext()) {
			Group group = (Group) iterator.next();
			addPermissions(adminGroup, group, adminTypes);
		}
	}

	private ICFile createContract(PrintingContext pcx, Application application, Locale locale) throws CreateException {
		try {
			MemoryFileBuffer buffer = new MemoryFileBuffer();
			OutputStream mos = new MemoryOutputStream(buffer);
			InputStream mis = new MemoryInputStream(buffer);

			pcx.setDocumentStream(mos);

			getPrintingService().printDocument(pcx);

			return createFile(pcx.getFileName() != null ? pcx.getFileName() : "contract", mis, buffer.length());
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private ICFile createFile(String fileName, InputStream is, int length) throws CreateException {
		try {
			ICFileHome home = (ICFileHome) getIDOHome(ICFile.class);
			ICFile file = home.create();

			if (!fileName.endsWith(".pdf") && !fileName.endsWith(".PDF")) {
				fileName += ".pdf";
			}

			file.setFileValue(is);
			file.setMimeType("application/x-pdf");

			file.setName(fileName);
			file.setFileSize(length);
			file.store();
			return file;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public void rejectApplication(Application application, User performer, Locale locale, String body) {
		changeCaseStatus(application, getCaseStatusDenied(), performer);

		String subject = getLocalizedString("application.rejected_message_subject", "Company account application rejected", locale);
		sendMessage(application, subject, body, performer, application.getOwner(), null, FSKConstants.CONTENT_CODE_REJECT_APPLICATION);
		sendMessage(application, subject, body, performer, application.getAdminUser(), null, FSKConstants.CONTENT_CODE_REJECT_APPLICATION);
	}

	public void reactivateApplication(Application application, User performer, Locale locale) {
		changeCaseStatus(application, getCaseStatusOpen(), performer);

		String subject = getLocalizedString("application.reactivated_message_subject", "Company account application reactivate", locale);
		String body = getLocalizedString("application.reactivated_message_body", "Your application for company account for {0}, {1} has been reactivated.", locale);
		sendMessage(application, subject, body, performer, application.getOwner(), null, FSKConstants.CONTENT_CODE_REACTIVATE_APPLICATION);
		sendMessage(application, subject, body, performer, application.getAdminUser(), null, FSKConstants.CONTENT_CODE_REACTIVATE_APPLICATION);
	}

	public void requestInformation(Application application, User performer, Locale locale, String message) {
		String subject = getLocalizedString("application.request_info_message_subject", "Further information requested", locale);
		sendMessage(application, subject, message, performer, application.getOwner(), null, FSKConstants.CONTENT_CODE_REQUEST_INFORMATION);
		sendMessage(application, subject, message, performer, application.getAdminUser(), null, FSKConstants.CONTENT_CODE_REQUEST_INFORMATION);
	}

	public void reopenApplication(Application application, User performer, Locale locale) {
		Company company = application.getCompany();
		company.setOpen(true);
		company.store();

		changeCaseStatus(application, getCaseStatusGranted(), performer);

		String subject = getLocalizedString("application.reopen_message_subject", "Company account application reopened", locale);
		String body = getLocalizedString("application.reopen_message_body", "Your application for company account for {0}, {1} has been reopened.", locale);
		sendMessage(application, subject, body, performer, application.getOwner(), null, FSKConstants.CONTENT_CODE_REOPEN_APPLICATION);
		sendMessage(application, subject, body, performer, application.getAdminUser(), null, FSKConstants.CONTENT_CODE_REOPEN_APPLICATION);
	}

	public void closeApplication(Application application, User performer, Locale locale, String message) {
		Company company = application.getCompany();
		company.setOpen(false);
		company.store();

		changeCaseStatus(application, getCaseStatusCancelled(), performer);

		String subject = getLocalizedString("application.closed_message_subject", "Company account application closed", locale);
		sendMessage(application, subject, message, performer, application.getOwner(), null, FSKConstants.CONTENT_CODE_CLOSE_APPLICATION);
		sendMessage(application, subject, message, performer, application.getAdminUser(), null, FSKConstants.CONTENT_CODE_CLOSE_APPLICATION);
	}

	public void sendReminder(User performer, Locale locale) {
		String subject = getLocalizedString("application.reminder_message_subject", "A reminder for attachments", locale);
		String body = getLocalizedString("application.reminder_message_body", "You are reminded to attach all required attachments to the application for company account for {0}, {1} as soon as possible so it can be handled.", locale);

		Collection applications = getUnhandledApplications();
		Iterator iterator = applications.iterator();
		while (iterator.hasNext()) {
			Application application = (Application) iterator.next();
			if (!application.hasRequiredFiles()) {
				sendMessage(application, subject, body, performer, application.getOwner(), null, FSKConstants.CONTENT_CODE_SEND_REMINDER);
				sendMessage(application, subject, body, performer, application.getAdminUser(), null, FSKConstants.CONTENT_CODE_SEND_REMINDER);
				changeCaseStatus(application, getCaseStatusReview(), performer);
			}
		}
	}

	// Courses
	public Collection getNonApprovedCourses() {
		try {
			return getCourseHome().findNonApproved();
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getApprovedCourses(Season season) {
		try {
			return getCourseHome().findApproved(season);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getRejectedCourses(Season season) {
		try {
			return getCourseHome().findRejected(season);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getCourses(Company company) {
		try {
			return getCourseHome().findByCompany(company);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getCourses(Object seasonPK, Object periodPK, Object companyPK, Object divisionPK, Object groupPK, Object subGroupPK) {
		try {
			Collection groups = null;
			if (subGroupPK != null && subGroupPK.toString().length() > 0 && Integer.parseInt(subGroupPK.toString()) > 0) {
				groups = getUserBusiness().getGroupBusiness().getChildGroupsRecursive(Integer.parseInt(subGroupPK.toString()));
			}
			else if (groupPK != null && groupPK.toString().length() > 0 && Integer.parseInt(groupPK.toString()) > 0) {
				groups = getUserBusiness().getGroupBusiness().getChildGroupsRecursive(Integer.parseInt(groupPK.toString()));
			}
			else if (divisionPK != null && divisionPK.toString().length() > 0 && Integer.parseInt(divisionPK.toString()) > 0) {
				groups = getUserBusiness().getGroupBusiness().getChildGroupsRecursive(Integer.parseInt(divisionPK.toString()));
			}
			else if (companyPK != null && companyPK.toString().length() > 0 && Integer.parseInt(companyPK.toString()) > 0) {
				groups = getUserBusiness().getGroupBusiness().getChildGroupsRecursive(Integer.parseInt(companyPK.toString()));
			}
			else {
				try {
					groups = getUserBusiness().getGroupBusiness().getChildGroupsRecursive(getRootCompanyGroup());
				}
				catch (CreateException e) {
					e.printStackTrace();
					return new ArrayList();
				}
			}

			Season season = getSeason(seasonPK);
			Period period = getPeriod(periodPK);

			Collection courses = new ArrayList();
			if (groups != null) {
				Iterator iterator = groups.iterator();
				while (iterator.hasNext()) {
					Group group = (Group) iterator.next();
					if (group.getGroupType().equals(FSKConstants.GROUP_TYPE_COURSE)) {
						Course course = getCourse(group);
						if (course != null) {
							Period coursePeriod = course.getPeriod();
							Season courseSeason = coursePeriod.getSeason();
							boolean addCourse = true;

							if (season != null) {
								addCourse = season.equals(courseSeason);
							}
							if (period != null) {
								addCourse = period.equals(coursePeriod);
							}

							if (addCourse) {
								courses.add(course);
							}
						}
					}
				}
			}

			return courses;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}

	public Collection getApprovedCourses(Season season, Company company) {
		try {
			return getCourseHome().findApprovedByCompany(season, company);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getRejectedCourses(Season season, Company company) {
		try {
			return getCourseHome().findRejectedByCompany(season, company);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Course getCourse(Object pk) {
		if (pk != null) {
			try {
				return getCourseHome().findByPrimaryKey(new Integer(pk.toString()));
			}
			catch (FinderException e) {
				log(e);
			}
		}
		return null;
	}

	public Course getCourse(Group group) {
		return getCourse(group.getPrimaryKey());
	}

	public Collection getParticipants(Course course) {
		try {
			Group group = course.getGroup();
			return getUserBusiness().getUsersInGroup(group);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public boolean isPlacedAtCompany(Company company, User user) {
		Collection courses = getCourses(company);
		Iterator iterator = courses.iterator();
		while (iterator.hasNext()) {
			Course course = (Course) iterator.next();
			if (isPlacedInCourse(course, user)) {
				return true;
			}
		}

		return false;
	}

	public boolean isPlacedInCourse(Course course, User user) {
		try {
			Group group = course.getGroup();
			return getUserBusiness().isMemberOfGroup(((Integer) group.getPrimaryKey()).intValue(), user);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public Collection getCoursesForChild(User user) {
		return getCoursesForChild(null, null, user);
	}

	public Collection getCoursesForChild(Season season, User user) {
		return getCoursesForChild(null, season, user);
	}

	public Collection getCoursesForChild(Company company, User user) {
		return getCoursesForChild(company, null, user);
	}

	public Collection getCoursesForChild(Company company, Season season, User user) {
		Collection courses = new ArrayList();

		try {
			String[] types = { FSKConstants.GROUP_TYPE_COURSE };
			Collection groups = getUserBusiness().getUserGroups(user, types, true);

			if (groups != null) {
				Iterator iterator = groups.iterator();
				while (iterator.hasNext()) {
					Group group = (Group) iterator.next();
					Course course = getCourse(group);
					Company courseCompany = course.getCompany();
					if (company != null && !company.equals(courseCompany)) {
						continue;
					}

					if (season != null) {
						if (course.getPeriod().getSeason().equals(season)) {
							courses.add(course);
						}
					}
					else {
						courses.add(course);
					}
				}
			}

			return courses;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public void storeCourse(Object pk, Company company, Object periodPK, String name, Object divisionPK, Object groupPK, Object subGroupPK, float price, float cost, Date startDate, Date endDate, int numberOfHours, int maxMale, int maxFemale, User performer) throws CreateException {
		Course course = null;
		if (pk != null) {
			course = getCourse(pk);
			if (course != null) {
				Group group = course.getGroup();
				Group parent = (Group) group.getParentNode();
				parent.removeGroup(new Integer(pk.toString()).intValue(), performer, false);
			}
		}
		if (course == null) {
			course = getCourseHome().create();
		}

		course.setName(name);
		course.setCompany(company);
		course.setPeriod(getPeriod(periodPK));
		course.setPrice(price);
		course.setCost(cost);
		course.setStartDate(startDate);
		course.setEndDate(endDate);
		course.setNumberOfHours(numberOfHours);
		course.setMaxMale(maxMale);
		course.setMaxFemale(maxFemale);
		course.setApproved(true);
		course.store();

		Group courseGroup = course.getGroup();

		try {
			Group group = null;
			if (subGroupPK != null && Integer.parseInt(subGroupPK.toString()) > 0) {
				group = getUserBusiness().getGroupHome().findByPrimaryKey(new Integer(subGroupPK.toString()));
			}
			else if (groupPK != null && Integer.parseInt(groupPK.toString()) > 0) {
				group = getUserBusiness().getGroupHome().findByPrimaryKey(new Integer(groupPK.toString()));
			}
			else if (divisionPK != null && Integer.parseInt(divisionPK.toString()) > 0) {
				group = getUserBusiness().getGroupHome().findByPrimaryKey(new Integer(divisionPK.toString()));
			}

			Group parentGroup = (Group) courseGroup.getParentNode();
			if (parentGroup != null && group != null && !parentGroup.equals(group)) {
				parentGroup.removeGroup(courseGroup, performer);
				group.addGroup(courseGroup);
			}
			else if (group != null && parentGroup == null) {
				group.addGroup(courseGroup);
			}

			String[] adminTypes = { "view", "edit", "create", "delete", "permit" };

			Group division = getDivision(course);
			if (division != null) {
				addPermissions(division, courseGroup, adminTypes);
			}

			Collection permissionGroups = getChildGroups(company.getGroup(), "permission");
			Iterator iterator = permissionGroups.iterator();
			while (iterator.hasNext()) {
				Group permissionGroup = (Group) iterator.next();
				addPermissions(permissionGroup, courseGroup, adminTypes);
			}

			try {
				addPermissions(getRootFSKGroup(), courseGroup, adminTypes);
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		catch (FinderException fe) {
			fe.printStackTrace();
		}
	}

	public boolean deleteCourse(Object pk, User performer) {
		try {
			Course course = getCourse(pk);
			if (course != null) {
				if (getNumberOfRegistrations(course) == 0) {
					course.remove();
					removeGroup(pk, performer);
					return true;
				}
			}
		}
		catch (RemoveException re) {
			re.printStackTrace();
		}
		return false;
	}

	public void closeCourses(Object[] courses, Locale locale) {
		if (courses != null) {
			for (int i = 0; i < courses.length; i++) {
				Object coursePK = courses[i];
				Course course = getCourse(coursePK);
				if (course != null) {
					course.setClosed(true);
					course.store();
				}
			}
		}
	}

	public void openCourses(Object[] courses, Locale locale) {
		if (courses != null) {
			for (int i = 0; i < courses.length; i++) {
				Object coursePK = courses[i];
				Course course = getCourse(coursePK);
				if (course != null) {
					course.setClosed(false);
					course.store();
				}
			}
		}
	}

	public void approveCourses(Object[] courses, Locale locale) {
		if (courses != null) {
			for (int i = 0; i < courses.length; i++) {
				Object coursePK = courses[i];
				Course course = getCourse(coursePK);
				if (course != null) {
					course.setApproved(true);
					course.store();
				}
			}
		}
	}

	public void rejectCourses(Object[] courses, Locale locale) {
		if (courses != null) {
			for (int i = 0; i < courses.length; i++) {
				Object coursePK = courses[i];
				Course course = getCourse(coursePK);
				if (course != null) {
					course.setApproved(false);
					course.store();
				}
			}
		}
	}

	public int getNumberOfRegistrations(Course course) {
		Group group = course.getGroup();
		try {
			return getUserBusiness().getUsersInGroup(group).size();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Division getDivision(Object pk) {
		try {
			return (Division) getDivisionHome().findByPrimaryKey(new Integer(pk.toString()));
		}
		catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Collection getDivisions() {
		try {
			return getDivisionHome().findAll();
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getNonApprovedDivisions(Company company) {
		Collection divisions = new ArrayList();
		Collection allDivisions = getDivisions();

		Iterator iterator = allDivisions.iterator();
		while (iterator.hasNext()) {
			Division division = (Division) iterator.next();
			if (division.isApproved() == null) {
				if (company != null) {
					Company divisionCompany = division.getCompany();
					if (divisionCompany != null && divisionCompany.equals(company)) {
						divisions.add(division);
					}
				}
				else {
					divisions.add(division);
				}
			}
		}

		return divisions;
	}

	public Collection getApprovedDivisions(Company company) {
		Collection divisions = new ArrayList();
		Collection allDivisions = getDivisions();

		Iterator iterator = allDivisions.iterator();
		while (iterator.hasNext()) {
			Division division = (Division) iterator.next();
			if (division.isApproved() != null && division.isApproved().booleanValue()) {
				if (company != null) {
					Company divisionCompany = division.getCompany();
					if (divisionCompany != null && divisionCompany.equals(company)) {
						divisions.add(division);
					}
				}
				else {
					divisions.add(division);
				}
			}
		}

		return divisions;
	}

	public Collection getRejectedDivisions(Company company) {
		Collection divisions = new ArrayList();
		Collection allDivisions = getDivisions();

		Iterator iterator = allDivisions.iterator();
		while (iterator.hasNext()) {
			Division division = (Division) iterator.next();
			if (division.isApproved() != null && !division.isApproved().booleanValue()) {
				if (company != null) {
					Company divisionCompany = division.getCompany();
					if (divisionCompany != null && divisionCompany.equals(company)) {
						divisions.add(division);
					}
				}
				else {
					divisions.add(division);
				}
			}
		}

		return divisions;
	}

	public Collection getAllDivisionsByCompany(IWUserContext iwuc, Company company, User user) {
		return getDivisions(iwuc, company.getGroup(), user, true);
	}

	public Collection getDivisions(IWUserContext iwuc, Company company, User user) {
		return getDivisions(iwuc, company.getGroup(), user);
	}

	public Collection getDivisions(IWUserContext iwuc, Group group, User user) {
		return getDivisions(iwuc, group, user, false);
	}

	public Collection getDivisions(IWUserContext iwuc, Group group, User user, boolean showAll) {
		List divisions = new ArrayList();
		Group divisionsGroup = getDivisionsGroup(group);

		boolean isAdmin = getIWApplicationContext().getIWMainApplication().getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwuc);
		boolean isCompanyAdmin = getIWApplicationContext().getIWMainApplication().getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY_ADMIN, iwuc);
		boolean isCompany = getIWApplicationContext().getIWMainApplication().getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY, iwuc);

		Collection userDivisions = null;
		if (!isAdmin && !isCompanyAdmin && isCompany) {
			userDivisions = new ArrayList();
			Collection groups = user.getParentGroups();
			Iterator iterator = groups.iterator();
			while (iterator.hasNext()) {
				Group parentGroup = (Group) iterator.next();
				if (parentGroup.getGroupType().equals(FSKConstants.GROUP_TYPE_DIVISION)) {
					userDivisions.add(parentGroup);
				}
			}
		}

		Collection children = divisionsGroup.getChildren();
		Iterator iterator = children.iterator();
		while (iterator.hasNext()) {
			Group element = (Group) iterator.next();
			Division division = getDivision(element.getPrimaryKey());
			if (division.getGroupType().equals(FSKConstants.GROUP_TYPE_DIVISION) && (showAll || (division.isApproved() != null && division.isApproved().booleanValue()))) {
				if (userDivisions != null) {
					if (userDivisions.contains(element)) {
						divisions.add(division);
					}
				}
				else {
					divisions.add(division);
				}
			}
		}
		Collections.sort(divisions, new DivisionComparator(getIWApplicationContext().getApplicationSettings().getDefaultLocale()));

		return divisions;
	}

	public Group getDivisionsGroup(Company company) {
		return getDivisionsGroup(company.getGroup());
	}

	public Group getDivisionsGroup(Group group) {
		Collection groups = getChildGroups(group, FSKConstants.GROUP_TYPE_DIVISIONS);
		Iterator iterator = groups.iterator();
		while (iterator.hasNext()) {
			return (Group) iterator.next();
		}

		return null;
	}

	public DivisionName getDivisionName(Object pk) {
		if (pk != null) {
			try {
				return getDivisionNameHome().findByPrimaryKey(new Integer(pk.toString()));
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
			catch (FinderException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public DivisionName getDivisionName(String name, CompanyType type) {
		try {
			return getDivisionNameHome().findByNameAndType(name, type);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Collection getDivisionNames(CompanyType type) {
		try {
			return getDivisionNameHome().findAllByType(type);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Collection getDivisionNames(Company company, Division division) {
		Group group = company.getGroup();
		CompanyType type = company.getType();

		Group divisionsGroup = getDivisionsGroup(group);
		Collection divisions = divisionsGroup.getChildren();

		try {
			Collection names = new ArrayList();

			Collection divisionNames = getDivisionNameHome().findAllByType(type);
			Iterator iterator = divisionNames.iterator();
			while (iterator.hasNext()) {
				DivisionName name = (DivisionName) iterator.next();
				names.add(name.getName());
			}

			iterator = divisions.iterator();
			while (iterator.hasNext()) {
				Group divisionGroup = (Group) iterator.next();
				if (division == null || (division != null && !division.getPrimaryKey().equals(divisionGroup.getPrimaryKey()))) {
					names.remove(divisionGroup.getName());
				}
			}

			return names;
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public boolean storeDivisionName(Object pk, String name, CompanyType type) throws CreateException {
		DivisionName divisionName = getDivisionName(pk);
		if (divisionName == null) {
			divisionName = getDivisionName(name, type);
			if (divisionName != null) {
				throw new CreateException();
			}

			divisionName = getDivisionNameHome().create();
		}

		divisionName.setName(name);
		divisionName.setType(type);
		divisionName.setValid(true);
		divisionName.store();

		return true;
	}

	public void removeDivisionName(Object pk) {
		DivisionName name = getDivisionName(pk);
		if (pk != null) {
			name.setValid(false);
			name.store();
		}
	}

	public void storeDivision(Object pk, String name, Company company) throws CreateException {
		Division division = null;

		boolean newDivision = false;
		if (pk != null) {
			division = getDivision(pk);
		}
		if (division == null) {
			division = (Division) getDivisionHome().create();
			newDivision = true;
		}
		division.setGroupType(FSKConstants.GROUP_TYPE_DIVISION);
		division.setName(name);
		if (newDivision) {
			division.setApproved(true);
		}
		division.store();

		if (newDivision) {
			getIWApplicationContext().getIWMainApplication().getAccessController().addRoleToGroup(FSKConstants.ROLE_KEY_FSK_COMPANY, division, getIWApplicationContext());

			Group companyGroup = company.getGroup();
			division.setHomePageID(companyGroup.getHomePageID());
			division.store();

			Group divisionsGroup = getDivisionsGroup(company);
			if (divisionsGroup != null) {
				divisionsGroup.addGroup(division);
			}
			else {
				throw new CreateException("No divisions group found for company...");
			}

			String[] viewTypes = { "view", "edit", "create" };
			addPermissions(division, division, viewTypes);

			String[] adminTypes = { "view", "edit", "create", "delete", "permit" };
			Collection permissionGroups = getChildGroups(company.getGroup(), "permission");
			Iterator iterator = permissionGroups.iterator();
			while (iterator.hasNext()) {
				Group permissionGroup = (Group) iterator.next();
				addPermissions(permissionGroup, division, adminTypes);
			}

			try {
				addPermissions(getRootFSKGroup(), division, adminTypes);
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
	}

	public void approveDivisions(Object[] divisions, Locale locale) {
		if (divisions != null) {
			for (int i = 0; i < divisions.length; i++) {
				Object divisionPK = divisions[i];
				Division division = getDivision(divisionPK);
				if (division != null) {
					division.setApproved(true);
					division.store();
				}
			}
		}
	}

	public void rejectDivisions(Object[] divisions, Locale locale) {
		if (divisions != null) {
			for (int i = 0; i < divisions.length; i++) {
				Object divisionPK = divisions[i];
				Division division = getDivision(divisionPK);
				if (division != null) {
					division.setApproved(false);
					division.store();
				}
			}
		}
	}

	public Group getGroup(Object pk) {
		try {
			return getUserBusiness().getGroupHome().findByPrimaryKey(new Integer(pk.toString()));
		}
		catch (FinderException fe) {
			fe.printStackTrace();
			return null;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public Collection getGroups(Company company) {
		return getChildGroups(company.getGroup(), FSKConstants.GROUP_TYPE_GROUP);
	}

	public Collection getGroups(Group division) {
		return getChildGroups(division, FSKConstants.GROUP_TYPE_GROUP);
	}

	public void storeGroup(Object pk, String name, Object divisionPK, Company company, User performer) throws FinderException, CreateException {
		try {
			Group group = null;
			if (pk != null) {
				group = getUserBusiness().getGroupHome().findByPrimaryKey(new Integer(pk.toString()));
			}
			if (group == null) {
				group = getUserBusiness().getGroupHome().create();
			}
			group.setGroupType(FSKConstants.GROUP_TYPE_GROUP);
			group.setName(name);
			group.store();

			Group division = getDivision(divisionPK);
			Group parentGroup = (Group) group.getParentNode();
			if (division != null && parentGroup != null && !division.equals(parentGroup)) {
				parentGroup.removeGroup(group, performer);
				division.addGroup(group);
			}
			else if (division != null && parentGroup == null) {
				division.addGroup(group);
			}

			String[] adminTypes = { "view", "edit", "create", "delete", "permit" };
			addPermissions(division, group, adminTypes);

			Collection permissionGroups = getChildGroups(company.getGroup(), "permission");
			Iterator iterator = permissionGroups.iterator();
			while (iterator.hasNext()) {
				Group permissionGroup = (Group) iterator.next();
				addPermissions(permissionGroup, group, adminTypes);
			}

			try {
				addPermissions(getRootFSKGroup(), group, adminTypes);
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public void storeSubGroup(Object pk, String name, Object parentGroupPK, Company company, User performer) throws FinderException, CreateException {
		try {
			Group subGroup = null;
			if (pk != null) {
				subGroup = getUserBusiness().getGroupHome().findByPrimaryKey(pk);
			}
			if (subGroup == null) {
				subGroup = getUserBusiness().getGroupHome().create();
			}
			subGroup.setGroupType(FSKConstants.GROUP_TYPE_SUB_GROUP);
			subGroup.setName(name);
			subGroup.store();

			Group group = parentGroupPK != null ? getUserBusiness().getGroupHome().findByPrimaryKey(new Integer(parentGroupPK.toString())) : null;
			Group parentGroup = (Group) subGroup.getParentNode();
			if (group != null && parentGroup != null && !group.equals(parentGroup)) {
				parentGroup.removeGroup(subGroup, performer);
				group.addGroup(subGroup);
			}
			else if (group != null && parentGroup == null) {
				group.addGroup(subGroup);
			}

			String[] adminTypes = { "view", "edit", "create", "delete", "permit" };

			if (group != null) {
				Group division = (Group) group.getParentNode();
				addPermissions(division, group, adminTypes);
			}

			Collection permissionGroups = getChildGroups(company.getGroup(), "permission");
			Iterator iterator = permissionGroups.iterator();
			while (iterator.hasNext()) {
				Group permissionGroup = (Group) iterator.next();
				addPermissions(permissionGroup, subGroup, adminTypes);
			}

			try {
				addPermissions(getRootFSKGroup(), subGroup, adminTypes);
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public boolean removeGroup(Object pk, User performer) {
		try {
			Group group = getUserBusiness().getGroupHome().findByPrimaryKey(new Integer(pk.toString()));
			Group parentGroup = (Group) group.getParentNode();

			if (getUserBusiness().getGroupBusiness().isGroupRemovable(group, parentGroup)) {
				parentGroup.removeGroup(group, performer);
				removePermissions(group, performer);
				removeRelation(getIWApplicationContext().getDomain(), group, performer);
				return true;
			}
			return false;
		}
		catch (RemoveException re) {
			re.printStackTrace();
			return false;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		catch (FinderException fe) {
			fe.printStackTrace();
			return false;
		}
	}

	private void removePermissions(Group group, User performer) {
		Collection parents = null;

		try {
			parents = getUserBusiness().getGroupBusiness().getParentGroups(group);
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}

		//if it has no parents it will disapeer from view and therefor we can disable its permissions.
		if (parents == null || parents.isEmpty()) {
			Collection permissions = getAllPermissionForGroup(group);

			Iterator entries = permissions.iterator();
			while (entries.hasNext()) {
				ICPermission permission = (ICPermission) entries.next();
				permission.removeBy(performer);
			}
			getIWApplicationContext().removeApplicationAttribute("ic_permission_map_" + AccessController.CATEGORY_GROUP_ID);
		}
	}

	private void removeRelation(ICDomain domain, Group group, User currentUser) {
		try {
			if (domain != null) {
				GroupDomainRelationHome home = (GroupDomainRelationHome) IDOLookup.getHome(GroupDomainRelation.class);
				Collection coll = home.findDomainsRelationshipsContaining(domain, group);
				Iterator iterator = coll.iterator();
				while (iterator.hasNext()) {
					GroupDomainRelation relation = (GroupDomainRelation) iterator.next();
					relation.removeBy(currentUser);
				}
			}
		}
		catch (Exception ex) {
		}
	}

	private Collection getAllPermissionForGroup(Group group) {
		Collection allPermissions = null;
		Collection permissionSetOnGroup = null;

		try {
			allPermissions = AccessControl.getAllGroupPermissionsForGroup(group);
			permissionSetOnGroup = AccessControl.getAllGroupPermissionsReverseForGroup(group);

			allPermissions.addAll(permissionSetOnGroup);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("GroupPermission selected group (" + group.getPrimaryKey() + ") not found or remote error!");
		}
		return allPermissions;
	}

	public Collection getSubGroups(Company company) {
		return getChildGroups(company.getGroup(), FSKConstants.GROUP_TYPE_SUB_GROUP);
	}

	public Collection getSubGroups(Group group) {
		return getChildGroups(group, FSKConstants.GROUP_TYPE_SUB_GROUP);
	}

	private Collection getChildGroups(Group group, String groupType) {
		try {
			List childGroups = new ArrayList();
			Collection groups = getUserBusiness().getGroupBusiness().getChildGroupsRecursive(group);
			if (groups != null) {
				Iterator iterator = groups.iterator();
				while (iterator.hasNext()) {
					Group element = (Group) iterator.next();
					if (element.getGroupType().equals(groupType)) {
						childGroups.add(element);
					}
				}
			}
			Collections.sort(childGroups, new DivisionComparator(getIWApplicationContext().getApplicationSettings().getDefaultLocale()));

			return childGroups;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}

	}

	public Group getDivision(Course course) {
		return getParentGroup(course, FSKConstants.GROUP_TYPE_DIVISION);
	}

	public Group getGroup(Course course) {
		return getParentGroup(course, FSKConstants.GROUP_TYPE_GROUP);
	}

	public Group getSubGroup(Course course) {
		return getParentGroup(course, FSKConstants.GROUP_TYPE_SUB_GROUP);
	}

	private Group getParentGroup(Course course, String groupType) {
		try {
			Group group = course.getGroup();
			String[] types = { groupType };

			Collection groups = getUserBusiness().getGroupBusiness().getParentGroupsRecursive(group, types, true);
			if (groups != null) {
				Iterator iterator = groups.iterator();
				while (iterator.hasNext()) {
					return (Group) iterator.next();
				}
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}

		return null;
	}

	public Map registerParticipants(String[] personalIDs, Object coursePK) {
		Map map = new HashMap();
		try {
			Course course = getCourse(coursePK);
			Group group = course.getGroup();

			NumberFormat format = NumberFormat.getNumberInstance();
			format.setGroupingUsed(false);
			format.setMinimumIntegerDigits(10);

			Collection imported = new ArrayList();
			Collection alreadyImported = new ArrayList();
			Collection outsideCommune = new ArrayList();
			Collection outsideAgeRange = new ArrayList();
			Collection invalidPersonalID = new ArrayList();
			Collection noUserFound = new ArrayList();

			for (int a = 0; a < personalIDs.length; a++) {
				String personalID = personalIDs[a];
				if (personalID == null || personalID.length() == 0) {
					continue;
				}

				try {
					personalID = format.format(format.parse(personalID.replaceAll("-", "")));
				}
				catch (ParseException e1) {
					invalidPersonalID.add(personalID);
					continue;
				}

				if (SocialSecurityNumber.isValidSocialSecurityNumber(personalID, getDefaultLocale())) {
					try {
						User user = getUserBusiness().getUser(personalID);
						if (!group.hasRelationTo(((Integer) user.getPrimaryKey()).intValue())) {
							IWTimestamp dateOfBirth = new IWTimestamp(user.getDateOfBirth());
							dateOfBirth.setMonth(1);
							dateOfBirth.setDay(1);
							Age age = new Age(dateOfBirth.getDate());

							if (age.getYears(course.getStartDate()) < 6 || age.getYears(course.getStartDate()) > 18) {
								outsideAgeRange.add(user);
								continue;
							}

							if (!getUserBusiness().isCitizenOfDefaultCommune(user)) {
								outsideCommune.add(user);
								continue;
							}

							group.addGroup(user);
							imported.add(user);
						}
						else {
							alreadyImported.add(user);
						}
					}
					catch (FinderException e) {
						noUserFound.add(personalID);
					}
				}
				else {
					invalidPersonalID.add(personalID);
				}
			}

			map.put(FSKConstants.REGISTRATION_CODE_REGISTERED, imported);
			map.put(FSKConstants.REGISTRATION_CODE_ALREADY_REGISTERED, alreadyImported);
			map.put(FSKConstants.REGISTRATION_CODE_OUTSIDE_COMMUNE, outsideCommune);
			map.put(FSKConstants.REGISTRATION_CODE_OUTSIDE_AGE_RANGE, outsideAgeRange);
			map.put(FSKConstants.REGISTRATION_CODE_INVALID_PERSONAL_ID, invalidPersonalID);
			map.put(FSKConstants.REGISTRATION_CODE_NO_USER_FOUND, noUserFound);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

	public Map importExcelFile(UploadFile file, Object coursePK, int column) {
		Map map = new HashMap();

		try {
			Course course = getCourse(coursePK);
			Group group = course.getGroup();

			FileInputStream input = new FileInputStream(file.getRealPath());
			HSSFWorkbook wb = new HSSFWorkbook(input);

			HSSFSheet sheet = wb.getSheetAt(0);

			NumberFormat format = NumberFormat.getNumberInstance();
			format.setGroupingUsed(false);
			format.setMinimumIntegerDigits(10);

			Collection imported = new ArrayList();
			Collection alreadyImported = new ArrayList();
			Collection outsideCommune = new ArrayList();
			Collection outsideAgeRange = new ArrayList();
			Collection invalidPersonalID = new ArrayList();
			Collection noUserFound = new ArrayList();

			for (int a = sheet.getFirstRowNum(); a <= sheet.getLastRowNum(); a++) {
				HSSFRow row = sheet.getRow(a);
				HSSFCell cell = row.getCell((short) (column - 1));
				if (cell == null) {
					continue;
				}

				String personalID = null;
				try {
					personalID = cell.getStringCellValue();
				}
				catch (NumberFormatException nfe) {
					personalID = String.valueOf(cell.getNumericCellValue());
				}

				try {
					personalID = format.format(format.parse(personalID.replaceAll("-", "")));
				}
				catch (ParseException e1) {
					e1.printStackTrace();
					continue;
				}

				if (SocialSecurityNumber.isValidSocialSecurityNumber(personalID, getDefaultLocale())) {
					try {
						User user = getUserBusiness().getUser(personalID);
						if (!group.hasRelationTo(((Integer) user.getPrimaryKey()).intValue())) {
							IWTimestamp dateOfBirth = new IWTimestamp(user.getDateOfBirth());
							dateOfBirth.setMonth(1);
							dateOfBirth.setDay(1);
							Age age = new Age(dateOfBirth.getDate());

							if (age.getYears(course.getStartDate()) < 6 || age.getYears(course.getStartDate()) > 18) {
								outsideAgeRange.add(user);
								continue;
							}

							if (!getUserBusiness().isCitizenOfDefaultCommune(user)) {
								outsideCommune.add(user);
								continue;
							}

							group.addGroup(user);
							imported.add(user);
						}
						else {
							alreadyImported.add(user);
						}
					}
					catch (FinderException e) {
						noUserFound.add(personalID);
					}
				}
				else {
					invalidPersonalID.add(personalID);
				}
			}

			map.put(FSKConstants.REGISTRATION_CODE_REGISTERED, imported);
			map.put(FSKConstants.REGISTRATION_CODE_ALREADY_REGISTERED, alreadyImported);
			map.put(FSKConstants.REGISTRATION_CODE_OUTSIDE_COMMUNE, outsideCommune);
			map.put(FSKConstants.REGISTRATION_CODE_OUTSIDE_AGE_RANGE, outsideAgeRange);
			map.put(FSKConstants.REGISTRATION_CODE_INVALID_PERSONAL_ID, invalidPersonalID);
			map.put(FSKConstants.REGISTRATION_CODE_NO_USER_FOUND, noUserFound);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

	// DWR
	public Map getAllGroups(String groupPK, String groupType, String country) {
		Map map = new LinkedHashMap();
		Locale locale = new Locale(country, country.toUpperCase());
		if (groupType.equals(FSKConstants.GROUP_TYPE_GROUP)) {
			map.put("-1", getLocalizedString("select_group", "Select group", locale));
		}
		else if (groupType.equals(FSKConstants.GROUP_TYPE_SUB_GROUP)) {
			map.put("-1", getLocalizedString("select_sub_group", "Select sub group", locale));
		}

		if (groupPK != null && Integer.parseInt(groupPK) > 0) {
			try {
				Group parentGroup = getUserBusiness().getGroupHome().findByPrimaryKey(new Integer(groupPK));
				Collection coll = getChildGroups(parentGroup, groupType);

				if (coll != null) {
					Iterator iter = coll.iterator();
					while (iter.hasNext()) {
						Group group = (Group) iter.next();
						map.put(group.getPrimaryKey().toString(), group.getName());
					}
				}
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
			catch (FinderException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public Map getPeriods(String seasonPK, String country) {
		Map map = new LinkedHashMap();
		Locale locale = new Locale(country, country.toUpperCase());
		map.put("-1", getLocalizedString("select_period", "Select period", locale));

		if (seasonPK != null && seasonPK.length() > 0 && Integer.parseInt(seasonPK) > 0) {
			Season season = getSeason(seasonPK);
			if (season != null) {
				Collection coll = getAllPeriods(season);

				if (coll != null) {
					Iterator iter = coll.iterator();
					while (iter.hasNext()) {
						Period period = (Period) iter.next();
						map.put(period.getPrimaryKey().toString(), period.getName());
					}
				}
			}
		}
		return map;
	}

	public String getUserName(String personalID) {
		try {
			User user = getUserBusiness().getUser(personalID);
			Name name = new Name(user.getFirstName(), user.getMiddleName(), user.getLastName());
			return name.getName(getDefaultLocale());
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
		return null;
	}

	public AdminUser getUser(String personalID, String country) {
		Locale locale = new Locale(country, country.toUpperCase());
		if (!SocialSecurityNumber.isValidSocialSecurityNumber(personalID, locale)) {
			return new AdminUser();
		}

		try {
			User user = getUserBusiness().getUser(personalID);
			Name name = new Name(user.getFirstName(), user.getMiddleName(), user.getLastName());

			Phone workPhone = null;
			try {
				workPhone = getUserBusiness().getUsersWorkPhone(user);
			}
			catch (NoPhoneFoundException e1) {
				// No phone found...
			}

			Phone mobilePhone = null;
			try {
				mobilePhone = getUserBusiness().getUsersMobilePhone(user);
			}
			catch (NoPhoneFoundException e) {
				// No phone found...
			}

			Email email = null;
			try {
				email = getUserBusiness().getUsersMainEmail(user);
			}
			catch (NoEmailFoundException e) {
				// No email found...
			}

			AdminUser adminUser = new AdminUser();
			adminUser.setPK(user.getPrimaryKey().toString());
			adminUser.setPersonalID(user.getPersonalID());
			adminUser.setName(name.getName(locale));
			if (workPhone != null) {
				adminUser.setWorkPhone(workPhone.getNumber());
			}
			if (mobilePhone != null) {
				adminUser.setMobilePhone(mobilePhone.getNumber());
			}
			if (email != null) {
				adminUser.setEmail(email.getEmailAddress());
			}

			return adminUser;
		}
		catch (FinderException fe) {
			fe.printStackTrace();
			AdminUser user = new AdminUser();
			user.setName(getLocalizedString("invalid_personal_id", "Invalid personal ID", locale));
			return user;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public CompanyDWR getCompanyInfo(String personalID, String companyPhone, String companyFax, String companyEmail, String companyWebpage, String companyBankAccount, String country) {
		CompanyDWR dwr = new CompanyDWR();
		dwr.setPersonalID(personalID);

		Locale locale = new Locale(country, country.toUpperCase());
		if (!SocialSecurityNumber.isValidSocialSecurityNumber(personalID, locale)) {
			System.out.println("Isn't valid company ssn");
			return dwr;
		}

		try {
			Company company = getCompanyBusiness().getCompany(personalID);
			Address address = company.getAddress();
			PostalCode code = address != null ? address.getPostalCode() : null;
			Phone phone = company.getPhone();
			Phone fax = company.getFax();
			Email email = company.getEmail();

			dwr.setPK(company.getPrimaryKey().toString());
			dwr.setName(company.getName());
			if (address != null) {
				dwr.setAddress(address.getStreetAddress());
			}
			if (code != null) {
				dwr.setPostalCode(code.getPostalCode());
				dwr.setCity(code.getName());
			}

			if (companyPhone != null && companyPhone.length() > 0) {
				dwr.setPhone(companyPhone);
			}
			else if (phone != null) {
				dwr.setPhone(phone.getNumber());
			}

			if (companyFax != null && companyFax.length() > 0) {
				dwr.setFax(companyFax);
			}
			else if (fax != null) {
				dwr.setFax(fax.getNumber());
			}

			if (companyEmail != null && companyEmail.length() > 0) {
				dwr.setEmail(companyEmail);
			}
			else if (email != null) {
				dwr.setEmail(email.getEmailAddress());
			}

			if (companyWebpage != null && companyWebpage.length() > 0) {
				dwr.setWebPage(companyWebpage);
			}
			else {
				dwr.setWebPage(company.getWebPage());
			}

			if (companyBankAccount != null && companyBankAccount.length() > 0) {
				dwr.setBankAccount(companyBankAccount);
			}
			else {
				dwr.setBankAccount(company.getBankAccount());
			}

			return dwr;
		}
		catch (FinderException fe) {
			//No company found...
			fe.printStackTrace();

			return dwr;
		}
		catch (RemoteException re) {
			re.printStackTrace();

			throw new IBORuntimeException(re);
		}
	}

	public Map getAllCourses(String companyPK, String seasonPK, String periodPK, String divisionPK, String groupPK, String subGroupPK, String country) {
		Locale locale = new Locale(country, country.toUpperCase());
		Map map = new LinkedHashMap();

		map.put("", getLocalizedString("select_course", "Select course", locale));

		List courses = new ArrayList(getCourses(seasonPK, periodPK, companyPK, divisionPK, groupPK, subGroupPK));

		if (courses != null) {
			Collections.sort(courses, new CourseComparator(getIWApplicationContext(), locale, CourseComparator.SORT_NAME));
			Iterator iterator = courses.iterator();
			while (iterator.hasNext()) {
				Course course = (Course) iterator.next();
				map.put(course.getPrimaryKey().toString(), course.getName());
			}
		}

		return map;
	}

	// General
	public void sendMessage(Application application, String subject, String body, User sender, User receiver, File attachment, String contentCode) {
		try {
			Object[] arguments = { application.getName(), application.getPersonalID() };
			getMessageBusiness().createUserMessage(application, receiver, sender, null, MessageFormat.format(subject, arguments), MessageFormat.format(body, arguments), MessageFormat.format(body, arguments), attachment, true, contentCode, false, true);
		}
		catch (RemoteException re) {
			re.printStackTrace();
		}
	}

	public void storeUserInformation(Object userPK, String workPhone, String mobilePhone, String email) {
		try {
			User user = getUserBusiness().getUser(new Integer(userPK.toString()));
			if (user != null) {
				getUserBusiness().updateUserWorkPhone(user, workPhone);
				getUserBusiness().updateUserMobilePhone(user, mobilePhone);
				getUserBusiness().storeUserEmail(user, email, true);
			}
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void fixPermissions() {
		boolean fixPermissions = getIWApplicationContext().getApplicationSettings().getBoolean("fsk.fix.permissions", true);

		if (fixPermissions) {
			try {
				Group rootCompanyGroup = getRootCompanyGroup();
				Group fskGroup = getRootFSKGroup();

				Collection companies = rootCompanyGroup.getChildren();
				Iterator iterator = companies.iterator();
				while (iterator.hasNext()) {
					Group company = (Group) iterator.next();
					Collection permissionGroups = getChildGroups(company, "permission");

					String[] adminTypes = { "view", "edit", "create", "delete", "permit" };
					String[] viewTypes = { "view", "edit", "create" };

					Group divisionGroup = getDivisionsGroup(company);
					if (divisionGroup != null) {
						Collection divisions = divisionGroup.getChildGroups();
						Iterator iterator2 = divisions.iterator();
						while (iterator2.hasNext()) {
							Group division = (Group) iterator2.next();
							fixDivisionPermission(division, permissionGroups, fskGroup, adminTypes, viewTypes);
						}
					}

					System.out.println("[FSKBusiness.fixPermissions()] Done fixing permissions for company: " + company.getName());
				}

				getIWApplicationContext().getApplicationSettings().setProperty("fsk.fix.permissions", Boolean.FALSE.toString());
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
			catch (CreateException ce) {
				ce.printStackTrace();
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
		}
	}

	private void fixDivisionPermission(Group division, Collection permissionGroups, Group fskGroup, String[] adminTypes, String[] viewTypes) {
		addPermissions(division, division, viewTypes);
		addPermissions(fskGroup, division, adminTypes);
		Iterator iterator = permissionGroups.iterator();
		while (iterator.hasNext()) {
			Group permissionGroup = (Group) iterator.next();
			addPermissions(permissionGroup, division, adminTypes);
		}

		iterator = division.getChildrenIterator();
		while (iterator.hasNext()) {
			Group group = (Group) iterator.next();
			fixGroupPermission(group, division, permissionGroups, fskGroup, adminTypes, viewTypes);
		}
	}

	private void fixGroupPermission(Group group, Group division, Collection permissionGroups, Group fskGroup, String[] adminTypes, String[] viewTypes) {
		addPermissions(division, group, adminTypes);
		addPermissions(fskGroup, group, adminTypes);
		Iterator iterator = permissionGroups.iterator();
		while (iterator.hasNext()) {
			Group permissionGroup = (Group) iterator.next();
			addPermissions(permissionGroup, division, adminTypes);
		}

		iterator = group.getChildrenIterator();
		while (iterator.hasNext()) {
			Group subGroup = (Group) iterator.next();
			fixGroupPermission(subGroup, division, permissionGroups, fskGroup, adminTypes, viewTypes);
		}
	}

	public Map getPaidEntries(IWUserContext iwuc, Company company, Date fromDate, Date toDate, Season season, Period period, Division division, User user) {
		try {
			Map map = new HashMap();
			if (division != null) {
				Collection courses = getCourses(season != null ? season.getPrimaryKey() : null, period != null ? period.getPrimaryKey() : null, company != null ? company.getPrimaryKey() : null, division != null ? division.getPrimaryKey() : null, null, null);
				if (!courses.isEmpty()) {
					Collection entries = getPaymentAllocationHome().findPaymentsByCriteria(courses, fromDate, toDate, true);

					map.put(division, entries);
				}
			}
			else {
				Collection divisions = null;
				if (company != null) {
					divisions = getDivisions(iwuc, company, user);
				}
				else {
					divisions = getDivisions();
				}
				Iterator iterator = divisions.iterator();
				while (iterator.hasNext()) {
					division = (Division) iterator.next();
					Collection courses = getCourses(season != null ? season.getPrimaryKey() : null, period != null ? period.getPrimaryKey() : null, company != null ? company.getPrimaryKey() : null, division != null ? division.getPrimaryKey() : null, null, null);
					if (!courses.isEmpty()) {
						Collection entries = getPaymentAllocationHome().findPaymentsByCriteria(courses, fromDate, toDate, true);

						map.put(division, entries);
					}
				}
			}

			return map;
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new HashMap();
		}
	}

	public Map getParticipants(IWUserContext iwuc, Company company, Date fromDate, Date toDate, Season season, Period period, Division division, User user) {
		Map map = new HashMap();

		Collection divisions = new ArrayList();
		if (division != null) {
			divisions.add(division);
		}
		else if (company != null) {
			divisions = getDivisions(iwuc, company, user);
		}
		else {
			divisions = getDivisions();
		}

		Iterator iterator = divisions.iterator();
		while (iterator.hasNext()) {
			division = (Division) iterator.next();
			if (division.isApproved() != null && !division.isApproved().booleanValue()) {
				continue;
			}
			Collection courses = getCourses(season != null ? season.getPrimaryKey() : null, period != null ? period.getPrimaryKey() : null, company != null ? company.getPrimaryKey() : null, division != null ? division.getPrimaryKey() : null, null, null);
			if (!courses.isEmpty()) {
				Set divisionParticipants = new LinkedHashSet();

				Iterator iterator2 = courses.iterator();
				while (iterator2.hasNext()) {
					Course course = (Course) iterator2.next();
					Period coursePeriod = course.getPeriod();
					Group courseGroup = course.getGroup();

					Collection participants = getParticipants(course);
					Iterator iterator3 = participants.iterator();
					while (iterator3.hasNext()) {
						User participant = (User) iterator3.next();
						if (isRegisteredInCourse(courseGroup, participant, fromDate, toDate)) {
							divisionParticipants.add(new ParticipantHolder(getParticipant(participant), course, coursePeriod));
						}
					}
				}
				map.put(division, divisionParticipants);
			}
		}

		return map;
	}

	private boolean isRegisteredInCourse(Group course, User user, Date fromDate, Date toDate) {
		try {
			GroupRelationHome home = (GroupRelationHome) IDOLookup.getHome(GroupRelation.class);
			Collection entries = home.findGroupsRelationshipsContaining(course, user);
			Iterator iterator = entries.iterator();
			while (iterator.hasNext()) {
				GroupRelation relation = (GroupRelation) iterator.next();
				IWTimestamp date = new IWTimestamp(relation.getInitiationDate());
				date.setAsDate();

				if (date.isBetween(new IWTimestamp(fromDate), new IWTimestamp(toDate)) || date.isEqualTo(new IWTimestamp(toDate))) {
					return true;
				}
			}
		}
		catch (IDOLookupException e) {
			e.printStackTrace();
		}
		catch (FinderException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Map getUnpaidEntries(IWUserContext iwuc, Company company, Date fromDate, Date toDate, Season season, Period period, Division division, User user) {
		try {
			Map map = new HashMap();
			if (division != null) {
				Collection courses = getCourses(season != null ? season.getPrimaryKey() : null, period != null ? period.getPrimaryKey() : null, company != null ? company.getPrimaryKey() : null, division != null ? division.getPrimaryKey() : null, null, null);
				if (!courses.isEmpty()) {
					Collection entries = getPaymentAllocationHome().findPaymentsByCriteria(courses, fromDate, toDate, false);

					map.put(division, entries);
				}
			}
			else {
				Collection divisions = null;
				if (company != null) {
					divisions = getDivisions(iwuc, company, user);
				}
				else {
					divisions = getDivisions();
				}
				Iterator iterator = divisions.iterator();
				while (iterator.hasNext()) {
					division = (Division) iterator.next();
					Collection courses = getCourses(season != null ? season.getPrimaryKey() : null, period != null ? period.getPrimaryKey() : null, company != null ? company.getPrimaryKey() : null, division != null ? division.getPrimaryKey() : null, null, null);
					if (!courses.isEmpty()) {
						Collection entries = getPaymentAllocationHome().findPaymentsByCriteria(courses, fromDate, toDate, false);

						map.put(division, entries);
					}
				}
			}

			return map;
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new HashMap();
		}
	}

	public void markUnpaidEntries(Collection entries) {
		IWTimestamp stamp = new IWTimestamp();

		Iterator iterator = entries.iterator();
		while (iterator.hasNext()) {
			PaymentAllocation allocation = (PaymentAllocation) iterator.next();
			allocation.setPaymentDate(stamp.getTimestamp());
			allocation.store();
		}
	}

	public void markUnpaidCostEntries(Map entries, Period period) {
		IWTimestamp stamp = new IWTimestamp();
		float amount = period.getCostAmount() != 0 ? period.getCostAmount() : Float.parseFloat(getIWApplicationContext().getApplicationSettings().getProperty(FSKConstants.PROPERTY_COST_AMOUNT, "400"));

		Iterator iterator = entries.keySet().iterator();
		while (iterator.hasNext()) {
			Division division = (Division) iterator.next();
			Collection participants = (Collection) entries.get(division);
			Iterator iterator2 = participants.iterator();
			while (iterator2.hasNext()) {
				ParticipantHolder holder = (ParticipantHolder) iterator2.next();
				User user = holder.getParticipant();
				Participant participant = getParticipant(user);
				if (!participant.hasCostMarking(division, period)) {
					participant.setCost(division, period, amount);
					participant.setCostDate(division, period, stamp.getTimestamp());
					participant.store();
				}
			}
		}
	}

	public CompanyHolder getTotalAllocationValues(CompanyHolder holder, Season season, Period period, Date fromDate, Date toDate) {
		Company company = holder.getCompany();
		Collection courses = getCourses(season != null ? season.getPrimaryKey() : null, period != null ? period.getPrimaryKey() : null, company != null ? company.getPrimaryKey() : null, null, null, null);

		if (!courses.isEmpty()) {
			try {
				holder.setAllocationAmount(getPaymentAllocationHome().getAmount(courses, fromDate, toDate, true));
			}
			catch (IDOException e) {
				e.printStackTrace();
			}

			try {
				holder.setAllocationCount(getPaymentAllocationHome().getCount(courses, fromDate, toDate, true));
			}
			catch (IDOException e) {
				e.printStackTrace();
			}
		}

		return holder;
	}

	public CompanyHolder getTotalCostsValues(IWUserContext iwuc, CompanyHolder holder, Season season, Period period, Date fromDate, Date toDate, User user) {
		Company company = holder.getCompany();

		Collection periods = new ArrayList();
		if (period != null) {
			periods.add(period);
		}
		else if (season != null) {
			try {
				periods.addAll(getPeriodHome().findAllBySeason(season));
			}
			catch (FinderException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				periods.addAll(getPeriodHome().findAllBetween(fromDate, toDate));
			}
			catch (FinderException e) {
				e.printStackTrace();
			}
		}

		int totalCount = 0;
		int totalAmount = 0;

		Collection divisions = getDivisions(iwuc, company, user);

		Iterator iter = periods.iterator();
		while (iter.hasNext()) {
			period = (Period) iter.next();

			Iterator iterator = divisions.iterator();
			while (iterator.hasNext()) {
				Division division = (Division) iterator.next();
				Collection courses = getCourses(season != null ? season.getPrimaryKey() : null, period != null ? period.getPrimaryKey() : null, company != null ? company.getPrimaryKey() : null, division.getPrimaryKey(), null, null);

				if (!courses.isEmpty()) {
					try {
						int count = getParticipantHome().getCount(courses, division, period, fromDate, toDate, true);

						totalCount += count;
						totalAmount += count * period.getCostAmount();
					}
					catch (IDOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		holder.setCostsAmount(totalAmount);
		holder.setCostsCount(totalCount);

		return holder;
	}

	public int getNumberOfAllocations(Collection courses, Date fromDate, Date toDate, boolean markedEntries) {
		try {
			return getPaymentAllocationHome().getCount(courses, fromDate, toDate, markedEntries);
		}
		catch (IDOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public float getAllocationAmount(Collection courses, Date fromDate, Date toDate, boolean markedEntries) {
		try {
			return getPaymentAllocationHome().getAmount(courses, fromDate, toDate, markedEntries);
		}
		catch (IDOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getInceptionYear() {
		return Integer.parseInt(getIWApplicationContext().getApplicationSettings().getProperty(FSKConstants.PROPERTY_INCEPTION_YEAR, "2007"));
	}
}