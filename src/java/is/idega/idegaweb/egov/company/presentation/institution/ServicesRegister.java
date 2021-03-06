package is.idega.idegaweb.egov.company.presentation.institution;

import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.application.presentation.ApplicationCreator;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;
import is.idega.idegaweb.egov.company.business.CompanyPortalBusiness;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.core.localisation.data.ICLocale;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.idegaweb.IWUserContext;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading1;
import com.idega.user.business.GroupBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.user.presentation.group.GroupsFilter;
import com.idega.util.ArrayUtil;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

/**
 * @author <a href="mailto:valdas@idega.com">Valdas Žemaitis</a>
 * @version Revision: 1.00
 *
 * Last modified: 2008.07.30 10:44:06 by: valdas
 */
public class ServicesRegister extends ApplicationCreator {
	
	private static final String SELECTED_GROUPS_PARAMETER_NAME = "prm_selected_groups_for_application";
	
	public ServicesRegister() {
		setRequiresLogin(true);
		setVisibleApplication(true);
		setHiddenFromGuests(true);
		setCheckIfCanViewApplication(true);
	}

	@Override
	public void main(IWContext iwc) throws Exception {
		CompanyApplicationBusiness companyApplication = (CompanyApplicationBusiness) IBOLookup.getServiceInstance(iwc, CompanyApplicationBusiness.class);
		
		boolean hasRights = companyApplication.isInstitutionAdministration(iwc) || companyApplication.isCompanyAdministrator(iwc);
		if (!hasRights) {
			Layer container = new Layer();
			container.setStyleClass("insufficientRigthsStyle");
			
			Heading1 errorMessage = new Heading1(getResourceBundle(iwc).getLocalizedString("insufficient_rights_to_register_new_service",
				"You have insufficient rights to register new service!"));
			container.add(errorMessage);
			add(container);
			
			User currentUser = iwc.isLoggedOn() ? iwc.getCurrentUser() : null;
			log(Level.WARNING, currentUser + ": user doesn't have rights to register new service!");
			return;
		}
		
		GroupsFilter filter = new GroupsFilter();
		List<String> groupsForCurrentApp = getGroupsFroCurrentApplication(iwc);
		if (!ListUtil.isEmpty(groupsForCurrentApp)) {
			filter.setSelectedGroups(groupsForCurrentApp);
		}
		filter.setSelectedGroupParameterName(SELECTED_GROUPS_PARAMETER_NAME);
		List<UIComponent> components = new ArrayList<UIComponent>(1);
		components.add(filter);
		Map<String, List<UIComponent>> formSections = new HashMap<String, List<UIComponent>>();
		formSections.put("availableGroupsForApplication", components);
		
		Layer component = getFormSection(getResourceBundle(iwc).getLocalizedString("groups", "Groups"), formSections);
		List<UIComponent> additionalComponents = new ArrayList<UIComponent>(1);
		additionalComponents.add(component);
		setAdditionalComponents(additionalComponents);
		
		super.main(iwc);
	}
	
	@Override
	public void present(IWContext iwc) throws Exception {
		super.present(iwc);
	}
	
	@Override
	public String getBundleIdentifier() {
		return EgovCompanyConstants.IW_BUNDLE_IDENTIFIER;
	}
	
	private List<String> getGroupsFroCurrentApplication(IWContext iwc) {
		String appId = iwc.getParameter(APPLICATION_ID_PARAMETER);
		if (StringUtil.isEmpty(appId)) {
			return null;
		}
		
		Object primaryKey = appId;
		Application app = null;
		try {
			app = getApplicationBusiness(iwc).getApplication(primaryKey);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		}
		if (app == null) {
			return null;
		}
		
		Collection<Group> groups = app.getGroups();
		if (ListUtil.isEmpty(groups)) {
			return null;
		}
		
		List<String> ids = new ArrayList<String>(groups.size());
		for (Group group: groups) {
			ids.add(group.getId());
		}
		
		return ids;
	}
	
	@Override
	protected boolean validate(IWContext iwc) {
		String[] selectedGroups = iwc.getParameterValues(SELECTED_GROUPS_PARAMETER_NAME);
		if (ArrayUtil.isEmpty(selectedGroups)) {
			iwc.addMessage(SELECTED_GROUPS_PARAMETER_NAME, new FacesMessage(getResourceBundle(iwc).getLocalizedString("group_must_be_selected",
																																"Group must be selected!")));
		}
		
		return super.validate(iwc);
	}
	
	@Override
	protected String saveApplication(IWContext iwc, List<ICLocale> locales) throws RemoteException, CreateException, FinderException {
		String appId = null;
		try {
			appId = super.saveApplication(iwc, locales);
		} catch (IDOAddRelationshipException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error saving application!", e);
		}
		if (StringUtil.isEmpty(appId)) {
			return null;
		}
		
		String[] selectedGroups = iwc.getParameterValues(SELECTED_GROUPS_PARAMETER_NAME);
		if (ArrayUtil.isEmpty(selectedGroups)) {
			log(Level.SEVERE, "No groups selected for application: " + appId);
			return null;
		}
		
		Application app = null;
		try {
			Object applicationId = appId;
			app = getApplicationBusiness(iwc).getApplication(applicationId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if (app == null) {
			throw new CreateException("Can't set groups for Application: " + appId);
		}
		
		List<Group> groupsForApp = getSelectedGroups(iwc, app, selectedGroups);
		if (ListUtil.isEmpty(groupsForApp)) {
			return appId;
		}
		for (Group group: groupsForApp) {
			try {
				app.addGroup(group);
			} catch(IDOAddRelationshipException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Error adding group " + group + " to application: " + app, e);
			}
		}
		app.store();
		
		CompanyPortalBusiness companyPortalBusiness = ELUtil.getInstance().getBean(CompanyPortalBusiness.SPRING_BEAN_IDENTIFIER);
		Group companyPortalRootGroup = companyPortalBusiness.getCompanyPortalRootGroup(iwc);
		if (companyPortalRootGroup != null && groupsForApp.contains(companyPortalRootGroup)) {
			CompanyApplicationBusiness companyApplicationBusinees = (CompanyApplicationBusiness) IBOLookup.getServiceInstance(iwc,
																																CompanyApplicationBusiness.class);
			companyApplicationBusinees.addCommonCompanyPortalServices(iwc);
		}
		
		return appId;
	}
	
	private List<Group> getSelectedGroups(IWContext iwc, Application app, String[] groupsIds) {
		GroupBusiness groupBusiness = null;
		try {
			groupBusiness = (GroupBusiness) IBOLookup.getServiceInstance(iwc, GroupBusiness.class);
		} catch (IBOLookupException e) {
			e.printStackTrace();
		}
		
		List<Group> selectedGroups = new ArrayList<Group>();
		
		Group group = null;
		for (int i = 0; i < groupsIds.length; i++) {
			group = null;
			try {
				group = groupBusiness.getGroupByGroupID(Integer.valueOf(groupsIds[i]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (FinderException e) {
				e.printStackTrace();
			}
			if (group != null) {
				selectedGroups.add(group);
			}
		}
		
		removeOldGroups(app, selectedGroups);
		
		Collection<Group> currentGroups = app.getGroups();
		if (ListUtil.isEmpty(currentGroups)) {
			return selectedGroups;
		}
		
		List<Group> newGroups = new ArrayList<Group>();
		for (Group selectedGroup: selectedGroups) {
			if (!currentGroups.contains(selectedGroup)) {
				newGroups.add(selectedGroup);
			}
		}
		
		return newGroups;
	}
	
	private void removeOldGroups(Application app, List<Group> newGroups) {
		List<Group> groupsToRemove = null;
		Collection<Group> currentGroups = app.getGroups();
		if (!ListUtil.isEmpty(currentGroups)) {
			if (ListUtil.isEmpty(newGroups)) {
				groupsToRemove = new ArrayList<Group>(currentGroups);
			}
			else {
				groupsToRemove = new ArrayList<Group>();
				for (Group currentGroup: currentGroups) {
					if (!newGroups.contains(currentGroup)) {
						groupsToRemove.add(currentGroup);
					}
				}
				
			}
		}
		
		if (ListUtil.isEmpty(groupsToRemove)) {
			return;
		}
		
		for (Group group: groupsToRemove) {
			try {
				app.removeGroup(group);
			} catch (IDORemoveRelationshipException e) {
				e.printStackTrace();
			}
		}
		app.store();
	}
	
	@Override
	public String getBuilderName(IWUserContext iwuc) {
		return ServicesRegister.class.getSimpleName();
	}
}
