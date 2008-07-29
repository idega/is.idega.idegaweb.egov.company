/*
 * $Id: CourseComparator.java,v 1.1 2008/07/29 10:48:18 anton Exp $
 * Created on Jun 28, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.business;

import is.idega.idegaweb.egov.fsk.data.Course;
import is.idega.idegaweb.egov.fsk.data.Period;

import java.rmi.RemoteException;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.user.data.Group;
import com.idega.util.IWTimestamp;

public class CourseComparator implements Comparator {

	public static final int SORT_GENERAL = 1;
	public static final int SORT_NAME = 2;
	public static final int SORT_SEASON = 3;

	private IWApplicationContext iwac;
	private Locale locale;
	private int type = SORT_GENERAL;

	public CourseComparator(IWApplicationContext iwac, Locale locale) {
		this(iwac, locale, SORT_GENERAL);
	}

	public CourseComparator(IWApplicationContext iwac, Locale locale, int type) {
		this.iwac = iwac;
		this.locale = locale;
		this.type = type;
	}

	public int compare(Object arg0, Object arg1) {
		Course course1 = (Course) arg0;
		Course course2 = (Course) arg1;

		switch (type) {
			case SORT_GENERAL:
				return general(course1, course2);

			case SORT_NAME:
				return name(course1, course2);

			case SORT_SEASON:
				int returner = season(course1, course2);
				if (returner == 0) {
					returner = general(course1, course2);
				}
				return returner;
		}

		return 0;
	}

	private int season(Course course1, Course course2) {
		Period period1 = course1.getPeriod();
		Period period2 = course2.getPeriod();
		if (period1 != null && period2 != null && !period1.equals(period2)) {
			IWTimestamp start1 = new IWTimestamp(period1.getSeason().getStartDate());
			IWTimestamp start2 = new IWTimestamp(period2.getSeason().getStartDate());

			if (start1.isEarlierThan(start2)) {
				return -1;
			}
			else if (start2.isEarlierThan(start1)) {
				return 1;
			}
		}

		return 0;
	}

	private int general(Course course1, Course course2) {
		try {
			Company company1 = course1.getCompany();
			Company company2 = course2.getCompany();

			Collator collator = Collator.getInstance(locale);
			int returner = collator.compare(company1.getName(), company2.getName());

			if (returner == 0) {
				Group division1 = getBusiness(iwac).getDivision(course1);
				Group division2 = getBusiness(iwac).getDivision(course2);

				returner = groupCompare(division1, division2);
				if (returner == 0) {
					Group group1 = getBusiness(iwac).getGroup(course1);
					Group group2 = getBusiness(iwac).getGroup(course2);

					if (group1 != null && group2 != null) {
						returner = groupCompare(group1, group2);
					}

					if (returner == 0) {
						Group subGroup1 = getBusiness(iwac).getSubGroup(course1);
						Group subGroup2 = getBusiness(iwac).getSubGroup(course2);

						if (subGroup1 != null && subGroup2 != null) {
							returner = groupCompare(subGroup1, subGroup2);
						}
					}
				}
				if (returner == 0) {
					returner = collator.compare(course1.getName(), course2.getName());
				}

				if (returner == 0) {
					Period period1 = course1.getPeriod();
					Period period2 = course2.getPeriod();
					if (period1 != null && period2 != null && !period1.equals(period2)) {
						IWTimestamp start1 = new IWTimestamp(period1.getStartDate());
						IWTimestamp start2 = new IWTimestamp(period2.getStartDate());

						if (start1.isEarlierThan(start2)) {
							returner = -1;
						}
						else if (start2.isEarlierThan(start1)) {
							returner = 1;
						}
					}
				}
			}

			return returner;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private int groupCompare(Group group1, Group group2) {
		Collator collator = Collator.getInstance(locale);

		return collator.compare(group1.getName(), group2.getName());
	}

	private int name(Course course1, Course course2) {
		Collator collator = Collator.getInstance(locale);
		return collator.compare(course1.getName(), course2.getName());
	}

	private FSKBusiness getBusiness(IWApplicationContext iwac) {
		try {
			return (FSKBusiness) IBOLookup.getServiceInstance(iwac, FSKBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}