/*
 * $Id: PaymentAllocationWriter.java,v 1.1 2008/07/29 12:57:44 anton Exp $ Created on Mar 28, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business.output;

import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.CourseComparator;
import is.idega.idegaweb.egov.company.business.FSKBusiness;
import is.idega.idegaweb.egov.company.data.Course;
import is.idega.idegaweb.egov.company.data.Division;
import is.idega.idegaweb.egov.company.data.Participant;
import is.idega.idegaweb.egov.company.data.ParticipantDiscount;
import is.idega.idegaweb.egov.company.data.PaymentAllocation;
import is.idega.idegaweb.egov.company.data.Period;
import is.idega.idegaweb.egov.company.data.Season;
import is.idega.idegaweb.egov.company.presentation.PaymentList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.Name;
import com.idega.util.text.TextSoap;

public class PaymentAllocationWriter extends DownloadWriter implements MediaWritable {

	private MemoryFileBuffer buffer = null;
	private FSKBusiness business;
	private Locale locale;
	private IWResourceBundle iwrb;

	public PaymentAllocationWriter() {
	}

	public void init(HttpServletRequest req, IWContext iwc) {
		try {
			this.locale = iwc.getApplicationSettings().getApplicationLocale();
			this.business = getBusiness(iwc);
			this.iwrb = iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(this.locale);

			Company company = iwc.isParameterSet(PaymentList.PARAMETER_COMPANY_PK) && !iwc.getParameter(PaymentList.PARAMETER_COMPANY_PK).equals("-1") ? getBusiness(iwc).getCompanyBusiness().getCompany((Object) iwc.getParameter(PaymentList.PARAMETER_COMPANY_PK)) : null;
			Season season = iwc.isParameterSet(PaymentList.PARAMETER_SEASON_PK) ? getBusiness(iwc).getSeason(iwc.getParameter(PaymentList.PARAMETER_SEASON_PK)) : null;
			Period period = iwc.isParameterSet(PaymentList.PARAMETER_PERIOD_PK) ? getBusiness(iwc).getPeriod(iwc.getParameter(PaymentList.PARAMETER_PERIOD_PK)) : null;
			IWTimestamp fromDate = new IWTimestamp(iwc.getParameter(PaymentList.PARAMETER_FROM_DATE));
			IWTimestamp toDate = new IWTimestamp(iwc.getParameter(PaymentList.PARAMETER_TO_DATE));
			toDate.addDays(1);
			IWTimestamp stamp = new IWTimestamp();

			Map entries = (Map) iwc.getSessionAttribute(PaymentList.PROPERTY_ALL_ENTRIES);

			this.buffer = writeXLS(iwc, company, season, period, fromDate, toDate, entries);
			setAsDownload(iwc, iwrb.getLocalizedString("file_name.allocations", "allocations_overview") + (company != null ? "-" + company.getPersonalID() : "") + "-" + stamp.getDateString("ddMMyy") + ".xls", this.buffer.length());
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

			createAllTotalsSheet(iwc, wb, companies, season, period, fromDate, toDate, stamp);

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
		createTotalsSheet(iwc, wb, company, season, period, fromDate, toDate, stamp, showOnlyTotals);

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
				Collection allocations = (Collection) entries.get(division);

				HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(division.getName()));
				sheet.setColumnWidth((short) 0, (short) (30 * 256));
				sheet.setColumnWidth((short) 1, (short) (14 * 256));
				sheet.setColumnWidth((short) 2, (short) (30 * 256));
				sheet.setColumnWidth((short) 3, (short) (14 * 256));
				sheet.setColumnWidth((short) 4, (short) (30 * 256));
				sheet.setColumnWidth((short) 5, (short) (14 * 256));
				sheet.setColumnWidth((short) 6, (short) (14 * 256));
				sheet.setColumnWidth((short) 7, (short) (20 * 256));
				sheet.setColumnWidth((short) 8, (short) (10 * 256));
				sheet.setColumnWidth((short) 9, (short) (20 * 256));

				int cellRow = 0;
				HSSFRow row = sheet.createRow(cellRow++);
				short iCell = 0;

				int firstRow = 1;

				HSSFCell cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("allocation_period", "Allocation period"));
				cell.setCellStyle(style);

				cell = row.createCell(iCell++);
				cell.setCellValue(fromDate.getDateString("dd.MM.yy") + " - " + toDate.getDateString("dd.MM.yy"));
				firstRow++;

				cell = row.createCell((short) 8);
				cell.setCellValue(this.iwrb.getLocalizedString("printed_date", "Printed date"));
				cell.setCellStyle(style);

				cell = row.createCell((short) 9);
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
				cell.setCellValue(this.iwrb.getLocalizedString("course", "Course"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("price", "Price"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("parent_allocation", "Parent allocation"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("allocation_date", "Allocation date"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("status", "Status"));
				cell.setCellStyle(style);
				cell = row.createCell(iCell++);
				cell.setCellValue(this.iwrb.getLocalizedString("payment_date", "Payment date"));
				cell.setCellStyle(style);

				Iterator iter = allocations.iterator();
				while (iter.hasNext()) {
					iCell = 0;
					PaymentAllocation allocation = (PaymentAllocation) iter.next();
					User user = allocation.getUser();
					Course course = allocation.getCourse();
					Participant participant = business.getParticipant(user);
					boolean active = participant.isActive(course);
					Name name = new Name(user.getFirstName(), user.getMiddleName(), user.getLastName());
					ParticipantDiscount discount = getBusiness(iwc).getDiscount(participant, course);
					User custodian = business.getCustodian(user);

					float price = course.getPrice();
					if (discount != null) {
						if (discount.isAmount()) {
							price = price - discount.getDiscount();
						}
						else if (discount.isPercentage()) {
							price = price * (1 - discount.getDiscount());
						}
					}

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
					cell.setCellValue(course.getName());
					if (!active) {
						cell.setCellStyle(italic);
					}

					cell = row.createCell(iCell++);
					cell.setCellValue(price);
					if (!active) {
						cell.setCellStyle(italic);
					}

					cell = row.createCell(iCell++);
					cell.setCellValue(allocation.getAmount());
					if (!active) {
						cell.setCellStyle(italic);
					}

					cell = row.createCell(iCell++);
					if (allocation.getEntryDate() != null) {
						cell.setCellValue(new IWTimestamp(allocation.getEntryDate()).getDateString("dd.MM.yyyy"));
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

					cell = row.createCell(iCell++);
					if (allocation.getPaymentDate() != null) {
						cell.setCellValue(new IWTimestamp(allocation.getPaymentDate()).getDateString("dd.MM.yyyy"));
					}
					else {
						cell.setCellValue("");
					}
					if (!active) {
						cell.setCellStyle(italic);
					}
				}

				if (cellRow > firstRow) {
					row = sheet.createRow(cellRow);

					cell = row.createCell((short) 2);
					cell.setCellValue(this.iwrb.getLocalizedString("total", "Total"));
					cell.setCellStyle(style);

					cell = row.createCell((short) 4);
					cell.setCellFormula("SUM(E" + (firstRow + 1) + ":E" + cellRow + ")");
					cell.setCellStyle(style);
				}
			}
		}
	}

	public void createTotalsSheet(IWContext iwc, HSSFWorkbook wb, Company company, Season season, Period period, IWTimestamp fromDate, IWTimestamp toDate, IWTimestamp stamp, boolean showOnlyTotals) throws Exception {
		HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(showOnlyTotals ? company.getName() : this.iwrb.getLocalizedString("total", "Total")));
		sheet.setColumnWidth((short) 0, (short) (30 * 256));
		sheet.setColumnWidth((short) 1, (short) (20 * 256));
		sheet.setColumnWidth((short) 2, (short) (20 * 256));
		sheet.setColumnWidth((short) 3, (short) (30 * 256));
		sheet.setColumnWidth((short) 4, (short) (14 * 256));
		sheet.setColumnWidth((short) 5, (short) (14 * 256));
		sheet.setColumnWidth((short) 6, (short) (14 * 256));

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 11);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);

		HSSFCellStyle rightStyle = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

		HSSFFont totalFont = wb.createFont();
		totalFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		totalFont.setFontHeightInPoints((short) 10);
		HSSFCellStyle totalStyle = wb.createCellStyle();
		totalStyle.setFont(totalFont);
		totalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		totalStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);

		int cellRow = 0;
		HSSFRow row = sheet.createRow(cellRow++);
		short iCell = 0;

		int firstRow = 1;

		HSSFCell cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("allocation_overview", "Allocation overview"));
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

		iCell = 0;
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("division", "Division"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("group", "Group"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("sub_group", "Sub group"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("course", "Course"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("estimate", "Estimate"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("registrations", "Registrations"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("parent_allocation", "Parent allocation"));
		cell.setCellStyle(style);

		List courses = null;
		try {
			courses = new ArrayList(business.getCourses(season != null ? season.getPrimaryKey() : null, period != null ? period.getPrimaryKey() : null, company.getPrimaryKey(), null, null, null));
		}
		catch (RemoteException rex) {
			courses = new ArrayList();
		}
		Collections.sort(courses, new CourseComparator(iwc, iwc.getCurrentLocale()));

		if (courses != null) {
			Group currentDivision = null;
			int currentFirstRow = 0;

			Iterator iter = courses.iterator();
			while (iter.hasNext()) {
				iCell = 0;
				Course course = (Course) iter.next();
				Group division = business.getDivision(course);
				if (!hasDivisionPermission(iwc, division)) {
					continue;
				}

				if (currentDivision == null) {
					currentDivision = division;
					currentFirstRow = cellRow;
				}
				else if ((currentDivision != null && !currentDivision.equals(division))) {
					row = sheet.createRow(cellRow++);

					cell = row.createCell((short) 0);
					cell.setCellValue(currentDivision.getName());
					cell.setCellStyle(totalStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue(this.iwrb.getLocalizedString("total", "Total"));
					cell.setCellStyle(totalStyle);

					for (int i = 2; i < 7; i++) {
						cell = row.createCell((short) i);
						cell.setCellStyle(totalStyle);
					}

					cell = row.createCell((short) 7);
					cell.setCellFormula("SUM(G" + (currentFirstRow + 1) + ":G" + (cellRow) + ")");
					cell.setCellStyle(totalStyle);

					currentDivision = division;
					currentFirstRow = cellRow;
				}

				Group divisionGroup = business.getGroup(course);
				Group subGroup = business.getSubGroup(course);

				row = sheet.createRow(cellRow++);

				cell = row.createCell(iCell++);
				cell.setCellValue(division.getName());

				cell = row.createCell(iCell++);
				if (divisionGroup != null) {
					cell.setCellValue(divisionGroup.getName());
				}

				cell = row.createCell(iCell++);
				if (subGroup != null) {
					cell.setCellValue(subGroup.getName());
				}

				cell = row.createCell(iCell++);
				cell.setCellValue(course.getName());

				cell = row.createCell(iCell++);
				cell.setCellValue((course.getMaxMale() + course.getMaxFemale()));

				cell = row.createCell(iCell++);
				cell.setCellValue(business.getNumberOfRegistrations(course));

				cell = row.createCell(iCell++);
				cell.setCellValue(business.getAllocation(course, fromDate.getDate(), toDate.getDate()));

				if (!iter.hasNext()) {
					row = sheet.createRow(cellRow++);

					cell = row.createCell((short) 0);
					cell.setCellValue(currentDivision.getName());
					cell.setCellStyle(totalStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue(this.iwrb.getLocalizedString("total", "Total"));
					cell.setCellStyle(totalStyle);

					for (int i = 2; i < 7; i++) {
						cell = row.createCell((short) i);
						cell.setCellStyle(totalStyle);
					}

					cell = row.createCell((short) 7);
					cell.setCellFormula("SUM(G" + (currentFirstRow + 1) + ":G" + (cellRow) + ")");
					cell.setCellStyle(totalStyle);
				}
			}

			if (cellRow > firstRow) {
				row = sheet.createRow(cellRow);

				cell = row.createCell((short) 0);
				cell.setCellValue(this.iwrb.getLocalizedString("total", "Total"));
				cell.setCellStyle(style);

				cell = row.createCell((short) 4);
				cell.setCellFormula("SUM(E" + (firstRow + 1) + ":E" + cellRow + ")");
				cell.setCellStyle(style);

				cell = row.createCell((short) 5);
				cell.setCellFormula("SUM(F" + (firstRow + 1) + ":F" + cellRow + ")");
				cell.setCellStyle(style);

				cell = row.createCell((short) 6);
				cell.setCellFormula("SUM(G" + (firstRow + 1) + ":G" + cellRow + ")");
				cell.setCellStyle(style);

				cell = row.createCell((short) 7);
				cell.setCellFormula("SUM(H" + (firstRow + 1) + ":H" + cellRow + ")");
				cell.setCellStyle(style);
			}
		}
	}

	public void createAllTotalsSheet(IWContext iwc, HSSFWorkbook wb, Collection companies, Season season, Period period, IWTimestamp fromDate, IWTimestamp toDate, IWTimestamp stamp) throws Exception {
		HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(this.iwrb.getLocalizedString("all_companies", "All companies")));
		sheet.setColumnWidth((short) 0, (short) (30 * 256));
		sheet.setColumnWidth((short) 1, (short) (14 * 256));
		sheet.setColumnWidth((short) 2, (short) (18 * 256));
		sheet.setColumnWidth((short) 3, (short) (14 * 256));
		sheet.setColumnWidth((short) 4, (short) (18 * 256));

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 11);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);

		HSSFFont totalFont = wb.createFont();
		totalFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		totalFont.setFontHeightInPoints((short) 10);
		HSSFCellStyle totalStyle = wb.createCellStyle();
		totalStyle.setFont(totalFont);
		totalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		totalStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);

		HSSFCellStyle rightStyle = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

		int cellRow = 0;
		HSSFRow row = sheet.createRow(cellRow++);
		short iCell = 0;

		int firstRow = 1;

		HSSFCell cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("allocation_overview", "Allocation overview"));
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
		cell.setCellValue(this.iwrb.getLocalizedString("payment_list.paid_entries", "Paid entries"));
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue(this.iwrb.getLocalizedString("payment_list.unpaid_entries", "Unpaid entries"));
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

		Iterator iter = companies.iterator();
		while (iter.hasNext()) {
			Company company = (Company) iter.next();
			List courses = null;
			try {
				courses = new ArrayList(business.getCourses(season != null ? season.getPrimaryKey() : null, period != null ? period.getPrimaryKey() : null, company.getPrimaryKey(), null, null, null));
			}
			catch (RemoteException rex) {
				courses = new ArrayList();
			}

			int paidEntries = 0;
			float amount = 0;

			int unpaidEntries = 0;
			float unpaidAmount = 0;

			if (courses != null && !courses.isEmpty()) {
				paidEntries = business.getNumberOfAllocations(courses, fromDate.getDate(), toDate.getDate(), true);
				amount = business.getAllocationAmount(courses, fromDate.getDate(), toDate.getDate(), true);

				unpaidEntries = business.getNumberOfAllocations(courses, fromDate.getDate(), toDate.getDate(), false);
				unpaidAmount = business.getAllocationAmount(courses, fromDate.getDate(), toDate.getDate(), false);
			}

			row = sheet.createRow(cellRow++);

			cell = row.createCell((short) 0);
			cell.setCellValue(company.getName());

			cell = row.createCell((short) 1);
			cell.setCellValue(paidEntries);

			cell = row.createCell((short) 2);
			cell.setCellValue(amount);

			cell = row.createCell((short) 3);
			cell.setCellValue(unpaidEntries);

			cell = row.createCell((short) 4);
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
			cell.setCellFormula("SUM(C" + (firstRow + 2) + ":C" + cellRow + ")");
			cell.setCellStyle(style);

			cell = row.createCell((short) 3);
			cell.setCellFormula("SUM(D" + (firstRow + 3) + ":D" + cellRow + ")");
			cell.setCellStyle(style);

			cell = row.createCell((short) 4);
			cell.setCellFormula("SUM(E" + (firstRow + 4) + ":E" + cellRow + ")");
			cell.setCellStyle(style);
		}
	}

	private boolean hasDivisionPermission(IWContext iwc, Group division) {
		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			return true;
		}
		else if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY_ADMIN, iwc)) {
			return true;
		}
		else if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY, iwc)) {
			Collection userDivisions = new ArrayList();
			Collection groups = iwc.getCurrentUser().getParentGroups();
			Iterator iterator = groups.iterator();
			while (iterator.hasNext()) {
				Group parentGroup = (Group) iterator.next();
				if (parentGroup.getGroupType().equals(FSKConstants.GROUP_TYPE_DIVISION)) {
					userDivisions.add(parentGroup);
				}
			}

			return userDivisions.contains(division);
		}

		return false;
	}

	private FSKBusiness getBusiness(IWApplicationContext iwc) throws RemoteException {
		return (FSKBusiness) IBOLookup.getServiceInstance(iwc, FSKBusiness.class);
	}
}