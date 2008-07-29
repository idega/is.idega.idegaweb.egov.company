package is.idega.idegaweb.egov.fsk.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;

public interface ConstantHome extends IDOHome {

	public Constant create() throws CreateException;

	public Constant findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAll(Period period) throws FinderException;

	public Constant findByType(Period period, String type) throws FinderException;
}