package is.idega.idegaweb.egov.fsk.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import com.idega.company.data.Company;

public interface ApplicationHome extends IDOHome {
	public Application create() throws CreateException;

	public Application findByPrimaryKey(Object pk) throws FinderException;

	public Collection findByStatus(String[] caseStatus) throws FinderException;

	public Application findByCompany(Company company) throws FinderException;
}