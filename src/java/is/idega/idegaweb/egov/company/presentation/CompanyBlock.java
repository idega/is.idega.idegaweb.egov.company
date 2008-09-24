package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.application.presentation.ApplicationBlock;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.company.data.Company;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.PostalCode;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplication;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.util.PersonalIDFormatter;
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
		PresentationUtil.addJavaScriptSourceLineToHeader(iwc, "/dwr/interface/CompanyApplicationBusiness.js");
		PresentationUtil.addJavaScriptSourceLineToHeader(iwc, "/dwr/engine.js");
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
	
	protected Layer getCompanyInfo(IWContext iwc, Company company) {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("info");

		if (company != null) {
			Address address = company.getAddress();
			PostalCode postal = null;
			if (address != null) {
				postal = address.getPostalCode();
			}

			Layer personInfo = new Layer(Layer.DIV);
			personInfo.setStyleClass("personInfo");
			personInfo.setID("name");
			personInfo.add(new Text(company.getName()));
			layer.add(personInfo);

			personInfo = new Layer(Layer.DIV);
			personInfo.setStyleClass("personInfo");
			personInfo.setID("personalID");
			personInfo.add(new Text(PersonalIDFormatter.format(company.getPersonalID(), iwc.getCurrentLocale())));
			layer.add(personInfo);

			personInfo = new Layer(Layer.DIV);
			personInfo.setStyleClass("personInfo");
			personInfo.setID("address");
			if (address != null) {
				personInfo.add(new Text(address.getStreetAddress()));
			}
			layer.add(personInfo);

			personInfo = new Layer(Layer.DIV);
			personInfo.setStyleClass("personInfo");
			personInfo.setID("postal");
			if (postal != null) {
				personInfo.add(new Text(postal.getPostalAddress()));
			}
			layer.add(personInfo);
		}

		return layer;
	}
	
	protected Link getButtonLink(String text) {
		Layer all = new Layer(Layer.SPAN);
		all.setStyleClass("buttonSpan");

		Layer left = new Layer(Layer.SPAN);
		left.setStyleClass("left");
		all.add(left);

		Layer middle = new Layer(Layer.SPAN);
		middle.setStyleClass("middle");
		middle.add(new Text(text));
		all.add(middle);

		Layer right = new Layer(Layer.SPAN);
		right.setStyleClass("right");
		all.add(right);

		Link link = new Link(all);
		link.setStyleClass("button");

		return link;
	}

}
