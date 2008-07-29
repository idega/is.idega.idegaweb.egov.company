/*
 * $Id: CourseWriter.java,v 1.1 2008/07/29 12:57:44 anton Exp $ Created on Mar 28, 2007
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
import is.idega.idegaweb.egov.company.data.Period;
import is.idega.idegaweb.egov.company.data.Season;
import is.idega.idegaweb.egov.company.presentation.CourseList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.idega.business.IBOLookup;
import com.idega.company.business.CompanyBusiness;
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
import com.idega.util.text.TextSoap;

public class CourseWriter extends DownloadWriter implements MediaWritable {

	private MemoryFileBuffer buffer = null;
	private FSKBusiness business;
	private Locale locale;
	private IWResourceBundle iwrb;
	private boolean isAdmin = false;

	private String companyName;

	public CourseWriter() {
	}

	public void init(HttpServletRequest req, IWContext iwc) {
		try {
			this.locale = iwc.getApplicationSettings().getApplicationLocale();
			this.business = getBusiness(iwc);
			this.iwrb = iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(this.locale);
			this.isAdmin = iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc);

			if (iwc.isParameterSet(CourseList.PARAMETER_COMPANY_PK)) {
				Company company = getCompanyBusiness(iwc).getCompany((Object) iwc.getParameter(CourseList.PARAMETER_COMPANY_PK));
				companyName = company.getName();

				List courses = new ArrayList(getBusiness(iwc).getCourses(iwc.getParameter(CourseList.PARAMETER_SEASON_PK), iwc.getParameter(CourseList.PARAMETER_PERIOD_PK), iwc.getParameter(CourseList.PARAMETER_COMPANY_PK), iwc.getParameter(CourseList.PARAMETER_DIVISION_PK), iwc.getParameter(CourseList.PARAMETER_GROUP_PK), iwc.getParameter(CourseList.PARAMETER_SUB_GROUP_PK)));
				Collections.sort(courses, new CourseComparator(iwc, iwc.getCurrentLocale()));

				this.buffer = writeXLS(iwc, courses);
				setAsDownload(iwc, "courses.xls", this.buffer.length());
			}
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

	public MemoryFileBuffer writeXLS(IWContext iwc, Collection courses) throws Exception {
		MemoryFileBuffer buffer = new MemoryFileBuffer();
		MemoryOutputStream mos = new MemoryOutputStream(buffer);

		short iCell = 0;
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(this.companyName));
		sheet.setColumnWidth(iCell++, (short) (30 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		sheet.setColumnWidth(iCell++, (short) (14 * 256));
		if (isAdmin) {
			sheet.setColumnWidth(iCell++, (short) (14 * 256));
		}

		HSSFFont bigFont = wb.createFont();
		bigFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		bigFont.setFontHeightInPoints((short) 14);
		HSSFCellStyle bigStyle = wb.createCellStyle();
		bigStyle.setFont(bigFont);

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 12);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font);

		int cellRow = 0;
		iCell = 0;
		HSSFRow row = sheet.createRow(cellRow++);

		HSSFCell cell = row.createCell(iCell++);
		cell.setCellValue(this.companyName);
		cell.setCellStyle(bigStyle);
		row = sheet.createRow(cellRow++);
		row = sheet.createRow(cellRow++);

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
		cell.setCellValue(this.iwrb.getLocalizedString("season", "Season"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("period", "Period"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("estimate", "Estimate"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("registrations", "Registrations"));
		cell.setCellStyle(style);
		if (isAdmin) {
			cell = row.createCell(iCell++);
			cell.setCellValue(this.iwrb.getLocalizedString("allocation", "Allocation"));
			cell.setCellStyle(style);
		}

		NumberFormat format = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());
		format.setMinimumFractionDigits(0);

		Iterator iter = courses.iterator();
		while (iter.hasNext()) {
			row = sheet.createRow(cellRow++);
			iCell = 0;

			Course course = (Course) iter.next();
			Group division = business.getDivision(course);
			Group divisionGroup = business.getGroup(course);
			Group subGroup = business.getSubGroup(course);
			Period period = course.getPeriod();
			Season season = period.getSeason();

			row.createCell(iCell++).setCellValue(division.getName());
			row.createCell(iCell++).setCellValue(divisionGroup != null ? divisionGroup.getName() : "");
			row.createCell(iCell++).setCellValue(subGroup != null ? subGroup.getName() : "");
			row.createCell(iCell++).setCellValue(course.getName());
			row.createCell(iCell++).setCellValue(season.getName());
			row.createCell(iCell++).setCellValue(period.getName());
			row.createCell(iCell++).setCellValue(course.getMaxMale() + course.getMaxFemale());
			row.createCell(iCell++).setCellValue(business.getNumberOfRegistrations(course));
			if (isAdmin) {
				row.createCell(iCell++).setCellValue(business.getAllocation(course));
			}
		}

		if (cellRow > 3) {
			row = sheet.createRow(cellRow);

			cell = row.createCell((short) 0);
			cell.setCellValue(this.iwrb.getLocalizedString("total", "Total"));
			cell.setCellStyle(style);

			cell = row.createCell((short) 6);
			cell.setCellFormula("SUM(G" + 4 + ":G" + cellRow + ")");
			cell.setCellStyle(style);

			cell = row.createCell((short) 7);
			cell.setCellFormula("SUM(H" + 4 + ":H" + cellRow + ")");
			cell.setCellStyle(style);

			if (isAdmin) {
				cell = row.createCell((short) 8);
				cell.setCellFormula("SUM(I" + 4 + ":I" + cellRow + ")");
				cell.setCellStyle(style);
			}
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