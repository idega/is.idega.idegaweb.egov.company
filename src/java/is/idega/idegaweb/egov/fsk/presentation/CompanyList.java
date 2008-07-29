/*
 * $Id: CompanyList.java,v 1.1 2008/07/29 10:48:18 anton Exp $
 * Created on Jun 14, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation;

import is.idega.idegaweb.egov.fsk.business.output.CompanyWriter;
import is.idega.idegaweb.egov.fsk.data.Application;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.idega.block.process.data.CaseStatus;
import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.core.builder.data.ICPage;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.PostalCode;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.DownloadLink;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.GenericButton;
import com.idega.util.PersonalIDFormatter;

public class CompanyList extends FSKBlock {

	private ICPage iResponsePage;

	public void present(IWContext iwc) {
		try {
			Form form = new Form();
			form.setID("courseList");
			form.setStyleClass("adminForm");

			form.add(getNavigation(iwc));
			form.add(getPrintouts(iwc));
			form.add(getCompanies(iwc));

			if (getBackPage() != null) {
				Layer buttonLayer = new Layer();
				buttonLayer.setStyleClass("buttonLayer");
				form.add(buttonLayer);

				GenericButton back = new GenericButton(getResourceBundle().getLocalizedString("back", "Back"));
				back.setPageToOpen(getBackPage());
				buttonLayer.add(back);
			}

			add(form);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private Layer getNavigation(IWContext iwc) throws RemoteException {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("formSection");

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		layer.add(clearLayer);

		return layer;
	}

	private Layer getPrintouts(IWContext iwc) {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("printIcons");

		layer.add(getXLSLink(iwc));

		return layer;
	}

	private Link getXLSLink(IWContext iwc) {
		DownloadLink link = new DownloadLink(getBundle().getImage("xls.gif"));
		link.setStyleClass("xls");
		link.setTarget(Link.TARGET_NEW_WINDOW);
		link.setMediaWriterClass(CompanyWriter.class);

		return link;
	}

	private Table2 getCompanies(IWContext iwc) throws RemoteException {
		Table2 table = new Table2();
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setWidth("100%");
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");

		Collection companies = null;
		try {
			companies = getCompanyBusiness(iwc).getActiveCompanies();
		}
		catch (RemoteException rex) {
			companies = new ArrayList();
		}

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("name");
		cell.add(new Text(getResourceBundle().getLocalizedString("name", "Name")));

		cell = row.createHeaderCell();
		cell.setStyleClass("personalID");
		cell.add(new Text(getResourceBundle().getLocalizedString("personal_id", "Personal ID")));

		cell = row.createHeaderCell();
		cell.setStyleClass("address");
		cell.add(new Text(getResourceBundle().getLocalizedString("address", "Address")));

		cell = row.createHeaderCell();
		cell.setStyleClass("postalCode");
		cell.add(new Text(getResourceBundle().getLocalizedString("postal_code", "Postal code")));

		cell = row.createHeaderCell();
		cell.setStyleClass("phoneNumber");
		cell.add(new Text(getResourceBundle().getLocalizedString("phone_number", "Phone number")));

		cell = row.createHeaderCell();
		cell.setStyleClass("email");
		cell.add(new Text(getResourceBundle().getLocalizedString("email", "E-mail")));

		cell = row.createHeaderCell();
		cell.setStyleClass("bankAccount");
		cell.add(new Text(getResourceBundle().getLocalizedString("bank_account", "bank_account")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("status");
		cell.add(new Text(getResourceBundle().getLocalizedString("status", "Status")));

		group = table.createBodyRowGroup();
		int iRow = 1;
		if (companies != null) {
			Iterator iter = companies.iterator();
			while (iter.hasNext()) {
				row = group.createRow();

				Company company = (Company) iter.next();
				Address address = company.getAddress();
				PostalCode code = address != null ? address.getPostalCode() : null;
				Phone phone = company.getPhone();
				Email email = company.getEmail();
				Application application = getBusiness().getApplication(company);
				CaseStatus status = application.getCaseStatus();

				cell = row.createCell();
				cell.setStyleClass("firstColumn");
				cell.setStyleClass("name");
				if (getResponsePage() != null) {
					Link link = new Link(company.getName());
					link.setPage(getResponsePage());
					link.addParameter(CourseList.PARAMETER_COMPANY_PK, company.getPrimaryKey().toString());
					cell.add(link);
				}
				else {
					cell.add(new Text(company.getName()));
				}

				cell = row.createCell();
				cell.setStyleClass("personalID");
				cell.add(new Text(PersonalIDFormatter.format(company.getPersonalID(), iwc.getCurrentLocale())));

				cell = row.createCell();
				cell.setStyleClass("address");
				if (address != null) {
					cell.add(new Text(address.getStreetAddress()));
				}
				else {
					cell.add(new Text("-"));
				}

				cell = row.createCell();
				cell.setStyleClass("postalCode");
				if (code != null) {
					cell.add(new Text(code.getPostalAddress()));
				}
				else {
					cell.add(new Text("-"));
				}

				cell = row.createCell();
				cell.setStyleClass("phoneNumber");
				if (phone != null) {
					cell.add(new Text(phone.getNumber()));
				}
				else {
					cell.add(new Text("-"));
				}

				cell = row.createCell();
				cell.setStyleClass("email");
				if (email != null) {
					cell.add(new Text(email.getEmailAddress()));
				}
				else {
					cell.add(new Text("-"));
				}

				cell = row.createCell();
				cell.setStyleClass("bankAccount");
				cell.add(new Text(company.getBankAccount()));

				cell = row.createCell();
				cell.setStyleClass("lastColumn");
				cell.setStyleClass("status");
				cell.add(new Text(String.valueOf(getBusiness().getLocalizedCaseStatusDescription(application, status, iwc.getCurrentLocale()))));

				if (iRow % 2 == 0) {
					row.setStyleClass("even");
				}
				else {
					row.setStyleClass("odd");
				}
				iRow++;
			}
		}

		group = table.createFooterRowGroup();
		row = group.createRow();

		cell = row.createCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("numberOfCompanies");
		cell.setColumnSpan(8);
		cell.add(new Text(getResourceBundle().getLocalizedString("number_of_companies", "Number of companies") + ": " + (iRow - 1)));

		return table;
	}

	public ICPage getResponsePage() {
		return this.iResponsePage;
	}

	public void setResponsePage(ICPage responsePage) {
		this.iResponsePage = responsePage;
	}
}