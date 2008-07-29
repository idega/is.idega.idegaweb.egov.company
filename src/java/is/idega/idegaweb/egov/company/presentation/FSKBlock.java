/*
 * $Id: FSKBlock.java,v 1.1 2008/07/29 12:57:41 anton Exp $ Created on Jun 8, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.FSKBusiness;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.data.Company;
import com.idega.core.builder.data.ICPage;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.PostalCode;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Paragraph;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.Form;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.PersonalIDFormatter;

public abstract class FSKBlock extends Block {

	private FSKBusiness business;

	private IWResourceBundle iwrb;
	private IWBundle iwb;

	private ICPage iBackPage;

	public void main(IWContext iwc) throws Exception {
		setBundle(getBundle(iwc));
		setResourceBundle(getResourceBundle(iwc));
		this.business = getBusiness(iwc);

		present(iwc);
	}

	public abstract void present(IWContext iwc);

	protected Company getCompany(User user) {
		try {
			return getBusiness().getCompany(user);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public String getBundleIdentifier() {
		return FSKConstants.IW_BUNDLE_IDENTIFIER;
	}

	protected IWResourceBundle getResourceBundle() {
		return this.iwrb;
	}

	private void setResourceBundle(IWResourceBundle iwrb) {
		this.iwrb = iwrb;
	}

	protected IWBundle getBundle() {
		return this.iwb;
	}

	private void setBundle(IWBundle iwb) {
		this.iwb = iwb;
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

	protected void showNoPermission(IWContext iwc) {
		Form form = new Form();
		add(form);

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("stop");
		form.add(layer);

		Layer image = new Layer(Layer.DIV);
		image.setStyleClass("stopImage");
		layer.add(image);

		Heading1 heading = new Heading1(getResourceBundle().getLocalizedString("no_permission.subject", "You have no permission"));
		layer.add(heading);

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Text(getResourceBundle().getLocalizedString("no_permission.body", "You have no permission to access the admin functionality until your application has been accepted or your account reopened.")));
		layer.add(paragraph);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		if (getBackPage() != null) {
			Link home = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
			home.setStyleClass("buttonHome");
			home.setPage(getBackPage());
			bottom.add(home);
		}
	}

	protected boolean hasPermission(IWContext iwc) {
		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			return true;
		}

		Company company = getCompany(iwc.getCurrentUser());
		if (company != null) {
			return company.isOpen();
		}

		return false;
	}

	protected boolean hasDivisionPermission(IWContext iwc, Group division) {
		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			return true;
		}
		else if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY_ADMIN, iwc)) {
			return true;
		}
		else if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY, iwc)) {
			Collection userDivisions = new ArrayList();
			Collection groups = iwc.getCurrentUser().getParentGroups();
			Iterator iterator = groups.iterator();
			while (iterator.hasNext()) {
				Group parentGroup = (Group) iterator.next();
				if (parentGroup.getGroupType().equals(FSKConstants.GROUP_TYPE_DIVISION)) {
					userDivisions.add(parentGroup);
				}
			}

			return userDivisions.contains(division);
		}

		return false;
	}

	protected FSKBusiness getBusiness() {
		return this.business;
	}

	private FSKBusiness getBusiness(IWApplicationContext iwac) {
		try {
			return (FSKBusiness) IBOLookup.getServiceInstance(iwac, FSKBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected CompanyBusiness getCompanyBusiness(IWApplicationContext iwac) {
		try {
			return (CompanyBusiness) IBOLookup.getServiceInstance(iwac, CompanyBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected ICPage getBackPage() {
		return this.iBackPage;
	}

	public void setBackPage(ICPage responsePage) {
		this.iBackPage = responsePage;
	}
}