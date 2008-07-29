/**
 * PaymentOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.payments;


/**
 * Information about the account from which funds will be drawn
 */
public class PaymentOut  implements java.io.Serializable {
    /* The ID of the account the funds will be drawn from. */
    private java.lang.String account;

    /* Person ID of the account owner. */
    private java.lang.String accountOwnerID;

    /* Category code for the payment */
    private java.lang.String categoryCode;

    /* Reference for the transaction. Usually the person ID of the
     * originator is set in this field so the reiceiver of the payment can
     * see from who the transaction is */
    private java.lang.String reference;

    /* Bill number for the payment */
    private java.lang.String billNumber;

    /* Receipts that are sent to the payor when the payment is processed */
    private is.idega.idegaweb.egov.company.banking.ws.payments.Communication receipt;

    public PaymentOut() {
    }

    public PaymentOut(
           java.lang.String account,
           java.lang.String accountOwnerID,
           java.lang.String categoryCode,
           java.lang.String reference,
           java.lang.String billNumber,
           is.idega.idegaweb.egov.company.banking.ws.payments.Communication receipt) {
           this.account = account;
           this.accountOwnerID = accountOwnerID;
           this.categoryCode = categoryCode;
           this.reference = reference;
           this.billNumber = billNumber;
           this.receipt = receipt;
    }


    /**
     * Gets the account value for this PaymentOut.
     * 
     * @return account   * The ID of the account the funds will be drawn from.
     */
    public java.lang.String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this PaymentOut.
     * 
     * @param account   * The ID of the account the funds will be drawn from.
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }


    /**
     * Gets the accountOwnerID value for this PaymentOut.
     * 
     * @return accountOwnerID   * Person ID of the account owner.
     */
    public java.lang.String getAccountOwnerID() {
        return accountOwnerID;
    }


    /**
     * Sets the accountOwnerID value for this PaymentOut.
     * 
     * @param accountOwnerID   * Person ID of the account owner.
     */
    public void setAccountOwnerID(java.lang.String accountOwnerID) {
        this.accountOwnerID = accountOwnerID;
    }


    /**
     * Gets the categoryCode value for this PaymentOut.
     * 
     * @return categoryCode   * Category code for the payment
     */
    public java.lang.String getCategoryCode() {
        return categoryCode;
    }


    /**
     * Sets the categoryCode value for this PaymentOut.
     * 
     * @param categoryCode   * Category code for the payment
     */
    public void setCategoryCode(java.lang.String categoryCode) {
        this.categoryCode = categoryCode;
    }


    /**
     * Gets the reference value for this PaymentOut.
     * 
     * @return reference   * Reference for the transaction. Usually the person ID of the
     * originator is set in this field so the reiceiver of the payment can
     * see from who the transaction is
     */
    public java.lang.String getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this PaymentOut.
     * 
     * @param reference   * Reference for the transaction. Usually the person ID of the
     * originator is set in this field so the reiceiver of the payment can
     * see from who the transaction is
     */
    public void setReference(java.lang.String reference) {
        this.reference = reference;
    }


    /**
     * Gets the billNumber value for this PaymentOut.
     * 
     * @return billNumber   * Bill number for the payment
     */
    public java.lang.String getBillNumber() {
        return billNumber;
    }


    /**
     * Sets the billNumber value for this PaymentOut.
     * 
     * @param billNumber   * Bill number for the payment
     */
    public void setBillNumber(java.lang.String billNumber) {
        this.billNumber = billNumber;
    }


    /**
     * Gets the receipt value for this PaymentOut.
     * 
     * @return receipt   * Receipts that are sent to the payor when the payment is processed
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.Communication getReceipt() {
        return receipt;
    }


    /**
     * Sets the receipt value for this PaymentOut.
     * 
     * @param receipt   * Receipts that are sent to the payor when the payment is processed
     */
    public void setReceipt(is.idega.idegaweb.egov.company.banking.ws.payments.Communication receipt) {
        this.receipt = receipt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentOut)) return false;
        PaymentOut other = (PaymentOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.account==null && other.getAccount()==null) || 
             (this.account!=null &&
              this.account.equals(other.getAccount()))) &&
            ((this.accountOwnerID==null && other.getAccountOwnerID()==null) || 
             (this.accountOwnerID!=null &&
              this.accountOwnerID.equals(other.getAccountOwnerID()))) &&
            ((this.categoryCode==null && other.getCategoryCode()==null) || 
             (this.categoryCode!=null &&
              this.categoryCode.equals(other.getCategoryCode()))) &&
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              this.reference.equals(other.getReference()))) &&
            ((this.billNumber==null && other.getBillNumber()==null) || 
             (this.billNumber!=null &&
              this.billNumber.equals(other.getBillNumber()))) &&
            ((this.receipt==null && other.getReceipt()==null) || 
             (this.receipt!=null &&
              this.receipt.equals(other.getReceipt())));
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
        if (getAccount() != null) {
            _hashCode += getAccount().hashCode();
        }
        if (getAccountOwnerID() != null) {
            _hashCode += getAccountOwnerID().hashCode();
        }
        if (getCategoryCode() != null) {
            _hashCode += getCategoryCode().hashCode();
        }
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        if (getBillNumber() != null) {
            _hashCode += getBillNumber().hashCode();
        }
        if (getReceipt() != null) {
            _hashCode += getReceipt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountOwnerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "AccountOwnerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "CategoryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Reference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "BillNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receipt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Receipt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Communication"));
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
