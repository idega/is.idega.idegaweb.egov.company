/*
 * $Id: GroupComparator.java,v 1.1 2008/07/29 10:48:19 anton Exp $
 * Created on Jun 28, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.business;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.idega.user.data.Group;

public class GroupComparator implements Comparator {

	private Locale locale;

	public GroupComparator(Locale locale) {
		this.locale = locale;
	}

	public int compare(Object arg0, Object arg1) {
		Collator collator = Collator.getInstance(locale);

		Group group1 = (Group) arg0;
		Group group2 = (Group) arg1;

		Group division1 = (Group) group1.getParentNode();
		Group division2 = (Group) group2.getParentNode();

		int returner = divisionCompare(division1, division2);
		if (returner == 0) {
			returner = collator.compare(group1.getName(), group2.getName());
		}

		return returner;
	}

	private int divisionCompare(Group group1, Group group2) {
		Collator collator = Collator.getInstance(locale);

		return collator.compare(group1.getName(), group2.getName());
	}
}