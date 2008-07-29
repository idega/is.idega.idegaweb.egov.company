/**
 * IcelandicOnlineBankingStatementsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.statements.client;

public class IcelandicOnlineBankingStatementsServiceLocator extends org.apache.axis.client.Service implements is.idega.idegaweb.egov.company.banking.ws.statements.client.IcelandicOnlineBankingStatementsService {

    public IcelandicOnlineBankingStatementsServiceLocator() {
    }


    public IcelandicOnlineBankingStatementsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IcelandicOnlineBankingStatementsServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IcelandicOnlineBankingStatementsSoap
    private java.lang.String IcelandicOnlineBankingStatementsSoap_address = "https://b2bws.fbl.is/statements.asmx";

    public java.lang.String getIcelandicOnlineBankingStatementsSoapAddress() {
        return IcelandicOnlineBankingStatementsSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IcelandicOnlineBankingStatementsSoapWSDDServiceName = "IcelandicOnlineBankingStatementsSoap";

    public java.lang.String getIcelandicOnlineBankingStatementsSoapWSDDServiceName() {
        return IcelandicOnlineBankingStatementsSoapWSDDServiceName;
    }

    public void setIcelandicOnlineBankingStatementsSoapWSDDServiceName(java.lang.String name) {
        IcelandicOnlineBankingStatementsSoapWSDDServiceName = name;
    }

    public is.idega.idegaweb.egov.company.banking.ws.statements.client.IcelandicOnlineBankingStatementsSoap getIcelandicOnlineBankingStatementsSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IcelandicOnlineBankingStatementsSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIcelandicOnlineBankingStatementsSoap(endpoint);
    }

    public is.idega.idegaweb.egov.company.banking.ws.statements.client.IcelandicOnlineBankingStatementsSoap getIcelandicOnlineBankingStatementsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            is.idega.idegaweb.egov.company.banking.ws.statements.client.IcelandicOnlineBankingStatementsSoapStub _stub = new is.idega.idegaweb.egov.company.banking.ws.statements.client.IcelandicOnlineBankingStatementsSoapStub(portAddress, this);
            _stub.setPortName(getIcelandicOnlineBankingStatementsSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIcelandicOnlineBankingStatementsSoapEndpointAddress(java.lang.String address) {
        IcelandicOnlineBankingStatementsSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (is.idega.idegaweb.egov.company.banking.ws.statements.client.IcelandicOnlineBankingStatementsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                is.idega.idegaweb.egov.company.banking.ws.statements.client.IcelandicOnlineBankingStatementsSoapStub _stub = new is.idega.idegaweb.egov.company.banking.ws.statements.client.IcelandicOnlineBankingStatementsSoapStub(new java.net.URL(IcelandicOnlineBankingStatementsSoap_address), this);
                _stub.setPortName(getIcelandicOnlineBankingStatementsSoapWSDDServiceName());
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
        if ("IcelandicOnlineBankingStatementsSoap".equals(inputPortName)) {
            return getIcelandicOnlineBankingStatementsSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/Statements", "IcelandicOnlineBankingStatementsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/Statements", "IcelandicOnlineBankingStatementsSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IcelandicOnlineBankingStatementsSoap".equals(portName)) {
            setIcelandicOnlineBankingStatementsSoapEndpointAddress(address);
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
