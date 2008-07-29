/*
 * $Id: ParticipantDiscountBMPBean.java,v 1.1 2008/07/29 10:48:16 anton Exp $
 * Created on Jan 24, 2008
 *
 * Copyright (C) 2008 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.data;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.user.data.User;

public class ParticipantDiscountBMPBean extends GenericEntity implements ParticipantDiscount {

	private static final String ENTITY_NAME = "fsk_participant_discount";

	private static final String COLUMN_PARTICIPANT = "participant_id";
	private static final String COLUMN_COURSE = "course_id";
	private static final String COLUMN_DISCOUNT = "discount";
	private static final String COLUMN_TYPE = "type";

	private static final String TYPE_PERCENT = "PERCENT";
	private static final String TYPE_AMOUNT = "AMOUNT";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());

		addManyToOneRelationship(COLUMN_PARTICIPANT, Participant.class);
		addManyToOneRelationship(COLUMN_COURSE, Course.class);

		addAttribute(COLUMN_DISCOUNT, "Discount", Float.class);
		addAttribute(COLUMN_TYPE, "Discount type", String.class);
	}

	//Getters
	public Participant getParticipant() {
		return (Participant) getColumnValue(COLUMN_PARTICIPANT);
	}

	public Course getCourse() {
		return (Course) getColumnValue(COLUMN_COURSE);
	}

	public float getDiscount() {
		return getFloatColumnValue(COLUMN_DISCOUNT);
	}

	public boolean isPercentage() {
		return getStringColumnValue(COLUMN_TYPE).equals(TYPE_PERCENT);
	}

	public boolean isAmount() {
		return getStringColumnValue(COLUMN_TYPE).equals(TYPE_AMOUNT);
	}

	//Setters
	public void setParticipant(User user) {
		setColumn(COLUMN_PARTICIPANT, user);
	}

	public void setCourse(Course course) {
		setColumn(COLUMN_COURSE, course);
	}

	public void setDiscount(float discount) {
		setColumn(COLUMN_DISCOUNT, discount);
	}

	public void setAsPercentage() {
		setColumn(COLUMN_TYPE, TYPE_PERCENT);
	}

	public void setAsAmount() {
		setColumn(COLUMN_TYPE, TYPE_AMOUNT);
	}

	//Finders
	public Object ejbFindByParticipantAndCourse(User user, Course course) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_PARTICIPANT), MatchCriteria.EQUALS, user));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_COURSE), MatchCriteria.EQUALS, course));

		return idoFindOnePKByQuery(query);
	}
}