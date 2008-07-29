/*
 * $Id: CourseRegistration.java,v 1.1 2008/07/29 12:57:41 anton Exp $ Created on Jun 12, 2007
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
import is.idega.idegaweb.egov.company.data.Participant;
import is.idega.idegaweb.egov.company.data.Period;
import is.idega.idegaweb.egov.company.data.Season;

import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.FinderException;

import com.idega.block.web2.business.Web2Business;
import com.idega.business.IBOLookup;
import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.io.UploadFile;
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
import com.idega.presentation.text.ListItem;
import com.idega.presentation.text.Lists;
import com.idega.presentation.text.Paragraph;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.FileInput;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.GenericButton;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.RadioButton;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextInput;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.Name;

public class CourseRegistration extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";
	private static final String PARAMETER_COURSE_PK = "prm_course_pk";
	private static final String PARAMETER_COLUMN_NUMBER = "prm_column_number";
	private static final String PARAMETER_PERSONAL_ID = "prm_personal_id";
	private static final String PARAMETER_ADD_LINE = "prm_add_line";
	private static final String PARAMETER_NUMBER_OF_LINES = "prm_number_of_lines";

	private static final String PARAMETER_COMPANY_PK = "prm_company_pk";
	private static final String PARAMETER_SEASON_PK = "prm_season_pk";
	private static final String PARAMETER_PERIOD_PK = "prm_period_pk";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_IMPORT_FORM = 2;
	private static final int ACTION_IMPORT = 3;
	private static final int ACTION_REGISTRATION_FORM = 4;
	private static final int ACTION_REGISTRATION = 5;

	public void present(IWContext iwc) {
		try {
			Object coursePK = iwc.getParameter(PARAMETER_COURSE_PK);

			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				case ACTION_IMPORT_FORM:
					showImportForm(iwc, coursePK);
					break;

				case ACTION_IMPORT:
					importFile(iwc, coursePK);
					break;

				case ACTION_REGISTRATION_FORM:
					showRegistrationForm(iwc, coursePK);
					break;

				case ACTION_REGISTRATION:
					register(iwc, coursePK);
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

	public void showList(IWContext iwc) throws RemoteException {
		if (!hasPermission(iwc)) {
			showNoPermission(iwc);
			return;
		}

		Form form = new Form();
		form.setID("courseRegistration");
		form.setStyleClass("adminForm");
		form.addParameter(PARAMETER_ACTION, ACTION_VIEW);

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
		column.setSpan(1);
		column.setWidth("12");

		boolean isOpen = true;
		Company company = getCompany(iwc);
		Season currentSeason = getSeason(iwc);
		Period currentPeriod = getPeriod(iwc);

		List courses = null;
		if (company != null && currentSeason != null) {
			isOpen = company.isOpen();
			try {
				courses = new ArrayList(getBusiness().getApprovedCourses(currentSeason, company));
			}
			catch (RemoteException rex) {
				courses = new ArrayList();
			}
		}
		else {
			courses = new ArrayList();
		}
		Collections.sort(courses, new CourseComparator(iwc, iwc.getCurrentLocale()));

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
		cell.setStyleClass("registrations");
		cell.add(new Text(getResourceBundle().getLocalizedString("registrations", "Registrations")));

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("select");
		cell.add(new Text(getResourceBundle().getLocalizedString("select", "Select")));

		boolean showLegend = false;

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
			if (period != null && currentPeriod != null && !period.equals(currentPeriod)) {
				continue;
			}
			Season season = period != null ? period.getSeason() : null;

			boolean canRegister = true;
			Constant constant = getBusiness().getConstant(period, FSKConstants.CONSTANT_REGISTER_PARTICIPANTS);
			if (constant != null) {
				IWTimestamp stamp = new IWTimestamp();
				IWTimestamp begin = new IWTimestamp(constant.getStartDate());
				IWTimestamp end = new IWTimestamp(constant.getEndDate());

				canRegister = stamp.isBetween(begin, end);
			}

			row = group.createRow();

			if (course.isClosed()) {
				row.setStyleClass("closed");
				showLegend = true;
			}

			RadioButton button = new RadioButton(PARAMETER_COURSE_PK, course.getPrimaryKey().toString());
			if (!course.isClosed() && canRegister) {
				button.setToDisableOnClick("importer", false);
				button.setToDisableOnClick("registration", false);
			}
			button.setDisabled(course.isClosed() || !canRegister);
			button.setStyleClass("radioButton");

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("division");
			if (division != null) {
				cell.add(new Text(division.getName()));
			}
			else {
				cell.add(new Text("-"));
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
			cell.setStyleClass("course");
			cell.add(new Text(course.getName()));

			cell = row.createCell();
			cell.setStyleClass("season");
			cell.add(new Text(season != null ? season.getName() : "-"));

			cell = row.createCell();
			cell.setStyleClass("period");
			cell.add(new Text(period != null ? period.getName() : "-"));

			cell = row.createCell();
			cell.setStyleClass("estimate");
			cell.add(new Text(String.valueOf((course.getMaxMale() + course.getMaxFemale()))));

			cell = row.createCell();
			cell.setStyleClass("registrations");
			cell.add(new Text(String.valueOf(getBusiness().getNumberOfRegistrations(course))));

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("select");
			if (isOpen) {
				cell.add(button);
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

		if (showLegend) {
			form.add(getLegend());
		}

		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);

		if (getBackPage() != null) {
			GenericButton back = new GenericButton(getResourceBundle().getLocalizedString("back", "Back"));
			back.setPageToOpen(getBackPage());
			buttonLayer.add(back);
		}

		if (company != null && isOpen) {
			SubmitButton importer = new SubmitButton("importer", getResourceBundle().getLocalizedString("course.import", "Import"));
			importer.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_IMPORT_FORM));
			importer.setDisabled(true);
			buttonLayer.add(importer);

			SubmitButton registration = new SubmitButton("registration", getResourceBundle().getLocalizedString("course.register", "Register"));
			registration.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_REGISTRATION_FORM));
			registration.setDisabled(true);
			buttonLayer.add(registration);
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
			companies.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_company", "Select company"));
			companies.keepStatusOnAction(true);
			companies.setToSubmit();

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

		DropdownMenu seasons = new DropdownMenu(PARAMETER_SEASON_PK);
		seasons.addMenuElements(getBusiness().getAllSeasons());
		seasons.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_season", "Select season"));
		seasons.setOnChange("changePeriods();");
		seasons.keepStatusOnAction(true);
		if (company == null) {
			seasons.setDisabled(true);
		}

		DropdownMenu periods = new DropdownMenu(PARAMETER_PERIOD_PK);
		periods.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_period", "Select period"));
		periods.setID(PARAMETER_PERIOD_PK);
		periods.keepStatusOnAction(true);
		if (company == null) {
			periods.setDisabled(true);
		}

		if (iwc.isParameterSet(PARAMETER_SEASON_PK)) {
			Season season = getBusiness().getSeason(iwc.getParameter(PARAMETER_SEASON_PK));
			if (season != null) {
				periods.addMenuElements(getBusiness().getAllPeriods(season));
			}
		}

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(getResourceBundle().getLocalizedString("season", "Season"), seasons);
		formItem.add(label);
		formItem.add(seasons);
		layer.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("period", "Period"), periods);
		formItem.add(label);
		formItem.add(periods);
		layer.add(formItem);

		SubmitButton fetch = new SubmitButton(getResourceBundle().getLocalizedString("get", "Get"));
		fetch.setStyleClass("indentedButton");
		fetch.setStyleClass("button");
		if (company == null) {
			fetch.setDisabled(true);
		}
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		formItem.add(fetch);
		layer.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		layer.add(clearLayer);

		return layer;
	}

	private Lists getLegend() {
		Lists list = new Lists();
		list.setStyleClass("legend");

		ListItem item = new ListItem();
		item.setStyleClass("closed");
		item.add(new Text(getResourceBundle().getLocalizedString("course.closed_course", "Closed course")));
		list.add(item);

		return list;
	}

	private Lists getParticipantLegend() {
		Lists list = new Lists();
		list.setStyleClass("legend");

		ListItem item = new ListItem();
		item.setStyleClass("inactive");
		item.add(new Text(getResourceBundle().getLocalizedString("participants_list.inactive_participant", "Inactive participant")));
		list.add(item);

		return list;
	}

	private void showImportForm(IWContext iwc, Object coursePK) throws java.rmi.RemoteException {
		Form form = new Form();
		form.setMultiPart();
		form.setID("courseRegistration");
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_IMPORT_FORM));
		form.addParameter(PARAMETER_COURSE_PK, coursePK.toString());
		form.maintainParameter(PARAMETER_COMPANY_PK);
		form.maintainParameter(PARAMETER_SEASON_PK);
		form.maintainParameter(PARAMETER_PERIOD_PK);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("course_registration.import_help", "Please select the file you want to import. If the personal IDs are not located in the first column of the Excel document, please state the column number.")));
		section.add(helpLayer);

		FileInput fileInput = new FileInput();
		TextInput column = new TextInput(PARAMETER_COLUMN_NUMBER);

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(getResourceBundle().getLocalizedString("file", "File"), fileInput);
		formItem.add(label);
		formItem.add(fileInput);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("column_number", "Column number"), column);
		formItem.add(label);
		formItem.add(column);
		section.add(formItem);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		section.add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link send = getButtonLink(getResourceBundle().getLocalizedString("import", "Import"));
		send.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_IMPORT));
		send.setToFormSubmit(form);
		bottom.add(send);

		Link back = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
		back.setStyleClass("buttonHome");
		back.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));
		back.setToFormSubmit(form);
		bottom.add(back);
		add(form);
	}

	private void showRegistrationForm(IWContext iwc, Object coursePK) throws java.rmi.RemoteException {
		super.getParentPage().addJavascriptURL("/dwr/interface/FSKDWRUtil.js");
		super.getParentPage().addJavascriptURL(CoreConstants.DWR_ENGINE_SCRIPT);
		super.getParentPage().addJavascriptURL("/dwr/util.js");

		Web2Business web2bus = (Web2Business) IBOLookup.getServiceInstance(iwc, Web2Business.class);
		super.getParentPage().addJavascriptURL(web2bus.getBundleURIToMootoolsLib());
		super.getParentPage().addJavascriptURL(getBundle().getResourcesVirtualPath() + "/js/registration.js");

		Course course = getBusiness().getCourse(coursePK);
		int numberOfStudents = course.getMaxFemale() + course.getMaxMale();
		List list = new ArrayList(getBusiness().getParticipants(course));
		if (list.size() > numberOfStudents) {
			numberOfStudents = list.size();
		}

		Form form = new Form();
		form.setID("courseRegistration");
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_REGISTRATION_FORM));
		form.addParameter(PARAMETER_COURSE_PK, coursePK.toString());
		form.addParameter(PARAMETER_ADD_LINE, "");
		form.maintainParameter(PARAMETER_COMPANY_PK);
		form.maintainParameter(PARAMETER_SEASON_PK);
		form.maintainParameter(PARAMETER_PERIOD_PK);

		if (iwc.isParameterSet(PARAMETER_NUMBER_OF_LINES)) {
			numberOfStudents = Integer.parseInt(iwc.getParameter(PARAMETER_NUMBER_OF_LINES));
		}
		if (iwc.isParameterSet(PARAMETER_ADD_LINE)) {
			numberOfStudents++;
		}
		form.addParameter(PARAMETER_NUMBER_OF_LINES, String.valueOf(numberOfStudents));

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("course_registration.registration_help", "Please select the file you want to import. If the personal IDs are not located in the first column of the Excel document, please state the column number.")));
		section.add(helpLayer);

		Layer headings = new Layer(Layer.DIV);
		headings.setStyleClass("formItem");
		headings.setStyleClass("columnHeader");
		section.add(headings);

		Span span = new Span();
		span.add(new Text(getResourceBundle().getLocalizedString("personal_id", "Personal ID")));
		headings.add(span);

		span = new Span();
		span.add(new Text(getResourceBundle().getLocalizedString("name", "Name")));
		headings.add(span);

		boolean showLegend = false;
		String[] personalIDs = iwc.getParameterValues(PARAMETER_PERSONAL_ID);

		int registeredSize = list != null ? list.size() : 0;
		for (int a = 0; a < numberOfStudents; a++) {
			TextInput input = new TextInput(PARAMETER_PERSONAL_ID);
			input.setMaxlength(10);
			input.setStyleClass("personalID");

			TextInput name = new TextInput("name");
			name.setStyleClass("userResults");
			name.setDisabled(true);

			if (registeredSize >= (a + 1)) {
				User user = (User) list.get(a);
				Participant participant = getBusiness().getParticipant(user);

				input.setContent(user.getPersonalID());
				input.setDisabled(true);
				name.setContent(new Name(user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale()));

				if (!participant.isActive(course)) {
					showLegend = true;
					input.setStyleClass("inactive");
					name.setStyleClass("inactive");
				}
			}
			else if (personalIDs != null && personalIDs.length > a - registeredSize) {
				String personalID = personalIDs[a - registeredSize];
				if (personalID != null && personalID.length() > 0) {
					input.setContent(personalID);
					String userName = getBusiness().getUserName(personalID);
					if (userName != null && userName.length() > 0) {
						name.setContent(userName);
					}
				}
			}

			Layer formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			Label label = new Label(getResourceBundle().getLocalizedString("participant", "Participant") + " " + (a + 1), input);
			formItem.add(label);
			formItem.add(input);
			formItem.add(name);
			section.add(formItem);
		}

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		section.add(clearLayer);

		if (showLegend) {
			form.add(getParticipantLegend());
		}

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link send = getButtonLink(getResourceBundle().getLocalizedString("register", "Register"));
		send.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_REGISTRATION));
		send.setToFormSubmit(form);
		bottom.add(send);

		Link addLine = getButtonLink(getResourceBundle().getLocalizedString("add_line", "Add line"));
		addLine.setValueOnClick(PARAMETER_ADD_LINE, Boolean.TRUE.toString());
		addLine.setToFormSubmit(form);
		bottom.add(addLine);

		Link back = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
		back.setStyleClass("buttonHome");
		back.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));
		back.setToFormSubmit(form);
		bottom.add(back);
		add(form);
	}

	private void importFile(IWContext iwc, Object coursePK) {
		UploadFile uploadFile = iwc.getUploadedFile();
		Map map = null;
		if (uploadFile != null && uploadFile.getName() != null && uploadFile.getName().length() > 0) {
			int column = iwc.isParameterSet(PARAMETER_COLUMN_NUMBER) ? Integer.parseInt(iwc.getParameter(PARAMETER_COLUMN_NUMBER)) : 1;
			try {
				map = getBusiness().importExcelFile(uploadFile, coursePK, column);
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}

		Collection imported = (Collection) map.get(FSKConstants.REGISTRATION_CODE_REGISTERED);
		Collection alreadyImported = (Collection) map.get(FSKConstants.REGISTRATION_CODE_ALREADY_REGISTERED);
		Collection outsideCommune = (Collection) map.get(FSKConstants.REGISTRATION_CODE_OUTSIDE_COMMUNE);
		Collection outsideAgeRange = (Collection) map.get(FSKConstants.REGISTRATION_CODE_OUTSIDE_AGE_RANGE);
		Collection invalidPersonalID = (Collection) map.get(FSKConstants.REGISTRATION_CODE_INVALID_PERSONAL_ID);
		Collection noUserFound = (Collection) map.get(FSKConstants.REGISTRATION_CODE_NO_USER_FOUND);

		Form form = new Form();
		add(form);

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("receipt");
		form.add(layer);

		Layer image = new Layer(Layer.DIV);
		image.setStyleClass("receiptImage");
		layer.add(image);

		Heading1 heading = new Heading1(getResourceBundle().getLocalizedString("course_registration.import_heading", "File imported"));
		layer.add(heading);

		Object[] arguments = { String.valueOf(imported.size()) };

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Text(MessageFormat.format(getResourceBundle().getLocalizedString("course_registration.import_text", "The file has been imported and {0} users were added to the course."), arguments)));
		layer.add(paragraph);

		if (!alreadyImported.isEmpty()) {
			paragraph = new Paragraph();
			paragraph.add(new Text(getResourceBundle().getLocalizedString("course_registration.already_registered", "The following are already registered to the course:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = alreadyImported.iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();

				ListItem item = new ListItem();
				item.add(new Text(new Name(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()) + " - " + user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale())));
				list.add(item);
			}
		}

		if (!outsideAgeRange.isEmpty()) {
			paragraph = new Paragraph();
			paragraph.add(new Text(getResourceBundle().getLocalizedString("course_registration.outside_age_range", "The following are outside age range:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = outsideAgeRange.iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();

				ListItem item = new ListItem();
				item.add(new Text(new Name(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()) + " - " + user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale())));
				list.add(item);
			}
		}

		if (!outsideCommune.isEmpty()) {
			paragraph = new Paragraph();
			paragraph.add(new Text(getResourceBundle().getLocalizedString("course_registration.outside_commune", "The following do not live in the commune:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = outsideCommune.iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();

				ListItem item = new ListItem();
				item.add(new Text(new Name(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()) + " - " + user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale())));
				list.add(item);
			}
		}

		if (!invalidPersonalID.isEmpty()) {
			paragraph = new Paragraph();
			paragraph.add(new Text(getResourceBundle().getLocalizedString("course_registration.invalid_personal_id", "The following personal ID(s) is/are invalid:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = invalidPersonalID.iterator();
			while (iterator.hasNext()) {
				String personalID = (String) iterator.next();

				ListItem item = new ListItem();
				item.add(new Text(personalID));
				list.add(item);
			}
		}

		if (!noUserFound.isEmpty()) {
			paragraph = new Paragraph();
			paragraph.add(new Text(getResourceBundle().getLocalizedString("course_registration.no_user_found", "The following personal ID(s) returned no user/s:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = noUserFound.iterator();
			while (iterator.hasNext()) {
				String personalID = (String) iterator.next();

				ListItem item = new ListItem();
				item.add(new Text(personalID));
				list.add(item);
			}
		}

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link home = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
		home.setStyleClass("buttonHome");
		home.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));
		home.maintainParameter(PARAMETER_COMPANY_PK, iwc);
		home.maintainParameter(PARAMETER_SEASON_PK, iwc);
		home.maintainParameter(PARAMETER_PERIOD_PK, iwc);
		bottom.add(home);
	}

	private void register(IWContext iwc, Object coursePK) {
		String[] personalIDs = iwc.getParameterValues(PARAMETER_PERSONAL_ID);
		Map map = null;
		if (personalIDs != null && personalIDs.length > 0) {
			try {
				map = getBusiness().registerParticipants(personalIDs, coursePK);
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}

		Collection imported = (Collection) map.get(FSKConstants.REGISTRATION_CODE_REGISTERED);
		Collection alreadyImported = (Collection) map.get(FSKConstants.REGISTRATION_CODE_ALREADY_REGISTERED);
		Collection outsideCommune = (Collection) map.get(FSKConstants.REGISTRATION_CODE_OUTSIDE_COMMUNE);
		Collection outsideAgeRange = (Collection) map.get(FSKConstants.REGISTRATION_CODE_OUTSIDE_AGE_RANGE);
		Collection invalidPersonalID = (Collection) map.get(FSKConstants.REGISTRATION_CODE_INVALID_PERSONAL_ID);
		Collection noUserFound = (Collection) map.get(FSKConstants.REGISTRATION_CODE_NO_USER_FOUND);

		Form form = new Form();
		add(form);

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("receipt");
		form.add(layer);

		Layer image = new Layer(Layer.DIV);
		image.setStyleClass("receiptImage");
		layer.add(image);

		Heading1 heading = new Heading1(getResourceBundle().getLocalizedString("course_registration.registration_heading", "Registration completed"));
		layer.add(heading);

		Object[] arguments = { String.valueOf(imported.size()) };

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Text(MessageFormat.format(getResourceBundle().getLocalizedString("course_registration.registration_text", "The registration has been completed and {0} users were added to the course."), arguments)));
		layer.add(paragraph);

		if (!alreadyImported.isEmpty()) {
			paragraph = new Paragraph();
			paragraph.add(new Text(getResourceBundle().getLocalizedString("course_registration.already_registered", "The following are already registered to the course:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = alreadyImported.iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();

				ListItem item = new ListItem();
				item.add(new Text(new Name(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()) + " - " + user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale())));
				list.add(item);
			}
		}

		if (!outsideAgeRange.isEmpty()) {
			paragraph = new Paragraph();
			paragraph.add(new Text(getResourceBundle().getLocalizedString("course_registration.outside_age_range", "The following are outside age range:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = outsideAgeRange.iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();

				ListItem item = new ListItem();
				item.add(new Text(new Name(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()) + " - " + user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale())));
				list.add(item);
			}
		}

		if (!outsideCommune.isEmpty()) {
			paragraph = new Paragraph();
			paragraph.add(new Text(getResourceBundle().getLocalizedString("course_registration.outside_commune", "The following do not live in the commune:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = outsideCommune.iterator();
			while (iterator.hasNext()) {
				User user = (User) iterator.next();

				ListItem item = new ListItem();
				item.add(new Text(new Name(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()) + " - " + user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale())));
				list.add(item);
			}
		}

		if (!invalidPersonalID.isEmpty()) {
			paragraph = new Paragraph();
			paragraph.add(new Text(getResourceBundle().getLocalizedString("course_registration.invalid_personal_id", "The following personal ID(s) is/are invalid:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = invalidPersonalID.iterator();
			while (iterator.hasNext()) {
				String personalID = (String) iterator.next();

				ListItem item = new ListItem();
				item.add(new Text(personalID));
				list.add(item);
			}
		}

		if (!noUserFound.isEmpty()) {
			paragraph = new Paragraph();
			paragraph.add(new Text(getResourceBundle().getLocalizedString("course_registration.no_user_found", "The following personal ID(s) returned no user/s:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = noUserFound.iterator();
			while (iterator.hasNext()) {
				String personalID = (String) iterator.next();

				ListItem item = new ListItem();
				item.add(new Text(personalID));
				list.add(item);
			}
		}

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link home = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
		home.setStyleClass("buttonHome");
		home.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));
		home.maintainParameter(PARAMETER_COMPANY_PK, iwc);
		home.maintainParameter(PARAMETER_SEASON_PK, iwc);
		home.maintainParameter(PARAMETER_PERIOD_PK, iwc);
		bottom.add(home);
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

	private Period getPeriod(IWContext iwc) {
		Period period = null;
		if (iwc.isParameterSet(PARAMETER_PERIOD_PK)) {
			try {
				period = getBusiness().getPeriod(new Integer(iwc.getParameter(PARAMETER_PERIOD_PK)));
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}

		return period;
	}
}