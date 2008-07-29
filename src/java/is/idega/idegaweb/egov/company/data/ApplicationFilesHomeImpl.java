package is.idega.idegaweb.egov.company.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class ApplicationFilesHomeImpl extends IDOFactory implements ApplicationFilesHome {

	public Class getEntityInterfaceClass() {
		return ApplicationFiles.class;
	}

	public ApplicationFiles create() throws CreateException {
		return (ApplicationFiles) super.createIDO();
	}

	public ApplicationFiles findByPrimaryKey(Object pk) throws FinderException {
		return (ApplicationFiles) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findByApplication(Application application) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ApplicationFilesBMPBean) entity).ejbFindByApplication(application);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllByApplicationAndType(Application application, String type) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((ApplicationFilesBMPBean) entity).ejbFindAllByApplicationAndType(application, type);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public ApplicationFiles findByApplicationAndType(Application application, String type) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ApplicationFilesBMPBean) entity).ejbFindByApplicationAndType(application, type);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public int getNumberOfFiles(Application application, String[] types) throws IDOException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		int theReturn = ((ApplicationFilesBMPBean) entity).ejbHomeGetNumberOfFiles(application, types);
		this.idoCheckInPooledEntity(entity);
		return theReturn;
	}
}