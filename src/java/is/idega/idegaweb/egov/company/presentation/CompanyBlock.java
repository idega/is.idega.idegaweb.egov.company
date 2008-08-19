package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.application.presentation.ApplicationBlock;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;

import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading1;
import com.idega.util.PresentationUtil;
import com.idega.util.expression.ELUtil;

public abstract class CompanyBlock extends ApplicationBlock {
	
	protected IWBundle bundle;
	protected IWResourceBundle iwrb;
	
	protected CompanyApplicationBusiness getCompanyBusiness() {
		CompanyApplicationBusiness compAppBusiness = ELUtil.getInstance().getBean(CompanyApplicationBusiness.SPRING_BEAN_IDENTIFIER);
		return compAppBusiness;
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
		Layer container = new Layer();
		add(container);
		container.setStyleClass("insufficientRigthsStyle");
		
		Heading1 errorMessage = new Heading1(message);
		container.add(errorMessage);
	}

}
