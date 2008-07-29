/**
 * QueryPaymentsResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.claims;


/**
 * Result of payment query.
 */
public class QueryPaymentsResult  implements java.io.Serializable {
    /* Total count of entries defined by the query. */
    private org.apache.axis.types.UnsignedInt totalCount;

    private is.idega.idegaweb.egov.fsk.banking.ws.claims.Payment[] payments;

    public QueryPaymentsResult() {
    }

    public QueryPaymentsResult(
           org.apache.axis.types.UnsignedInt totalCount,
           is.idega.idegaweb.egov.fsk.banking.ws.claims.Payment[] payments) {
           this.totalCount = totalCount;
           this.payments = payments;
    }


    /**
     * Gets the totalCount value for this QueryPaymentsResult.
     * 
     * @return totalCount   * Total count of entries defined by the query.
     */
    public org.apache.axis.types.UnsignedInt getTotalCount() {
        return totalCount;
    }


    /**
     * Sets the totalCount value for this QueryPaymentsResult.
     * 
     * @param totalCount   * Total count of entries defined by the query.
     */
    public void setTotalCount(org.apache.axis.types.UnsignedInt totalCount) {
        this.totalCount = totalCount;
    }


    /**
     * Gets the payments value for this QueryPaymentsResult.
     * 
     * @return payments
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.Payment[] getPayments() {
        return payments;
    }


    /**
     * Sets the payments value for this QueryPaymentsResult.
     * 
     * @param payments
     */
    public void setPayments(is.idega.idegaweb.egov.fsk.banking.ws.claims.Payment[] payments) {
        this.payments = payments;
    }

    public is.idega.idegaweb.egov.fsk.banking.ws.claims.Payment getPayments(int i) {
        return this.payments[i];
    }

    public void setPayments(int i, is.idega.idegaweb.egov.fsk.banking.ws.claims.Payment _value) {
        this.payments[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryPaymentsResult)) return false;
        QueryPaymentsResult other = (QueryPaymentsResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.totalCount==null && other.getTotalCount()==null) || 
             (this.totalCount!=null &&
              this.totalCount.equals(other.getTotalCount()))) &&
            ((this.payments==null && other.getPayments()==null) || 
             (this.payments!=null &&
              java.util.Arrays.equals(this.payments, other.getPayments())));
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
        if (getTotalCount() != null) {
            _hashCode += getTotalCount().hashCode();
        }
        if (getPayments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPayments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPayments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryPaymentsResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "QueryPaymentsResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "TotalCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Payments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Payment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
