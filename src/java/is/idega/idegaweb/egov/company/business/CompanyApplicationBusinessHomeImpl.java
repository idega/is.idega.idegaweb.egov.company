package is.idega.idegaweb.egov.company.business;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.idega.business.IBOHomeImpl;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 25, 2008 by Author: Anton 
 *
 */

public class CompanyApplicationBusinessHomeImpl extends IBOHomeImpl implements CompanyApplicationBusinessHome {

	private static final long serialVersionUID = 6199578008318491126L;

	public Class getBeanInterfaceClass() {
		return CompanyApplicationBusiness.class;
	}

	public CompanyApplicationBusiness create() throws CreateException,
			RemoteException {
		return (CompanyApplicationBusiness) super.createIBO();
	}
}
