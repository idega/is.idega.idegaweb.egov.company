/**
 * ClaimOperationResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.fsk.banking.ws.claims;


/**
 * The results returned from operations on claims. A list of claims
 * and/or errors is returned. Information about printing and direct payment
 * is only relevant to creation of claims.
 */
public class ClaimOperationResult  implements java.io.Serializable {
    /* The unique identifier of the operation. If an error has occured,
     * this can return an empty Id. */
    private java.lang.String ID;

    /* Status of the batch of claims . */
    private is.idega.idegaweb.egov.fsk.banking.ws.claims.BatchStatus status;

    /* List of claims whose operation was a success. */
    private is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimResult[] success;

    /* List of claims with errors. */
    private is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimError[] errors;

    public ClaimOperationResult() {
    }

    public ClaimOperationResult(
           java.lang.String ID,
           is.idega.idegaweb.egov.fsk.banking.ws.claims.BatchStatus status,
           is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimResult[] success,
           is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimError[] errors) {
           this.ID = ID;
           this.status = status;
           this.success = success;
           this.errors = errors;
    }


    /**
     * Gets the ID value for this ClaimOperationResult.
     * 
     * @return ID   * The unique identifier of the operation. If an error has occured,
     * this can return an empty Id.
     */
    public java.lang.String getID() {
        return ID;
    }


    /**
     * Sets the ID value for this ClaimOperationResult.
     * 
     * @param ID   * The unique identifier of the operation. If an error has occured,
     * this can return an empty Id.
     */
    public void setID(java.lang.String ID) {
        this.ID = ID;
    }


    /**
     * Gets the status value for this ClaimOperationResult.
     * 
     * @return status   * Status of the batch of claims .
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.BatchStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ClaimOperationResult.
     * 
     * @param status   * Status of the batch of claims .
     */
    public void setStatus(is.idega.idegaweb.egov.fsk.banking.ws.claims.BatchStatus status) {
        this.status = status;
    }


    /**
     * Gets the success value for this ClaimOperationResult.
     * 
     * @return success   * List of claims whose operation was a success.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimResult[] getSuccess() {
        return success;
    }


    /**
     * Sets the success value for this ClaimOperationResult.
     * 
     * @param success   * List of claims whose operation was a success.
     */
    public void setSuccess(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimResult[] success) {
        this.success = success;
    }

    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimResult getSuccess(int i) {
        return this.success[i];
    }

    public void setSuccess(int i, is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimResult _value) {
        this.success[i] = _value;
    }


    /**
     * Gets the errors value for this ClaimOperationResult.
     * 
     * @return errors   * List of claims with errors.
     */
    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimError[] getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this ClaimOperationResult.
     * 
     * @param errors   * List of claims with errors.
     */
    public void setErrors(is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimError[] errors) {
        this.errors = errors;
    }

    public is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimError getErrors(int i) {
        return this.errors[i];
    }

    public void setErrors(int i, is.idega.idegaweb.egov.fsk.banking.ws.claims.ClaimError _value) {
        this.errors[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClaimOperationResult)) return false;
        ClaimOperationResult other = (ClaimOperationResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ID==null && other.getID()==null) || 
             (this.ID!=null &&
              this.ID.equals(other.getID()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.success==null && other.getSuccess()==null) || 
             (this.success!=null &&
              java.util.Arrays.equals(this.success, other.getSuccess()))) &&
            ((this.errors==null && other.getErrors()==null) || 
             (this.errors!=null &&
              java.util.Arrays.equals(this.errors, other.getErrors())));
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
        if (getID() != null) {
            _hashCode += getID().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSuccess() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSuccess());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSuccess(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrors(), i);
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
        new org.apache.axis.description.TypeDesc(ClaimOperationResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimOperationResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "BatchStatus"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("success");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Success"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
