/**
 * IcelandicOnlineBankingPaymentsSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.payments.client;

public interface IcelandicOnlineBankingPaymentsSoap extends java.rmi.Remote {
    public is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentsResult doPayment(is.idega.idegaweb.egov.fsk.banking.ws.payments.Payment payment) throws java.rmi.RemoteException;
    public java.lang.String doPayments(is.idega.idegaweb.egov.fsk.banking.ws.payments.Payments payments) throws java.rmi.RemoteException;
    public is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentsResult getPaymentResult(java.lang.String paymentID) throws java.rmi.RemoteException;
    public is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentsResult getPaymentsResult(java.lang.String paymentID, is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentsStatus filter) throws java.rmi.RemoteException;
}
