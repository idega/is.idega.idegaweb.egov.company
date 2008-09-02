package is.idega.idegaweb.egov.company.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHome;
import java.rmi.RemoteException;

public interface CompanyApplicationBusinessHome extends IBOHome {
	
	public CompanyApplicationBusiness create() throws CreateException, RemoteException;
}