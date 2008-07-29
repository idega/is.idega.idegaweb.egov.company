/*
 * $Id: AttachmentComparator.java,v 1.1 2008/07/29 10:48:19 anton Exp $ Created on Jun 11, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.business;

import is.idega.idegaweb.egov.fsk.FSKConstants;
import is.idega.idegaweb.egov.fsk.data.ApplicationFiles;
import is.idega.idegaweb.egov.fsk.data.Season;

import java.sql.Timestamp;
import java.util.Comparator;

import com.idega.core.file.data.ICFile;
import com.idega.util.IWTimestamp;

public class AttachmentComparator implements Comparator {

	public AttachmentComparator() {
	}

	public int compare(Object arg0, Object arg1) {
		ApplicationFiles file1 = (ApplicationFiles) arg0;
		ApplicationFiles file2 = (ApplicationFiles) arg1;

		Season season1 = file1.getSeason();
		Season season2 = file2.getSeason();

		int compare = seasonCompare(season1, season2);
		if (compare == 0) {
			compare = typeCompare(file1.getType(), file2.getType());
		}
		if (compare == 0) {
			ICFile icFile1 = file1.getFile();
			ICFile icFile2 = file2.getFile();

			compare = timestampCompare(icFile1.getCreationDate(), icFile2.getCreationDate());
		}

		return compare;
	}

	private int seasonCompare(Season season1, Season season2) {
		if (season1 == null && season2 != null) {
			return 1;
		}
		else if (season1 != null && season2 == null) {
			return -1;
		}
		else if (season1 == null && season2 == null) {
			return 0;
		}

		if (season1.equals(season2)) {
			return 0;
		}

		IWTimestamp start1 = new IWTimestamp(season1.getStartDate());
		IWTimestamp start2 = new IWTimestamp(season2.getStartDate());
		if (start1.isEarlierThan(start2)) {
			return -1;
		}
		else if (start2.isEarlierThan(start1)) {
			return 1;
		}
		return 0;
	}

	private int typeCompare(String type1, String type2) {
		if (type1.equals(type2)) {
			return 0;
		}

		if (type1.equals(FSKConstants.FILE_TYPE_TARIFF)) {
			return -1;
		}
		else if (type2.equals(FSKConstants.FILE_TYPE_TARIFF)) {
			return 1;
		}
		else if (type1.equals(FSKConstants.FILE_TYPE_SCHEDULE)) {
			return -1;
		}
		else if (type2.equals(FSKConstants.FILE_TYPE_SCHEDULE)) {
			return 1;
		}
		return 0;
	}

	private int timestampCompare(Timestamp time1, Timestamp time2) {
		if (time1.equals(time2)) {
			return 0;
		}

		IWTimestamp stamp1 = new IWTimestamp(time1);
		IWTimestamp stamp2 = new IWTimestamp(time2);

		if (stamp1.isEarlierThan(stamp2)) {
			return -1;
		}
		else if (stamp2.isEarlierThan(stamp1)) {
			return 1;
		}
		return 0;
	}
}