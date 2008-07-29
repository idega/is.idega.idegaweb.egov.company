/*
 * $Id: ApplicationComparator.java,v 1.1 2008/07/29 10:48:19 anton Exp $ Created on Jun 11, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.business;

import is.idega.idegaweb.egov.fsk.data.Application;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.idega.company.data.CompanyType;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.util.IWTimestamp;

public class ApplicationComparator implements Comparator {

	public static final int SORT_NAME = 1;
	public static final int SORT_PERSONAL_ID = 2;
	public static final int SORT_TYPE = 3;
	public static final int SORT_CREATED = 4;

	private int sortType = SORT_CREATED;
	private IWApplicationContext iwac;
	private Locale locale;

	public ApplicationComparator(IWApplicationContext iwac, int sortType, Locale locale) {
		this.sortType = sortType;
		this.iwac = iwac;
		this.locale = locale;
	}

	public int compare(Object arg0, Object arg1) {
		Application app1 = (Application) arg0;
		Application app2 = (Application) arg1;

		switch (sortType) {
			case SORT_NAME:
				return nameCompare(app1, app2);

			case SORT_PERSONAL_ID:
				return personalIDCompare(app1, app2);

			case SORT_TYPE:
				return typeCompare(app1, app2);

			case SORT_CREATED:
				return createdCompare(app1, app2);

			default:
				createdCompare(app1, app2);
				break;
		}
		return 0;
	}

	private int nameCompare(Application app1, Application app2) {
		Collator collator = Collator.getInstance(locale);
		return collator.compare(app1.getName(), app2.getName());
	}

	private int personalIDCompare(Application app1, Application app2) {
		Collator collator = Collator.getInstance(locale);
		return collator.compare(app1.getPersonalID(), app2.getPersonalID());
	}

	private int typeCompare(Application app1, Application app2) {
		CompanyType type1 = app1.getType();
		CompanyType type2 = app2.getType();

		Collator collator = Collator.getInstance(locale);
		return collator.compare(type1.getLocalizedName(iwac, locale), type2.getLocalizedName(iwac, locale));
	}

	private int createdCompare(Application app1, Application app2) {
		IWTimestamp created1 = new IWTimestamp(app1.getCreated());
		IWTimestamp created2 = new IWTimestamp(app2.getCreated());

		if (created1.isEarlierThan(created2)) {
			return -1;
		}
		else if (created1.isLaterThan(created2)) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
