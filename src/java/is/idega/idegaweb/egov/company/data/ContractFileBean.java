package is.idega.idegaweb.egov.company.data;

import java.io.InputStream;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Sep 22, 2008 by Author: Anton 
 *
 */

public class ContractFileBean {
	private String name;
	private int size;
	private InputStream contractFileStream;
	private String mimeType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public InputStream getContractFileStream() {
		return contractFileStream;
	}
	public void setContractFileStream(InputStream contractFileStream) {
		this.contractFileStream = contractFileStream;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}
