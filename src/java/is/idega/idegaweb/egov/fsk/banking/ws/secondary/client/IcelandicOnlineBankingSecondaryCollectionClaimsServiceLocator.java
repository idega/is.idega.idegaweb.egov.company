/**
 * IcelandicOnlineBankingSecondaryCollectionClaimsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.secondary.client;

public class IcelandicOnlineBankingSecondaryCollectionClaimsServiceLocator extends org.apache.axis.client.Service implements is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsService {

    public IcelandicOnlineBankingSecondaryCollectionClaimsServiceLocator() {
    }


    public IcelandicOnlineBankingSecondaryCollectionClaimsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IcelandicOnlineBankingSecondaryCollectionClaimsServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IcelandicOnlineBankingSecondaryCollectionClaimsSoap
    private java.lang.String IcelandicOnlineBankingSecondaryCollectionClaimsSoap_address = "https://b2bws.fbl.is/secondarycollectionclaims.asmx";

    public java.lang.String getIcelandicOnlineBankingSecondaryCollectionClaimsSoapAddress() {
        return IcelandicOnlineBankingSecondaryCollectionClaimsSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IcelandicOnlineBankingSecondaryCollectionClaimsSoapWSDDServiceName = "IcelandicOnlineBankingSecondaryCollectionClaimsSoap";

    public java.lang.String getIcelandicOnlineBankingSecondaryCollectionClaimsSoapWSDDServiceName() {
        return IcelandicOnlineBankingSecondaryCollectionClaimsSoapWSDDServiceName;
    }

    public void setIcelandicOnlineBankingSecondaryCollectionClaimsSoapWSDDServiceName(java.lang.String name) {
        IcelandicOnlineBankingSecondaryCollectionClaimsSoapWSDDServiceName = name;
    }

    public is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoap getIcelandicOnlineBankingSecondaryCollectionClaimsSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IcelandicOnlineBankingSecondaryCollectionClaimsSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIcelandicOnlineBankingSecondaryCollectionClaimsSoap(endpoint);
    }

    public is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoap getIcelandicOnlineBankingSecondaryCollectionClaimsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoapStub _stub = new is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoapStub(portAddress, this);
            _stub.setPortName(getIcelandicOnlineBankingSecondaryCollectionClaimsSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIcelandicOnlineBankingSecondaryCollectionClaimsSoapEndpointAddress(java.lang.String address) {
        IcelandicOnlineBankingSecondaryCollectionClaimsSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoapStub _stub = new is.idega.idegaweb.egov.fsk.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoapStub(new java.net.URL(IcelandicOnlineBankingSecondaryCollectionClaimsSoap_address), this);
                _stub.setPortName(getIcelandicOnlineBankingSecondaryCollectionClaimsSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("IcelandicOnlineBankingSecondaryCollectionClaimsSoap".equals(inputPortName)) {
            return getIcelandicOnlineBankingSecondaryCollectionClaimsSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "IcelandicOnlineBankingSecondaryCollectionClaimsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "IcelandicOnlineBankingSecondaryCollectionClaimsSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IcelandicOnlineBankingSecondaryCollectionClaimsSoap".equals(portName)) {
            setIcelandicOnlineBankingSecondaryCollectionClaimsSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
