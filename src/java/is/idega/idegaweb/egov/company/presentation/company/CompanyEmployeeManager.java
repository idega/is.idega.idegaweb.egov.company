package is.idega.idegaweb.egov.company.presentation.company;

import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.business.CompanyUserApplicationEngineBean;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.idega.core.accesscontrol.business.StandardRoles;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.user.app.SimpleUserApp;
import com.idega.user.data.Group;
import com.idega.util.ListUtil;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 27, 2008 by Author: Anton 
 *
 */

public class CompanyEmployeeManager extends CompanyBlock {

	private String roleTypes = null;
	
	@Override
	protected void present(IWContext iwc) throws Exception {
		super.present(iwc);
		
		if (!getCompanyBusiness().isCompanyAdministrator(iwc)) {
			showInsufficientRightsMessage(iwrb.getLocalizedString("insufficient_rights_to_manage_accounts",
					"You have insufficient rights to manage accounts!"));
			return;
		}
		
		if (group == null) {
			group = getGroupThatIsCompanyForCurrentUser(iwc);
		}
		if (getGroup() == null) {
			showMessage(iwrb.getLocalizedString("no_user_group_selected", "There's no user group selected"));
			return;
		}
		
		showUserForm(iwc);
	}
	
	private void showUserForm(IWContext iwc) {
		Layer container = new Layer();
		add(container);
		//container.add(getLink(iwrb.getLocalizedString("show_employee_list", "Show employees list"), PARAMETER_ACTION, String.valueOf(ACTION_VIEW)));
		
		Layer employeeUserForm = getEmployeeAccountManager(iwc, ListUtil.convertListOfStringsToCommaseparatedString(EgovCompanyConstants.ALL_COMPANY_TYPES));
		add(employeeUserForm);
	}
	
	private Layer getEmployeeAccountManager(IWContext iwc, String groupTypesForChildrenGroups) {
		Layer container = new Layer();
		container.setStyleClass("usersContainerStyle");
		
		SimpleUserApp sua = new SimpleUserApp();
		container.add(sua);
		sua.setParentGroup(group);
		sua.setGroupTypes(StandardRoles.ROLE_KEY_COMPANY);
		sua.setGroupTypesForChildGroups(groupTypesForChildrenGroups);
		sua.setRoleTypesForChildGroups(getRoleTypesForChildGroups());
		sua.setAllowEnableDisableAccount(true);
		sua.setAllFieldsEditable(true);
		sua.setSendMailToUser(true);
		sua.setChangePasswordNextTime(true);
		sua.setAddChildGroupCreateButton(false);
		sua.setAddChildGroupEditButton(false);
		sua.setAddGroupCreateButton(false);
		sua.setAddGroupEditButton(false);
		sua.setEngineClassName(CompanyUserApplicationEngineBean.class.getName());
		
		return container;
	}
	
	private String getRoleTypesForChildGroups() {
		if (roleTypes == null) {
			List<String> roles = new ArrayList<String>();
			roles.add(StandardRoles.ROLE_KEY_AUTHOR);
			roles.add(StandardRoles.ROLE_KEY_EDITOR);
			roles.addAll(Arrays.asList(new String[] {EgovCompanyConstants.COMPANY_ADMIN_ROLE, EgovCompanyConstants.COMPANY_EMPLOYEE_ROLE,
													StandardRoles.ROLE_KEY_COMPANY}));
			roleTypes = ListUtil.convertListOfStringsToCommaseparatedString(roles);
		}
		return roleTypes;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
