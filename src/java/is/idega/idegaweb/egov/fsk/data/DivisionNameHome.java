package is.idega.idegaweb.egov.fsk.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import com.idega.company.data.CompanyType;

public interface DivisionNameHome extends IDOHome {

	public DivisionName create() throws CreateException;

	public DivisionName findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAllByType(CompanyType type) throws FinderException;

	public DivisionName findByNameAndType(String name, CompanyType type) throws FinderException;
}