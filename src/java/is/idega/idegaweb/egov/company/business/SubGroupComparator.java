/*
 * $Id: SubGroupComparator.java,v 1.1 2008/07/29 12:57:44 anton Exp $
 * Created on Jun 28, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.idega.user.data.Group;

public class SubGroupComparator implements Comparator {

	private Locale locale;

	public SubGroupComparator(Locale locale) {
		this.locale = locale;
	}

	public int compare(Object arg0, Object arg1) {
		Collator collator = Collator.getInstance(locale);

		Group subGroup1 = (Group) arg0;
		Group subGroup2 = (Group) arg1;

		Group group1 = (Group) subGroup1.getParentNode();
		Group group2 = (Group) subGroup2.getParentNode();

		Group division1 = (Group) group1.getParentNode();
		Group division2 = (Group) group2.getParentNode();

		int returner = divisionCompare(division1, division2);
		if (returner == 0) {
			returner = groupCompare(group1, group2);
		}
		if (returner == 0) {
			returner = collator.compare(subGroup1.getName(), subGroup2.getName());
		}

		return returner;
	}

	private int divisionCompare(Group group1, Group group2) {
		Collator collator = Collator.getInstance(locale);

		return collator.compare(group1.getName(), group2.getName());
	}

	private int groupCompare(Group group1, Group group2) {
		Collator collator = Collator.getInstance(locale);

		return collator.compare(group1.getName(), group2.getName());
	}
}
