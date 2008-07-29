/*
 * $Id: PeriodEditor.java,v 1.1 2008/07/29 10:48:18 anton Exp $ Created on Jun 8, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation;

import is.idega.idegaweb.egov.fsk.FSKConstants;
import is.idega.idegaweb.egov.fsk.data.Constant;
import is.idega.idegaweb.egov.fsk.data.Period;
import is.idega.idegaweb.egov.fsk.data.Season;

import java.rmi.RemoteException;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.business.IBORuntimeException;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Span;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableColumn;
import com.idega.presentation.TableColumnGroup;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DatePicker;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.GenericButton;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextInput;
import com.idega.presentation.ui.util.SelectorUtility;
import com.idega.util.IWTimestamp;

public class PeriodEditor extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";
	private static final String PARAMETER_SEASON_PK = "prm_period_pk";
	private static final String PARAMETER_PERIOD_PK = "prm_season_pk";
	private static final String PARAMETER_NAME = "prm_name";
	private static final String PARAMETER_START_DATE = "prm_start_date";
	private static final String PARAMETER_END_DATE = "prm_end_date";
	private static final String PARAMETER_MINIMUM_WEEKS = "prm_minimum_weeks";
	private static final String PARAMETER_COST_AMOUNT = "prm_cost_amount";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_EDIT = 2;
	private static final int ACTION_NEW = 3;
	private static final int ACTION_SAVE = 4;
	private static final int ACTION_DELETE = 5;

	public void present(IWContext iwc) {
		try {
			Object periodPK = iwc.getParameter(PARAMETER_PERIOD_PK);

			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				case ACTION_EDIT:
					showEditor(iwc, periodPK);
					break;

				case ACTION_NEW:
					showEditor(iwc, null);
					break;

				case ACTION_SAVE:
					if (savePeriod(iwc)) {
						showList(iwc);
					}
					else {
						showEditor(iwc, periodPK);
					}
					break;

				case ACTION_DELETE:
					if (!getBusiness().deletePeriod(iwc.getParameter(PARAMETER_PERIOD_PK))) {
						getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("period.remove_error", "You can not remove a period that has courses attached to it."));
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
		form.setID("periodEditor");
		form.setStyleClass("adminForm");

		Table2 table = new Table2();
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setWidth("100%");
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");

		TableColumnGroup columnGroup = table.createColumnGroup();
		TableColumn column = columnGroup.createColumn();
		column.setSpan(4);
		column = columnGroup.createColumn();
		column.setSpan(2);
		column.setWidth("12");

		Collection periods = null;
		try {
			periods = getBusiness().getAllPeriods();
		}
		catch (RemoteException rex) {
			periods = new ArrayList();
		}

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("season");
		cell.add(new Text(getResourceBundle().getLocalizedString("season", "Season")));

		cell = row.createHeaderCell();
		cell.setStyleClass("name");
		cell.add(new Text(getResourceBundle().getLocalizedString("period", "period")));

		cell = row.createHeaderCell();
		cell.setStyleClass("startDate");
		cell.add(new Text(getResourceBundle().getLocalizedString("start_date", "Start date")));

		cell = row.createHeaderCell();
		cell.setStyleClass("endDate");
		cell.add(new Text(getResourceBundle().getLocalizedString("end_date", "End date")));

		cell = row.createHeaderCell();
		cell.setStyleClass("minimumWeeks");
		cell.add(new Text(getResourceBundle().getLocalizedString("minimum_weeks", "Minimum weeks")));

		cell = row.createHeaderCell();
		cell.setStyleClass("costsAmount");
		cell.add(new Text(getResourceBundle().getLocalizedString("costs_amount", "Cost amount")));

		cell = row.createHeaderCell();
		cell.setStyleClass("edit");
		cell.add(new Text(getResourceBundle().getLocalizedString("edit", "Edit")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("delete");
		cell.add(new Text(getResourceBundle().getLocalizedString("delete", "Delete")));

		NumberFormat format = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());
		format.setMinimumFractionDigits(0);

		group = table.createBodyRowGroup();
		int iRow = 1;
		Iterator iter = periods.iterator();
		while (iter.hasNext()) {
			row = group.createRow();

			Period period = (Period) iter.next();
			Season season = period.getSeason();
			IWTimestamp startDate = new IWTimestamp(period.getStartDate());
			IWTimestamp endDate = new IWTimestamp(period.getEndDate());

			Link edit = new Link(getBundle().getImage("edit.png", getResourceBundle().getLocalizedString("edit", "Edit")));
			edit.addParameter(PARAMETER_PERIOD_PK, period.getPrimaryKey().toString());
			edit.addParameter(PARAMETER_ACTION, ACTION_EDIT);

			Link delete = new Link(getBundle().getImage("delete.png", getResourceBundle().getLocalizedString("delete", "Delete")));
			delete.addParameter(PARAMETER_PERIOD_PK, period.getPrimaryKey().toString());
			delete.addParameter(PARAMETER_ACTION, ACTION_DELETE);
			delete.setClickConfirmation(getResourceBundle().getLocalizedString("period.confirm_delete", "Are you sure you want to delete the period selected?"));

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("season");
			cell.add(new Text(season.getName()));

			cell = row.createCell();
			cell.setStyleClass("name");
			cell.add(new Text(period.getName()));

			cell = row.createCell();
			cell.setStyleClass("startDate");
			cell.add(new Text(startDate.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)));

			cell = row.createCell();
			cell.setStyleClass("endDate");
			cell.add(new Text(endDate.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)));

			cell = row.createCell();
			cell.setStyleClass("minimumWeeks");
			cell.add(new Text(String.valueOf(period.getMinimumWeeks())));

			cell = row.createCell();
			cell.setStyleClass("costsAmount");
			if (period.getCostAmount() > 0) {
				cell.add(new Text(format.format(period.getCostAmount())));
			}
			else {
				cell.add(new Text("-"));
			}

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

		SubmitButton newLink = new SubmitButton(getResourceBundle().getLocalizedString("period.new", "New period"), PARAMETER_ACTION, String.valueOf(ACTION_NEW));
		buttonLayer.add(newLink);

		add(form);
	}

	public void showEditor(IWContext iwc, Object periodPK) throws java.rmi.RemoteException {
		Form form = new Form();
		form.setID("periodEditor");
		form.setStyleClass("adminForm");
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_SAVE));

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("period.period_editor_help", "Fill in the desired values and click 'Save'.")));
		section.add(helpLayer);

		Period period = getBusiness().getPeriod(periodPK);

		SelectorUtility util = new SelectorUtility();

		DropdownMenu seasons = (DropdownMenu) util.getSelectorFromIDOEntities(new DropdownMenu(PARAMETER_SEASON_PK), getBusiness().getAllSeasons());
		seasons.keepStatusOnAction(true);

		TextInput name = new TextInput(PARAMETER_NAME);
		name.keepStatusOnAction(true);

		DatePicker startDate = new DatePicker(PARAMETER_START_DATE);
		startDate.setDateFormatPattern("dd.MM.yyyy");
		startDate.keepStatusOnAction(true);

		DatePicker endDate = new DatePicker(PARAMETER_END_DATE);
		endDate.setDateFormatPattern("dd.MM.yyyy");
		endDate.keepStatusOnAction(true);

		TextInput weeks = new TextInput(PARAMETER_MINIMUM_WEEKS);
		weeks.keepStatusOnAction(true);

		TextInput costsAmount = new TextInput(PARAMETER_COST_AMOUNT);
		costsAmount.keepStatusOnAction(true);

		if (period != null) {
			Season season = period.getSeason();
			seasons.setSelectedElement(season.getPrimaryKey().toString());
			name.setContent(period.getName());
			if (period.getStartDate() != null) {
				startDate.setDate(period.getStartDate());
			}
			if (period.getEndDate() != null) {
				endDate.setDate(period.getEndDate());
			}
			weeks.setContent(String.valueOf(period.getMinimumWeeks()));
			if (period.getCostAmount() > 0) {
				costsAmount.setContent(String.valueOf((int) period.getCostAmount()));
			}

			form.add(new HiddenInput(PARAMETER_PERIOD_PK, periodPK.toString()));
		}

		Layer layer;
		Label label;

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("season", "Season"), seasons);
		layer.add(label);
		layer.add(seasons);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("period", "Period"), name);
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

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("minimum_weeks", "Minimum weeks"), weeks);
		layer.add(label);
		layer.add(weeks);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("costs_amount", "Costs amount"), costsAmount);
		layer.add(label);
		layer.add(costsAmount);
		section.add(layer);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");

		section.add(clearLayer);

		Heading1 heading = new Heading1(getResourceBundle().getLocalizedString("period.constants_heading", "Declare constants"));
		heading.setStyleClass("subHeader");
		form.add(heading);

		section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		section.setStyleClass("columnSection");
		form.add(section);

		helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("period.constant_declaration_help", "Fill in the desired values and click 'Save'.")));
		section.add(helpLayer);

		Layer headings = new Layer(Layer.DIV);
		headings.setStyleClass("formItem");
		headings.setStyleClass("columnHeader");
		section.add(headings);

		Span span = new Span();
		span.add(new Text(getResourceBundle().getLocalizedString("start_date", "Start date")));
		headings.add(span);

		span = new Span();
		span.add(new Text(getResourceBundle().getLocalizedString("end_date", "End date")));
		headings.add(span);

		for (int i = 0; i < FSKConstants.ALL_CONSTANTS.length; i++) {
			String type = FSKConstants.ALL_CONSTANTS[i];
			Constant constant = period != null ? getBusiness().getConstant(period, type) : null;

			startDate = new DatePicker(PARAMETER_START_DATE + type);
			startDate.setDateFormatPattern("dd.MM.yyyy");
			startDate.keepStatusOnAction(true);

			endDate = new DatePicker(PARAMETER_END_DATE + type);
			endDate.setDateFormatPattern("dd.MM.yyyy");
			endDate.keepStatusOnAction(true);

			if (constant != null) {
				if (constant.getStartDate() != null) {
					startDate.setDate(constant.getStartDate());
				}
				if (constant.getEndDate() != null) {
					endDate.setDate(constant.getEndDate());
				}
			}

			layer = new Layer(Layer.DIV);
			layer.setStyleClass("formItem");
			label = new Label();
			label.add(getResourceBundle().getLocalizedString("constant." + type, type));
			layer.add(label);
			layer.add(startDate);
			layer.add(endDate);
			section.add(layer);

		}

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

	private boolean savePeriod(IWContext iwc) {
		Object pk = iwc.getParameter(PARAMETER_PERIOD_PK);
		Object seasonPK = iwc.getParameter(PARAMETER_SEASON_PK);
		String name = iwc.isParameterSet(PARAMETER_NAME) ? iwc.getParameter(PARAMETER_NAME) : null;
		Date startDate = iwc.isParameterSet(PARAMETER_START_DATE) ? new IWTimestamp(iwc.getParameter(PARAMETER_START_DATE)).getDate() : null;
		Date endDate = iwc.isParameterSet(PARAMETER_END_DATE) ? new IWTimestamp(iwc.getParameter(PARAMETER_END_DATE)).getDate() : null;
		int minimumWeeks = iwc.isParameterSet(PARAMETER_MINIMUM_WEEKS) ? Integer.parseInt(iwc.getParameter(PARAMETER_MINIMUM_WEEKS)) : -1;
		float costsAmount = iwc.isParameterSet(PARAMETER_COST_AMOUNT) ? Float.parseFloat(iwc.getParameter(PARAMETER_COST_AMOUNT)) : -1;

		try {
			if (name == null) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("period_editor_error.must_set_name", "You have to provide a name"));
				return false;
			}
			if (seasonPK == null) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("period_editor_error.must_select_season", "You have to select a season"));
				return false;
			}
			if (startDate == null) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("period_editor_error.must_select_start_date", "You have to select a start date"));
				return false;
			}
			if (endDate == null) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("period_editor_error.must_select_end_date", "You have to select an end date"));
				return false;
			}
			if (minimumWeeks == -1) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("period_editor_error.must_set_minimum_weeks", "You have to set minimum weeks"));
				return false;
			}
			if (costsAmount == -1) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("period_editor_error.must_set_costs_amount", "You have to set costs amount"));
				return false;
			}

			Season season = getBusiness().getSeason(seasonPK);
			Period period = null;

			if (getBusiness().canStorePeriod(pk, season, startDate, endDate)) {
				period = getBusiness().storePeriod(pk, seasonPK, name, startDate, endDate, minimumWeeks, costsAmount);
			}
			else {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("period_editor.store_error", "You can not store a period that overlaps with already created periods."));
				return false;
			}

			for (int i = 0; i < FSKConstants.ALL_CONSTANTS.length; i++) {
				String type = FSKConstants.ALL_CONSTANTS[i];
				Date constantStartDate = iwc.isParameterSet(PARAMETER_START_DATE + type) ? new IWTimestamp(iwc.getParameter(PARAMETER_START_DATE + type)).getDate() : startDate;
				Date constantEndDate = iwc.isParameterSet(PARAMETER_END_DATE + type) ? new IWTimestamp(iwc.getParameter(PARAMETER_END_DATE + type)).getDate() : endDate;

				getBusiness().storeConstant(period, type, constantStartDate, constantEndDate);
			}

			return true;
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