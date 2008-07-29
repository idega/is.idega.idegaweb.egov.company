/*
 * $Id: ConstantBMPBean.java,v 1.1 2008/07/29 12:57:41 anton Exp $ Created on Jun 7, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.data;

import java.sql.Date;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.Order;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;

public class ConstantBMPBean extends GenericEntity implements Constant {

	private static final String ENTITY_NAME = "fsk_constants";

	private static final String COLUMN_PERIOD = "period_id";
	private static final String COLUMN_TYPE = "constant_type";
	private static final String COLUMN_START_DATE = "start_date";
	private static final String COLUMN_END_DATE = "end_date";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());

		addManyToOneRelationship(COLUMN_PERIOD, "Period", Period.class);

		addAttribute(COLUMN_TYPE, "Type", String.class);
		addAttribute(COLUMN_START_DATE, "Start date", Date.class);
		addAttribute(COLUMN_END_DATE, "Start date", Date.class);
	}

	// Getters
	public Period getPeriod() {
		return (Period) getColumnValue(COLUMN_PERIOD);
	}

	public String getType() {
		return getStringColumnValue(COLUMN_TYPE);
	}

	public Date getStartDate() {
		return getDateColumnValue(COLUMN_START_DATE);
	}

	public Date getEndDate() {
		return getDateColumnValue(COLUMN_END_DATE);
	}

	// Setters
	public void setPeriod(Period period) {
		setColumn(COLUMN_PERIOD, period);
	}

	public void setType(String type) {
		setColumn(COLUMN_TYPE, type);
	}

	public void setStartDate(Date date) {
		setColumn(COLUMN_START_DATE, date);
	}

	public void setEndDate(Date date) {
		setColumn(COLUMN_END_DATE, date);
	}

	// Finders
	public Collection ejbFindAll(Period period) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_PERIOD), MatchCriteria.EQUALS, period));
		query.addOrder(new Order(table.getColumn(COLUMN_TYPE), true));

		return idoFindPKsByQuery(query);
	}

	public Object ejbFindByType(Period period, String type) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_PERIOD), MatchCriteria.EQUALS, period));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_TYPE), MatchCriteria.EQUALS, type));
		query.addOrder(new Order(table.getColumn(COLUMN_TYPE), true));

		return idoFindOnePKByQuery(query);
	}
}