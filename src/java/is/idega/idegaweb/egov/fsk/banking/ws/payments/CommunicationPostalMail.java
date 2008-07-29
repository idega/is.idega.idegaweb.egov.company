/**
 * CommunicationPostalMail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.payments;

public class CommunicationPostalMail  extends is.idega.idegaweb.egov.fsk.banking.ws.payments.Receipt  implements java.io.Serializable {
    /* The address for the receiver of the receipt */
    private is.idega.idegaweb.egov.fsk.banking.ws.common.Address receiverAddress;

    /* If true, then the address associated with the person ID is
     * used. The  person ID is either the account owner ID of the senders
     * account or the receivers account, depending on the type of the receipt */
    private java.lang.Boolean usePersonID;

    public CommunicationPostalMail() {
    }

    public CommunicationPostalMail(
           java.lang.String language,
           is.idega.idegaweb.egov.fsk.banking.ws.common.Address receiverAddress,
           java.lang.Boolean usePersonID) {
        super(
            language);
        this.receiverAddress = receiverAddress;
        this.usePersonID = usePersonID;
    }


    /**
     * Gets the receiverAddress value for this CommunicationPostalMail.
     * 
     * @return receiverAddress   * The address for the receiver of the receipt
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.common.Address getReceiverAddress() {
        return receiverAddress;
    }


    /**
     * Sets the receiverAddress value for this CommunicationPostalMail.
     * 
     * @param receiverAddress   * The address for the receiver of the receipt
     */
    public void setReceiverAddress(is.idega.idegaweb.egov.fsk.banking.ws.common.Address receiverAddress) {
        this.receiverAddress = receiverAddress;
    }


    /**
     * Gets the usePersonID value for this CommunicationPostalMail.
     * 
     * @return usePersonID   * If true, then the address associated with the person ID is
     * used. The  person ID is either the account owner ID of the senders
     * account or the receivers account, depending on the type of the receipt
     */
    public java.lang.Boolean getUsePersonID() {
        return usePersonID;
    }


    /**
     * Sets the usePersonID value for this CommunicationPostalMail.
     * 
     * @param usePersonID   * If true, then the address associated with the person ID is
     * used. The  person ID is either the account owner ID of the senders
     * account or the receivers account, depending on the type of the receipt
     */
    public void setUsePersonID(java.lang.Boolean usePersonID) {
        this.usePersonID = usePersonID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CommunicationPostalMail)) return false;
        CommunicationPostalMail other = (CommunicationPostalMail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.receiverAddress==null && other.getReceiverAddress()==null) || 
             (this.receiverAddress!=null &&
              this.receiverAddress.equals(other.getReceiverAddress()))) &&
            ((this.usePersonID==null && other.getUsePersonID()==null) || 
             (this.usePersonID!=null &&
              this.usePersonID.equals(other.getUsePersonID())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getReceiverAddress() != null) {
            _hashCode += getReceiverAddress().hashCode();
        }
        if (getUsePersonID() != null) {
            _hashCode += getUsePersonID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommunicationPostalMail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", ">Communication>PostalMail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receiverAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "ReceiverAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usePersonID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "UsePersonID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
