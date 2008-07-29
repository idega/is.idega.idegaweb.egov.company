/*
 * $Id: ParticipantHolderComparator.java,v 1.1 2008/07/29 12:57:44 anton Exp $
 * Created on Sep 17, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business;

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

public class ParticipantHolderComparator implements Comparator {

	private IWApplicationContext iwac;
	private Locale locale;
	private boolean sortDate = false;

	public ParticipantHolderComparator(IWApplicationContext iwac, Locale locale, boolean sortDate) {
		this.iwac = iwac;
		this.locale = locale;
		this.sortDate = sortDate;
	}

	public int compare(Object obj1, Object obj2) {
		ParticipantHolder holder1 = (ParticipantHolder) obj1;
		ParticipantHolder holder2 = (ParticipantHolder) obj2;

		User user1 = holder1.getParticipant();
		User user2 = holder2.getParticipant();

		if (sortDate) {
			try {
				PaymentAllocation allocation1 = getBusiness(iwac).getLatestAllocation(holder1.getCourse(), user1);
				PaymentAllocation allocation2 = getBusiness(iwac).getLatestAllocation(holder2.getCourse(), user2);

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