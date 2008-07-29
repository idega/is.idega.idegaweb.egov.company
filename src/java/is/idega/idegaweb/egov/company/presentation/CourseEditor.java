/*
 * $Id: CourseEditor.java,v 1.1 2008/07/29 12:57:41 anton Exp $ Created on Jun 12, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.CourseComparator;
import is.idega.idegaweb.egov.company.data.Constant;
import is.idega.idegaweb.egov.company.data.Course;
import is.idega.idegaweb.egov.company.data.Period;
import is.idega.idegaweb.egov.company.data.Season;

import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
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
import com.idega.presentation.text.Paragraph;
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
import com.idega.user.data.Group;
import com.idega.util.CoreConstants;
import com.idega.util.IWTimestamp;
import com.idega.util.text.TextSoap;

public class CourseEditor extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";
	private static final String PARAMETER_COURSE_PK = "prm_course_pk";
	private static final String PARAMETER_SEASON_PK = "prm_season_pk";
	private static final String PARAMETER_PERIOD_PK = "prm_period_pk";
	private static final String PARAMETER_DIVISION_PK = "prm_division_pk";
	private static final String PARAMETER_GROUP_PK = "prm_group_pk";
	private static final String PARAMETER_SUB_GROUP_PK = "prm_sub_group_pk";
	private static final String PARAMETER_NAME = "prm_name";
	private static final String PARAMETER_PRICE = "prm_price";
	private static final String PARAMETER_COST = "prm_cost";
	private static final String PARAMETER_NUMBER_OF_HOURS = "prm_number_of_hours";
	private static final String PARAMETER_START_DATE = "prm_start_date";
	private static final String PARAMETER_END_DATE = "prm_end_date";
	private static final String PARAMETER_MAX_MALE = "prm_max_male";
	private static final String PARAMETER_MAX_FEMALE = "prm_max_female";
	private static final String PARAMETER_SORT = "prm_sort";

	private static final String PARAMETER_COMPANY_PK = "prm_company_pk";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_EDIT = 2;
	private static final int ACTION_NEW = 3;
	private static final int ACTION_SAVE = 4;
	private static final int ACTION_DELETE = 5;

	private int iSort = CourseComparator.SORT_GENERAL;

	public void present(IWContext iwc) {
		try {
			Object coursePK = iwc.getParameter(PARAMETER_COURSE_PK);

			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				case ACTION_EDIT:
					showEditor(iwc, coursePK);
					break;

				case ACTION_NEW:
					showEditor(iwc, null);
					break;

				case ACTION_SAVE:
					if (!saveCourse(iwc)) {
						showEditor(iwc, coursePK);
					}
					break;

				case ACTION_DELETE:
					if (!getBusiness().deleteCourse(iwc.getParameter(PARAMETER_COURSE_PK), iwc.getCurrentUser())) {
						getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course.remove_error", "You can not remove a course that has children attached to it."));
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
		if (iwc.isParameterSet(PARAMETER_SORT)) {
			iSort = Integer.parseInt(iwc.getParameter(PARAMETER_SORT));
		}

		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_VIEW;
	}

	public void showList(IWContext iwc) throws RemoteException {
		if (!hasPermission(iwc)) {
			showNoPermission(iwc);
			return;
		}

		Form form = new Form();
		form.setID("courseEditor");
		form.setStyleClass("adminForm");

		form.add(getNavigation(iwc));

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
		column.setSpan(2);
		column.setWidth("12");

		Company company = getCompany(iwc);

		boolean isOpen = true;
		List courses = null;
		if (company != null) {
			isOpen = company.isOpen();
			try {
				courses = new ArrayList(getBusiness().getCourses(company));
			}
			catch (RemoteException rex) {
				courses = new ArrayList();
			}
		}
		else {
			courses = new ArrayList();
		}
		Collections.sort(courses, new CourseComparator(iwc, iwc.getCurrentLocale(), iSort));

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("division");
		cell.add(new Text(getResourceBundle().getLocalizedString("division", "Division")));

		cell = row.createHeaderCell();
		cell.setStyleClass("group");
		cell.add(new Text(getResourceBundle().getLocalizedString("group", "Group")));

		cell = row.createHeaderCell();
		cell.setStyleClass("subGroup");
		cell.add(new Text(getResourceBundle().getLocalizedString("sub_group", "Sub group")));

		cell = row.createHeaderCell();
		cell.setStyleClass("name");
		cell.add(new Text(getResourceBundle().getLocalizedString("name", "Name")));

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
		cell.setStyleClass("registrations");
		cell.add(new Text(getResourceBundle().getLocalizedString("registrations", "Registrations")));

		cell = row.createHeaderCell();
		cell.setStyleClass("copy");
		cell.add(new Text(getResourceBundle().getLocalizedString("copy", "Copy")));

		cell = row.createHeaderCell();
		cell.setStyleClass("edit");
		cell.add(new Text(getResourceBundle().getLocalizedString("edit", "Edit")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("delete");
		cell.add(new Text(getResourceBundle().getLocalizedString("delete", "Delete")));

		group = table.createBodyRowGroup();
		int iRow = 1;
		java.util.Iterator iter = courses.iterator();
		while (iter.hasNext()) {
			Course course = (Course) iter.next();
			Group division = getBusiness().getDivision(course);
			if (!hasDivisionPermission(iwc, division)) {
				continue;
			}

			Group divisionGroup = getBusiness().getGroup(course);
			Group subGroup = getBusiness().getSubGroup(course);
			Period period = course.getPeriod();
			Season season = period != null ? period.getSeason() : null;

			row = group.createRow();

			/*if (course.isApproved()) {
				row.setStyleClass("approved");
			}*/

			Link copy = new Link(getBundle().getImage("copy.png", getResourceBundle().getLocalizedString("copy", "Copy")));
			copy.maintainParameter(PARAMETER_COMPANY_PK, iwc);
			copy.addParameter(PARAMETER_ACTION, ACTION_NEW);
			copy.addParameter(PARAMETER_DIVISION_PK, division.getPrimaryKey().toString());
			if (divisionGroup != null) {
				copy.addParameter(PARAMETER_GROUP_PK, divisionGroup.getPrimaryKey().toString());
			}
			if (subGroup != null) {
				copy.addParameter(PARAMETER_SUB_GROUP_PK, subGroup.getPrimaryKey().toString());
			}
			copy.addParameter(PARAMETER_NAME, course.getName());
			copy.addParameter(PARAMETER_MAX_FEMALE, course.getMaxFemale());
			copy.addParameter(PARAMETER_MAX_MALE, course.getMaxMale());
			copy.addParameter(PARAMETER_NUMBER_OF_HOURS, course.getNumberOfHours());

			Link edit = new Link(getBundle().getImage("edit.png", getResourceBundle().getLocalizedString("edit", "Edit")));
			edit.addParameter(PARAMETER_COURSE_PK, course.getPrimaryKey().toString());
			edit.maintainParameter(PARAMETER_COMPANY_PK, iwc);
			edit.addParameter(PARAMETER_ACTION, ACTION_EDIT);

			Link delete = new Link(getBundle().getImage("delete.png", getResourceBundle().getLocalizedString("delete", "Delete")));
			delete.addParameter(PARAMETER_COURSE_PK, course.getPrimaryKey().toString());
			delete.addParameter(PARAMETER_ACTION, ACTION_DELETE);
			delete.maintainParameter(PARAMETER_COMPANY_PK, iwc);
			delete.setClickConfirmation(getResourceBundle().getLocalizedString("course.confirm_delete", "Are you sure you want to delete the course selected?"));

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("division");
			if (division != null) {
				cell.add(new Text(division.getName()));
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

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
			cell.setStyleClass("name");
			cell.add(new Text(course.getName()));

			cell = row.createCell();
			cell.setStyleClass("season");
			if (season != null) {
				cell.add(new Text(season.getName()));
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

			cell = row.createCell();
			cell.setStyleClass("period");
			if (period != null) {
				cell.add(new Text(period.getName()));
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

			cell = row.createCell();
			cell.setStyleClass("estimate");
			cell.add(new Text(String.valueOf((course.getMaxMale() + course.getMaxFemale()))));

			cell = row.createCell();
			cell.setStyleClass("registrations");
			cell.add(new Text(String.valueOf(getBusiness().getNumberOfRegistrations(course))));

			cell = row.createCell();
			cell.setStyleClass("copy");
			if (isOpen) {
				cell.add(copy);
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

			cell = row.createCell();
			cell.setStyleClass("edit");
			if (isOpen) {
				cell.add(edit);
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("delete");
			if (isOpen) {
				cell.add(delete);
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

			if (iRow % 2 == 0) {
				row.setStyleClass("even");
			}
			else {
				row.setStyleClass("odd");
			}
			iRow++;
		}
		form.add(table);

		//form.add(getLegend());

		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);

		if (getBackPage() != null) {
			GenericButton back = new GenericButton(getResourceBundle().getLocalizedString("back", "Back"));
			back.setPageToOpen(getBackPage());
			buttonLayer.add(back);
		}

		if (company != null && isOpen) {
			SubmitButton newLink = new SubmitButton(getResourceBundle().getLocalizedString("course.new", "New course"), PARAMETER_ACTION, String.valueOf(ACTION_NEW));
			buttonLayer.add(newLink);
		}

		add(form);
	}

	protected Layer getNavigation(IWContext iwc) throws RemoteException {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("formSection");

		Company company = getCompany(iwc);

		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			Collection collection = getCompanyBusiness(iwc).getActiveCompanies();

			DropdownMenu companies = new DropdownMenu(PARAMETER_COMPANY_PK);
			companies.addMenuElements(collection);
			companies.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_company", "Select company"));
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

		DropdownMenu sort = new DropdownMenu(PARAMETER_SORT);
		sort.addMenuElement(CourseComparator.SORT_GENERAL, getResourceBundle().getLocalizedString("course_editor.sort_division", "Division"));
		sort.addMenuElement(CourseComparator.SORT_SEASON, getResourceBundle().getLocalizedString("course_editor.sort_season", "Season"));
		sort.keepStatusOnAction(true);
		sort.setToSubmit(true);

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(getResourceBundle().getLocalizedString("sort", "Sort"), sort);
		formItem.add(label);
		formItem.add(sort);
		layer.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		layer.add(clearLayer);

		return layer;
	}

	/*private Lists getLegend() {
		Lists list = new Lists();
		list.setStyleClass("legend");

		ListItem item = new ListItem();
		item.setStyleClass("approved");
		item.add(new Text(getResourceBundle().getLocalizedString("course.approved_course", "Approved course")));
		list.add(item);

		return list;
	}*/

	public void showEditor(IWContext iwc, Object coursePK) throws java.rmi.RemoteException {
		Form form = new Form();
		form.setID("courseEditor");
		form.setStyleClass("adminForm");
		form.maintainParameter(PARAMETER_COMPANY_PK);
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_SAVE));

		super.getParentPage().addJavascriptURL("/dwr/interface/FSKDWRUtil.js");
		super.getParentPage().addJavascriptURL(CoreConstants.DWR_ENGINE_SCRIPT);
		super.getParentPage().addJavascriptURL("/dwr/util.js");

		StringBuffer script2 = new StringBuffer();
		script2.append("function setGroups(data) {\n").append("\tDWRUtil.removeAllOptions(\"" + PARAMETER_GROUP_PK + "\");\n").append("\tDWRUtil.removeAllOptions(\"" + PARAMETER_SUB_GROUP_PK + "\");\n").append("\tDWRUtil.addOptions(\"" + PARAMETER_GROUP_PK + "\", data);\n").append("}");

		StringBuffer script = new StringBuffer();
		script.append("function changeGroups() {\n").append("\tFSKDWRUtil.getAllGroups(DWRUtil.getValue('" + PARAMETER_DIVISION_PK + "'), '" + FSKConstants.GROUP_TYPE_GROUP + "', '" + iwc.getCurrentLocale().getCountry() + "', setGroups);\n").append("}");

		StringBuffer script3 = new StringBuffer();
		script3.append("function setSubGroups(data) {\n").append("\tDWRUtil.removeAllOptions(\"" + PARAMETER_SUB_GROUP_PK + "\");\n").append("\tDWRUtil.addOptions(\"" + PARAMETER_SUB_GROUP_PK + "\", data);\n").append("}");

		StringBuffer script4 = new StringBuffer();
		script4.append("function changeSubGroups() {\n").append("\tFSKDWRUtil.getAllGroups(DWRUtil.getValue('" + PARAMETER_GROUP_PK + "'), '" + FSKConstants.GROUP_TYPE_SUB_GROUP + "', '" + iwc.getCurrentLocale().getCountry() + "', setSubGroups);\n").append("}");

		StringBuffer script5 = new StringBuffer();
		script5.append("function setPeriods(data) {\n").append("\tDWRUtil.removeAllOptions(\"" + PARAMETER_PERIOD_PK + "\");\n").append("\tDWRUtil.addOptions(\"" + PARAMETER_PERIOD_PK + "\", data);\n").append("}");

		StringBuffer script6 = new StringBuffer();
		script6.append("function changePeriods() {\n").append("\tFSKDWRUtil.getPeriods(DWRUtil.getValue('" + PARAMETER_SEASON_PK + "'), '" + iwc.getCurrentLocale().getCountry() + "', setPeriods);\n").append("}");

		super.getParentPage().getAssociatedScript().addFunction("setGroups", script2.toString());
		super.getParentPage().getAssociatedScript().addFunction("changeGroups", script.toString());
		super.getParentPage().getAssociatedScript().addFunction("setSubGroups", script3.toString());
		super.getParentPage().getAssociatedScript().addFunction("changeSubGroups", script4.toString());
		super.getParentPage().getAssociatedScript().addFunction("setPeriods", script5.toString());
		super.getParentPage().getAssociatedScript().addFunction("changePeriods", script6.toString());

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("course.course_editor_help", "Fill in the desired values and click 'Save'.")));
		section.add(helpLayer);

		Company company = getCompany(iwc);
		Course course = getBusiness().getCourse(coursePK);

		SelectorUtility util = new SelectorUtility();

		DropdownMenu seasons = (DropdownMenu) util.getSelectorFromIDOEntities(new DropdownMenu(PARAMETER_SEASON_PK), getBusiness().getAllSeasons());
		seasons.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_season", "Select season"));
		seasons.setOnChange("changePeriods();");
		seasons.keepStatusOnAction(true);

		DropdownMenu periods = new DropdownMenu(PARAMETER_PERIOD_PK);
		periods.setID(PARAMETER_PERIOD_PK);
		periods.keepStatusOnAction(true);

		DropdownMenu divisions = (DropdownMenu) util.getSelectorFromIDOEntities(new DropdownMenu(PARAMETER_DIVISION_PK), getBusiness().getDivisions(iwc, company, iwc.getCurrentUser()));
		divisions.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_division", "Select division"));
		divisions.setOnChange("changeGroups();");
		divisions.keepStatusOnAction(true);

		DropdownMenu groups = new DropdownMenu(PARAMETER_GROUP_PK);
		groups.setOnChange("changeSubGroups();");
		groups.setId(PARAMETER_GROUP_PK);
		groups.keepStatusOnAction(true);

		DropdownMenu subGroups = new DropdownMenu(PARAMETER_SUB_GROUP_PK);
		subGroups.setId(PARAMETER_SUB_GROUP_PK);
		subGroups.keepStatusOnAction(false);

		TextInput name = new TextInput(PARAMETER_NAME);
		name.keepStatusOnAction(true);

		TextInput price = new TextInput(PARAMETER_PRICE);
		price.keepStatusOnAction(true);

		TextInput cost = new TextInput(PARAMETER_COST);
		cost.keepStatusOnAction(true);

		TextInput numberOfHours = new TextInput(PARAMETER_NUMBER_OF_HOURS);
		numberOfHours.keepStatusOnAction(true);

		DatePicker startDate = new DatePicker(PARAMETER_START_DATE);
		//		startDate.setShowEmpty(true);
		startDate.keepStatusOnAction(true);

		DatePicker endDate = new DatePicker(PARAMETER_END_DATE);
		//		endDate.setShowEmpty(true);
		endDate.keepStatusOnAction(true);

		TextInput maxMale = new TextInput(PARAMETER_MAX_MALE);
		maxMale.keepStatusOnAction(true);

		TextInput maxFemale = new TextInput(PARAMETER_MAX_FEMALE);
		maxFemale.keepStatusOnAction(true);

		if (course != null) {
			Period period = course.getPeriod();
			Season season = period.getSeason();

			Group courseGroup = course.getGroup();
			Group parentGroup = (Group) courseGroup.getParentNode();

			Group division = null;
			Group group = null;
			Group subGroup = null;

			boolean hasRegistrations = getBusiness().getNumberOfRegistrations(course) > 0;

			if (parentGroup.getGroupType().equals(FSKConstants.GROUP_TYPE_DIVISION)) {
				division = parentGroup;
				groups.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_group", "Select group"));
				groups.addMenuElements(getBusiness().getGroups(division));
				divisions.setSelectedElement(division.getPrimaryKey().toString());
			}
			else if (parentGroup.getGroupType().equals(FSKConstants.GROUP_TYPE_GROUP)) {
				division = (Group) parentGroup.getParentNode();
				group = parentGroup;

				groups.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_group", "Select group"));
				groups.addMenuElements(getBusiness().getGroups(division));
				subGroups.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_sub_group", "Select sub group"));
				subGroups.addMenuElements(getBusiness().getSubGroups(group));
				divisions.setSelectedElement(division.getPrimaryKey().toString());
				groups.setSelectedElement(group.getPrimaryKey().toString());
			}
			else if (parentGroup.getGroupType().equals(FSKConstants.GROUP_TYPE_SUB_GROUP)) {
				group = (Group) parentGroup.getParentNode();
				division = (Group) group.getParentNode();
				subGroup = parentGroup;

				groups.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_group", "Select group"));
				groups.addMenuElements(getBusiness().getGroups(division));
				subGroups.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_sub_group", "Select sub group"));
				subGroups.addMenuElements(getBusiness().getSubGroups(group));
				divisions.setSelectedElement(division.getPrimaryKey().toString());
				groups.setSelectedElement(group.getPrimaryKey().toString());
				subGroups.setSelectedElement(subGroup.getPrimaryKey().toString());
			}

			seasons.setSelectedElement(season.getPrimaryKey().toString());
			seasons.setDisabled(hasRegistrations);

			periods.addMenuElements(getBusiness().getAllPeriods(season));
			periods.setSelectedElement(period.getPrimaryKey().toString());
			periods.setDisabled(hasRegistrations);

			if (hasRegistrations) {
				form.add(new HiddenInput(PARAMETER_PERIOD_PK, period.getPrimaryKey().toString()));
			}

			name.setContent(course.getName());
			price.setContent(String.valueOf((int) course.getPrice()));
			cost.setContent(String.valueOf((int) course.getCost()));
			numberOfHours.setContent(String.valueOf(course.getNumberOfHours()));
			if (course.getStartDate() != null) {
				startDate.setDate(course.getStartDate());
			}
			if (course.getEndDate() != null) {
				endDate.setDate(course.getEndDate());
			}
			numberOfHours.setContent(String.valueOf(course.getNumberOfHours()));
			maxMale.setContent(String.valueOf(course.getMaxMale()));
			maxFemale.setContent(String.valueOf(course.getMaxFemale()));

			form.add(new HiddenInput(PARAMETER_COURSE_PK, coursePK.toString()));
		}

		if (iwc.isParameterSet(PARAMETER_SEASON_PK)) {
			Season season = getBusiness().getSeason(iwc.getParameter(PARAMETER_SEASON_PK));
			periods.addMenuElements(getBusiness().getAllPeriods(season));
		}
		if (iwc.isParameterSet(PARAMETER_DIVISION_PK)) {
			Group division = getBusiness().getDivision(iwc.getParameter(PARAMETER_DIVISION_PK));
			groups.addMenuElements(getBusiness().getGroups(division));
		}
		if (iwc.isParameterSet(PARAMETER_GROUP_PK)) {
			Group group = getBusiness().getGroup(iwc.getParameter(PARAMETER_DIVISION_PK));
			subGroups.addMenuElements(getBusiness().getSubGroups(group));
		}

		Layer layer;
		Label label;

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label(new Span(new Text(getResourceBundle().getLocalizedString("season", "Season"))), seasons);
		layer.add(label);
		layer.add(seasons);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label(new Span(new Text(getResourceBundle().getLocalizedString("period", "Period"))), periods);
		layer.add(label);
		layer.add(periods);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label(new Span(new Text(getResourceBundle().getLocalizedString("name", "Name"))), name);
		layer.add(label);
		layer.add(name);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label(new Span(new Text(getResourceBundle().getLocalizedString("division", "Division"))), divisions);
		layer.add(label);
		layer.add(divisions);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("group", "Group"), groups);
		layer.add(label);
		layer.add(groups);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("sub_group", "Sub group"), subGroups);
		layer.add(label);
		layer.add(subGroups);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label(new Span(new Text(getResourceBundle().getLocalizedString("price", "Price"))), price);
		layer.add(label);
		layer.add(price);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label(new Span(new Text(getResourceBundle().getLocalizedString("cost", "Cost"))), cost);
		layer.add(label);
		layer.add(cost);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label();
		label.add(new Span(new Text(getResourceBundle().getLocalizedString("start_date", "Start date"))));
		layer.add(label);
		layer.add(startDate);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label();
		label.add(new Span(new Text(getResourceBundle().getLocalizedString("end_date", "End date"))));
		layer.add(label);
		layer.add(endDate);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label(new Span(new Text(getResourceBundle().getLocalizedString("number_of_hours", "Number of hours"))), numberOfHours);
		layer.add(label);
		layer.add(numberOfHours);
		section.add(layer);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");

		section.add(clearLayer);

		Heading1 heading = new Heading1(getResourceBundle().getLocalizedString("course.estimate_heading", "Declare estimates"));
		heading.setStyleClass("subHeader");
		form.add(heading);

		section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("course.estimates_help", "Fill in the desired values and click 'Save'.")));
		section.add(helpLayer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label(getResourceBundle().getLocalizedString("max_male", "Max male"), maxMale);
		layer.add(label);
		layer.add(maxMale);
		section.add(layer);

		layer = new Layer(Layer.DIV);
		layer.setStyleClass("formItem");
		layer.setStyleClass("required");
		label = new Label(getResourceBundle().getLocalizedString("max_female", "Max female"), maxFemale);
		layer.add(label);
		layer.add(maxFemale);
		section.add(layer);

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

	private boolean saveCourse(IWContext iwc) {
		Object pk = iwc.getParameter(PARAMETER_COURSE_PK);
		Object periodPK = iwc.isParameterSet(PARAMETER_PERIOD_PK) ? iwc.getParameter(PARAMETER_PERIOD_PK) : null;
		String name = iwc.isParameterSet(PARAMETER_NAME) ? iwc.getParameter(PARAMETER_NAME) : null;
		Object divisionPK = iwc.isParameterSet(PARAMETER_DIVISION_PK) ? iwc.getParameter(PARAMETER_DIVISION_PK) : null;
		Object groupPK = iwc.isParameterSet(PARAMETER_GROUP_PK) ? iwc.getParameter(PARAMETER_GROUP_PK) : null;
		Object subGroupPK = iwc.isParameterSet(PARAMETER_SUB_GROUP_PK) ? iwc.getParameter(PARAMETER_SUB_GROUP_PK) : null;
		IWTimestamp startDate = iwc.isParameterSet(PARAMETER_START_DATE) ? new IWTimestamp(iwc.getParameter(PARAMETER_START_DATE)) : null;
		IWTimestamp endDate = iwc.isParameterSet(PARAMETER_END_DATE) ? new IWTimestamp(iwc.getParameter(PARAMETER_END_DATE)) : null;

		float price = 0;
		try {
			if (iwc.isParameterSet(PARAMETER_PRICE)) {
				String priceString = iwc.getParameter(PARAMETER_PRICE);
				priceString = TextSoap.findAndReplace(priceString, ",", "");
				priceString = TextSoap.findAndReplace(priceString, ".", "");

				price = Float.parseFloat(priceString);
			}
		}
		catch (NumberFormatException nfe) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.illegal_price_value", "You have entered an illegal value."));
			return false;
		}

		float cost = 0;
		try {
			cost = iwc.isParameterSet(PARAMETER_COST) ? Float.parseFloat(iwc.getParameter(PARAMETER_COST)) : 0;
		}
		catch (NumberFormatException nfe) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.illegal_cost_value", "You have entered an illegal value."));
			return false;
		}

		int numberOfHours = 0;
		try {
			numberOfHours = iwc.isParameterSet(PARAMETER_NUMBER_OF_HOURS) ? Integer.parseInt(iwc.getParameter(PARAMETER_NUMBER_OF_HOURS)) : 0;
		}
		catch (NumberFormatException nfe) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.illegal_number_of_hours_value", "You have entered an illegal value."));
			return false;
		}

		int maxMale = 0;
		try {
			maxMale = iwc.isParameterSet(PARAMETER_MAX_MALE) ? Integer.parseInt(iwc.getParameter(PARAMETER_MAX_MALE)) : 0;
		}
		catch (NumberFormatException nfe) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.illegal_max_male_value", "You have entered an illegal value."));
			return false;
		}

		int maxFemale = 0;
		try {
			maxFemale = iwc.isParameterSet(PARAMETER_MAX_FEMALE) ? Integer.parseInt(iwc.getParameter(PARAMETER_MAX_FEMALE)) : 0;
		}
		catch (NumberFormatException nfe) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.illegal_max_female_value", "You have entered an illegal value."));
			return false;
		}

		if (maxMale == 0 && maxFemale == 0) {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.must_set_max_participants_value", "You have to enter a max participants value."));
			return false;
		}

		try {
			if (name == null) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.must_set_name", "You have to provide a name"));
				return false;
			}
			if (periodPK == null || periodPK.toString().equals("-1")) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.must_select_period", "You have to select a period"));
				return false;
			}
			if (divisionPK == null) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.must_select_division", "You have to select a division"));
				return false;
			}
			if (startDate == null) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.must_select_start_date", "You have to select a start date"));
				return false;
			}
			if (endDate == null) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.must_select_end_date", "You have to select an end date"));
				return false;
			}
			if (price < 0) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.must_set_price", "You have to provide a price"));
				return false;
			}

			Period period = getBusiness().getPeriod(periodPK);
			IWTimestamp periodStart = new IWTimestamp(period.getStartDate());
			IWTimestamp periodEnd = new IWTimestamp(period.getEndDate());
			if (!(startDate.isBetween(periodStart, periodEnd) || startDate.isEqualTo(periodEnd))) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.start_date_invalid", "The selected start date is not within the selected period"));
				return false;
			}
			if (!(endDate.isBetween(periodStart, periodEnd) || endDate.isEqualTo(periodEnd))) {
				getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("course_editor_error.end_date_invalid", "The selected end date is not within the selected period"));
				return false;
			}

			IWTimestamp stamp = new IWTimestamp(startDate);
			stamp.addWeeks(period.getMinimumWeeks());
			stamp.addDays(-3);
			if (stamp.isLaterThan(endDate)) {
				Object[] arguments = { String.valueOf(period.getMinimumWeeks()) };
				getParentPage().setAlertOnLoad(MessageFormat.format(getResourceBundle().getLocalizedString("course_editor_error.minimum_weeks_error", "Courses for the selected period must be at least {0} week/s."), arguments));
				return false;
			}

			Constant constant = getBusiness().getConstant(period, FSKConstants.CONSTANT_COURSE_CREATE);
			if (constant != null) {
				stamp = new IWTimestamp();
				stamp.setAsDate();

				IWTimestamp constantStart = new IWTimestamp(constant.getStartDate());
				IWTimestamp constantEnd = new IWTimestamp(constant.getEndDate());
				if (!(stamp.isBetween(constantStart, constantEnd) || stamp.isEqualTo(constantEnd))) {
					Object[] arguments = { constantStart.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT), constantEnd.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT) };
					getParentPage().setAlertOnLoad(MessageFormat.format(getResourceBundle().getLocalizedString("course_editor_error.period_not_open_for_course_creation", "The selected period is not open for course creation.  Course creation can occur between {0} and {1}."), arguments));
					return false;
				}
			}

			getBusiness().storeCourse(pk, getCompany(iwc), periodPK, name, divisionPK, groupPK, subGroupPK, price, cost, startDate.getDate(), endDate.getDate(), numberOfHours, maxMale, maxFemale, iwc.getCurrentUser());

			Form form = new Form();
			add(form);

			Layer layer = new Layer(Layer.DIV);
			layer.setStyleClass("receipt");
			form.add(layer);

			Layer image = new Layer(Layer.DIV);
			image.setStyleClass("receiptImage");
			layer.add(image);

			Heading1 heading = new Heading1(getResourceBundle().getLocalizedString("course.course_created_heading", "Course stored"));
			layer.add(heading);

			constant = getBusiness().getConstant(period, FSKConstants.CONSTANT_REGISTER_PARTICIPANTS);
			Object[] arguments = { new IWTimestamp(constant.getEndDate()).getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.LONG) };

			Paragraph paragraph = new Paragraph();
			paragraph.add(new Text(MessageFormat.format(getResourceBundle().getLocalizedString("course.course_created_text", "Your course has been stored."), arguments)));
			layer.add(paragraph);

			Layer bottom = new Layer(Layer.DIV);
			bottom.setStyleClass("bottom");
			form.add(bottom);

			Link home = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
			home.setStyleClass("buttonHome");
			home.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));
			bottom.add(home);
		}
		catch (CreateException ce) {
			add(ce.getMessage());
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}

		return true;
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