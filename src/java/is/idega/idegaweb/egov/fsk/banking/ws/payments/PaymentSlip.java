/**
 * PaymentSlip.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.payments;


/**
 * Payment slip is used to pay payment slips for claims (ledger 62
 * and 66), debenture (ledger 74) and bill of exchange (ledger 60, 70).
 */
public class PaymentSlip  implements java.io.Serializable {
    /* The ID of the account. */
    private java.lang.String account;

    /* PersonID from the OCR line of the payment slip. If ledger is
     * 66 then this is the PersonId of the claimant, otherwise this is the
     * personId of the payor. */
    private java.lang.String personID;

    /* Due date for the payment slip */
    private java.util.Date dueDate;

    /* True if the payment is deposit, false if the payment slip should
     * be fully paid */
    private boolean isDeposit;

    public PaymentSlip() {
    }

    public PaymentSlip(
           java.lang.String account,
           java.lang.String personID,
           java.util.Date dueDate,
           boolean isDeposit) {
           this.account = account;
           this.personID = personID;
           this.dueDate = dueDate;
           this.isDeposit = isDeposit;
    }


    /**
     * Gets the account value for this PaymentSlip.
     * 
     * @return account   * The ID of the account.
     */
    public java.lang.String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this PaymentSlip.
     * 
     * @param account   * The ID of the account.
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }


    /**
     * Gets the personID value for this PaymentSlip.
     * 
     * @return personID   * PersonID from the OCR line of the payment slip. If ledger is
     * 66 then this is the PersonId of the claimant, otherwise this is the
     * personId of the payor.
     */
    public java.lang.String getPersonID() {
        return personID;
    }


    /**
     * Sets the personID value for this PaymentSlip.
     * 
     * @param personID   * PersonID from the OCR line of the payment slip. If ledger is
     * 66 then this is the PersonId of the claimant, otherwise this is the
     * personId of the payor.
     */
    public void setPersonID(java.lang.String personID) {
        this.personID = personID;
    }


    /**
     * Gets the dueDate value for this PaymentSlip.
     * 
     * @return dueDate   * Due date for the payment slip
     */
    public java.util.Date getDueDate() {
        return dueDate;
    }


    /**
     * Sets the dueDate value for this PaymentSlip.
     * 
     * @param dueDate   * Due date for the payment slip
     */
    public void setDueDate(java.util.Date dueDate) {
        this.dueDate = dueDate;
    }


    /**
     * Gets the isDeposit value for this PaymentSlip.
     * 
     * @return isDeposit   * True if the payment is deposit, false if the payment slip should
     * be fully paid
     */
    public boolean isIsDeposit() {
        return isDeposit;
    }


    /**
     * Sets the isDeposit value for this PaymentSlip.
     * 
     * @param isDeposit   * True if the payment is deposit, false if the payment slip should
     * be fully paid
     */
    public void setIsDeposit(boolean isDeposit) {
        this.isDeposit = isDeposit;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentSlip)) return false;
        PaymentSlip other = (PaymentSlip) obj;
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
            ((this.personID==null && other.getPersonID()==null) || 
             (this.personID!=null &&
              this.personID.equals(other.getPersonID()))) &&
            ((this.dueDate==null && other.getDueDate()==null) || 
             (this.dueDate!=null &&
              this.dueDate.equals(other.getDueDate()))) &&
            this.isDeposit == other.isIsDeposit();
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
        if (getPersonID() != null) {
            _hashCode += getPersonID().hashCode();
        }
        if (getDueDate() != null) {
            _hashCode += getDueDate().hashCode();
        }
        _hashCode += (isIsDeposit() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentSlip.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentSlip"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PersonID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "DueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isDeposit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "IsDeposit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
