/**
 * DefaultInterest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;


/**
 * Default interest
 */
public class DefaultInterest  implements java.io.Serializable {
    /* Determines if the claim should incur default interests after
     * the reference date and if so, how it should be calculated. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.DefaultInterestRule rule;

    /* If used, default interest on a claim is calculated based on
     * this percentage and not on the default interest period, as in the
     * case of Central Bank default interest. Not specifying the percentage
     * ensures that the Central Bank default interest is used, and interest
     * is compounded every twelve months, i.e. the default interest is added
     * to the principal. */
    private java.math.BigDecimal percentage4;

    /* Sending in a special code is the only way to set certain interest
     * rules that map to RB Penalty interest rules. Further documentation
     * can be found in RB documentation. */
    private java.lang.String specialCode;

    public DefaultInterest() {
    }

    public DefaultInterest(
           is.idega.idegaweb.egov.company.banking.ws.claims.DefaultInterestRule rule,
           java.math.BigDecimal percentage4,
           java.lang.String specialCode) {
           this.rule = rule;
           this.percentage4 = percentage4;
           this.specialCode = specialCode;
    }


    /**
     * Gets the rule value for this DefaultInterest.
     * 
     * @return rule   * Determines if the claim should incur default interests after
     * the reference date and if so, how it should be calculated.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.DefaultInterestRule getRule() {
        return rule;
    }


    /**
     * Sets the rule value for this DefaultInterest.
     * 
     * @param rule   * Determines if the claim should incur default interests after
     * the reference date and if so, how it should be calculated.
     */
    public void setRule(is.idega.idegaweb.egov.company.banking.ws.claims.DefaultInterestRule rule) {
        this.rule = rule;
    }


    /**
     * Gets the percentage4 value for this DefaultInterest.
     * 
     * @return percentage4   * If used, default interest on a claim is calculated based on
     * this percentage and not on the default interest period, as in the
     * case of Central Bank default interest. Not specifying the percentage
     * ensures that the Central Bank default interest is used, and interest
     * is compounded every twelve months, i.e. the default interest is added
     * to the principal.
     */
    public java.math.BigDecimal getPercentage4() {
        return percentage4;
    }


    /**
     * Sets the percentage4 value for this DefaultInterest.
     * 
     * @param percentage4   * If used, default interest on a claim is calculated based on
     * this percentage and not on the default interest period, as in the
     * case of Central Bank default interest. Not specifying the percentage
     * ensures that the Central Bank default interest is used, and interest
     * is compounded every twelve months, i.e. the default interest is added
     * to the principal.
     */
    public void setPercentage4(java.math.BigDecimal percentage4) {
        this.percentage4 = percentage4;
    }


    /**
     * Gets the specialCode value for this DefaultInterest.
     * 
     * @return specialCode   * Sending in a special code is the only way to set certain interest
     * rules that map to RB Penalty interest rules. Further documentation
     * can be found in RB documentation.
     */
    public java.lang.String getSpecialCode() {
        return specialCode;
    }


    /**
     * Sets the specialCode value for this DefaultInterest.
     * 
     * @param specialCode   * Sending in a special code is the only way to set certain interest
     * rules that map to RB Penalty interest rules. Further documentation
     * can be found in RB documentation.
     */
    public void setSpecialCode(java.lang.String specialCode) {
        this.specialCode = specialCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DefaultInterest)) return false;
        DefaultInterest other = (DefaultInterest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rule==null && other.getRule()==null) || 
             (this.rule!=null &&
              this.rule.equals(other.getRule()))) &&
            ((this.percentage4==null && other.getPercentage4()==null) || 
             (this.percentage4!=null &&
              this.percentage4.equals(other.getPercentage4()))) &&
            ((this.specialCode==null && other.getSpecialCode()==null) || 
             (this.specialCode!=null &&
              this.specialCode.equals(other.getSpecialCode())));
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
        if (getRule() != null) {
            _hashCode += getRule().hashCode();
        }
        if (getPercentage4() != null) {
            _hashCode += getPercentage4().hashCode();
        }
        if (getSpecialCode() != null) {
            _hashCode += getSpecialCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DefaultInterest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultInterest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rule");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Rule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultInterestRule"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentage4");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Percentage4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "SpecialCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
