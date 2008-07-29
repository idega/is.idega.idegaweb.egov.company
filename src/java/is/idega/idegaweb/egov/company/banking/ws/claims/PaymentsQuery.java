/**
 * PaymentsQuery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;


/**
 * Query on payment.
 */
public class PaymentsQuery  implements java.io.Serializable {
    /* The persion Id of the claimant. */
    private java.lang.String claimant;

    /* The claim identifier belonging to the claimant used to filter
     * the query. */
    private java.lang.String identifier;

    /* The account to filter by. A convenience when many identifiers
     * are connected to the same disposal account, to query for all payments
     * connected to that account. */
    private java.lang.String disposalAccount;

    /* Date from inclusive. */
    private java.util.Date transactionDateFrom;

    /* Date to inclusive. */
    private java.util.Date transactionDateTo;

    /* Entry from inclusive will be returned, starting from 1. */
    private org.apache.axis.types.UnsignedInt entryFrom;

    /* Entry to inclusive. */
    private org.apache.axis.types.UnsignedInt entryTo;

    public PaymentsQuery() {
    }

    public PaymentsQuery(
           java.lang.String claimant,
           java.lang.String identifier,
           java.lang.String disposalAccount,
           java.util.Date transactionDateFrom,
           java.util.Date transactionDateTo,
           org.apache.axis.types.UnsignedInt entryFrom,
           org.apache.axis.types.UnsignedInt entryTo) {
           this.claimant = claimant;
           this.identifier = identifier;
           this.disposalAccount = disposalAccount;
           this.transactionDateFrom = transactionDateFrom;
           this.transactionDateTo = transactionDateTo;
           this.entryFrom = entryFrom;
           this.entryTo = entryTo;
    }


    /**
     * Gets the claimant value for this PaymentsQuery.
     * 
     * @return claimant   * The persion Id of the claimant.
     */
    public java.lang.String getClaimant() {
        return claimant;
    }


    /**
     * Sets the claimant value for this PaymentsQuery.
     * 
     * @param claimant   * The persion Id of the claimant.
     */
    public void setClaimant(java.lang.String claimant) {
        this.claimant = claimant;
    }


    /**
     * Gets the identifier value for this PaymentsQuery.
     * 
     * @return identifier   * The claim identifier belonging to the claimant used to filter
     * the query.
     */
    public java.lang.String getIdentifier() {
        return identifier;
    }


    /**
     * Sets the identifier value for this PaymentsQuery.
     * 
     * @param identifier   * The claim identifier belonging to the claimant used to filter
     * the query.
     */
    public void setIdentifier(java.lang.String identifier) {
        this.identifier = identifier;
    }


    /**
     * Gets the disposalAccount value for this PaymentsQuery.
     * 
     * @return disposalAccount   * The account to filter by. A convenience when many identifiers
     * are connected to the same disposal account, to query for all payments
     * connected to that account.
     */
    public java.lang.String getDisposalAccount() {
        return disposalAccount;
    }


    /**
     * Sets the disposalAccount value for this PaymentsQuery.
     * 
     * @param disposalAccount   * The account to filter by. A convenience when many identifiers
     * are connected to the same disposal account, to query for all payments
     * connected to that account.
     */
    public void setDisposalAccount(java.lang.String disposalAccount) {
        this.disposalAccount = disposalAccount;
    }


    /**
     * Gets the transactionDateFrom value for this PaymentsQuery.
     * 
     * @return transactionDateFrom   * Date from inclusive.
     */
    public java.util.Date getTransactionDateFrom() {
        return transactionDateFrom;
    }


    /**
     * Sets the transactionDateFrom value for this PaymentsQuery.
     * 
     * @param transactionDateFrom   * Date from inclusive.
     */
    public void setTransactionDateFrom(java.util.Date transactionDateFrom) {
        this.transactionDateFrom = transactionDateFrom;
    }


    /**
     * Gets the transactionDateTo value for this PaymentsQuery.
     * 
     * @return transactionDateTo   * Date to inclusive.
     */
    public java.util.Date getTransactionDateTo() {
        return transactionDateTo;
    }


    /**
     * Sets the transactionDateTo value for this PaymentsQuery.
     * 
     * @param transactionDateTo   * Date to inclusive.
     */
    public void setTransactionDateTo(java.util.Date transactionDateTo) {
        this.transactionDateTo = transactionDateTo;
    }


    /**
     * Gets the entryFrom value for this PaymentsQuery.
     * 
     * @return entryFrom   * Entry from inclusive will be returned, starting from 1.
     */
    public org.apache.axis.types.UnsignedInt getEntryFrom() {
        return entryFrom;
    }


    /**
     * Sets the entryFrom value for this PaymentsQuery.
     * 
     * @param entryFrom   * Entry from inclusive will be returned, starting from 1.
     */
    public void setEntryFrom(org.apache.axis.types.UnsignedInt entryFrom) {
        this.entryFrom = entryFrom;
    }


    /**
     * Gets the entryTo value for this PaymentsQuery.
     * 
     * @return entryTo   * Entry to inclusive.
     */
    public org.apache.axis.types.UnsignedInt getEntryTo() {
        return entryTo;
    }


    /**
     * Sets the entryTo value for this PaymentsQuery.
     * 
     * @param entryTo   * Entry to inclusive.
     */
    public void setEntryTo(org.apache.axis.types.UnsignedInt entryTo) {
        this.entryTo = entryTo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentsQuery)) return false;
        PaymentsQuery other = (PaymentsQuery) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.claimant==null && other.getClaimant()==null) || 
             (this.claimant!=null &&
              this.claimant.equals(other.getClaimant()))) &&
            ((this.identifier==null && other.getIdentifier()==null) || 
             (this.identifier!=null &&
              this.identifier.equals(other.getIdentifier()))) &&
            ((this.disposalAccount==null && other.getDisposalAccount()==null) || 
             (this.disposalAccount!=null &&
              this.disposalAccount.equals(other.getDisposalAccount()))) &&
            ((this.transactionDateFrom==null && other.getTransactionDateFrom()==null) || 
             (this.transactionDateFrom!=null &&
              this.transactionDateFrom.equals(other.getTransactionDateFrom()))) &&
            ((this.transactionDateTo==null && other.getTransactionDateTo()==null) || 
             (this.transactionDateTo!=null &&
              this.transactionDateTo.equals(other.getTransactionDateTo()))) &&
            ((this.entryFrom==null && other.getEntryFrom()==null) || 
             (this.entryFrom!=null &&
              this.entryFrom.equals(other.getEntryFrom()))) &&
            ((this.entryTo==null && other.getEntryTo()==null) || 
             (this.entryTo!=null &&
              this.entryTo.equals(other.getEntryTo())));
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
        if (getClaimant() != null) {
            _hashCode += getClaimant().hashCode();
        }
        if (getIdentifier() != null) {
            _hashCode += getIdentifier().hashCode();
        }
        if (getDisposalAccount() != null) {
            _hashCode += getDisposalAccount().hashCode();
        }
        if (getTransactionDateFrom() != null) {
            _hashCode += getTransactionDateFrom().hashCode();
        }
        if (getTransactionDateTo() != null) {
            _hashCode += getTransactionDateTo().hashCode();
        }
        if (getEntryFrom() != null) {
            _hashCode += getEntryFrom().hashCode();
        }
        if (getEntryTo() != null) {
            _hashCode += getEntryTo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentsQuery.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "PaymentsQuery"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimant");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Claimant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Identifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disposalAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DisposalAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionDateFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "TransactionDateFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionDateTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "TransactionDateTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entryFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "EntryFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entryTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "EntryTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"));
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
