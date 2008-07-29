/*
 * $Id: FSKConstants.java,v 1.1 2008/07/29 10:48:22 anton Exp $ Created on Jun 7, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk;

public class FSKConstants {

	public static final String IW_BUNDLE_IDENTIFIER = "is.idega.idegaweb.egov.fsk";

	public static final String CASE_CODE_KEY = "COMPAPP";

	public static final String ROLE_KEY_FSK_ADMIN = "fskAdmin";
	public static final String ROLE_KEY_FSK_COMPANY = "fskCompany";
	public static final String ROLE_KEY_FSK_COMPANY_ADMIN = "fskCompanyAdmin";

	public static final String FILE_TYPE_TARIFF = "tariff";
	public static final String FILE_TYPE_SCHEDULE = "schedule";
	public static final String FILE_TYPE_OTHER = "other";

	public static final String GROUP_TYPE_DIVISIONS = "fsk_divisions";
	public static final String GROUP_TYPE_DIVISION = "fsk_division";
	public static final String GROUP_TYPE_GROUP = "fsk_group";
	public static final String GROUP_TYPE_SUB_GROUP = "fsk_sub_group";
	public static final String GROUP_TYPE_COURSE = "fsk_course";

	public static final String COMPANY_TYPE_SPORTS_CLUB = "sports_club";
	public static final String COMPANY_TYPE_ARTS_AND_CULTURE = "arts_and_culture";
	public static final String COMPANY_TYPE_YOUTH_CLUB = "youth_club";
	public static final String COMPANY_TYPE_OTHER = "other";

	public static final String CONSTANT_COURSE_CREATE = "course_creation";
	public static final String CONSTANT_REGISTER_PARTICIPANTS = "register_participants";
	public static final String CONSTANT_PARENT_ALLOCATION = "parent_allocation";
	public static final String CONSTANT_ADMIN_PAYMENT = "admin_payment";

	public static final String PROPERTY_MAXIMUM_AGE = "egov.fsk.maximum.age";
	public static final String PROPERTY_MINIMUM_AGE = "egov.fsk.minimum.age";
	public static final String PROPERTY_POSTAL_CODES = "egov.fsk.postal.codes";
	public static final String PROPERTY_FSK_GROUP = "egov.fsk.root.group";
	public static final String PROPERTY_COMPANY_GROUP = "root_company_group_id";
	public static final String PROPERTY_MAX_PERIOD_ALLOCATIONS = "egov.fsk.max.period.allocation";
	public static final String PROPERTY_COST_AMOUNT = "egov.fsk.cost.amount";
	public static final String PROPERTY_INCEPTION_YEAR = "egov.fsk.inception.year";

	public static final String[] ALL_CONSTANTS = { CONSTANT_COURSE_CREATE, CONSTANT_REGISTER_PARTICIPANTS, CONSTANT_PARENT_ALLOCATION, CONSTANT_ADMIN_PAYMENT };
	public static final String[] DEFAULT_POSTAL_CODES = { "101", "103", "104", "105", "107", "108", "109", "110", "111", "112", "113", "116", "170", "190", "200", "201", "203", "210", "220", "221", "225" };

	public static final String CONTENT_CODE_APPROVE_APPLICATION = "fsk_approve";
	public static final String CONTENT_CODE_CLOSE_APPLICATION = "fsk_close";
	public static final String CONTENT_CODE_REACTIVATE_APPLICATION = "fsk_reactivate";
	public static final String CONTENT_CODE_REJECT_APPLICATION = "fsk_reject";
	public static final String CONTENT_CODE_REOPEN_APPLICATION = "fsk_reopen";
	public static final String CONTENT_CODE_REQUEST_INFORMATION = "fsk_request";
	public static final String CONTENT_CODE_SEND_REMINDER = "fsk_send_reminder";

	public static final String REGISTRATION_CODE_REGISTERED = "registered";
	public static final String REGISTRATION_CODE_ALREADY_REGISTERED = "already_registered";
	public static final String REGISTRATION_CODE_OUTSIDE_COMMUNE = "outside_commune";
	public static final String REGISTRATION_CODE_OUTSIDE_AGE_RANGE = "outside_age_range";
	public static final String REGISTRATION_CODE_INVALID_PERSONAL_ID = "invalid_personal_id";
	public static final String REGISTRATION_CODE_NO_USER_FOUND = "no_user_found";

	public static final String DELETE_PARTICIPANTS_REMOVED = "removed";
	public static final String DELETE_PARTICIPANTS_NON_REMOVED = "non_removed";
}