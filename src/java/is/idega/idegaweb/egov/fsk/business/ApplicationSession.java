package is.idega.idegaweb.egov.fsk.business;


import com.idega.core.file.data.ICFile;
import is.idega.idegaweb.egov.fsk.data.Period;
import java.util.Map;
import javax.servlet.http.HttpSessionBindingEvent;
import com.idega.business.IBOSession;
import java.rmi.RemoteException;
import is.idega.idegaweb.egov.fsk.data.Course;

public interface ApplicationSession extends IBOSession {
	/**
	 * @see is.idega.idegaweb.egov.fsk.business.ApplicationSessionBean#valueBound
	 */
	public void valueBound(HttpSessionBindingEvent arg0) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.ApplicationSessionBean#valueUnbound
	 */
	public void valueUnbound(HttpSessionBindingEvent arg0)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.ApplicationSessionBean#getFiles
	 */
	public Map getFiles() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.ApplicationSessionBean#addFile
	 */
	public void addFile(ICFile file, String type) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.ApplicationSessionBean#removeFile
	 */
	public void removeFile(ICFile file) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.ApplicationSessionBean#contains
	 */
	public boolean contains(String filename) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.ApplicationSessionBean#addAllocation
	 */
	public void addAllocation(Period period, Course course)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.ApplicationSessionBean#getNumberOfAllocations
	 */
	public int getNumberOfAllocations(Period period) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.fsk.business.ApplicationSessionBean#clear
	 */
	public void clear() throws RemoteException;
}