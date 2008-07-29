/**
 * DiscountCharge.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;


/**
 * Used for discounts
 */
public class DiscountCharge  extends is.idega.idegaweb.egov.company.banking.ws.claims.DateRestrictedCharge  implements java.io.Serializable {
    private boolean isPostRefDate;  // attribute

    public DiscountCharge() {
    }

    public DiscountCharge(
           is.idega.idegaweb.egov.company.banking.ws.claims.ReferenceDate referenceDate,
           is.idega.idegaweb.egov.company.banking.ws.claims.DiscountOrDefaultChargeBase first,
           is.idega.idegaweb.egov.company.banking.ws.claims.DiscountOrDefaultChargeBase second,
           boolean isPostRefDate) {
        super(
            first,
            second,
            referenceDate);
        this.isPostRefDate = isPostRefDate;
    }


    /**
     * Gets the isPostRefDate value for this DiscountCharge.
     * 
     * @return isPostRefDate
     */
    public boolean isIsPostRefDate() {
        return isPostRefDate;
    }


    /**
     * Sets the isPostRefDate value for this DiscountCharge.
     * 
     * @param isPostRefDate
     */
    public void setIsPostRefDate(boolean isPostRefDate) {
        this.isPostRefDate = isPostRefDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DiscountCharge)) return false;
        DiscountCharge other = (DiscountCharge) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.isPostRefDate == other.isIsPostRefDate();
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
        _hashCode += (isIsPostRefDate() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DiscountCharge.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DiscountCharge"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("isPostRefDate");
        attrField.setXmlName(new javax.xml.namespace.QName("", "IsPostRefDate"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
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
