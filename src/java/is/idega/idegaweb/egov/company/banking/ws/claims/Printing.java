/**
 * Printing.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;


/**
 * Contains information used for displaying and printing the claim.
 * Changing this info on a claim that has already been created will not
 * result in it being printed again.
 */
public class Printing  implements java.io.Serializable {
    /* Alternate address for claimant if address from the National
     * Register should not be used. */
    private is.idega.idegaweb.egov.company.banking.ws.common.Address claimantAddress;

    /* Alternate address for payor if address from the National Register
     * should not be used. */
    private is.idega.idegaweb.egov.company.banking.ws.common.Address payorAddress;

    /* Details of claim. */
    private is.idega.idegaweb.egov.company.banking.ws.claims.ItemRow[] itemRows;

    /* Comments that are displayed on the lower half of the payment
     * slip. */
    private java.lang.String[] comments;

    public Printing() {
    }

    public Printing(
           is.idega.idegaweb.egov.company.banking.ws.common.Address claimantAddress,
           is.idega.idegaweb.egov.company.banking.ws.common.Address payorAddress,
           is.idega.idegaweb.egov.company.banking.ws.claims.ItemRow[] itemRows,
           java.lang.String[] comments) {
           this.claimantAddress = claimantAddress;
           this.payorAddress = payorAddress;
           this.itemRows = itemRows;
           this.comments = comments;
    }


    /**
     * Gets the claimantAddress value for this Printing.
     * 
     * @return claimantAddress   * Alternate address for claimant if address from the National
     * Register should not be used.
     */
    public is.idega.idegaweb.egov.company.banking.ws.common.Address getClaimantAddress() {
        return claimantAddress;
    }


    /**
     * Sets the claimantAddress value for this Printing.
     * 
     * @param claimantAddress   * Alternate address for claimant if address from the National
     * Register should not be used.
     */
    public void setClaimantAddress(is.idega.idegaweb.egov.company.banking.ws.common.Address claimantAddress) {
        this.claimantAddress = claimantAddress;
    }


    /**
     * Gets the payorAddress value for this Printing.
     * 
     * @return payorAddress   * Alternate address for payor if address from the National Register
     * should not be used.
     */
    public is.idega.idegaweb.egov.company.banking.ws.common.Address getPayorAddress() {
        return payorAddress;
    }


    /**
     * Sets the payorAddress value for this Printing.
     * 
     * @param payorAddress   * Alternate address for payor if address from the National Register
     * should not be used.
     */
    public void setPayorAddress(is.idega.idegaweb.egov.company.banking.ws.common.Address payorAddress) {
        this.payorAddress = payorAddress;
    }


    /**
     * Gets the itemRows value for this Printing.
     * 
     * @return itemRows   * Details of claim.
     */
    public is.idega.idegaweb.egov.company.banking.ws.claims.ItemRow[] getItemRows() {
        return itemRows;
    }


    /**
     * Sets the itemRows value for this Printing.
     * 
     * @param itemRows   * Details of claim.
     */
    public void setItemRows(is.idega.idegaweb.egov.company.banking.ws.claims.ItemRow[] itemRows) {
        this.itemRows = itemRows;
    }

    public is.idega.idegaweb.egov.company.banking.ws.claims.ItemRow getItemRows(int i) {
        return this.itemRows[i];
    }

    public void setItemRows(int i, is.idega.idegaweb.egov.company.banking.ws.claims.ItemRow _value) {
        this.itemRows[i] = _value;
    }


    /**
     * Gets the comments value for this Printing.
     * 
     * @return comments   * Comments that are displayed on the lower half of the payment
     * slip.
     */
    public java.lang.String[] getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this Printing.
     * 
     * @param comments   * Comments that are displayed on the lower half of the payment
     * slip.
     */
    public void setComments(java.lang.String[] comments) {
        this.comments = comments;
    }

    public java.lang.String getComments(int i) {
        return this.comments[i];
    }

    public void setComments(int i, java.lang.String _value) {
        this.comments[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Printing)) return false;
        Printing other = (Printing) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.claimantAddress==null && other.getClaimantAddress()==null) || 
             (this.claimantAddress!=null &&
              this.claimantAddress.equals(other.getClaimantAddress()))) &&
            ((this.payorAddress==null && other.getPayorAddress()==null) || 
             (this.payorAddress!=null &&
              this.payorAddress.equals(other.getPayorAddress()))) &&
            ((this.itemRows==null && other.getItemRows()==null) || 
             (this.itemRows!=null &&
              java.util.Arrays.equals(this.itemRows, other.getItemRows()))) &&
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              java.util.Arrays.equals(this.comments, other.getComments())));
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
        if (getClaimantAddress() != null) {
            _hashCode += getClaimantAddress().hashCode();
        }
        if (getPayorAddress() != null) {
            _hashCode += getPayorAddress().hashCode();
        }
        if (getItemRows() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItemRows());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItemRows(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getComments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Printing.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Printing"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimantAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimantAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payorAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "PayorAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemRows");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ItemRows"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ItemRow"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Comment"));
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
