/*
 * $Id: CostsAllocationWriter.java,v 1.1 2008/07/29 12:57:44 anton Exp $ Created on Mar 28, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business.output;

import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.FSKBusiness;
import is.idega.idegaweb.egov.company.business.ParticipantHolder;
import is.idega.idegaweb.egov.company.data.Course;
import is.idega.idegaweb.egov.company.data.Division;
import is.idega.idegaweb.egov.company.data.Participant;
import is.idega.idegaweb.egov.company.data.Period;
import is.idega.idegaweb.egov.company.data.Season;
import is.idega.idegaweb.egov.company.presentation.CostsList;
import is.idega.idegaweb.egov.company.presentation.PaymentList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.idega.business.IBOLookup;
import com.idega.company.data.Company;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.io.DownloadWriter;
import com.idega.io.MediaWritable;
import com.idega.io.MemoryFileBuffer;
import com.idega.io.MemoryInputStream;
import com.idega.io.MemoryOutputStream;
import com.idega.presentation.IWContext;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.Name;
import com.idega.util.text.TextSoap;

public class CostsAllocationWriter extends DownloadWriter implements MediaWritable {

	private MemoryFileBuffer buffer = null;
	private FSKBusiness business;
	private Locale locale;
	private IWResourceBundle iwrb;

	public CostsAllocationWriter() {
	}

	public void init(HttpServletRequest req, IWContext iwc) {
		try {
			this.locale = iwc.getApplicationSettings().getApplicationLocale();
			this.business = getBusiness(iwc);
			this.iwrb = iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(this.locale);

			Company company = iwc.isParameterSet(CostsList.PARAMETER_COMPANY_PK) && !iwc.getParameter(CostsList.PARAMETER_COMPANY_PK).equals("-1") ? getBusiness(iwc).getCompanyBusiness().getCompany((Object) iwc.getParameter(CostsList.PARAMETER_COMPANY_PK)) : null;
			Season season = iwc.isParameterSet(CostsList.PARAMETER_SEASON_PK) ? getBusiness(iwc).getSeason(iwc.getParameter(CostsList.PARAMETER_SEASON_PK)) : null;
			Period period = iwc.isParameterSet(CostsList.PARAMETER_PERIOD_PK) ? getBusiness(iwc).getPeriod(iwc.getParameter(CostsList.PARAMETER_PERIOD_PK)) : null;
			IWTimestamp fromDate = new IWTimestamp(iwc.getParameter(CostsList.PARAMETER_FROM_DATE));
			IWTimestamp toDate = new IWTimestamp(iwc.getParameter(CostsList.PARAMETER_TO_DATE));
			toDate.addDays(1);
			IWTimestamp stamp = new IWTimestamp();

			Map entries = (Map) iwc.getSessionAttribute(PaymentList.PROPERTY_ALL_ENTRIES);

			this.buffer = writeXLS(iwc, company, season, period, fromDate, toDate, entries);
			setAsDownload(iwc, iwrb.getLocalizedString("file_name.costs", "costs_overview") + (company != null ? "-" + company.getPersonalID() : "") + "-" + stamp.getDateString("ddMMyy") + ".xls", this.buffer.length());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getMimeType() {
		if (this.buffer != null) {
			return this.buffer.getMimeType();
		}
		return super.getMimeType();
	}

	public void writeTo(OutputStream out) throws IOException {
		if (this.buffer != null) {
			MemoryInputStream mis = new MemoryInputStream(this.buffer);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (mis.available() > 0) {
				baos.write(mis.read());
			}
			baos.writeTo(out);
		}
		else {
			System.err.println("buffer is null");
		}
	}

	public MemoryFileBuffer writeXLS(IWContext iwc, Company company, Season season, Period period, IWTimestamp fromDate, IWTimestamp toDate, Map entries) throws Exception {
		MemoryFileBuffer buffer = new MemoryFileBuffer();
		MemoryOutputStream mos = new MemoryOutputStream(buffer);

		HSSFWorkbook wb = new HSSFWorkbook();
		IWTimestamp stamp = new IWTimestamp();

		if (company != null) {
			write(iwc, wb, company, season, period, fromDate, toDate, entries, stamp, false);
		}
		else {
			Collection companies = getBusiness(iwc).getCompanyBusiness().getActiveCompanies();

			createAllTotalsSheet(iwc, wb, season, period, fromDate, toDate, companies, entries, stamp);

			Iterator iter = companies.iterator();
			while (iter.hasNext()) {
				company = (Company) iter.next();
				write(iwc, wb, company, season, period, fromDate, toDate, entries, stamp, true);
			}
		}

		wb.write(mos);
		buffer.setMimeType("application/x-msexcel");
		return buffer;
	}

	private void write(IWContext iwc, HSSFWorkbook wb, Company company, Season season, Period period, IWTimestamp fromDate, IWTimestamp toDate, Map entries, IWTimestamp stamp, boolean showOnlyTotals) throws Exception {
		createTotalsSheet(iwc, wb, company, season, period, fromDate, toDate, entries, stamp, showOnlyTotals);

		if (!showOnlyTotals) {
			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints((short) 11);
			HSSFCellStyle style = wb.createCellStyle();
			style.setFont(font);

			HSSFFont italicFont = wb.createFont();
			italicFont.setItalic(true);
			italicFont.setColor(HSSFColor.GREY_50_PERCENT.index);
			HSSFCellStyle italic = wb.createCellStyle();
			italic.setFont(italicFont);

			Iterator iterator = entries.keySet().iterator();
			while (iterator.hasNext()) {
				Division division = (Division) iterator.next();
				Collection participants = (Collection) entries.get(division);

				HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(division.getName()));
				sheet.setColumnWidth((short) 0, (short) (30 * 256));
				sheet.setColumnWidth((short) 1, (short) (14 * 256));
				sheet.setColumnWidth((short) 2, (short) (30 * 256));
				sheet.setColumnWidth((short) 3, (short) (14 * 256));
				sheet.setColumnWidth((short) 4, (short) (14 * 256));
				sheet.setColumnWidth((short) 5, (short) (20 * 256));
				sheet.setColumnWidth((short) 6, (short) (14 * 256));

				int cellRow = 0;
				HSSFRow row = sheet.createRow(cellRow++);
				short iCell = 0;

				int firstRow = 1;

				HSSFCell cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("registration_period", "Registration period"));
				cell.setCellStyle(style);

				cell = row.createCell(iCell++);
				cell.setCellValue(fromDate.getDateString("dd.MM.yy") + " - " + toDate.getDateString("dd.MM.yy"));
				firstRow++;

				cell = row.createCell((short) 5);
				cell.setCellValue(this.iwrb.getLocalizedString("printed_date", "Printed date"));
				cell.setCellStyle(style);

				cell = row.createCell((short) 6);
				cell.setCellValue(stamp.getLocaleDateAndTime(locale, IWTimestamp.SHORT, IWTimestamp.SHORT));

				iCell = 0;

				row = sheet.createRow(cellRow++);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("company", "Company"));
				cell.setCellStyle(style);

				cell = row.createCell(iCell++);
				cell.setCellValue(company.getName());
				firstRow++;

				iCell = 0;

				row = sheet.createRow(cellRow++);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("personal_id", "Personal ID"));
				cell.setCellStyle(style);

				cell = row.createCell(iCell++);
				cell.setCellValue(PersonalIDFormatter.format(company.getPersonalID(), locale));
				firstRow++;

				iCell = 0;

				row = sheet.createRow(cellRow++);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("bank_account", "Bank account"));
				cell.setCellStyle(style);

				cell = row.createCell(iCell++);
				cell.setCellValue(company.getBankAccount() != null ? company.getBankAccount() : "-");
				firstRow++;

				iCell = 0;

				row = sheet.createRow(cellRow++);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("season", "Season"));
				cell.setCellStyle(style);

				cell = row.createCell(iCell++);
				cell.setCellValue(season != null ? season.getName() : "-");
				firstRow++;

				iCell = 0;

				row = sheet.createRow(cellRow++);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("period", "Period"));
				cell.setCellStyle(style);

				cell = row.createCell(iCell++);
				cell.setCellValue(period != null ? period.getName() : "-");
				firstRow++;

				if (division != null) {
					row = sheet.createRow(cellRow++);
					iCell = 0;
					firstRow++;

					cell = row.createCell(iCell++);
					cell.setCellValue(this.iwrb.getLocalizedString("division", "Division"));
					cell.setCellStyle(style);

					cell = row.createCell(iCell++);
					cell.setCellValue(division.getName());
				}

				row = sheet.createRow(cellRow++);
				row = sheet.createRow(cellRow++);
				firstRow++;

				iCell = 0;
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("name", "Name"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("personal_id", "Personal ID"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("custodian_name", "Custodian name"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("custodian_personal_id", "Custodian personal ID"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("costs_amount", "Costs amount"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("costs_date", "Costs date"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("status", "Status"));
				cell.setCellStyle(style);

				Iterator iter = participants.iterator();
				while (iter.hasNext()) {
					iCell = 0;
					ParticipantHolder holder = (ParticipantHolder) iter.next();
					User user = holder.getParticipant();
					Course course = holder.getCourse();
					Period coursePeriod = course.getPeriod();
					User custodian = business.getCustodian(user);

					Participant participant = business.getParticipant(user);
					boolean active = true;
					Name name = new Name(user.getFirstName(), user.getMiddleName(), user.getLastName());
					float amount = participant.getCost(division, coursePeriod);
					IWTimestamp costDate = participant.getCostDate(division, coursePeriod) != null ? new IWTimestamp(participant.getCostDate(division, coursePeriod)) : null;

					row = sheet.createRow(cellRow++);

					cell = row.createCell(iCell++);
					cell.setCellValue(name.getName(this.locale, true));
					if (!active) {
						cell.setCellStyle(italic);
					}

					cell = row.createCell(iCell++);
					cell.setCellValue(user.getPersonalID());
					if (!active) {
						cell.setCellStyle(italic);
					}

					if (custodian != null) {
						Name custodianName = new Name(custodian.getFirstName(), custodian.getMiddleName(), custodian.getLastName());

						cell = row.createCell(iCell++);
						cell.setCellValue(custodianName.getName(this.locale, true));
						if (!active) {
							cell.setCellStyle(italic);
						}

						cell = row.createCell(iCell++);
						cell.setCellValue(custodian.getPersonalID());
						if (!active) {
							cell.setCellStyle(italic);
						}
					}
					else {
						iCell++;
						iCell++;
					}

					cell = row.createCell(iCell++);
					if (amount > 0) {
						cell.setCellValue(amount);
					}
					else {
						cell.setCellValue(coursePeriod.getCostAmount());
					}
					if (!active) {
						cell.setCellStyle(italic);
					}

					cell = row.createCell(iCell++);
					if (costDate != null) {
						cell.setCellValue(costDate.getDateString("dd.MM.yyyy"));
					}
					else {
						cell.setCellValue("");
					}
					if (!active) {
						cell.setCellStyle(italic);
					}

					cell = row.createCell(iCell++);
					if (!active) {
						cell.setCellValue(iwrb.getLocalizedString("status.removed"));
						cell.setCellStyle(italic);
					}
					else {
						cell.setCellValue(iwrb.getLocalizedString("status.active"));
					}
				}

				if (cellRow > firstRow) {
					row = sheet.createRow(cellRow);

					cell = row.createCell((short) 1);
					cell.setCellValue(this.iwrb.getLocalizedString("total", "Total"));
					cell.setCellStyle(style);

					cell = row.createCell((short) 2);
					cell.setCellFormula("SUM(C" + (firstRow + 1) + ":C" + cellRow + ")");
					cell.setCellStyle(style);
				}
			}
		}
	}

	public void createTotalsSheet(IWContext iwc, HSSFWorkbook wb, Company company, Season season, Period period, IWTimestamp fromDate, IWTimestamp toDate, Map entries, IWTimestamp stamp, boolean showOnlyTotals) throws Exception {
		HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(showOnlyTotals ? company.getName() : this.iwrb.getLocalizedString("total", "Total")));
		sheet.setColumnWidth((short) 0, (short) (30 * 256));
		sheet.setColumnWidth((short) 1, (short) (14 * 256));
		sheet.setColumnWidth((short) 2, (short) (22 * 256));
		sheet.setColumnWidth((short) 3, (short) (14 * 256));
		sheet.setColumnWidth((short) 4, (short) (22 * 256));

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 11);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);

		HSSFCellStyle rightStyle = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

		int cellRow = 0;
		HSSFRow row = sheet.createRow(cellRow++);
		short iCell = 0;

		int firstRow = 1;

		HSSFCell cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("costs_overview", "Costs overview"));
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue(this.iwrb.getLocalizedString("printed_date", "Printed date"));
		cell.setCellStyle(rightStyle);

		cell = row.createCell((short) 4);
		cell.setCellValue(stamp.getLocaleDateAndTime(locale, IWTimestamp.SHORT, IWTimestamp.SHORT));
		firstRow++;

		iCell = 0;

		row = sheet.createRow(cellRow++);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("allocation_period", "Allocation period"));
		cell.setCellStyle(style);

		cell = row.createCell(iCell++);
		cell.setCellValue(fromDate.getDateString("dd.MM.yy") + " - " + toDate.getDateString("dd.MM.yy"));
		firstRow++;

		iCell = 0;

		row = sheet.createRow(cellRow++);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("company", "Company"));
		cell.setCellStyle(style);

		cell = row.createCell(iCell++);
		cell.setCellValue(company.getName());
		firstRow++;

		iCell = 0;

		row = sheet.createRow(cellRow++);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("personal_id", "Personal ID"));
		cell.setCellStyle(style);

		cell = row.createCell(iCell++);
		cell.setCellValue(PersonalIDFormatter.format(company.getPersonalID(), locale));
		firstRow++;

		iCell = 0;

		row = sheet.createRow(cellRow++);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("bank_account", "Bank account"));
		cell.setCellStyle(style);

		cell = row.createCell(iCell++);
		cell.setCellValue(company.getBankAccount() != null ? company.getBankAccount() : "-");
		firstRow++;

		iCell = 0;

		row = sheet.createRow(cellRow++);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("season", "Season"));
		cell.setCellStyle(style);

		cell = row.createCell(iCell++);
		cell.setCellValue(season != null ? season.getName() : "-");
		firstRow++;

		iCell = 0;

		row = sheet.createRow(cellRow++);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("period", "Period"));
		cell.setCellStyle(style);

		cell = row.createCell(iCell++);
		cell.setCellValue(period != null ? period.getName() : "-");
		firstRow++;

		row = sheet.createRow(cellRow++);
		row = sheet.createRow(cellRow++);
		firstRow++;

		cell = row.createCell((short) 1);
		cell.setCellValue(this.iwrb.getLocalizedString("payment_list.paid_cost_entries", "Paid cost entries"));
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue(this.iwrb.getLocalizedString("payment_list.unpaid_cost_entries", "Unpaid cost entries"));
		cell.setCellStyle(style);

		row = sheet.createRow(cellRow++);
		firstRow++;

		iCell = 0;
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("division", "Division"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_list.count", "Count"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_list.total_amount", "Total amount"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_list.count", "Count"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_list.total_amount", "Total amount"));
		cell.setCellStyle(style);

		float totalAmount = 0;

		Iterator iterator = entries.keySet().iterator();
		while (iterator.hasNext()) {
			Division division = (Division) iterator.next();
			Company divisionCompany = division.getCompany();
			if (!divisionCompany.equals(company)) {
				continue;
			}

			Collection participants = (Collection) entries.get(division);

			int paidEntries = 0;
			float amount = 0;

			int unpaidEntries = 0;
			float unpaidAmount = 0;

			Iterator iterator2 = participants.iterator();
			while (iterator2.hasNext()) {
				ParticipantHolder holder = (ParticipantHolder) iterator2.next();
				Course course = holder.getCourse();
				Period coursePeriod = course.getPeriod();

				Participant participant = holder.getParticipant();
				if (participant.hasCostMarking(division, coursePeriod)) {
					paidEntries++;
					amount += coursePeriod.getCostAmount();
					totalAmount += coursePeriod.getCostAmount();
				}
				else {
					unpaidEntries++;
					unpaidAmount += coursePeriod.getCostAmount();
					totalAmount += coursePeriod.getCostAmount();
				}
			}

			iCell = 0;
			row = sheet.createRow(cellRow++);

			cell = row.createCell(iCell++);
			cell.setCellValue(division.getName());

			cell = row.createCell(iCell++);
			cell.setCellValue(paidEntries);

			cell = row.createCell(iCell++);
			cell.setCellValue(amount);

			cell = row.createCell(iCell++);
			cell.setCellValue(unpaidEntries);

			cell = row.createCell(iCell++);
			cell.setCellValue(unpaidAmount);
		}

		if (cellRow > firstRow) {
			row = sheet.createRow(cellRow);

			cell = row.createCell((short) 0);
			cell.setCellValue(this.iwrb.getLocalizedString("total", "Total"));
			cell.setCellStyle(style);

			cell = row.createCell((short) 1);
			cell.setCellFormula("SUM(B" + (firstRow + 1) + ":B" + cellRow + ")");
			cell.setCellStyle(style);

			cell = row.createCell((short) 1);
			cell.setCellFormula("SUM(C" + (firstRow + 1) + ":C" + cellRow + ")");
			cell.setCellStyle(style);

			cell = row.createCell((short) 1);
			cell.setCellFormula("SUM(D" + (firstRow + 1) + ":D" + cellRow + ")");
			cell.setCellStyle(style);

			cell = row.createCell((short) 1);
			cell.setCellFormula("SUM(E" + (firstRow + 1) + ":E" + cellRow + ")");
			cell.setCellStyle(style);
		}

		row = sheet.createRow(cellRow + 2);

		cell = row.createCell((short) 1);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_list.total_amount", "Total amount"));

		cell = row.createCell((short) 2);
		cell.setCellValue(totalAmount);

	}

	public void createAllTotalsSheet(IWContext iwc, HSSFWorkbook wb, Season season, Period period, IWTimestamp fromDate, IWTimestamp toDate, Collection companies, Map entries, IWTimestamp stamp) throws Exception {
		HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(this.iwrb.getLocalizedString("all_companies", "All companies")));
		sheet.setColumnWidth((short) 0, (short) (30 * 256));
		sheet.setColumnWidth((short) 1, (short) (14 * 256));
		sheet.setColumnWidth((short) 2, (short) (22 * 256));
		sheet.setColumnWidth((short) 3, (short) (14 * 256));
		sheet.setColumnWidth((short) 4, (short) (22 * 256));

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 11);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);

		HSSFCellStyle rightStyle = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

		int cellRow = 0;
		HSSFRow row = sheet.createRow(cellRow++);
		short iCell = 0;

		int firstRow = 1;

		HSSFCell cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("costs_overview", "Costs overview"));
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue(this.iwrb.getLocalizedString("printed_date", "Printed date"));
		cell.setCellStyle(rightStyle);

		cell = row.createCell((short) 6);
		cell.setCellValue(stamp.getLocaleDateAndTime(locale, IWTimestamp.SHORT, IWTimestamp.SHORT));
		firstRow++;

		iCell = 0;

		row = sheet.createRow(cellRow++);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("allocation_period", "Allocation period"));
		cell.setCellStyle(style);

		cell = row.createCell(iCell++);
		cell.setCellValue(fromDate.getDateString("dd.MM.yy") + " - " + toDate.getDateString("dd.MM.yy"));
		firstRow++;

		iCell = 0;

		row = sheet.createRow(cellRow++);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("season", "Season"));
		cell.setCellStyle(style);

		cell = row.createCell(iCell++);
		cell.setCellValue(season != null ? season.getName() : "-");
		firstRow++;

		iCell = 0;

		row = sheet.createRow(cellRow++);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("period", "Period"));
		cell.setCellStyle(style);

		cell = row.createCell(iCell++);
		cell.setCellValue(period != null ? period.getName() : "-");
		firstRow++;

		row = sheet.createRow(cellRow++);
		row = sheet.createRow(cellRow++);
		firstRow++;

		cell = row.createCell((short) 1);
		cell.setCellValue(this.iwrb.getLocalizedString("payment_list.paid_cost_entries", "Paid cost entries"));
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue(this.iwrb.getLocalizedString("payment_list.unpaid_cost_entries", "Unpaid cost entries"));
		cell.setCellStyle(style);

		row = sheet.createRow(cellRow++);
		firstRow++;

		iCell = 0;
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("company", "Company"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_list.count", "Count"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_list.total_amount", "Total amount"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_list.count", "Count"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_list.total_amount", "Total amount"));
		cell.setCellStyle(style);

		float totalAmount = 0;

		Iterator iter = companies.iterator();
		while (iter.hasNext()) {
			Company company = (Company) iter.next();
			Iterator iterator = entries.keySet().iterator();

			int paidEntries = 0;
			float amount = 0;

			int unpaidEntries = 0;
			float unpaidAmount = 0;

			while (iterator.hasNext()) {
				Division division = (Division) iterator.next();
				Company divisionCompany = division.getCompany();
				if (!divisionCompany.equals(company)) {
					continue;
				}

				Collection participants = (Collection) entries.get(division);

				Iterator iterator2 = participants.iterator();
				while (iterator2.hasNext()) {
					ParticipantHolder holder = (ParticipantHolder) iterator2.next();
					Course course = holder.getCourse();
					Period coursePeriod = course.getPeriod();

					Participant participant = holder.getParticipant();
					if (participant.hasCostMarking(division, coursePeriod)) {
						paidEntries++;
						amount += coursePeriod.getCostAmount();
						totalAmount += amount;
					}
					else {
						unpaidEntries++;
						unpaidAmount += coursePeriod.getCostAmount();
						totalAmount += amount;
					}
				}
			}

			iCell = 0;
			row = sheet.createRow(cellRow++);

			cell = row.createCell(iCell++);
			cell.setCellValue(company.getName());

			cell = row.createCell(iCell++);
			cell.setCellValue(paidEntries);

			cell = row.createCell(iCell++);
			cell.setCellValue(amount);

			cell = row.createCell(iCell++);
			cell.setCellValue(unpaidEntries);

			cell = row.createCell(iCell++);
			cell.setCellValue(unpaidAmount);
		}

		if (cellRow > firstRow) {
			row = sheet.createRow(cellRow);

			cell = row.createCell((short) 0);
			cell.setCellValue(this.iwrb.getLocalizedString("total", "Total"));
			cell.setCellStyle(style);

			cell = row.createCell((short) 1);
			cell.setCellFormula("SUM(B" + (firstRow + 1) + ":B" + cellRow + ")");
			cell.setCellStyle(style);

			cell = row.createCell((short) 2);
			cell.setCellFormula("SUM(C" + (firstRow + 1) + ":C" + cellRow + ")");
			cell.setCellStyle(style);

			cell = row.createCell((short) 3);
			cell.setCellFormula("SUM(D" + (firstRow + 1) + ":D" + cellRow + ")");
			cell.setCellStyle(style);

			cell = row.createCell((short) 4);
			cell.setCellFormula("SUM(E" + (firstRow + 1) + ":E" + cellRow + ")");
			cell.setCellStyle(style);
		}

		row = sheet.createRow(cellRow + 2);

		cell = row.createCell((short) 1);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_list.total_amount", "Total amount"));

		cell = row.createCell((short) 2);
		cell.setCellValue(totalAmount);

	}

	private FSKBusiness getBusiness(IWApplicationContext iwc) throws RemoteException {
		return (FSKBusiness) IBOLookup.getServiceInstance(iwc, FSKBusiness.class);
	}
}