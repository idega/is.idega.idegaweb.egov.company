package is.idega.idegaweb.egov.company.banking.beans;

import is.idega.idegaweb.egov.company.banking.ws.payments.PaymentOut;

public class PaymentOutData {
	
	private String payerId;
	private String payerAccount;
	private String invoiceId;
	
	public PaymentOutData(String payerId, String payerAccount, String invoiceId) {
		this.payerId = payerId;
		this.payerAccount = payerAccount;
		this.invoiceId = invoiceId;
	}
	
	public String getPayerId() {
		return payerId;
	}
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	public String getPayerAccount() {
		return payerAccount;
	}
	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	public PaymentOut getPaymentOutBean() {
		PaymentOut out = new PaymentOut();
		out.setAccount(this.payerAccount);
		out.setAccountOwnerID(this.payerId);
		out.setBillNumber(this.invoiceId);
		return out;
	}

}
