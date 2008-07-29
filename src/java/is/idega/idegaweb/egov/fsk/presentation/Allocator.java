/*
 * $Id: Allocator.java,v 1.1 2008/07/29 10:48:18 anton Exp $ Created on Jun 8, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation;

import is.idega.idegaweb.egov.fsk.data.Allocation;
import is.idega.idegaweb.egov.fsk.data.Season;

import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.business.IBORuntimeException;
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
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextInput;
import com.idega.presentation.ui.util.SelectorUtility;
import com.idega.util.IWTimestamp;
import com.idega.util.text.TextSoap;

public class Allocator extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";
	private static final String PARAMETER_ALLOCATION_PK = "prm_allocation_pk";
	private static final String PARAMETER_SEASON_PK = "prm_season_pk";
	private static final String PARAMETER_BIRTHYEAR_FROM = "prm_birthyear_from";
	private static final String PARAMETER_BIRTHYEAR_TO = "prm_birthyear_to";
	private static final String PARAMETER_AMOUNT = "prm_amount";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_EDIT = 2;
	private static final int ACTION_NEW = 3;
	private static final int ACTION_SAVE = 4;
	private static final int ACTION_DELETE = 5;

	public void present(IWContext iwc) {
		try {
			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				case ACTION_EDIT:
					Object typePK = iwc.getParameter(PARAMETER_ALLOCATION_PK);
					showEditor(iwc, typePK);
					break;

				case ACTION_NEW:
					showEditor(iwc, null);
					break;

				case ACTION_SAVE:
					saveAllocation(iwc);
					break;

				case ACTION_DELETE:
					if (!getBusiness().deleteAllocation(iwc.getParameter(PARAMETER_ALLOCATION_PK))) {
						getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("allocator.remove_error", "You can not remove an allocation that has parent allocations attached to it."));
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

	public void showList(IWContext iwc) throws RemoteException {
		Form form = new Form();
		form.setStyleClass("adminForm");

		Table2 table = new Table2();
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setWidth("100%");
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");

		TableColumnGroup columnGroup = table.createColumnGroup();
		TableColumn column = columnGroup.createColumn();
		column.setSpan(7);
		column = columnGroup.createColumn();
		column.setSpan(2);
		column.setWidth("12");

		Collection allocations = null;
		try {
			allocations = getBusiness().getAllocations();
		}
		catch (RemoteException rex) {
			allocations = new ArrayList();
		}

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("season");
		cell.add(new Text(getResourceBundle().getLocalizedString("season", "Season")));

		cell = row.createHeaderCell();
		cell.setStyleClass("creationDate");
		cell.add(new Text(getResourceBundle().getLocalizedString("creation_date", "Creation date")));

		cell = row.createHeaderCell();
		cell.setStyleClass("birthyearFrom");
		cell.add(new Text(getResourceBundle().getLocalizedString("birthyear_from", "Birthyear from")));

		cell = row.createHeaderCell();
		cell.setStyleClass("birthyearTo");
		cell.add(new Text(getResourceBundle().getLocalizedString("birthyear_to", "Birthyear to")));

		cell = row.createHeaderCell();
		cell.setStyleClass("amount");
		cell.add(new Text(getResourceBundle().getLocalizedString("amount", "Amount")));

		cell = row.createHeaderCell();
		cell.setStyleClass("numberOfChildren");
		cell.add(new Text(getResourceBundle().getLocalizedString("number_of_children", "Number of children")));

		cell = row.createHeaderCell();
		cell.setStyleClass("totalAmount");
		cell.add(new Text(getResourceBundle().getLocalizedString("total_amount", "Total amount")));

		cell = row.createHeaderCell();
		cell.setStyleClass("edit");
		cell.add(new Text(getResourceBundle().getLocalizedString("edit", "Edit")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("delete");
		cell.add(new Text(getResourceBundle().getLocalizedString("delete", "Delete")));

		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());
		currencyFormat.setMaximumFractionDigits(0);
		NumberFormat numberFormat = NumberFormat.getNumberInstance(iwc.getCurrentLocale());
		numberFormat.setMaximumFractionDigits(0);

		group = table.createBodyRowGroup();
		int iRow = 1;
		java.util.Iterator iter = allocations.iterator();
		while (iter.hasNext()) {
			row = group.createRow();

			Allocation allocation = (Allocation) iter.next();
			Season season = allocation.getSeason();
			IWTimestamp creationDate = new IWTimestamp(allocation.getCreationDate());
			int numberOfChildren = getBusiness().getNumberOfChildren(allocation.getBirthyearFrom(), allocation.getBirthyearTo());
			float totalAmount = numberOfChildren * (int) allocation.getAmount();
			boolean hasAllocations = getBusiness().hasPaymentAllocations(allocation);

			Link edit = new Link(getBundle().getImage("edit.png", getResourceBundle().getLocalizedString("edit", "Edit")));
			edit.addParameter(PARAMETER_ALLOCATION_PK, allocation.getPrimaryKey().toString());
			edit.addParameter(PARAMETER_ACTION, ACTION_EDIT);

			Link delete = new Link(getBundle().getImage("delete.png", getResourceBundle().getLocalizedString("delete", "Delete")));
			delete.addParameter(PARAMETER_ALLOCATION_PK, allocation.getPrimaryKey().toString());
			delete.addParameter(PARAMETER_ACTION, ACTION_DELETE);
			delete.setClickConfirmation(getResourceBundle().getLocalizedString("allocator.confirm_delete", "Are you sure you want to delete the allocation selected?"));

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("season");
			cell.add(new Text(season.getName()));

			cell = row.createCell();
			cell.setStyleClass("creationDate");
			cell.add(new Text(creationDate.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)));

			cell = row.createCell();
			cell.setStyleClass("birthyearFrom");
			cell.add(new Text(String.valueOf(allocation.getBirthyearFrom())));

			cell = row.createCell();
			cell.setStyleClass("birthyearTo");
			cell.add(new Text(String.valueOf(allocation.getBirthyearTo())));

			cell = row.createCell();
			cell.setStyleClass("amount");
			cell.add(new Text(currencyFormat.format(allocation.getAmount())));

			cell = row.createCell();
			cell.setStyleClass("numberOfChildren");
			cell.add(new Text(numberFormat.format(numberOfChildren)));

			cell = row.createCell();
			cell.setStyleClass("totalAmount");
			cell.add(new Text(currencyFormat.format(totalAmount)));

			cell = row.createCell();
			cell.setStyleClass("edit");
			if (!hasAllocations) {
				cell.add(edit);
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("delete");
			if (!hasAllocations) {
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

		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);

		if (getBackPage() != null) {
			GenericButton back = new GenericButton(getResourceBundle().getLocalizedString("back", "Back"));
			back.setPageToOpen(getBackPage());
			buttonLayer.add(back);
		}

		SubmitButton newLink = new SubmitButton(getResourceBundle().getLocalizedString("allocator.new", "New allocation"), PARAMETER_ACTION, String.valueOf(ACTION_NEW));
		buttonLayer.add(newLink);

		add(form);
	}

	public void showEditor(IWContext iwc, Object allocationPK) throws java.rmi.RemoteException {
		Form form = new Form();
		form.setStyleClass("adminForm");

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("allocator.allocator_help", "Fill in the desired values and click 'Save'.")));
		section.add(helpLayer);

		Allocation allocation = getBusiness().getAllocation(allocationPK);

		SelectorUtility util = new SelectorUtility();

		DropdownMenu seasons = (DropdownMenu) util.getSelectorFromIDOEntities(new DropdownMenu(PARAMETER_SEASON_PK), getBusiness().getAllSeasons());
		seasons.keepStatusOnAction(true);

		TextInput amount = new TextInput(PARAMETER_AMOUNT);
		amount.keepStatusOnAction(true);

		TextInput birthyearFrom = new TextInput(PARAMETER_BIRTHYEAR_FROM);
		birthyearFrom.keepStatusOnAction(true);

		TextInput birthyearTo = new TextInput(PARAMETER_BIRTHYEAR_TO);
		birthyearTo.keepStatusOnAction(true);

		if (allocation != null) {
			seasons.setSelectedElement(allocation.getSeason().getPrimaryKey().toString());
			amount.setContent(String.valueOf((int) allocation.getAmount()));
			birthyearFrom.setContent(String.valueOf(allocation.getBirthyearFrom()));
			birthyearTo.setContent(String.valueOf(allocation.getBirthyearTo()));

			form.add(new HiddenInput(PARAMETER_ALLOCATION_PK, allocationPK.toString()));
		}

		Layer layer;
		Label label;

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("name", "Name"), seasons);
		layer.add(label);
		layer.add(seasons);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("amount", "Amount"), amount);
		layer.add(label);
		layer.add(amount);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("birthyear_from", "Birthyear from"), birthyearFrom);
		layer.add(label);
		layer.add(birthyearFrom);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("birthyear_to", "Birthyear to"), birthyearTo);
		layer.add(label);
		layer.add(birthyearTo);
		section.add(layer);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");

		section.add(clearLayer);

		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);

		SubmitButton save = new SubmitButton(getResourceBundle().getLocalizedString("save", "Save"), PARAMETER_ACTION, String.valueOf(ACTION_SAVE));
		SubmitButton cancel = new SubmitButton(getResourceBundle().getLocalizedString("cancel", "Cancel"), PARAMETER_ACTION, String.valueOf(ACTION_VIEW));

		buttonLayer.add(cancel);
		buttonLayer.add(save);
		add(form);
	}

	private void saveAllocation(IWContext iwc) {
		Object pk = iwc.getParameter(PARAMETER_ALLOCATION_PK);
		Object seasonPK = iwc.getParameter(PARAMETER_SEASON_PK);
		int birthyearFrom = iwc.isParameterSet(PARAMETER_BIRTHYEAR_FROM) ? Integer.parseInt(iwc.getParameter(PARAMETER_BIRTHYEAR_FROM)) : -1;
		int birthyearTo = iwc.isParameterSet(PARAMETER_BIRTHYEAR_TO) ? Integer.parseInt(iwc.getParameter(PARAMETER_BIRTHYEAR_TO)) : -1;
		float amount = -1;

		if (iwc.isParameterSet(PARAMETER_AMOUNT)) {
			String amountString = iwc.getParameter(PARAMETER_AMOUNT);
			amountString = TextSoap.findAndReplace(amountString, ",", "");
			amountString = TextSoap.findAndReplace(amountString, ".", "");

			amount = Float.parseFloat(amountString);
		}

		if (birthyearFrom > 0 && birthyearTo > 0 && amount > 0) {
			try {
				Season season = getBusiness().getSeason(seasonPK);
				if (getBusiness().canStoreAllocation(pk, season, birthyearFrom, birthyearTo)) {
					getBusiness().storeAllocation(pk, season, birthyearFrom, birthyearTo, amount);
					showList(iwc);
				}
				else {
					getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("allocator.store_error", "You can not store an allocation that overlaps with already created allocation."));
					showEditor(iwc, pk);
				}
			}
			catch (CreateException ce) {
				add(ce.getMessage());
			}
			catch (FinderException fe) {
				fe.printStackTrace();
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}
	}
}