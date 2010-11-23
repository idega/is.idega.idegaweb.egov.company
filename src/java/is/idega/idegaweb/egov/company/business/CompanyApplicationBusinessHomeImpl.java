package is.idega.idegaweb.egov.company.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHomeImpl;

public class CompanyApplicationBusinessHomeImpl extends IBOHomeImpl implements
		CompanyApplicationBusinessHome {
	public Class getBeanInterfaceClass() {
		return CompanyApplicationBusiness.class;
	}

	public CompanyApplicationBusiness create() throws CreateException {
		return (CompanyApplicationBusiness) super.createIBO();
	}
}