/**
 * CGiro.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.payments;


/**
 * Describes a CGiro payment
 */
public class CGiro  implements java.io.Serializable {
    /* The ID of the account. */
    private java.lang.String account;

    /* PersonID of the account owner. */
    private java.lang.String accountOwnerID;

    /* Bill number for the payment */
    private java.lang.String billNumber;

    /* Category code for the payment */
    private java.lang.String categoryCode;

    public CGiro() {
    }

    public CGiro(
           java.lang.String account,
           java.lang.String accountOwnerID,
           java.lang.String billNumber,
           java.lang.String categoryCode) {
           this.account = account;
           this.accountOwnerID = accountOwnerID;
           this.billNumber = billNumber;
           this.categoryCode = categoryCode;
    }


    /**
     * Gets the account value for this CGiro.
     * 
     * @return account   * The ID of the account.
     */
    public java.lang.String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this CGiro.
     * 
     * @param account   * The ID of the account.
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }


    /**
     * Gets the accountOwnerID value for this CGiro.
     * 
     * @return accountOwnerID   * PersonID of the account owner.
     */
    public java.lang.String getAccountOwnerID() {
        return accountOwnerID;
    }


    /**
     * Sets the accountOwnerID value for this CGiro.
     * 
     * @param accountOwnerID   * PersonID of the account owner.
     */
    public void setAccountOwnerID(java.lang.String accountOwnerID) {
        this.accountOwnerID = accountOwnerID;
    }


    /**
     * Gets the billNumber value for this CGiro.
     * 
     * @return billNumber   * Bill number for the payment
     */
    public java.lang.String getBillNumber() {
        return billNumber;
    }


    /**
     * Sets the billNumber value for this CGiro.
     * 
     * @param billNumber   * Bill number for the payment
     */
    public void setBillNumber(java.lang.String billNumber) {
        this.billNumber = billNumber;
    }


    /**
     * Gets the categoryCode value for this CGiro.
     * 
     * @return categoryCode   * Category code for the payment
     */
    public java.lang.String getCategoryCode() {
        return categoryCode;
    }


    /**
     * Sets the categoryCode value for this CGiro.
     * 
     * @param categoryCode   * Category code for the payment
     */
    public void setCategoryCode(java.lang.String categoryCode) {
        this.categoryCode = categoryCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CGiro)) return false;
        CGiro other = (CGiro) obj;
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
            ((this.billNumber==null && other.getBillNumber()==null) || 
             (this.billNumber!=null &&
              this.billNumber.equals(other.getBillNumber()))) &&
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
        int _hashCode = 1;
        if (getAccount() != null) {
            _hashCode += getAccount().hashCode();
        }
        if (getAccountOwnerID() != null) {
            _hashCode += getAccountOwnerID().hashCode();
        }
        if (getBillNumber() != null) {
            _hashCode += getBillNumber().hashCode();
        }
        if (getCategoryCode() != null) {
            _hashCode += getCategoryCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CGiro.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "CGiro"));
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
        elemField.setFieldName("billNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "BillNumber"));
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
