/**
 * ClaimKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;


/**
 * The combination of fields that uniquely identifies a claim.
 */
public class ClaimKey  implements java.io.Serializable {
    /* The person identification number of the claimant and part of
     * the unique key of the claim. */
    private java.lang.String claimantID;

    /* The Bank number of the claim, ledger number (66) and number
     * of claim. */
    private java.lang.String account;

    /* Due date of the claim. */
    private java.util.Date dueDate;

    public ClaimKey() {
    }

    public ClaimKey(
           java.lang.String claimantID,
           java.lang.String account,
           java.util.Date dueDate) {
           this.claimantID = claimantID;
           this.account = account;
           this.dueDate = dueDate;
    }


    /**
     * Gets the claimantID value for this ClaimKey.
     * 
     * @return claimantID   * The person identification number of the claimant and part of
     * the unique key of the claim.
     */
    public java.lang.String getClaimantID() {
        return claimantID;
    }


    /**
     * Sets the claimantID value for this ClaimKey.
     * 
     * @param claimantID   * The person identification number of the claimant and part of
     * the unique key of the claim.
     */
    public void setClaimantID(java.lang.String claimantID) {
        this.claimantID = claimantID;
    }


    /**
     * Gets the account value for this ClaimKey.
     * 
     * @return account   * The Bank number of the claim, ledger number (66) and number
     * of claim.
     */
    public java.lang.String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this ClaimKey.
     * 
     * @param account   * The Bank number of the claim, ledger number (66) and number
     * of claim.
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }


    /**
     * Gets the dueDate value for this ClaimKey.
     * 
     * @return dueDate   * Due date of the claim.
     */
    public java.util.Date getDueDate() {
        return dueDate;
    }


    /**
     * Sets the dueDate value for this ClaimKey.
     * 
     * @param dueDate   * Due date of the claim.
     */
    public void setDueDate(java.util.Date dueDate) {
        this.dueDate = dueDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClaimKey)) return false;
        ClaimKey other = (ClaimKey) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.claimantID==null && other.getClaimantID()==null) || 
             (this.claimantID!=null &&
              this.claimantID.equals(other.getClaimantID()))) &&
            ((this.account==null && other.getAccount()==null) || 
             (this.account!=null &&
              this.account.equals(other.getAccount()))) &&
            ((this.dueDate==null && other.getDueDate()==null) || 
             (this.dueDate!=null &&
              this.dueDate.equals(other.getDueDate())));
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
        if (getClaimantID() != null) {
            _hashCode += getClaimantID().hashCode();
        }
        if (getAccount() != null) {
            _hashCode += getAccount().hashCode();
        }
        if (getDueDate() != null) {
            _hashCode += getDueDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClaimKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimantID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimantID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
