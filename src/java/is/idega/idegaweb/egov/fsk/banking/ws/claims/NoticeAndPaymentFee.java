/**
 * NoticeAndPaymentFee.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.claims;


/**
 * Charge for calculation and printout of payment slip and sending
 * to payer.
 */
public class NoticeAndPaymentFee  implements java.io.Serializable {
    /* Charge used if claim is printed. */
    private java.math.BigDecimal printing;

    /* Charge used if claim is not printed. */
    private java.math.BigDecimal paperless;

    public NoticeAndPaymentFee() {
    }

    public NoticeAndPaymentFee(
           java.math.BigDecimal printing,
           java.math.BigDecimal paperless) {
           this.printing = printing;
           this.paperless = paperless;
    }


    /**
     * Gets the printing value for this NoticeAndPaymentFee.
     * 
     * @return printing   * Charge used if claim is printed.
     */
    public java.math.BigDecimal getPrinting() {
        return printing;
    }


    /**
     * Sets the printing value for this NoticeAndPaymentFee.
     * 
     * @param printing   * Charge used if claim is printed.
     */
    public void setPrinting(java.math.BigDecimal printing) {
        this.printing = printing;
    }


    /**
     * Gets the paperless value for this NoticeAndPaymentFee.
     * 
     * @return paperless   * Charge used if claim is not printed.
     */
    public java.math.BigDecimal getPaperless() {
        return paperless;
    }


    /**
     * Sets the paperless value for this NoticeAndPaymentFee.
     * 
     * @param paperless   * Charge used if claim is not printed.
     */
    public void setPaperless(java.math.BigDecimal paperless) {
        this.paperless = paperless;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NoticeAndPaymentFee)) return false;
        NoticeAndPaymentFee other = (NoticeAndPaymentFee) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.printing==null && other.getPrinting()==null) || 
             (this.printing!=null &&
              this.printing.equals(other.getPrinting()))) &&
            ((this.paperless==null && other.getPaperless()==null) || 
             (this.paperless!=null &&
              this.paperless.equals(other.getPaperless())));
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
        if (getPrinting() != null) {
            _hashCode += getPrinting().hashCode();
        }
        if (getPaperless() != null) {
            _hashCode += getPaperless().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NoticeAndPaymentFee.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "NoticeAndPaymentFee"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printing");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Printing"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paperless");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Paperless"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
