/**
 * PaymentError.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.payments;


/**
 * Includes information about a payment that has resulted in an error
 */
public class PaymentError  implements java.io.Serializable {
    /* Information about the payment */
    private is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentIn payment;

    /* Information about the error */
    private is.idega.idegaweb.egov.fsk.banking.ws.common.Error error;

    public PaymentError() {
    }

    public PaymentError(
           is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentIn payment,
           is.idega.idegaweb.egov.fsk.banking.ws.common.Error error) {
           this.payment = payment;
           this.error = error;
    }


    /**
     * Gets the payment value for this PaymentError.
     * 
     * @return payment   * Information about the payment
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentIn getPayment() {
        return payment;
    }


    /**
     * Sets the payment value for this PaymentError.
     * 
     * @param payment   * Information about the payment
     */
    public void setPayment(is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentIn payment) {
        this.payment = payment;
    }


    /**
     * Gets the error value for this PaymentError.
     * 
     * @return error   * Information about the error
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.common.Error getError() {
        return error;
    }


    /**
     * Sets the error value for this PaymentError.
     * 
     * @param error   * Information about the error
     */
    public void setError(is.idega.idegaweb.egov.fsk.banking.ws.common.Error error) {
        this.error = error;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentError)) return false;
        PaymentError other = (PaymentError) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.payment==null && other.getPayment()==null) || 
             (this.payment!=null &&
              this.payment.equals(other.getPayment()))) &&
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getPayment() != null) {
            _hashCode += getPayment().hashCode();
        }
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentError.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentError"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Payment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentIn"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "Error"));
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
