/**
 * IcelandicOnlineBankingPaymentsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.payments.client;

public class IcelandicOnlineBankingPaymentsServiceLocator extends org.apache.axis.client.Service implements is.idega.idegaweb.egov.company.banking.ws.payments.client.IcelandicOnlineBankingPaymentsService {

    public IcelandicOnlineBankingPaymentsServiceLocator() {
    }


    public IcelandicOnlineBankingPaymentsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IcelandicOnlineBankingPaymentsServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IcelandicOnlineBankingPaymentsSoap
    private java.lang.String IcelandicOnlineBankingPaymentsSoap_address = "https://b2bws.fbl.is/payments.asmx";

    public java.lang.String getIcelandicOnlineBankingPaymentsSoapAddress() {
        return IcelandicOnlineBankingPaymentsSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IcelandicOnlineBankingPaymentsSoapWSDDServiceName = "IcelandicOnlineBankingPaymentsSoap";

    public java.lang.String getIcelandicOnlineBankingPaymentsSoapWSDDServiceName() {
        return IcelandicOnlineBankingPaymentsSoapWSDDServiceName;
    }

    public void setIcelandicOnlineBankingPaymentsSoapWSDDServiceName(java.lang.String name) {
        IcelandicOnlineBankingPaymentsSoapWSDDServiceName = name;
    }

    public is.idega.idegaweb.egov.company.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoap getIcelandicOnlineBankingPaymentsSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IcelandicOnlineBankingPaymentsSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIcelandicOnlineBankingPaymentsSoap(endpoint);
    }

    public is.idega.idegaweb.egov.company.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoap getIcelandicOnlineBankingPaymentsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            is.idega.idegaweb.egov.company.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoapStub _stub = new is.idega.idegaweb.egov.company.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoapStub(portAddress, this);
            _stub.setPortName(getIcelandicOnlineBankingPaymentsSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIcelandicOnlineBankingPaymentsSoapEndpointAddress(java.lang.String address) {
        IcelandicOnlineBankingPaymentsSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (is.idega.idegaweb.egov.company.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                is.idega.idegaweb.egov.company.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoapStub _stub = new is.idega.idegaweb.egov.company.banking.ws.payments.client.IcelandicOnlineBankingPaymentsSoapStub(new java.net.URL(IcelandicOnlineBankingPaymentsSoap_address), this);
                _stub.setPortName(getIcelandicOnlineBankingPaymentsSoapWSDDServiceName());
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
        if ("IcelandicOnlineBankingPaymentsSoap".equals(inputPortName)) {
            return getIcelandicOnlineBankingPaymentsSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/Payments", "IcelandicOnlineBankingPaymentsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/Payments", "IcelandicOnlineBankingPaymentsSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IcelandicOnlineBankingPaymentsSoap".equals(portName)) {
            setIcelandicOnlineBankingPaymentsSoapEndpointAddress(address);
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
