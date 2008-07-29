/**
 * PaymentsResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.payments;


/**
 * Shows the processing results of a payment batch
 */
public class PaymentsResult  implements java.io.Serializable {
    /* ID number for the payment batch. */
    private java.lang.String ID;

    /* The status of the payment batch */
    private is.idega.idegaweb.egov.company.banking.ws.payments.BatchStatus status;

    /* Lists the payments that where executed with success. */
    private is.idega.idegaweb.egov.company.banking.ws.payments.PaymentResultDetails[] success;

    /* The payments that resulted in errors. */
    private is.idega.idegaweb.egov.company.banking.ws.payments.PaymentError[] errors;

    /* Returns the date when a payment will be executed, used for
     * forward payments. */
    private java.util.Calendar dateOfPayment;

    public PaymentsResult() {
    }

    public PaymentsResult(
           java.lang.String ID,
           is.idega.idegaweb.egov.company.banking.ws.payments.BatchStatus status,
           is.idega.idegaweb.egov.company.banking.ws.payments.PaymentResultDetails[] success,
           is.idega.idegaweb.egov.company.banking.ws.payments.PaymentError[] errors,
           java.util.Calendar dateOfPayment) {
           this.ID = ID;
           this.status = status;
           this.success = success;
           this.errors = errors;
           this.dateOfPayment = dateOfPayment;
    }


    /**
     * Gets the ID value for this PaymentsResult.
     * 
     * @return ID   * ID number for the payment batch.
     */
    public java.lang.String getID() {
        return ID;
    }


    /**
     * Sets the ID value for this PaymentsResult.
     * 
     * @param ID   * ID number for the payment batch.
     */
    public void setID(java.lang.String ID) {
        this.ID = ID;
    }


    /**
     * Gets the status value for this PaymentsResult.
     * 
     * @return status   * The status of the payment batch
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.BatchStatus getStatus() {
        return status;
    }


    /**
     * Sets the status value for this PaymentsResult.
     * 
     * @param status   * The status of the payment batch
     */
    public void setStatus(is.idega.idegaweb.egov.company.banking.ws.payments.BatchStatus status) {
        this.status = status;
    }


    /**
     * Gets the success value for this PaymentsResult.
     * 
     * @return success   * Lists the payments that where executed with success.
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.PaymentResultDetails[] getSuccess() {
        return success;
    }


    /**
     * Sets the success value for this PaymentsResult.
     * 
     * @param success   * Lists the payments that where executed with success.
     */
    public void setSuccess(is.idega.idegaweb.egov.company.banking.ws.payments.PaymentResultDetails[] success) {
        this.success = success;
    }

    public is.idega.idegaweb.egov.company.banking.ws.payments.PaymentResultDetails getSuccess(int i) {
        return this.success[i];
    }

    public void setSuccess(int i, is.idega.idegaweb.egov.company.banking.ws.payments.PaymentResultDetails _value) {
        this.success[i] = _value;
    }


    /**
     * Gets the errors value for this PaymentsResult.
     * 
     * @return errors   * The payments that resulted in errors.
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.PaymentError[] getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this PaymentsResult.
     * 
     * @param errors   * The payments that resulted in errors.
     */
    public void setErrors(is.idega.idegaweb.egov.company.banking.ws.payments.PaymentError[] errors) {
        this.errors = errors;
    }

    public is.idega.idegaweb.egov.company.banking.ws.payments.PaymentError getErrors(int i) {
        return this.errors[i];
    }

    public void setErrors(int i, is.idega.idegaweb.egov.company.banking.ws.payments.PaymentError _value) {
        this.errors[i] = _value;
    }


    /**
     * Gets the dateOfPayment value for this PaymentsResult.
     * 
     * @return dateOfPayment   * Returns the date when a payment will be executed, used for
     * forward payments.
     */
    public java.util.Calendar getDateOfPayment() {
        return dateOfPayment;
    }


    /**
     * Sets the dateOfPayment value for this PaymentsResult.
     * 
     * @param dateOfPayment   * Returns the date when a payment will be executed, used for
     * forward payments.
     */
    public void setDateOfPayment(java.util.Calendar dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaymentsResult)) return false;
        PaymentsResult other = (PaymentsResult) obj;
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
              java.util.Arrays.equals(this.errors, other.getErrors()))) &&
            ((this.dateOfPayment==null && other.getDateOfPayment()==null) || 
             (this.dateOfPayment!=null &&
              this.dateOfPayment.equals(other.getDateOfPayment())));
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
        if (getDateOfPayment() != null) {
            _hashCode += getDateOfPayment().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaymentsResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentsResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "BatchStatus"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("success");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Success"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentResultDetails"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PaymentError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateOfPayment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "DateOfPayment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
