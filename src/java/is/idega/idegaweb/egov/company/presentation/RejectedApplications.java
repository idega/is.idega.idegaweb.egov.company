/*
 * $Id: RejectedApplications.java,v 1.1 2008/07/29 12:57:41 anton Exp $ Created on Jun 11, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.company.business.ApplicationComparator;
import is.idega.idegaweb.egov.company.data.Application;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.idega.business.IBORuntimeException;
import com.idega.company.data.CompanyType;
import com.idega.core.builder.data.ICPage;
import com.idega.presentation.IWContext;
import com.idega.presentation.Image;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableColumn;
import com.idega.presentation.TableColumnGroup;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.GenericButton;
import com.idega.presentation.ui.Label;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;

public class RejectedApplications extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";
	private static final String PARAMETER_SORT = "prm_sort";

	private static final int ACTION_VIEW = 1;

	private ICPage iResponsePage;

	public void present(IWContext iwc) {
		try {
			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					drawForm(iwc);
					break;
			}
		}
		catch (RemoteException e) {
			throw new IBORuntimeException(e.getMessage());
		}
	}

	private int parseAction(IWContext iwc) {
		int action = ACTION_VIEW;
		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			action = Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}

		return action;
	}

	private void drawForm(IWContext iwc) throws RemoteException {
		Form form = new Form();
		form.setID("applicationApprover");
		form.setStyleClass("adminForm");
		form.addParameter(PARAMETER_ACTION, ACTION_VIEW);

		form.add(getNavigation(iwc));
		form.add(getApplications(iwc));

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

	protected Layer getNavigation(IWContext iwc) throws RemoteException {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("formSection");

		DropdownMenu menu = new DropdownMenu(PARAMETER_SORT);
		menu.addMenuElement(ApplicationComparator.SORT_CREATED, getResourceBundle().getLocalizedString("sort.created_date", "Created date"));
		menu.addMenuElement(ApplicationComparator.SORT_NAME, getResourceBundle().getLocalizedString("sort.name", "Name"));
		menu.addMenuElement(ApplicationComparator.SORT_PERSONAL_ID, getResourceBundle().getLocalizedString("sort.personal_id", "Personal ID"));
		menu.addMenuElement(ApplicationComparator.SORT_TYPE, getResourceBundle().getLocalizedString("sort.type", "Type"));
		menu.keepStatusOnAction(true);
		menu.setToSubmit();

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(getResourceBundle().getLocalizedString("approver.sort_by", "Sort by"), menu);
		formItem.add(label);
		formItem.add(menu);
		layer.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		layer.add(clearLayer);

		return layer;
	}

	private Table2 getApplications(IWContext iwc) throws RemoteException {
		Table2 table = new Table2();
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");
		table.setWidth("100%");
		table.setCellpadding(0);
		table.setCellspacing(0);

		TableColumnGroup columnGroup = table.createColumnGroup();
		TableColumn column = columnGroup.createColumn();
		column.setSpan(4);
		column = columnGroup.createColumn();
		column.setSpan(1);
		column.setWidth("12");

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
		cell.setStyleClass("companyType");
		cell.add(new Text(getResourceBundle().getLocalizedString("company_type", "Company type")));

		cell = row.createHeaderCell();
		cell.setStyleClass("createdDate");
		cell.add(new Text(getResourceBundle().getLocalizedString("created_date", "Created date")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("view");
		cell.add(Text.getNonBrakingSpace());

		group = table.createBodyRowGroup();
		int iRow = 1;

		List applications = new ArrayList(getBusiness().getRejectedApplications());
		Collections.sort(applications, new ApplicationComparator(iwc, iwc.isParameterSet(PARAMETER_SORT) ? Integer.parseInt(iwc.getParameter(PARAMETER_SORT)) : ApplicationComparator.SORT_NAME, iwc.getCurrentLocale()));

		Iterator iterator = applications.iterator();
		while (iterator.hasNext()) {
			row = group.createRow();

			Application application = (Application) iterator.next();
			CompanyType type = application.getType();
			IWTimestamp createdDate = new IWTimestamp(application.getCreated());

			Image edit = getBundle().getImage("edit.png");
			edit.setAlt(getResourceBundle().getLocalizedString("approver.view_application", "View application"));

			Link view = new Link(edit);
			view.setToolTip(getResourceBundle().getLocalizedString("approver.view_application", "View application"));
			view.addParameter(getBusiness().getSelectedCaseParameter(), application.getPrimaryKey().toString());
			if (getResponsePage() != null) {
				view.setPage(getResponsePage());
			}

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("name");
			cell.add(new Text(application.getName()));

			cell = row.createCell();
			cell.setStyleClass("personalID");
			cell.add(new Text(PersonalIDFormatter.format(application.getPersonalID(), iwc.getCurrentLocale())));

			cell = row.createCell();
			cell.setStyleClass("companyType");
			cell.add(new Text(type.getLocalizedName(iwc, iwc.getCurrentLocale())));

			cell = row.createCell();
			cell.setStyleClass("createdDate");
			cell.add(new Text(createdDate.getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT)));

			cell = row.createCell();
			cell.setStyleClass("view");
			cell.add(view);

			if (iRow % 2 == 0) {
				row.setStyleClass("evenRow");
			}
			else {
				row.setStyleClass("oddRow");
			}
			iRow++;
		}

		return table;
	}

	private ICPage getResponsePage() {
		return this.iResponsePage;
	}

	public void setResponsePage(ICPage responsePage) {
		this.iResponsePage = responsePage;
	}
}