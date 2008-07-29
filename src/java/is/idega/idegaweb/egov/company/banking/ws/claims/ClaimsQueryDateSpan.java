/**
 * ClaimsQueryDateSpan.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;


/**
 * Datespan of claims query.
 */
public class ClaimsQueryDateSpan  implements java.io.Serializable {
    /* Date from inclusive. */
    private java.util.Date dateFrom;

    /* Date to inclusive. */
    private java.util.Date dateTo;

    private is.idega.idegaweb.egov.company.banking.ws.claims.DateSpanReferenceDate dateSpanReferenceDate;  // attribute

    public ClaimsQueryDateSpan() {
    }

    public ClaimsQueryDateSpan(
           java.util.Date dateFrom,
           java.util.Date dateTo,
           is.idega.idegaweb.egov.company.banking.ws.claims.DateSpanReferenceDate dateSpanReferenceDate) {
           this.dateFrom = dateFrom;
           this.dateTo = dateTo;
           this.dateSpanReferenceDate = dateSpanReferenceDate;
    }


    /**
     * Gets the dateFrom value for this ClaimsQueryDateSpan.
     * 
     * @return dateFrom   * Date from inclusive.
     */
    public java.util.Date getDateFrom() {
        return dateFrom;
    }


    /**
     * Sets the dateFrom value for this ClaimsQueryDateSpan.
     * 
     * @param dateFrom   * Date from inclusive.
     */
    public void setDateFrom(java.util.Date dateFrom) {
        this.dateFrom = dateFrom;
    }


    /**
     * Gets the dateTo value for this ClaimsQueryDateSpan.
     * 
     * @return dateTo   * Date to inclusive.
     */
    public java.util.Date getDateTo() {
        return dateTo;
    }


    /**
     * Sets the dateTo value for this ClaimsQueryDateSpan.
     * 
     * @param dateTo   * Date to inclusive.
     */
    public void setDateTo(java.util.Date dateTo) {
        this.dateTo = dateTo;
    }


    /**
     * Gets the dateSpanReferenceDate value for this ClaimsQueryDateSpan.
     * 
     * @return dateSpanReferenceDate
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.DateSpanReferenceDate getDateSpanReferenceDate() {
        return dateSpanReferenceDate;
    }


    /**
     * Sets the dateSpanReferenceDate value for this ClaimsQueryDateSpan.
     * 
     * @param dateSpanReferenceDate
     */
    public void setDateSpanReferenceDate(is.idega.idegaweb.egov.company.banking.ws.claims.DateSpanReferenceDate dateSpanReferenceDate) {
        this.dateSpanReferenceDate = dateSpanReferenceDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClaimsQueryDateSpan)) return false;
        ClaimsQueryDateSpan other = (ClaimsQueryDateSpan) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dateFrom==null && other.getDateFrom()==null) || 
             (this.dateFrom!=null &&
              this.dateFrom.equals(other.getDateFrom()))) &&
            ((this.dateTo==null && other.getDateTo()==null) || 
             (this.dateTo!=null &&
              this.dateTo.equals(other.getDateTo()))) &&
            ((this.dateSpanReferenceDate==null && other.getDateSpanReferenceDate()==null) || 
             (this.dateSpanReferenceDate!=null &&
              this.dateSpanReferenceDate.equals(other.getDateSpanReferenceDate())));
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
        if (getDateFrom() != null) {
            _hashCode += getDateFrom().hashCode();
        }
        if (getDateTo() != null) {
            _hashCode += getDateTo().hashCode();
        }
        if (getDateSpanReferenceDate() != null) {
            _hashCode += getDateSpanReferenceDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClaimsQueryDateSpan.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimsQueryDateSpan"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("dateSpanReferenceDate");
        attrField.setXmlName(new javax.xml.namespace.QName("", "DateSpanReferenceDate"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DateSpanReferenceDate"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DateFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DateTo"));
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
