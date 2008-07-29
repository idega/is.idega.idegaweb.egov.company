/*
 * $Id: ApplicationFilesBMPBean.java,v 1.1 2008/07/29 12:57:41 anton Exp $ Created on Jun 11, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.core.file.data.ICFile;
import com.idega.data.GenericEntity;
import com.idega.data.IDOException;
import com.idega.data.query.CountColumn;
import com.idega.data.query.InCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;

public class ApplicationFilesBMPBean extends GenericEntity implements ApplicationFiles {

	private static final String ENTITY_NAME = "fsk_application_files";

	private static final String COLUMN_APPLICATION = "application_id";
	private static final String COLUMN_TYPE = "type";
	private static final String COLUMN_FILE = "file_id";
	private static final String COLUMN_SEASON = "season_id";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());

		addManyToOneRelationship(COLUMN_APPLICATION, Application.class);
		addManyToOneRelationship(COLUMN_FILE, ICFile.class);
		addManyToOneRelationship(COLUMN_SEASON, Season.class);

		addAttribute(COLUMN_TYPE, "Type", String.class);
	}

	// Getters
	public Application getApplication() {
		return (Application) getColumnValue(COLUMN_APPLICATION);
	}

	public ICFile getFile() {
		return (ICFile) getColumnValue(COLUMN_FILE);
	}

	public Season getSeason() {
		return (Season) getColumnValue(COLUMN_SEASON);
	}

	public String getType() {
		return getStringColumnValue(COLUMN_TYPE);
	}

	// Setters
	public void setAppliation(Application application) {
		setColumn(COLUMN_APPLICATION, application);
	}

	public void setFile(ICFile file) {
		setColumn(COLUMN_FILE, file);
	}

	public void setSeason(Season season) {
		setColumn(COLUMN_SEASON, season);
	}

	public void setType(String type) {
		setColumn(COLUMN_TYPE, type);
	}

	// Finders
	public Collection ejbFindByApplication(Application application) throws FinderException {
		return ejbFindAllByApplicationAndType(application, null);
	}

	public Collection ejbFindAllByApplicationAndType(Application application, String type) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_APPLICATION), MatchCriteria.EQUALS, application));
		if (type != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_TYPE), MatchCriteria.EQUALS, type));
		}

		return idoFindPKsByQuery(query);
	}

	public Object ejbFindByApplicationAndType(Application application, String type) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_APPLICATION), MatchCriteria.EQUALS, application));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_TYPE), MatchCriteria.EQUALS, type));

		return idoFindOnePKByQuery(query);
	}

	public int ejbHomeGetNumberOfFiles(Application application, String[] types) throws IDOException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(new CountColumn(table, getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_APPLICATION), MatchCriteria.EQUALS, application));
		query.addCriteria(new InCriteria(table.getColumn(COLUMN_TYPE), types));

		return idoGetNumberOfRecords(query);
	}
}