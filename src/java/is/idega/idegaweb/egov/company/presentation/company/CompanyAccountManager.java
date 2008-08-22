package is.idega.idegaweb.egov.company.presentation.company;

import is.idega.idegaweb.egov.company.presentation.CompanyBlock;

import com.idega.company.CompanyConstants;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Link;
import com.idega.user.app.SimpleUserApp;

public class CompanyAccountManager extends CompanyBlock {
	
	private static final String PARAMETER_MANAGE_COMPANY_ACCOUNT = "prm_manage_company_account";
	private static final String PARAMETER_MANAGE_COMPANY_EMPLOYEES_ACCOUNT = "prm_manage_company_emplpyees_account";
	
	@Override
	protected void present(IWContext iwc) throws Exception {
		super.present(iwc);
		
		if (!getCompanyBusiness().isCompanyAdministrator(iwc)) {
			showInsufficientRightsMessage(iwrb.getLocalizedString("insufficient_rights_to_manage_accounts",
					"You have insufficient rights to manage accounts!"));
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
		container.add(getSwitcherLink(iwrb.getLocalizedString("manage_company_account", "Manage company account"), PARAMETER_MANAGE_COMPANY_ACCOUNT));
		
		//	TODO: finish
	}
	
	private void showCompanyAccountManager(IWContext iwc) {
		Layer container = new Layer();
		add(container);
		container.add(getSwitcherLink(iwrb.getLocalizedString("manage_accounts_of_company_employees", "Manage accounts of company employees"),
				PARAMETER_MANAGE_COMPANY_EMPLOYEES_ACCOUNT));
		
		Layer suaInContainer = getCompanyAccountManager(iwc);
		container.add(suaInContainer);
		
		Layer accountsManagerInContainer = getAccountsManager(iwc);
		container.add(accountsManagerInContainer);
	}
	
	private Layer getCompanyAccountManager(IWContext iwc) {
		Layer container = new Layer();
		container.setStyleClass("usersContainerStyle");
		
		SimpleUserApp sua = new SimpleUserApp();
		container.add(sua);
		sua.setGroupTypes(CompanyConstants.GROUP_TYPE_COMPANY);
		sua.setJuridicalPerson(true);
		sua.setAddGroupCreateButton(true);
		
		return container;
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

}
