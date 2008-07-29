/*
 * $Id: TotalPaymentsList.java,v 1.1 2008/07/29 12:57:41 anton Exp $
 * Created on Oct 9, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.company.business.CompanyHolder;
import is.idega.idegaweb.egov.company.business.output.TotalPaymentWriter;
import is.idega.idegaweb.egov.company.data.Period;
import is.idega.idegaweb.egov.company.data.Season;

import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.util.SelectorUtility;
import com.idega.util.CoreConstants;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;

public class TotalPaymentsList extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";

	public static final String PARAMETER_COMPANY_PK = "prm_company_pk";
	public static final String PARAMETER_SEASON_PK = "prm_season_pk";
	public static final String PARAMETER_PERIOD_PK = "prm_period_pk";
	public static final String PARAMETER_FROM_DATE = "prm_from_date";
	public static final String PARAMETER_TO_DATE = "prm_to_date";

	public static final String PROPERTY_ALL_ENTRIES = "sprm_total_entries";

	private static final int ACTION_VIEW = 1;

	public void present(IWContext iwc) {
		try {
			switch (parseAction(iwc)) {
				case ACTION_VIEW:
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

	private Collection getData(IWContext iwc) throws RemoteException {
		Collection coll = new ArrayList();

		Company company = getCompany(iwc);
		Collection companies = new ArrayList();
		if (company != null) {
			companies.add(company);
		}
		else {
			companies = getBusiness().getCompanyBusiness().getActiveCompanies();
		}

		IWTimestamp fromDate = new IWTimestamp(iwc.getParameter(PARAMETER_FROM_DATE));
		IWTimestamp toDate = new IWTimestamp(iwc.getParameter(PARAMETER_TO_DATE));
		toDate.addDays(1);
		Season season = getBusiness().getSeason(iwc.getParameter(PARAMETER_SEASON_PK));
		Period period = getBusiness().getPeriod(iwc.getParameter(PARAMETER_PERIOD_PK));

		Iterator iter = companies.iterator();
		while (iter.hasNext()) {
			company = (Company) iter.next();

			CompanyHolder holder = new CompanyHolder();
			holder.setCompany(company);
			holder = getBusiness().getTotalAllocationValues(holder, season, period, fromDate.getDate(), toDate.getDate());
			holder = getBusiness().getTotalCostsValues(iwc, holder, season, period, fromDate.getDate(), toDate.getDate(), iwc.getCurrentUser());

			coll.add(holder);
		}

		return coll;
	}

	private void showForm(IWContext iwc) throws RemoteException {
		Form form = new Form();
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));

		form.add(getNavigation(iwc));

		if (iwc.isParameterSet(PARAMETER_FROM_DATE) && iwc.isParameterSet(PARAMETER_TO_DATE)) {
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
			cell.setStyleClass("count");
			cell.add(Text.getNonBrakingSpace());

			cell = row.createHeaderCell();
			cell.setStyleClass("company");
			cell.add(new Text(getResourceBundle().getLocalizedString("company", "Company")));

			cell = row.createHeaderCell();
			cell.setStyleClass("personalID");
			cell.add(new Text(getResourceBundle().getLocalizedString("personal_id", "Personal ID")));

			cell = row.createHeaderCell();
			cell.setStyleClass("bankAccount");
			cell.add(new Text(getResourceBundle().getLocalizedString("bank_account", "Bank account")));

			cell = row.createHeaderCell();
			cell.setStyleClass("costsCount");
			cell.add(new Text(getResourceBundle().getLocalizedString("total_payment_list.costs_count", "Costs count")));

			cell = row.createHeaderCell();
			cell.setStyleClass("costsAmount");
			cell.add(new Text(getResourceBundle().getLocalizedString("total_payment_list.costs_amount", "Costs amount")));

			cell = row.createHeaderCell();
			cell.setStyleClass("allocationCount");
			cell.add(new Text(getResourceBundle().getLocalizedString("total_payment_list.allocation_count", "Allocation count")));

			cell = row.createHeaderCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("allocationAmount");
			cell.add(new Text(getResourceBundle().getLocalizedString("total_payment_list.allocation_amount", "Allocation amount")));

			group = table.createBodyRowGroup();

			Collection companies = getData(iwc);
			iwc.setSessionAttribute(PROPERTY_ALL_ENTRIES, companies);

			NumberFormat format = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());
			format.setMinimumFractionDigits(0);

			int costsAmount = 0;
			int costsCount = 0;
			int allocationAmount = 0;
			int allocationCount = 0;

			int iRow = 1;
			Iterator iter = companies.iterator();
			while (iter.hasNext()) {
				row = group.createRow();

				CompanyHolder holder = (CompanyHolder) iter.next();
				Company company = holder.getCompany();

				costsAmount += holder.getCostsAmount();
				costsCount += holder.getCostsCount();
				allocationAmount += holder.getAllocationAmount();
				allocationCount += holder.getAllocationCount();

				if (iRow % 2 == 0) {
					row.setStyleClass("even");
				}
				else {
					row.setStyleClass("odd");
				}

				cell = row.createCell();
				cell.setStyleClass("firstColumn");
				cell.setStyleClass("count");
				cell.add(new Text(String.valueOf(iRow)));

				cell = row.createCell();
				cell.setStyleClass("company");
				cell.add(new Text(company.getName()));

				cell = row.createCell();
				cell.setStyleClass("personalID");
				cell.add(new Text(PersonalIDFormatter.format(company.getPersonalID(), iwc.getCurrentLocale())));

				cell = row.createCell();
				cell.setStyleClass("bankAccount");
				cell.add(new Text(company.getBankAccount()));

				cell = row.createCell();
				cell.setStyleClass("costsCount");
				cell.add(new Text(String.valueOf(holder.getCostsCount())));

				cell = row.createCell();
				cell.setStyleClass("costsAmount");
				cell.add(new Text(format.format(holder.getCostsAmount())));

				cell = row.createCell();
				cell.setStyleClass("allocationCount");
				cell.add(new Text(String.valueOf(holder.getAllocationCount())));

				cell = row.createCell();
				cell.setStyleClass("allocationAmount");
				cell.add(new Text(format.format(holder.getAllocationAmount())));

				iRow++;
			}

			group = table.createFooterRowGroup();
			row = group.createRow();

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("count");
			cell.setColumnSpan(4);
			cell.add(new Text(getResourceBundle().getLocalizedString("total", "Total")));

			cell = row.createCell();
			cell.setStyleClass("costsCount");
			cell.add(new Text(String.valueOf(costsCount)));

			cell = row.createCell();
			cell.setStyleClass("costsAmount");
			cell.add(new Text(format.format(costsAmount)));

			cell = row.createCell();
			cell.setStyleClass("allocationCount");
			cell.add(new Text(String.valueOf(allocationCount)));

			cell = row.createCell();
			cell.setStyleClass("allocationAmount");
			cell.add(new Text(format.format(allocationAmount)));
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

		Collection collection = getCompanyBusiness(iwc).getActiveCompanies();

		DropdownMenu companies = new DropdownMenu(PARAMETER_COMPANY_PK);
		companies.addMenuElements(collection);
		companies.addMenuElementFirst("", getResourceBundle().getLocalizedString("all_companies", "All companies"));
		companies.keepStatusOnAction(true);

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(getResourceBundle().getLocalizedString("company", "Company"), companies);
		formItem.add(label);
		formItem.add(companies);
		layer.add(formItem);

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

		IWTimestamp stamp = new IWTimestamp();

		DateInput fromDate = new DateInput(PARAMETER_FROM_DATE);
		fromDate.setYearRange(getBusiness().getInceptionYear(), stamp.getYear() + 1);
		fromDate.keepStatusOnAction(true);
		fromDate.setStyleClass("dateInput");

		DateInput toDate = new DateInput(PARAMETER_TO_DATE);
		toDate.setYearRange(getBusiness().getInceptionYear(), stamp.getYear() + 1);
		toDate.keepStatusOnAction(true);
		toDate.setStyleClass("dateInput");

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
		link.maintainParameter(PARAMETER_SEASON_PK, iwc);
		link.maintainParameter(PARAMETER_PERIOD_PK, iwc);
		link.maintainParameter(PARAMETER_FROM_DATE, iwc);
		link.maintainParameter(PARAMETER_TO_DATE, iwc);
		link.setMediaWriterClass(TotalPaymentWriter.class);

		return link;
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