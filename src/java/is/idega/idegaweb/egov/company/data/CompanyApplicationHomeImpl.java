package is.idega.idegaweb.egov.company.data;

import is.idega.idegaweb.egov.application.data.Application;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.company.data.Company;
import com.idega.data.IDOEntity;
import com.idega.user.data.UserBMPBean;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 20, 2008 by Author: Anton 
 *
 */

public class CompanyApplicationHomeImpl extends com.idega.data.IDOFactory implements CompanyApplicationHome {
	@Override
	protected Class getEntityInterfaceClass(){
		return CompanyApplication.class;
	}
	
	public CompanyApplication create() throws CreateException {
		return (CompanyApplication) super.createIDO();
	}

	public CompanyApplication findByPersonalID(java.lang.String p0)throws javax.ejb.FinderException{
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((UserBMPBean)entity).ejbFindByPersonalID(p0);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public CompanyApplication findByPrimaryKey(Object pk) throws javax.ejb.FinderException{
		return (CompanyApplication) super.findByPrimaryKeyIDO(pk);
	}
	
	public CompanyApplication findByCompany(Company company) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CompanyApplicationBMPBean) entity).ejbFindByCompany(company);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
	
	public Collection<Application> findAllByCaseCodesAndStatuses(String[] caseCodes, String[] statuses) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CompanyApplicationBMPBean) entity).ejbFindAllByCaseCodesAndStatuses(caseCodes, statuses);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}
