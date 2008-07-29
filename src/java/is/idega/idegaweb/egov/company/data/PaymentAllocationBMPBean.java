/*
 * $Id: PaymentAllocationBMPBean.java,v 1.1 2008/07/29 12:57:41 anton Exp $ Created on Jun 7, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.IDOCompositePrimaryKeyException;
import com.idega.data.IDOException;
import com.idega.data.IDORelationshipException;
import com.idega.data.query.CountColumn;
import com.idega.data.query.InCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.Order;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.SumColumn;
import com.idega.data.query.Table;
import com.idega.user.data.User;

public class PaymentAllocationBMPBean extends GenericEntity implements PaymentAllocation {

	private static final String ENTITY_NAME = "fsk_payment_allocation";

	private static final String COLUMN_ALLOCATION = "allocation_id";
	private static final String COLUMN_USER = "user_id";
	private static final String COLUMN_COURSE = "course_id";
	private static final String COLUMN_AMOUNT = "amount";
	private static final String COLUMN_IS_LOCKED = "is_locked";
	private static final String COLUMN_ENTRY_DATE = "entry_date";
	private static final String COLUMN_PAYMENT_DATE = "payment_date";
	private static final String COLUMN_COST_DATE = "cost_date";
	private static final String COLUMN_COST_AMOUNT = "cost_amount";
	private static final String COLUMN_TRANSFER_PAYMENT = "transfer_payment";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());

		addManyToOneRelationship(COLUMN_ALLOCATION, Allocation.class);
		addManyToOneRelationship(COLUMN_USER, User.class);
		addManyToOneRelationship(COLUMN_COURSE, Course.class);

		addAttribute(COLUMN_AMOUNT, "Amount", Float.class);
		addAttribute(COLUMN_IS_LOCKED, "Is locked", Boolean.class);
		addAttribute(COLUMN_ENTRY_DATE, "Entry date", Timestamp.class);
		addAttribute(COLUMN_PAYMENT_DATE, "Payment date", Timestamp.class);
		addAttribute(COLUMN_COST_DATE, "Cost date", Timestamp.class);
		addAttribute(COLUMN_COST_AMOUNT, "Cost amount", Float.class);
		addAttribute(COLUMN_TRANSFER_PAYMENT, "Transfer payment", Boolean.class);
	}

	// Getters
	public Allocation getAllocation() {
		return (Allocation) getColumnValue(COLUMN_ALLOCATION);
	}

	public User getUser() {
		return (User) getColumnValue(COLUMN_USER);
	}

	public Course getCourse() {
		return (Course) getColumnValue(COLUMN_COURSE);
	}

	public float getAmount() {
		return getFloatColumnValue(COLUMN_AMOUNT);
	}

	public boolean isLocked() {
		return getBooleanColumnValue(COLUMN_IS_LOCKED, false);
	}
	
	public boolean isTransfer() {
		return getBooleanColumnValue(COLUMN_TRANSFER_PAYMENT, false);
	}

	public Timestamp getEntryDate() {
		return getTimestampColumnValue(COLUMN_ENTRY_DATE);
	}

	public Timestamp getPaymentDate() {
		return getTimestampColumnValue(COLUMN_PAYMENT_DATE);
	}

	public Timestamp getCostDate() {
		return getTimestampColumnValue(COLUMN_COST_DATE);
	}

	public float getCostAmount() {
		return getFloatColumnValue(COLUMN_COST_AMOUNT, 0);
	}

	// Setters
	public void setAllocation(Allocation allocation) {
		setColumn(COLUMN_ALLOCATION, allocation);
	}

	public void setUser(User user) {
		setColumn(COLUMN_USER, user);
	}

	public void setCourse(Course course) {
		setColumn(COLUMN_COURSE, course);
	}

	public void setAmount(float amount) {
		setColumn(COLUMN_AMOUNT, amount);
	}

	public void setLocked(boolean locked) {
		setColumn(COLUMN_IS_LOCKED, locked);
	}
	
	public void setTransfer(boolean transfer) {
		setColumn(COLUMN_TRANSFER_PAYMENT, transfer);
	}

	public void setEntryDate(Timestamp date) {
		setColumn(COLUMN_ENTRY_DATE, date);
	}

	public void setPaymentDate(Timestamp date) {
		setColumn(COLUMN_PAYMENT_DATE, date);
	}

	public void setCostDate(Timestamp date) {
		setColumn(COLUMN_COST_DATE, date);
	}

	public void setCostAmount(float amount) {
		setColumn(COLUMN_COST_AMOUNT, amount);
	}

	// Finders
	public Collection ejbFindByUser(User user) throws FinderException {
		return ejbFindByUser((Season) null, user);
	}

	public Collection ejbFindByUser(Season season, User user) throws FinderException {
		Table table = new Table(this);
		Table allocation = new Table(Allocation.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		try {
			query.addJoin(table, allocation);
		}
		catch (IDORelationshipException e) {
			throw new FinderException(e.getMessage());
		}
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_USER), MatchCriteria.EQUALS, user));
		if (season != null) {
			query.addCriteria(new MatchCriteria(allocation.getColumn("season_id"), MatchCriteria.EQUALS, season));
		}
		query.addOrder(new Order(table.getColumn(COLUMN_ENTRY_DATE), true));

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindByUser(Course course, User user) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));

		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_USER), MatchCriteria.EQUALS, user));
		if (course != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COURSE), MatchCriteria.EQUALS, course));
		}
		query.addOrder(new Order(table.getColumn(COLUMN_ENTRY_DATE), true));

		return idoFindPKsByQuery(query);
	}

	public Object ejbFindLatestByUser(Course course, User user) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));

		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_USER), MatchCriteria.EQUALS, user));
		if (course != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COURSE), MatchCriteria.EQUALS, course));
		}
		query.addOrder(new Order(table.getColumn(COLUMN_ENTRY_DATE), false));

		return idoFindOnePKByQuery(query);
	}

	public Collection ejbFindByUser(Period period, User user) throws IDOException, FinderException {
		Table table = new Table(this);
		Table course = new Table(Course.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addJoin(table, course);

		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_USER), MatchCriteria.EQUALS, user));
		if (period != null) {
			query.addCriteria(new MatchCriteria(course.getColumn("period_id"), MatchCriteria.EQUALS, period));
		}
		query.addOrder(new Order(table.getColumn(COLUMN_ENTRY_DATE), true));

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindPaymentsByCriteria(Collection courses, Date fromDate, Date toDate, boolean hasDateSet) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ENTRY_DATE), MatchCriteria.GREATEREQUAL, fromDate));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ENTRY_DATE), MatchCriteria.LESSEQUAL, toDate));
		query.addCriteria(new InCriteria(table.getColumn(COLUMN_COURSE), courses));
		if (hasDateSet) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_PAYMENT_DATE), true));
		}
		else {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_PAYMENT_DATE)));
		}
		query.addOrder(new Order(table.getColumn(COLUMN_ENTRY_DATE), true));

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindCostsByCriteria(Collection courses, Date fromDate, Date toDate, boolean hasDateSet) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ENTRY_DATE), MatchCriteria.GREATEREQUAL, fromDate));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ENTRY_DATE), MatchCriteria.LESSEQUAL, toDate));
		query.addCriteria(new InCriteria(table.getColumn(COLUMN_COURSE), courses));
		if (hasDateSet) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COST_DATE), true));
		}
		else {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COST_DATE)));
		}
		query.addOrder(new Order(table.getColumn(COLUMN_ENTRY_DATE), true));

		return idoFindPKsByQuery(query);
	}

	public int ejbHomeGetTotalAmount(Season season, User user) throws IDOException {
		Table table = new Table(this);
		Table allocation = new Table(Allocation.class);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new SumColumn(table, COLUMN_AMOUNT));
		query.addJoin(table, allocation);
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_USER), MatchCriteria.EQUALS, user));
		query.addCriteria(new MatchCriteria(allocation.getColumn("season_id"), MatchCriteria.EQUALS, season));

		return idoGetNumberOfRecords(query);
	}

	public int ejbHomeGetAmount(Course course) throws IDOException {
		return ejbHomeGetAmount(course, null);
	}

	public int ejbHomeGetAmount(Course course, Date fromDate, Date toDate) throws IDOException {
		return ejbHomeGetAmount(course, null, fromDate, toDate, null);
	}

	public int ejbHomeGetAmount(Course course, Date fromDate, Date toDate, boolean costMarked) throws IDOException {
		return ejbHomeGetAmount(course, null, fromDate, toDate, new Boolean(costMarked));
	}

	public int ejbHomeGetAmount(Course course, User user) throws IDOException {
		return ejbHomeGetAmount(course, user, null, null, null);
	}

	public int ejbHomeGetAmount(Course course, User user, Date fromDate, Date toDate, Boolean costMarked) throws IDOException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new SumColumn(table, COLUMN_AMOUNT));
		if (user != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_USER), MatchCriteria.EQUALS, user));
		}
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COURSE), MatchCriteria.EQUALS, course));
		if (fromDate != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ENTRY_DATE), MatchCriteria.GREATEREQUAL, fromDate));
		}
		if (toDate != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ENTRY_DATE), MatchCriteria.LESSEQUAL, toDate));
		}

		return idoGetNumberOfRecords(query);
	}

	public int ejbHomeGetAmount(Collection courses, Date fromDate, Date toDate, boolean markedEntries) throws IDOException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new SumColumn(table, COLUMN_AMOUNT));
		query.addCriteria(new InCriteria(table.getColumn(COLUMN_COURSE), courses));
		if (fromDate != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ENTRY_DATE), MatchCriteria.GREATEREQUAL, fromDate));
		}
		if (toDate != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ENTRY_DATE), MatchCriteria.LESSEQUAL, toDate));
		}
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_PAYMENT_DATE), markedEntries));

		return idoGetNumberOfRecords(query);
	}

	public int ejbHomeGetCount(Collection courses, Date fromDate, Date toDate, boolean markedEntries) throws IDOException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new CountColumn(table, getIDColumnName()));
		query.addCriteria(new InCriteria(table.getColumn(COLUMN_COURSE), courses));
		if (fromDate != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ENTRY_DATE), MatchCriteria.GREATEREQUAL, fromDate));
		}
		if (toDate != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ENTRY_DATE), MatchCriteria.LESSEQUAL, toDate));
		}
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_PAYMENT_DATE), markedEntries));

		return idoGetNumberOfRecords(query);
	}

	public int ejbHomeGetNumberOfAllocations(Period period, User user) throws IDOException {
		Table table = new Table(this);
		Table allocation = new Table(Allocation.class);
		Table periods = new Table(Period.class);

		SelectQuery query = new SelectQuery(table);
		CountColumn countColumn = new CountColumn(table, COLUMN_COURSE);
		countColumn.setAsDistinct();
		query.addColumn(countColumn);
		query.addJoin(table, allocation);
		query.addJoin(allocation, "season_id", periods, "season_id");
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_USER), MatchCriteria.EQUALS, user));
		try {
			query.addCriteria(new MatchCriteria(periods.getColumn(periods.getPrimaryKeyColumnName()), MatchCriteria.EQUALS, period));
		}
		catch (IDOCompositePrimaryKeyException icpe) {
			throw new IDOException(icpe);
		}

		return idoGetNumberOfRecords(query);
	}

	public int ejbHomeGetNumberOfAllocations(Allocation allocation) throws IDOException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new CountColumn(table, getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_ALLOCATION), MatchCriteria.EQUALS, allocation));

		return idoGetNumberOfRecords(query);
	}

	public int ejbHomeGetNumberOfCostMarkedAllocations(Collection courses, User user) throws IDOException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new CountColumn(table, getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_USER), MatchCriteria.EQUALS, user));
		query.addCriteria(new InCriteria(table.getColumn(COLUMN_COURSE), courses));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COST_DATE), true));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COST_AMOUNT), true));

		return idoGetNumberOfRecords(query);
	}
}