package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.application.presentation.ApplicationBlock;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading1;
import com.idega.util.PresentationUtil;
import com.idega.util.StringUtil;

public abstract class CompanyBlock extends ApplicationBlock {
	
	protected IWBundle bundle;
	protected IWResourceBundle iwrb;
	
	protected CompanyApplicationBusiness getCompanyBusiness() {
		try {
			return (CompanyApplicationBusiness) IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CompanyApplicationBusiness.class);
		} catch (IBOLookupException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String getBundleIdentifier() {
		return EgovCompanyConstants.IW_BUNDLE_IDENTIFIER;
	}
	
	@Override
	protected void present(IWContext iwc) throws Exception {
		bundle = getBundle(iwc);
		iwrb = bundle.getResourceBundle(iwc);
		
		PresentationUtil.addStyleSheetToHeader(iwc, bundle.getVirtualPathWithFileNameString("style/egov_company.css"));
	}
	
	protected void showInsufficientRightsMessage(String message) {
		showMessage(message, "insufficientRigthsStyle");
	}
	
	protected void showMessage(String message) {
		showMessage(message, null);
	}
	
	protected void showMessage(String message, String styleClass) {	
		add(getMessage(message, styleClass));
	}
	
	protected Layer getMessage(String message) {
		return getMessage(message, null);
	}
	
	protected Layer getMessage(String message, String styleClass) {
		Layer container = new Layer();
		if (!StringUtil.isEmpty(styleClass)) {
			container.setStyleClass(styleClass);
		}
		
		Heading1 errorMessage = new Heading1(message);
		container.add(errorMessage);
		return container;
	}

}
