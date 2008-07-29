/*
 * $Id: ParticipantHolder.java,v 1.1 2008/07/29 10:48:19 anton Exp $
 * Created on Nov 19, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.business;

import is.idega.idegaweb.egov.fsk.data.Course;
import is.idega.idegaweb.egov.fsk.data.Participant;
import is.idega.idegaweb.egov.fsk.data.Period;

public class ParticipantHolder {

	private Participant participant;
	private Course course;
	private Period period;

	public ParticipantHolder(Participant participant, Course course) {
		this.participant = participant;
		this.course = course;
	}

	public ParticipantHolder(Participant participant, Course course, Period period) {
		this.participant = participant;
		this.course = course;
		this.period = period;
	}

	public Participant getParticipant() {
		return this.participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public boolean equals(Object obj) {
		return equals((ParticipantHolder) obj);
	}

	public boolean equals(ParticipantHolder holder) {
		return participant.equals(holder.getParticipant()) && period.equals(holder.getPeriod());
	}

	public int hashCode() {
		String string = participant.getPrimaryKey().toString() + period != null ? period.getPrimaryKey().toString() : "";
		return Integer.parseInt(string);
	}

	public String toString() {
		return participant.getName() + " - " + course.getName();
	}
}