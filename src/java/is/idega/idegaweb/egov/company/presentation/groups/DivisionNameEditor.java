/*
 * $Id: DivisionNameEditor.java,v 1.1 2008/07/29 12:57:47 anton Exp $
 * Created on Jun 20, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation.groups;

import is.idega.idegaweb.egov.company.data.DivisionName;
import is.idega.idegaweb.egov.company.presentation.FSKBlock;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.business.IBORuntimeException;
import com.idega.company.data.CompanyType;
import com.idega.presentation.IWContext;
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
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextInput;

public class DivisionNameEditor extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";
	private static final String PARAMETER_TYPE_PK = "prm_type_pk";
	private static final String PARAMETER_COMPANY_TYPE_PK = "prm_company_type_pk";
	private static final String PARAMETER_DIVISION_NAME_PK = "prm_division_name_pk";
	private static final String PARAMETER_NAME = "prm_name";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_EDIT = 2;
	private static final int ACTION_NEW = 3;
	private static final int ACTION_SAVE = 4;
	private static final int ACTION_DELETE = 5;

	public void present(IWContext iwc) {
		try {
			Object groupPK = iwc.getParameter(PARAMETER_DIVISION_NAME_PK);

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
					if (saveDivisionName(iwc)) {
						showList(iwc);
					}
					else {
						showEditor(iwc, groupPK);
					}
					break;

				case ACTION_DELETE:
					getBusiness().removeDivisionName(iwc.getParameter(PARAMETER_DIVISION_NAME_PK));
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

		Collection collection = getCompanyBusiness(iwc).getTypes();

		DropdownMenu type = new DropdownMenu(PARAMETER_TYPE_PK);
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			CompanyType element = (CompanyType) iterator.next();
			type.addMenuElement(element.getPrimaryKey().toString(), element.getLocalizedName(iwc, iwc.getCurrentLocale()));
		}
		type.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_company_type", "Select company type"));
		type.keepStatusOnAction(true);
		type.setToSubmit(true);

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(getResourceBundle().getLocalizedString("company_type", "Company type"), type);
		formItem.add(label);
		formItem.add(type);
		layer.add(formItem);

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
		column.setSpan(3);
		column = columnGroup.createColumn();
		column.setSpan(2);
		column.setWidth("12");

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("name");
		cell.add(new Text(getResourceBundle().getLocalizedString("name", "Name")));

		cell = row.createHeaderCell();
		cell.setStyleClass("companyType");
		cell.add(new Text(getResourceBundle().getLocalizedString("company_type", "Company type")));

		cell = row.createHeaderCell();
		cell.setStyleClass("edit");
		cell.add(new Text(getResourceBundle().getLocalizedString("edit", "Edit")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("delete");
		cell.add(new Text(getResourceBundle().getLocalizedString("delete", "Delete")));

		group = table.createBodyRowGroup();
		int iRow = 1;

		Collection names = getBusiness().getDivisionNames(getCompanyType(iwc));
		java.util.Iterator iter = names.iterator();
		while (iter.hasNext()) {
			DivisionName name = (DivisionName) iter.next();
			CompanyType type = name.getType();

			row = group.createRow();

			Link edit = new Link(getBundle().getImage("edit.png", getResourceBundle().getLocalizedString("edit", "Edit")));
			edit.addParameter(PARAMETER_DIVISION_NAME_PK, name.getPrimaryKey().toString());
			edit.maintainParameter(PARAMETER_TYPE_PK, iwc);
			edit.addParameter(PARAMETER_ACTION, ACTION_EDIT);

			Link delete = new Link(getBundle().getImage("delete.png", getResourceBundle().getLocalizedString("delete", "Delete")));
			delete.addParameter(PARAMETER_DIVISION_NAME_PK, name.getPrimaryKey().toString());
			delete.maintainParameter(PARAMETER_TYPE_PK, iwc);
			delete.addParameter(PARAMETER_ACTION, ACTION_DELETE);
			delete.setClickConfirmation(getResourceBundle().getLocalizedString("division_name.confirm_delete", "Are you sure you want to delete the division name selected?"));

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("division");
			cell.add(new Text(name.getName()));

			cell = row.createCell();
			cell.setStyleClass("group");
			cell.add(new Text(type.getLocalizedName(iwc, iwc.getCurrentLocale())));

			cell = row.createCell();
			cell.setStyleClass("edit");
			cell.add(edit);

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("delete");
			cell.add(delete);

			if (iRow % 2 == 0) {
				row.setStyleClass("even");
			}
			else {
				row.setStyleClass("odd");
			}
			iRow++;
		}
		form.add(table);

		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);

		if (getBackPage() != null) {
			GenericButton back = new GenericButton(getResourceBundle().getLocalizedString("back", "Back"));
			back.setPageToOpen(getBackPage());
			buttonLayer.add(back);
		}

		SubmitButton newLink = new SubmitButton(getResourceBundle().getLocalizedString("division_name.new", "New group"), PARAMETER_ACTION, String.valueOf(ACTION_NEW));
		buttonLayer.add(newLink);

		add(form);
	}

	private void showEditor(IWContext iwc, Object namePK) throws java.rmi.RemoteException {
		Form form = new Form();
		form.setID("divisionNameEditor");
		form.setStyleClass("adminForm");
		form.maintainParameter(PARAMETER_TYPE_PK);
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_SAVE));

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("division_name.editor_help", "Fill in the desired values and click 'Save'.")));
		section.add(helpLayer);

		TextInput name = new TextInput(PARAMETER_NAME);
		name.keepStatusOnAction(true);

		CompanyType companyType = getCompanyType(iwc);
		Collection collection = getCompanyBusiness(iwc).getTypes();

		DropdownMenu type = new DropdownMenu(PARAMETER_COMPANY_TYPE_PK);
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			CompanyType element = (CompanyType) iterator.next();
			type.addMenuElement(element.getPrimaryKey().toString(), element.getLocalizedName(iwc, iwc.getCurrentLocale()));
		}
		type.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_company_type", "Select company type"));
		type.keepStatusOnAction(true);
		if (companyType != null) {
			type.setSelectedElement(companyType.getPrimaryKey().toString());
		}

		if (namePK != null) {
			DivisionName divisionName = getBusiness().getDivisionName(namePK);
			if (divisionName != null) {
				name.setContent(divisionName.getName());
				type.setSelectedElement(divisionName.getType().getPrimaryKey().toString());

				form.maintainParameter(PARAMETER_DIVISION_NAME_PK);
			}
		}

		Layer layer;
		Label label;

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("company_type", "Company type"), type);
		layer.add(label);
		layer.add(type);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("name", "Name"), name);
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

	private boolean saveDivisionName(IWContext iwc) throws RemoteException {
		Object pk = iwc.getParameter(PARAMETER_DIVISION_NAME_PK);
		Object typePK = iwc.isParameterSet(PARAMETER_COMPANY_TYPE_PK) ? iwc.getParameter(PARAMETER_COMPANY_TYPE_PK) : null;
		String name = iwc.isParameterSet(PARAMETER_NAME) ? iwc.getParameter(PARAMETER_NAME) : null;

		if (name == null) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("division_name_editor_error.must_provide_name", "You have to provide a name"));
			return false;
		}
		if (typePK == null) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("division_name_editor_error.must_select_type", "You have to select a company type"));
			return false;
		}

		try {
			CompanyType type = getCompanyBusiness(iwc).getCompanyType(typePK);
			getBusiness().storeDivisionName(pk, name, type);
		}
		catch (CreateException ce) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("division_name_editor_error.division_name_already_exists", "The name and type combination already exists."));
			return false;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
		catch (FinderException fe) {
			add(fe.getMessage());
		}

		return true;
	}

	private CompanyType getCompanyType(IWContext iwc) {
		CompanyType type = null;
		if (iwc.isParameterSet(PARAMETER_TYPE_PK)) {
			try {
				type = getCompanyBusiness(iwc).getCompanyType(iwc.getParameter(PARAMETER_TYPE_PK));
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
			catch (FinderException e) {
				e.printStackTrace();
			}
		}

		return type;
	}
}