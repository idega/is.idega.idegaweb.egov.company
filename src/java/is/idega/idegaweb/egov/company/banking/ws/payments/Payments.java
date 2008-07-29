/**
 * Payments.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.payments;


/**
 * Represents one or more payments to be processed.
 */
public class Payments  implements java.io.Serializable {
    /* Information about the debit account */
    private is.idega.idegaweb.egov.company.banking.ws.payments.PaymentOut out;

    /* Information about the accounts funds will be transferred to.
     * Max 500 records */
    private is.idega.idegaweb.egov.company.banking.ws.payments.PaymentIn[] in;

    /* Date when the payments will be executed. */
    private java.util.Date dateOfForwardPayment;

    /* A name provided by and for the convenience of the sender */
    private java.lang.String nameOfBatch;

    private boolean rollbackOnError;  // attribute

    private boolean isOneToMany;  // attribute

    public Payments() {
    }

    public Payments(
           is.idega.idegaweb.egov.company.banking.ws.payments.PaymentOut out,
           is.idega.idegaweb.egov.company.banking.ws.payments.PaymentIn[] in,
           java.util.Date dateOfForwardPayment,
           java.lang.String nameOfBatch,
           boolean rollbackOnError,
           boolean isOneToMany) {
           this.out = out;
           this.in = in;
           this.dateOfForwardPayment = dateOfForwardPayment;
           this.nameOfBatch = nameOfBatch;
           this.rollbackOnError = rollbackOnError;
           this.isOneToMany = isOneToMany;
    }


    /**
     * Gets the out value for this Payments.
     * 
     * @return out   * Information about the debit account
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.PaymentOut getOut() {
        return out;
    }


    /**
     * Sets the out value for this Payments.
     * 
     * @param out   * Information about the debit account
     */
    public void setOut(is.idega.idegaweb.egov.company.banking.ws.payments.PaymentOut out) {
        this.out = out;
    }


    /**
     * Gets the in value for this Payments.
     * 
     * @return in   * Information about the accounts funds will be transferred to.
     * Max 500 records
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.PaymentIn[] getIn() {
        return in;
    }


    /**
     * Sets the in value for this Payments.
     * 
     * @param in   * Information about the accounts funds will be transferred to.
     * Max 500 records
     */
    public void setIn(is.idega.idegaweb.egov.company.banking.ws.payments.PaymentIn[] in) {
        this.in = in;
    }

    public is.idega.idegaweb.egov.company.banking.ws.payments.PaymentIn getIn(int i) {
        return this.in[i];
    }

    public void setIn(int i, is.idega.idegaweb.egov.company.banking.ws.payments.PaymentIn _value) {
        this.in[i] = _value;
    }


    /**
     * Gets the dateOfForwardPayment value for this Payments.
     * 
     * @return dateOfForwardPayment   * Date when the payments will be executed.
     */
    public java.util.Date getDateOfForwardPayment() {
        return dateOfForwardPayment;
    }


    /**
     * Sets the dateOfForwardPayment value for this Payments.
     * 
     * @param dateOfForwardPayment   * Date when the payments will be executed.
     */
    public void setDateOfForwardPayment(java.util.Date dateOfForwardPayment) {
        this.dateOfForwardPayment = dateOfForwardPayment;
    }


    /**
     * Gets the nameOfBatch value for this Payments.
     * 
     * @return nameOfBatch   * A name provided by and for the convenience of the sender
     */
    public java.lang.String getNameOfBatch() {
        return nameOfBatch;
    }


    /**
     * Sets the nameOfBatch value for this Payments.
     * 
     * @param nameOfBatch   * A name provided by and for the convenience of the sender
     */
    public void setNameOfBatch(java.lang.String nameOfBatch) {
        this.nameOfBatch = nameOfBatch;
    }


    /**
     * Gets the rollbackOnError value for this Payments.
     * 
     * @return rollbackOnError
     */
    public boolean isRollbackOnError() {
        return rollbackOnError;
    }


    /**
     * Sets the rollbackOnError value for this Payments.
     * 
     * @param rollbackOnError
     */
    public void setRollbackOnError(boolean rollbackOnError) {
        this.rollbackOnError = rollbackOnError;
    }


    /**
     * Gets the isOneToMany value for this Payments.
     * 
     * @return isOneToMany
     */
    public boolean isIsOneToMany() {
        return isOneToMany;
    }


    /**
     * Sets the isOneToMany value for this Payments.
     * 
     * @param isOneToMany
     */
    public void setIsOneToMany(boolean isOneToMany) {
        this.isOneToMany = isOneToMany;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Payments)) return false;
        Payments other = (Payments) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.out==null && other.getOut()==null) || 
             (this.out!=null &&
              this.out.equals(other.getOut()))) &&
            ((this.in==null && other.getIn()==null) || 
             (this.in!=null &&
              java.util.Arrays.equals(this.in, other.getIn()))) &&
            ((this.dateOfForwardPayment==null && other.getDateOfForwardPayment()==null) || 
             (this.dateOfForwardPayment!=null &&
              this.dateOfForwardPayment.equals(other.getDateOfForwardPayment()))) &&
            ((this.nameOfBatch==null && other.getNameOfBatch()==null) || 
             (this.nameOfBatch!=null &&
              this.nameOfBatch.equals(other.getNameOfBatch()))) &&
            this.rollbackOnError == other.isRollbackOnError() &&
            this.isOneToMany == other.isIsOneToMany();
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
        if (getOut() != null) {
            _hashCode += getOut().hashCode();
        }
        if (getIn() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIn());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIn(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDateOfForwardPayment() != null) {
            _hashCode += getDateOfForwardPayment().hashCode();
        }
        if (getNameOfBatch() != null) {
            _hashCode += getNameOfBatch().hashCode();
        }
        _hashCode += (isRollbackOnError() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isIsOneToMany() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Payments.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Payments"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("rollbackOnError");
        attrField.setXmlName(new javax.xml.namespace.QName("", "RollbackOnError"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("isOneToMany");
        attrField.setXmlName(new javax.xml.namespace.QName("", "IsOneToMany"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("out");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Out"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentOut"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("in");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "In"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentIn"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateOfForwardPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "DateOfForwardPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameOfBatch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "NameOfBatch"));
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
