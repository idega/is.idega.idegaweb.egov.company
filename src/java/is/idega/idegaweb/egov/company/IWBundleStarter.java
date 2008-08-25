/*
 * $Id: IWBundleStarter.java,v 1.1 2008/07/29 12:57:50 anton
 * 
 * Copyright (C) 2008 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company;

import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;

public class IWBundleStarter extends is.idega.idegaweb.egov.fsk.IWBundleStarter implements IWBundleStartable {
	
	public void start(IWBundle starterBundle) {
		IWApplicationContext iwac = starterBundle.getApplication().getIWApplicationContext();
		
		insertGroupType(iwac, EgovCompanyConstants.GROUP_TYPE_COMPANY_COURSE);
		insertGroupType(iwac, EgovCompanyConstants.GROUP_TYPE_COMPANY_DIVISION);
		insertGroupType(iwac, EgovCompanyConstants.GROUP_TYPE_COMPANY_DIVISIONS);
		insertGroupType(iwac, EgovCompanyConstants.GROUP_TYPE_COMPANY_SUB_GROUP);
		
		//	TODO: insert company types?
	}

	public void stop(IWBundle starterBundle) {
	}
}