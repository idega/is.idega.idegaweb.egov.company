/*
 * $Id: TotalPaymentWriter.java,v 1.1 2008/07/29 10:48:21 anton Exp $ Created on Mar 28, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.business.output;

import is.idega.idegaweb.egov.fsk.FSKConstants;
import is.idega.idegaweb.egov.fsk.business.CompanyHolder;
import is.idega.idegaweb.egov.fsk.business.FSKBusiness;
import is.idega.idegaweb.egov.fsk.data.Period;
import is.idega.idegaweb.egov.fsk.data.Season;
import is.idega.idegaweb.egov.fsk.presentation.TotalPaymentsList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

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
import com.idega.util.IWTimestamp;
import com.idega.util.text.TextSoap;

public class TotalPaymentWriter extends DownloadWriter implements MediaWritable {

	private MemoryFileBuffer buffer = null;
	private Locale locale;
	private IWResourceBundle iwrb;

	public TotalPaymentWriter() {
	}

	public void init(HttpServletRequest req, IWContext iwc) {
		try {
			this.locale = iwc.getApplicationSettings().getApplicationLocale();
			this.iwrb = iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(this.locale);

			Season season = iwc.isParameterSet(TotalPaymentsList.PARAMETER_SEASON_PK) ? getBusiness(iwc).getSeason(iwc.getParameter(TotalPaymentsList.PARAMETER_SEASON_PK)) : null;
			Period period = iwc.isParameterSet(TotalPaymentsList.PARAMETER_PERIOD_PK) ? getBusiness(iwc).getPeriod(iwc.getParameter(TotalPaymentsList.PARAMETER_PERIOD_PK)) : null;
			IWTimestamp fromDate = new IWTimestamp(iwc.getParameter(TotalPaymentsList.PARAMETER_FROM_DATE));
			IWTimestamp toDate = new IWTimestamp(iwc.getParameter(TotalPaymentsList.PARAMETER_TO_DATE));
			toDate.addDays(1);
			IWTimestamp stamp = new IWTimestamp();

			Collection entries = (Collection) iwc.getSessionAttribute(TotalPaymentsList.PROPERTY_ALL_ENTRIES);

			this.buffer = writeXLS(iwc, season, period, fromDate, toDate, entries);
			setAsDownload(iwc, iwrb.getLocalizedString("file_name.total_payments", "total_payments") + "-" + stamp.getDateString("ddMMyy") + ".xls", this.buffer.length());
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

	public MemoryFileBuffer writeXLS(IWContext iwc, Season season, Period period, IWTimestamp fromDate, IWTimestamp toDate, Collection entries) throws Exception {
		MemoryFileBuffer buffer = new MemoryFileBuffer();
		MemoryOutputStream mos = new MemoryOutputStream(buffer);

		HSSFWorkbook wb = new HSSFWorkbook();
		write(iwc, wb, season, period, fromDate, toDate, entries);

		wb.write(mos);
		buffer.setMimeType("application/x-msexcel");
		return buffer;
	}

	private void write(IWContext iwc, HSSFWorkbook wb, Season season, Period period, IWTimestamp fromDate, IWTimestamp toDate, Collection entries) throws Exception {
		IWTimestamp stamp = new IWTimestamp();

		HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(this.iwrb.getLocalizedString("total", "Total")));
		sheet.setColumnWidth((short) 0, (short) (30 * 256));
		sheet.setColumnWidth((short) 1, (short) (14 * 256));
		sheet.setColumnWidth((short) 2, (short) (20 * 256));
		sheet.setColumnWidth((short) 3, (short) (12 * 256));
		sheet.setColumnWidth((short) 4, (short) (20 * 256));
		sheet.setColumnWidth((short) 5, (short) (12 * 256));
		sheet.setColumnWidth((short) 6, (short) (20 * 256));

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 11);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);

		HSSFCellStyle rightStyle = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

		HSSFFont italicFont = wb.createFont();
		italicFont.setItalic(true);
		italicFont.setColor(HSSFColor.GREY_50_PERCENT.index);
		HSSFCellStyle italic = wb.createCellStyle();
		italic.setFont(italicFont);

		int cellRow = 0;
		HSSFRow row = sheet.createRow(cellRow++);
		short iCell = 0;

		int firstRow = 1;

		HSSFCell cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payment_overview", "Total payment overview"));
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

		cell = row.createCell((short) 3);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payments_list.marked_costs_entries", "Marked costs entries"));
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellValue(this.iwrb.getLocalizedString("total_payments_list.marked_allocation_entries", "Marked allocation entries"));
		cell.setCellStyle(style);

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
		cell.setCellValue(this.iwrb.getLocalizedString("bank_account", "Bank account"));
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

		Iterator iterator = entries.iterator();
		while (iterator.hasNext()) {
			CompanyHolder holder = (CompanyHolder) iterator.next();
			Company company = holder.getCompany();

			iCell = 0;
			row = sheet.createRow(cellRow++);

			row.createCell(iCell++).setCellValue(company.getName());
			row.createCell(iCell++).setCellValue(company.getPersonalID());
			row.createCell(iCell++).setCellValue(company.getBankAccount());
			row.createCell(iCell++).setCellValue(holder.getCostsCount());
			row.createCell(iCell++).setCellValue(holder.getCostsAmount());
			row.createCell(iCell++).setCellValue(holder.getAllocationCount());
			row.createCell(iCell++).setCellValue(holder.getAllocationAmount());
		}

		if (cellRow > firstRow) {
			row = sheet.createRow(cellRow);

			cell = row.createCell((short) 0);
			cell.setCellValue(this.iwrb.getLocalizedString("number_of_companies", "Number of companies") + ": " + entries.size());
			cell.setCellStyle(style);

			cell = row.createCell((short) 2);
			cell.setCellValue(this.iwrb.getLocalizedString("total", "Total"));
			cell.setCellStyle(style);

			cell = row.createCell((short) 3);
			cell.setCellFormula("SUM(D" + (firstRow + 1) + ":D" + cellRow + ")");
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
		}
	}

	private FSKBusiness getBusiness(IWApplicationContext iwc) throws RemoteException {
		return (FSKBusiness) IBOLookup.getServiceInstance(iwc, FSKBusiness.class);
	}
}