/*
 * $Id: DivisionNameBMPBean.java,v 1.1 2008/07/29 10:48:15 anton Exp $
 * Created on Jul 19, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.company.data.CompanyType;
import com.idega.data.GenericEntity;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.OR;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;

public class DivisionNameBMPBean extends GenericEntity implements DivisionName {

	private static final String ENTITY_NAME = "fsk_division_name";

	private static final String COLUMN_COMPANY_TYPE = "company_type";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_VALID = "is_valid";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());

		addManyToOneRelationship(COLUMN_COMPANY_TYPE, CompanyType.class);
		addAttribute(COLUMN_NAME, "Name", String.class);
		addAttribute(COLUMN_VALID, "Is valid", Boolean.class);
	}

	//Getters
	public CompanyType getType() {
		return (CompanyType) getColumnValue(COLUMN_COMPANY_TYPE);
	}

	public String getName() {
		return getStringColumnValue(COLUMN_NAME);
	}

	public boolean isValid() {
		return getBooleanColumnValue(COLUMN_VALID, true);
	}

	//Setters
	public void setType(CompanyType type) {
		setColumn(COLUMN_COMPANY_TYPE, type);
	}

	public void setName(String name) {
		setColumn(COLUMN_NAME, name);
	}

	public void setValid(boolean valid) {
		setColumn(COLUMN_VALID, valid);
	}

	//Finders
	public Collection ejbFindAllByType(CompanyType type) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		if (type != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COMPANY_TYPE), MatchCriteria.EQUALS, type));
		}
		query.addCriteria(new OR(new MatchCriteria(table.getColumn(COLUMN_VALID)), new MatchCriteria(table.getColumn(COLUMN_VALID), MatchCriteria.EQUALS, true)));
		query.addOrder(table, COLUMN_COMPANY_TYPE, true);
		query.addOrder(table, COLUMN_NAME, true);

		return idoFindPKsByQuery(query);
	}

	public Object ejbFindByNameAndType(String name, CompanyType type) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_NAME), MatchCriteria.EQUALS, name));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COMPANY_TYPE), MatchCriteria.EQUALS, type));
		query.addCriteria(new OR(new MatchCriteria(table.getColumn(COLUMN_VALID)), new MatchCriteria(table.getColumn(COLUMN_VALID), MatchCriteria.EQUALS, true)));

		return idoFindPKsByQuery(query);
	}
}