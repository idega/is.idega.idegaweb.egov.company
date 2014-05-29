package is.idega.idegaweb.egov.company.data.bean;

import is.idega.idegaweb.egov.company.EgovCompanyConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.idega.user.data.bean.Group;

@Entity
@DiscriminatorValue(EgovCompanyConstants.GROUP_TYPE_COMPANY_ADMINS)
public class CompanyAdminsGroup extends Group {

	private static final long serialVersionUID = -8798558516833324617L;

}