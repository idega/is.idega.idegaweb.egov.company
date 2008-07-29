/*
 * $Id: IWBundleStarter.java,v 1.1 2008/07/29 12:57:50 anton Exp $ Created on Jun 12, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company;

import is.idega.idegaweb.egov.company.business.FSKBusiness;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.block.process.business.CaseCodeManager;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.business.CompanyBusiness;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.idegaweb.include.ExternalLink;
import com.idega.idegaweb.include.GlobalIncludeManager;
import com.idega.user.business.GroupBusiness;
import com.idega.user.data.GroupType;

public class IWBundleStarter implements IWBundleStartable {

	public void start(IWBundle starterBundle) {
		GlobalIncludeManager.getInstance().addBundleStyleSheet(FSKConstants.IW_BUNDLE_IDENTIFIER, "/style/fsk.css", ExternalLink.MEDIA_SCREEN);
		CaseCodeManager.getInstance().addCaseBusinessForCode(FSKConstants.CASE_CODE_KEY, FSKBusiness.class);
		updateData(starterBundle.getApplication().getIWApplicationContext());
		
		try {
			getBusiness(starterBundle.getApplication().getIWApplicationContext()).fixPermissions();
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public void stop(IWBundle starterBundle) {
	}

	private void updateData(IWApplicationContext iwac) {
		insertGroupType(iwac, FSKConstants.GROUP_TYPE_DIVISIONS);
		insertGroupType(iwac, FSKConstants.GROUP_TYPE_DIVISION);
		insertGroupType(iwac, FSKConstants.GROUP_TYPE_GROUP);
		insertGroupType(iwac, FSKConstants.GROUP_TYPE_SUB_GROUP);
		insertGroupType(iwac, FSKConstants.GROUP_TYPE_COURSE);

		String[] types = { FSKConstants.COMPANY_TYPE_SPORTS_CLUB, FSKConstants.COMPANY_TYPE_ARTS_AND_CULTURE, FSKConstants.COMPANY_TYPE_YOUTH_CLUB, FSKConstants.COMPANY_TYPE_OTHER };
		String[] names = { "Sports club", "Arts and cultural activity", "Youth club", "Other" };
		for (int i = 0; i < types.length; i++) {
			String type = types[i];
			String name = names[i];

			insertCompanyType(iwac, type, name, null, i + 1);
		}
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

	private void insertCompanyType(IWApplicationContext iwac, String type, String name, String description, int order) {
		try {
			CompanyBusiness business = (CompanyBusiness) IBOLookup.getServiceInstance(iwac, CompanyBusiness.class);
			try {
				business.getCompanyType(type);
			}
			catch (FinderException e) {
				business.storeCompanyType(type, name, description, order);
			}
		}
		catch (IBOLookupException e) {
			e.printStackTrace();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
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