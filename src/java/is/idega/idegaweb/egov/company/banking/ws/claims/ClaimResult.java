/**
 * ClaimResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;


/**
 * The result of an operation on a claim. If is was created it contains
 * information indicating whether it should be printed and if it is connected
 * to an automatic debit aggreement.
 */
public class ClaimResult  implements java.io.Serializable {
    private is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key;

    /* Indicates whether the payment slip needs to be printed or not.
     * If the claimant has an agreement with the bank, it will handle the
     * printing. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.PrintInformation print;

    public ClaimResult() {
    }

    public ClaimResult(
           is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key,
           is.idega.idegaweb.egov.company.banking.ws.claims.PrintInformation print) {
           this.key = key;
           this.print = print;
    }


    /**
     * Gets the key value for this ClaimResult.
     * 
     * @return key
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey getKey() {
        return key;
    }


    /**
     * Sets the key value for this ClaimResult.
     * 
     * @param key
     */
    public void setKey(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key) {
        this.key = key;
    }


    /**
     * Gets the print value for this ClaimResult.
     * 
     * @return print   * Indicates whether the payment slip needs to be printed or not.
     * If the claimant has an agreement with the bank, it will handle the
     * printing.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.PrintInformation getPrint() {
        return print;
    }


    /**
     * Sets the print value for this ClaimResult.
     * 
     * @param print   * Indicates whether the payment slip needs to be printed or not.
     * If the claimant has an agreement with the bank, it will handle the
     * printing.
     */
    public void setPrint(is.idega.idegaweb.egov.company.banking.ws.claims.PrintInformation print) {
        this.print = print;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClaimResult)) return false;
        ClaimResult other = (ClaimResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.key==null && other.getKey()==null) || 
             (this.key!=null &&
              this.key.equals(other.getKey()))) &&
            ((this.print==null && other.getPrint()==null) || 
             (this.print!=null &&
              this.print.equals(other.getPrint())));
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
        if (getKey() != null) {
            _hashCode += getKey().hashCode();
        }
        if (getPrint() != null) {
            _hashCode += getPrint().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClaimResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("key");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimKey"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("print");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Print"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "PrintInformation"));
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
