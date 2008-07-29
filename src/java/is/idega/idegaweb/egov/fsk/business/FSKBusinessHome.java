package is.idega.idegaweb.egov.fsk.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHome;
import java.rmi.RemoteException;

public interface FSKBusinessHome extends IBOHome {

	public FSKBusiness create() throws CreateException, RemoteException;
}