/*
 * $Id: SeasonEditor.java,v 1.1 2008/07/29 10:48:18 anton Exp $ Created on Jun 8, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation;

import is.idega.idegaweb.egov.fsk.data.Season;

import java.rmi.RemoteException;
import java.sql.Date;
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
import com.idega.presentation.ui.DatePicker;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.GenericButton;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextInput;
import com.idega.util.IWTimestamp;

public class SeasonEditor extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";
	private static final String PARAMETER_SEASON_PK = "prm_season_pk";
	private static final String PARAMETER_NAME = "prm_name";
	private static final String PARAMETER_START_DATE = "prm_start_date";
	private static final String PARAMETER_END_DATE = "prm_end_date";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_EDIT = 2;
	private static final int ACTION_NEW = 3;
	private static final int ACTION_SAVE = 4;
	private static final int ACTION_DELETE = 5;

	public void present(IWContext iwc) {
		try {
			Object seasonPK = iwc.getParameter(PARAMETER_SEASON_PK);

			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				case ACTION_EDIT:
					showEditor(iwc, seasonPK);
					break;

				case ACTION_NEW:
					showEditor(iwc, null);
					break;

				case ACTION_SAVE:
					if (saveSeason(iwc)) {
						showList(iwc);
					}
					else {
						showEditor(iwc, seasonPK);
					}
					break;

				case ACTION_DELETE:
					if (!getBusiness().deleteSeason(iwc.getParameter(PARAMETER_SEASON_PK))) {
						getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("season.remove_error", "You can not remove a season that has periods attached to it."));
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

	public void showList(IWContext iwc) {
		Form form = new Form();
		form.setID("seasonEditor");
		form.setStyleClass("adminForm");

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

		Collection seasons = null;
		try {
			seasons = getBusiness().getAllSeasons();
		}
		catch (RemoteException rex) {
			seasons = new ArrayList();
		}

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("name");
		cell.add(new Text(getResourceBundle().getLocalizedString("season", "Season")));

		cell = row.createHeaderCell();
		cell.setStyleClass("startDate");
		cell.add(new Text(getResourceBundle().getLocalizedString("start_date", "Start date")));

		cell = row.createHeaderCell();
		cell.setStyleClass("endDate");
		cell.add(new Text(getResourceBundle().getLocalizedString("end_date", "End date")));

		cell = row.createHeaderCell();
		cell.setStyleClass("edit");
		cell.add(new Text(getResourceBundle().getLocalizedString("edit", "Edit")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("delete");
		cell.add(new Text(getResourceBundle().getLocalizedString("delete", "Delete")));

		group = table.createBodyRowGroup();
		int iRow = 1;
		java.util.Iterator iter = seasons.iterator();
		while (iter.hasNext()) {
			row = group.createRow();

			Season season = (Season) iter.next();
			IWTimestamp startDate = new IWTimestamp(season.getStartDate());
			IWTimestamp endDate = new IWTimestamp(season.getEndDate());

			Link edit = new Link(getBundle().getImage("edit.png", getResourceBundle().getLocalizedString("edit", "Edit")));
			edit.addParameter(PARAMETER_SEASON_PK, season.getPrimaryKey().toString());
			edit.addParameter(PARAMETER_ACTION, ACTION_EDIT);

			Link delete = new Link(getBundle().getImage("delete.png", getResourceBundle().getLocalizedString("delete", "Delete")));
			delete.addParameter(PARAMETER_SEASON_PK, season.getPrimaryKey().toString());
			delete.addParameter(PARAMETER_ACTION, ACTION_DELETE);
			delete.setClickConfirmation(getResourceBundle().getLocalizedString("season.confirm_delete", "Are you sure you want to delete the season selected?"));

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("name");
			cell.add(new Text(season.getName()));

			cell = row.createCell();
			cell.setStyleClass("startDate");
			cell.add(new Text(startDate.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)));

			cell = row.createCell();
			cell.setStyleClass("endDate");
			cell.add(new Text(endDate.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)));

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

		SubmitButton newLink = new SubmitButton(getResourceBundle().getLocalizedString("season.new", "New season"), PARAMETER_ACTION, String.valueOf(ACTION_NEW));
		buttonLayer.add(newLink);

		add(form);
	}

	public void showEditor(IWContext iwc, Object seasonPK) throws java.rmi.RemoteException {
		Form form = new Form();
		form.setID("seasonEditor");
		form.setStyleClass("adminForm");
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_SAVE));

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("season.season_editor_help", "Fill in the desired values and click 'Save'.")));
		section.add(helpLayer);

		Season season = getBusiness().getSeason(seasonPK);

		TextInput name = new TextInput(PARAMETER_NAME);
		DatePicker startDate = new DatePicker(PARAMETER_START_DATE);
		startDate.setDateFormatPattern("dd.MM.yyyy");
		DatePicker endDate = new DatePicker(PARAMETER_END_DATE);
		endDate.setDateFormatPattern("dd.MM.yyyy");

		if (season != null) {
			name.setContent(season.getName());
			if (season.getStartDate() != null) {
				startDate.setDate(season.getStartDate());
			}
			if (season.getEndDate() != null) {
				endDate.setDate(season.getEndDate());
			}

			form.add(new HiddenInput(PARAMETER_SEASON_PK, seasonPK.toString()));
		}

		Layer layer;
		Label label;

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("season", "Season"), name);
		layer.add(label);
		layer.add(name);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label();
		label.setLabel(getResourceBundle().getLocalizedString("start_date", "Start date"));
		layer.add(label);
		layer.add(startDate);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label();
		label.setLabel(getResourceBundle().getLocalizedString("end_date", "End date"));
		layer.add(label);
		layer.add(endDate);
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

	private boolean saveSeason(IWContext iwc) {
		Object pk = iwc.getParameter(PARAMETER_SEASON_PK);
		String name = iwc.isParameterSet(PARAMETER_NAME) ? iwc.getParameter(PARAMETER_NAME) : null;
		Date startDate = iwc.isParameterSet(PARAMETER_START_DATE) ? new IWTimestamp(iwc.getParameter(PARAMETER_START_DATE)).getDate() : null;
		Date endDate = iwc.isParameterSet(PARAMETER_END_DATE) ? new IWTimestamp(iwc.getParameter(PARAMETER_END_DATE)).getDate() : null;

		if (name == null) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("season_editor_error.must_set_name", "You have to provide a name"));
			return false;
		}
		if (startDate == null) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("season_editor_error.must_select_start_date", "You have to select a start date"));
			return false;
		}
		if (endDate == null) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("season_editor_error.must_select_end_date", "You have to select an end date"));
			return false;
		}

		try {
			if (getBusiness().canStoreSeason(pk, startDate, endDate)) {
				getBusiness().storeSeason(pk, name, startDate, endDate);
				return true;
			}
			else {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("season_editor.store_error", "You can not store a season that overlaps with already created seasons."));
				return false;
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

		return false;
	}
}