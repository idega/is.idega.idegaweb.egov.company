package is.idega.idegaweb.egov.company.business;


import javax.ejb.CreateException;
import java.rmi.RemoteException;
import com.idega.business.IBOHome;

public interface CompanyApplicationBusinessHome extends IBOHome {
	public CompanyApplicationBusiness create() throws CreateException,
			RemoteException;
}