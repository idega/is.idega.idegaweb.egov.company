package is.idega.idegaweb.egov.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.idega.company.CompanyConstants;
import com.idega.core.accesscontrol.business.StandardRoles;

public class EgovCompanyConstants {
	
	private EgovCompanyConstants() {}

	public static final String IW_BUNDLE_IDENTIFIER = "is.idega.idegaweb.egov.company";
	
	public static final String COMPANY_SUPER_ADMIN_ROLE = "company_super_admin";
	public static final String COMPANY_ADMIN_ROLE = "company_admin";
	public static final String COMPANY_EMPLOYEE_ROLE = "company_staff";
	
	public static final List<String> ALL_COMPANY_ROLES = Collections.unmodifiableList(Arrays.asList(
			StandardRoles.ROLE_KEY_COMPANY,
			COMPANY_SUPER_ADMIN_ROLE,
			COMPANY_ADMIN_ROLE,
			COMPANY_EMPLOYEE_ROLE
	));
	
	public static final String GROUP_TYPE_COMPANY_ADMINS = "iw_company_admins_group";
	public static final String GROUP_TYPE_COMPANY_STAFF = "iw_company_staff_group";
	public static final List<String> ALL_COMPANY_TYPES = Collections.unmodifiableList(Arrays.asList(
			CompanyConstants.GROUP_TYPE_COMPANY,
			GROUP_TYPE_COMPANY_ADMINS,
			GROUP_TYPE_COMPANY_STAFF
	));
	
	public static final String CASE_CODE_KEY = "COMPORT";

	public static final String CONTENT_CODE_REQUEST_INFORMATION = "prm_company_code_request_information";
	
	public static final Integer APPLICATION_TYPE_APPROVED = 0;
	public static final Integer APPLICATION_TYPE_REJECTED = 1;
	public static final Integer APPLICATION_TYPE_UNHANDLED = 2;
	
	public static final String USER_LOGIN_METADATA = "userLoginEnabledMetaDataForCompanyPortal";
	
	public static final String COMPANY_PORTAL_ROOT_GROUP = "Company Portal";
	public static final String COMPANY_ADMINS_GROUP_IN_COMPANY_PORTAL = "Company Admins";
	public static final String COMPANY_SUPER_ADMINS_GROUP_IN_COMPANY_PORTAL = "Company Super Admins";
	
	public static final String COMPANY_PORTAL_HOME_PAGE_APPLICATION_PROPERTY = "companyPortalHomePage";
	public static final String COMPANY_STAFF_HOME_PAGE_APPLICATION_PROPERTY = "companyStaffHomePage";
	
	public static final String COMPANY_PORTAL_CURRENT_COMPANY_ATTRIBUTE = "companyPortalCurrentCompanyId";
}