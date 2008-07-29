/*
 * $Id: ApplicationSessionBean.java,v 1.1 2008/07/29 10:48:18 anton Exp $ Created on Jun 11, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.business;

import is.idega.idegaweb.egov.fsk.data.Course;
import is.idega.idegaweb.egov.fsk.data.Period;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSessionBindingEvent;

import com.idega.business.IBOSessionBean;
import com.idega.core.file.data.ICFile;

public class ApplicationSessionBean extends IBOSessionBean implements ApplicationSession {

	private Map files;
	private Map periods;

	public void valueBound(HttpSessionBindingEvent arg0) {
	}

	public void valueUnbound(HttpSessionBindingEvent arg0) {
		clear();
	}

	private Map getMap() {
		if (files == null) {
			files = new LinkedHashMap();
		}

		return files;
	}

	public Map getFiles() {
		return getMap();
	}

	public void addFile(ICFile file, String type) {
		getMap().put(file, type);
	}

	public void removeFile(ICFile file) {
		getMap().remove(file);
	}

	public boolean contains(String filename) {
		Iterator iterator = getMap().keySet().iterator();
		while (iterator.hasNext()) {
			ICFile file = (ICFile) iterator.next();
			if (file.getName().equals(filename)) {
				return true;
			}
		}

		return false;
	}

	public void addAllocation(Period period, Course course) {
		if (periods == null) {
			periods = new HashMap();
		}

		ArrayList courses = null;
		if (periods.containsKey(period)) {
			courses = (ArrayList) periods.get(period);
		}
		else {
			courses = new ArrayList();
		}

		if (!courses.contains(course)) {
			courses.add(course);
		}
		
		periods.put(period, courses);
}

	public int getNumberOfAllocations(Period period) {
		if (periods != null) {
			ArrayList courses = (ArrayList) periods.get(period);
			if (courses != null) {
				return courses.size(); 
			}
		}

		return 0;
	}

	public void clear() {
		files = null;
		periods = null;
	}
}