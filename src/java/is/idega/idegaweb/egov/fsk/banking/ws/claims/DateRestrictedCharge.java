/**
 * DateRestrictedCharge.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.claims;


/**
 * Used for defaultcharge and as base for discounts
 */
public class DateRestrictedCharge  implements java.io.Serializable {
    private is.idega.idegaweb.egov.fsk.banking.ws.claims.DiscountOrDefaultChargeBase first;

    private is.idega.idegaweb.egov.fsk.banking.ws.claims.DiscountOrDefaultChargeBase second;

    private is.idega.idegaweb.egov.fsk.banking.ws.claims.ReferenceDate referenceDate;  // attribute

    public DateRestrictedCharge() {
    }

    public DateRestrictedCharge(
           is.idega.idegaweb.egov.fsk.banking.ws.claims.DiscountOrDefaultChargeBase first,
           is.idega.idegaweb.egov.fsk.banking.ws.claims.DiscountOrDefaultChargeBase second,
           is.idega.idegaweb.egov.fsk.banking.ws.claims.ReferenceDate referenceDate) {
           this.first = first;
           this.second = second;
           this.referenceDate = referenceDate;
    }


    /**
     * Gets the first value for this DateRestrictedCharge.
     * 
     * @return first
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.DiscountOrDefaultChargeBase getFirst() {
        return first;
    }


    /**
     * Sets the first value for this DateRestrictedCharge.
     * 
     * @param first
     */
    public void setFirst(is.idega.idegaweb.egov.fsk.banking.ws.claims.DiscountOrDefaultChargeBase first) {
        this.first = first;
    }


    /**
     * Gets the second value for this DateRestrictedCharge.
     * 
     * @return second
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.DiscountOrDefaultChargeBase getSecond() {
        return second;
    }


    /**
     * Sets the second value for this DateRestrictedCharge.
     * 
     * @param second
     */
    public void setSecond(is.idega.idegaweb.egov.fsk.banking.ws.claims.DiscountOrDefaultChargeBase second) {
        this.second = second;
    }


    /**
     * Gets the referenceDate value for this DateRestrictedCharge.
     * 
     * @return referenceDate
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ReferenceDate getReferenceDate() {
        return referenceDate;
    }


    /**
     * Sets the referenceDate value for this DateRestrictedCharge.
     * 
     * @param referenceDate
     */
    public void setReferenceDate(is.idega.idegaweb.egov.fsk.banking.ws.claims.ReferenceDate referenceDate) {
        this.referenceDate = referenceDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DateRestrictedCharge)) return false;
        DateRestrictedCharge other = (DateRestrictedCharge) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.first==null && other.getFirst()==null) || 
             (this.first!=null &&
              this.first.equals(other.getFirst()))) &&
            ((this.second==null && other.getSecond()==null) || 
             (this.second!=null &&
              this.second.equals(other.getSecond()))) &&
            ((this.referenceDate==null && other.getReferenceDate()==null) || 
             (this.referenceDate!=null &&
              this.referenceDate.equals(other.getReferenceDate())));
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
        if (getFirst() != null) {
            _hashCode += getFirst().hashCode();
        }
        if (getSecond() != null) {
            _hashCode += getSecond().hashCode();
        }
        if (getReferenceDate() != null) {
            _hashCode += getReferenceDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DateRestrictedCharge.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DateRestrictedCharge"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("referenceDate");
        attrField.setXmlName(new javax.xml.namespace.QName("", "ReferenceDate"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ReferenceDate"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("first");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "First"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DiscountOrDefaultChargeBase"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("second");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Second"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DiscountOrDefaultChargeBase"));
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
