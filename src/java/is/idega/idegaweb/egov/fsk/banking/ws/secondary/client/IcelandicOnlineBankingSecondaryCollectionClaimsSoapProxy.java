package is.idega.idegaweb.egov.fsk.banking.ws.secondary.client;

public class IcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy implements is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoap {
  private String _endpoint = null;
  private is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoap icelandicOnlineBankingSecondaryCollectionClaimsSoap = null;
  
  public IcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy() {
    _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
  }
  
  public IcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
  }
  
  private void _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy() {
    try {
      icelandicOnlineBankingSecondaryCollectionClaimsSoap = (new is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsServiceLocator()).getIcelandicOnlineBankingSecondaryCollectionClaimsSoap();
      if (icelandicOnlineBankingSecondaryCollectionClaimsSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)icelandicOnlineBankingSecondaryCollectionClaimsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)icelandicOnlineBankingSecondaryCollectionClaimsSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap != null)
      ((javax.xml.rpc.Stub)icelandicOnlineBankingSecondaryCollectionClaimsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoap getIcelandicOnlineBankingSecondaryCollectionClaimsSoap() {
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap;
  }
  
  public java.lang.String alterClaims(is.idega.idegaweb.egov.fsk.banking.ws.claims.Claim[] claims) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.alterClaims(claims);
  }
  
  public java.lang.String cancelClaims(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimKey[] keys) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.cancelClaims(keys);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimOperationResult alterClaim(is.idega.idegaweb.egov.fsk.banking.ws.claims.Claim claim) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.alterClaim(claim);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimOperationResult cancelClaim(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimKey key) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.cancelClaim(key);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimOperationResult getClaimOperationResult(java.lang.String id) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.getClaimOperationResult(id);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.claims.QueryClaimsResult queryClaims(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimsQuery query) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.queryClaims(query);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimInfo queryClaim(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimKey key) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.queryClaim(key);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.claims.QueryPaymentsResult queryPayments(is.idega.idegaweb.egov.fsk.banking.ws.claims.PaymentsQuery query) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.queryPayments(query);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.claims.QueryPaymentsResult querySecondaryCollectionPayments(is.idega.idegaweb.egov.fsk.banking.ws.claims.SecondaryCollectionPaymentsQuery query) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.querySecondaryCollectionPayments(query);
  }
  
  public is.idega.idegaweb.egov.fsk.banking.ws.claims.QueryClaimsResult querySecondaryCollectionClaims(is.idega.idegaweb.egov.fsk.banking.ws.claims.SecondaryCollectionClaimsQuery query) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.querySecondaryCollectionClaims(query);
  }
  
  public java.lang.String secondaryCollectionReturnClaim(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimKey key) throws java.rmi.RemoteException{
    if (icelandicOnlineBankingSecondaryCollectionClaimsSoap == null)
      _initIcelandicOnlineBankingSecondaryCollectionClaimsSoapProxy();
    return icelandicOnlineBankingSecondaryCollectionClaimsSoap.secondaryCollectionReturnClaim(key);
  }
  
  
}