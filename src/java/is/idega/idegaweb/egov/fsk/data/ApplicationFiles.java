package is.idega.idegaweb.egov.fsk.data;


import com.idega.core.file.data.ICFile;
import com.idega.data.IDOEntity;

public interface ApplicationFiles extends IDOEntity {

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ApplicationFilesBMPBean#getApplication
	 */
	public Application getApplication();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ApplicationFilesBMPBean#getFile
	 */
	public ICFile getFile();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ApplicationFilesBMPBean#getSeason
	 */
	public Season getSeason();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ApplicationFilesBMPBean#getType
	 */
	public String getType();

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ApplicationFilesBMPBean#setAppliation
	 */
	public void setAppliation(Application application);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ApplicationFilesBMPBean#setFile
	 */
	public void setFile(ICFile file);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ApplicationFilesBMPBean#setSeason
	 */
	public void setSeason(Season season);

	/**
	 * @see is.idega.idegaweb.egov.fsk.data.ApplicationFilesBMPBean#setType
	 */
	public void setType(String type);
}