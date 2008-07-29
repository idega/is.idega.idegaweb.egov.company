/**
 * AccountTransaction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.statements;


/**
 * Account Transaction
 */
public class AccountTransaction  implements java.io.Serializable {
    /* Uniquely identifies this transaction with the originating system. */
    private java.lang.String transactionID;

    /* The date when this transaction was booked. */
    private java.util.Date transactionDate;

    /* Value Date */
    private java.util.Date valueDate;

    /* Batch Identifier */
    private java.lang.String batchNumber;

    /* The bank where the transaction originated. */
    private java.lang.String redeemingBank;

    /* Transaction Key */
    private java.lang.String transaction;

    /* Reference */
    private java.lang.String reference;

    /* Bill number */
    private java.lang.String billNumber;

    /* Category code */
    private java.lang.String categoryCode;

    /* Expands on the category code and gives the fulltext explanation. */
    private java.lang.String category;

    /* Expands on the information in the reference, usually by trying
     * to matching the information entered with person identification numbers. */
    private java.lang.String referenceDetail;

    /* Person ID of payor */
    private java.lang.String payorID;

    /* Amount */
    private java.math.BigDecimal amount;

    /* The balance of the account after this transaction. */
    private java.math.BigDecimal balance;

    public AccountTransaction() {
    }

    public AccountTransaction(
           java.lang.String transactionID,
           java.util.Date transactionDate,
           java.util.Date valueDate,
           java.lang.String batchNumber,
           java.lang.String redeemingBank,
           java.lang.String transaction,
           java.lang.String reference,
           java.lang.String billNumber,
           java.lang.String categoryCode,
           java.lang.String category,
           java.lang.String referenceDetail,
           java.lang.String payorID,
           java.math.BigDecimal amount,
           java.math.BigDecimal balance) {
           this.transactionID = transactionID;
           this.transactionDate = transactionDate;
           this.valueDate = valueDate;
           this.batchNumber = batchNumber;
           this.redeemingBank = redeemingBank;
           this.transaction = transaction;
           this.reference = reference;
           this.billNumber = billNumber;
           this.categoryCode = categoryCode;
           this.category = category;
           this.referenceDetail = referenceDetail;
           this.payorID = payorID;
           this.amount = amount;
           this.balance = balance;
    }


    /**
     * Gets the transactionID value for this AccountTransaction.
     * 
     * @return transactionID   * Uniquely identifies this transaction with the originating system.
     */
    public java.lang.String getTransactionID() {
        return transactionID;
    }


    /**
     * Sets the transactionID value for this AccountTransaction.
     * 
     * @param transactionID   * Uniquely identifies this transaction with the originating system.
     */
    public void setTransactionID(java.lang.String transactionID) {
        this.transactionID = transactionID;
    }


    /**
     * Gets the transactionDate value for this AccountTransaction.
     * 
     * @return transactionDate   * The date when this transaction was booked.
     */
    public java.util.Date getTransactionDate() {
        return transactionDate;
    }


    /**
     * Sets the transactionDate value for this AccountTransaction.
     * 
     * @param transactionDate   * The date when this transaction was booked.
     */
    public void setTransactionDate(java.util.Date transactionDate) {
        this.transactionDate = transactionDate;
    }


    /**
     * Gets the valueDate value for this AccountTransaction.
     * 
     * @return valueDate   * Value Date
     */
    public java.util.Date getValueDate() {
        return valueDate;
    }


    /**
     * Sets the valueDate value for this AccountTransaction.
     * 
     * @param valueDate   * Value Date
     */
    public void setValueDate(java.util.Date valueDate) {
        this.valueDate = valueDate;
    }


    /**
     * Gets the batchNumber value for this AccountTransaction.
     * 
     * @return batchNumber   * Batch Identifier
     */
    public java.lang.String getBatchNumber() {
        return batchNumber;
    }


    /**
     * Sets the batchNumber value for this AccountTransaction.
     * 
     * @param batchNumber   * Batch Identifier
     */
    public void setBatchNumber(java.lang.String batchNumber) {
        this.batchNumber = batchNumber;
    }


    /**
     * Gets the redeemingBank value for this AccountTransaction.
     * 
     * @return redeemingBank   * The bank where the transaction originated.
     */
    public java.lang.String getRedeemingBank() {
        return redeemingBank;
    }


    /**
     * Sets the redeemingBank value for this AccountTransaction.
     * 
     * @param redeemingBank   * The bank where the transaction originated.
     */
    public void setRedeemingBank(java.lang.String redeemingBank) {
        this.redeemingBank = redeemingBank;
    }


    /**
     * Gets the transaction value for this AccountTransaction.
     * 
     * @return transaction   * Transaction Key
     */
    public java.lang.String getTransaction() {
        return transaction;
    }


    /**
     * Sets the transaction value for this AccountTransaction.
     * 
     * @param transaction   * Transaction Key
     */
    public void setTransaction(java.lang.String transaction) {
        this.transaction = transaction;
    }


    /**
     * Gets the reference value for this AccountTransaction.
     * 
     * @return reference   * Reference
     */
    public java.lang.String getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this AccountTransaction.
     * 
     * @param reference   * Reference
     */
    public void setReference(java.lang.String reference) {
        this.reference = reference;
    }


    /**
     * Gets the billNumber value for this AccountTransaction.
     * 
     * @return billNumber   * Bill number
     */
    public java.lang.String getBillNumber() {
        return billNumber;
    }


    /**
     * Sets the billNumber value for this AccountTransaction.
     * 
     * @param billNumber   * Bill number
     */
    public void setBillNumber(java.lang.String billNumber) {
        this.billNumber = billNumber;
    }


    /**
     * Gets the categoryCode value for this AccountTransaction.
     * 
     * @return categoryCode   * Category code
     */
    public java.lang.String getCategoryCode() {
        return categoryCode;
    }


    /**
     * Sets the categoryCode value for this AccountTransaction.
     * 
     * @param categoryCode   * Category code
     */
    public void setCategoryCode(java.lang.String categoryCode) {
        this.categoryCode = categoryCode;
    }


    /**
     * Gets the category value for this AccountTransaction.
     * 
     * @return category   * Expands on the category code and gives the fulltext explanation.
     */
    public java.lang.String getCategory() {
        return category;
    }


    /**
     * Sets the category value for this AccountTransaction.
     * 
     * @param category   * Expands on the category code and gives the fulltext explanation.
     */
    public void setCategory(java.lang.String category) {
        this.category = category;
    }


    /**
     * Gets the referenceDetail value for this AccountTransaction.
     * 
     * @return referenceDetail   * Expands on the information in the reference, usually by trying
     * to matching the information entered with person identification numbers.
     */
    public java.lang.String getReferenceDetail() {
        return referenceDetail;
    }


    /**
     * Sets the referenceDetail value for this AccountTransaction.
     * 
     * @param referenceDetail   * Expands on the information in the reference, usually by trying
     * to matching the information entered with person identification numbers.
     */
    public void setReferenceDetail(java.lang.String referenceDetail) {
        this.referenceDetail = referenceDetail;
    }


    /**
     * Gets the payorID value for this AccountTransaction.
     * 
     * @return payorID   * Person ID of payor
     */
    public java.lang.String getPayorID() {
        return payorID;
    }


    /**
     * Sets the payorID value for this AccountTransaction.
     * 
     * @param payorID   * Person ID of payor
     */
    public void setPayorID(java.lang.String payorID) {
        this.payorID = payorID;
    }


    /**
     * Gets the amount value for this AccountTransaction.
     * 
     * @return amount   * Amount
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this AccountTransaction.
     * 
     * @param amount   * Amount
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the balance value for this AccountTransaction.
     * 
     * @return balance   * The balance of the account after this transaction.
     */
    public java.math.BigDecimal getBalance() {
        return balance;
    }


    /**
     * Sets the balance value for this AccountTransaction.
     * 
     * @param balance   * The balance of the account after this transaction.
     */
    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AccountTransaction)) return false;
        AccountTransaction other = (AccountTransaction) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.transactionID==null && other.getTransactionID()==null) || 
             (this.transactionID!=null &&
              this.transactionID.equals(other.getTransactionID()))) &&
            ((this.transactionDate==null && other.getTransactionDate()==null) || 
             (this.transactionDate!=null &&
              this.transactionDate.equals(other.getTransactionDate()))) &&
            ((this.valueDate==null && other.getValueDate()==null) || 
             (this.valueDate!=null &&
              this.valueDate.equals(other.getValueDate()))) &&
            ((this.batchNumber==null && other.getBatchNumber()==null) || 
             (this.batchNumber!=null &&
              this.batchNumber.equals(other.getBatchNumber()))) &&
            ((this.redeemingBank==null && other.getRedeemingBank()==null) || 
             (this.redeemingBank!=null &&
              this.redeemingBank.equals(other.getRedeemingBank()))) &&
            ((this.transaction==null && other.getTransaction()==null) || 
             (this.transaction!=null &&
              this.transaction.equals(other.getTransaction()))) &&
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              this.reference.equals(other.getReference()))) &&
            ((this.billNumber==null && other.getBillNumber()==null) || 
             (this.billNumber!=null &&
              this.billNumber.equals(other.getBillNumber()))) &&
            ((this.categoryCode==null && other.getCategoryCode()==null) || 
             (this.categoryCode!=null &&
              this.categoryCode.equals(other.getCategoryCode()))) &&
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory()))) &&
            ((this.referenceDetail==null && other.getReferenceDetail()==null) || 
             (this.referenceDetail!=null &&
              this.referenceDetail.equals(other.getReferenceDetail()))) &&
            ((this.payorID==null && other.getPayorID()==null) || 
             (this.payorID!=null &&
              this.payorID.equals(other.getPayorID()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.balance==null && other.getBalance()==null) || 
             (this.balance!=null &&
              this.balance.equals(other.getBalance())));
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
        if (getTransactionID() != null) {
            _hashCode += getTransactionID().hashCode();
        }
        if (getTransactionDate() != null) {
            _hashCode += getTransactionDate().hashCode();
        }
        if (getValueDate() != null) {
            _hashCode += getValueDate().hashCode();
        }
        if (getBatchNumber() != null) {
            _hashCode += getBatchNumber().hashCode();
        }
        if (getRedeemingBank() != null) {
            _hashCode += getRedeemingBank().hashCode();
        }
        if (getTransaction() != null) {
            _hashCode += getTransaction().hashCode();
        }
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        if (getBillNumber() != null) {
            _hashCode += getBillNumber().hashCode();
        }
        if (getCategoryCode() != null) {
            _hashCode += getCategoryCode().hashCode();
        }
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        if (getReferenceDetail() != null) {
            _hashCode += getReferenceDetail().hashCode();
        }
        if (getPayorID() != null) {
            _hashCode += getPayorID().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getBalance() != null) {
            _hashCode += getBalance().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AccountTransaction.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "AccountTransaction"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "TransactionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "TransactionDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "ValueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("batchNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "BatchNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("redeemingBank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "RedeemingBank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transaction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Transaction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Reference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "BillNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "CategoryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("category");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Category"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referenceDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "ReferenceDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payorID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "PayorID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Balance"));
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
