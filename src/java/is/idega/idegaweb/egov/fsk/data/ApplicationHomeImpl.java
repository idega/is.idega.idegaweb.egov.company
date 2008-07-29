package is.idega.idegaweb.egov.fsk.data;


import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.company.data.Company;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class ApplicationHomeImpl extends IDOFactory implements ApplicationHome {
	public Class getEntityInterfaceClass() {
		return Application.class;
	}

	public Application create() throws CreateException {
		return (Application) super.createIDO();
	}

	public Application findByPrimaryKey(Object pk) throws FinderException {
		return (Application) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findByStatus(String[] caseStatus) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ApplicationBMPBean) entity)
				.ejbFindByStatus(caseStatus);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Application findByCompany(Company company) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ApplicationBMPBean) entity).ejbFindByCompany(company);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}