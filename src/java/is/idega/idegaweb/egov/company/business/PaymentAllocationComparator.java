/*
 * $Id: PaymentAllocationComparator.java,v 1.1 2008/07/29 12:57:44 anton Exp $
 * Created on Sep 17, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business;

import is.idega.idegaweb.egov.company.data.PaymentAllocation;

import java.util.Comparator;

import com.idega.util.IWTimestamp;

public class PaymentAllocationComparator implements Comparator {

	public int compare(Object obj1, Object obj2) {
		PaymentAllocation allocation1 = (PaymentAllocation) obj1;
		PaymentAllocation allocation2 = (PaymentAllocation) obj2;

		IWTimestamp stamp1 = new IWTimestamp(allocation1.getEntryDate());
		IWTimestamp stamp2 = new IWTimestamp(allocation2.getEntryDate());

		int difference = (int) (stamp2.getTimestamp().getTime() - stamp1.getTimestamp().getTime());
		if (difference == 0) {
			difference = ((Integer) allocation1.getPrimaryKey()).intValue() - ((Integer) allocation2.getPrimaryKey()).intValue();
		}
		return difference;
	}
}