package is.idega.idegaweb.egov.company.business;


import com.idega.core.file.data.ICFile;

import is.idega.idegaweb.egov.company.data.Course;
import is.idega.idegaweb.egov.company.data.Period;

import java.util.Map;
import javax.servlet.http.HttpSessionBindingEvent;
import com.idega.business.IBOSession;
import java.rmi.RemoteException;

public interface ApplicationSession extends IBOSession {
	/**
	 * @see is.idega.idegaweb.egov.company.business.ApplicationSessionBean#valueBound
	 */
	public void valueBound(HttpSessionBindingEvent arg0) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.ApplicationSessionBean#valueUnbound
	 */
	public void valueUnbound(HttpSessionBindingEvent arg0)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.ApplicationSessionBean#getFiles
	 */
	public Map getFiles() throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.ApplicationSessionBean#addFile
	 */
	public void addFile(ICFile file, String type) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.ApplicationSessionBean#removeFile
	 */
	public void removeFile(ICFile file) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.ApplicationSessionBean#contains
	 */
	public boolean contains(String filename) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.ApplicationSessionBean#addAllocation
	 */
	public void addAllocation(Period period, Course course)
			throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.ApplicationSessionBean#getNumberOfAllocations
	 */
	public int getNumberOfAllocations(Period period) throws RemoteException;

	/**
	 * @see is.idega.idegaweb.egov.company.business.ApplicationSessionBean#clear
	 */
	public void clear() throws RemoteException;
}