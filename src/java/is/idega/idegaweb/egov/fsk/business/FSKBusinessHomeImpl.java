package is.idega.idegaweb.egov.fsk.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHomeImpl;

public class FSKBusinessHomeImpl extends IBOHomeImpl implements FSKBusinessHome {

	public Class getBeanInterfaceClass() {
		return FSKBusiness.class;
	}

	public FSKBusiness create() throws CreateException {
		return (FSKBusiness) super.createIBO();
	}
}