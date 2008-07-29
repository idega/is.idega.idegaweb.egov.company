/**
 * IcelandicOnlineBankingStatementsSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.statements.client;

public interface IcelandicOnlineBankingStatementsSoap extends java.rmi.Remote {
    public is.idega.idegaweb.egov.fsk.banking.ws.statements.AccountStatementResponse getAccountStatement(is.idega.idegaweb.egov.fsk.banking.ws.statements.AccountStatement accountStatement) throws java.rmi.RemoteException;
    public is.idega.idegaweb.egov.fsk.banking.ws.common.CurrencyRate[] getCurrencyRates(is.idega.idegaweb.egov.fsk.banking.ws.common.CurrencyType currencyType, java.util.Calendar date) throws java.rmi.RemoteException;
}
