/**
 * CurrencyRate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.common;


/**
 * A rate for currency, at the time the timestamp states.
 */
public class CurrencyRate  implements java.io.Serializable {
    /* Currency. */
    private java.lang.String currencyCode;

    /* The selling rate for the currency. */
    private java.math.BigDecimal sellingRate;

    /* The buying rate for the currency. */
    private java.math.BigDecimal buyingRate;

    /* Customs rate. Only returned when specifically asked for */
    private java.math.BigDecimal customsRate;

    /* Rate time, i.e. the time it applies to. */
    private java.util.Calendar timeStamp;

    public CurrencyRate() {
    }

    public CurrencyRate(
           java.lang.String currencyCode,
           java.math.BigDecimal sellingRate,
           java.math.BigDecimal buyingRate,
           java.math.BigDecimal customsRate,
           java.util.Calendar timeStamp) {
           this.currencyCode = currencyCode;
           this.sellingRate = sellingRate;
           this.buyingRate = buyingRate;
           this.customsRate = customsRate;
           this.timeStamp = timeStamp;
    }


    /**
     * Gets the currencyCode value for this CurrencyRate.
     * 
     * @return currencyCode   * Currency.
     */
    public java.lang.String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Sets the currencyCode value for this CurrencyRate.
     * 
     * @param currencyCode   * Currency.
     */
    public void setCurrencyCode(java.lang.String currencyCode) {
        this.currencyCode = currencyCode;
    }


    /**
     * Gets the sellingRate value for this CurrencyRate.
     * 
     * @return sellingRate   * The selling rate for the currency.
     */
    public java.math.BigDecimal getSellingRate() {
        return sellingRate;
    }


    /**
     * Sets the sellingRate value for this CurrencyRate.
     * 
     * @param sellingRate   * The selling rate for the currency.
     */
    public void setSellingRate(java.math.BigDecimal sellingRate) {
        this.sellingRate = sellingRate;
    }


    /**
     * Gets the buyingRate value for this CurrencyRate.
     * 
     * @return buyingRate   * The buying rate for the currency.
     */
    public java.math.BigDecimal getBuyingRate() {
        return buyingRate;
    }


    /**
     * Sets the buyingRate value for this CurrencyRate.
     * 
     * @param buyingRate   * The buying rate for the currency.
     */
    public void setBuyingRate(java.math.BigDecimal buyingRate) {
        this.buyingRate = buyingRate;
    }


    /**
     * Gets the customsRate value for this CurrencyRate.
     * 
     * @return customsRate   * Customs rate. Only returned when specifically asked for
     */
    public java.math.BigDecimal getCustomsRate() {
        return customsRate;
    }


    /**
     * Sets the customsRate value for this CurrencyRate.
     * 
     * @param customsRate   * Customs rate. Only returned when specifically asked for
     */
    public void setCustomsRate(java.math.BigDecimal customsRate) {
        this.customsRate = customsRate;
    }


    /**
     * Gets the timeStamp value for this CurrencyRate.
     * 
     * @return timeStamp   * Rate time, i.e. the time it applies to.
     */
    public java.util.Calendar getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this CurrencyRate.
     * 
     * @param timeStamp   * Rate time, i.e. the time it applies to.
     */
    public void setTimeStamp(java.util.Calendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CurrencyRate)) return false;
        CurrencyRate other = (CurrencyRate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.currencyCode==null && other.getCurrencyCode()==null) || 
             (this.currencyCode!=null &&
              this.currencyCode.equals(other.getCurrencyCode()))) &&
            ((this.sellingRate==null && other.getSellingRate()==null) || 
             (this.sellingRate!=null &&
              this.sellingRate.equals(other.getSellingRate()))) &&
            ((this.buyingRate==null && other.getBuyingRate()==null) || 
             (this.buyingRate!=null &&
              this.buyingRate.equals(other.getBuyingRate()))) &&
            ((this.customsRate==null && other.getCustomsRate()==null) || 
             (this.customsRate!=null &&
              this.customsRate.equals(other.getCustomsRate()))) &&
            ((this.timeStamp==null && other.getTimeStamp()==null) || 
             (this.timeStamp!=null &&
              this.timeStamp.equals(other.getTimeStamp())));
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
        if (getCurrencyCode() != null) {
            _hashCode += getCurrencyCode().hashCode();
        }
        if (getSellingRate() != null) {
            _hashCode += getSellingRate().hashCode();
        }
        if (getBuyingRate() != null) {
            _hashCode += getBuyingRate().hashCode();
        }
        if (getCustomsRate() != null) {
            _hashCode += getCustomsRate().hashCode();
        }
        if (getTimeStamp() != null) {
            _hashCode += getTimeStamp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CurrencyRate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "CurrencyRate"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencyCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "CurrencyCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellingRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "SellingRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buyingRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "BuyingRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customsRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "CustomsRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "TimeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
