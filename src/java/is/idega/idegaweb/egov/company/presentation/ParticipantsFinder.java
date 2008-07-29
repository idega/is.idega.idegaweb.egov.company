/*
 * $Id: ParticipantsFinder.java,v 1.1 2008/07/29 12:57:41 anton Exp $ Created on Mar 27, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.citizen.presentation.CitizenFinder;
import is.idega.idegaweb.egov.company.FSKConstants;
import is.idega.idegaweb.egov.company.business.FSKBusiness;
import is.idega.idegaweb.egov.company.data.Course;
import is.idega.idegaweb.egov.company.data.Participant;
import is.idega.idegaweb.egov.company.data.Period;
import is.idega.idegaweb.egov.company.data.Season;

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
import com.idega.presentation.text.Paragraph;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.Form;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.Age;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.Name;

public class ParticipantsFinder extends CitizenFinder {

	private ICPage iBackPage;

	protected Collection filterResults(IWContext iwc, Collection users) {
		try {
			Company company = getCompany(iwc, iwc.getCurrentUser());
			Collection participants = new ArrayList();

			Iterator iter = users.iterator();
			while (iter.hasNext()) {
				User user = (User) iter.next();
				if (isOfAge(iwc, user)) {
					boolean addUser = false;
					if (company != null) {
						addUser = getBusiness(iwc).isPlacedAtCompany(company, user);
					}
					else {
						addUser = true;
					}

					if (addUser) {
						participants.add(user);
					}
				}
			}

			return participants;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	protected Lists getLegend(IWContext iwc) {
		IWResourceBundle iwrb2 = iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(iwc);

		Lists list = new Lists();
		list.setStyleClass("legend");

		ListItem item = new ListItem();
		item.setStyleClass("inactive");
		item.add(new Text(iwrb2.getLocalizedString("participants_list.inactive_participant", "Inactive participant")));
		list.add(item);

		return list;
	}

	protected void showNoPermission(IWContext iwc) {
		IWResourceBundle iwrb2 = iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(iwc);

		Form form = new Form();
		add(form);

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("stop");
		form.add(layer);

		Layer image = new Layer(Layer.DIV);
		image.setStyleClass("stopImage");
		layer.add(image);

		Heading1 heading = new Heading1(iwrb2.getLocalizedString("no_permission.subject", "You have no permission"));
		layer.add(heading);

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Text(iwrb2.getLocalizedString("no_permission.body", "You have no permission to access the admin functionality until your application has been accepted or your account reopened.")));
		layer.add(paragraph);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		if (getBackPage() != null) {
			Link home = getButtonLink(iwrb2.getLocalizedString("back", "Back"));
			home.setStyleClass("buttonHome");
			home.setPage(getBackPage());
			bottom.add(home);
		}
	}

	protected Link getButtonLink(String text) {
		Layer all = new Layer(Layer.SPAN);
		all.setStyleClass("buttonSpan");

		Layer left = new Layer(Layer.SPAN);
		left.setStyleClass("left");
		all.add(left);

		Layer middle = new Layer(Layer.SPAN);
		middle.setStyleClass("middle");
		middle.add(new Text(text));
		all.add(middle);

		Layer right = new Layer(Layer.SPAN);
		right.setStyleClass("right");
		all.add(right);

		Link link = new Link(all);
		link.setStyleClass("button");

		return link;
	}

	protected boolean hasPermission(IWContext iwc) {
		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			return true;
		}

		Company company = getCompany(iwc, iwc.getCurrentUser());
		if (company != null) {
			return company.isOpen();
		}

		return false;
	}

	protected boolean isOfAge(IWContext iwc, User user) {
		if (user.getDateOfBirth() != null) {
			IWTimestamp dateOfBirth = new IWTimestamp(user.getDateOfBirth());
			dateOfBirth.setDay(1);
			dateOfBirth.setMonth(1);

			Age age = new Age(dateOfBirth.getDate());
			int minimumAge = Integer.parseInt(iwc.getApplicationSettings().getProperty(FSKConstants.PROPERTY_MINIMUM_AGE, "6"));
			int maximumAge = Integer.parseInt(iwc.getApplicationSettings().getProperty(FSKConstants.PROPERTY_MAXIMUM_AGE, "18"));

			return (age.getYears() >= minimumAge && age.getYears() <= maximumAge);
		}
		return false;
	}

	protected Table2 getUserTable(IWContext iwc) {
		try {
			IWResourceBundle iwrb2 = iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(iwc);

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
			cell.setStyleClass("name");
			cell.add(new Text(iwrb2.getLocalizedString("name", "Name")));

			cell = row.createHeaderCell();
			cell.setStyleClass("personalID");
			cell.add(new Text(iwrb2.getLocalizedString("personal_id", "Personal ID")));

			if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
				cell = row.createHeaderCell();
				cell.setStyleClass("company");
				cell.add(new Text(iwrb2.getLocalizedString("company", "Company")));
			}

			cell = row.createHeaderCell();
			cell.setStyleClass("season");
			cell.add(new Text(iwrb2.getLocalizedString("season", "Season")));

			cell = row.createHeaderCell();
			cell.setStyleClass("period");
			cell.add(new Text(iwrb2.getLocalizedString("period", "Period")));

			cell = row.createHeaderCell();
			cell.setStyleClass("division");
			cell.add(new Text(iwrb2.getLocalizedString("division", "Division")));

			cell = row.createHeaderCell();
			cell.setStyleClass("group");
			cell.add(new Text(iwrb2.getLocalizedString("group", "Group")));

			cell = row.createHeaderCell();
			cell.setStyleClass("subGroup");
			cell.add(new Text(iwrb2.getLocalizedString("sub_group", "Sub group")));

			cell = row.createHeaderCell();
			cell.setStyleClass("course");
			cell.add(new Text(iwrb2.getLocalizedString("course", "Course")));

			cell = row.createHeaderCell();
			cell.setStyleClass("lastColumn");
			cell.setStyleClass("allocation");
			cell.add(new Text(iwrb2.getLocalizedString("parent_allocation", "Parent allocation")));

			group = table.createBodyRowGroup();
			int iRow = 1;

			NumberFormat format = NumberFormat.getCurrencyInstance(iwc.getCurrentLocale());
			format.setMinimumFractionDigits(0);

			Iterator iter = this.users.iterator();
			while (iter.hasNext()) {
				User user = (User) iter.next();
				if (user.getPersonalID() == null || user.getPersonalID().length() == 0 || user.getPersonalID().indexOf("/") != -1) {
					continue;
				}
				Name name = new Name(user.getFirstName(), user.getMiddleName(), user.getLastName());
				Participant participant = getBusiness(iwc).getParticipant(user);
				Company company = getCompany(iwc, iwc.getCurrentUser());
				Collection courses = getBusiness(iwc).getCoursesForChild(company, user);

				Iterator iterator = courses.iterator();
				while (iterator.hasNext()) {
					Course course = (Course) iterator.next();
					Group division = getBusiness(iwc).getDivision(course);
					if (!hasDivisionPermission(iwc, division)) {
						continue;
					}

					Group divisionGroup = getBusiness(iwc).getGroup(course);
					Group subGroup = getBusiness(iwc).getSubGroup(course);

					Period period = course.getPeriod();
					Season season = period != null ? period.getSeason() : null;
					float allocation = getBusiness(iwc).getAllocation(course, user);
					boolean active = participant != null ? participant.isActive(course) : true;

					row = group.createRow();

					if (!active) {
						row.setStyleClass("inactive");
					}

					cell = row.createCell();
					cell.setStyleClass("firstColumn");
					cell.setStyleClass("name");
					cell.add(new Text(name.getName(iwc.getCurrentLocale())));

					cell = row.createCell();
					cell.setStyleClass("personalID");
					cell.add(new Text(PersonalIDFormatter.format(user.getPersonalID(), iwc.getCurrentLocale())));

					if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
						Company courseCompany = course.getCompany();

						cell = row.createCell();
						cell.setStyleClass("company");
						cell.add(new Text(courseCompany.getName()));
					}

					cell = row.createCell();
					cell.setStyleClass("season");
					cell.add(new Text(season != null ? season.getName() : "-"));

					cell = row.createCell();
					cell.setStyleClass("period");
					cell.add(new Text(period != null ? period.getName() : "-"));

					cell = row.createCell();
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
					if (getResponsePage() != null) {
						Link link = new Link(course.getName());
						link.setPage(getResponsePage());
						link.addParameter(ParticipantsList.PARAMETER_COMPANY_PK, company.getPrimaryKey().toString());
						link.addParameter(ParticipantsList.PARAMETER_COURSE_PK, course.getPrimaryKey().toString());
						cell.add(link);
					}
					else {
						cell.add(new Text(course.getName()));
					}

					cell = row.createCell();
					cell.setStyleClass("lastColumn");
					cell.setStyleClass("allocation");
					cell.add(new Text(format.format(allocation)));

					if (iRow % 2 == 0) {
						row.setStyleClass("evenRow");
					}
					else {
						row.setStyleClass("oddRow");
					}

					iRow++;
				}

			}

			return table;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	protected String getHeading(IWContext iwc) {
		IWResourceBundle iwrb = iwc.getIWMainApplication().getBundle(FSKConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(iwc.getCurrentLocale());
		return iwrb.getLocalizedString("participant_finder", "Participant finder");
	}

	private Company getCompany(IWContext iwc, User user) {
		try {
			return getBusiness(iwc).getCompany(user);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private boolean hasDivisionPermission(IWContext iwc, Group division) {
		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			return true;
		}
		else if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY_ADMIN, iwc)) {
			return true;
		}
		else if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY, iwc)) {
			Collection userDivisions = new ArrayList();
			Collection groups = iwc.getCurrentUser().getParentGroups();
			Iterator iterator = groups.iterator();
			while (iterator.hasNext()) {
				Group parentGroup = (Group) iterator.next();
				if (parentGroup.getGroupType().equals(FSKConstants.GROUP_TYPE_DIVISION)) {
					userDivisions.add(parentGroup);
				}
			}

			return userDivisions.contains(division);
		}

		return false;
	}

	private FSKBusiness getBusiness(IWApplicationContext iwac) {
		try {
			return (FSKBusiness) IBOLookup.getServiceInstance(iwac, FSKBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected ICPage getBackPage() {
		return this.iBackPage;
	}

	public void setBackPage(ICPage responsePage) {
		this.iBackPage = responsePage;
	}
}