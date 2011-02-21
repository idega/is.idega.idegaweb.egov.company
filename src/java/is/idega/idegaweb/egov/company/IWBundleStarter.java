/*
 * $Id: IWBundleStarter.java,v 1.1 2008/07/29 12:57:50 anton
 * 
 * Copyright (C) 2008 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company;

import is.idega.idegaweb.egov.EGOVConstants;
import is.idega.idegaweb.egov.company.business.CompanyPortalBusiness;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.builder.business.BuilderLogic;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.core.accesscontrol.business.AccessController;
import com.idega.core.accesscontrol.business.StandardRoles;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.servlet.filter.IWBundleResourceFilter;
import com.idega.user.business.GroupBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.GroupType;
import com.idega.util.ListUtil;
import com.idega.util.expression.ELUtil;

public class IWBundleStarter implements IWBundleStartable {
	
	public void start(IWBundle starterBundle) {
		IWApplicationContext iwac = starterBundle.getApplication().getIWApplicationContext();
		
		createCompanyPortalRoles(iwac);
		
		GroupBusiness groupBusiness = null;
		try {
			groupBusiness = (GroupBusiness) IBOLookup.getServiceInstance(iwac, GroupBusiness.class);
		} catch (IBOLookupException e) {
			e.printStackTrace();
		}
		if (groupBusiness == null) {
			return;
		}
		
		for (String type: EgovCompanyConstants.ALL_COMPANY_TYPES) {
			insertGroupType(groupBusiness, type);
		}
		
		Group companyPortal = null;
		try {
			companyPortal = createRootGroup(iwac, groupBusiness);
		} catch(RemoteException e) {
			e.printStackTrace();
		}
		if (companyPortal == null) {
			return;
		}
		
		try {
			createAdminsGroup(iwac, groupBusiness, companyPortal);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		try {
			createSuperAdminsGroup(iwac, groupBusiness, companyPortal);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		setRoleToCommuneAcceptedCitizensGroup(iwac, groupBusiness);
		
		setHomePageToGroups(iwac);

		IWBundleResourceFilter.copyAllFilesFromJarDirectory(starterBundle.getApplication(), starterBundle, "/resources/");
	}
	
	private void setHomePageToGroups(IWApplicationContext iwac) {
		CompanyPortalBusiness companyPortalBusiness = ELUtil.getInstance().getBean(CompanyPortalBusiness.SPRING_BEAN_IDENTIFIER);
		if (companyPortalBusiness == null) {
			return;
		}
		
		companyPortalBusiness.setHomePageToGroups(iwac);
	}
	
	@SuppressWarnings("unchecked")
	private void setRoleToCommuneAcceptedCitizensGroup(IWApplicationContext iwac, GroupBusiness groupBusiness) {
		Collection<Group> groups = null;
		try {
			groups = groupBusiness.getGroupsByGroupName(EGOVConstants.COMMUNE_ACCPETED_CITIZENS);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (ListUtil.isEmpty(groups)) {
			return;
		}
		
		AccessController accessController = iwac.getIWMainApplication().getAccessController();
		for (Group group: groups) {
			accessController.addRoleToGroup(StandardRoles.ROLE_KEY_CUSTOMER, group, iwac);
		}
	}
	
	private void createCompanyPortalRoles(IWApplicationContext iwac) {
		AccessController accessController = iwac.getIWMainApplication().getAccessController();
		
		for (String roleKey: EgovCompanyConstants.ALL_COMPANY_ROLES) {
			accessController.checkIfRoleExistsInDataBaseAndCreateIfMissing(roleKey);
		}
	}

	private Group getGroupByName(IWApplicationContext iwac, Group parentGroup, String name) throws RemoteException {
		CompanyPortalBusiness companyPortalBusiness = ELUtil.getInstance().getBean(CompanyPortalBusiness.SPRING_BEAN_IDENTIFIER);
		return companyPortalBusiness.getGroupByName(parentGroup, name);
	}
	
	private Group createGroup(GroupBusiness groupBusiness, String name, String description, boolean createUnderRootDomain, Group parentGroup) {
		try {
			return groupBusiness.createGroup(name, description, groupBusiness.getGroupTypeHome().getPermissionGroupTypeString(), -1, -1, createUnderRootDomain,
					parentGroup);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void createSuperAdminsGroup(IWApplicationContext iwac, GroupBusiness groupBusiness, Group companyPortal) throws RemoteException {
		Group companySuperAdmins = getGroupByName(iwac, companyPortal, EgovCompanyConstants.COMPANY_SUPER_ADMINS_GROUP_IN_COMPANY_PORTAL);
		
		if (companySuperAdmins == null) {
			companySuperAdmins = createGroup(groupBusiness, EgovCompanyConstants.COMPANY_SUPER_ADMINS_GROUP_IN_COMPANY_PORTAL, "Company super administrators",
					false, companyPortal);
		}
			
		if (companySuperAdmins != null) {
			AccessController accessController = iwac.getIWMainApplication().getAccessController();
			accessController.addRoleToGroup(EgovCompanyConstants.COMPANY_SUPER_ADMIN_ROLE, companySuperAdmins, iwac);
		}
	}
	
	private void createAdminsGroup(IWApplicationContext iwac, GroupBusiness groupBusiness, Group companyPortal) throws RemoteException {
		Group companyAdmins = getGroupByName(iwac, companyPortal, EgovCompanyConstants.COMPANY_ADMINS_GROUP_IN_COMPANY_PORTAL);
		
		if (companyAdmins == null) {
			companyAdmins = createGroup(groupBusiness, EgovCompanyConstants.COMPANY_ADMINS_GROUP_IN_COMPANY_PORTAL, "Company administrators", false,companyPortal);
		}
		
		//	Adding role
		if (companyAdmins != null) {
			AccessController accessController = iwac.getIWMainApplication().getAccessController();
			accessController.addRoleToGroup(EgovCompanyConstants.COMPANY_ADMIN_ROLE, companyAdmins, iwac);
		}
		
		//	Setting home page
		CompanyPortalBusiness companyPortalBusiness = ELUtil.getInstance().getBean(CompanyPortalBusiness.SPRING_BEAN_IDENTIFIER);
		String homePageKey = iwac.getApplicationSettings().getProperty(EgovCompanyConstants.COMPANY_PORTAL_HOME_PAGE_APPLICATION_PROPERTY);
		companyPortalBusiness.setHomePage(iwac, companyAdmins, homePageKey);
	}
	
	private Group createRootGroup(IWApplicationContext iwac, GroupBusiness groupBusiness) throws RemoteException {
		//	Root group for Company Portal
		CompanyPortalBusiness companyPortalBusiness = ELUtil.getInstance().getBean(CompanyPortalBusiness.SPRING_BEAN_IDENTIFIER);
		Group companyPortal = companyPortalBusiness.getCompanyPortalRootGroup(iwac);
		if (companyPortal != null) {
			return companyPortal;
		}
		
		companyPortal = createGroup(groupBusiness, EgovCompanyConstants.COMPANY_PORTAL_ROOT_GROUP, "Root group for Company Portal", true, null);
		if (companyPortal == null) {
			return null;
		}
		
		BuilderLogic.getInstance().reloadGroupsInCachedDomain(iwac, null);
		
		return companyPortal;
	}
	
	private void insertGroupType(GroupBusiness groupBusiness, String groupType) {
		try {
			GroupType type;
			try {
				type = groupBusiness.getGroupTypeFromString(groupType);
			}
			catch (FinderException e) {
				type = groupBusiness.getGroupTypeHome().create();
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