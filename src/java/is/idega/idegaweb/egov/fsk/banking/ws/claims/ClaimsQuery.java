/**
 * ClaimsQuery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.claims;


/**
 * Query on claims.
 */
public class ClaimsQuery  implements java.io.Serializable {
    /* The person identifier of the claimant whose data is being queried.
     * If empty, data will be returned for all person ids linked to the authenticated
     * user. */
    private java.lang.String claimant;

    /* The unique identifier of the claimant used to filter the query.
     * If empty, data will be returned for all identifiers. */
    private java.lang.String identifier;

    /* Time period used to filter result set. */
    private is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimsQueryDateSpan period;

    /* Person Id of the payor. */
    private java.lang.String payor;

    /* Status of the claims. */
    private is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimStatus status;

    /* Entry from inclusive will be returned, starting from 1. */
    private org.apache.axis.types.UnsignedInt entryFrom;

    /* Entry to inclusive. */
    private org.apache.axis.types.UnsignedInt entryTo;

    public ClaimsQuery() {
    }

    public ClaimsQuery(
           java.lang.String claimant,
           java.lang.String identifier,
           is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimsQueryDateSpan period,
           java.lang.String payor,
           is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimStatus status,
           org.apache.axis.types.UnsignedInt entryFrom,
           org.apache.axis.types.UnsignedInt entryTo) {
           this.claimant = claimant;
           this.identifier = identifier;
           this.period = period;
           this.payor = payor;
           this.status = status;
           this.entryFrom = entryFrom;
           this.entryTo = entryTo;
    }


    /**
     * Gets the claimant value for this ClaimsQuery.
     * 
     * @return claimant   * The person identifier of the claimant whose data is being queried.
     * If empty, data will be returned for all person ids linked to the authenticated
     * user.
     */
    public java.lang.String getClaimant() {
        return claimant;
    }


    /**
     * Sets the claimant value for this ClaimsQuery.
     * 
     * @param claimant   * The person identifier of the claimant whose data is being queried.
     * If empty, data will be returned for all person ids linked to the authenticated
     * user.
     */
    public void setClaimant(java.lang.String claimant) {
        this.claimant = claimant;
    }


    /**
     * Gets the identifier value for this ClaimsQuery.
     * 
     * @return identifier   * The unique identifier of the claimant used to filter the query.
     * If empty, data will be returned for all identifiers.
     */
    public java.lang.String getIdentifier() {
        return identifier;
    }


    /**
     * Sets the identifier value for this ClaimsQuery.
     * 
     * @param identifier   * The unique identifier of the claimant used to filter the query.
     * If empty, data will be returned for all identifiers.
     */
    public void setIdentifier(java.lang.String identifier) {
        this.identifier = identifier;
    }


    /**
     * Gets the period value for this ClaimsQuery.
     * 
     * @return period   * Time period used to filter result set.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimsQueryDateSpan getPeriod() {
        return period;
    }


    /**
     * Sets the period value for this ClaimsQuery.
     * 
     * @param period   * Time period used to filter result set.
     */
    public void setPeriod(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimsQueryDateSpan period) {
        this.period = period;
    }


    /**
     * Gets the payor value for this ClaimsQuery.
     * 
     * @return payor   * Person Id of the payor.
     */
    public java.lang.String getPayor() {
        return payor;
    }


    /**
     * Sets the payor value for this ClaimsQuery.
     * 
     * @param payor   * Person Id of the payor.
     */
    public void setPayor(java.lang.String payor) {
        this.payor = payor;
    }


    /**
     * Gets the status value for this ClaimsQuery.
     * 
     * @return status   * Status of the claims.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ClaimsQuery.
     * 
     * @param status   * Status of the claims.
     */
    public void setStatus(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimStatus status) {
        this.status = status;
    }


    /**
     * Gets the entryFrom value for this ClaimsQuery.
     * 
     * @return entryFrom   * Entry from inclusive will be returned, starting from 1.
     */
    public org.apache.axis.types.UnsignedInt getEntryFrom() {
        return entryFrom;
    }


    /**
     * Sets the entryFrom value for this ClaimsQuery.
     * 
     * @param entryFrom   * Entry from inclusive will be returned, starting from 1.
     */
    public void setEntryFrom(org.apache.axis.types.UnsignedInt entryFrom) {
        this.entryFrom = entryFrom;
    }


    /**
     * Gets the entryTo value for this ClaimsQuery.
     * 
     * @return entryTo   * Entry to inclusive.
     */
    public org.apache.axis.types.UnsignedInt getEntryTo() {
        return entryTo;
    }


    /**
     * Sets the entryTo value for this ClaimsQuery.
     * 
     * @param entryTo   * Entry to inclusive.
     */
    public void setEntryTo(org.apache.axis.types.UnsignedInt entryTo) {
        this.entryTo = entryTo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClaimsQuery)) return false;
        ClaimsQuery other = (ClaimsQuery) obj;
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
            ((this.period==null && other.getPeriod()==null) || 
             (this.period!=null &&
              this.period.equals(other.getPeriod()))) &&
            ((this.payor==null && other.getPayor()==null) || 
             (this.payor!=null &&
              this.payor.equals(other.getPayor()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
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
        if (getPeriod() != null) {
            _hashCode += getPeriod().hashCode();
        }
        if (getPayor() != null) {
            _hashCode += getPayor().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
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
        new org.apache.axis.description.TypeDesc(ClaimsQuery.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimsQuery"));
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
        elemField.setFieldName("period");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Period"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimsQueryDateSpan"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Payor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimStatus"));
        elemField.setMinOccurs(0);
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
