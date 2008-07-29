/*
 * $Id: ParticipantBMPBean.java,v 1.1 2008/07/29 10:48:15 anton Exp $
 * Created on Jun 28, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import com.idega.data.IDOException;
import com.idega.data.MetaData;
import com.idega.data.query.Column;
import com.idega.data.query.InCriteria;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.user.data.GroupRelation;
import com.idega.user.data.User;
import com.idega.user.data.UserBMPBean;

public class ParticipantBMPBean extends UserBMPBean implements Participant, User {

	private static final String METADATA_COURSE_STATUS = "course_status_";
	private static final String METADATA_COST = "cost_";
	private static final String METADATA_COST_DATE = "cost_date_";

	private Course course;

	public void setCourse(Course course) {
		this.course = course;
	}

	public Course getCourse() {
		return course;
	}

	public void setActive(Course course, boolean active) {
		setMetaData(METADATA_COURSE_STATUS + course.getPrimaryKey().toString(), Boolean.valueOf(active).toString(), "java.lang.Boolean");
	}

	public boolean isActive(Course course) {
		String meta = getMetaData(METADATA_COURSE_STATUS + course.getPrimaryKey().toString());
		if (meta != null && meta.length() > 0) {
			return new Boolean(meta).booleanValue();
		}
		return true;
	}

	public void setCost(Division division, Period period, float cost) {
		setMetaData(METADATA_COST + division.getPrimaryKey().toString() + "_" + period.getPrimaryKey().toString(), Float.toString(cost), "java.lang.Float");
	}

	public float getCost(Division division, Period period) {
		String meta = getMetaData(METADATA_COST + division.getPrimaryKey().toString() + "_" + period.getPrimaryKey().toString());
		if (meta != null && meta.length() > 0) {
			return new Float(meta).floatValue();
		}
		return 0;
	}

	public void setCostDate(Division division, Period period, Timestamp timestamp) {
		setMetaData(METADATA_COST_DATE + division.getPrimaryKey().toString() + "_" + period.getPrimaryKey().toString(), timestamp.toString(), "java.sql.Timestamp");
	}

	public Timestamp getCostDate(Division division, Period period) {
		String meta = getMetaData(METADATA_COST_DATE + division.getPrimaryKey().toString() + "_" + period.getPrimaryKey().toString());
		if (meta != null && meta.length() > 0) {
			return Timestamp.valueOf(meta);
		}
		return null;
	}

	public boolean hasCostMarking(Division division, Period period) {
		String meta = getMetaData(METADATA_COST + division.getPrimaryKey().toString() + "_" + period.getPrimaryKey().toString());
		return meta != null;
	}

	public int ejbHomeGetCount(Collection courses, Division division, Period period, Date fromDate, Date toDate, boolean markedEntries) throws IDOException {
		Table table = new Table(this, "u");
		Table relation = new Table(GroupRelation.class, "r");
		Table metadata = new Table(MetaData.class, "m");

		Column column = markedEntries ? new Column(metadata, "ic_metadata_id") : new Column(table, "ic_user_id");
		column.setAsCount();
		column.setAsDistinct();

		SelectQuery query = new SelectQuery(table);
		query.addColumn(column);
		if (markedEntries) {
			query.addJoin(table, metadata);
		}
		query.addJoin(table, getIDColumnName(), relation, GroupRelation.FIELD_RELATED_GROUP);
		query.addCriteria(new InCriteria(relation.getColumn(GroupRelation.FIELD_GROUP), courses));
		query.addCriteria(new MatchCriteria(relation.getColumn(GroupRelation.FIELD_INITIATION_DATE), MatchCriteria.GREATEREQUAL, fromDate));
		query.addCriteria(new MatchCriteria(relation.getColumn(GroupRelation.FIELD_INITIATION_DATE), MatchCriteria.LESSEQUAL, toDate));
		query.addCriteria(new MatchCriteria(relation.getColumn(GroupRelation.FIELD_STATUS), MatchCriteria.EQUALS, GroupRelation.STATUS_ACTIVE));
		if (markedEntries) {
			query.addCriteria(new MatchCriteria(metadata.getColumn("metadata_name"), MatchCriteria.EQUALS, METADATA_COST_DATE + division.getPrimaryKey().toString() + "_" + period.getPrimaryKey().toString()));
		}

		return idoGetNumberOfRecords(query);
	}
}