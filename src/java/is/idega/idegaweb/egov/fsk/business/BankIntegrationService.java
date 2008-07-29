package is.idega.idegaweb.egov.fsk.business;

import is.idega.idegaweb.egov.fsk.banking.beans.PaymentInData;
import is.idega.idegaweb.egov.fsk.banking.beans.PaymentOutData;

import java.rmi.RemoteException;
import java.util.List;

import com.idega.business.IBOService;

public interface BankIntegrationService extends IBOService {
	
	/**
	 * @see is.idega.idegaweb.egov.fsk.business.BankIntegrationServiceBean#paySingleInvoice
	 */
	public boolean paySingleInvoice(PaymentOutData out, PaymentInData in) throws RemoteException, Exception;
	
	/**
	 * @see is.idega.idegaweb.egov.fsk.business.BankIntegrationServiceBean#payMultipleInvoices
	 */
	public boolean payMultipleInvoices(PaymentOutData out, PaymentInData[] ins) throws RemoteException, Exception;

}
