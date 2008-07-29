/*
 * $Id: DivisionComparator.java,v 1.1 2008/07/29 12:57:44 anton Exp $
 * Created on Jun 28, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business;

import is.idega.idegaweb.egov.company.data.Division;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.idega.company.data.Company;
import com.idega.user.data.Group;

public class DivisionComparator implements Comparator {

	private Locale locale;
	private boolean compareByCompany = false;

	public DivisionComparator(Locale locale) {
		this(locale, false);
	}

	public DivisionComparator(Locale locale, boolean compareByCompany) {
		this.locale = locale;
		this.compareByCompany = compareByCompany;
	}

	public int compare(Object arg0, Object arg1) {
		Collator collator = Collator.getInstance(locale);

		Group group1 = (Group) arg0;
		Group group2 = (Group) arg1;

		int returner = 0;

		if (compareByCompany) {
			Division division1 = (Division) group1;
			Division division2 = (Division) group2;

			Company company1 = division1.getCompany();
			Company company2 = division2.getCompany();

			if (company1 == null && company2 != null) {
				returner = 1;
			}
			else if (company1 != null && company2 == null) {
				returner = -1;
			}
			else if (company1 != null && company2 != null) {
				returner = collator.compare(company1.getName(), company2.getName());
			}
		}

		if (returner == 0) {
			returner = collator.compare(group1.getName(), group2.getName());
		}

		return returner;
	}

}
