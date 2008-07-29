package is.idega.idegaweb.egov.fsk.banking.beans;

import is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentIn;
import is.idega.idegaweb.egov.fsk.banking.ws.payments.Transfer;

import java.math.BigDecimal;

public class PaymentInData {
	
	private String ownerId;
	private String ownerAccount;
	private BigDecimal amount;
	private String invoiceId;
	
	public PaymentInData(String ownerId, String ownerAccount, String invoiceId, BigDecimal amount) {
		this.ownerId = ownerId;
		this.ownerAccount = ownerAccount;
		this.invoiceId = invoiceId;
		this.amount = amount;
	}
	
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerAccount() {
		return ownerAccount;
	}
	public void setOwnerAccount(String ownerAccount) {
		this.ownerAccount = ownerAccount;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	public PaymentIn getPaymentInBean() {
		PaymentIn in = new PaymentIn();
		in.setAmount(this.amount);
		Transfer transfer = new Transfer();
		transfer.setAccount(this.ownerAccount);
		transfer.setAccountOwnerID(this.ownerId);
		in.setTransfer(transfer);
		return in;
	}

}
