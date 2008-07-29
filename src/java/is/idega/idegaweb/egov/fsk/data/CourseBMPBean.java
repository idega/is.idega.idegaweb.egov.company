/*
 * $Id: CourseBMPBean.java,v 1.1 2008/07/29 10:48:16 anton Exp $ Created on Jun 7, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.data;

import is.idega.idegaweb.egov.fsk.FSKConstants;

import java.sql.Date;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;

import com.idega.company.data.Company;
import com.idega.data.GenericEntity;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORuntimeException;
import com.idega.data.IDOStoreException;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.user.data.Group;
import com.idega.user.data.GroupHome;

public class CourseBMPBean extends GenericEntity implements Course {

	private static final String ENTITY_NAME = "fsk_course";

	private static final String COLUMN_PERIOD = "period_id";
	private static final String COLUMN_COMPANY = "company_id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_PRICE = "price";
	private static final String COLUMN_COST = "cost";
	private static final String COLUMN_START_DATE = "start_date";
	private static final String COLUMN_END_DATE = "end_date";
	private static final String COLUMN_NUMBER_OF_HOURS = "number_of_hours";
	private static final String COLUMN_IS_APPROVED = "is_approved";
	private static final String COLUMN_IS_CLOSED = "is_closed";
	private static final String COLUMN_MAX_MALE = "max_male";
	private static final String COLUMN_MAX_FEMALE = "max_female";

	private Group iGroup;

	protected boolean doInsertInCreate() {
		return true;
	}

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addOneToOneRelationship(getIDColumnName(), Group.class);
		setAsPrimaryKey(getIDColumnName(), true);

		addManyToOneRelationship(COLUMN_PERIOD, Period.class);
		addManyToOneRelationship(COLUMN_COMPANY, Company.class);

		addAttribute(COLUMN_NAME, "Name", String.class);
		addAttribute(COLUMN_PRICE, "Price", Float.class);
		addAttribute(COLUMN_COST, "Cost", Float.class);
		addAttribute(COLUMN_START_DATE, "Start date", Date.class);
		addAttribute(COLUMN_END_DATE, "Start date", Date.class);
		addAttribute(COLUMN_NUMBER_OF_HOURS, "Number of hours", Integer.class);
		addAttribute(COLUMN_IS_CLOSED, "Is closed", Boolean.class);
		addAttribute(COLUMN_IS_APPROVED, "Is approved", Boolean.class);
		addAttribute(COLUMN_MAX_MALE, "Max male", Integer.class);
		addAttribute(COLUMN_MAX_FEMALE, "Max female", Integer.class);
	}

	// Getters
	public Group getGroup() {
		return (Group) getColumnValue(getIDColumnName());
	}

	public Period getPeriod() {
		return (Period) getColumnValue(COLUMN_PERIOD);
	}

	public Company getCompany() {
		return (Company) getColumnValue(COLUMN_COMPANY);
	}

	public String getName() {
		return getStringColumnValue(COLUMN_NAME);
	}

	public float getPrice() {
		return getFloatColumnValue(COLUMN_PRICE);
	}

	public float getCost() {
		return getFloatColumnValue(COLUMN_COST);
	}

	public Date getStartDate() {
		return getDateColumnValue(COLUMN_START_DATE);
	}

	public Date getEndDate() {
		return getDateColumnValue(COLUMN_END_DATE);
	}

	public int getNumberOfHours() {
		return getIntColumnValue(COLUMN_NUMBER_OF_HOURS);
	}

	public boolean isApproved() {
		return getBooleanColumnValue(COLUMN_IS_APPROVED, false);
	}

	public boolean isClosed() {
		return getBooleanColumnValue(COLUMN_IS_CLOSED, false);
	}

	public int getMaxMale() {
		return getIntColumnValue(COLUMN_MAX_MALE);
	}

	public int getMaxFemale() {
		return getIntColumnValue(COLUMN_MAX_FEMALE);
	}

	// Setters
	public void setPeriod(Period period) {
		setColumn(COLUMN_PERIOD, period);
	}

	public void setCompany(Company company) {
		setColumn(COLUMN_COMPANY, company);
	}

	public void setName(String name) {
		getGeneralGroup().setName(name);
		setColumn(COLUMN_NAME, name);
	}

	public void setPrice(float price) {
		setColumn(COLUMN_PRICE, price);
	}

	public void setCost(float cost) {
		setColumn(COLUMN_COST, cost);
	}

	public void setStartDate(Date date) {
		setColumn(COLUMN_START_DATE, date);
	}

	public void setEndDate(Date date) {
		setColumn(COLUMN_END_DATE, date);
	}

	public void setNumberOfHours(int numberOfHours) {
		setColumn(COLUMN_NUMBER_OF_HOURS, numberOfHours);
	}

	public void setApproved(boolean isApproved) {
		setColumn(COLUMN_IS_APPROVED, isApproved);
	}

	public void setClosed(boolean isClosed) {
		setColumn(COLUMN_IS_CLOSED, isClosed);
	}

	public void setMaxMale(int max) {
		setColumn(COLUMN_MAX_MALE, max);
	}

	public void setMaxFemale(int max) {
		setColumn(COLUMN_MAX_FEMALE, max);
	}

	// Finders and creators
	public Object ejbCreate() throws CreateException {
		this.iGroup = this.getGroupHome().create();
		this.iGroup.setGroupType(FSKConstants.GROUP_TYPE_COURSE);
		this.iGroup.store();
		this.setPrimaryKey(this.iGroup.getPrimaryKey());
		return super.ejbCreate();
	}

	public Collection ejbFindByCompany(Company company) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COMPANY), MatchCriteria.EQUALS, company));

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindApprovedByCompany(Season season, Company company) throws FinderException {
		Table table = new Table(this);
		Table period = new Table(Period.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		try {
			query.addJoin(table, period);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COMPANY), MatchCriteria.EQUALS, company));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_APPROVED), MatchCriteria.EQUALS, true));
		if (season != null) {
			query.addCriteria(new MatchCriteria(period.getColumn("season_id"), MatchCriteria.EQUALS, season));
		}

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindRejectedByCompany(Season season, Company company) throws FinderException {
		Table table = new Table(this);
		Table period = new Table(Period.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		try {
			query.addJoin(table, period);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COMPANY), MatchCriteria.EQUALS, company));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_APPROVED), MatchCriteria.EQUALS, false));
		if (season != null) {
			query.addCriteria(new MatchCriteria(period.getColumn("season_id"), MatchCriteria.EQUALS, season));
		}

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindNonApproved() throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_APPROVED), false));

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindApproved(Season season) throws FinderException {
		Table table = new Table(this);
		Table period = new Table(Period.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		try {
			query.addJoin(table, period);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_APPROVED), MatchCriteria.EQUALS, true));
		if (season != null) {
			query.addCriteria(new MatchCriteria(period.getColumn("season_id"), MatchCriteria.EQUALS, season));
		}

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindRejected(Season season) throws FinderException {
		Table table = new Table(this);
		Table period = new Table(Period.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		try {
			query.addJoin(table, period);
		}
		catch (IDORelationshipException ire) {
			throw new FinderException(ire.getMessage());
		}
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_APPROVED), MatchCriteria.EQUALS, false));
		if (season != null) {
			query.addCriteria(new MatchCriteria(period.getColumn("season_id"), MatchCriteria.EQUALS, season));
		}

		return idoFindPKsByQuery(query);
	}

	// General methods
	public void store() throws IDOStoreException {
		getGeneralGroup().store();
		super.store();
	}

	private Group getGeneralGroup() {
		if (this.iGroup == null) {
			try {
				this.iGroup = getGroupHome().findByPrimaryKey(this.getPrimaryKey());
			}
			catch (FinderException fe) {
				fe.printStackTrace();
				throw new EJBException(fe.getMessage());
			}
		}
		return this.iGroup;
	}

	private GroupHome getGroupHome() {
		try {
			return (GroupHome) getIDOHome(Group.class);
		}
		catch (IDOLookupException e) {
			throw new IDORuntimeException(e.getMessage());
		}
	}
}
