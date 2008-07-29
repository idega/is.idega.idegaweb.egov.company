/*
 * $Id: DivisionEditor.java,v 1.1 2008/07/29 10:48:21 anton Exp $
 * Created on Jun 20, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation.groups;

import is.idega.idegaweb.egov.fsk.FSKConstants;
import is.idega.idegaweb.egov.fsk.business.DivisionComparator;
import is.idega.idegaweb.egov.fsk.data.Division;
import is.idega.idegaweb.egov.fsk.presentation.FSKBlock;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
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
import com.idega.presentation.text.Link;
import com.idega.presentation.text.ListItem;
import com.idega.presentation.text.Lists;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.GenericButton;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.util.IWTimestamp;

public class DivisionEditor extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";
	private static final String PARAMETER_COMPANY_PK = "prm_compamy_pk";
	private static final String PARAMETER_GROUP_PK = "prm_group_pk";
	private static final String PARAMETER_NAME = "prm_name";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_EDIT = 2;
	private static final int ACTION_NEW = 3;
	private static final int ACTION_SAVE = 4;
	private static final int ACTION_DELETE = 5;

	public void present(IWContext iwc) {
		try {
			Object groupPK = iwc.getParameter(PARAMETER_GROUP_PK);

			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				case ACTION_EDIT:
					showEditor(iwc, groupPK);
					break;

				case ACTION_NEW:
					showEditor(iwc, null);
					break;

				case ACTION_SAVE:
					if (saveGroup(iwc)) {
						showList(iwc);
					}
					else {
						showEditor(iwc, groupPK);
					}
					break;

				case ACTION_DELETE:
					if (!getBusiness().removeGroup(iwc.getParameter(PARAMETER_GROUP_PK), iwc.getCurrentUser())) {
						getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("division.remove_error", "You can not remove a division that has subgroups and/or courses attached to it."));
					}
					showList(iwc);
					break;
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private int parseAction(IWContext iwc) {
		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_VIEW;
	}

	protected Layer getNavigation(IWContext iwc) throws RemoteException {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("formSection");

		Company company = getCompany(iwc);

		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			Collection collection = getCompanyBusiness(iwc).getActiveCompanies();

			DropdownMenu companies = new DropdownMenu(PARAMETER_COMPANY_PK);
			companies.addMenuElements(collection);
			companies.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_company", "Select company"));
			companies.keepStatusOnAction(true);
			companies.setToSubmit(true);

			Layer formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			Label label = new Label(getResourceBundle().getLocalizedString("company", "Company"), companies);
			formItem.add(label);
			formItem.add(companies);
			layer.add(formItem);
		}
		else if (company != null) {
			layer.add(new HiddenInput(PARAMETER_COMPANY_PK, company.getPrimaryKey().toString()));
		}

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		layer.add(clearLayer);

		return layer;
	}

	private void showList(IWContext iwc) throws RemoteException {
		if (!hasPermission(iwc)) {
			showNoPermission(iwc);
			return;
		}
		if (!iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc) && !iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY_ADMIN, iwc)) {
			showNoPermission(iwc);
			return;
		}

		Form form = new Form();
		form.setID("seasonEditor");
		form.setStyleClass("adminForm");

		form.add(getNavigation(iwc));

		Table2 table = new Table2();
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setWidth("100%");
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");

		TableColumnGroup columnGroup = table.createColumnGroup();
		TableColumn column = columnGroup.createColumn();
		column.setSpan(2);
		column = columnGroup.createColumn();
		column.setSpan(2);
		column.setWidth("12");

		boolean isOpen = false;
		Company company = getCompany(iwc);

		List divisions = null;
		if (company != null) {
			isOpen = company.isOpen();
			try {
				divisions = new ArrayList(getBusiness().getAllDivisionsByCompany(iwc, company, iwc.getCurrentUser()));
			}
			catch (RemoteException rex) {
				divisions = new ArrayList();
			}
		}
		else {
			divisions = new ArrayList();
		}
		Collections.sort(divisions, new DivisionComparator(iwc.getCurrentLocale()));

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("name");
		cell.add(new Text(getResourceBundle().getLocalizedString("division", "Division")));

		cell = row.createHeaderCell();
		cell.setStyleClass("created");
		cell.add(new Text(getResourceBundle().getLocalizedString("division_editor.created_date", "Created date")));

		cell = row.createHeaderCell();
		cell.setStyleClass("edit");
		cell.add(new Text(getResourceBundle().getLocalizedString("edit", "Edit")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("delete");
		cell.add(new Text(getResourceBundle().getLocalizedString("delete", "Delete")));

		boolean showLegend = false;

		group = table.createBodyRowGroup();
		int iRow = 1;
		Iterator iter = divisions.iterator();
		while (iter.hasNext()) {
			row = group.createRow();

			boolean approved = false;
			Division division = (Division) iter.next();
			if (division.isApproved() != null && division.isApproved().booleanValue()) {
				row.setStyleClass("approved");
				approved = true;
				showLegend = true;
			}
			IWTimestamp created = new IWTimestamp(division.getCreated());

			Link edit = new Link(getBundle().getImage("edit.png", getResourceBundle().getLocalizedString("edit", "Edit")));
			edit.addParameter(PARAMETER_GROUP_PK, division.getPrimaryKey().toString());
			edit.addParameter(PARAMETER_ACTION, ACTION_EDIT);
			edit.maintainParameter(PARAMETER_COMPANY_PK, iwc);

			Link delete = new Link(getBundle().getImage("delete.png", getResourceBundle().getLocalizedString("delete", "Delete")));
			delete.addParameter(PARAMETER_GROUP_PK, division.getPrimaryKey().toString());
			delete.addParameter(PARAMETER_ACTION, ACTION_DELETE);
			delete.maintainParameter(PARAMETER_COMPANY_PK, iwc);
			delete.setClickConfirmation(getResourceBundle().getLocalizedString("division.confirm_delete", "Are you sure you want to delete the group selected?"));

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("name");
			cell.add(new Text(division.getName()));

			cell = row.createCell();
			cell.setStyleClass("created");
			cell.add(new Text(created.getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT)));

			cell = row.createCell();
			cell.setStyleClass("edit");
			cell.add(edit);

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("delete");
			if (approved) {
				cell.add(delete);
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

			if (iRow % 2 == 0) {
				row.setStyleClass("even");
			}
			else {
				row.setStyleClass("odd");
			}
			iRow++;
		}
		form.add(table);

		if (showLegend) {
			form.add(getLegend());
		}

		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);

		if (getBackPage() != null) {
			GenericButton back = new GenericButton(getResourceBundle().getLocalizedString("back", "Back"));
			back.setPageToOpen(getBackPage());
			buttonLayer.add(back);
		}

		if (company != null && (isOpen || iwc.getIWMainApplication().getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc))) {
			SubmitButton newLink = new SubmitButton(getResourceBundle().getLocalizedString("division.new", "New division"), PARAMETER_ACTION, String.valueOf(ACTION_NEW));
			buttonLayer.add(newLink);
		}

		add(form);
	}

	private void showEditor(IWContext iwc, Object divisionPK) throws java.rmi.RemoteException {
		Form form = new Form();
		form.setID("seasonEditor");
		form.setStyleClass("adminForm");
		form.maintainParameter(PARAMETER_COMPANY_PK);
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_SAVE));

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("division.division_editor_help", "Fill in the desired values and click 'Save'.")));
		section.add(helpLayer);

		Division division = divisionPK != null ? getBusiness().getDivision(divisionPK) : null;
		Collection names = getBusiness().getDivisionNames(getCompany(iwc), division);

		DropdownMenu name = new DropdownMenu(PARAMETER_NAME);
		name.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_division", "Select division"));
		Iterator iterator = names.iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			name.addMenuElement(string, string);
		}
		if (division != null) {
			name.setSelectedElement(division.getName());

			form.add(new HiddenInput(PARAMETER_GROUP_PK, divisionPK.toString()));
		}

		Layer layer;
		Label label;

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("division", "Division"), name);
		layer.add(label);
		layer.add(name);
		section.add(layer);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");

		section.add(clearLayer);

		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);

		SubmitButton save = new SubmitButton(getResourceBundle().getLocalizedString("save", "Save"));
		SubmitButton cancel = new SubmitButton(getResourceBundle().getLocalizedString("cancel", "Cancel"));
		cancel.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));

		buttonLayer.add(save);
		buttonLayer.add(cancel);
		add(form);
	}

	private Lists getLegend() {
		Lists list = new Lists();
		list.setStyleClass("legend");

		ListItem item = new ListItem();
		item.setStyleClass("approved");
		item.add(new Text(getResourceBundle().getLocalizedString("division.approved_division", "Approved division")));
		list.add(item);

		return list;
	}

	private boolean saveGroup(IWContext iwc) {
		Object pk = iwc.getParameter(PARAMETER_GROUP_PK);
		String name = iwc.isParameterSet(PARAMETER_NAME) ? iwc.getParameter(PARAMETER_NAME) : null;

		if (name == null) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("group_editor_error.must_provide_name", "You have to provide a name"));
			return false;
		}

		try {
			getBusiness().storeDivision(pk, name, getCompany(iwc));
		}
		catch (CreateException ce) {
			add(ce.getMessage());
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}

		return true;
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