package is.idega.idegaweb.egov.fsk.data;

import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import com.idega.company.data.Company;

public interface CourseHome extends IDOHome {

	public Course create() throws CreateException;

	public Course findByPrimaryKey(Object pk) throws FinderException;

	public Collection findByCompany(Company company) throws FinderException;

	public Collection findApprovedByCompany(Season season, Company company) throws FinderException;

	public Collection findRejectedByCompany(Season season, Company company) throws FinderException;

	public Collection findNonApproved() throws FinderException;

	public Collection findApproved(Season season) throws FinderException;

	public Collection findRejected(Season season) throws FinderException;
}