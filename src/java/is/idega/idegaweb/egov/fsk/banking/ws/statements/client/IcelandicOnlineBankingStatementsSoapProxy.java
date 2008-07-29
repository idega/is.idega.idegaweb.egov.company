package is.idega.idegaweb.egov.fsk.banking.ws.statements.client;

public class IcelandicOnlineBankingStatementsSoapProxy implements is.idega.idegaweb.egov.fsk.banking.ws.statements.client.IcelandicOnlineBankingStatementsSoap {
  private String _endpoint = null;
  private is.idega.idegaweb.egov.fsk.banking.ws.statements.client.IcelandicOnlineBankingStatementsSoap icelandicOnlineBankingStatementsSoap = null;
  
  public IcelandicOnlineBankingStatementsSoapProxy() {
    _initIcelandicOnlineBankingStatementsSoapProxy();
  }
  
  public IcelandicOnlineBankingStatementsSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initIcelandicOnlineBankingStatementsSoapProxy();
  }
  
  private void _initIcelandicOnlineBankingStatementsSoapProxy() {
    try {
      icelandicOnlineBankingStatementsSoap = (new is.idega.idegaweb.egov.fsk.banking.ws.statements.client.IcelandicOnlineBankingStatementsServiceLocator()).getIcelandicOnlineBankingStatementsSoap();
      if (icelandicOnlineBankingStatementsSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)icelandicOnlineBankingStatementsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)icelandicOnlineBankingStatementsSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (icelandicOnlineBankingStatementsSoap != null)
      ((javax.xml.rpc.Stub)icelandicOnlineBankingStatementsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.statements.client.IcelandicOnlineBankingStatementsSoap getIcelandicOnlineBankingStatementsSoap() {
    if (icelandicOnlineBankingStatementsSoap == null)
      _initIcelandicOnlineBankingStatementsSoapProxy();
    return icelandicOnlineBankingStatementsSoap;
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.statements.AccountStatementResponse getAccountStatement(is.idega.idegaweb.egov.fsk.banking.ws.statements.AccountStatement accountStatement) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingStatementsSoap == null)
      _initIcelandicOnlineBankingStatementsSoapProxy();
    return icelandicOnlineBankingStatementsSoap.getAccountStatement(accountStatement);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.common.CurrencyRate[] getCurrencyRates(is.idega.idegaweb.egov.fsk.banking.ws.common.CurrencyType currencyType, java.util.Calendar date) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingStatementsSoap == null)
      _initIcelandicOnlineBankingStatementsSoapProxy();
    return icelandicOnlineBankingStatementsSoap.getCurrencyRates(currencyType, date);
  }
  
  
}