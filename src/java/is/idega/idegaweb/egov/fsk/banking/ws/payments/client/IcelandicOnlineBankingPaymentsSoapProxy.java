package is.idega.idegaweb.egov.fsk.banking.ws.payments.client;

public class IcelandicOnlineBankingPaymentsSoapProxy implements is.idega.idegaweb.egov.fsk.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoap {
  private String _endpoint = null;
  private is.idega.idegaweb.egov.fsk.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoap icelandicOnlineBankingPaymentsSoap = null;
  
  public IcelandicOnlineBankingPaymentsSoapProxy() {
    _initIcelandicOnlineBankingPaymentsSoapProxy();
  }
  
  public IcelandicOnlineBankingPaymentsSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initIcelandicOnlineBankingPaymentsSoapProxy();
  }
  
  private void _initIcelandicOnlineBankingPaymentsSoapProxy() {
    try {
      icelandicOnlineBankingPaymentsSoap = (new is.idega.idegaweb.egov.fsk.banking.ws.payments.client.IcelandicOnlineBankingPaymentsServiceLocator()).getIcelandicOnlineBankingPaymentsSoap();
      if (icelandicOnlineBankingPaymentsSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)icelandicOnlineBankingPaymentsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)icelandicOnlineBankingPaymentsSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (icelandicOnlineBankingPaymentsSoap != null)
      ((javax.xml.rpc.Stub)icelandicOnlineBankingPaymentsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoap getIcelandicOnlineBankingPaymentsSoap() {
    if (icelandicOnlineBankingPaymentsSoap == null)
      _initIcelandicOnlineBankingPaymentsSoapProxy();
    return icelandicOnlineBankingPaymentsSoap;
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentsResult doPayment(is.idega.idegaweb.egov.fsk.banking.ws.payments.Payment payment) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingPaymentsSoap == null)
      _initIcelandicOnlineBankingPaymentsSoapProxy();
    return icelandicOnlineBankingPaymentsSoap.doPayment(payment);
  }
  
  public java.lang.String doPayments(is.idega.idegaweb.egov.fsk.banking.ws.payments.Payments payments) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingPaymentsSoap == null)
      _initIcelandicOnlineBankingPaymentsSoapProxy();
    return icelandicOnlineBankingPaymentsSoap.doPayments(payments);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentsResult getPaymentResult(java.lang.String paymentID) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingPaymentsSoap == null)
      _initIcelandicOnlineBankingPaymentsSoapProxy();
    return icelandicOnlineBankingPaymentsSoap.getPaymentResult(paymentID);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentsResult getPaymentsResult(java.lang.String paymentID, is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentsStatus filter) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingPaymentsSoap == null)
      _initIcelandicOnlineBankingPaymentsSoapProxy();
    return icelandicOnlineBankingPaymentsSoap.getPaymentsResult(paymentID, filter);
  }
  
  
}