/*
 * $Id: ParentAllocation.java,v 1.1 2008/07/29 10:48:18 anton Exp $
 * Created on Jun 17, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation;

import is.idega.idegaweb.egov.accounting.business.CitizenBusiness;
import is.idega.idegaweb.egov.application.presentation.ApplicationForm;
import is.idega.idegaweb.egov.fsk.FSKConstants;
import is.idega.idegaweb.egov.fsk.business.ApplicationSession;
import is.idega.idegaweb.egov.fsk.business.FSKBusiness;
import is.idega.idegaweb.egov.fsk.data.Constant;
import is.idega.idegaweb.egov.fsk.data.Course;
import is.idega.idegaweb.egov.fsk.data.Participant;
import is.idega.idegaweb.egov.fsk.data.ParticipantDiscount;
import is.idega.idegaweb.egov.fsk.data.PaymentAllocation;
import is.idega.idegaweb.egov.fsk.data.Period;
import is.idega.idegaweb.egov.fsk.data.Season;

import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.core.builder.data.ICPage;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.idegaweb.IWUserContext;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.TextInput;
import com.idega.user.business.UserSession;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
import com.idega.util.text.TextSoap;

public class ParentAllocation extends ApplicationForm {

	protected static final String PARAMETER_ACTION = "prm_action";
	public static final String PARAMETER_CHILD_PK = "prm_child_pk";
	public static final String PARAMETER_SEASON = "prm_season";
	protected static final String PARAMETER_COURSE = "prm_course";
	protected static final String PARAMETER_ALLOCATION = "prm_allocation";

	protected static final int ACTION_FORM = 1;
	protected static final int ACTION_OVERVIEW = 2;
	protected static final int ACTION_SAVE = 3;

	protected boolean iUseSessionUser = false;

	protected int iNumberOfPhases = 3;
	protected IWResourceBundle iwrb;
	private ICPage iPage = null;

	public String getBundleIdentifier() {
		return FSKConstants.IW_BUNDLE_IDENTIFIER;
	}

	protected String getCaseCode() {
		return FSKConstants.CASE_CODE_KEY;
	}

	public void present(IWContext iwc) {
		this.iwrb = getResourceBundle(iwc);

		try {
			switch (parseAction(iwc)) {
				case ACTION_FORM:
					showForm(iwc);
					break;

				case ACTION_OVERVIEW:
					showOverview(iwc);
					break;

				case ACTION_SAVE:
					save(iwc);
					break;
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private int parseAction(IWContext iwc) {
		int action = ACTION_FORM;
		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			action = Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}

		return action;
	}

	protected Form createForm(int phase) {
		Form form = new Form();
		if (phase != ACTION_FORM) {
			form.maintainParameter(PARAMETER_SEASON);
			form.maintainParameter(PARAMETER_CHILD_PK);
			form.maintainParameter(PARAMETER_COURSE);
			form.maintainParameter(PARAMETER_ALLOCATION);
		}

		return form;
	}

	protected void showForm(IWContext iwc) throws RemoteException {
		Form form = createForm(1);
		form.add(new HiddenInput(PARAMETER_ACTION, String.valueOf(ACTION_FORM)));
		add(form);

		addErrors(iwc, form);
		getSession(iwc).clear();

		form.add(getPhasesHeader(this.iwrb.getLocalizedString("application.applicant", "Child"), 1, this.iNumberOfPhases));

		User applicant = getChild(iwc);

		Layer info = new Layer(Layer.DIV);
		info.setStyleClass("info");
		form.add(info);

		Heading1 heading = new Heading1(this.iwrb.getLocalizedString("select_child", "Select a child"));
		info.add(heading);

		DropdownMenu chooser = getUserChooser(iwc, getUser(iwc), applicant, PARAMETER_CHILD_PK, this.iwrb);
		chooser.setToSubmit();

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(this.iwrb.getLocalizedString("name", "Name"), chooser);
		formItem.add(label);
		formItem.add(chooser);
		info.add(formItem);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(this.iwrb.getLocalizedString("parent_allocation.child_chooser_help", "Please select the child from the drop down list.")));
		info.add(helpLayer);

		if (applicant != null) {
			if (!getCitizenBusiness(iwc).isCitizenOfDefaultCommune(applicant)) {
				form.add(getStopLayer(iwrb.getLocalizedString("parent_allocation.not_citizen_in_commune_subject", "Not a citizen of commune"), iwrb.getLocalizedString("parent_allocation.not_citizen_in_commune_text", "The child you have selected is not a citizen of the commune and is therefore not eligible for allocation.")));
				return;
			}

			Season season = getBusiness(iwc).getCurrentSeason();
			if (season == null) {
				form.add(getStopLayer(iwrb.getLocalizedString("parent_allocation.no_season_found_subject", "No season found"), iwrb.getLocalizedString("parent_allocation.no_season_found_text", "No season was found to allocate for.")));
				return;
			}
			else {
				form.addParameter(PARAMETER_SEASON, season.getPrimaryKey().toString());
			}

			float totalAmount = season != null ? getBusiness(iwc).getAllocationAmount(season, applicant) : 0;
			float remainder = season != null ? getBusiness(iwc).getRemainder(season, applicant) : 0;
			float allocated = totalAmount - remainder;

			heading = new Heading1(this.iwrb.getLocalizedString("parent_allocation.allocation_information", "Allotment overview"));
			heading.setStyleClass("subHeader");
			heading.setStyleClass("topSubHeader");
			form.add(heading);

			Layer section = new Layer(Layer.DIV);
			section.setStyleClass("formSection");
			form.add(section);

			helpLayer = new Layer(Layer.DIV);
			helpLayer.setStyleClass("helperText");
			helpLayer.add(new Text(this.iwrb.getLocalizedString("parent_allocation.season_help", "Select the season that you want to allocate for.  The total amount as well as the remainder of allocation is then shown.")));
			section.add(helpLayer);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label();
			label.add(new Text(this.iwrb.getLocalizedString("season", "Year")));
			Layer span = new Layer(Layer.SPAN);
			span.add(new Text(season.getName()));
			formItem.add(label);
			formItem.add(span);
			section.add(formItem);

			NumberFormat format = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());
			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label();
			label.add(new Text(this.iwrb.getLocalizedString("total_amount", "Total amount")));
			span = new Layer(Layer.SPAN);
			span.add(new Text(format.format(totalAmount)));
			formItem.add(label);
			formItem.add(span);
			section.add(formItem);

			if (allocated > 0) {
				formItem = new Layer(Layer.DIV);
				formItem.setStyleClass("formItem");
				label = new Label();
				label.add(new Text(this.iwrb.getLocalizedString("allocated", "Distributed")));
				span = new Layer(Layer.SPAN);
				span.add(new Text(format.format(allocated)));
				formItem.add(label);
				formItem.add(span);
				section.add(formItem);
			}

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label();
			label.add(new Text(this.iwrb.getLocalizedString("remainder", "Balance")));
			span = new Layer(Layer.SPAN);
			span.add(new Text(format.format(remainder)));
			formItem.add(label);
			formItem.add(span);
			section.add(formItem);

			Layer clearLayer = new Layer(Layer.DIV);
			clearLayer.setStyleClass("Clear");
			section.add(clearLayer);

			boolean hasCourses = false;
			heading = new Heading1(this.iwrb.getLocalizedString("parent_allocation.parent_allocation_information", "Subvention distribution"));
			heading.setStyleClass("subHeader");
			form.add(heading);

			section = new Layer(Layer.DIV);
			section.setStyleClass("formSection");
			form.add(section);

			helpLayer = new Layer(Layer.DIV);
			helpLayer.setStyleClass("helperText");
			helpLayer.add(new Text(this.iwrb.getLocalizedString("parent_allocation.allocation_help", "Enter the amount you want to allocate for each course.")));
			section.add(helpLayer);

			Table2 table = new Table2();
			table.setStyleClass("parentAllocation");
			table.setCellpadding(0);
			table.setCellspacing(0);
			section.add(table);

			TableRowGroup group = table.createHeaderRowGroup();
			TableRow row = group.createRow();

			TableCell2 cell = row.createHeaderCell();
			cell.setStyleClass("company");
			cell.add(new Text(iwrb.getLocalizedString("company", "Company/association")));

			cell = row.createHeaderCell();
			cell.setStyleClass("division");
			cell.add(new Text(iwrb.getLocalizedString("division", "Division")));

			cell = row.createHeaderCell();
			cell.setStyleClass("course");
			cell.add(new Text(iwrb.getLocalizedString("course", "Course")));

			cell = row.createHeaderCell();
			cell.setStyleClass("period");
			cell.add(new Text(iwrb.getLocalizedString("period", "Time period/Season")));

			cell = row.createHeaderCell();
			cell.setStyleClass("price");
			cell.add(new Text(iwrb.getLocalizedString("price", "Price")));

			cell = row.createHeaderCell();
			cell.setStyleClass("allocation");
			cell.add(new Text(iwrb.getLocalizedString("parent_allocation", "Allocation")));

			cell = row.createHeaderCell();
			cell.setStyleClass("allocation");
			cell.add(new Text(iwrb.getLocalizedString("parent_already_allocated", "Already distributed")));

			int iRow = 1;
			group = table.createBodyRowGroup();

			Collection courses = getBusiness(iwc).getCoursesForChild(season, applicant);
			hasCourses = !courses.isEmpty();

			Iterator iterator = courses.iterator();
			while (iterator.hasNext()) {
				Course course = (Course) iterator.next();
				Group division = getBusiness(iwc).getDivision(course);
				Participant participant = getBusiness(iwc).getParticipant(applicant);
				if (!course.isApproved()) {
					continue;
				}
				else if (!participant.isActive(course)) {
					continue;
				}

				row = group.createRow();

				ParticipantDiscount discount = getBusiness(iwc).getDiscount(participant, course);
				Company company = course.getCompany();
				Period period = course.getPeriod();
				float courseAllocation = getBusiness(iwc).getAllocation(course, applicant);
				float price = course.getPrice();
				if (discount != null) {
					if (discount.isAmount()) {
						price = price - discount.getDiscount();
					}
					else if (discount.isPercentage()) {
						price = price * (1 - discount.getDiscount());
					}
				}
				//boolean locked = true;

				boolean canAllocate = true;
				Constant constant = getBusiness(iwc).getConstant(period, FSKConstants.CONSTANT_PARENT_ALLOCATION);
				if (constant != null) {
					IWTimestamp start = new IWTimestamp(constant.getStartDate());
					IWTimestamp end = new IWTimestamp(constant.getEndDate());
					IWTimestamp stamp = new IWTimestamp();
					stamp.setAsDate();

					canAllocate = stamp.isBetween(start, end) || stamp.isEqualTo(end);
				}

				boolean isDisabled = false;
				boolean isClosed = false;
				TextInput allocation = new TextInput(PARAMETER_ALLOCATION);
				HiddenInput hidden = new HiddenInput(PARAMETER_COURSE, course.getPrimaryKey().toString());
				allocation.keepStatusOnAction(true, iRow - 1);
				if (course.isClosed() || !canAllocate) {
					allocation.setDisabled(true);
					isDisabled = true;
				}
				if (course.isClosed()) {
					isClosed = true;
				}

				cell = row.createCell();
				cell.setStyleClass("company");
				cell.add(new Text(company.getName()));

				cell = row.createCell();
				cell.setStyleClass("division");
				if (division != null) {
					cell.add(new Text(division.getName()));
				}
				else {
					cell.add(Text.getNonBrakingSpace());
				}

				cell = row.createCell();
				cell.setStyleClass("course");
				cell.add(new Text(course.getName()));

				cell = row.createCell();
				cell.setStyleClass("period");
				cell.add(new Text(period.getName()));

				cell = row.createCell();
				cell.setStyleClass("price");
				cell.add(new Text(format.format(price)));

				cell = row.createCell();
				cell.setStyleClass("allocation");
				cell.setStyleClass("closedCourse");
				if (!isClosed) {
					cell.add(allocation);
				}
				else {
					cell.add(new Text(iwrb.getLocalizedString("allocation_closed", "Allocation closed")));
				}
				if (isDisabled) {
					cell.add(new HiddenInput(PARAMETER_ALLOCATION, ""));
				}
				cell.add(hidden);

				cell = row.createCell();
				if (courseAllocation > 0) {
					cell.setStyleClass("already_allocated");
					cell.add(new Text(format.format(courseAllocation)));
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

			section.add(clearLayer);

			if (totalAmount == 0) {
				section.add(getAttentionLayer(iwrb.getLocalizedString("parent_allocation.no_allocation_found_text", "No allocation was found for the selected child.")));
			}

			Layer bottom = new Layer(Layer.DIV);
			bottom.setStyleClass("bottom");
			form.add(bottom);

			if (hasCourses && totalAmount > 0) {
				Link next = getButtonLink(this.iwrb.getLocalizedString("next", "Forward"));
				next.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_OVERVIEW));
				next.setToFormSubmit(form);
				bottom.add(next);
			}
		}
	}

	protected void showOverview(IWContext iwc) throws RemoteException {
		User child = getChild(iwc);
		Season season = getBusiness(iwc).getSeason(iwc.getParameter(PARAMETER_SEASON));
		int totalAmount = (int) getBusiness(iwc).getAllocationAmount(season, child);
		int remainder = (int) getBusiness(iwc).getRemainder(season, child);
		int allocated = totalAmount - remainder;

		int totalAllocation = 0;

		Object[] coursePKs = iwc.getParameterValues(PARAMETER_COURSE);
		String[] amounts = iwc.getParameterValues(PARAMETER_ALLOCATION);

		if (coursePKs != null) {
			Collection periods = new ArrayList();

			for (int i = 0; i < amounts.length; i++) {
				Object coursePK = coursePKs[i];
				String amountString = amounts[i];

				int amount = 0;
				if (amountString.length() > 0) {
					try {
						amountString = TextSoap.findAndReplace(amountString, ".", "");
						amount = Integer.parseInt(amountString);

						Course course = getBusiness(iwc).getCourse(coursePK);
						ParticipantDiscount discount = getBusiness(iwc).getDiscount(child, course);
						Period period = course.getPeriod();
						float courseAllocation = getBusiness(iwc).getAllocation(course, child);
						float price = course.getPrice();
						if (discount != null) {
							if (discount.isAmount()) {
								price = price - discount.getDiscount();
							}
							else if (discount.isPercentage()) {
								price = price * (1 - discount.getDiscount());
							}
						}

						if (!periods.contains(period)) {
							periods.add(period);
						}

						getSession(iwc).addAllocation(period, course);

						if (price < (amount + courseAllocation)) {
							setError(PARAMETER_ALLOCATION, iwrb.getLocalizedString("parent_allocation_error.amount_exceeds_price", "The selected amount exceeds the course price"));
						}
						else if (amount <= 0) {
							setError(PARAMETER_ALLOCATION, iwrb.getLocalizedString("parent_allocation_error.amount_illegal", "The selected amount is not valid"));
						}
						else {
							totalAllocation += amount;
						}
					}
					catch (NumberFormatException nfe) {
						setError(PARAMETER_ALLOCATION, iwrb.getLocalizedString("parent_allocation_error.not_a_number", "Not a valid amount"));
					}
				}
			}

			Iterator iterator = periods.iterator();
			while (iterator.hasNext()) {
				Period period = (Period) iterator.next();

				Collection courses = getBusiness(iwc).getAllocations(period, child);
				Iterator it = courses.iterator();
				while (it.hasNext()) {
					PaymentAllocation pa = (PaymentAllocation) it.next();
					getSession(iwc).addAllocation(period, pa.getCourse());
				}

				int numberOfAllocations = getSession(iwc).getNumberOfAllocations(period);

				if (numberOfAllocations > Integer.parseInt(iwc.getApplicationSettings().getProperty(FSKConstants.PROPERTY_MAX_PERIOD_ALLOCATIONS, "3"))) {
					setError(PARAMETER_ALLOCATION, iwrb.getLocalizedString("parent_allocation_error.exceeded_allocations_for_period", "You have exceeded the maximum amount of allocations for each period."));
				}
			}
		}

		if (totalAllocation == 0 && !hasErrors()) {
			setError(PARAMETER_ALLOCATION, iwrb.getLocalizedString("parent_allocation_error.must_allocate", "You have to allocate to at least one company"));
		}
		if ((allocated + totalAllocation) > totalAmount) {
			setError(PARAMETER_ALLOCATION, iwrb.getLocalizedString("parent_allocation_error.allocation_exceeds_total_amount", "You can not allocate more than the total amount"));
		}

		if (hasErrors()) {
			getSession(iwc).clear();
			showForm(iwc);
			return;
		}

		Form form = createForm(ACTION_OVERVIEW);
		form.addParameter(PARAMETER_ACTION, String.valueOf(ACTION_OVERVIEW));

		form.add(getPhasesHeader(this.iwrb.getLocalizedString("parent_allocation.overview", "Overview of allocated funds"), 2, iNumberOfPhases));

		form.add(getPersonInfo(iwc, child));

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");

		Heading1 heading = new Heading1(this.iwrb.getLocalizedString("parent_allocation.season_information", "Information on yearly allocation"));
		heading.setStyleClass("subHeader");
		heading.setStyleClass("topSubHeader");
		form.add(heading);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer formItem = new Layer(Layer.DIV);
		Label label = new Label();
		Layer span = new Layer();

		NumberFormat format = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.add(new Text(this.iwrb.getLocalizedString("season", "Year")));
		span = new Layer(Layer.SPAN);
		span.add(new Text(season.getName()));
		formItem.add(label);
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.add(new Text(this.iwrb.getLocalizedString("total_amount", "Total amount")));
		span = new Layer(Layer.SPAN);
		span.add(new Text(format.format(totalAmount)));
		formItem.add(label);
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.add(new Text(this.iwrb.getLocalizedString("allocated", "Allocated")));
		span = new Layer(Layer.SPAN);
		span.add(new Text(format.format((allocated))));
		formItem.add(label);
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.add(new Text(this.iwrb.getLocalizedString("remainder", "Balance")));
		span = new Layer(Layer.SPAN);
		span.add(new Text(format.format(remainder)));
		formItem.add(label);
		formItem.add(span);
		section.add(formItem);

		section.add(clearLayer);

		heading = new Heading1(this.iwrb.getLocalizedString("parent_allocation.allocation_information_overview", "Distribution overview"));
		heading.setStyleClass("subHeader");
		form.add(heading);

		section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		section.setID("parentAllocationOverviewSection");
		form.add(section);

		section.add(getAttentionLayer(this.iwrb.getLocalizedString("parent_allocation.overview_information", "This is an overview of what you have allocated.  The allocation will not be registered until you have pressed the 'confirm' button below.")));

		Table2 table = new Table2();
		table.setStyleClass("parentAllocation");
		table.setStyleClass("overview");
		table.setCellpadding(0);
		table.setCellspacing(0);
		section.add(table);

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();

		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("company");
		cell.add(new Text(iwrb.getLocalizedString("company", "Company")));

		cell = row.createHeaderCell();
		cell.setStyleClass("division");
		cell.add(new Text(iwrb.getLocalizedString("division", "Division")));

		cell = row.createHeaderCell();
		cell.setStyleClass("course");
		cell.add(new Text(iwrb.getLocalizedString("course", "Course")));

		cell = row.createHeaderCell();
		cell.setStyleClass("period");
		cell.add(new Text(iwrb.getLocalizedString("period", "Period")));

		cell = row.createHeaderCell();
		cell.setStyleClass("price");
		cell.add(new Text(iwrb.getLocalizedString("price", "Price")));

		cell = row.createHeaderCell();
		cell.setStyleClass("allocation");
		cell.add(new Text(iwrb.getLocalizedString("parent_allocation", "Parent allocation")));

		cell = row.createHeaderCell();
		cell.setStyleClass("allocation");
		cell.add(new Text(iwrb.getLocalizedString("parent_already_allocated", "Parent allocated")));

		int iRow = 1;
		group = table.createBodyRowGroup();

		for (int i = 0; i < amounts.length; i++) {
			row = group.createRow();

			Course course = getBusiness(iwc).getCourse(coursePKs[i]);
			ParticipantDiscount discount = getBusiness(iwc).getDiscount(child, course);
			Group division = getBusiness(iwc).getDivision(course);
			Company company = course.getCompany();
			Period period = course.getPeriod();
			int amount = 0;
			if (amounts[i] != null && !"".equals(amounts[i])) {
				String amountString = TextSoap.findAndReplace(amounts[i], ".", "");
				amount = Integer.parseInt(amountString);
			}
			float price = course.getPrice();
			if (discount != null) {
				if (discount.isAmount()) {
					price = price - discount.getDiscount();
				}
				else if (discount.isPercentage()) {
					price = price * (1 - discount.getDiscount());
				}
			}

			cell = row.createCell();
			cell.setStyleClass("company");
			cell.add(new Text(company.getName()));

			cell = row.createCell();
			cell.setStyleClass("division");
			if (division != null) {
				cell.add(new Text(division.getName()));
			}
			else {
				cell.add(Text.getNonBrakingSpace());
			}

			cell = row.createCell();
			cell.setStyleClass("course");
			cell.add(new Text(course.getName()));

			cell = row.createCell();
			cell.setStyleClass("period");
			cell.add(new Text(period.getName()));

			cell = row.createCell();
			cell.setStyleClass("price");
			cell.add(new Text(format.format(price)));

			cell = row.createCell();
			cell.setStyleClass("allocation");
			cell.add(new Text(format.format(amount)));

			cell = row.createCell();
			cell.setStyleClass("allocation");
			cell.add(new Text(format.format(getBusiness(iwc).getAllocation(course, child))));

			if (iRow % 2 == 0) {
				row.setStyleClass("even");
			}
			else {
				row.setStyleClass("odd");
			}
			iRow++;
		}

		section.add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(this.iwrb.getLocalizedString("confirm", "Confirm"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_SAVE));
		next.setToFormSubmit(form);
		bottom.add(next);

		Link back = getButtonLink(this.iwrb.getLocalizedString("previous", "Back"));
		back.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_FORM));
		back.setToFormSubmit(form);
		bottom.add(back);

		add(form);
	}

	protected void save(IWContext iwc) throws RemoteException {
		Object[] coursePKs = iwc.getParameterValues(PARAMETER_COURSE);
		String[] allocations = iwc.getParameterValues(PARAMETER_ALLOCATION);
		Season season = getBusiness(iwc).getSeason(iwc.getParameter(PARAMETER_SEASON));

		getBusiness(iwc).storeParentAllocations(getChild(iwc), season, coursePKs, allocations);
		getSession(iwc).clear();

		addPhasesReceipt(iwc, this.iwrb.getLocalizedString("parent_allocation.receipt", "Distribution confirmation"), this.iwrb.getLocalizedString("parent_allocation.allocation_completed", "Allocation completed"), this.iwrb.getLocalizedString("parent_allocation.allocation_completed_confirmation", "Your allocations have been stored."), 3, iNumberOfPhases);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		add(clearLayer);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		add(bottom);

		if (getPage() != null) {
			Link link = getButtonLink(iUseSessionUser ? iwrb.getLocalizedString("back", "Back") : this.iwrb.getLocalizedString("allocation_overview", "Allocation overview"));
			link.maintainParameter(PARAMETER_CHILD_PK, iwc);
			link.setStyleClass("homeButton");
			link.setPage(getPage());
			bottom.add(link);
		}
	}

	protected User getChild(IWContext iwc) {
		if (iwc.isParameterSet(PARAMETER_CHILD_PK)) {
			try {
				return getUserBusiness(iwc).getUser(new Integer(iwc.getParameter(PARAMETER_CHILD_PK)));
			}
			catch (RemoteException re) {
				throw new IBORuntimeException(re);
			}
		}

		return null;
	}

	protected User getUser(IWContext iwc) throws RemoteException {
		if (this.iUseSessionUser) {
			return getUserSession(iwc).getUser();
		}
		else {
			return iwc.getCurrentUser();
		}
	}

	protected FSKBusiness getBusiness(IWApplicationContext iwac) {
		try {
			return (FSKBusiness) IBOLookup.getServiceInstance(iwac, FSKBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected ApplicationSession getSession(IWUserContext iwuc) {
		try {
			return (ApplicationSession) IBOLookup.getSessionInstance(iwuc, ApplicationSession.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected CitizenBusiness getCitizenBusiness(IWApplicationContext iwac) {
		try {
			return (CitizenBusiness) IBOLookup.getServiceInstance(iwac, CitizenBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private UserSession getUserSession(IWUserContext iwuc) {
		try {
			return (UserSession) IBOLookup.getSessionInstance(iwuc, UserSession.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	public void setUseSessionUser(boolean useSessionUser) {
		this.iUseSessionUser = useSessionUser;
	}

	protected ICPage getPage() {
		return this.iPage;
	}

	public void setResponsePage(ICPage page) {
		this.iPage = page;
	}
}