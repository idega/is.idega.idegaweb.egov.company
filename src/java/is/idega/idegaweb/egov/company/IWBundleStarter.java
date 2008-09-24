/*
 * $Id: IWBundleStarter.java,v 1.1 2008/07/29 12:57:50 anton
 * 
 * Copyright (C) 2008 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.user.business.GroupBusiness;
import com.idega.user.data.GroupType;

public class IWBundleStarter implements IWBundleStartable {
	
	public void start(IWBundle starterBundle) {
		IWApplicationContext iwac = starterBundle.getApplication().getIWApplicationContext();
		
		insertGroupType(iwac, EgovCompanyConstants.GROUP_TYPE_COMPANY_COURSE);
		insertGroupType(iwac, EgovCompanyConstants.GROUP_TYPE_COMPANY_DIVISION);
		insertGroupType(iwac, EgovCompanyConstants.GROUP_TYPE_COMPANY_DIVISIONS);
		insertGroupType(iwac, EgovCompanyConstants.GROUP_TYPE_COMPANY_SUB_GROUP);
		
		//	TODO: insert company types?
	}
	
	private void insertGroupType(IWApplicationContext iwac, String groupType) {
		try {
			GroupBusiness business = (GroupBusiness) IBOLookup.getServiceInstance(iwac, GroupBusiness.class);
			GroupType type;
			try {
				type = business.getGroupTypeFromString(groupType);
			}
			catch (FinderException e) {
				type = business.getGroupTypeHome().create();
				type.setType(groupType);
				type.setVisibility(true);
				type.store();
			}
		}
		catch (IBOLookupException e) {
			e.printStackTrace();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		catch (CreateException e) {
			e.printStackTrace();
		}
	}

	public void stop(IWBundle starterBundle) {
	}
}