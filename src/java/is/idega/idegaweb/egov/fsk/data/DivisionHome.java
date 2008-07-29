package is.idega.idegaweb.egov.fsk.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.user.data.GroupHome;

public interface DivisionHome extends GroupHome {

	public Collection findAll() throws FinderException;
}