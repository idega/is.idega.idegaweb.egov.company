/**
 * Payment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.payments;


/**
 * Represents a single payment.
 */
public class Payment  implements java.io.Serializable {
    /* Information about the account from which funds will be drawn. */
    private is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentOut out;

    /* Information about the account to which funds will be transferred. */
    private is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentIn in;

    /* Date when the payment will be executed. */
    private java.util.Date dateOfForwardPayment;

    public Payment() {
    }

    public Payment(
           is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentOut out,
           is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentIn in,
           java.util.Date dateOfForwardPayment) {
           this.out = out;
           this.in = in;
           this.dateOfForwardPayment = dateOfForwardPayment;
    }


    /**
     * Gets the out value for this Payment.
     * 
     * @return out   * Information about the account from which funds will be drawn.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentOut getOut() {
        return out;
    }


    /**
     * Sets the out value for this Payment.
     * 
     * @param out   * Information about the account from which funds will be drawn.
     */
    public void setOut(is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentOut out) {
        this.out = out;
    }


    /**
     * Gets the in value for this Payment.
     * 
     * @return in   * Information about the account to which funds will be transferred.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentIn getIn() {
        return in;
    }


    /**
     * Sets the in value for this Payment.
     * 
     * @param in   * Information about the account to which funds will be transferred.
     */
    public void setIn(is.idega.idegaweb.egov.fsk.banking.ws.payments.PaymentIn in) {
        this.in = in;
    }


    /**
     * Gets the dateOfForwardPayment value for this Payment.
     * 
     * @return dateOfForwardPayment   * Date when the payment will be executed.
     */
    public java.util.Date getDateOfForwardPayment() {
        return dateOfForwardPayment;
    }


    /**
     * Sets the dateOfForwardPayment value for this Payment.
     * 
     * @param dateOfForwardPayment   * Date when the payment will be executed.
     */
    public void setDateOfForwardPayment(java.util.Date dateOfForwardPayment) {
        this.dateOfForwardPayment = dateOfForwardPayment;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Payment)) return false;
        Payment other = (Payment) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.out==null && other.getOut()==null) || 
             (this.out!=null &&
              this.out.equals(other.getOut()))) &&
            ((this.in==null && other.getIn()==null) || 
             (this.in!=null &&
              this.in.equals(other.getIn()))) &&
            ((this.dateOfForwardPayment==null && other.getDateOfForwardPayment()==null) || 
             (this.dateOfForwardPayment!=null &&
              this.dateOfForwardPayment.equals(other.getDateOfForwardPayment())));
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
        if (getOut() != null) {
            _hashCode += getOut().hashCode();
        }
        if (getIn() != null) {
            _hashCode += getIn().hashCode();
        }
        if (getDateOfForwardPayment() != null) {
            _hashCode += getDateOfForwardPayment().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Payment.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Payment"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("out");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Out"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentOut"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "In"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentIn"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateOfForwardPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "DateOfForwardPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
