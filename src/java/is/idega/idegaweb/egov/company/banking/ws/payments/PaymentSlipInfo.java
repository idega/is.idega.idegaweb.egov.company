/**
 * PaymentSlipInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.payments;


/**
 * Detailed information about payment slip. When payment slip has
 * been payed the detail will show how much was paid in interests and
 * charges.
 */
public class PaymentSlipInfo  extends is.idega.idegaweb.egov.company.banking.ws.payments.PaymentSlip  implements java.io.Serializable {
    /* Total amount to pay */
    private java.math.BigDecimal amountDue;

    /* Defaul cost */
    private java.math.BigDecimal defaultCosts;

    /* Other cost */
    private java.math.BigDecimal otherCosts;

    /* Other default costs */
    private java.math.BigDecimal otherDefaultCosts;

    /* Default Interest */
    private java.math.BigDecimal defaultInterest;

    /* Notification fee */
    private java.math.BigDecimal noticeAndPaymentFee;

    /* Discount */
    private java.math.BigDecimal discount;

    /* Category code */
    private java.lang.String categoryCode;

    public PaymentSlipInfo() {
    }

    public PaymentSlipInfo(
           java.lang.String account,
           java.lang.String personID,
           java.util.Date dueDate,
           boolean isDeposit,
           java.math.BigDecimal amountDue,
           java.math.BigDecimal defaultCosts,
           java.math.BigDecimal otherCosts,
           java.math.BigDecimal otherDefaultCosts,
           java.math.BigDecimal defaultInterest,
           java.math.BigDecimal noticeAndPaymentFee,
           java.math.BigDecimal discount,
           java.lang.String categoryCode) {
        super(
            account,
            personID,
            dueDate,
            isDeposit);
        this.amountDue = amountDue;
        this.defaultCosts = defaultCosts;
        this.otherCosts = otherCosts;
        this.otherDefaultCosts = otherDefaultCosts;
        this.defaultInterest = defaultInterest;
        this.noticeAndPaymentFee = noticeAndPaymentFee;
        this.discount = discount;
        this.categoryCode = categoryCode;
    }


    /**
     * Gets the amountDue value for this PaymentSlipInfo.
     * 
     * @return amountDue   * Total amount to pay
     */
    public java.math.BigDecimal getAmountDue() {
        return amountDue;
    }


    /**
     * Sets the amountDue value for this PaymentSlipInfo.
     * 
     * @param amountDue   * Total amount to pay
     */
    public void setAmountDue(java.math.BigDecimal amountDue) {
        this.amountDue = amountDue;
    }


    /**
     * Gets the defaultCosts value for this PaymentSlipInfo.
     * 
     * @return defaultCosts   * Defaul cost
     */
    public java.math.BigDecimal getDefaultCosts() {
        return defaultCosts;
    }


    /**
     * Sets the defaultCosts value for this PaymentSlipInfo.
     * 
     * @param defaultCosts   * Defaul cost
     */
    public void setDefaultCosts(java.math.BigDecimal defaultCosts) {
        this.defaultCosts = defaultCosts;
    }


    /**
     * Gets the otherCosts value for this PaymentSlipInfo.
     * 
     * @return otherCosts   * Other cost
     */
    public java.math.BigDecimal getOtherCosts() {
        return otherCosts;
    }


    /**
     * Sets the otherCosts value for this PaymentSlipInfo.
     * 
     * @param otherCosts   * Other cost
     */
    public void setOtherCosts(java.math.BigDecimal otherCosts) {
        this.otherCosts = otherCosts;
    }


    /**
     * Gets the otherDefaultCosts value for this PaymentSlipInfo.
     * 
     * @return otherDefaultCosts   * Other default costs
     */
    public java.math.BigDecimal getOtherDefaultCosts() {
        return otherDefaultCosts;
    }


    /**
     * Sets the otherDefaultCosts value for this PaymentSlipInfo.
     * 
     * @param otherDefaultCosts   * Other default costs
     */
    public void setOtherDefaultCosts(java.math.BigDecimal otherDefaultCosts) {
        this.otherDefaultCosts = otherDefaultCosts;
    }


    /**
     * Gets the defaultInterest value for this PaymentSlipInfo.
     * 
     * @return defaultInterest   * Default Interest
     */
    public java.math.BigDecimal getDefaultInterest() {
        return defaultInterest;
    }


    /**
     * Sets the defaultInterest value for this PaymentSlipInfo.
     * 
     * @param defaultInterest   * Default Interest
     */
    public void setDefaultInterest(java.math.BigDecimal defaultInterest) {
        this.defaultInterest = defaultInterest;
    }


    /**
     * Gets the noticeAndPaymentFee value for this PaymentSlipInfo.
     * 
     * @return noticeAndPaymentFee   * Notification fee
     */
    public java.math.BigDecimal getNoticeAndPaymentFee() {
        return noticeAndPaymentFee;
    }


    /**
     * Sets the noticeAndPaymentFee value for this PaymentSlipInfo.
     * 
     * @param noticeAndPaymentFee   * Notification fee
     */
    public void setNoticeAndPaymentFee(java.math.BigDecimal noticeAndPaymentFee) {
        this.noticeAndPaymentFee = noticeAndPaymentFee;
    }


    /**
     * Gets the discount value for this PaymentSlipInfo.
     * 
     * @return discount   * Discount
     */
    public java.math.BigDecimal getDiscount() {
        return discount;
    }


    /**
     * Sets the discount value for this PaymentSlipInfo.
     * 
     * @param discount   * Discount
     */
    public void setDiscount(java.math.BigDecimal discount) {
        this.discount = discount;
    }


    /**
     * Gets the categoryCode value for this PaymentSlipInfo.
     * 
     * @return categoryCode   * Category code
     */
    public java.lang.String getCategoryCode() {
        return categoryCode;
    }


    /**
     * Sets the categoryCode value for this PaymentSlipInfo.
     * 
     * @param categoryCode   * Category code
     */
    public void setCategoryCode(java.lang.String categoryCode) {
        this.categoryCode = categoryCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentSlipInfo)) return false;
        PaymentSlipInfo other = (PaymentSlipInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.amountDue==null && other.getAmountDue()==null) || 
             (this.amountDue!=null &&
              this.amountDue.equals(other.getAmountDue()))) &&
            ((this.defaultCosts==null && other.getDefaultCosts()==null) || 
             (this.defaultCosts!=null &&
              this.defaultCosts.equals(other.getDefaultCosts()))) &&
            ((this.otherCosts==null && other.getOtherCosts()==null) || 
             (this.otherCosts!=null &&
              this.otherCosts.equals(other.getOtherCosts()))) &&
            ((this.otherDefaultCosts==null && other.getOtherDefaultCosts()==null) || 
             (this.otherDefaultCosts!=null &&
              this.otherDefaultCosts.equals(other.getOtherDefaultCosts()))) &&
            ((this.defaultInterest==null && other.getDefaultInterest()==null) || 
             (this.defaultInterest!=null &&
              this.defaultInterest.equals(other.getDefaultInterest()))) &&
            ((this.noticeAndPaymentFee==null && other.getNoticeAndPaymentFee()==null) || 
             (this.noticeAndPaymentFee!=null &&
              this.noticeAndPaymentFee.equals(other.getNoticeAndPaymentFee()))) &&
            ((this.discount==null && other.getDiscount()==null) || 
             (this.discount!=null &&
              this.discount.equals(other.getDiscount()))) &&
            ((this.categoryCode==null && other.getCategoryCode()==null) || 
             (this.categoryCode!=null &&
              this.categoryCode.equals(other.getCategoryCode())));
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
        if (getAmountDue() != null) {
            _hashCode += getAmountDue().hashCode();
        }
        if (getDefaultCosts() != null) {
            _hashCode += getDefaultCosts().hashCode();
        }
        if (getOtherCosts() != null) {
            _hashCode += getOtherCosts().hashCode();
        }
        if (getOtherDefaultCosts() != null) {
            _hashCode += getOtherDefaultCosts().hashCode();
        }
        if (getDefaultInterest() != null) {
            _hashCode += getDefaultInterest().hashCode();
        }
        if (getNoticeAndPaymentFee() != null) {
            _hashCode += getNoticeAndPaymentFee().hashCode();
        }
        if (getDiscount() != null) {
            _hashCode += getDiscount().hashCode();
        }
        if (getCategoryCode() != null) {
            _hashCode += getCategoryCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentSlipInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentSlipInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountDue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "AmountDue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultCosts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "DefaultCosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherCosts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "OtherCosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherDefaultCosts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "OtherDefaultCosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultInterest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "DefaultInterest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noticeAndPaymentFee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "NoticeAndPaymentFee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Discount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "CategoryCode"));
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
