package is.idega.idegaweb.egov.company.presentation.employee;

import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;

import java.util.ArrayList;
import java.util.Collection;

import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Heading3;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.Age;
import com.idega.util.ListUtil;

public class CompanyServicesViewer extends CompanyBlock {

	protected Group group;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void present(IWContext iwc) throws Exception {
		super.present(iwc);

		Layer container = new Layer();
		add(container);
		container.add(new Heading1(getResourceBundle(iwc).getLocalizedString("services", "Services")));

		// Temporal remove this after testing
		/*Collection<Group> selectedGroups = getUserBusiness(iwc).getGroupBusiness().getGroupsByGroupName("company group");
		setGroup(selectedGroups.iterator().next());*/

		if (!getCompanyBusiness().isCompanyEmployee(iwc)) {
			container.add(new Heading3(getResourceBundle(iwc).getLocalizedString("insufficient_rights", "Insufficient rights")));
			return;
		}
		if (group == null) {
			container.add(new Heading3(getResourceBundle(iwc).getLocalizedString("select_group", "Select a group")));
			return;
		}

		User user = iwc.getCurrentUser();
		Collection<Application> userApplicationList = getCompanyBusiness().getUserApplications(iwc, user);
		Collection<Application> applicationForSelectedGroup = new ArrayList<Application>();
		for (Application application : userApplicationList) {
			if (application.getGroups().contains(getGroup())) {
				applicationForSelectedGroup.add(application);
			}
		}

		if (ListUtil.isEmpty(userApplicationList)) {
			container.add(new Heading3(getResourceBundle(iwc).getLocalizedString("no_services_faund", "No services faund")));
			return;
		}

		Age[] ages = null;
		boolean checkAges = false;
		ages = getApplicationBusiness(iwc).getAgesForUserAndChildren(user);
		checkAges = (ages != null);

		container.add(getApplicationList(iwc, checkAges, applicationForSelectedGroup, ages));
	}
}
