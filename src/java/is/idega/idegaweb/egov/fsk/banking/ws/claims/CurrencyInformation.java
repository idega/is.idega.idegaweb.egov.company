/**
 * CurrencyInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.claims;


/**
 * Combines fields that define currency information for claim.
 */
public class CurrencyInformation  implements java.io.Serializable {
    /* The reference used to determine which currency type to use. */
    private is.idega.idegaweb.egov.fsk.banking.ws.claims.CurrencyInformationReferenceRate referenceRate;

    /* 3 letter currency code. */
    private java.lang.String currency;

    /* Currency code, Indicates the spot rate to be used in the calculation
     * of an IK forex claim. */
    private is.idega.idegaweb.egov.fsk.banking.ws.claims.CurrencyInformationPaymentRate paymentRate;

    public CurrencyInformation() {
    }

    public CurrencyInformation(
           is.idega.idegaweb.egov.fsk.banking.ws.claims.CurrencyInformationReferenceRate referenceRate,
           java.lang.String currency,
           is.idega.idegaweb.egov.fsk.banking.ws.claims.CurrencyInformationPaymentRate paymentRate) {
           this.referenceRate = referenceRate;
           this.currency = currency;
           this.paymentRate = paymentRate;
    }


    /**
     * Gets the referenceRate value for this CurrencyInformation.
     * 
     * @return referenceRate   * The reference used to determine which currency type to use.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.CurrencyInformationReferenceRate getReferenceRate() {
        return referenceRate;
    }


    /**
     * Sets the referenceRate value for this CurrencyInformation.
     * 
     * @param referenceRate   * The reference used to determine which currency type to use.
     */
    public void setReferenceRate(is.idega.idegaweb.egov.fsk.banking.ws.claims.CurrencyInformationReferenceRate referenceRate) {
        this.referenceRate = referenceRate;
    }


    /**
     * Gets the currency value for this CurrencyInformation.
     * 
     * @return currency   * 3 letter currency code.
     */
    public java.lang.String getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this CurrencyInformation.
     * 
     * @param currency   * 3 letter currency code.
     */
    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }


    /**
     * Gets the paymentRate value for this CurrencyInformation.
     * 
     * @return paymentRate   * Currency code, Indicates the spot rate to be used in the calculation
     * of an IK forex claim.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.CurrencyInformationPaymentRate getPaymentRate() {
        return paymentRate;
    }


    /**
     * Sets the paymentRate value for this CurrencyInformation.
     * 
     * @param paymentRate   * Currency code, Indicates the spot rate to be used in the calculation
     * of an IK forex claim.
     */
    public void setPaymentRate(is.idega.idegaweb.egov.fsk.banking.ws.claims.CurrencyInformationPaymentRate paymentRate) {
        this.paymentRate = paymentRate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CurrencyInformation)) return false;
        CurrencyInformation other = (CurrencyInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.referenceRate==null && other.getReferenceRate()==null) || 
             (this.referenceRate!=null &&
              this.referenceRate.equals(other.getReferenceRate()))) &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            ((this.paymentRate==null && other.getPaymentRate()==null) || 
             (this.paymentRate!=null &&
              this.paymentRate.equals(other.getPaymentRate())));
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
        if (getReferenceRate() != null) {
            _hashCode += getReferenceRate().hashCode();
        }
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        if (getPaymentRate() != null) {
            _hashCode += getPaymentRate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CurrencyInformation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CurrencyInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referenceRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ReferenceRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", ">CurrencyInformation>ReferenceRate"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "PaymentRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", ">CurrencyInformation>PaymentRate"));
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
