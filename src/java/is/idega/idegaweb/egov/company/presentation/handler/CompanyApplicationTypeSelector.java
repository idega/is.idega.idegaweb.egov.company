package is.idega.idegaweb.egov.company.presentation.handler;

import is.idega.idegaweb.egov.company.EgovCompanyConstants;

import java.util.List;

import com.idega.core.builder.presentation.ICPropertyHandler;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.PresentationObject;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.SelectOption;
import com.idega.util.StringUtil;

public class CompanyApplicationTypeSelector implements ICPropertyHandler {

	public List<?> getDefaultHandlerTypes() {
		return null;
	}

	public PresentationObject getHandlerObject(String name, String stringValue, IWContext iwc, boolean oldGenerationHandler, String instanceId, String method) {
		IWResourceBundle iwrb = iwc.getIWMainApplication().getBundle(EgovCompanyConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(iwc);
		
		DropdownMenu types = new DropdownMenu(name);
		types.addOption(new SelectOption(iwrb.getLocalizedString("unhandled_applications", "Unhandled applications"),
				EgovCompanyConstants.APPLICATION_TYPE_UNHANDLED));
		types.addOption(new SelectOption(iwrb.getLocalizedString("approved_applications", "Approved applications"),
				EgovCompanyConstants.APPLICATION_TYPE_APPROVED));
		types.addOption(new SelectOption(iwrb.getLocalizedString("rejected_applications", "Rejected applications"),
				EgovCompanyConstants.APPLICATION_TYPE_REJECTED));
		types.addFirstOption(new SelectOption(iwrb.getLocalizedString("select_application_type", "Select application type")));
		if (!StringUtil.isEmpty(stringValue)) {
			types.setSelectedElement(stringValue);
		}
		
		return types;
	}

	public void onUpdate(String[] values, IWContext iwc) {
	}

}
