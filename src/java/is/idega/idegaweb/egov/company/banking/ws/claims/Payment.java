/**
 * Payment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;


/**
 * Describes a discrete payment.
 */
public class Payment  implements java.io.Serializable {
    /* The combination of fields that uniquely identifies a claim. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key;

    /* Payor person id. */
    private java.lang.String payorID;

    /* Amount of claim. */
    private java.math.BigDecimal amount;

    /* Final due date of claim. */
    private java.util.Date finalDueDate;

    /* The unique identifier of the claimant that the claim relates
     * to. */
    private java.lang.String identifier;

    /* Reference determined by claimant. */
    private java.lang.String reference;

    /* Claimant’s text key, explanation of payment. Optional property
     * of claim. */
    private java.lang.String categoryCode;

    /* Where the payment comes from. */
    private java.lang.String redeemingBank;

    /* The date on which a transaction in the Claims File is effected. */
    private java.util.Calendar transactionDate;

    /* The date when the transaction is booked. */
    private java.util.Date bookingDate;

    /* The validation date used to calculate default interests and
     * charges. */
    private java.util.Date valueDate;

    private is.idega.idegaweb.egov.company.banking.ws.claims.PaymentPaymentType paymentType;

    /* Amount deposited in the account used for the disposal of payments. */
    private java.math.BigDecimal amountDeposited;

    /* Total amount paid, with default interest and costs. */
    private java.math.BigDecimal totalAmount;

    /* Withheld capital gains tax. */
    private java.math.BigDecimal capitalGainsTax;

    /* Billnumber, optional. Information provided by claimant. */
    private java.lang.String billNumber;

    /* Customer number of payor, claimant's unique code for the customer. */
    private java.lang.String customerNumber;

    /* Notice charge paid. */
    private java.math.BigDecimal noticeChargeAmount;

    /* Default charge paid. */
    private java.math.BigDecimal defaultChargeAmount;

    /* Other costs paid. */
    private java.math.BigDecimal otherCostsAmount;

    /* Other default costs paid. */
    private java.math.BigDecimal otherDefaultCostsAmount;

    /* Paid default interest. */
    private java.math.BigDecimal defaultInterestAmount;

    /* Discount granted. */
    private java.math.BigDecimal discountAmount;

    /* Currency information, only available for forex claims. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyExchangeRate currency;

    /* Batch number of payment. */
    private java.lang.String batchNumber;

    public Payment() {
    }

    public Payment(
           is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key,
           java.lang.String payorID,
           java.math.BigDecimal amount,
           java.util.Date finalDueDate,
           java.lang.String identifier,
           java.lang.String reference,
           java.lang.String categoryCode,
           java.lang.String redeemingBank,
           java.util.Calendar transactionDate,
           java.util.Date bookingDate,
           java.util.Date valueDate,
           is.idega.idegaweb.egov.company.banking.ws.claims.PaymentPaymentType paymentType,
           java.math.BigDecimal amountDeposited,
           java.math.BigDecimal totalAmount,
           java.math.BigDecimal capitalGainsTax,
           java.lang.String billNumber,
           java.lang.String customerNumber,
           java.math.BigDecimal noticeChargeAmount,
           java.math.BigDecimal defaultChargeAmount,
           java.math.BigDecimal otherCostsAmount,
           java.math.BigDecimal otherDefaultCostsAmount,
           java.math.BigDecimal defaultInterestAmount,
           java.math.BigDecimal discountAmount,
           is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyExchangeRate currency,
           java.lang.String batchNumber) {
           this.key = key;
           this.payorID = payorID;
           this.amount = amount;
           this.finalDueDate = finalDueDate;
           this.identifier = identifier;
           this.reference = reference;
           this.categoryCode = categoryCode;
           this.redeemingBank = redeemingBank;
           this.transactionDate = transactionDate;
           this.bookingDate = bookingDate;
           this.valueDate = valueDate;
           this.paymentType = paymentType;
           this.amountDeposited = amountDeposited;
           this.totalAmount = totalAmount;
           this.capitalGainsTax = capitalGainsTax;
           this.billNumber = billNumber;
           this.customerNumber = customerNumber;
           this.noticeChargeAmount = noticeChargeAmount;
           this.defaultChargeAmount = defaultChargeAmount;
           this.otherCostsAmount = otherCostsAmount;
           this.otherDefaultCostsAmount = otherDefaultCostsAmount;
           this.defaultInterestAmount = defaultInterestAmount;
           this.discountAmount = discountAmount;
           this.currency = currency;
           this.batchNumber = batchNumber;
    }


    /**
     * Gets the key value for this Payment.
     * 
     * @return key   * The combination of fields that uniquely identifies a claim.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey getKey() {
        return key;
    }


    /**
     * Sets the key value for this Payment.
     * 
     * @param key   * The combination of fields that uniquely identifies a claim.
     */
    public void setKey(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key) {
        this.key = key;
    }


    /**
     * Gets the payorID value for this Payment.
     * 
     * @return payorID   * Payor person id.
     */
    public java.lang.String getPayorID() {
        return payorID;
    }


    /**
     * Sets the payorID value for this Payment.
     * 
     * @param payorID   * Payor person id.
     */
    public void setPayorID(java.lang.String payorID) {
        this.payorID = payorID;
    }


    /**
     * Gets the amount value for this Payment.
     * 
     * @return amount   * Amount of claim.
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this Payment.
     * 
     * @param amount   * Amount of claim.
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the finalDueDate value for this Payment.
     * 
     * @return finalDueDate   * Final due date of claim.
     */
    public java.util.Date getFinalDueDate() {
        return finalDueDate;
    }


    /**
     * Sets the finalDueDate value for this Payment.
     * 
     * @param finalDueDate   * Final due date of claim.
     */
    public void setFinalDueDate(java.util.Date finalDueDate) {
        this.finalDueDate = finalDueDate;
    }


    /**
     * Gets the identifier value for this Payment.
     * 
     * @return identifier   * The unique identifier of the claimant that the claim relates
     * to.
     */
    public java.lang.String getIdentifier() {
        return identifier;
    }


    /**
     * Sets the identifier value for this Payment.
     * 
     * @param identifier   * The unique identifier of the claimant that the claim relates
     * to.
     */
    public void setIdentifier(java.lang.String identifier) {
        this.identifier = identifier;
    }


    /**
     * Gets the reference value for this Payment.
     * 
     * @return reference   * Reference determined by claimant.
     */
    public java.lang.String getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this Payment.
     * 
     * @param reference   * Reference determined by claimant.
     */
    public void setReference(java.lang.String reference) {
        this.reference = reference;
    }


    /**
     * Gets the categoryCode value for this Payment.
     * 
     * @return categoryCode   * Claimant’s text key, explanation of payment. Optional property
     * of claim.
     */
    public java.lang.String getCategoryCode() {
        return categoryCode;
    }


    /**
     * Sets the categoryCode value for this Payment.
     * 
     * @param categoryCode   * Claimant’s text key, explanation of payment. Optional property
     * of claim.
     */
    public void setCategoryCode(java.lang.String categoryCode) {
        this.categoryCode = categoryCode;
    }


    /**
     * Gets the redeemingBank value for this Payment.
     * 
     * @return redeemingBank   * Where the payment comes from.
     */
    public java.lang.String getRedeemingBank() {
        return redeemingBank;
    }


    /**
     * Sets the redeemingBank value for this Payment.
     * 
     * @param redeemingBank   * Where the payment comes from.
     */
    public void setRedeemingBank(java.lang.String redeemingBank) {
        this.redeemingBank = redeemingBank;
    }


    /**
     * Gets the transactionDate value for this Payment.
     * 
     * @return transactionDate   * The date on which a transaction in the Claims File is effected.
     */
    public java.util.Calendar getTransactionDate() {
        return transactionDate;
    }


    /**
     * Sets the transactionDate value for this Payment.
     * 
     * @param transactionDate   * The date on which a transaction in the Claims File is effected.
     */
    public void setTransactionDate(java.util.Calendar transactionDate) {
        this.transactionDate = transactionDate;
    }


    /**
     * Gets the bookingDate value for this Payment.
     * 
     * @return bookingDate   * The date when the transaction is booked.
     */
    public java.util.Date getBookingDate() {
        return bookingDate;
    }


    /**
     * Sets the bookingDate value for this Payment.
     * 
     * @param bookingDate   * The date when the transaction is booked.
     */
    public void setBookingDate(java.util.Date bookingDate) {
        this.bookingDate = bookingDate;
    }


    /**
     * Gets the valueDate value for this Payment.
     * 
     * @return valueDate   * The validation date used to calculate default interests and
     * charges.
     */
    public java.util.Date getValueDate() {
        return valueDate;
    }


    /**
     * Sets the valueDate value for this Payment.
     * 
     * @param valueDate   * The validation date used to calculate default interests and
     * charges.
     */
    public void setValueDate(java.util.Date valueDate) {
        this.valueDate = valueDate;
    }


    /**
     * Gets the paymentType value for this Payment.
     * 
     * @return paymentType
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.PaymentPaymentType getPaymentType() {
        return paymentType;
    }


    /**
     * Sets the paymentType value for this Payment.
     * 
     * @param paymentType
     */
    public void setPaymentType(is.idega.idegaweb.egov.company.banking.ws.claims.PaymentPaymentType paymentType) {
        this.paymentType = paymentType;
    }


    /**
     * Gets the amountDeposited value for this Payment.
     * 
     * @return amountDeposited   * Amount deposited in the account used for the disposal of payments.
     */
    public java.math.BigDecimal getAmountDeposited() {
        return amountDeposited;
    }


    /**
     * Sets the amountDeposited value for this Payment.
     * 
     * @param amountDeposited   * Amount deposited in the account used for the disposal of payments.
     */
    public void setAmountDeposited(java.math.BigDecimal amountDeposited) {
        this.amountDeposited = amountDeposited;
    }


    /**
     * Gets the totalAmount value for this Payment.
     * 
     * @return totalAmount   * Total amount paid, with default interest and costs.
     */
    public java.math.BigDecimal getTotalAmount() {
        return totalAmount;
    }


    /**
     * Sets the totalAmount value for this Payment.
     * 
     * @param totalAmount   * Total amount paid, with default interest and costs.
     */
    public void setTotalAmount(java.math.BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    /**
     * Gets the capitalGainsTax value for this Payment.
     * 
     * @return capitalGainsTax   * Withheld capital gains tax.
     */
    public java.math.BigDecimal getCapitalGainsTax() {
        return capitalGainsTax;
    }


    /**
     * Sets the capitalGainsTax value for this Payment.
     * 
     * @param capitalGainsTax   * Withheld capital gains tax.
     */
    public void setCapitalGainsTax(java.math.BigDecimal capitalGainsTax) {
        this.capitalGainsTax = capitalGainsTax;
    }


    /**
     * Gets the billNumber value for this Payment.
     * 
     * @return billNumber   * Billnumber, optional. Information provided by claimant.
     */
    public java.lang.String getBillNumber() {
        return billNumber;
    }


    /**
     * Sets the billNumber value for this Payment.
     * 
     * @param billNumber   * Billnumber, optional. Information provided by claimant.
     */
    public void setBillNumber(java.lang.String billNumber) {
        this.billNumber = billNumber;
    }


    /**
     * Gets the customerNumber value for this Payment.
     * 
     * @return customerNumber   * Customer number of payor, claimant's unique code for the customer.
     */
    public java.lang.String getCustomerNumber() {
        return customerNumber;
    }


    /**
     * Sets the customerNumber value for this Payment.
     * 
     * @param customerNumber   * Customer number of payor, claimant's unique code for the customer.
     */
    public void setCustomerNumber(java.lang.String customerNumber) {
        this.customerNumber = customerNumber;
    }


    /**
     * Gets the noticeChargeAmount value for this Payment.
     * 
     * @return noticeChargeAmount   * Notice charge paid.
     */
    public java.math.BigDecimal getNoticeChargeAmount() {
        return noticeChargeAmount;
    }


    /**
     * Sets the noticeChargeAmount value for this Payment.
     * 
     * @param noticeChargeAmount   * Notice charge paid.
     */
    public void setNoticeChargeAmount(java.math.BigDecimal noticeChargeAmount) {
        this.noticeChargeAmount = noticeChargeAmount;
    }


    /**
     * Gets the defaultChargeAmount value for this Payment.
     * 
     * @return defaultChargeAmount   * Default charge paid.
     */
    public java.math.BigDecimal getDefaultChargeAmount() {
        return defaultChargeAmount;
    }


    /**
     * Sets the defaultChargeAmount value for this Payment.
     * 
     * @param defaultChargeAmount   * Default charge paid.
     */
    public void setDefaultChargeAmount(java.math.BigDecimal defaultChargeAmount) {
        this.defaultChargeAmount = defaultChargeAmount;
    }


    /**
     * Gets the otherCostsAmount value for this Payment.
     * 
     * @return otherCostsAmount   * Other costs paid.
     */
    public java.math.BigDecimal getOtherCostsAmount() {
        return otherCostsAmount;
    }


    /**
     * Sets the otherCostsAmount value for this Payment.
     * 
     * @param otherCostsAmount   * Other costs paid.
     */
    public void setOtherCostsAmount(java.math.BigDecimal otherCostsAmount) {
        this.otherCostsAmount = otherCostsAmount;
    }


    /**
     * Gets the otherDefaultCostsAmount value for this Payment.
     * 
     * @return otherDefaultCostsAmount   * Other default costs paid.
     */
    public java.math.BigDecimal getOtherDefaultCostsAmount() {
        return otherDefaultCostsAmount;
    }


    /**
     * Sets the otherDefaultCostsAmount value for this Payment.
     * 
     * @param otherDefaultCostsAmount   * Other default costs paid.
     */
    public void setOtherDefaultCostsAmount(java.math.BigDecimal otherDefaultCostsAmount) {
        this.otherDefaultCostsAmount = otherDefaultCostsAmount;
    }


    /**
     * Gets the defaultInterestAmount value for this Payment.
     * 
     * @return defaultInterestAmount   * Paid default interest.
     */
    public java.math.BigDecimal getDefaultInterestAmount() {
        return defaultInterestAmount;
    }


    /**
     * Sets the defaultInterestAmount value for this Payment.
     * 
     * @param defaultInterestAmount   * Paid default interest.
     */
    public void setDefaultInterestAmount(java.math.BigDecimal defaultInterestAmount) {
        this.defaultInterestAmount = defaultInterestAmount;
    }


    /**
     * Gets the discountAmount value for this Payment.
     * 
     * @return discountAmount   * Discount granted.
     */
    public java.math.BigDecimal getDiscountAmount() {
        return discountAmount;
    }


    /**
     * Sets the discountAmount value for this Payment.
     * 
     * @param discountAmount   * Discount granted.
     */
    public void setDiscountAmount(java.math.BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }


    /**
     * Gets the currency value for this Payment.
     * 
     * @return currency   * Currency information, only available for forex claims.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyExchangeRate getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this Payment.
     * 
     * @param currency   * Currency information, only available for forex claims.
     */
    public void setCurrency(is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyExchangeRate currency) {
        this.currency = currency;
    }


    /**
     * Gets the batchNumber value for this Payment.
     * 
     * @return batchNumber   * Batch number of payment.
     */
    public java.lang.String getBatchNumber() {
        return batchNumber;
    }


    /**
     * Sets the batchNumber value for this Payment.
     * 
     * @param batchNumber   * Batch number of payment.
     */
    public void setBatchNumber(java.lang.String batchNumber) {
        this.batchNumber = batchNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Payment)) return false;
        Payment other = (Payment) obj;
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
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.finalDueDate==null && other.getFinalDueDate()==null) || 
             (this.finalDueDate!=null &&
              this.finalDueDate.equals(other.getFinalDueDate()))) &&
            ((this.identifier==null && other.getIdentifier()==null) || 
             (this.identifier!=null &&
              this.identifier.equals(other.getIdentifier()))) &&
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              this.reference.equals(other.getReference()))) &&
            ((this.categoryCode==null && other.getCategoryCode()==null) || 
             (this.categoryCode!=null &&
              this.categoryCode.equals(other.getCategoryCode()))) &&
            ((this.redeemingBank==null && other.getRedeemingBank()==null) || 
             (this.redeemingBank!=null &&
              this.redeemingBank.equals(other.getRedeemingBank()))) &&
            ((this.transactionDate==null && other.getTransactionDate()==null) || 
             (this.transactionDate!=null &&
              this.transactionDate.equals(other.getTransactionDate()))) &&
            ((this.bookingDate==null && other.getBookingDate()==null) || 
             (this.bookingDate!=null &&
              this.bookingDate.equals(other.getBookingDate()))) &&
            ((this.valueDate==null && other.getValueDate()==null) || 
             (this.valueDate!=null &&
              this.valueDate.equals(other.getValueDate()))) &&
            ((this.paymentType==null && other.getPaymentType()==null) || 
             (this.paymentType!=null &&
              this.paymentType.equals(other.getPaymentType()))) &&
            ((this.amountDeposited==null && other.getAmountDeposited()==null) || 
             (this.amountDeposited!=null &&
              this.amountDeposited.equals(other.getAmountDeposited()))) &&
            ((this.totalAmount==null && other.getTotalAmount()==null) || 
             (this.totalAmount!=null &&
              this.totalAmount.equals(other.getTotalAmount()))) &&
            ((this.capitalGainsTax==null && other.getCapitalGainsTax()==null) || 
             (this.capitalGainsTax!=null &&
              this.capitalGainsTax.equals(other.getCapitalGainsTax()))) &&
            ((this.billNumber==null && other.getBillNumber()==null) || 
             (this.billNumber!=null &&
              this.billNumber.equals(other.getBillNumber()))) &&
            ((this.customerNumber==null && other.getCustomerNumber()==null) || 
             (this.customerNumber!=null &&
              this.customerNumber.equals(other.getCustomerNumber()))) &&
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
              this.discountAmount.equals(other.getDiscountAmount()))) &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            ((this.batchNumber==null && other.getBatchNumber()==null) || 
             (this.batchNumber!=null &&
              this.batchNumber.equals(other.getBatchNumber())));
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
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getFinalDueDate() != null) {
            _hashCode += getFinalDueDate().hashCode();
        }
        if (getIdentifier() != null) {
            _hashCode += getIdentifier().hashCode();
        }
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        if (getCategoryCode() != null) {
            _hashCode += getCategoryCode().hashCode();
        }
        if (getRedeemingBank() != null) {
            _hashCode += getRedeemingBank().hashCode();
        }
        if (getTransactionDate() != null) {
            _hashCode += getTransactionDate().hashCode();
        }
        if (getBookingDate() != null) {
            _hashCode += getBookingDate().hashCode();
        }
        if (getValueDate() != null) {
            _hashCode += getValueDate().hashCode();
        }
        if (getPaymentType() != null) {
            _hashCode += getPaymentType().hashCode();
        }
        if (getAmountDeposited() != null) {
            _hashCode += getAmountDeposited().hashCode();
        }
        if (getTotalAmount() != null) {
            _hashCode += getTotalAmount().hashCode();
        }
        if (getCapitalGainsTax() != null) {
            _hashCode += getCapitalGainsTax().hashCode();
        }
        if (getBillNumber() != null) {
            _hashCode += getBillNumber().hashCode();
        }
        if (getCustomerNumber() != null) {
            _hashCode += getCustomerNumber().hashCode();
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
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        if (getBatchNumber() != null) {
            _hashCode += getBatchNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Payment.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Payment"));
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
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finalDueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "FinalDueDate"));
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
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Reference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("redeemingBank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "RedeemingBank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "TransactionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "BookingDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ValueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "PaymentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", ">Payment>PaymentType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountDeposited");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "AmountDeposited"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "TotalAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capitalGainsTax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CapitalGainsTax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
        elemField.setFieldName("noticeChargeAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "NoticeChargeAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultChargeAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultChargeAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherCostsAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "OtherCostsAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherDefaultCostsAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "OtherDefaultCostsAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultInterestAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultInterestAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discountAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DiscountAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CurrencyExchangeRate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("batchNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "BatchNumber"));
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
