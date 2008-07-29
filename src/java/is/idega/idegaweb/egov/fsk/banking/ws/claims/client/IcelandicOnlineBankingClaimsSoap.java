/**
 * IcelandicOnlineBankingClaimsSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.claims.client;

public interface IcelandicOnlineBankingClaimsSoap extends java.rmi.Remote {

    /**
     * Create new claims.
     */
    public java.lang.String createClaims(is.idega.idegaweb.egov.fsk.banking.ws.claims.Claim[] claims) throws java.rmi.RemoteException;

    /**
     * Alter existings claims.
     */
    public java.lang.String alterClaims(is.idega.idegaweb.egov.fsk.banking.ws.claims.Claim[] claims) throws java.rmi.RemoteException;

    /**
     * Cancel claims corresponding to the list of unique keys.
     */
    public java.lang.String cancelClaims(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimKey[] keys) throws java.rmi.RemoteException;

    /**
     * Create a new claim.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimOperationResult createClaim(is.idega.idegaweb.egov.fsk.banking.ws.claims.Claim claim) throws java.rmi.RemoteException;

    /**
     * Alter an existing claim.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimOperationResult alterClaim(is.idega.idegaweb.egov.fsk.banking.ws.claims.Claim claim) throws java.rmi.RemoteException;

    /**
     * Cancel a claim corresponding to the unique key.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimOperationResult cancelClaim(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimKey key) throws java.rmi.RemoteException;

    /**
     * Returns the result of an operation where claims have been created,
     * updated or canceled.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimOperationResult getClaimOperationResult(java.lang.String id) throws java.rmi.RemoteException;

    /**
     * Return the status of claims that fall within the parameters
     * of the query.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.QueryClaimsResult queryClaims(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimsQuery query) throws java.rmi.RemoteException;

    /**
     * Return the status of a claim corresponding to a unique key.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimInfo queryClaim(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimKey key) throws java.rmi.RemoteException;

    /**
     * Return the payments that fall within the parameters of the
     * query.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.QueryPaymentsResult queryPayments(is.idega.idegaweb.egov.fsk.banking.ws.claims.PaymentsQuery query) throws java.rmi.RemoteException;
}
