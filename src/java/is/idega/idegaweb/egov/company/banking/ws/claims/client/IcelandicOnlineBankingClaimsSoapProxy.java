package is.idega.idegaweb.egov.company.banking.ws.claims.client;

public class IcelandicOnlineBankingClaimsSoapProxy implements is.idega.idegaweb.egov.company.banking.ws.claims.client.IcelandicOnlineBankingClaimsSoap {
  private String _endpoint = null;
  private is.idega.idegaweb.egov.company.banking.ws.claims.client.IcelandicOnlineBankingClaimsSoap icelandicOnlineBankingClaimsSoap = null;
  
  public IcelandicOnlineBankingClaimsSoapProxy() {
    _initIcelandicOnlineBankingClaimsSoapProxy();
  }
  
  public IcelandicOnlineBankingClaimsSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initIcelandicOnlineBankingClaimsSoapProxy();
  }
  
  private void _initIcelandicOnlineBankingClaimsSoapProxy() {
    try {
      icelandicOnlineBankingClaimsSoap = (new is.idega.idegaweb.egov.company.banking.ws.claims.client.IcelandicOnlineBankingClaimsServiceLocator()).getIcelandicOnlineBankingClaimsSoap();
      if (icelandicOnlineBankingClaimsSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)icelandicOnlineBankingClaimsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)icelandicOnlineBankingClaimsSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (icelandicOnlineBankingClaimsSoap != null)
      ((javax.xml.rpc.Stub)icelandicOnlineBankingClaimsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public is.idega.idegaweb.egov.company.banking.ws.claims.client.IcelandicOnlineBankingClaimsSoap getIcelandicOnlineBankingClaimsSoap() {
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap;
  }
  
  public java.lang.String createClaims(is.idega.idegaweb.egov.company.banking.ws.claims.Claim[] claims) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap.createClaims(claims);
  }
  
  public java.lang.String alterClaims(is.idega.idegaweb.egov.company.banking.ws.claims.Claim[] claims) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap.alterClaims(claims);
  }
  
  public java.lang.String cancelClaims(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey[] keys) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap.cancelClaims(keys);
  }
  
  public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult createClaim(is.idega.idegaweb.egov.company.banking.ws.claims.Claim claim) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap.createClaim(claim);
  }
  
  public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult alterClaim(is.idega.idegaweb.egov.company.banking.ws.claims.Claim claim) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap.alterClaim(claim);
  }
  
  public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult cancelClaim(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap.cancelClaim(key);
  }
  
  public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult getClaimOperationResult(java.lang.String id) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap.getClaimOperationResult(id);
  }
  
  public is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult queryClaims(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimsQuery query) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap.queryClaims(query);
  }
  
  public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimInfo queryClaim(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap.queryClaim(key);
  }
  
  public is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult queryPayments(is.idega.idegaweb.egov.company.banking.ws.claims.PaymentsQuery query) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingClaimsSoap == null)
      _initIcelandicOnlineBankingClaimsSoapProxy();
    return icelandicOnlineBankingClaimsSoap.queryPayments(query);
  }
  
  
}