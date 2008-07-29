package is.idega.idegaweb.egov.fsk.data;

import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.company.data.Company;
import com.idega.data.IDOCreateException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class CourseHomeImpl extends IDOFactory implements CourseHome {

	public Class getEntityInterfaceClass() {
		return Course.class;
	}

	public Course findByPrimaryKey(Object pk) throws FinderException {
		return (Course) super.findByPrimaryKeyIDO(pk);
	}

	public Course create() throws CreateException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CourseBMPBean) entity).ejbCreate();
		((CourseBMPBean) entity).ejbPostCreate();
		this.idoCheckInPooledEntity(entity);
		try {
			return findByPrimaryKey(pk);
		}
		catch (FinderException fe) {
			throw new IDOCreateException(fe);
		}
		catch (Exception e) {
			throw new IDOCreateException(e);
		}
	}

	public Collection findByCompany(Company company) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CourseBMPBean) entity).ejbFindByCompany(company);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findApprovedByCompany(Season season, Company company) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CourseBMPBean) entity).ejbFindApprovedByCompany(season, company);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findRejectedByCompany(Season season, Company company) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CourseBMPBean) entity).ejbFindRejectedByCompany(season, company);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findNonApproved() throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CourseBMPBean) entity).ejbFindNonApproved();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findApproved(Season season) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CourseBMPBean) entity).ejbFindApproved(season);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findRejected(Season season) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CourseBMPBean) entity).ejbFindRejected(season);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}