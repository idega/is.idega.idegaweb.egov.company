/*
 * $Id: AllocationBMPBean.java,v 1.1 2008/07/29 12:57:42 anton Exp $ Created on Jun 7, 2007
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
import com.idega.data.IDOException;
import com.idega.data.query.AND;
import com.idega.data.query.CountColumn;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.OR;
import com.idega.data.query.Order;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;

public class AllocationBMPBean extends GenericEntity implements Allocation {

	private static final String ENTITY_NAME = "fsk_allocation";

	private static final String COLUMN_SEASON = "season_id";
	private static final String COLUMN_CREATION_DATE = "creation_date";
	private static final String COLUMN_AMOUNT = "amount";
	private static final String COLUMN_BIRTHYEAR_FROM = "birthyear_from";
	private static final String COLUMN_BIRTHYEAR_TO = "birthyear_to";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());

		addManyToOneRelationship(COLUMN_SEASON, "Season", Season.class);

		addAttribute(COLUMN_CREATION_DATE, "Creation date", Date.class);
		addAttribute(COLUMN_AMOUNT, "Amount", Float.class);
		addAttribute(COLUMN_BIRTHYEAR_FROM, "Birthyear from", Integer.class);
		addAttribute(COLUMN_BIRTHYEAR_TO, "Birthyear to", Integer.class);
	}

	// Getters
	public Season getSeason() {
		return (Season) getColumnValue(COLUMN_SEASON);
	}

	public Date getCreationDate() {
		return getDateColumnValue(COLUMN_CREATION_DATE);
	}

	public float getAmount() {
		return getFloatColumnValue(COLUMN_AMOUNT);
	}

	public int getBirthyearFrom() {
		return getIntColumnValue(COLUMN_BIRTHYEAR_FROM);
	}

	public int getBirthyearTo() {
		return getIntColumnValue(COLUMN_BIRTHYEAR_TO);
	}

	// Setters
	public void setSeason(Season season) {
		setColumn(COLUMN_SEASON, season);
	}

	public void setCreationDate(Date date) {
		setColumn(COLUMN_CREATION_DATE, date);
	}

	public void setAmount(float amount) {
		setColumn(COLUMN_AMOUNT, amount);
	}

	public void setBirthyearFrom(int year) {
		setColumn(COLUMN_BIRTHYEAR_FROM, year);
	}

	public void setBirthyearTo(int year) {
		setColumn(COLUMN_BIRTHYEAR_TO, year);
	}

	// Finders
	public Collection ejbFindAll() throws FinderException {
		return ejbFindAll(null);
	}

	public Collection ejbFindAll(Season season) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		if (season != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_SEASON), MatchCriteria.EQUALS, season));
		}
		query.addOrder(new Order(table.getColumn(COLUMN_BIRTHYEAR_FROM), true));

		return idoFindPKsByQuery(query);
	}

	public Object ejbFindByBirthyear(Season season, int year) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_SEASON), MatchCriteria.EQUALS, season));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_BIRTHYEAR_FROM), MatchCriteria.LESSEQUAL, year));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_BIRTHYEAR_TO), MatchCriteria.GREATEREQUAL, year));

		return idoFindOnePKByQuery(query);
	}

	public int ejbHomeGetNumberOfAllocationsInPeriod(Object allocationPK, Season season, int birthyearFrom, int birthyearTo) throws IDOException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new CountColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_SEASON), MatchCriteria.EQUALS, season));
		if (allocationPK != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(getIDColumnName()), MatchCriteria.NOTEQUALS, Integer.parseInt(allocationPK.toString())));
		}

		AND and1 = new AND(new MatchCriteria(table.getColumn(COLUMN_BIRTHYEAR_FROM), MatchCriteria.LESSEQUAL, birthyearFrom), new MatchCriteria(table.getColumn(COLUMN_BIRTHYEAR_TO), MatchCriteria.GREATEREQUAL, birthyearFrom));
		AND and2 = new AND(new MatchCriteria(table.getColumn(COLUMN_BIRTHYEAR_FROM), MatchCriteria.LESSEQUAL, birthyearTo), new MatchCriteria(table.getColumn(COLUMN_BIRTHYEAR_TO), MatchCriteria.GREATEREQUAL, birthyearTo));
		query.addCriteria(new OR(and1, and2));

		return idoGetNumberOfRecords(query);
	}
}