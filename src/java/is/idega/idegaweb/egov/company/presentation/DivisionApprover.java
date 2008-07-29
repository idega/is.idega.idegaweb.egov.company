/*
 * $Id: DivisionApprover.java,v 1.1 2008/07/29 12:57:41 anton Exp $
 * Created on Jun 14, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.company.business.DivisionComparator;
import is.idega.idegaweb.egov.company.data.Division;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ejb.FinderException;

import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableColumn;
import com.idega.presentation.TableColumnGroup;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Paragraph;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.GenericButton;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.util.IWTimestamp;

public class DivisionApprover extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";

	private static final String PARAMETER_COMPANY_PK = "prm_company_pk";
	private static final String PARAMETER_DIVISION_PK = "prm_division_pk";
	private static final String PARAMETER_FILTER = "prm_filter";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_APPROVE = 2;
	private static final int ACTION_REJECT = 3;

	//private static final int FILTER_UNAPPROVED = 1;
	private static final int FILTER_APPROVED = 2;
	private static final int FILTER_REJECTED = 3;

	private int filter = FILTER_APPROVED;

	public void present(IWContext iwc) {
		try {
			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				case ACTION_APPROVE:
					approve(iwc);
					break;

				case ACTION_REJECT:
					reject(iwc);
					break;
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private int parseAction(IWContext iwc) {
		if (iwc.isParameterSet(PARAMETER_FILTER)) {
			filter = Integer.parseInt(iwc.getParameter(PARAMETER_FILTER));
		}
		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_VIEW;
	}

	protected Layer getNavigation(IWContext iwc) throws RemoteException {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("formSection");

		Collection collection = getCompanyBusiness(iwc).getActiveCompanies();

		DropdownMenu companies = new DropdownMenu(PARAMETER_COMPANY_PK);
		companies.addMenuElements(collection);
		companies.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_company", "Select company"));
		companies.keepStatusOnAction(true);
		companies.setToSubmit();

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(getResourceBundle().getLocalizedString("company", "Company"), companies);
		formItem.add(label);
		formItem.add(companies);
		layer.add(formItem);

		DropdownMenu filter = new DropdownMenu(PARAMETER_FILTER);
		//filter.addMenuElement(FILTER_UNAPPROVED, getResourceBundle().getLocalizedString("division_approver.unapproved", "Unapproved"));
		filter.addMenuElement(FILTER_APPROVED, getResourceBundle().getLocalizedString("division_approver.approved", "Approved"));
		filter.addMenuElement(FILTER_REJECTED, getResourceBundle().getLocalizedString("division_approver.rejected", "Rejected"));
		filter.keepStatusOnAction(true);
		filter.setToSubmit(true);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("filter", "Filter"), filter);
		formItem.add(label);
		formItem.add(filter);
		layer.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		layer.add(clearLayer);

		return layer;
	}

	private void showList(IWContext iwc) {
		try {
			Form form = new Form();
			form.setID("divisionApprover");
			form.setStyleClass("adminForm");

			form.add(getNavigation(iwc));
			form.add(getCourses(iwc));

			Layer buttonLayer = new Layer();
			buttonLayer.setStyleClass("buttonLayer");
			form.add(buttonLayer);

			if (/*filter == FILTER_UNAPPROVED ||*/filter == FILTER_REJECTED) {
				SubmitButton approve = new SubmitButton(getResourceBundle().getLocalizedString("division.approve", "Approve"), PARAMETER_ACTION, String.valueOf(ACTION_APPROVE));
				approve.setSubmitConfirm(getResourceBundle().getLocalizedString("division_approver.confirm_approve", "Are you sure you want to approve the selected divisions?"));
				buttonLayer.add(approve);
			}

			if (/*filter == FILTER_UNAPPROVED ||*/filter == FILTER_APPROVED) {
				SubmitButton reject = new SubmitButton(getResourceBundle().getLocalizedString(filter == FILTER_APPROVED ? "division.close" : "division.reject", "Reject"), PARAMETER_ACTION, String.valueOf(ACTION_REJECT));
				reject.setSubmitConfirm(getResourceBundle().getLocalizedString("division_approver.confirm_reject", "Are you sure you want to reject the selected divisions?"));
				buttonLayer.add(reject);
			}

			if (getBackPage() != null) {
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

	private Table2 getCourses(IWContext iwc) throws RemoteException {
		Table2 table = new Table2();
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setWidth("100%");
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");

		TableColumnGroup columnGroup = table.createColumnGroup();
		TableColumn column = columnGroup.createColumn();
		column.setSpan(3);
		column = columnGroup.createColumn();
		column.setSpan(1);
		column.setWidth("12");

		Company company = getCompany(iwc);

		List divisions = null;
		try {
			/*if (filter == FILTER_UNAPPROVED) {
				divisions = new ArrayList(getBusiness().getNonApprovedDivisions(company));
			}
			else*/if (filter == FILTER_APPROVED) {
				divisions = new ArrayList(getBusiness().getApprovedDivisions(company));
			}
			else if (filter == FILTER_REJECTED) {
				divisions = new ArrayList(getBusiness().getRejectedDivisions(company));
			}
		}
		catch (RemoteException rex) {
			divisions = new ArrayList();
		}
		Collections.sort(divisions, new DivisionComparator(iwc.getCurrentLocale(), true));

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("company");
		cell.add(new Text(getResourceBundle().getLocalizedString("company", "Company")));

		cell = row.createHeaderCell();
		cell.setStyleClass("division");
		cell.add(new Text(getResourceBundle().getLocalizedString("division", "Division")));

		cell = row.createHeaderCell();
		cell.setStyleClass("created");
		cell.add(new Text(getResourceBundle().getLocalizedString("division_approver.created_date", "Created date")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("checkbox");
		cell.add(new Text(getResourceBundle().getLocalizedString("select", "Select")));

		group = table.createBodyRowGroup();
		int iRow = 1;
		if (divisions != null) {
			Iterator iter = divisions.iterator();
			while (iter.hasNext()) {
				Division division = (Division) iter.next();
				company = division.getCompany();
				if (company == null) {
					continue;
				}
				IWTimestamp created = new IWTimestamp(division.getCreated());

				row = group.createRow();

				CheckBox check = new CheckBox(PARAMETER_DIVISION_PK, division.getPrimaryKey().toString());

				cell = row.createCell();
				cell.setStyleClass("firstColumn");
				cell.setStyleClass("company");
				cell.add(new Text(company.getName()));

				cell = row.createCell();
				cell.setStyleClass("division");
				cell.add(new Text(division.getName()));

				cell = row.createCell();
				cell.setStyleClass("created");
				cell.add(new Text(created.getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT)));

				cell = row.createCell();
				cell.setStyleClass("lastColumn");
				cell.setStyleClass("checkbox");
				cell.add(check);

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
		cell.setStyleClass("numberOfDivisions");
		cell.setColumnSpan(4);
		cell.add(new Text(getResourceBundle().getLocalizedString("number_of_divisions", "Number of divisions") + ": " + (iRow - 1)));

		return table;
	}

	private void approve(IWContext iwc) throws RemoteException {
		getBusiness().approveDivisions(iwc.getParameterValues(PARAMETER_DIVISION_PK), iwc.getCurrentLocale());
		showReceipt(iwc, getResourceBundle().getLocalizedString("division_approver.approved_heading", "Divisions approved"), getResourceBundle().getLocalizedString("division_approver.approved_text", "The selected divisions have been approved."));
	}

	private void reject(IWContext iwc) throws RemoteException {
		getBusiness().rejectDivisions(iwc.getParameterValues(PARAMETER_DIVISION_PK), iwc.getCurrentLocale());
		showReceipt(iwc, getResourceBundle().getLocalizedString("division_approver.rejected_heading", "Divisions rejected"), getResourceBundle().getLocalizedString("division_approver.rejected_text", "The selected divisions have been rejected."));
	}

	private void showReceipt(IWContext iwc, String subject, String body) throws RemoteException {
		Form form = new Form();
		add(form);

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("receipt");
		form.add(layer);

		Layer image = new Layer(Layer.DIV);
		image.setStyleClass("receiptImage");
		layer.add(image);

		Heading1 heading = new Heading1(subject);
		layer.add(heading);

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Text(body));
		layer.add(paragraph);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link home = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
		home.setStyleClass("buttonHome");
		home.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));
		home.maintainParameter(PARAMETER_FILTER, iwc);
		bottom.add(home);
	}

	private Company getCompany(IWContext iwc) {
		Company company = getCompany(iwc.getCurrentUser());
		if (company == null && iwc.isParameterSet(PARAMETER_COMPANY_PK)) {
			try {
				company = getCompanyBusiness(iwc).getCompany(new Integer(iwc.getParameter(PARAMETER_COMPANY_PK)));
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
			catch (FinderException e) {
				e.printStackTrace();
			}
		}

		return company;
	}
}