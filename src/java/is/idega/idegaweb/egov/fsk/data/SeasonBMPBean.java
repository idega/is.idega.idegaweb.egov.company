/*
 * $Id: SeasonBMPBean.java,v 1.1 2008/07/29 10:48:16 anton Exp $ Created on Jun 7, 2007
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

public class SeasonBMPBean extends GenericEntity implements Season {

	private static final String ENTITY_NAME = "fsk_season";

	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_START_DATE = "start_date";
	private static final String COLUMN_END_DATE = "end_date";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());

		addAttribute(COLUMN_NAME, "Name", String.class);
		addAttribute(COLUMN_START_DATE, "Start date", Date.class);
		addAttribute(COLUMN_END_DATE, "Start date", Date.class);
	}

	// Getters
	public String getName() {
		return getStringColumnValue(COLUMN_NAME);
	}

	public Date getStartDate() {
		return getDateColumnValue(COLUMN_START_DATE);
	}

	public Date getEndDate() {
		return getDateColumnValue(COLUMN_END_DATE);
	}

	// Setters
	public void setName(String name) {
		setColumn(COLUMN_NAME, name);
	}

	public void setStartDate(Date date) {
		setColumn(COLUMN_START_DATE, date);
	}

	public void setEndDate(Date date) {
		setColumn(COLUMN_END_DATE, date);
	}

	// Finders
	public Collection ejbFindAll() throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addOrder(new Order(table.getColumn(COLUMN_START_DATE), true));

		return idoFindPKsByQuery(query);
	}

	public Object ejbFindByDate(Date date) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_START_DATE), MatchCriteria.LESSEQUAL, date));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_END_DATE), MatchCriteria.GREATEREQUAL, date));

		return idoFindOnePKByQuery(query);
	}

	public int ejbHomeGetNumberOfSeasonsInPeriod(Object seasonPK, Date startDate, Date endDate) throws IDOException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new CountColumn(getIDColumnName()));
		if (seasonPK != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(getIDColumnName()), MatchCriteria.NOTEQUALS, Integer.parseInt(seasonPK.toString())));
		}

		AND and1 = new AND(new MatchCriteria(table.getColumn(COLUMN_START_DATE), MatchCriteria.LESSEQUAL, startDate), new MatchCriteria(table.getColumn(COLUMN_END_DATE), MatchCriteria.GREATEREQUAL, startDate));
		AND and2 = new AND(new MatchCriteria(table.getColumn(COLUMN_START_DATE), MatchCriteria.LESSEQUAL, endDate), new MatchCriteria(table.getColumn(COLUMN_END_DATE), MatchCriteria.GREATEREQUAL, endDate));
		query.addCriteria(new OR(and1, and2));

		return idoGetNumberOfRecords(query);
	}
}