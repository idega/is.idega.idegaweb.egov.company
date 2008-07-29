/*
 * $Id: CostsList.java,v 1.1 2008/07/29 12:57:41 anton Exp $
 * Created on Oct 9, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.ParticipantHolder;
import is.idega.idegaweb.egov.company.business.output.CostsAllocationWriter;
import is.idega.idegaweb.egov.company.data.Course;
import is.idega.idegaweb.egov.company.data.Division;
import is.idega.idegaweb.egov.company.data.Participant;
import is.idega.idegaweb.egov.company.data.Period;
import is.idega.idegaweb.egov.company.data.Season;

import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.FinderException;

import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.DownloadLink;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DateInput;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.util.SelectorUtility;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.IWTimestamp;

public class CostsList extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";

	public static final String PARAMETER_COMPANY_PK = "prm_company_pk";
	public static final String PARAMETER_SEASON_PK = "prm_season_pk";
	public static final String PARAMETER_PERIOD_PK = "prm_period_pk";
	public static final String PARAMETER_DIVISION_PK = "prm_division_pk";
	public static final String PARAMETER_FROM_DATE = "prm_from_date";
	public static final String PARAMETER_TO_DATE = "prm_to_date";

	private static final String PROPERTY_UNPAID_ENTRIES = "sprm_unpaid_entries";
	public static final String PROPERTY_ALL_ENTRIES = "sprm_all_entries";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_STORE = 2;
	private static final int ACTION_CANCEL = 3;

	public void present(IWContext iwc) {
		try {
			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showForm(iwc);
					break;

				case ACTION_STORE:
					store(iwc);
					showForm(iwc);
					break;

				case ACTION_CANCEL:
					cancel(iwc);
					showForm(iwc);
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

	private void showForm(IWContext iwc) throws RemoteException {
		Form form = new Form();
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));

		form.add(getNavigation(iwc));

		if (parseAction(iwc) != ACTION_CANCEL && iwc.isParameterSet(PARAMETER_FROM_DATE) && iwc.isParameterSet(PARAMETER_TO_DATE)) {
			form.add(getPrintouts(iwc));

			Table2 table = new Table2();
			table.setStyleClass("adminTable");
			table.setStyleClass("ruler");
			table.setWidth("100%");
			table.setCellpadding(0);
			table.setCellspacing(0);
			form.add(table);

			TableRowGroup group = table.createHeaderRowGroup();
			TableRow row = group.createRow();
			TableCell2 cell = row.createHeaderCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("type");
			cell.add(Text.getNonBrakingSpace());

			cell = row.createHeaderCell();
			cell.setStyleClass("numberOfEntries");
			cell.add(new Text(getResourceBundle().getLocalizedString("payment_list.number_of_participants", "Number of participants")));

			cell = row.createHeaderCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("totalAmount");
			cell.add(new Text(getResourceBundle().getLocalizedString("payment_list.total_amount", "Total amount")));

			group = table.createBodyRowGroup();

			IWTimestamp fromDate = new IWTimestamp(iwc.getParameter(PARAMETER_FROM_DATE));
			IWTimestamp toDate = new IWTimestamp(iwc.getParameter(PARAMETER_TO_DATE));
			toDate.addDays(1);
			Division division = iwc.isParameterSet(PARAMETER_DIVISION_PK) ? getBusiness().getDivision(iwc.getParameter(PARAMETER_DIVISION_PK)) : null;
			Season season = getBusiness().getSeason(iwc.getParameter(PARAMETER_SEASON_PK));
			Period period = getBusiness().getPeriod(iwc.getParameter(PARAMETER_PERIOD_PK));

			Map participants = getBusiness().getParticipants(iwc, getCompany(iwc), fromDate.getDate(), toDate.getDate(), season, period, division, iwc.getCurrentUser());
			iwc.setSessionAttribute(PROPERTY_ALL_ENTRIES, participants);

			Collection paidEntries = new ArrayList();
			float amount = 0;
			float unpaidAmount = 0;

			Collection unpaidEntries = new ArrayList();

			Iterator iterator = participants.keySet().iterator();
			while (iterator.hasNext()) {
				Division divisionGroup = (Division) iterator.next();
				Collection divisionParticipants = (Collection) participants.get(divisionGroup);

				Iterator iterator2 = divisionParticipants.iterator();
				while (iterator2.hasNext()) {
					ParticipantHolder holder = (ParticipantHolder) iterator2.next();
					User user = holder.getParticipant();
					Course course = holder.getCourse();
					period = course.getPeriod();

					Participant participant = getBusiness().getParticipant(user);
					if (participant.hasCostMarking(divisionGroup, period)) {
						paidEntries.add(participant);
						amount += participant.getCost(divisionGroup, period);
					}
					else {
						unpaidEntries.add(participant);
						unpaidAmount += period.getCostAmount();
					}
				}
			}

			NumberFormat format = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());
			format.setMinimumFractionDigits(0);

			int numberOfEntries = paidEntries.size();

			int totalNumberOfEntries = numberOfEntries;
			float totalAmount = amount;

			row = group.createRow();
			row.setStyleClass("odd");

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("type");
			cell.add(new Text(getResourceBundle().getLocalizedString("payment_list.paid_cost_entries", "Paid cost entries")));

			cell = row.createCell();
			cell.setStyleClass("numberOfEntries");
			cell.add(new Text(String.valueOf(numberOfEntries)));

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("totalAmount");
			cell.add(new Text(format.format(amount)));

			numberOfEntries = unpaidEntries.size();
			totalNumberOfEntries += numberOfEntries;
			totalAmount += unpaidAmount;

			row = group.createRow();
			row.setStyleClass("even");

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("type");
			cell.add(new Text(getResourceBundle().getLocalizedString("payment_list.unpaid_cost_entries", "Unpaid cost entries")));

			cell = row.createCell();
			cell.setStyleClass("numberOfEntries");
			cell.add(new Text(String.valueOf(numberOfEntries)));

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("totalAmount");
			cell.add(new Text(format.format(unpaidAmount)));

			group = table.createFooterRowGroup();
			row = group.createRow();

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("type");
			cell.add(new Text(getResourceBundle().getLocalizedString("payment_list.total_entries", "Total entries")));

			cell = row.createCell();
			cell.setStyleClass("numberOfEntries");
			cell.add(new Text(String.valueOf(totalNumberOfEntries)));

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("totalAmount");
			cell.add(new Text(format.format(totalAmount)));

			if (!unpaidEntries.isEmpty() && iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
				iwc.setSessionAttribute(PROPERTY_UNPAID_ENTRIES, unpaidEntries);

				Layer buttonLayer = new Layer();
				buttonLayer.setStyleClass("buttonLayer");
				form.add(buttonLayer);

				if (period != null) {
					SubmitButton store = new SubmitButton(getResourceBundle().getLocalizedString("payment_list.store", "Store"));
					store.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_STORE));
					store.setSubmitConfirm(getResourceBundle().getLocalizedString("payment_list.confirm_store", "Are you sure you want to mark the selected allocations as paid?"));
					buttonLayer.add(store);
				}

				SubmitButton cancel = new SubmitButton(getResourceBundle().getLocalizedString("payment_list.cancel", "Cancel"));
				cancel.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_CANCEL));
				buttonLayer.add(cancel);
			}
		}

		add(form);
	}

	protected Layer getNavigation(IWContext iwc) throws RemoteException {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("formSection");

		super.getParentPage().addJavascriptURL("/dwr/interface/FSKDWRUtil.js");
		super.getParentPage().addJavascriptURL(CoreConstants.DWR_ENGINE_SCRIPT);
		super.getParentPage().addJavascriptURL("/dwr/util.js");
		super.getParentPage().addJavascriptURL(getBundle().getResourcesVirtualPath() + "/js/navigation.js");

		Company company = getCompany(iwc);

		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			Collection collection = getCompanyBusiness(iwc).getActiveCompanies();

			DropdownMenu companies = new DropdownMenu(PARAMETER_COMPANY_PK);
			companies.addMenuElements(collection);
			companies.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("all_companies", "All companies"));
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

		SelectorUtility util = new SelectorUtility();

		DropdownMenu seasons = (DropdownMenu) util.getSelectorFromIDOEntities(new DropdownMenu(PARAMETER_SEASON_PK), getBusiness().getAllSeasons());
		seasons.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_season", "Select season"));
		seasons.setOnChange("changePeriods();");
		seasons.keepStatusOnAction(true);

		DropdownMenu periods = new DropdownMenu(PARAMETER_PERIOD_PK);
		periods.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_period", "Select period"));
		periods.setID(PARAMETER_PERIOD_PK);
		periods.keepStatusOnAction(true);

		if (iwc.isParameterSet(PARAMETER_SEASON_PK)) {
			Season season = getBusiness().getSeason(iwc.getParameter(PARAMETER_SEASON_PK));
			if (season != null) {
				periods.addMenuElements(getBusiness().getAllPeriods(season));
			}
		}

		DropdownMenu divisions = new DropdownMenu(PARAMETER_DIVISION_PK);
		divisions.addMenuElementFirst("", getResourceBundle().getLocalizedString("all_divisions", "All divisions"));
		divisions.keepStatusOnAction(true);
		if (company == null) {
			divisions.setDisabled(true);
		}

		if (company != null) {
			Collection collection = getBusiness().getDivisions(iwc, company, iwc.getCurrentUser());
			divisions.addMenuElements(collection);
		}

		IWTimestamp stamp = new IWTimestamp();

		DateInput fromDate = new DateInput(PARAMETER_FROM_DATE);
		fromDate.setYearRange(getBusiness().getInceptionYear(), stamp.getYear() + 1);
		fromDate.keepStatusOnAction(true);
		fromDate.setStyleClass("dateInput");

		DateInput toDate = new DateInput(PARAMETER_TO_DATE);
		toDate.setYearRange(getBusiness().getInceptionYear(), stamp.getYear() + 1);
		toDate.keepStatusOnAction(true);
		toDate.setStyleClass("dateInput");

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(getResourceBundle().getLocalizedString("division", "Division"), divisions);
		formItem.add(label);
		formItem.add(divisions);
		layer.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("season", "Season"), seasons);
		formItem.add(label);
		formItem.add(seasons);
		layer.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("period", "Period"), periods);
		formItem.add(label);
		formItem.add(periods);
		layer.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("from_date", "From date"), fromDate);
		formItem.add(label);
		formItem.add(fromDate);
		layer.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("to_date", "To date"), toDate);
		formItem.add(label);
		formItem.add(toDate);
		layer.add(formItem);

		SubmitButton fetch = new SubmitButton(getResourceBundle().getLocalizedString("get", "Get"));
		fetch.setStyleClass("indentedButton");
		fetch.setStyleClass("button");

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.add(fetch);
		layer.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		layer.add(clearLayer);

		return layer;
	}

	public Layer getPrintouts(IWContext iwc) {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("printIcons");

		layer.add(getXLSLink(iwc));

		return layer;
	}

	protected Link getXLSLink(IWContext iwc) {
		DownloadLink link = new DownloadLink(getBundle().getImage("xls.gif"));
		link.setStyleClass("xls");
		link.setTarget(Link.TARGET_NEW_WINDOW);
		link.maintainParameter(PARAMETER_COMPANY_PK, iwc);
		link.maintainParameter(PARAMETER_SEASON_PK, iwc);
		link.maintainParameter(PARAMETER_PERIOD_PK, iwc);
		link.maintainParameter(PARAMETER_DIVISION_PK, iwc);
		link.maintainParameter(PARAMETER_FROM_DATE, iwc);
		link.maintainParameter(PARAMETER_TO_DATE, iwc);
		link.setMediaWriterClass(CostsAllocationWriter.class);

		return link;
	}

	private void store(IWContext iwc) throws RemoteException {
		Period period = getBusiness().getPeriod(iwc.getParameter(PARAMETER_PERIOD_PK));
		Map entries = (Map) iwc.getSessionAttribute(PROPERTY_ALL_ENTRIES);
		getBusiness().markUnpaidCostEntries(entries, period);

		cancel(iwc);
	}

	private void cancel(IWContext iwc) {
		iwc.removeSessionAttribute(PROPERTY_UNPAID_ENTRIES);
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