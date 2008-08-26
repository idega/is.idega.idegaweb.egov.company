package is.idega.idegaweb.egov.company.presentation.employee;

import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Heading3;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.Age;
import com.idega.util.ListUtil;

public class CompanyServicesViewer extends CompanyBlock {

	@SuppressWarnings("unchecked")
	@Override
	protected void present(IWContext iwc) throws Exception {
		super.present(iwc);

		Layer container = new Layer();
		add(container);
		container.add(new Heading1(getResourceBundle(iwc).getLocalizedString("services", "Services")));

		if (!getCompanyBusiness().isCompanyEmployee(iwc)) {
			container.add(new Heading3(getResourceBundle(iwc).getLocalizedString("insufficient_rights", "Insufficient rights")));
			return;
		}

		User user = iwc.getCurrentUser();
		Collection<Application> allApplications = getCompanyBusiness().getApplicationHome().findAll();

		List<Application> userApplicationList = new ArrayList<Application>();
		for (Application app : allApplications) {
			boolean appAdded = false;
			for (Group group : app.getGroups()) {
				try {
					if (!appAdded && (getUserBusiness(iwc).isMemberOfGroup(Integer.valueOf(group.getId()), user) || iwc.isSuperAdmin())) {
						userApplicationList.add(app);
						appAdded = true;
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
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

		container.add(getApplicationList(iwc, checkAges, userApplicationList, ages));
	}
}
