/*
 * $Id: CourseApprover.java,v 1.1 2008/07/29 10:48:18 anton Exp $
 * Created on Jun 14, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation;

import is.idega.idegaweb.egov.fsk.data.Course;
import is.idega.idegaweb.egov.fsk.data.Period;
import is.idega.idegaweb.egov.fsk.data.Season;

import java.rmi.RemoteException;
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
import com.idega.presentation.TableColumn;
import com.idega.presentation.TableColumnGroup;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Paragraph;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.GenericButton;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.user.data.Group;

public class CourseApprover extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";

	public static final String PARAMETER_COMPANY_PK = "prm_company_pk";
	public static final String PARAMETER_SEASON_PK = "prm_season_pk";
	public static final String PARAMETER_COURSE_PK = "prm_course_pk";
	public static final String PARAMETER_DIVISION_PK = "prm_division_pk";
	private static final String PARAMETER_FILTER = "prm_filter";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_APPROVE = 2;
	private static final int ACTION_REJECT = 3;

	private static final int FILTER_APPROVED = 1;
	private static final int FILTER_REJECTED = 2;

	private int filter = FILTER_APPROVED;

	public void present(IWContext iwc) {
		try {
			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				case ACTION_APPROVE:
					approve(iwc);
					break;

				case ACTION_REJECT:
					reject(iwc);
					break;
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private int parseAction(IWContext iwc) {
		if (iwc.isParameterSet(PARAMETER_FILTER)) {
			filter = Integer.parseInt(iwc.getParameter(PARAMETER_FILTER));
		}
		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_VIEW;
	}

	private Company getCompany(IWContext iwc) {
		Company company = null;
		if (iwc.isParameterSet(PARAMETER_COMPANY_PK)) {
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

	private Season getSeason(IWContext iwc) {
		Season season = null;
		if (iwc.isParameterSet(PARAMETER_SEASON_PK)) {
			try {
				season = getBusiness().getSeason(new Integer(iwc.getParameter(PARAMETER_SEASON_PK)));
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}

		return season;
	}

	private void showList(IWContext iwc) {
		try {
			Form form = new Form();
			form.setID("courseApprover");
			form.setStyleClass("adminForm");

			form.add(getNavigation(iwc));
			form.add(getCourses(iwc));

			Layer buttonLayer = new Layer();
			buttonLayer.setStyleClass("buttonLayer");
			form.add(buttonLayer);

			if (filter == FILTER_REJECTED) {
				SubmitButton approve = new SubmitButton(getResourceBundle().getLocalizedString("course.approve", "Approve"), PARAMETER_ACTION, String.valueOf(ACTION_APPROVE));
				approve.setSubmitConfirm(getResourceBundle().getLocalizedString("course_approver.confirm_approve", "Are you sure you want to approve the selected courses?"));
				buttonLayer.add(approve);
			}

			if (filter == FILTER_APPROVED) {
				SubmitButton reject = new SubmitButton(getResourceBundle().getLocalizedString("course.reject", "Reject"), PARAMETER_ACTION, String.valueOf(ACTION_REJECT));
				reject.setSubmitConfirm(getResourceBundle().getLocalizedString("course_approver.confirm_reject", "Are you sure you want to reject the selected courses?"));
				buttonLayer.add(reject);
			}

			if (getBackPage() != null) {
				GenericButton back = new GenericButton(getResourceBundle().getLocalizedString("back", "Back"));
				back.setPageToOpen(getBackPage());
				buttonLayer.add(back);
			}

			add(form);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private Table2 getCourses(IWContext iwc) throws RemoteException {
		Table2 table = new Table2();
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setWidth("100%");
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");

		TableColumnGroup columnGroup = table.createColumnGroup();
		TableColumn column = columnGroup.createColumn();
		column.setSpan(8);
		column = columnGroup.createColumn();
		column.setSpan(1);
		column.setWidth("12");

		Company currentCompany = getCompany(iwc);
		Season currentSeason = getSeason(iwc);

		Collection courses = null;
		try {
			if (filter == FILTER_APPROVED) {
				courses = currentCompany != null ? getBusiness().getApprovedCourses(currentSeason, currentCompany) : getBusiness().getApprovedCourses(currentSeason);
			}
			if (filter == FILTER_REJECTED) {
				courses = currentCompany != null ? getBusiness().getRejectedCourses(currentSeason, currentCompany) : getBusiness().getRejectedCourses(currentSeason);
			}
		}
		catch (RemoteException rex) {
			courses = new ArrayList();
		}

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		if (currentCompany == null) {
			cell.setStyleClass("company");
			cell.add(new Text(getResourceBundle().getLocalizedString("company", "Company")));
			cell = row.createHeaderCell();
		}

		cell.setStyleClass("division");
		cell.add(new Text(getResourceBundle().getLocalizedString("division", "Division")));

		cell = row.createHeaderCell();
		cell.setStyleClass("group");
		cell.add(new Text(getResourceBundle().getLocalizedString("group", "Group")));

		cell = row.createHeaderCell();
		cell.setStyleClass("subGroup");
		cell.add(new Text(getResourceBundle().getLocalizedString("sub_group", "Sub group")));

		cell = row.createHeaderCell();
		cell.setStyleClass("course");
		cell.add(new Text(getResourceBundle().getLocalizedString("course", "Course")));

		cell = row.createHeaderCell();
		cell.setStyleClass("season");
		cell.add(new Text(getResourceBundle().getLocalizedString("season", "Season")));

		cell = row.createHeaderCell();
		cell.setStyleClass("period");
		cell.add(new Text(getResourceBundle().getLocalizedString("period", "Period")));

		cell = row.createHeaderCell();
		cell.setStyleClass("estimate");
		cell.add(new Text(getResourceBundle().getLocalizedString("estimate", "Estimate")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("checkbox");
		cell.add(Text.getNonBrakingSpace());

		group = table.createBodyRowGroup();
		int iRow = 1;
		if (courses != null) {
			Iterator iter = courses.iterator();
			while (iter.hasNext()) {
				Course course = (Course) iter.next();
				Company company = course.getCompany();
				Group division = getBusiness().getDivision(course);
				if (division == null) {
					continue;
				}

				row = group.createRow();

				Group divisionGroup = getBusiness().getGroup(course);
				Group subGroup = getBusiness().getSubGroup(course);
				Period period = course.getPeriod();
				Season season = period.getSeason();

				CheckBox check = new CheckBox(PARAMETER_COURSE_PK, course.getPrimaryKey().toString());

				cell = row.createCell();
				cell.setStyleClass("firstColumn");
				if (currentCompany == null) {
					cell.setStyleClass("company");
					cell.add(new Text(company.getName()));
					cell = row.createCell();
				}

				cell.setStyleClass("division");
				cell.add(new Text(division.getName()));

				cell = row.createCell();
				cell.setStyleClass("group");
				if (divisionGroup != null) {
					cell.add(new Text(divisionGroup.getName()));
				}
				else {
					cell.add(new Text("-"));
				}

				cell = row.createCell();
				cell.setStyleClass("subGroup");
				if (subGroup != null) {
					cell.add(new Text(subGroup.getName()));
				}
				else {
					cell.add(new Text("-"));
				}

				cell = row.createCell();
				cell.setStyleClass("course");
				cell.add(new Text(course.getName()));

				cell = row.createCell();
				cell.setStyleClass("season");
				cell.add(new Text(season.getName()));

				cell = row.createCell();
				cell.setStyleClass("period");
				cell.add(new Text(period.getName()));

				cell = row.createCell();
				cell.setStyleClass("estimate");
				cell.add(new Text(String.valueOf((course.getMaxMale() + course.getMaxFemale()))));

				cell = row.createCell();
				cell.setStyleClass("lastColumn");
				cell.setStyleClass("checkbox");
				cell.add(check);

				if (iRow % 2 == 0) {
					row.setStyleClass("even");
				}
				else {
					row.setStyleClass("odd");
				}
				iRow++;
			}
		}

		group = table.createFooterRowGroup();
		row = group.createRow();

		cell = row.createCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("numberOfCourses");
		cell.setColumnSpan(9);
		cell.add(new Text(getResourceBundle().getLocalizedString("number_of_courses", "Number of courses") + ": " + (iRow - 1)));

		return table;
	}

	protected Layer getNavigation(IWContext iwc) throws RemoteException {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("formSection");

		Collection collection = getCompanyBusiness(iwc).getActiveCompanies();

		DropdownMenu companies = new DropdownMenu(PARAMETER_COMPANY_PK);
		companies.addMenuElements(collection);
		companies.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_company", "Select company"));
		companies.setToSubmit(true);
		companies.keepStatusOnAction(true);

		DropdownMenu season = new DropdownMenu(PARAMETER_SEASON_PK);
		season.addMenuElements(getBusiness().getAllSeasons());
		season.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_season", "Select season"));
		season.setToSubmit(true);
		season.keepStatusOnAction(true);

		DropdownMenu filter = new DropdownMenu(PARAMETER_FILTER);
		filter.addMenuElement(FILTER_APPROVED, getResourceBundle().getLocalizedString("course_approver.approved", "Approved"));
		filter.addMenuElement(FILTER_REJECTED, getResourceBundle().getLocalizedString("course_approver.rejected", "Rejected"));
		filter.keepStatusOnAction(true);
		filter.setToSubmit(true);

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(getResourceBundle().getLocalizedString("company", "Company"), companies);
		formItem.add(label);
		formItem.add(companies);
		layer.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("season", "Season"), season);
		formItem.add(label);
		formItem.add(season);
		layer.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("filter", "Filter"), filter);
		formItem.add(label);
		formItem.add(filter);
		layer.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		layer.add(clearLayer);

		return layer;
	}

	private void approve(IWContext iwc) throws RemoteException {
		getBusiness().approveCourses(iwc.getParameterValues(PARAMETER_COURSE_PK), iwc.getCurrentLocale());
		showReceipt(iwc, getResourceBundle().getLocalizedString("course_approver.approved_heading", "Courses approved"), getResourceBundle().getLocalizedString("course_approver.approved_text", "The selected courses have been approved."));
	}

	private void reject(IWContext iwc) throws RemoteException {
		getBusiness().rejectCourses(iwc.getParameterValues(PARAMETER_COURSE_PK), iwc.getCurrentLocale());
		showReceipt(iwc, getResourceBundle().getLocalizedString("course_approver.rejected_heading", "Courses rejected"), getResourceBundle().getLocalizedString("course_approver.rejected_text", "The selected courses have been rejected."));
	}

	private void showReceipt(IWContext iwc, String subject, String body) throws RemoteException {
		Form form = new Form();
		add(form);

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("receipt");
		form.add(layer);

		Layer image = new Layer(Layer.DIV);
		image.setStyleClass("receiptImage");
		layer.add(image);

		Heading1 heading = new Heading1(subject);
		layer.add(heading);

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Text(body));
		layer.add(paragraph);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link home = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
		home.setStyleClass("buttonHome");
		home.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));
		home.maintainParameter(PARAMETER_COMPANY_PK, iwc);
		home.maintainParameter(PARAMETER_SEASON_PK, iwc);
		bottom.add(home);
	}
}