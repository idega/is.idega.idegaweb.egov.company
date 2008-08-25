package is.idega.idegaweb.egov.company.business;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.idega.business.IBOHome;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 25, 2008 by Author: Anton 
 *
 */

public interface CompanyApplicationBusinessHome extends IBOHome {
	public CompanyApplicationBusiness create() throws CreateException, RemoteException;
}
