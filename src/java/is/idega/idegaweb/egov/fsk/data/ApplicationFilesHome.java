package is.idega.idegaweb.egov.fsk.data;


import com.idega.data.IDOException;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;

public interface ApplicationFilesHome extends IDOHome {

	public ApplicationFiles create() throws CreateException;

	public ApplicationFiles findByPrimaryKey(Object pk) throws FinderException;

	public Collection findByApplication(Application application) throws FinderException;

	public Collection findAllByApplicationAndType(Application application, String type) throws FinderException;

	public ApplicationFiles findByApplicationAndType(Application application, String type) throws FinderException;

	public int getNumberOfFiles(Application application, String[] types) throws IDOException;
}