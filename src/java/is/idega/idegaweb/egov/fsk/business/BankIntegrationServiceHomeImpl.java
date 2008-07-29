package is.idega.idegaweb.egov.fsk.business;

import javax.ejb.CreateException;

import com.idega.business.IBOHomeImpl;

public class BankIntegrationServiceHomeImpl extends IBOHomeImpl implements
		BankIntegrationServiceHome {

	public Class getBeanInterfaceClass() {
		return BankIntegrationService.class;
	}

	public BankIntegrationService create() throws CreateException {
		return (BankIntegrationService) super.createIBO();
	}

}
