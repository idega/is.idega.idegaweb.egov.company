package is.idega.idegaweb.egov.company.business;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.idega.business.IBOHome;

public interface BankIntegrationServiceHome extends IBOHome {
	
	public BankIntegrationService create() throws CreateException, RemoteException;

}
