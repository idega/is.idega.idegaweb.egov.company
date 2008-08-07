package is.idega.idegaweb.egov.company.presentation;

import is.idega.idegaweb.egov.application.presentation.ApplicationBlock;
import is.idega.idegaweb.egov.company.business.CompanyApplicationBusiness;

import com.idega.util.expression.ELUtil;

public abstract class CompanyBlock extends ApplicationBlock {

	protected CompanyApplicationBusiness getCompanyBusiness() {
		CompanyApplicationBusiness compAppBusiness = ELUtil.getInstance().getBean(CompanyApplicationBusiness.SPRING_BEAN_IDENTIFIER);
		return compAppBusiness;
	}

}
