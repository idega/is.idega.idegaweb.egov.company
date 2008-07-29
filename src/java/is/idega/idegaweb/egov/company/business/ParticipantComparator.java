/*
 * $Id: ParticipantComparator.java,v 1.1 2008/07/29 12:57:44 anton Exp $
 * Created on Sep 17, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business;

import is.idega.idegaweb.egov.company.data.Course;
import is.idega.idegaweb.egov.company.data.Division;
import is.idega.idegaweb.egov.company.data.PaymentAllocation;

import java.rmi.RemoteException;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
import com.idega.util.text.Name;

public class ParticipantComparator implements Comparator {

	private IWApplicationContext iwac;
	private Locale locale;
	private Division division;
	private Course course;

	public ParticipantComparator(IWApplicationContext iwac, Locale locale, Course course) {
		this.iwac = iwac;
		this.locale = locale;
		this.course = course;
	}

	public ParticipantComparator(IWApplicationContext iwac, Locale locale, Division division) {
		this.iwac = iwac;
		this.locale = locale;
		this.division = division;
	}

	public int compare(Object obj1, Object obj2) {
		User user1 = (User) obj1;
		User user2 = (User) obj2;

		if (course != null) {
			try {
				PaymentAllocation allocation1 = getBusiness(iwac).getLatestAllocation(course, user1);
				PaymentAllocation allocation2 = getBusiness(iwac).getLatestAllocation(course, user2);

				if (allocation1 != null && allocation2 != null) {
					IWTimestamp stamp1 = new IWTimestamp(allocation1.getEntryDate());
					IWTimestamp stamp2 = new IWTimestamp(allocation2.getEntryDate());
					if (stamp1.isLaterThan(stamp2)) {
						return -1;
					}
					else if (stamp2.isLaterThan(stamp1)) {
						return 1;
					}
				}
				else if (allocation1 != null && allocation2 == null) {
					return -1;
				}
				else if (allocation1 == null && allocation2 != null) {
					return 1;
				}
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
		else if (division != null) {
			try {
				PaymentAllocation allocation1 = getBusiness(iwac).getLatestAllocation(division, user1);
				PaymentAllocation allocation2 = getBusiness(iwac).getLatestAllocation(division, user2);

				if (allocation1 != null && allocation2 != null) {
					IWTimestamp stamp1 = new IWTimestamp(allocation1.getEntryDate());
					IWTimestamp stamp2 = new IWTimestamp(allocation2.getEntryDate());
					return (int) (stamp2.getTimestamp().getTime() - stamp1.getTimestamp().getTime());
				}
				else if (allocation1 != null && allocation2 == null) {
					return -1;
				}
				else if (allocation1 == null && allocation2 != null) {
					return 1;
				}
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}

		return Collator.getInstance(locale).compare(new Name(user1.getFirstName(), user1.getMiddleName(), user1.getLastName()).getName(locale), new Name(user2.getFirstName(), user2.getMiddleName(), user2.getLastName()).getName(locale));
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