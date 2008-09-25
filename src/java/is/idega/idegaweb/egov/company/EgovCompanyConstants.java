/*
 * $Id: EgovCompanyConstants.java,v 1.1 2008/07/29 12:57:50 anton
 * 
 * Copyright (C) 2008 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.idega.core.accesscontrol.business.StandardRoles;

public class EgovCompanyConstants {
	
	private EgovCompanyConstants() {}

	public static final String IW_BUNDLE_IDENTIFIER = "is.idega.idegaweb.egov.company";
	
	public static final String COMPANY_ADMIN_ROLE = "company_admin_role";
	public static final String COMPANY_EMPLOYEE_ROLE = "company_employee_role";
	
	public static final List<String> ALL_COMPANY_ROLES = Collections.unmodifiableList(Arrays.asList(new String[] {StandardRoles.ROLE_KEY_COMPANY,
			COMPANY_ADMIN_ROLE, COMPANY_EMPLOYEE_ROLE}));
	
	public static final String GROUP_TYPE_COMPANY_DIVISIONS = "iw_company_divisions";
	public static final String GROUP_TYPE_COMPANY_DIVISION = "iw_company_division";
	public static final String GROUP_TYPE_COMPANY_SUB_GROUP = "iw_company_sub_group";
	public static final String GROUP_TYPE_COMPANY_COURSE = "iw_company_course";
	
	public static final String CASE_CODE_KEY = "COMPORT";

	public static final String CONTENT_CODE_REQUEST_INFORMATION = "prm_company_code_request_information";
	
	public static final Integer APPLICATION_TYPE_APPROVED = 0;
	public static final Integer APPLICATION_TYPE_REJECTED = 1;
	public static final Integer APPLICATION_TYPE_UNHANDLED = 2;
	
	public static final String USER_LOGIN_METADATA = "userLoginEnabledMetaDataForCompanyPortal";
}