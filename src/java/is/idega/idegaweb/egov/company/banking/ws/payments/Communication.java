/**
 * Communication.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.payments;


/**
 * Wrapper for different types for receipts
 */
public class Communication  implements java.io.Serializable {
    /* Postal mail */
    private is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationPostalMail postalMail;

    /* Email */
    private is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationEMail[] EMail;

    /* SMS message (Short Message Service, a service for sending messages
     * to GSM mobilephones) */
    private is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationSMS[] SMS;

    public Communication() {
    }

    public Communication(
           is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationPostalMail postalMail,
           is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationEMail[] EMail,
           is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationSMS[] SMS) {
           this.postalMail = postalMail;
           this.EMail = EMail;
           this.SMS = SMS;
    }


    /**
     * Gets the postalMail value for this Communication.
     * 
     * @return postalMail   * Postal mail
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationPostalMail getPostalMail() {
        return postalMail;
    }


    /**
     * Sets the postalMail value for this Communication.
     * 
     * @param postalMail   * Postal mail
     */
    public void setPostalMail(is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationPostalMail postalMail) {
        this.postalMail = postalMail;
    }


    /**
     * Gets the EMail value for this Communication.
     * 
     * @return EMail   * Email
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationEMail[] getEMail() {
        return EMail;
    }


    /**
     * Sets the EMail value for this Communication.
     * 
     * @param EMail   * Email
     */
    public void setEMail(is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationEMail[] EMail) {
        this.EMail = EMail;
    }

    public is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationEMail getEMail(int i) {
        return this.EMail[i];
    }

    public void setEMail(int i, is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationEMail _value) {
        this.EMail[i] = _value;
    }


    /**
     * Gets the SMS value for this Communication.
     * 
     * @return SMS   * SMS message (Short Message Service, a service for sending messages
     * to GSM mobilephones)
     */
    public is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationSMS[] getSMS() {
        return SMS;
    }


    /**
     * Sets the SMS value for this Communication.
     * 
     * @param SMS   * SMS message (Short Message Service, a service for sending messages
     * to GSM mobilephones)
     */
    public void setSMS(is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationSMS[] SMS) {
        this.SMS = SMS;
    }

    public is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationSMS getSMS(int i) {
        return this.SMS[i];
    }

    public void setSMS(int i, is.idega.idegaweb.egov.company.banking.ws.payments.CommunicationSMS _value) {
        this.SMS[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Communication)) return false;
        Communication other = (Communication) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.postalMail==null && other.getPostalMail()==null) || 
             (this.postalMail!=null &&
              this.postalMail.equals(other.getPostalMail()))) &&
            ((this.EMail==null && other.getEMail()==null) || 
             (this.EMail!=null &&
              java.util.Arrays.equals(this.EMail, other.getEMail()))) &&
            ((this.SMS==null && other.getSMS()==null) || 
             (this.SMS!=null &&
              java.util.Arrays.equals(this.SMS, other.getSMS())));
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
        if (getPostalMail() != null) {
            _hashCode += getPostalMail().hashCode();
        }
        if (getEMail() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEMail());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEMail(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSMS() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSMS());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSMS(), i);
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
        new org.apache.axis.description.TypeDesc(Communication.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "Communication"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalMail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "PostalMail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", ">Communication>PostalMail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EMail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "EMail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", ">Communication>EMail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SMS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", "SMS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/PaymentTypes", ">Communication>SMS"));
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
