/*
 * $Id: ParticipantsList.java,v 1.1 2008/07/29 12:57:41 anton Exp $
 * Created on Jun 14, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.accounting.business.CitizenBusiness;
import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.ParticipantComparator;
import is.idega.idegaweb.egov.company.business.ParticipantHolder;
import is.idega.idegaweb.egov.company.business.ParticipantHolderComparator;
import is.idega.idegaweb.egov.company.business.output.ParticipantsWriter;
import is.idega.idegaweb.egov.company.data.Course;
import is.idega.idegaweb.egov.company.data.Participant;
import is.idega.idegaweb.egov.company.data.ParticipantDiscount;
import is.idega.idegaweb.egov.company.data.PaymentAllocation;
import is.idega.idegaweb.egov.company.data.Season;

import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.FinderException;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.PostalCode;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Break;
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
import com.idega.presentation.ui.TextInput;
import com.idega.presentation.ui.util.SelectorUtility;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.Name;

public class ParticipantsList extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";

	public static final String PARAMETER_COURSE_PK = "prm_course_pk";
	public static final String PARAMETER_USER_COURSE_PK = "prm_user_course_pk";
	public static final String PARAMETER_GROUP_PK = "prm_group_pk";
	public static final String PARAMETER_DIVISION_PK = "prm_division_pk";
	public static final String PARAMETER_SUB_GROUP_PK = "prm_sub_group_pk";
	public static final String PARAMETER_COMPANY_PK = "prm_company_pk";
	public static final String PARAMETER_SEASON_PK = "prm_season_pk";
	public static final String PARAMETER_PERIOD_PK = "prm_period_pk";
	public static final String PARAMETER_USER_PK = "prm_user_pk";
	public static final String PARAMETER_SORT = "prm_sort";
	public static final String PARAMETER_DISCOUNT = "prm_discount";

	public static final String PARAMETER_DELETE = "prm_delete";
	public static final String PARAMETER_DEACTIVATE = "prm_deactivate";
	public static final String PARAMETER_ACTIVATE = "prm_activate";

	private static final int ACTION_VIEW = 1;
	//private static final int ACTION_LOCK = 2;
	private static final int ACTION_DELETE = 2;
	private static final int ACTION_ACTIVATE = 3;
	private static final int ACTION_DEACTIVATE = 4;
	private static final int ACTION_DISCOUNT_FORM = 5;
	private static final int ACTION_DISCOUNT = 6;

	private static final String SORT_NAME = "sort_name";
	private static final String SORT_DATE = "sort_date";

	private boolean showLegend = false;

	public void present(IWContext iwc) {
		try {
			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				/*case ACTION_LOCK:
					lockAllocations(iwc);
					break;*/

				case ACTION_DELETE:
					deleteParticipants(iwc);
					break;

				case ACTION_ACTIVATE:
					activateParticipant(iwc);
					break;

				case ACTION_DEACTIVATE:
					deactivateParticipant(iwc);
					break;

				case ACTION_DISCOUNT_FORM:
					showDiscountForm(iwc);
					break;

				case ACTION_DISCOUNT:
					saveDiscount(iwc);
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
		form.setID("participantsList");
		form.setStyleClass("adminForm");
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));

		form.add(getNavigation(iwc));
		if (iwc.isParameterSet(PARAMETER_DIVISION_PK) || iwc.isParameterSet(PARAMETER_COURSE_PK)) {
			form.add(getPrintouts(iwc));
		}
		form.add(getParticipants(iwc));
		if (showLegend) {
			form.add(getLegend());
		}

		Layer buttonLayer = new Layer();
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);

		/*if (iwc.isParameterSet(PARAMETER_COURSE_PK)) {
			Course course = getBusiness().getCourse(iwc.getParameter(PARAMETER_COURSE_PK));
			Period period = course.getPeriod();

			//boolean canLock = false;
			Constant constant = getBusiness().getConstant(period, FSKConstants.CONSTANT_COMPANY_CLEANUP);
			if (constant != null) {
				IWTimestamp stamp = new IWTimestamp();
				IWTimestamp begin = new IWTimestamp(constant.getStartDate());
				IWTimestamp end = new IWTimestamp(constant.getEndDate());

				canLock = stamp.isBetween(begin, end);
			}

			if (canLock) {
				SubmitButton lock = new SubmitButton(getResourceBundle().getLocalizedString("participants_list.lock_allocations", "Lock allocations"), PARAMETER_ACTION, String.valueOf(ACTION_LOCK));
				lock.setSubmitConfirm(getResourceBundle().getLocalizedString("participants_list.confirm_lock", "Are you sure you want to lock the selected allocations?"));
				buttonLayer.add(lock);
			}
		}*/

		SubmitButton deactivate = new SubmitButton(PARAMETER_DEACTIVATE, getResourceBundle().getLocalizedString("participants_list.deactivate_participants", "Deactivate participants"));
		deactivate.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_DEACTIVATE));
		deactivate.setSubmitConfirm(getResourceBundle().getLocalizedString("participants_list.confirm_deactivate", "Are you sure you want to deactivate the selected participants?"));
		deactivate.setToEnableWhenChecked(PARAMETER_USER_PK);
		buttonLayer.add(deactivate);

		SubmitButton activate = new SubmitButton(PARAMETER_ACTIVATE, getResourceBundle().getLocalizedString("participants_list.activate_participants", "Activate participants"));
		activate.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_ACTIVATE));
		activate.setSubmitConfirm(getResourceBundle().getLocalizedString("participants_list.confirm_reactivate", "Are you sure you want to activate the selected participants?"));
		activate.setToEnableWhenChecked(PARAMETER_USER_PK);
		buttonLayer.add(activate);

		SubmitButton remove = new SubmitButton(PARAMETER_DELETE, getResourceBundle().getLocalizedString("participants_list.delete_participants", "Delete participants"));
		remove.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_DELETE));
		remove.setSubmitConfirm(getResourceBundle().getLocalizedString("participants_list.confirm_delete", "Are you sure you want to delete the selected participants?"));
		remove.setToEnableWhenChecked(PARAMETER_USER_PK);
		buttonLayer.add(remove);

		if (getBackPage() != null) {
			GenericButton back = new GenericButton(getResourceBundle().getLocalizedString("back", "Back"));
			back.setPageToOpen(getBackPage());
			buttonLayer.add(back);
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
		seasons.setOnChange("changeAllCourses('" + iwc.getCurrentLocale().getCountry() + "')");
		seasons.keepStatusOnAction(true);
		if (company == null) {
			seasons.setDisabled(true);
		}

		DropdownMenu periods = new DropdownMenu(PARAMETER_PERIOD_PK);
		periods.addMenuElementFirst("-1", getResourceBundle().getLocalizedString("select_period", "Select period"));
		periods.setOnChange("changeAllCourses('" + iwc.getCurrentLocale().getCountry() + "')");
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
		divisions.setOnChange("changeGroups('" + FSKConstants.GROUP_TYPE_GROUP + "', '" + iwc.getCurrentLocale().getCountry() + "', true)");
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
		groups.setOnChange("changeSubGroups('" + FSKConstants.GROUP_TYPE_SUB_GROUP + "', '" + iwc.getCurrentLocale().getCountry() + "', true)");
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
		subGroups.setOnChange("changeAllCourses('" + iwc.getCurrentLocale().getCountry() + "')");
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

		DropdownMenu courses = new DropdownMenu(PARAMETER_COURSE_PK);
		courses.setId(PARAMETER_COURSE_PK);
		courses.keepStatusOnAction(true);
		if (company == null) {
			courses.setDisabled(true);
		}

		if (company != null) {
			Map map = getBusiness().getAllCourses(company.getPrimaryKey().toString(), iwc.getParameter(PARAMETER_SEASON_PK), iwc.getParameter(PARAMETER_PERIOD_PK), iwc.getParameter(PARAMETER_DIVISION_PK), iwc.getParameter(PARAMETER_GROUP_PK), iwc.getParameter(PARAMETER_SUB_GROUP_PK), iwc.getCurrentLocale().getCountry());
			Iterator iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String value = (String) map.get(key);
				courses.addMenuElement(key, value);
			}
		}
		else {
			courses.addMenuElementFirst("", getResourceBundle().getLocalizedString("select_course", "Select course"));
		}

		DropdownMenu sort = new DropdownMenu(PARAMETER_SORT);
		sort.addMenuElement(SORT_NAME, getResourceBundle().getLocalizedString("sort.name", "Name"));
		sort.addMenuElement(SORT_DATE, getResourceBundle().getLocalizedString("sort.allocation_date", "Date"));
		sort.keepStatusOnAction(true);
		if (company == null) {
			sort.setDisabled(true);
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

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("course", "Course"), courses);
		formItem.add(label);
		formItem.add(courses);
		layer.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("sort", "Sort"), sort);
		formItem.add(label);
		formItem.add(sort);
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

	public Layer getPrintouts(IWContext iwc) {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("printIcons");

		layer.add(getXLSLink(iwc));

		return layer;
	}

	protected Link getXLSLink(IWContext iwc) {
		DownloadLink link = new DownloadLink(getBundle().getImage("xls.gif"));
		link.setStyleClass("xls");
		link.setTarget(Link.TARGET_NEW_WINDOW);
		link.maintainParameter(PARAMETER_COURSE_PK, iwc);
		link.maintainParameter(PARAMETER_COMPANY_PK, iwc);
		link.maintainParameter(PARAMETER_SEASON_PK, iwc);
		link.maintainParameter(PARAMETER_PERIOD_PK, iwc);
		link.maintainParameter(PARAMETER_DIVISION_PK, iwc);
		link.maintainParameter(PARAMETER_GROUP_PK, iwc);
		link.maintainParameter(PARAMETER_SUB_GROUP_PK, iwc);
		link.setMediaWriterClass(ParticipantsWriter.class);

		return link;
	}

	protected Table2 getParticipants(IWContext iwc) throws RemoteException {
		Table2 table = new Table2();
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");
		table.setWidth("100%");
		table.setCellpadding(0);
		table.setCellspacing(0);

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("number");
		cell.add(Text.getNonBrakingSpace());

		cell = row.createHeaderCell();
		cell.setStyleClass("name");
		cell.add(new Text(getResourceBundle().getLocalizedString("name", "Name")));

		cell = row.createHeaderCell();
		cell.setStyleClass("personalID");
		cell.add(new Text(getResourceBundle().getLocalizedString("personal_id", "Personal ID")));

		/*cell = row.createHeaderCell();
		cell.setStyleClass("season");
		cell.add(new Text(getResourceBundle().getLocalizedString("season", "Season")));

		cell = row.createHeaderCell();
		cell.setStyleClass("period");
		cell.add(new Text(getResourceBundle().getLocalizedString("period", "Period")));*/

		cell = row.createHeaderCell();
		cell.setStyleClass("division");
		cell.add(new Text(getResourceBundle().getLocalizedString("division", "Division")));

		cell = row.createHeaderCell();
		cell.setStyleClass("name");
		cell.add(new Text(getResourceBundle().getLocalizedString("course", "Course")));

		cell = row.createHeaderCell();
		cell.setStyleClass("price");
		cell.add(new Text(getResourceBundle().getLocalizedString("price", "Price")));

		cell = row.createHeaderCell();
		cell.setStyleClass("allocationDate");
		cell.add(new Text(getResourceBundle().getLocalizedString("parent_allocation_date", "Parent allocation date")));

		cell = row.createHeaderCell();
		cell.setStyleClass("allocation");
		cell.add(new Text(getResourceBundle().getLocalizedString("parent_allocation", "Parent allocation")));

		cell = row.createHeaderCell();
		cell.setStyleClass("remainder");
		cell.add(new Text(getResourceBundle().getLocalizedString("remainder", "Remainder")));

		cell = row.createHeaderCell();
		cell.setStyleClass("discount");
		cell.add(Text.getNonBrakingSpace());

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("remove");
		cell.add(Text.getNonBrakingSpace());

		group = table.createBodyRowGroup();
		int iRow = 1;

		Course course = null;
		List users = new ArrayList();
		List courses = new ArrayList();
		if (iwc.isParameterSet(PARAMETER_COURSE_PK)) {
			course = getBusiness().getCourse(iwc.getParameter(PARAMETER_COURSE_PK));
			if (course != null) {
				Group courseGroup = course.getGroup();
				users = new ArrayList(getBusiness().getUserBusiness().getUsersInGroup(courseGroup));

				if (iwc.isParameterSet(PARAMETER_SORT)) {
					if (iwc.getParameter(PARAMETER_SORT).equals(SORT_DATE)) {
						Collections.sort(users, new ParticipantComparator(iwc, iwc.getCurrentLocale(), course));
					}
				}
			}
		}
		else if (iwc.isParameterSet(PARAMETER_DIVISION_PK)) {
			courses = new ArrayList(getBusiness().getCourses(iwc.getParameter(PARAMETER_SEASON_PK), iwc.getParameter(PARAMETER_PERIOD_PK), getCompany(iwc) != null ? getCompany(iwc).getPrimaryKey().toString() : iwc.getParameter(PARAMETER_COMPANY_PK), iwc.getParameter(PARAMETER_DIVISION_PK), iwc.getParameter(PARAMETER_GROUP_PK), iwc.getParameter(PARAMETER_SUB_GROUP_PK)));
			Iterator iterator = courses.iterator();
			while (iterator.hasNext()) {
				Course theCourse = (Course) iterator.next();
				Group courseGroup = theCourse.getGroup();
				Collection courseUsers = getBusiness().getUserBusiness().getUsersInGroup(courseGroup);
				Iterator iter = courseUsers.iterator();
				while (iter.hasNext()) {
					User courseUser = (User) iter.next();
					users.add(new ParticipantHolder(getBusiness().getParticipant(courseUser), theCourse));
				}
			}

			boolean sortDate = iwc.isParameterSet(PARAMETER_SORT) && iwc.getParameter(PARAMETER_SORT).equals(SORT_DATE);
			Collections.sort(users, new ParticipantHolderComparator(iwc, iwc.getCurrentLocale(), sortDate));
		}

		NumberFormat format = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());
		format.setMinimumFractionDigits(0);

		List addedUsers = new ArrayList();

		int numberOfParticipants = 0;
		Iterator iter = users.iterator();
		while (iter.hasNext()) {
			Participant participant = null;

			Object nextItem = iter.next();
			if (nextItem instanceof Participant) {
				participant = (Participant) nextItem;
			}
			else if (nextItem instanceof User) {
				participant = getBusiness().getParticipant((User) nextItem);
			}
			else if (nextItem instanceof ParticipantHolder) {
				ParticipantHolder holder = (ParticipantHolder) nextItem;
				participant = holder.getParticipant();
				course = holder.getCourse();
			}

			boolean showNumber = false;
			if (!addedUsers.contains(participant)) {
				numberOfParticipants++;
				showNumber = true;
				addedUsers.add(participant);
			}
			if (participant == null) {
				continue;
			}

			ParticipantDiscount discount = getBusiness().getDiscount(participant, course);
			Name name = new Name(participant.getFirstName(), participant.getMiddleName(), participant.getLastName());
			float price = course.getPrice();
			if (discount != null) {
				if (discount.isAmount()) {
					price = price - discount.getDiscount();
				}
				else if (discount.isPercentage()) {
					price = price * (1 - discount.getDiscount());
				}
			}

			float allocation = getBusiness().getAllocation(course, participant);
			float remainder = price - allocation;
			boolean active = participant != null ? participant.isActive(course) : true;
			PaymentAllocation latestAllocation = getBusiness().getLatestAllocation(course, participant);

			row = group.createRow();

			Group division = getBusiness().getDivision(course);

			Link link = new Link(getBundle().getImage("edit.png", getResourceBundle().getLocalizedString("edit", "Edit")));
			link.addParameter(PARAMETER_ACTION, ACTION_DISCOUNT_FORM);
			link.addParameter(PARAMETER_USER_PK, participant.getPrimaryKey().toString());
			link.addParameter(PARAMETER_USER_COURSE_PK, course.getPrimaryKey().toString());
			link.maintainParameter(PARAMETER_SEASON_PK, iwc);
			link.maintainParameter(PARAMETER_PERIOD_PK, iwc);
			link.maintainParameter(PARAMETER_DIVISION_PK, iwc);
			link.maintainParameter(PARAMETER_GROUP_PK, iwc);
			link.maintainParameter(PARAMETER_SUB_GROUP_PK, iwc);
			link.maintainParameter(PARAMETER_COURSE_PK, iwc);

			CheckBox box = new CheckBox(PARAMETER_USER_PK, participant.getPrimaryKey().toString() + "|" + course.getPrimaryKey().toString());

			if (!active) {
				row.setStyleClass("inactive");
				showLegend = true;
			}
			if (discount != null) {
				row.setStyleClass("hasDiscount");
				showLegend = true;
			}

			cell = row.createCell();
			cell.setStyleClass("firstColumn");
			cell.setStyleClass("number");
			if (showNumber) {
				cell.add(new Text(String.valueOf(numberOfParticipants)));
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

			cell = row.createCell();
			cell.setStyleClass("name");
			cell.add(new Text(name.getName(iwc.getCurrentLocale())));

			cell = row.createCell();
			cell.setStyleClass("personalID");
			cell.add(new Text(PersonalIDFormatter.format(participant.getPersonalID(), iwc.getCurrentLocale())));

			/*cell = row.createCell();
			cell.setStyleClass("season");
			cell.add(new Text(season != null ? season.getName() : "-"));

			cell = row.createCell();
			cell.setStyleClass("period");
			cell.add(new Text(period != null ? period.getName() : "-"));*/

			cell = row.createCell();
			cell.setStyleClass("division");
			cell.add(new Text(division.getName()));

			cell = row.createCell();
			cell.setStyleClass("name");
			cell.add(new Text(course.getName()));

			cell = row.createCell();
			cell.setStyleClass("price");
			cell.add(new Text(format.format(price)));

			cell = row.createCell();
			cell.setStyleClass("allocationDate");
			if (latestAllocation != null) {
				cell.add(new Text(new IWTimestamp(latestAllocation.getEntryDate()).getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT)));
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

			cell = row.createCell();
			cell.setStyleClass("allocation");
			cell.add(new Text(format.format(allocation)));

			cell = row.createCell();
			cell.setStyleClass("remainder");
			cell.add(new Text(format.format(remainder)));

			cell = row.createCell();
			cell.setStyleClass("discount");
			cell.add(link);

			cell = row.createCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("remove");
			cell.add(box);

			if (iRow % 2 == 0) {
				row.setStyleClass("evenRow");
			}
			else {
				row.setStyleClass("oddRow");
			}
			iRow++;
		}

		group = table.createFooterRowGroup();
		row = group.createRow();

		cell = row.createCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("numberOfParticipants");
		cell.setColumnSpan(12);
		cell.add(new Text(getResourceBundle().getLocalizedString("number_of_participants", "Number of participants") + ": " + numberOfParticipants));

		return table;
	}

	private Lists getLegend() {
		Lists list = new Lists();
		list.setStyleClass("legend");

		/*ListItem item = new ListItem();
		item.setStyleClass("locked");
		item.add(new Text(getResourceBundle().getLocalizedString("participants_list.locked_allocation", "Locked allocation")));
		list.add(item);*/

		ListItem item = new ListItem();
		item.setStyleClass("inactive");
		item.add(new Text(getResourceBundle().getLocalizedString("participants_list.inactive_participant", "Inactive participant")));
		list.add(item);

		item = new ListItem();
		item.setStyleClass("hasDiscount");
		item.add(new Text(getResourceBundle().getLocalizedString("participants_list.has_discount", "Has discount")));
		list.add(item);

		return list;
	}

	private void showDiscountForm(IWContext iwc) throws RemoteException {
		Participant participant = getBusiness().getParticipantByPK(iwc.getParameter(PARAMETER_USER_PK));
		Course course = getBusiness().getCourse(iwc.getParameter(PARAMETER_USER_COURSE_PK));
		float allocation = getBusiness().getAllocation(course, participant);
		ParticipantDiscount discount = getBusiness().getDiscount(participant, course);

		Form form = new Form();
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_DISCOUNT_FORM));
		form.maintainParameter(PARAMETER_COURSE_PK);
		form.maintainParameter(PARAMETER_COMPANY_PK);
		form.maintainParameter(PARAMETER_SEASON_PK);
		form.maintainParameter(PARAMETER_PERIOD_PK);
		form.maintainParameter(PARAMETER_DIVISION_PK);
		form.maintainParameter(PARAMETER_GROUP_PK);
		form.maintainParameter(PARAMETER_SUB_GROUP_PK);
		form.maintainParameter(PARAMETER_USER_PK);
		form.maintainParameter(PARAMETER_USER_COURSE_PK);

		form.add(getPersonInfo(iwc, participant));

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");

		Heading1 heading = new Heading1(getResourceBundle().getLocalizedString("participants_list.participant_information", "Participant information"));
		heading.setStyleClass("subHeader");
		heading.setStyleClass("topSubHeader");
		form.add(heading);

		TextInput discountInput = new TextInput(PARAMETER_DISCOUNT);
		discountInput.keepStatusOnAction(true);
		if (discount != null) {
			discountInput.setValue(discount.getDiscount() > 1 ? String.valueOf((int) discount.getDiscount()) : (discount.getDiscount() * 100) + "%");
		}

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("participants_list.discount_help", "Enter an amount (5000) or a percentage (25%) to add a discount for the selected participant and course.  Leave the field empty to remove an already created discount.")));
		section.add(helpLayer);

		Layer formItem = new Layer(Layer.DIV);
		Label label = new Label();
		Layer span = new Layer();

		NumberFormat format = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.add(new Text(getResourceBundle().getLocalizedString("course", "Course")));
		span = new Layer(Layer.SPAN);
		span.add(new Text(course.getName()));
		formItem.add(label);
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.add(new Text(getResourceBundle().getLocalizedString("price", "Price")));
		span = new Layer(Layer.SPAN);
		span.add(new Text(format.format(course.getPrice())));
		formItem.add(label);
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.add(new Text(getResourceBundle().getLocalizedString("allocated", "Allocated")));
		span = new Layer(Layer.SPAN);
		span.add(new Text(format.format((allocation))));
		formItem.add(label);
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("discount", "Discount"), discountInput);
		formItem.add(label);
		formItem.add(discountInput);
		section.add(formItem);

		section.add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(getResourceBundle().getLocalizedString("save", "Save"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_DISCOUNT));
		next.setToFormSubmit(form);
		bottom.add(next);

		Link back = getButtonLink(getResourceBundle().getLocalizedString("previous", "Back"));
		back.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));
		back.setToFormSubmit(form);
		bottom.add(back);

		add(form);
	}

	private Layer getPersonInfo(IWContext iwc, User user) throws RemoteException {
		Layer layer = new Layer();
		layer.setID("personInfo");
		layer.setStyleClass("info");

		if (user != null) {
			Address address = getUserBusiness(iwc).getUsersMainAddress(user);
			PostalCode postal = null;
			if (address != null) {
				postal = address.getPostalCode();
			}

			Layer personInfo = new Layer(Layer.DIV);
			personInfo.setStyleClass("personInfo");
			personInfo.setID("name");
			personInfo.add(new Text(user.getName()));
			layer.add(personInfo);

			personInfo = new Layer(Layer.DIV);
			personInfo.setStyleClass("personInfo");
			personInfo.setID("personalID");
			personInfo.add(new Text(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale())));
			layer.add(personInfo);

			personInfo = new Layer(Layer.DIV);
			personInfo.setStyleClass("personInfo");
			personInfo.setID("address");
			personInfo.add(new Text(address.getStreetAddress()));
			layer.add(personInfo);

			personInfo = new Layer(Layer.DIV);
			personInfo.setStyleClass("personInfo");
			personInfo.setID("postal");
			personInfo.add(new Text(postal.getPostalAddress()));
			layer.add(personInfo);

			Layer clear = new Layer(Layer.DIV);
			clear.setStyleClass("Clear");
			layer.add(clear);
		}

		return layer;
	}

	/*private void lockAllocations(IWContext iwc) throws RemoteException {
		getBusiness().lockAllocations(iwc.getParameter(PARAMETER_COURSE_PK), iwc.getParameterValues(PARAMETER_USER_PK));
		showReceipt(iwc, getResourceBundle().getLocalizedString("participants_list.locked_heading", "Parent allocations locked"), getResourceBundle().getLocalizedString("participants_list.locked_text", "The selected allocations have now been locked and can not be changed by the parents."));
	}*/

	private void deactivateParticipant(IWContext iwc) throws RemoteException {
		String[] participants = iwc.getParameterValues(PARAMETER_USER_PK);
		Collection collection = getBusiness().deactivateParticipants(participants, iwc.getCurrentUser());
		Object[] arguments = { String.valueOf(collection.size()) };

		Layer layer = addReceipt(iwc, getResourceBundle().getLocalizedString("participants_list.inactivated_heading", "Participant inactivated"), MessageFormat.format(getResourceBundle().getLocalizedString("participants_list.inactivated_text", "The selected participants has now been inactivated."), arguments));

		if (!collection.isEmpty()) {
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Break());
			paragraph.add(new Text(getResourceBundle().getLocalizedString("participants_list.deactivated_successfully", "The following were deactivated successfully:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				ParticipantHolder holder = (ParticipantHolder) iterator.next();
				User user = holder.getParticipant();
				Course course = holder.getCourse();

				ListItem item = new ListItem();
				item.add(new Text(new Name(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()) + " - " + user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale()) + " (" + course.getName() + ")"));
				list.add(item);
			}
		}
	}

	private void saveDiscount(IWContext iwc) throws RemoteException {
		String amount = iwc.getParameter(PARAMETER_DISCOUNT);
		Participant participant = getBusiness().getParticipantByPK(iwc.getParameter(PARAMETER_USER_PK));
		Course course = getBusiness().getCourse(iwc.getParameter(PARAMETER_USER_COURSE_PK));
		if (getBusiness().storeDiscount(participant, course, amount)) {
			Object[] arguments = { participant.getName(), course.getName(), amount };
			if (amount == null || amount.length() == 0) {
				addReceipt(iwc, getResourceBundle().getLocalizedString("participants_list.discount_removed_heading", "Discount removed"), MessageFormat.format(getResourceBundle().getLocalizedString("participants_list.discount_removed_text", "The discount for {0} in course {1} has been removed"), arguments));
			}
			else {
				addReceipt(iwc, getResourceBundle().getLocalizedString("participants_list.discount_added_heading", "Discount added"), MessageFormat.format(getResourceBundle().getLocalizedString("participants_list.discount_added_text", "A discount has been added for {0} in the course {1}.  The discount is {2}."), arguments));
			}
		}
		else {
			getParentPage().setAlertOnLoad(getResourceBundle().getLocalizedString("participants_list.discount_error", "An error occured while storing discount.  Discounts should be either an amount (5000) or a percentage (25%)."));
			showDiscountForm(iwc);
		}
	}

	private void activateParticipant(IWContext iwc) throws RemoteException {
		String[] participants = iwc.getParameterValues(PARAMETER_USER_PK);
		Collection collection = getBusiness().activateParticipants(participants, iwc.getCurrentUser());
		Object[] arguments = { String.valueOf(collection.size()) };

		Layer layer = addReceipt(iwc, getResourceBundle().getLocalizedString("participants_list.activated_heading", "Participant reactivated"), MessageFormat.format(getResourceBundle().getLocalizedString("participants_list.activated_text", "Activation completed with {0} participants activated."), arguments));

		if (!collection.isEmpty()) {
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Break());
			paragraph.add(new Text(getResourceBundle().getLocalizedString("participants_list.activated_successfully", "The following were activated successfully:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				ParticipantHolder holder = (ParticipantHolder) iterator.next();
				User user = holder.getParticipant();
				Course course = holder.getCourse();

				ListItem item = new ListItem();
				item.add(new Text(new Name(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()) + " - " + user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale()) + " (" + course.getName() + ")"));
				list.add(item);
			}
		}
	}

	private void deleteParticipants(IWContext iwc) throws RemoteException {
		String[] participants = iwc.getParameterValues(PARAMETER_USER_PK);
		Map map = getBusiness().deleteParticipants(participants, iwc.getCurrentUser());
		Collection removed = (Collection) map.get(FSKConstants.DELETE_PARTICIPANTS_REMOVED);
		Collection nonRemoved = (Collection) map.get(FSKConstants.DELETE_PARTICIPANTS_NON_REMOVED);
		Object[] arguments = { String.valueOf(removed.size()) };

		Layer layer = nonRemoved.isEmpty() ? addReceipt(iwc, getResourceBundle().getLocalizedString("participants_list.deleted_heading", "Participants deleted"), MessageFormat.format(getResourceBundle().getLocalizedString("participants_list.deleted_text", "Deletion completed with {0} being deleted."), arguments)) : addReceiptWithNoBottom(iwc, getResourceBundle().getLocalizedString("participants_list.deleted_heading", "Participants deleted"), MessageFormat.format(getResourceBundle().getLocalizedString("participants_list.deleted_text", "Deletion completed with {0} being deleted."), arguments));

		if (!removed.isEmpty()) {
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Break());
			paragraph.add(new Text(getResourceBundle().getLocalizedString("participants_list.deleted_successfully", "The following were deleted successfully:")));
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = removed.iterator();
			while (iterator.hasNext()) {
				ParticipantHolder holder = (ParticipantHolder) iterator.next();
				User user = holder.getParticipant();
				Course course = holder.getCourse();

				ListItem item = new ListItem();
				item.add(new Text(new Name(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()) + " - " + user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale()) + " (" + course.getName() + ")"));
				list.add(item);
			}
		}

		if (!nonRemoved.isEmpty()) {
			layer = addStop(iwc, getResourceBundle().getLocalizedString("participants_list.non_deleted_heading", "Participants not deleted"), getResourceBundle().getLocalizedString("participants_list.delete_failed", "The following could not be deleted:"));

			Paragraph paragraph = new Paragraph();
			paragraph.setStyleClass("nonRemoved");
			layer.add(paragraph);

			Lists list = new Lists();
			paragraph.add(list);

			Iterator iterator = nonRemoved.iterator();
			while (iterator.hasNext()) {
				ParticipantHolder holder = (ParticipantHolder) iterator.next();
				User user = holder.getParticipant();
				Course course = holder.getCourse();

				ListItem item = new ListItem();
				item.add(new Text(new Name(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale()) + " - " + user.getFirstName(), user.getMiddleName(), user.getLastName()).getName(iwc.getCurrentLocale()) + " (" + course.getName() + ")"));
				list.add(item);
			}
		}
	}

	private Layer addReceipt(IWContext iwc, String subject, String body) throws RemoteException {
		return addReceipt(iwc, "receipt", "receiptImage", subject, body, true);
	}

	private Layer addReceiptWithNoBottom(IWContext iwc, String subject, String body) throws RemoteException {
		return addReceipt(iwc, "receipt", "receiptImage", subject, body, false);
	}

	private Layer addStop(IWContext iwc, String subject, String body) throws RemoteException {
		return addReceipt(iwc, "stop", "stopImage", subject, body, true);
	}

	private Layer addReceipt(IWContext iwc, String layerClass, String layerImageClass, String subject, String body, boolean addBottom) throws RemoteException {
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass(layerClass);
		add(layer);

		Layer image = new Layer(Layer.DIV);
		image.setStyleClass(layerImageClass);
		layer.add(image);

		Heading1 heading = new Heading1(subject);
		layer.add(heading);

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Text(body));
		layer.add(paragraph);

		if (addBottom) {
			Layer bottom = new Layer(Layer.DIV);
			bottom.setStyleClass("bottom");
			add(bottom);

			Link home = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
			home.setStyleClass("buttonHome");
			home.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));
			home.maintainParameter(PARAMETER_COMPANY_PK, iwc);
			home.maintainParameter(PARAMETER_SEASON_PK, iwc);
			home.maintainParameter(PARAMETER_PERIOD_PK, iwc);
			home.maintainParameter(PARAMETER_DIVISION_PK, iwc);
			home.maintainParameter(PARAMETER_GROUP_PK, iwc);
			home.maintainParameter(PARAMETER_SUB_GROUP_PK, iwc);
			home.maintainParameter(PARAMETER_COURSE_PK, iwc);
			bottom.add(home);
		}

		return layer;
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

	private CitizenBusiness getUserBusiness(IWApplicationContext iwac) {
		try {
			return (CitizenBusiness) IBOLookup.getServiceInstance(iwac, CitizenBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}