/*
 * $Id: PeriodBMPBean.java,v 1.1 2008/07/29 10:48:15 anton Exp $ Created on Jun 7, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.data;

import java.sql.Date;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.IDOException;
import com.idega.data.query.AND;
import com.idega.data.query.CountColumn;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.OR;
import com.idega.data.query.Order;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;

public class PeriodBMPBean extends GenericEntity implements Period {

	private static final String ENTITY_NAME = "fsk_period";

	private static final String COLUMN_SEASON = "season_id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_START_DATE = "start_date";
	private static final String COLUMN_END_DATE = "end_date";
	private static final String COLUMN_MINIMUM_WEEKS = "minimum_weeks";
	private static final String COLUMN_COST_AMOUNT = "cost_amount";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());

		addManyToOneRelationship(COLUMN_SEASON, Season.class);

		addAttribute(COLUMN_NAME, "Name", String.class);
		addAttribute(COLUMN_START_DATE, "Start date", Date.class);
		addAttribute(COLUMN_END_DATE, "Start date", Date.class);
		addAttribute(COLUMN_MINIMUM_WEEKS, "Minimum weeks", Integer.class);
		addAttribute(COLUMN_COST_AMOUNT, "Cost amount", Float.class);
	}

	// Getters
	public Season getSeason() {
		return (Season) getColumnValue(COLUMN_SEASON);
	}

	public String getName() {
		return getStringColumnValue(COLUMN_NAME);
	}

	public Date getStartDate() {
		return getDateColumnValue(COLUMN_START_DATE);
	}

	public Date getEndDate() {
		return getDateColumnValue(COLUMN_END_DATE);
	}

	public int getMinimumWeeks() {
		int length = getIntColumnValue(COLUMN_MINIMUM_WEEKS);
		if (length < 0) {
			length = 10;
		}

		return length;
	}

	public float getCostAmount() {
		return getFloatColumnValue(COLUMN_COST_AMOUNT, 0);
	}

	// Setters
	public void setSeason(Season season) {
		setColumn(COLUMN_SEASON, season);
	}

	public void setName(String name) {
		setColumn(COLUMN_NAME, name);
	}

	public void setStartDate(Date date) {
		setColumn(COLUMN_START_DATE, date);
	}

	public void setEndDate(Date date) {
		setColumn(COLUMN_END_DATE, date);
	}

	public void setMinimumWeeks(int weeks) {
		setColumn(COLUMN_MINIMUM_WEEKS, weeks);
	}

	public void setCostAmount(float amount) {
		setColumn(COLUMN_COST_AMOUNT, amount);
	}

	// Finders
	public Collection ejbFindAll() throws FinderException {
		return ejbFindAllBySeason(null);
	}

	public Collection ejbFindAllBySeason(Season season) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		if (season != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_SEASON), MatchCriteria.EQUALS, season));
		}
		query.addOrder(new Order(table.getColumn(COLUMN_START_DATE), true));

		return idoFindPKsByQuery(query);
	}

	public Collection ejbFindAllBetween(Date startDate, Date endDate) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_START_DATE), MatchCriteria.GREATEREQUAL, startDate));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_START_DATE), MatchCriteria.LESSEQUAL, endDate));

		return idoFindPKsByQuery(query);
	}

	public int ejbHomeGetNumberOfPeriodsInPeriod(Object periodPK, Season season, Date startDate, Date endDate) throws IDOException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new CountColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_SEASON), MatchCriteria.EQUALS, season));
		if (periodPK != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(getIDColumnName()), MatchCriteria.NOTEQUALS, Integer.parseInt(periodPK.toString())));
		}

		AND and1 = new AND(new MatchCriteria(table.getColumn(COLUMN_START_DATE), MatchCriteria.LESSEQUAL, startDate), new MatchCriteria(table.getColumn(COLUMN_END_DATE), MatchCriteria.GREATEREQUAL, startDate));
		AND and2 = new AND(new MatchCriteria(table.getColumn(COLUMN_START_DATE), MatchCriteria.LESSEQUAL, endDate), new MatchCriteria(table.getColumn(COLUMN_END_DATE), MatchCriteria.GREATEREQUAL, endDate));
		query.addCriteria(new OR(and1, and2));

		return idoGetNumberOfRecords(query);
	}
}