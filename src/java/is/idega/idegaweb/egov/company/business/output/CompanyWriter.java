/*
 * $Id: CompanyWriter.java,v 1.1 2008/07/29 12:57:44 anton Exp $ Created on Mar 28, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business.output;

import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.FSKBusiness;
import is.idega.idegaweb.egov.company.data.Application;

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

import com.idega.block.process.data.CaseStatus;
import com.idega.business.IBOLookup;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.data.Company;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.PostalCode;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.io.DownloadWriter;
import com.idega.io.MediaWritable;
import com.idega.io.MemoryFileBuffer;
import com.idega.io.MemoryInputStream;
import com.idega.io.MemoryOutputStream;
import com.idega.presentation.IWContext;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.TextSoap;

public class CompanyWriter extends DownloadWriter implements MediaWritable {

	private MemoryFileBuffer buffer = null;
	private Locale locale;
	private IWResourceBundle iwrb;

	private String sheetName;

	public CompanyWriter() {
	}

	public void init(HttpServletRequest req, IWContext iwc) {
		try {
			this.locale = iwc.getApplicationSettings().getApplicationLocale();
			this.iwrb = iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(this.locale);

			Collection companies = getCompanyBusiness(iwc).getCompanies();
			sheetName = iwrb.getLocalizedString("company_writer.sheet_name", "Companies");

			this.buffer = writeXLS(iwc, companies);
			setAsDownload(iwc, "courses.xls", this.buffer.length());
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

	public MemoryFileBuffer writeXLS(IWContext iwc, Collection companies) throws Exception {
		MemoryFileBuffer buffer = new MemoryFileBuffer();
		MemoryOutputStream mos = new MemoryOutputStream(buffer);

		short iCell = 0;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(this.sheetName));
		sheet.setColumnWidth(iCell++, (short) (30 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (30 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 12);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);

		int cellRow = 0;
		iCell = 0;
		HSSFRow row = sheet.createRow(cellRow++);
		HSSFCell cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("name", "Name"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("personal_id", "Personal ID"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("address", "Address"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("postal_code", "Postal code"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("phone_number", "Phone number"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("email", "E-mail"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("bank_account", "Bank account"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("status", "Status"));
		cell.setCellStyle(style);

		Iterator iter = companies.iterator();
		while (iter.hasNext()) {
			row = sheet.createRow(cellRow++);
			iCell = 0;

			Company company = (Company) iter.next();
			Address address = company.getAddress();
			PostalCode code = address != null ? address.getPostalCode() : null;
			Phone phone = company.getPhone();
			Email email = company.getEmail();
			Application application = getBusiness(iwc).getApplication(company);
			CaseStatus status = application.getCaseStatus();

			row.createCell(iCell++).setCellValue(company.getName());
			row.createCell(iCell++).setCellValue(PersonalIDFormatter.format(company.getPersonalID(), locale));
			row.createCell(iCell++).setCellValue(address != null ? address.getStreetAddress() : "");
			row.createCell(iCell++).setCellValue(code != null ? code.getPostalAddress() : "");
			row.createCell(iCell++).setCellValue(phone != null ? phone.getNumber() : "");
			row.createCell(iCell++).setCellValue(email != null ? email.getEmailAddress() : "");
			row.createCell(iCell++).setCellValue(company.getBankAccount());
			row.createCell(iCell++).setCellValue(getBusiness(iwc).getLocalizedCaseStatusDescription(application, status, locale));
		}
		wb.write(mos);
		buffer.setMimeType("application/x-msexcel");
		return buffer;
	}

	private FSKBusiness getBusiness(IWApplicationContext iwc) throws RemoteException {
		return (FSKBusiness) IBOLookup.getServiceInstance(iwc, FSKBusiness.class);
	}

	private CompanyBusiness getCompanyBusiness(IWApplicationContext iwc) throws RemoteException {
		return (CompanyBusiness) IBOLookup.getServiceInstance(iwc, CompanyBusiness.class);
	}
}