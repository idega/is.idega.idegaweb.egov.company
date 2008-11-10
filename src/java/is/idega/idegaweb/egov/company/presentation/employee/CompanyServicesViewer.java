package is.idega.idegaweb.egov.company.presentation.employee;

import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;

import java.util.Collection;

import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading3;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.Age;
import com.idega.util.ListUtil;

public class CompanyServicesViewer extends CompanyBlock {

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	protected void present(IWContext iwc) throws Exception {
		super.present(iwc);

		Layer container = new Layer();
		add(container);

		CompanyApplicationBusiness companyBusiness = getCompanyBusiness();
		
		if (!companyBusiness.isCompanyEmployee(iwc)) {
			container.add(new Heading3(getResourceBundle(iwc).getLocalizedString("insufficient_rights", "Insufficient rights")));
			return;
		}
		
		if (group == null) {
			group = getGroupThatIsCompanyForCurrentUser(iwc);
		}
		if (group == null) {
			container.add(new Heading3(getResourceBundle(iwc).getLocalizedString("select_group_that_represents_company",
					"Select a group that represents company")));
			return;
		}

		User user = iwc.getCurrentUser();
		Collection<Application> userApplicationList = (companyBusiness.isInstitutionAdministration(iwc) || companyBusiness.isCompanyAdministrator(iwc)) ?
																companyBusiness.getUserApplications(iwc, user) : companyBusiness.getAssignedServices(iwc, user);
		if (ListUtil.isEmpty(userApplicationList)) {
			String noServicesMessage = new StringBuilder(getResourceBundle(iwc).getLocalizedString("there_are_no_services_provided_by_company",
					"There are no services provided by company")).append(": ").append(group.getNodeName(iwc.getCurrentLocale())).toString();
			container.add(getMessage(noServicesMessage, "companyPortalNoServicesAvailableMessageStyle"));
			return;
		}

		Age[] ages = null;
		boolean checkAges = false;
		ages = getApplicationBusiness(iwc).getAgesForUserAndChildren(user);
		checkAges = (ages != null);

		container.add(getApplicationList(iwc, checkAges, userApplicationList, ages));
	}
}
