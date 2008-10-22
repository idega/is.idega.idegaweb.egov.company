package is.idega.idegaweb.egov.company.presentation.institution;

import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.idega.company.CompanyConstants;
import com.idega.core.accesscontrol.business.StandardRoles;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading3;
import com.idega.presentation.text.Link;
import com.idega.user.app.SimpleUserApp;
import com.idega.user.data.Group;
import com.idega.util.ListUtil;

public class CompanyAccountManager extends CompanyBlock {
	
	private static final String PARAMETER_MANAGE_COMPANY_ACCOUNT = "prm_manage_company_account";
	private static final String PARAMETER_MANAGE_COMPANY_EMPLOYEES_ACCOUNT = "prm_manage_company_emplpyees_account";
	
	private String roleTypes = null;
	
	private Group group = null;
	
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
		if (group == null) {
			showMessage(iwrb.getLocalizedString("select_group_first_please", "Select group (that represents company) firstly, please!"));
			return;
		}
		
		if (iwc.isParameterSet(PARAMETER_MANAGE_COMPANY_EMPLOYEES_ACCOUNT)) {
			showCompanyEmployeesAccountsManager(iwc);
			return;
		}
		
		//	By default showing company account manager
		showCompanyAccountManager(iwc);
		return;
	}
	
	private void showCompanyEmployeesAccountsManager(IWContext iwc) {
		Layer container = new Layer();
		add(container);
		
		container.add(getMessage(iwrb.getLocalizedString("company_employees_accounts_manager", "Company employees accounts manager")));
		
		container.add(new Heading3(getResourceBundle(iwc).getLocalizedString("company", "Company") + ": " + group.getNodeName(iwc.getCurrentLocale())));
		
		container.add(getSwitcherLink(iwrb.getLocalizedString("manage_company_account", "Manage company account"), PARAMETER_MANAGE_COMPANY_ACCOUNT));
		
		Layer suaInContainer = getSimpleUserApplication(iwc, ListUtil.convertListOfStringsToCommaseparatedString(Arrays.asList(new String [] {
				CompanyConstants.GROUP_TYPE_COMPANY, EgovCompanyConstants.GROUP_TYPE_COMPANY_DIVISIONS, EgovCompanyConstants.GROUP_TYPE_COMPANY_COURSE,
				EgovCompanyConstants.GROUP_TYPE_COMPANY_DIVISION, EgovCompanyConstants.GROUP_TYPE_COMPANY_SUB_GROUP})), false);
		container.add(suaInContainer);
		
		Layer accountsManagerInContainer = getAccountsManager(iwc);
		container.add(accountsManagerInContainer);
	}
	
	private void showCompanyAccountManager(IWContext iwc) {
		Layer container = new Layer();
		add(container);
		
		container.add(getMessage(iwrb.getLocalizedString("company_account_manager", "Company account manager")));
		
		container.add(new Heading3(getResourceBundle(iwc).getLocalizedString("company", "Company") + ": " + group.getNodeName(iwc.getCurrentLocale())));
		
		container.add(getSwitcherLink(iwrb.getLocalizedString("manage_accounts_of_company_employees", "Manage accounts of company employees"),
				PARAMETER_MANAGE_COMPANY_EMPLOYEES_ACCOUNT));
		
		Layer suaInContainer = getSimpleUserApplication(iwc, ListUtil.convertListOfStringsToCommaseparatedString(Arrays.asList(new String [] {
				CompanyConstants.GROUP_TYPE_COMPANY, EgovCompanyConstants.GROUP_TYPE_COMPANY_DIVISIONS, EgovCompanyConstants.GROUP_TYPE_COMPANY_COURSE,
				EgovCompanyConstants.GROUP_TYPE_COMPANY_DIVISION, EgovCompanyConstants.GROUP_TYPE_COMPANY_SUB_GROUP})), true);
		container.add(suaInContainer);
	}
	
	private Layer getSimpleUserApplication(IWContext iwc, String groupTypesForChildrenGroups, boolean juridicalPerson) {
		Layer container = new Layer();
		container.setStyleClass("usersContainerStyle");
		
		SimpleUserApp sua = new SimpleUserApp();
		container.add(sua);
		sua.setParentGroup(group);
		sua.setUseChildrenOfTopNodesAsParentGroups(!juridicalPerson);
		sua.setGroupTypes(juridicalPerson ? CompanyConstants.GROUP_TYPE_COMPANY : groupTypesForChildrenGroups);
		sua.setGroupTypesForChildGroups(groupTypesForChildrenGroups);
		sua.setRoleTypesForChildGroups(getRoleTypesForChildGroups());
		sua.setJuridicalPerson(juridicalPerson);
		sua.setAllowEnableDisableAccount(true);
		sua.setAllFieldsEditable(true);
		sua.setSendMailToUser(true);
		sua.setChangePasswordNextTime(true);
		sua.setAddGroupCreateButton(false);
		sua.setAddGroupEditButton(juridicalPerson);
		sua.setAddChildGroupCreateButton(true);
		sua.setAddChildGroupEditButton(true);
		
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
	
	private Layer getSwitcherLink(String message, String parameterToMaintain) {
		Layer switcher = new Layer();
		Link switchLink = new Link(message);
		switcher.add(switchLink);
		switchLink.addParameter(parameterToMaintain, Boolean.TRUE.toString());
		return switcher;
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
