package is.idega.idegaweb.egov.company.data.bean;

import is.idega.idegaweb.egov.company.EgovCompanyConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.idega.user.data.bean.Group;

@Entity
@DiscriminatorValue(EgovCompanyConstants.GROUP_TYPE_COMPANY_STAFF)
public class CompanyStaffGroup extends Group {

	private static final long serialVersionUID = 4892938999476646342L;

}