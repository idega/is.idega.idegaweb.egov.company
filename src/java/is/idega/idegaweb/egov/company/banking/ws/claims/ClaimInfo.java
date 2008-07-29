/**
 * ClaimInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;


/**
 * Information about the current status of a claim.
 */
public class ClaimInfo  extends is.idega.idegaweb.egov.company.banking.ws.claims.Claim  implements java.io.Serializable {
    /* The status of the claim. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.ClaimStatus status;

    /* Claimant's text key, explanation of payment. Optional property
     * of claim. */
    private java.lang.String categoryCode;

    /* The total amount due for payment. */
    private java.math.BigDecimal totalAmountDue;

    /* Notice charge due for payment */
    private java.math.BigDecimal noticeChargeAmount;

    /* Default charge due for payment */
    private java.math.BigDecimal defaultChargeAmount;

    /* Other costs due to be paid. */
    private java.math.BigDecimal otherCostsAmount;

    /* Other default costs due for payment. */
    private java.math.BigDecimal otherDefaultCostsAmount;

    /* Default interest amount due for payment. */
    private java.math.BigDecimal defaultInterestAmount;

    /* The discount amount that is due to be granted. */
    private java.math.BigDecimal discountAmount;

    public ClaimInfo() {
    }

    public ClaimInfo(
           is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key,
           java.lang.String payorID,
           java.util.Date cancellationDate,
           java.lang.String identifier,
           java.math.BigDecimal amount,
           java.lang.String reference,
           java.util.Date finalDueDate,
           java.lang.String billNumber,
           java.lang.String customerNumber,
           is.idega.idegaweb.egov.company.banking.ws.claims.NoticeAndPaymentFee noticeAndPaymentFee,
           is.idega.idegaweb.egov.company.banking.ws.claims.DateRestrictedCharge defaultCharge,
           java.math.BigDecimal otherCosts,
           java.math.BigDecimal otherDefaultCosts,
           is.idega.idegaweb.egov.company.banking.ws.claims.DefaultInterest defaultInterest,
           is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyInformation currencyInformation,
           boolean permitOutOfSequencePayment,
           is.idega.idegaweb.egov.company.banking.ws.claims.DiscountCharge discount,
           boolean isPartialPaymentAllowed,
           is.idega.idegaweb.egov.company.banking.ws.claims.BillPresentmentSystem billPresentmentSystem,
           is.idega.idegaweb.egov.company.banking.ws.claims.Printing printing,
           is.idega.idegaweb.egov.company.banking.ws.claims.ClaimStatus status,
           java.lang.String categoryCode,
           java.math.BigDecimal totalAmountDue,
           java.math.BigDecimal noticeChargeAmount,
           java.math.BigDecimal defaultChargeAmount,
           java.math.BigDecimal otherCostsAmount,
           java.math.BigDecimal otherDefaultCostsAmount,
           java.math.BigDecimal defaultInterestAmount,
           java.math.BigDecimal discountAmount) {
        super(
            key,
            payorID,
            cancellationDate,
            identifier,
            amount,
            reference,
            finalDueDate,
            billNumber,
            customerNumber,
            noticeAndPaymentFee,
            defaultCharge,
            otherCosts,
            otherDefaultCosts,
            defaultInterest,
            currencyInformation,
            permitOutOfSequencePayment,
            discount,
            isPartialPaymentAllowed,
            billPresentmentSystem,
            printing);
        this.status = status;
        this.categoryCode = categoryCode;
        this.totalAmountDue = totalAmountDue;
        this.noticeChargeAmount = noticeChargeAmount;
        this.defaultChargeAmount = defaultChargeAmount;
        this.otherCostsAmount = otherCostsAmount;
        this.otherDefaultCostsAmount = otherDefaultCostsAmount;
        this.defaultInterestAmount = defaultInterestAmount;
        this.discountAmount = discountAmount;
    }


    /**
     * Gets the status value for this ClaimInfo.
     * 
     * @return status   * The status of the claim.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ClaimInfo.
     * 
     * @param status   * The status of the claim.
     */
    public void setStatus(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimStatus status) {
        this.status = status;
    }


    /**
     * Gets the categoryCode value for this ClaimInfo.
     * 
     * @return categoryCode   * Claimant's text key, explanation of payment. Optional property
     * of claim.
     */
    public java.lang.String getCategoryCode() {
        return categoryCode;
    }


    /**
     * Sets the categoryCode value for this ClaimInfo.
     * 
     * @param categoryCode   * Claimant's text key, explanation of payment. Optional property
     * of claim.
     */
    public void setCategoryCode(java.lang.String categoryCode) {
        this.categoryCode = categoryCode;
    }


    /**
     * Gets the totalAmountDue value for this ClaimInfo.
     * 
     * @return totalAmountDue   * The total amount due for payment.
     */
    public java.math.BigDecimal getTotalAmountDue() {
        return totalAmountDue;
    }


    /**
     * Sets the totalAmountDue value for this ClaimInfo.
     * 
     * @param totalAmountDue   * The total amount due for payment.
     */
    public void setTotalAmountDue(java.math.BigDecimal totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }


    /**
     * Gets the noticeChargeAmount value for this ClaimInfo.
     * 
     * @return noticeChargeAmount   * Notice charge due for payment
     */
    public java.math.BigDecimal getNoticeChargeAmount() {
        return noticeChargeAmount;
    }


    /**
     * Sets the noticeChargeAmount value for this ClaimInfo.
     * 
     * @param noticeChargeAmount   * Notice charge due for payment
     */
    public void setNoticeChargeAmount(java.math.BigDecimal noticeChargeAmount) {
        this.noticeChargeAmount = noticeChargeAmount;
    }


    /**
     * Gets the defaultChargeAmount value for this ClaimInfo.
     * 
     * @return defaultChargeAmount   * Default charge due for payment
     */
    public java.math.BigDecimal getDefaultChargeAmount() {
        return defaultChargeAmount;
    }


    /**
     * Sets the defaultChargeAmount value for this ClaimInfo.
     * 
     * @param defaultChargeAmount   * Default charge due for payment
     */
    public void setDefaultChargeAmount(java.math.BigDecimal defaultChargeAmount) {
        this.defaultChargeAmount = defaultChargeAmount;
    }


    /**
     * Gets the otherCostsAmount value for this ClaimInfo.
     * 
     * @return otherCostsAmount   * Other costs due to be paid.
     */
    public java.math.BigDecimal getOtherCostsAmount() {
        return otherCostsAmount;
    }


    /**
     * Sets the otherCostsAmount value for this ClaimInfo.
     * 
     * @param otherCostsAmount   * Other costs due to be paid.
     */
    public void setOtherCostsAmount(java.math.BigDecimal otherCostsAmount) {
        this.otherCostsAmount = otherCostsAmount;
    }


    /**
     * Gets the otherDefaultCostsAmount value for this ClaimInfo.
     * 
     * @return otherDefaultCostsAmount   * Other default costs due for payment.
     */
    public java.math.BigDecimal getOtherDefaultCostsAmount() {
        return otherDefaultCostsAmount;
    }


    /**
     * Sets the otherDefaultCostsAmount value for this ClaimInfo.
     * 
     * @param otherDefaultCostsAmount   * Other default costs due for payment.
     */
    public void setOtherDefaultCostsAmount(java.math.BigDecimal otherDefaultCostsAmount) {
        this.otherDefaultCostsAmount = otherDefaultCostsAmount;
    }


    /**
     * Gets the defaultInterestAmount value for this ClaimInfo.
     * 
     * @return defaultInterestAmount   * Default interest amount due for payment.
     */
    public java.math.BigDecimal getDefaultInterestAmount() {
        return defaultInterestAmount;
    }


    /**
     * Sets the defaultInterestAmount value for this ClaimInfo.
     * 
     * @param defaultInterestAmount   * Default interest amount due for payment.
     */
    public void setDefaultInterestAmount(java.math.BigDecimal defaultInterestAmount) {
        this.defaultInterestAmount = defaultInterestAmount;
    }


    /**
     * Gets the discountAmount value for this ClaimInfo.
     * 
     * @return discountAmount   * The discount amount that is due to be granted.
     */
    public java.math.BigDecimal getDiscountAmount() {
        return discountAmount;
    }


    /**
     * Sets the discountAmount value for this ClaimInfo.
     * 
     * @param discountAmount   * The discount amount that is due to be granted.
     */
    public void setDiscountAmount(java.math.BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClaimInfo)) return false;
        ClaimInfo other = (ClaimInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.categoryCode==null && other.getCategoryCode()==null) || 
             (this.categoryCode!=null &&
              this.categoryCode.equals(other.getCategoryCode()))) &&
            ((this.totalAmountDue==null && other.getTotalAmountDue()==null) || 
             (this.totalAmountDue!=null &&
              this.totalAmountDue.equals(other.getTotalAmountDue()))) &&
            ((this.noticeChargeAmount==null && other.getNoticeChargeAmount()==null) || 
             (this.noticeChargeAmount!=null &&
              this.noticeChargeAmount.equals(other.getNoticeChargeAmount()))) &&
            ((this.defaultChargeAmount==null && other.getDefaultChargeAmount()==null) || 
             (this.defaultChargeAmount!=null &&
              this.defaultChargeAmount.equals(other.getDefaultChargeAmount()))) &&
            ((this.otherCostsAmount==null && other.getOtherCostsAmount()==null) || 
             (this.otherCostsAmount!=null &&
              this.otherCostsAmount.equals(other.getOtherCostsAmount()))) &&
            ((this.otherDefaultCostsAmount==null && other.getOtherDefaultCostsAmount()==null) || 
             (this.otherDefaultCostsAmount!=null &&
              this.otherDefaultCostsAmount.equals(other.getOtherDefaultCostsAmount()))) &&
            ((this.defaultInterestAmount==null && other.getDefaultInterestAmount()==null) || 
             (this.defaultInterestAmount!=null &&
              this.defaultInterestAmount.equals(other.getDefaultInterestAmount()))) &&
            ((this.discountAmount==null && other.getDiscountAmount()==null) || 
             (this.discountAmount!=null &&
              this.discountAmount.equals(other.getDiscountAmount())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getCategoryCode() != null) {
            _hashCode += getCategoryCode().hashCode();
        }
        if (getTotalAmountDue() != null) {
            _hashCode += getTotalAmountDue().hashCode();
        }
        if (getNoticeChargeAmount() != null) {
            _hashCode += getNoticeChargeAmount().hashCode();
        }
        if (getDefaultChargeAmount() != null) {
            _hashCode += getDefaultChargeAmount().hashCode();
        }
        if (getOtherCostsAmount() != null) {
            _hashCode += getOtherCostsAmount().hashCode();
        }
        if (getOtherDefaultCostsAmount() != null) {
            _hashCode += getOtherDefaultCostsAmount().hashCode();
        }
        if (getDefaultInterestAmount() != null) {
            _hashCode += getDefaultInterestAmount().hashCode();
        }
        if (getDiscountAmount() != null) {
            _hashCode += getDiscountAmount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClaimInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimStatus"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CategoryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalAmountDue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "TotalAmountDue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noticeChargeAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "NoticeChargeAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultChargeAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultChargeAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherCostsAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "OtherCostsAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherDefaultCostsAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "OtherDefaultCostsAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultInterestAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultInterestAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discountAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DiscountAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
