/**
 * Claim.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;


/**
 * Encapsulates a claim, used for creating and changing.
 */
public class Claim  implements java.io.Serializable {
    /* The combination of fields that uniquely identifies a claim. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key;

    /* Payor person id. */
    private java.lang.String payorID;

    /* Date of cancellation, when the claim is no longer valid in
     * the system. */
    private java.util.Date cancellationDate;

    /* The unique identifier of the claimant that the claim relates
     * to. */
    private java.lang.String identifier;

    /* Amount of claim. */
    private java.math.BigDecimal amount;

    /* Refence determined by claimant. Alphanumeric, maximum 16 letters. */
    private java.lang.String reference;

    /* Final due date of claim. */
    private java.util.Date finalDueDate;

    /* Billnumber, optional. Information provided by claimant. */
    private java.lang.String billNumber;

    /* Customer number, optional key used by claimant to identify
     * payor. Necessary if payor is to be able to enable automotic debiting
     * of claims. */
    private java.lang.String customerNumber;

    /* Charge for calculation and printout of payment slip and sending
     * to payer. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.NoticeAndPaymentFee noticeAndPaymentFee;

    /* Default charge that claims incur when they default, either
     * a percentage or fixed amount. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.DateRestrictedCharge defaultCharge;

    /* For special charges to be paid by the payer of the claim. */
    private java.math.BigDecimal otherCosts;

    /* For special charges, e.g. temporary collection charge, to be
     * paid by the payer of the claim. */
    private java.math.BigDecimal otherDefaultCosts;

    /* Rule for calculating default interest. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.DefaultInterest defaultInterest;

    /* For claims which are linked to a foreign currency. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyInformation currencyInformation;

    /* Payment of claim allowed if earlier due date is unpaid. */
    private boolean permitOutOfSequencePayment;

    /* Two amount or percentage fields can be attached to a claim
     * base, the first and second discount, which are used to reduce the
     * payment if made before a fixed date. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.DiscountCharge discount;

    /* Indicates whether partial payment is permitted. */
    private boolean isPartialPaymentAllowed;

    /* Indicates the bill presentment system to which the claim pertains. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.BillPresentmentSystem billPresentmentSystem;

    /* Information that is used when printing a claim, if it is to
     * be printed by the bank. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.Printing printing;

    public Claim() {
    }

    public Claim(
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
           is.idega.idegaweb.egov.company.banking.ws.claims.Printing printing) {
           this.key = key;
           this.payorID = payorID;
           this.cancellationDate = cancellationDate;
           this.identifier = identifier;
           this.amount = amount;
           this.reference = reference;
           this.finalDueDate = finalDueDate;
           this.billNumber = billNumber;
           this.customerNumber = customerNumber;
           this.noticeAndPaymentFee = noticeAndPaymentFee;
           this.defaultCharge = defaultCharge;
           this.otherCosts = otherCosts;
           this.otherDefaultCosts = otherDefaultCosts;
           this.defaultInterest = defaultInterest;
           this.currencyInformation = currencyInformation;
           this.permitOutOfSequencePayment = permitOutOfSequencePayment;
           this.discount = discount;
           this.isPartialPaymentAllowed = isPartialPaymentAllowed;
           this.billPresentmentSystem = billPresentmentSystem;
           this.printing = printing;
    }


    /**
     * Gets the key value for this Claim.
     * 
     * @return key   * The combination of fields that uniquely identifies a claim.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey getKey() {
        return key;
    }


    /**
     * Sets the key value for this Claim.
     * 
     * @param key   * The combination of fields that uniquely identifies a claim.
     */
    public void setKey(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key) {
        this.key = key;
    }


    /**
     * Gets the payorID value for this Claim.
     * 
     * @return payorID   * Payor person id.
     */
    public java.lang.String getPayorID() {
        return payorID;
    }


    /**
     * Sets the payorID value for this Claim.
     * 
     * @param payorID   * Payor person id.
     */
    public void setPayorID(java.lang.String payorID) {
        this.payorID = payorID;
    }


    /**
     * Gets the cancellationDate value for this Claim.
     * 
     * @return cancellationDate   * Date of cancellation, when the claim is no longer valid in
     * the system.
     */
    public java.util.Date getCancellationDate() {
        return cancellationDate;
    }


    /**
     * Sets the cancellationDate value for this Claim.
     * 
     * @param cancellationDate   * Date of cancellation, when the claim is no longer valid in
     * the system.
     */
    public void setCancellationDate(java.util.Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }


    /**
     * Gets the identifier value for this Claim.
     * 
     * @return identifier   * The unique identifier of the claimant that the claim relates
     * to.
     */
    public java.lang.String getIdentifier() {
        return identifier;
    }


    /**
     * Sets the identifier value for this Claim.
     * 
     * @param identifier   * The unique identifier of the claimant that the claim relates
     * to.
     */
    public void setIdentifier(java.lang.String identifier) {
        this.identifier = identifier;
    }


    /**
     * Gets the amount value for this Claim.
     * 
     * @return amount   * Amount of claim.
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this Claim.
     * 
     * @param amount   * Amount of claim.
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the reference value for this Claim.
     * 
     * @return reference   * Refence determined by claimant. Alphanumeric, maximum 16 letters.
     */
    public java.lang.String getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this Claim.
     * 
     * @param reference   * Refence determined by claimant. Alphanumeric, maximum 16 letters.
     */
    public void setReference(java.lang.String reference) {
        this.reference = reference;
    }


    /**
     * Gets the finalDueDate value for this Claim.
     * 
     * @return finalDueDate   * Final due date of claim.
     */
    public java.util.Date getFinalDueDate() {
        return finalDueDate;
    }


    /**
     * Sets the finalDueDate value for this Claim.
     * 
     * @param finalDueDate   * Final due date of claim.
     */
    public void setFinalDueDate(java.util.Date finalDueDate) {
        this.finalDueDate = finalDueDate;
    }


    /**
     * Gets the billNumber value for this Claim.
     * 
     * @return billNumber   * Billnumber, optional. Information provided by claimant.
     */
    public java.lang.String getBillNumber() {
        return billNumber;
    }


    /**
     * Sets the billNumber value for this Claim.
     * 
     * @param billNumber   * Billnumber, optional. Information provided by claimant.
     */
    public void setBillNumber(java.lang.String billNumber) {
        this.billNumber = billNumber;
    }


    /**
     * Gets the customerNumber value for this Claim.
     * 
     * @return customerNumber   * Customer number, optional key used by claimant to identify
     * payor. Necessary if payor is to be able to enable automotic debiting
     * of claims.
     */
    public java.lang.String getCustomerNumber() {
        return customerNumber;
    }


    /**
     * Sets the customerNumber value for this Claim.
     * 
     * @param customerNumber   * Customer number, optional key used by claimant to identify
     * payor. Necessary if payor is to be able to enable automotic debiting
     * of claims.
     */
    public void setCustomerNumber(java.lang.String customerNumber) {
        this.customerNumber = customerNumber;
    }


    /**
     * Gets the noticeAndPaymentFee value for this Claim.
     * 
     * @return noticeAndPaymentFee   * Charge for calculation and printout of payment slip and sending
     * to payer.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.NoticeAndPaymentFee getNoticeAndPaymentFee() {
        return noticeAndPaymentFee;
    }


    /**
     * Sets the noticeAndPaymentFee value for this Claim.
     * 
     * @param noticeAndPaymentFee   * Charge for calculation and printout of payment slip and sending
     * to payer.
     */
    public void setNoticeAndPaymentFee(is.idega.idegaweb.egov.company.banking.ws.claims.NoticeAndPaymentFee noticeAndPaymentFee) {
        this.noticeAndPaymentFee = noticeAndPaymentFee;
    }


    /**
     * Gets the defaultCharge value for this Claim.
     * 
     * @return defaultCharge   * Default charge that claims incur when they default, either
     * a percentage or fixed amount.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.DateRestrictedCharge getDefaultCharge() {
        return defaultCharge;
    }


    /**
     * Sets the defaultCharge value for this Claim.
     * 
     * @param defaultCharge   * Default charge that claims incur when they default, either
     * a percentage or fixed amount.
     */
    public void setDefaultCharge(is.idega.idegaweb.egov.company.banking.ws.claims.DateRestrictedCharge defaultCharge) {
        this.defaultCharge = defaultCharge;
    }


    /**
     * Gets the otherCosts value for this Claim.
     * 
     * @return otherCosts   * For special charges to be paid by the payer of the claim.
     */
    public java.math.BigDecimal getOtherCosts() {
        return otherCosts;
    }


    /**
     * Sets the otherCosts value for this Claim.
     * 
     * @param otherCosts   * For special charges to be paid by the payer of the claim.
     */
    public void setOtherCosts(java.math.BigDecimal otherCosts) {
        this.otherCosts = otherCosts;
    }


    /**
     * Gets the otherDefaultCosts value for this Claim.
     * 
     * @return otherDefaultCosts   * For special charges, e.g. temporary collection charge, to be
     * paid by the payer of the claim.
     */
    public java.math.BigDecimal getOtherDefaultCosts() {
        return otherDefaultCosts;
    }


    /**
     * Sets the otherDefaultCosts value for this Claim.
     * 
     * @param otherDefaultCosts   * For special charges, e.g. temporary collection charge, to be
     * paid by the payer of the claim.
     */
    public void setOtherDefaultCosts(java.math.BigDecimal otherDefaultCosts) {
        this.otherDefaultCosts = otherDefaultCosts;
    }


    /**
     * Gets the defaultInterest value for this Claim.
     * 
     * @return defaultInterest   * Rule for calculating default interest.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.DefaultInterest getDefaultInterest() {
        return defaultInterest;
    }


    /**
     * Sets the defaultInterest value for this Claim.
     * 
     * @param defaultInterest   * Rule for calculating default interest.
     */
    public void setDefaultInterest(is.idega.idegaweb.egov.company.banking.ws.claims.DefaultInterest defaultInterest) {
        this.defaultInterest = defaultInterest;
    }


    /**
     * Gets the currencyInformation value for this Claim.
     * 
     * @return currencyInformation   * For claims which are linked to a foreign currency.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyInformation getCurrencyInformation() {
        return currencyInformation;
    }


    /**
     * Sets the currencyInformation value for this Claim.
     * 
     * @param currencyInformation   * For claims which are linked to a foreign currency.
     */
    public void setCurrencyInformation(is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyInformation currencyInformation) {
        this.currencyInformation = currencyInformation;
    }


    /**
     * Gets the permitOutOfSequencePayment value for this Claim.
     * 
     * @return permitOutOfSequencePayment   * Payment of claim allowed if earlier due date is unpaid.
     */
    public boolean isPermitOutOfSequencePayment() {
        return permitOutOfSequencePayment;
    }


    /**
     * Sets the permitOutOfSequencePayment value for this Claim.
     * 
     * @param permitOutOfSequencePayment   * Payment of claim allowed if earlier due date is unpaid.
     */
    public void setPermitOutOfSequencePayment(boolean permitOutOfSequencePayment) {
        this.permitOutOfSequencePayment = permitOutOfSequencePayment;
    }


    /**
     * Gets the discount value for this Claim.
     * 
     * @return discount   * Two amount or percentage fields can be attached to a claim
     * base, the first and second discount, which are used to reduce the
     * payment if made before a fixed date.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.DiscountCharge getDiscount() {
        return discount;
    }


    /**
     * Sets the discount value for this Claim.
     * 
     * @param discount   * Two amount or percentage fields can be attached to a claim
     * base, the first and second discount, which are used to reduce the
     * payment if made before a fixed date.
     */
    public void setDiscount(is.idega.idegaweb.egov.company.banking.ws.claims.DiscountCharge discount) {
        this.discount = discount;
    }


    /**
     * Gets the isPartialPaymentAllowed value for this Claim.
     * 
     * @return isPartialPaymentAllowed   * Indicates whether partial payment is permitted.
     */
    public boolean isIsPartialPaymentAllowed() {
        return isPartialPaymentAllowed;
    }


    /**
     * Sets the isPartialPaymentAllowed value for this Claim.
     * 
     * @param isPartialPaymentAllowed   * Indicates whether partial payment is permitted.
     */
    public void setIsPartialPaymentAllowed(boolean isPartialPaymentAllowed) {
        this.isPartialPaymentAllowed = isPartialPaymentAllowed;
    }


    /**
     * Gets the billPresentmentSystem value for this Claim.
     * 
     * @return billPresentmentSystem   * Indicates the bill presentment system to which the claim pertains.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.BillPresentmentSystem getBillPresentmentSystem() {
        return billPresentmentSystem;
    }


    /**
     * Sets the billPresentmentSystem value for this Claim.
     * 
     * @param billPresentmentSystem   * Indicates the bill presentment system to which the claim pertains.
     */
    public void setBillPresentmentSystem(is.idega.idegaweb.egov.company.banking.ws.claims.BillPresentmentSystem billPresentmentSystem) {
        this.billPresentmentSystem = billPresentmentSystem;
    }


    /**
     * Gets the printing value for this Claim.
     * 
     * @return printing   * Information that is used when printing a claim, if it is to
     * be printed by the bank.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.Printing getPrinting() {
        return printing;
    }


    /**
     * Sets the printing value for this Claim.
     * 
     * @param printing   * Information that is used when printing a claim, if it is to
     * be printed by the bank.
     */
    public void setPrinting(is.idega.idegaweb.egov.company.banking.ws.claims.Printing printing) {
        this.printing = printing;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Claim)) return false;
        Claim other = (Claim) obj;
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
            ((this.payorID==null && other.getPayorID()==null) || 
             (this.payorID!=null &&
              this.payorID.equals(other.getPayorID()))) &&
            ((this.cancellationDate==null && other.getCancellationDate()==null) || 
             (this.cancellationDate!=null &&
              this.cancellationDate.equals(other.getCancellationDate()))) &&
            ((this.identifier==null && other.getIdentifier()==null) || 
             (this.identifier!=null &&
              this.identifier.equals(other.getIdentifier()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              this.reference.equals(other.getReference()))) &&
            ((this.finalDueDate==null && other.getFinalDueDate()==null) || 
             (this.finalDueDate!=null &&
              this.finalDueDate.equals(other.getFinalDueDate()))) &&
            ((this.billNumber==null && other.getBillNumber()==null) || 
             (this.billNumber!=null &&
              this.billNumber.equals(other.getBillNumber()))) &&
            ((this.customerNumber==null && other.getCustomerNumber()==null) || 
             (this.customerNumber!=null &&
              this.customerNumber.equals(other.getCustomerNumber()))) &&
            ((this.noticeAndPaymentFee==null && other.getNoticeAndPaymentFee()==null) || 
             (this.noticeAndPaymentFee!=null &&
              this.noticeAndPaymentFee.equals(other.getNoticeAndPaymentFee()))) &&
            ((this.defaultCharge==null && other.getDefaultCharge()==null) || 
             (this.defaultCharge!=null &&
              this.defaultCharge.equals(other.getDefaultCharge()))) &&
            ((this.otherCosts==null && other.getOtherCosts()==null) || 
             (this.otherCosts!=null &&
              this.otherCosts.equals(other.getOtherCosts()))) &&
            ((this.otherDefaultCosts==null && other.getOtherDefaultCosts()==null) || 
             (this.otherDefaultCosts!=null &&
              this.otherDefaultCosts.equals(other.getOtherDefaultCosts()))) &&
            ((this.defaultInterest==null && other.getDefaultInterest()==null) || 
             (this.defaultInterest!=null &&
              this.defaultInterest.equals(other.getDefaultInterest()))) &&
            ((this.currencyInformation==null && other.getCurrencyInformation()==null) || 
             (this.currencyInformation!=null &&
              this.currencyInformation.equals(other.getCurrencyInformation()))) &&
            this.permitOutOfSequencePayment == other.isPermitOutOfSequencePayment() &&
            ((this.discount==null && other.getDiscount()==null) || 
             (this.discount!=null &&
              this.discount.equals(other.getDiscount()))) &&
            this.isPartialPaymentAllowed == other.isIsPartialPaymentAllowed() &&
            ((this.billPresentmentSystem==null && other.getBillPresentmentSystem()==null) || 
             (this.billPresentmentSystem!=null &&
              this.billPresentmentSystem.equals(other.getBillPresentmentSystem()))) &&
            ((this.printing==null && other.getPrinting()==null) || 
             (this.printing!=null &&
              this.printing.equals(other.getPrinting())));
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
        if (getPayorID() != null) {
            _hashCode += getPayorID().hashCode();
        }
        if (getCancellationDate() != null) {
            _hashCode += getCancellationDate().hashCode();
        }
        if (getIdentifier() != null) {
            _hashCode += getIdentifier().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        if (getFinalDueDate() != null) {
            _hashCode += getFinalDueDate().hashCode();
        }
        if (getBillNumber() != null) {
            _hashCode += getBillNumber().hashCode();
        }
        if (getCustomerNumber() != null) {
            _hashCode += getCustomerNumber().hashCode();
        }
        if (getNoticeAndPaymentFee() != null) {
            _hashCode += getNoticeAndPaymentFee().hashCode();
        }
        if (getDefaultCharge() != null) {
            _hashCode += getDefaultCharge().hashCode();
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
        if (getCurrencyInformation() != null) {
            _hashCode += getCurrencyInformation().hashCode();
        }
        _hashCode += (isPermitOutOfSequencePayment() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getDiscount() != null) {
            _hashCode += getDiscount().hashCode();
        }
        _hashCode += (isIsPartialPaymentAllowed() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getBillPresentmentSystem() != null) {
            _hashCode += getBillPresentmentSystem().hashCode();
        }
        if (getPrinting() != null) {
            _hashCode += getPrinting().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Claim.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Claim"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("key");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimKey"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payorID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "PayorID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cancellationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CancellationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Identifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Reference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finalDueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "FinalDueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "BillNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CustomerNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noticeAndPaymentFee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "NoticeAndPaymentFee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "NoticeAndPaymentFee"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DateRestrictedCharge"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherCosts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "OtherCosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherDefaultCosts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "OtherDefaultCosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultInterest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultInterest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultInterest"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencyInformation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CurrencyInformation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CurrencyInformation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permitOutOfSequencePayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "PermitOutOfSequencePayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Discount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DiscountCharge"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isPartialPaymentAllowed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "IsPartialPaymentAllowed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billPresentmentSystem");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "BillPresentmentSystem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "BillPresentmentSystem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("printing");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Printing"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Printing"));
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
