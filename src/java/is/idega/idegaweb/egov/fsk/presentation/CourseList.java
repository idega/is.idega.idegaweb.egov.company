/*
 * $Id: CourseList.java,v 1.1 2008/07/29 10:48:18 anton Exp $
 * Created on Jun 14, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation;

import is.idega.idegaweb.egov.fsk.FSKConstants;
import is.idega.idegaweb.egov.fsk.business.CourseComparator;
import is.idega.idegaweb.egov.fsk.business.output.CourseWriter;
import is.idega.idegaweb.egov.fsk.data.Course;
import is.idega.idegaweb.egov.fsk.data.Period;
import is.idega.idegaweb.egov.fsk.data.Season;

import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ejb.FinderException;

import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.core.builder.data.ICPage;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.DownloadLink;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.ListItem;
import com.idega.presentation.text.Lists;
import com.idega.presentation.text.Paragraph;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.GenericButton;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.util.SelectorUtility;
import com.idega.user.data.Group;
import com.idega.util.CoreConstants;

public class CourseList extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";

	private static final String PARAMETER_COURSE_PK = "prm_course_pk";
	public static final String PARAMETER_GROUP_PK = "prm_group_pk";
	public static final String PARAMETER_DIVISION_PK = "prm_division_pk";
	public static final String PARAMETER_SUB_GROUP_PK = "prm_sub_group_pk";
	public static final String PARAMETER_COMPANY_PK = "prm_company_pk";
	public static final String PARAMETER_SEASON_PK = "prm_season_pk";
	public static final String PARAMETER_PERIOD_PK = "prm_period_pk";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_CLOSE = 2;
	private static final int ACTION_OPEN = 3;

	private ICPage iResponsePage;
	private boolean showLegend = false;

	public void present(IWContext iwc) {
		try {
			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				case ACTION_CLOSE:
					close(iwc);
					break;

				case ACTION_OPEN:
					open(iwc);
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

	private void showList(IWContext iwc) throws RemoteException {
		if (!hasPermission(iwc)) {
			showNoPermission(iwc);
			return;
		}

		Form form = new Form();
		form.setID("courseList");
		form.setStyleClass("adminForm");

		form.add(getNavigation(iwc));
		if (getCompany(iwc) != null) {
			form.add(getPrintouts(iwc));
		}
		form.add(getCourses(iwc));
		if (showLegend) {
			form.add(getLegend());
		}

		Layer buttonLayer = new Layer();
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);

		if (getCompany(iwc) != null) {
			SubmitButton close = new SubmitButton(getResourceBundle().getLocalizedString("course_list.close_course", "Close course"), PARAMETER_ACTION, String.valueOf(ACTION_CLOSE));
			close.setSubmitConfirm(getResourceBundle().getLocalizedString("course_list.confirm_close", "Are you sure you want to close the selected courses?"));
			buttonLayer.add(close);

			SubmitButton open = new SubmitButton(getResourceBundle().getLocalizedString("course_list.open_course", "Close course"), PARAMETER_ACTION, String.valueOf(ACTION_OPEN));
			open.setSubmitConfirm(getResourceBundle().getLocalizedString("course_list.confirm_open", "Are you sure you want to open the selected courses?"));
			buttonLayer.add(open);
		}

		if (getBackPage() != null) {
			GenericButton back = new GenericButton(getResourceBundle().getLocalizedString("back", "Back"));
			back.setPageToOpen(getBackPage());
			buttonLayer.add(back);
		}

		add(form);
	}

	private Layer getNavigation(IWContext iwc) throws RemoteException {
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

		SelectorUtility util = new SelectorUtility();

		DropdownMenu seasons = (DropdownMenu) util.getSelectorFromIDOEntities(new DropdownMenu(PARAMETER_SEASON_PK), getBusiness().getAllSeasons());
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

		DropdownMenu divisions = new DropdownMenu(PARAMETER_DIVISION_PK);
		divisions.setId(PARAMETER_DIVISION_PK);
		divisions.setOnChange("changeGroups('" + FSKConstants.GROUP_TYPE_GROUP + "', '" + iwc.getCurrentLocale().getCountry() + "', false)");
		divisions.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_division", "Select division"));
		divisions.keepStatusOnAction(true);
		if (company == null) {
			divisions.setDisabled(true);
		}

		if (company != null) {
			Collection collection = getBusiness().getDivisions(iwc, company, iwc.getCurrentUser());
			divisions.addMenuElements(collection);
		}

		DropdownMenu groups = new DropdownMenu(PARAMETER_GROUP_PK);
		groups.setId(PARAMETER_GROUP_PK);
		groups.setOnChange("changeSubGroups('" + FSKConstants.GROUP_TYPE_SUB_GROUP + "', '" + iwc.getCurrentLocale().getCountry() + "', false)");
		groups.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_group", "Select group"));
		groups.keepStatusOnAction(true);
		if (company == null) {
			groups.setDisabled(true);
		}

		if (iwc.isParameterSet(PARAMETER_DIVISION_PK) && Integer.parseInt(iwc.getParameter(PARAMETER_DIVISION_PK)) > 0) {
			try {
				Group group = getBusiness().getUserBusiness().getGroupBusiness().getGroupByGroupID(new Integer(iwc.getParameter(PARAMETER_DIVISION_PK)).intValue());
				Collection collection = getBusiness().getGroups(group);
				groups.addMenuElements(collection);
			}
			catch (FinderException e) {
				e.printStackTrace();
			}
		}

		DropdownMenu subGroups = new DropdownMenu(PARAMETER_SUB_GROUP_PK);
		subGroups.setId(PARAMETER_SUB_GROUP_PK);
		subGroups.keepStatusOnAction(true);
		subGroups.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_sub_group", "Select sub group"));
		if (company == null) {
			subGroups.setDisabled(true);
		}

		if (iwc.isParameterSet(PARAMETER_GROUP_PK) && Integer.parseInt(iwc.getParameter(PARAMETER_GROUP_PK)) > 0) {
			try {
				Group group = getBusiness().getUserBusiness().getGroupBusiness().getGroupByGroupID(new Integer(iwc.getParameter(PARAMETER_GROUP_PK)).intValue());
				Collection collection = getBusiness().getSubGroups(group);
				subGroups.addMenuElements(collection);
			}
			catch (FinderException e) {
				e.printStackTrace();
			}
		}

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(getResourceBundle().getLocalizedString("season", "Peason"), seasons);
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
		label = new Label(getResourceBundle().getLocalizedString("division", "Division"), divisions);
		formItem.add(label);
		formItem.add(divisions);
		layer.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("group", "Group"), groups);
		formItem.add(label);
		formItem.add(groups);
		layer.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("sub_group", "Sub group"), subGroups);
		formItem.add(label);
		formItem.add(subGroups);
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

	private Layer getPrintouts(IWContext iwc) {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("printIcons");

		layer.add(getXLSLink(iwc));

		return layer;
	}

	private Link getXLSLink(IWContext iwc) {
		DownloadLink link = new DownloadLink(getBundle().getImage("xls.gif"));
		link.setStyleClass("xls");
		link.setTarget(Link.TARGET_NEW_WINDOW);
		link.maintainParameter(PARAMETER_SEASON_PK, iwc);
		link.maintainParameter(PARAMETER_PERIOD_PK, iwc);
		link.maintainParameter(PARAMETER_DIVISION_PK, iwc);
		link.maintainParameter(PARAMETER_GROUP_PK, iwc);
		link.maintainParameter(PARAMETER_SUB_GROUP_PK, iwc);
		link.maintainParameter(PARAMETER_COMPANY_PK, iwc);
		link.setMediaWriterClass(CourseWriter.class);

		return link;
	}

	private Table2 getCourses(IWContext iwc) throws RemoteException {
		Table2 table = new Table2();
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setWidth("100%");
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");

		boolean isSuperAdmin = iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc);

		Company company = getCompany(iwc);
		List courses = null;
		if (company != null && iwc.isParameterSet(PARAMETER_SEASON_PK) && iwc.isParameterSet(PARAMETER_PERIOD_PK)) {
			try {
				courses = new ArrayList(getBusiness().getCourses(iwc.getParameter(PARAMETER_SEASON_PK), iwc.getParameter(PARAMETER_PERIOD_PK), company.getPrimaryKey(), iwc.getParameter(PARAMETER_DIVISION_PK), iwc.getParameter(PARAMETER_GROUP_PK), iwc.getParameter(PARAMETER_SUB_GROUP_PK)));
			}
			catch (RemoteException rex) {
				courses = new ArrayList();
			}
			Collections.sort(courses, new CourseComparator(iwc, iwc.getCurrentLocale()));
		}

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

		if (isSuperAdmin) {
			cell = row.createHeaderCell();
			cell.setStyleClass("totalAllocation");
			cell.add(new Text(getResourceBundle().getLocalizedString("parent_allocation", "Parent allocation")));
		}

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("select");
		cell.add(Text.getNonBrakingSpace());

		NumberFormat format = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());
		format.setMinimumFractionDigits(0);

		group = table.createBodyRowGroup();
		int iRow = 1;
		if (courses != null) {
			Iterator iter = courses.iterator();
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

				CheckBox close = new CheckBox(PARAMETER_COURSE_PK, course.getPrimaryKey().toString());

				/*if (course.isApproved()) {
					row.setStyleClass("approved");
				}*/
				if (course.isClosed()) {
					row.setStyleClass("closed");
					showLegend = true;
				}

				cell = row.createCell();
				cell.setStyleClass("firstColumn");
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
				cell.setStyleClass("name");
				if (getResponsePage() != null) {
					Link link = new Link(course.getName());
					link.setPage(getResponsePage());
					link.addParameter(ParticipantsList.PARAMETER_COMPANY_PK, company.getPrimaryKey().toString());
					link.addParameter(ParticipantsList.PARAMETER_DIVISION_PK, division.getPrimaryKey().toString());
					link.addParameter(ParticipantsList.PARAMETER_COURSE_PK, course.getPrimaryKey().toString());
					if (divisionGroup != null) {
						link.addParameter(ParticipantsList.PARAMETER_GROUP_PK, divisionGroup.getPrimaryKey().toString());
					}
					if (subGroup != null) {
						link.addParameter(ParticipantsList.PARAMETER_SUB_GROUP_PK, subGroup.getPrimaryKey().toString());
					}
					cell.add(link);
				}
				else {
					cell.add(new Text(course.getName()));
				}

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

				if (isSuperAdmin) {
					cell = row.createCell();
					cell.setStyleClass("totalAllocation");
					cell.add(new Text(format.format(getBusiness().getAllocation(course))));
				}

				cell = row.createCell();
				cell.setStyleClass("lastColumn");
				cell.setStyleClass("select");
				cell.add(close);

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
		cell.setColumnSpan(isSuperAdmin ? 10 : 9);
		cell.add(new Text(getResourceBundle().getLocalizedString("number_of_courses", "Number of courses") + ": " + (iRow - 1)));

		return table;
	}

	private Lists getLegend() {
		Lists list = new Lists();
		list.setStyleClass("legend");

		/*ListItem item = new ListItem();
		item.setStyleClass("approved");
		item.add(new Text(getResourceBundle().getLocalizedString("course.approved_course", "Approved course")));
		list.add(item);*/

		ListItem item = new ListItem();
		item.setStyleClass("closed");
		item.add(new Text(getResourceBundle().getLocalizedString("course.closed_course", "Closed course")));
		list.add(item);

		return list;
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

	private void open(IWContext iwc) throws RemoteException {
		getBusiness().openCourses(iwc.getParameterValues(PARAMETER_COURSE_PK), iwc.getCurrentLocale());
		showReceipt(iwc, getResourceBundle().getLocalizedString("course_list.opened_heading", "Courses opened"), getResourceBundle().getLocalizedString("course_list.opened_text", "The selected courses have been opened."));
	}

	private void close(IWContext iwc) throws RemoteException {
		getBusiness().closeCourses(iwc.getParameterValues(PARAMETER_COURSE_PK), iwc.getCurrentLocale());
		showReceipt(iwc, getResourceBundle().getLocalizedString("course_list.closed_heading", "Courses closed"), getResourceBundle().getLocalizedString("course_list.closed_text", "The selected courses have been closed."));
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
		home.maintainParameter(PARAMETER_PERIOD_PK, iwc);
		home.maintainParameter(PARAMETER_DIVISION_PK, iwc);
		home.maintainParameter(PARAMETER_GROUP_PK, iwc);
		home.maintainParameter(PARAMETER_SUB_GROUP_PK, iwc);
		bottom.add(home);
	}

	public ICPage getResponsePage() {
		return this.iResponsePage;
	}

	public void setResponsePage(ICPage responsePage) {
		this.iResponsePage = responsePage;
	}
}