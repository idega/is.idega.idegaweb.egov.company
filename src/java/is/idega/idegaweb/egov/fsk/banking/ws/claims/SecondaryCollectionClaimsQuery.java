/**
 * SecondaryCollectionClaimsQuery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.claims;


/**
 * Query on claims in secondary cellection.
 */
public class SecondaryCollectionClaimsQuery  implements java.io.Serializable {
    /* The persion Id of the claimant. */
    private java.lang.String claimant;

    /* Date from inclusive. */
    private java.util.Date collectionDateFrom;

    /* Date to inclusive. */
    private java.util.Date collectionDateTo;

    /* Status of the claims. */
    private is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimStatus status;

    /* Entry from inclusive will be returned, starting from 1. */
    private org.apache.axis.types.UnsignedInt entryFrom;

    /* Entry to inclusive. */
    private org.apache.axis.types.UnsignedInt entryTo;

    public SecondaryCollectionClaimsQuery() {
    }

    public SecondaryCollectionClaimsQuery(
           java.lang.String claimant,
           java.util.Date collectionDateFrom,
           java.util.Date collectionDateTo,
           is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimStatus status,
           org.apache.axis.types.UnsignedInt entryFrom,
           org.apache.axis.types.UnsignedInt entryTo) {
           this.claimant = claimant;
           this.collectionDateFrom = collectionDateFrom;
           this.collectionDateTo = collectionDateTo;
           this.status = status;
           this.entryFrom = entryFrom;
           this.entryTo = entryTo;
    }


    /**
     * Gets the claimant value for this SecondaryCollectionClaimsQuery.
     * 
     * @return claimant   * The persion Id of the claimant.
     */
    public java.lang.String getClaimant() {
        return claimant;
    }


    /**
     * Sets the claimant value for this SecondaryCollectionClaimsQuery.
     * 
     * @param claimant   * The persion Id of the claimant.
     */
    public void setClaimant(java.lang.String claimant) {
        this.claimant = claimant;
    }


    /**
     * Gets the collectionDateFrom value for this SecondaryCollectionClaimsQuery.
     * 
     * @return collectionDateFrom   * Date from inclusive.
     */
    public java.util.Date getCollectionDateFrom() {
        return collectionDateFrom;
    }


    /**
     * Sets the collectionDateFrom value for this SecondaryCollectionClaimsQuery.
     * 
     * @param collectionDateFrom   * Date from inclusive.
     */
    public void setCollectionDateFrom(java.util.Date collectionDateFrom) {
        this.collectionDateFrom = collectionDateFrom;
    }


    /**
     * Gets the collectionDateTo value for this SecondaryCollectionClaimsQuery.
     * 
     * @return collectionDateTo   * Date to inclusive.
     */
    public java.util.Date getCollectionDateTo() {
        return collectionDateTo;
    }


    /**
     * Sets the collectionDateTo value for this SecondaryCollectionClaimsQuery.
     * 
     * @param collectionDateTo   * Date to inclusive.
     */
    public void setCollectionDateTo(java.util.Date collectionDateTo) {
        this.collectionDateTo = collectionDateTo;
    }


    /**
     * Gets the status value for this SecondaryCollectionClaimsQuery.
     * 
     * @return status   * Status of the claims.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this SecondaryCollectionClaimsQuery.
     * 
     * @param status   * Status of the claims.
     */
    public void setStatus(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimStatus status) {
        this.status = status;
    }


    /**
     * Gets the entryFrom value for this SecondaryCollectionClaimsQuery.
     * 
     * @return entryFrom   * Entry from inclusive will be returned, starting from 1.
     */
    public org.apache.axis.types.UnsignedInt getEntryFrom() {
        return entryFrom;
    }


    /**
     * Sets the entryFrom value for this SecondaryCollectionClaimsQuery.
     * 
     * @param entryFrom   * Entry from inclusive will be returned, starting from 1.
     */
    public void setEntryFrom(org.apache.axis.types.UnsignedInt entryFrom) {
        this.entryFrom = entryFrom;
    }


    /**
     * Gets the entryTo value for this SecondaryCollectionClaimsQuery.
     * 
     * @return entryTo   * Entry to inclusive.
     */
    public org.apache.axis.types.UnsignedInt getEntryTo() {
        return entryTo;
    }


    /**
     * Sets the entryTo value for this SecondaryCollectionClaimsQuery.
     * 
     * @param entryTo   * Entry to inclusive.
     */
    public void setEntryTo(org.apache.axis.types.UnsignedInt entryTo) {
        this.entryTo = entryTo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SecondaryCollectionClaimsQuery)) return false;
        SecondaryCollectionClaimsQuery other = (SecondaryCollectionClaimsQuery) obj;
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
            ((this.collectionDateFrom==null && other.getCollectionDateFrom()==null) || 
             (this.collectionDateFrom!=null &&
              this.collectionDateFrom.equals(other.getCollectionDateFrom()))) &&
            ((this.collectionDateTo==null && other.getCollectionDateTo()==null) || 
             (this.collectionDateTo!=null &&
              this.collectionDateTo.equals(other.getCollectionDateTo()))) &&
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
        if (getCollectionDateFrom() != null) {
            _hashCode += getCollectionDateFrom().hashCode();
        }
        if (getCollectionDateTo() != null) {
            _hashCode += getCollectionDateTo().hashCode();
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
        new org.apache.axis.description.TypeDesc(SecondaryCollectionClaimsQuery.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "SecondaryCollectionClaimsQuery"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimant");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Claimant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionDateFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CollectionDateFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectionDateTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CollectionDateTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
