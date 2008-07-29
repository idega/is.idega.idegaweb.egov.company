/*
 * $Id: ParticipantsWriter.java,v 1.1 2008/07/29 12:57:44 anton Exp $ Created on Mar 28, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business.output;

import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.FSKBusiness;
import is.idega.idegaweb.egov.company.business.ParticipantComparator;
import is.idega.idegaweb.egov.company.data.Course;
import is.idega.idegaweb.egov.company.data.Participant;
import is.idega.idegaweb.egov.company.data.ParticipantDiscount;
import is.idega.idegaweb.egov.company.data.PaymentAllocation;
import is.idega.idegaweb.egov.company.data.Period;
import is.idega.idegaweb.egov.company.data.Season;
import is.idega.idegaweb.egov.company.presentation.ParticipantsList;

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
import com.idega.util.text.Name;
import com.idega.util.text.TextSoap;

public class ParticipantsWriter extends DownloadWriter implements MediaWritable {

	private MemoryFileBuffer buffer = null;
	private FSKBusiness business;
	private Locale locale;
	private IWResourceBundle iwrb;

	private String courseName;
	private String companyName;

	public ParticipantsWriter() {
	}

	public void init(HttpServletRequest req, IWContext iwc) {
		try {
			this.locale = iwc.getApplicationSettings().getApplicationLocale();
			this.business = getBusiness(iwc);
			this.iwrb = iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(this.locale);

			Group division = null;
			Group group = null;
			Group subGroup = null;
			Period period = null;
			Season season = null;

			Course course = null;
			List users = new ArrayList();
			Collection courses = new ArrayList();
			if (iwc.isParameterSet(ParticipantsList.PARAMETER_COURSE_PK)) {
				course = business.getCourse(iwc.getParameter(ParticipantsList.PARAMETER_COURSE_PK));
				Group courseGroup = course.getGroup();
				Group parentGroup = (Group) courseGroup.getParentNode();
				Company company = course.getCompany();
				courseName = course.getName();
				companyName = company.getName();
				period = course.getPeriod();
				season = period != null ? period.getSeason() : null;

				if (parentGroup.getGroupType().equals(FSKConstants.GROUP_TYPE_DIVISION)) {
					division = parentGroup;
				}
				else if (parentGroup.getGroupType().equals(FSKConstants.GROUP_TYPE_GROUP)) {
					division = (Group) parentGroup.getParentNode();
					group = parentGroup;
				}
				else if (parentGroup.getGroupType().equals(FSKConstants.GROUP_TYPE_SUB_GROUP)) {
					group = (Group) parentGroup.getParentNode();
					division = (Group) group.getParentNode();
					subGroup = parentGroup;
				}

				users = new ArrayList(getBusiness(iwc).getUserBusiness().getUsersInGroup(courseGroup));
			}
			else {
				courses = new ArrayList(business.getCourses(iwc.getParameter(ParticipantsList.PARAMETER_SEASON_PK), iwc.getParameter(ParticipantsList.PARAMETER_PERIOD_PK), iwc.getParameter(ParticipantsList.PARAMETER_COMPANY_PK), iwc.getParameter(ParticipantsList.PARAMETER_DIVISION_PK), iwc.getParameter(ParticipantsList.PARAMETER_GROUP_PK), iwc.getParameter(ParticipantsList.PARAMETER_SUB_GROUP_PK)));
				Iterator iterator = courses.iterator();
				while (iterator.hasNext()) {
					course = (Course) iterator.next();
					Group courseGroup = course.getGroup();
					users.addAll(business.getUserBusiness().getUsersInGroup(courseGroup));
				}

				Company company = iwc.isParameterSet(ParticipantsList.PARAMETER_COMPANY_PK) ? business.getCompanyBusiness().getCompany((Object) iwc.getParameter(ParticipantsList.PARAMETER_COMPANY_PK)) : null;
				companyName = company != null ? company.getName() : null;

				division = iwc.isParameterSet(ParticipantsList.PARAMETER_DIVISION_PK) ? business.getDivision(iwc.getParameter(ParticipantsList.PARAMETER_DIVISION_PK)) : null;
				group = iwc.isParameterSet(ParticipantsList.PARAMETER_GROUP_PK) ? business.getGroup(iwc.getParameter(ParticipantsList.PARAMETER_GROUP_PK)) : null;
				subGroup = iwc.isParameterSet(ParticipantsList.PARAMETER_SUB_GROUP_PK) ? business.getGroup(iwc.getParameter(ParticipantsList.PARAMETER_SUB_GROUP_PK)) : null;
				season = iwc.isParameterSet(ParticipantsList.PARAMETER_SEASON_PK) ? business.getSeason(iwc.getParameter(ParticipantsList.PARAMETER_SEASON_PK)) : null;
				period = iwc.isParameterSet(ParticipantsList.PARAMETER_PERIOD_PK) ? business.getPeriod(iwc.getParameter(ParticipantsList.PARAMETER_PERIOD_PK)) : null;
			}
			Collections.sort(users, new ParticipantComparator(iwc, iwc.getCurrentLocale(), (Course) null));

			this.buffer = writeXLS(iwc, season, period, division, group, subGroup, course, courses, users);
			setAsDownload(iwc, "participants.xls", this.buffer.length());
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

	public MemoryFileBuffer writeXLS(IWContext iwc, Season season, Period period, Group division, Group group, Group subGroup, Course course, Collection courses, Collection users) throws Exception {
		MemoryFileBuffer buffer = new MemoryFileBuffer();
		MemoryOutputStream mos = new MemoryOutputStream(buffer);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(TextSoap.encodeToValidExcelSheetName(this.companyName));
		sheet.setColumnWidth((short) 0, (short) (30 * 256));
		sheet.setColumnWidth((short) 1, (short) (14 * 256));
		sheet.setColumnWidth((short) 2, (short) (30 * 256));
		sheet.setColumnWidth((short) 3, (short) (14 * 256));
		sheet.setColumnWidth((short) 4, (short) (14 * 256));
		sheet.setColumnWidth((short) 5, (short) (14 * 256));
		sheet.setColumnWidth((short) 6, (short) (14 * 256));
		sheet.setColumnWidth((short) 7, (short) (14 * 256));
		sheet.setColumnWidth((short) 8, (short) (10 * 256));

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
		cell.setCellValue(this.iwrb.getLocalizedString("participants_overview", "Participants overview"));
		cell.setCellStyle(style);

		cell = row.createCell((short) 7);
		cell.setCellValue(this.iwrb.getLocalizedString("printed_date", "Printed date"));
		cell.setCellStyle(rightStyle);

		IWTimestamp stamp = new IWTimestamp();
		cell = row.createCell((short) 8);
		cell.setCellValue(stamp.getLocaleDateAndTime(locale, IWTimestamp.SHORT, IWTimestamp.SHORT));
		firstRow++;

		iCell = 0;

		row = sheet.createRow(cellRow++);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("company", "Company"));
		cell.setCellStyle(style);

		cell = row.createCell(iCell++);
		cell.setCellValue(this.companyName);
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
		if (group != null) {
			row = sheet.createRow(cellRow++);
			iCell = 0;
			firstRow++;

			cell = row.createCell(iCell++);
			cell.setCellValue(this.iwrb.getLocalizedString("group", "Group"));
			cell.setCellStyle(style);

			cell = row.createCell(iCell++);
			cell.setCellValue(group.getName());
		}
		if (subGroup != null) {
			row = sheet.createRow(cellRow++);
			iCell = 0;
			firstRow++;

			cell = row.createCell(iCell++);
			cell.setCellValue(this.iwrb.getLocalizedString("sub_group", "Sub group"));
			cell.setCellStyle(style);

			cell = row.createCell(iCell++);
			cell.setCellValue(subGroup.getName());
		}

		if (courseName != null) {
			row = sheet.createRow(cellRow++);
			iCell = 0;
			firstRow++;

			cell = row.createCell(iCell++);
			cell.setCellValue(this.iwrb.getLocalizedString("course", "Course"));
			cell.setCellStyle(style);

			cell = row.createCell(iCell++);
			cell.setCellValue(courseName);
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
		if (courseName == null) {
			cell = row.createCell(iCell++);
			cell.setCellValue(this.iwrb.getLocalizedString("course", "Course"));
			cell.setCellStyle(style);
		}
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
		cell.setCellValue(this.iwrb.getLocalizedString("remainder", "Remainder"));
		cell.setCellStyle(style);
		cell = row.createCell(iCell++);
		cell.setCellValue(this.iwrb.getLocalizedString("status", "Status"));
		cell.setCellStyle(style);

		User currentUser = null;
		List userCourses = null;

		Iterator iter = users.iterator();
		while (iter.hasNext()) {
			User user = (User) iter.next();
			Participant participant = business.getParticipant(user);
			Name name = new Name(user.getFirstName(), user.getMiddleName(), user.getLastName());

			User custodian = business.getCustodian(user);

			if (currentUser == null || (currentUser != null && !currentUser.equals(user))) {
				userCourses = new ArrayList();
				currentUser = user;
			}

			Iterator iterator = courses.iterator();
			while (iterator.hasNext()) {
				course = (Course) iterator.next();
				Group courseGroup = course.getGroup();
				if (user.hasRelationTo(courseGroup) && !userCourses.contains(course)) {
					userCourses.add(course);
					break;
				}
			}

			ParticipantDiscount discount = getBusiness(iwc).getDiscount(participant, course);
			float price = course.getPrice();
			if (discount != null) {
				if (discount.isAmount()) {
					price = price - discount.getDiscount();
				}
				else if (discount.isPercentage()) {
					price = price * (1 - discount.getDiscount());
				}
			}

			float totalAllocation = business.getAllocation(course, user);
			if (totalAllocation > 0.0f) {
				Collection allocations = business.getAllocations(course, user);
				Iterator it2 = allocations.iterator();
				float localAllocation = 0.0f;
				boolean active = participant.isActive(course);
				boolean lastEntry = false;
				int size = allocations.size();
				int current = 0;
				while (it2.hasNext()) {
					iCell = 0;
					current++;

					if (current == size) {
						lastEntry = true;
					}
					row = sheet.createRow(cellRow++);

					PaymentAllocation pa = (PaymentAllocation) it2.next();
					localAllocation += pa.getAmount();

					float remainder = price - localAllocation;

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

					if (courseName == null) {
						cell = row.createCell(iCell++);
						cell.setCellValue(course.getName());
						if (!active) {
							cell.setCellStyle(italic);
						}
					}

					cell = row.createCell(iCell++);
					if (lastEntry) {
						cell.setCellValue(price);
					}
					else {
						cell.setCellValue("");
					}
					if (!active) {
						cell.setCellStyle(italic);
					}

					cell = row.createCell(iCell++);
					cell.setCellValue(pa.getAmount());
					if (!active) {
						cell.setCellStyle(italic);
					}

					cell = row.createCell(iCell++);
					if (pa.getEntryDate() != null) {
						cell.setCellValue(new IWTimestamp(pa.getEntryDate()).getDateString("dd.MM.yyyy"));
					}
					else {
						cell.setCellValue("");
					}
					if (!active) {
						cell.setCellStyle(italic);
					}

					cell = row.createCell(iCell++);
					if (lastEntry) {
						cell.setCellValue(remainder);
					}
					else {
						cell.setCellValue("");
					}
					if (!active) {
						cell.setCellStyle(italic);
					}

					if (lastEntry) {
						cell = row.createCell(iCell++);
						if (!active) {
							cell.setCellValue(iwrb.getLocalizedString("status.removed"));
							cell.setCellStyle(italic);
						}
						else {
							cell.setCellValue(iwrb.getLocalizedString("status.active"));
						}
					}
				}
			}
			else {
				float remainder = price - totalAllocation;
				boolean active = participant.isActive(course);

				iCell = 0;
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

				if (courseName == null) {
					cell = row.createCell(iCell++);
					cell.setCellValue(course.getName());
					if (!active) {
						cell.setCellStyle(italic);
					}
				}

				cell = row.createCell(iCell++);
				cell.setCellValue(price);
				if (!active) {
					cell.setCellStyle(italic);
				}

				cell = row.createCell(iCell++);
				cell.setCellValue(totalAllocation);
				if (!active) {
					cell.setCellStyle(italic);
				}

				cell = row.createCell(iCell++);
				cell.setCellValue("");
				if (!active) {
					cell.setCellStyle(italic);
				}

				cell = row.createCell(iCell++);
				cell.setCellValue(remainder);
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
		}

		if (cellRow > firstRow) {
			row = sheet.createRow(cellRow);

			cell = row.createCell((short) 0);
			cell.setCellValue(this.iwrb.getLocalizedString("total", "Total"));
			cell.setCellStyle(style);

			if (courseName != null) {
				cell = row.createCell((short) 4);
				cell.setCellFormula("SUM(E" + (firstRow + 1) + ":E" + cellRow + ")");
				cell.setCellStyle(style);

				cell = row.createCell((short) 5);
				cell.setCellFormula("SUM(F" + (firstRow + 1) + ":F" + cellRow + ")");
				cell.setCellStyle(style);

				cell = row.createCell((short) 7);
				cell.setCellFormula("SUM(H" + (firstRow + 1) + ":H" + cellRow + ")");
				cell.setCellStyle(style);
			}
			else {
				cell = row.createCell((short) 5);
				cell.setCellFormula("SUM(F" + (firstRow + 1) + ":F" + cellRow + ")");
				cell.setCellStyle(style);

				cell = row.createCell((short) 6);
				cell.setCellFormula("SUM(G" + (firstRow + 1) + ":G" + cellRow + ")");
				cell.setCellStyle(style);

				cell = row.createCell((short) 8);
				cell.setCellFormula("SUM(I" + (firstRow + 1) + ":I" + cellRow + ")");
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
}