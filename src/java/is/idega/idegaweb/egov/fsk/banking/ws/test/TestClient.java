package is.idega.idegaweb.egov.fsk.banking.ws.test;

import is.idega.idegaweb.egov.fsk.banking.beans.PaymentInData;
import is.idega.idegaweb.egov.fsk.banking.beans.PaymentOutData;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.Payment;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentsResult;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.client.IcelandicOnlineBankingPaymentsServiceLocator;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoap;
import is.idega.idegaweb.egov.fsk.business.BankIntegrationServiceBean;

import java.math.BigDecimal;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestClient {
	
	private static final Logger logger = Logger.getLogger(BankIntegrationServiceBean.class.getName());
	
	public static void main(String[] args) {

		try {
			
			IcelandicOnlineBankingPaymentsServiceLocator paymentsLocator = new IcelandicOnlineBankingPaymentsServiceLocator();
			IcelandicOnlineBankingPaymentsSoap service = paymentsLocator.getIcelandicOnlineBankingPaymentsSoap(new URL("https://b2bws.fbl.is/test/statements.asmx"));

			PaymentOutData out = new PaymentOutData("", "", "123456");
			PaymentInData in = new PaymentInData("0123456789", "5ertertert4", "123456", new BigDecimal("100.00"));
			
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
