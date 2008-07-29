/**
 * PaymentIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.payments;


/**
 * Information about the account that funds will be transferred to
 */
public class PaymentIn  implements java.io.Serializable {
    private is.idega.idegaweb.egov.company.banking.ws.payments.ABGiro ABGiro;

    private is.idega.idegaweb.egov.company.banking.ws.payments.CGiro CGiro;

    private is.idega.idegaweb.egov.company.banking.ws.payments.PaymentSlip paymentSlip;

    private is.idega.idegaweb.egov.company.banking.ws.payments.Transfer transfer;

    /* Payment amount in ISK. Please note that it is only possible
     * to transfer money between accounts in ISK */
    private java.math.BigDecimal amount;

    /* Receipts that are sent when the payment is processed */
    private is.idega.idegaweb.egov.company.banking.ws.payments.Communication receipt;

    /* Description for the payment. This description is shown if the
     * customer looks up the payment in the Internet bank */
    private java.lang.String description;

    /* The booking ID field can be used by the sender to include his
     * own ID for the payment. This ID is also returned to the sender when
     * the payment is queried (e.g. when the GetPaymentsResult operation
     * is used) */
    private java.lang.String bookingID;

    public PaymentIn() {
    }

    public PaymentIn(
           is.idega.idegaweb.egov.company.banking.ws.payments.ABGiro ABGiro,
           is.idega.idegaweb.egov.company.banking.ws.payments.CGiro CGiro,
           is.idega.idegaweb.egov.company.banking.ws.payments.PaymentSlip paymentSlip,
           is.idega.idegaweb.egov.company.banking.ws.payments.Transfer transfer,
           java.math.BigDecimal amount,
           is.idega.idegaweb.egov.company.banking.ws.payments.Communication receipt,
           java.lang.String description,
           java.lang.String bookingID) {
           this.ABGiro = ABGiro;
           this.CGiro = CGiro;
           this.paymentSlip = paymentSlip;
           this.transfer = transfer;
           this.amount = amount;
           this.receipt = receipt;
           this.description = description;
           this.bookingID = bookingID;
    }


    /**
     * Gets the ABGiro value for this PaymentIn.
     * 
     * @return ABGiro
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.ABGiro getABGiro() {
        return ABGiro;
    }


    /**
     * Sets the ABGiro value for this PaymentIn.
     * 
     * @param ABGiro
     */
    public void setABGiro(is.idega.idegaweb.egov.company.banking.ws.payments.ABGiro ABGiro) {
        this.ABGiro = ABGiro;
    }


    /**
     * Gets the CGiro value for this PaymentIn.
     * 
     * @return CGiro
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.CGiro getCGiro() {
        return CGiro;
    }


    /**
     * Sets the CGiro value for this PaymentIn.
     * 
     * @param CGiro
     */
    public void setCGiro(is.idega.idegaweb.egov.company.banking.ws.payments.CGiro CGiro) {
        this.CGiro = CGiro;
    }


    /**
     * Gets the paymentSlip value for this PaymentIn.
     * 
     * @return paymentSlip
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.PaymentSlip getPaymentSlip() {
        return paymentSlip;
    }


    /**
     * Sets the paymentSlip value for this PaymentIn.
     * 
     * @param paymentSlip
     */
    public void setPaymentSlip(is.idega.idegaweb.egov.company.banking.ws.payments.PaymentSlip paymentSlip) {
        this.paymentSlip = paymentSlip;
    }


    /**
     * Gets the transfer value for this PaymentIn.
     * 
     * @return transfer
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.Transfer getTransfer() {
        return transfer;
    }


    /**
     * Sets the transfer value for this PaymentIn.
     * 
     * @param transfer
     */
    public void setTransfer(is.idega.idegaweb.egov.company.banking.ws.payments.Transfer transfer) {
        this.transfer = transfer;
    }


    /**
     * Gets the amount value for this PaymentIn.
     * 
     * @return amount   * Payment amount in ISK. Please note that it is only possible
     * to transfer money between accounts in ISK
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this PaymentIn.
     * 
     * @param amount   * Payment amount in ISK. Please note that it is only possible
     * to transfer money between accounts in ISK
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the receipt value for this PaymentIn.
     * 
     * @return receipt   * Receipts that are sent when the payment is processed
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.Communication getReceipt() {
        return receipt;
    }


    /**
     * Sets the receipt value for this PaymentIn.
     * 
     * @param receipt   * Receipts that are sent when the payment is processed
     */
    public void setReceipt(is.idega.idegaweb.egov.company.banking.ws.payments.Communication receipt) {
        this.receipt = receipt;
    }


    /**
     * Gets the description value for this PaymentIn.
     * 
     * @return description   * Description for the payment. This description is shown if the
     * customer looks up the payment in the Internet bank
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this PaymentIn.
     * 
     * @param description   * Description for the payment. This description is shown if the
     * customer looks up the payment in the Internet bank
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the bookingID value for this PaymentIn.
     * 
     * @return bookingID   * The booking ID field can be used by the sender to include his
     * own ID for the payment. This ID is also returned to the sender when
     * the payment is queried (e.g. when the GetPaymentsResult operation
     * is used)
     */
    public java.lang.String getBookingID() {
        return bookingID;
    }


    /**
     * Sets the bookingID value for this PaymentIn.
     * 
     * @param bookingID   * The booking ID field can be used by the sender to include his
     * own ID for the payment. This ID is also returned to the sender when
     * the payment is queried (e.g. when the GetPaymentsResult operation
     * is used)
     */
    public void setBookingID(java.lang.String bookingID) {
        this.bookingID = bookingID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentIn)) return false;
        PaymentIn other = (PaymentIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ABGiro==null && other.getABGiro()==null) || 
             (this.ABGiro!=null &&
              this.ABGiro.equals(other.getABGiro()))) &&
            ((this.CGiro==null && other.getCGiro()==null) || 
             (this.CGiro!=null &&
              this.CGiro.equals(other.getCGiro()))) &&
            ((this.paymentSlip==null && other.getPaymentSlip()==null) || 
             (this.paymentSlip!=null &&
              this.paymentSlip.equals(other.getPaymentSlip()))) &&
            ((this.transfer==null && other.getTransfer()==null) || 
             (this.transfer!=null &&
              this.transfer.equals(other.getTransfer()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.receipt==null && other.getReceipt()==null) || 
             (this.receipt!=null &&
              this.receipt.equals(other.getReceipt()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.bookingID==null && other.getBookingID()==null) || 
             (this.bookingID!=null &&
              this.bookingID.equals(other.getBookingID())));
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
        if (getABGiro() != null) {
            _hashCode += getABGiro().hashCode();
        }
        if (getCGiro() != null) {
            _hashCode += getCGiro().hashCode();
        }
        if (getPaymentSlip() != null) {
            _hashCode += getPaymentSlip().hashCode();
        }
        if (getTransfer() != null) {
            _hashCode += getTransfer().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getReceipt() != null) {
            _hashCode += getReceipt().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getBookingID() != null) {
            _hashCode += getBookingID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ABGiro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "ABGiro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "ABGiro"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CGiro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "CGiro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "CGiro"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentSlip");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentSlip"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentSlip"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transfer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Transfer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Transfer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receipt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Receipt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Communication"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookingID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "BookingID"));
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
