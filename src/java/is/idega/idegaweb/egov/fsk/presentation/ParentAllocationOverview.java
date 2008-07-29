/*
 * $Id: ParentAllocationOverview.java,v 1.1 2008/07/29 10:48:18 anton Exp $
 * Created on Jun 17, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation;

import is.idega.idegaweb.egov.fsk.data.Course;
import is.idega.idegaweb.egov.fsk.data.ParticipantDiscount;
import is.idega.idegaweb.egov.fsk.data.PaymentAllocation;
import is.idega.idegaweb.egov.fsk.data.Period;
import is.idega.idegaweb.egov.fsk.data.Season;

import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.core.builder.data.ICPage;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.ListItem;
import com.idega.presentation.text.Lists;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Label;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;

public class ParentAllocationOverview extends ParentAllocation {

	protected ICPage iResponsePage;

	public void present(IWContext iwc) {
		try {
			this.iwrb = getResourceBundle(iwc);

			Form form = new Form();
			add(form);

			User applicant = getChild(iwc);

			Layer info = new Layer(Layer.DIV);
			info.setStyleClass("info");
			form.add(info);

			Heading1 heading = new Heading1(this.iwrb.getLocalizedString("select_child", "Select child"));
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
				if (totalAmount == 0) {
					form.add(getStopLayer(iwrb.getLocalizedString("parent_allocation.no_allocation_found_subject", "No allocation found"), iwrb.getLocalizedString("parent_allocation.no_allocation_found_text", "No allocation was found for the selected child.")));
					return;
				}
				float allocated = totalAmount - remainder;

				Collection allocations = getBusiness(iwc).getAllocations(season, applicant);
				if (allocations.isEmpty()) {
					form.add(getStopLayer(iwrb.getLocalizedString("parent_allocation.no_parent_allocation_found_subject", "No parent allocation found"), iwrb.getLocalizedString("parent_allocation.no_parent_allocation_found_text", "No parent allocation was found for the selected child.")));
					return;
				}

				heading = new Heading1(this.iwrb.getLocalizedString("parent_allocation.allocation_information", "Allocation for season"));
				heading.setStyleClass("subHeader");
				heading.setStyleClass("topSubHeader");
				form.add(heading);

				Layer section = new Layer(Layer.DIV);
				section.setStyleClass("formSection");
				form.add(section);

				helpLayer = new Layer(Layer.DIV);
				helpLayer.setStyleClass("helperText");
				helpLayer.add(new Text(this.iwrb.getLocalizedString("parent_allocation.season_overview_help", "Select the season that you want to allocate for.  The total amount as well as the remainder of allocation is then shown.")));
				section.add(helpLayer);

				formItem = new Layer(Layer.DIV);
				formItem.setStyleClass("formItem");
				label = new Label();
				label.add(new Text(this.iwrb.getLocalizedString("season", "Season")));
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
					label.add(new Text(this.iwrb.getLocalizedString("allocated", "Allocated")));
					span = new Layer(Layer.SPAN);
					span.add(new Text(format.format(allocated)));
					formItem.add(label);
					formItem.add(span);
					section.add(formItem);
				}

				formItem = new Layer(Layer.DIV);
				formItem.setStyleClass("formItem");
				label = new Label();
				label.add(new Text(this.iwrb.getLocalizedString("remainder", "Remainder")));
				span = new Layer(Layer.SPAN);
				span.add(new Text(format.format(remainder)));
				formItem.add(label);
				formItem.add(span);
				section.add(formItem);

				Layer clearLayer = new Layer(Layer.DIV);
				clearLayer.setStyleClass("Clear");
				section.add(clearLayer);

				if (totalAmount > 0) {
					heading = new Heading1(this.iwrb.getLocalizedString("parent_allocation.allocation_information", "Allocation for season"));
					heading.setStyleClass("subHeader");
					form.add(heading);

					section = new Layer(Layer.DIV);
					section.setStyleClass("formSection");
					form.add(section);

					helpLayer = new Layer(Layer.DIV);
					helpLayer.setStyleClass("helperText");
					helpLayer.add(new Text(this.iwrb.getLocalizedString("parent_allocation.allocation_overview_help", "Enter the amount you want to allocate for each course.")));
					section.add(helpLayer);

					Table2 table = new Table2();
					table.setStyleClass("parentAllocation");
					table.setCellpadding(0);
					table.setCellspacing(0);
					section.add(table);

					TableRowGroup group = table.createHeaderRowGroup();
					TableRow row = group.createRow();

					TableCell2 cell = row.createHeaderCell();
					cell.setStyleClass("date");
					cell.add(new Text(iwrb.getLocalizedString("date", "Date")));

					cell = row.createHeaderCell();
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

					int iRow = 1;
					group = table.createBodyRowGroup();
					Collection courses = new ArrayList();

					Iterator iterator = allocations.iterator();
					while (iterator.hasNext()) {
						row = group.createRow();

						PaymentAllocation allocation = (PaymentAllocation) iterator.next();
						if (allocation.getPaymentDate() != null) {
							row.setStyleClass("paidAllocation");
						}
						if(allocation.isTransfer()) {
							row.setStyleClass("transferAllocation");
						}
						Course course = allocation.getCourse();
						ParticipantDiscount discount = getBusiness(iwc).getDiscount(applicant, course);
						Group division = getBusiness(iwc).getDivision(course);
						Company company = course.getCompany();
						Period period = course.getPeriod();
						IWTimestamp date = allocation.getEntryDate() != null ? new IWTimestamp(allocation.getEntryDate()) : null;
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
						cell.setStyleClass("date");
						if (date != null) {
							cell.add(new Text(date.getLocaleDate(iwc.getCurrentLocale(), IWTimestamp.SHORT)));
						}
						else {
							cell.add(new Text("-"));
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
						if (!courses.contains(course)) {
							cell.add(new Text(format.format(price)));
						}
						else {
							cell.add(new Text("-"));
						}

						cell = row.createCell();
						cell.setStyleClass("allocation");
						cell.add(new Text(format.format(allocation.getAmount())));

						if (iRow % 2 == 0) {
							row.setStyleClass("even");
						}
						else {
							row.setStyleClass("odd");
						}
						iRow++;

						courses.add(course);
					}

					Lists list = new Lists();
					list.setStyleClass("legend");
					section.add(list);

					ListItem item = new ListItem();
					item.setStyleClass("paidAllocation");
					item.add(new Text(iwrb.getLocalizedString("parent_allocation.paid_allocation", "Paid allocation")));
					list.add(item);

					section.add(clearLayer);
				}

				Layer bottom = new Layer(Layer.DIV);
				bottom.setStyleClass("bottom");
				form.add(bottom);

				if (iResponsePage != null && remainder > 0) {
					Link next = getButtonLink(this.iwrb.getLocalizedString("allocate_remainder", "Allocate remainder"));
					next.addParameter(PARAMETER_SEASON, season.getPrimaryKey().toString());
					next.addParameter(PARAMETER_CHILD_PK, applicant.getPrimaryKey().toString());
					next.setPage(iResponsePage);
					bottom.add(next);
				}
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	public void setResponsePage(ICPage page) {
		this.iResponsePage = page;
	}
}