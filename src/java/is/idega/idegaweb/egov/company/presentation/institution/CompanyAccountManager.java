package is.idega.idegaweb.egov.company.presentation.institution;

import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;

import java.util.Arrays;
import java.util.List;

import com.idega.company.CompanyConstants;
import com.idega.core.accesscontrol.business.StandardRoles;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.user.app.SimpleUserApp;
import com.idega.user.data.Group;
import com.idega.util.ListUtil;

public class CompanyAccountManager extends CompanyBlock {
	
	private String roleTypes = null;
	
	private Group group = null;
	
	@Override
	protected void present(IWContext iwc) throws Exception {
		super.present(iwc);
		
		if (!getCompanyBusiness().isInstitutionAdministration(iwc)) {
			showInsufficientRightsMessage(iwrb.getLocalizedString("insufficient_rights_to_manage_accounts",
					"You have insufficient rights to manage accounts!"));
			return;
		}
		
		if (group == null) {
			group = getCompanyPortalBusiness().getCompanyPortalRootGroup(iwc);
		}
		if (group == null) {
			showMessage(iwrb.getLocalizedString("select_group_first_please", "Select group (that represents company) firstly, please!"));
			return;
		}
		
		showCompanyEmployeesAccountsManager(iwc);
	}
	
	private void showCompanyEmployeesAccountsManager(IWContext iwc) {
		Layer container = new Layer();
		add(container);
		
		container.add(getMessage(iwrb.getLocalizedString("company_employees_accounts_manager", "Company employees accounts manager")));
		
		Layer suaInContainer = getSimpleUserApplication(iwc, ListUtil.convertListOfStringsToCommaseparatedString(EgovCompanyConstants.ALL_COMPANY_TYPES), false);
		container.add(suaInContainer);
		
		Layer accountsManagerInContainer = getAccountsManager(iwc);
		container.add(accountsManagerInContainer);
	}
	
	private Layer getSimpleUserApplication(IWContext iwc, String groupTypesForChildrenGroups, boolean juridicalPerson) {
		Layer container = new Layer();
		container.setStyleClass("usersContainerStyle");
		
		SimpleUserApp sua = new SimpleUserApp();
		container.add(sua);
		sua.setParentGroup(group);
		sua.setUseChildrenOfTopNodesAsParentGroups(!juridicalPerson);
		sua.setGroupTypes(juridicalPerson ? CompanyConstants.GROUP_TYPE_COMPANY : groupTypesForChildrenGroups);
		sua.setGroupTypesForChildGroups(juridicalPerson ?
				ListUtil.convertListOfStringsToCommaseparatedString(Arrays.asList(CompanyConstants.GROUP_TYPE_COMPANY,
																					EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS)) :
				groupTypesForChildrenGroups);
		sua.setRoleTypesForChildGroups(getRoleTypesForChildGroups());
		sua.setJuridicalPerson(juridicalPerson);
		sua.setAllowEnableDisableAccount(true);
		sua.setAllFieldsEditable(true);
		sua.setSendMailToUser(true);
		sua.setChangePasswordNextTime(true);
		sua.setAddGroupCreateButton(false);
		sua.setAddGroupEditButton(juridicalPerson);
		sua.setAddChildGroupCreateButton(false);
		sua.setAddChildGroupEditButton(false);
		
		return container;
	}
	
	private String getRoleTypesForChildGroups() {
		if (roleTypes == null) {
			List<String> roles = Arrays.asList(EgovCompanyConstants.COMPANY_ADMIN_ROLE,EgovCompanyConstants.COMPANY_EMPLOYEE_ROLE,StandardRoles.ROLE_KEY_COMPANY);
			roleTypes = ListUtil.convertListOfStringsToCommaseparatedString(roles);
		}
		return roleTypes;
	}
	
	private Layer getAccountsManager(IWContext iwc) {
		Layer container = new Layer();
		container.setStyleClass("accountsManagerContainerStyle");
		return container;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
