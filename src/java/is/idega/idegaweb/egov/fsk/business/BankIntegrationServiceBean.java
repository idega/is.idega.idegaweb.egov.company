package is.idega.idegaweb.egov.fsk.business;

import is.idega.idegaweb.egov.fsk.banking.beans.PaymentInData;
import is.idega.idegaweb.egov.fsk.banking.beans.PaymentOutData;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.Payment;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentIn;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.Payments;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentsResult;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.client.IcelandicOnlineBankingPaymentsServiceLocator;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoap;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.rpc.ServiceException;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.message.token.UsernameToken;

import com.idega.business.IBOServiceBean;
import com.idega.idegaweb.IWMainApplication;

public class BankIntegrationServiceBean extends IBOServiceBean implements BankIntegrationService {
	
	private static final Logger logger = Logger.getLogger(BankIntegrationServiceBean.class.getName());
	
	private IcelandicOnlineBankingPaymentsServiceLocator paymentsLocator;
	
	/**
	 * @see BankIntegrationService method description
	 * 
	 */
	public boolean paySingleInvoice(PaymentOutData out, PaymentInData in) throws RemoteException, Exception {

		IcelandicOnlineBankingPaymentsSoap service = getBankPaymentsService();

		PaymentsResult paymentsResult = null;

		try {
			Payment payment = new Payment();
			payment.setIn(in.getPaymentInBean());
			payment.setOut(out.getPaymentOutBean());
			paymentsResult = service.doPayment(payment);
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "Exception while trying to process payment in the banking system: ", e);
		}
		
		if(paymentsResult == null) {
			
		}

		return true;
	}
	
	/**
	 * @see BankIntegrationService method description
	 * 
	 */
	public boolean payMultipleInvoices(PaymentOutData out, PaymentInData[] ins) throws RemoteException, Exception {

		IcelandicOnlineBankingPaymentsSoap service = getBankPaymentsService();

		String paymentsResult = null;

		try {
			Payments payments = new Payments();
			payments.setOut(out.getPaymentOutBean());
			PaymentIn[] pin = new PaymentIn[ins.length];
			for(int i = 0; i < ins.length; i++) {
				pin[i] = ins[i].getPaymentInBean();
			}
			payments.setIn(pin);
			paymentsResult = service.doPayments(payments);
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "Exceptioon while trying to process payment in the banking system: ", e);
		}
		
		if(paymentsResult == null || paymentsResult.equals("")) {
			
		}

		return true;
	}
	
	protected IcelandicOnlineBankingPaymentsSoap getBankPaymentsService() throws ServiceException {
		if (paymentsLocator == null) {
			paymentsLocator = new IcelandicOnlineBankingPaymentsServiceLocator();
		}
		IWMainApplication iwma = getIWMainApplication();
		String urlString = iwma.getSettings().getProperty("bank.b2b.url");
		if(urlString!=null){
			URL url;
			try {
				url = new URL(urlString);
				return paymentsLocator.getIcelandicOnlineBankingPaymentsSoap(url);
			}
			catch (MalformedURLException e) {
				throw new ServiceException(e);
			}
		}
		else{
			return paymentsLocator.getIcelandicOnlineBankingPaymentsSoap();
		}
	}

}
