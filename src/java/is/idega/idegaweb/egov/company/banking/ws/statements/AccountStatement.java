/**
 * AccountStatement.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.statements;

public class AccountStatement  implements java.io.Serializable {
    /* The account information is requested for. */
    private java.lang.String account;

    private java.util.Date dateFrom;

    private java.util.Date dateTo;

    /* Used to limit the records in the response to a certain range. */
    private java.lang.Long recordFrom;

    /* Used to limit the records in the response to a certain range. */
    private java.lang.Long recordTo;

    public AccountStatement() {
    }

    public AccountStatement(
           java.lang.String account,
           java.util.Date dateFrom,
           java.util.Date dateTo,
           java.lang.Long recordFrom,
           java.lang.Long recordTo) {
           this.account = account;
           this.dateFrom = dateFrom;
           this.dateTo = dateTo;
           this.recordFrom = recordFrom;
           this.recordTo = recordTo;
    }


    /**
     * Gets the account value for this AccountStatement.
     * 
     * @return account   * The account information is requested for.
     */
    public java.lang.String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this AccountStatement.
     * 
     * @param account   * The account information is requested for.
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }


    /**
     * Gets the dateFrom value for this AccountStatement.
     * 
     * @return dateFrom
     */
    public java.util.Date getDateFrom() {
        return dateFrom;
    }


    /**
     * Sets the dateFrom value for this AccountStatement.
     * 
     * @param dateFrom
     */
    public void setDateFrom(java.util.Date dateFrom) {
        this.dateFrom = dateFrom;
    }


    /**
     * Gets the dateTo value for this AccountStatement.
     * 
     * @return dateTo
     */
    public java.util.Date getDateTo() {
        return dateTo;
    }


    /**
     * Sets the dateTo value for this AccountStatement.
     * 
     * @param dateTo
     */
    public void setDateTo(java.util.Date dateTo) {
        this.dateTo = dateTo;
    }


    /**
     * Gets the recordFrom value for this AccountStatement.
     * 
     * @return recordFrom   * Used to limit the records in the response to a certain range.
     */
    public java.lang.Long getRecordFrom() {
        return recordFrom;
    }


    /**
     * Sets the recordFrom value for this AccountStatement.
     * 
     * @param recordFrom   * Used to limit the records in the response to a certain range.
     */
    public void setRecordFrom(java.lang.Long recordFrom) {
        this.recordFrom = recordFrom;
    }


    /**
     * Gets the recordTo value for this AccountStatement.
     * 
     * @return recordTo   * Used to limit the records in the response to a certain range.
     */
    public java.lang.Long getRecordTo() {
        return recordTo;
    }


    /**
     * Sets the recordTo value for this AccountStatement.
     * 
     * @param recordTo   * Used to limit the records in the response to a certain range.
     */
    public void setRecordTo(java.lang.Long recordTo) {
        this.recordTo = recordTo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AccountStatement)) return false;
        AccountStatement other = (AccountStatement) obj;
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
            ((this.dateFrom==null && other.getDateFrom()==null) || 
             (this.dateFrom!=null &&
              this.dateFrom.equals(other.getDateFrom()))) &&
            ((this.dateTo==null && other.getDateTo()==null) || 
             (this.dateTo!=null &&
              this.dateTo.equals(other.getDateTo()))) &&
            ((this.recordFrom==null && other.getRecordFrom()==null) || 
             (this.recordFrom!=null &&
              this.recordFrom.equals(other.getRecordFrom()))) &&
            ((this.recordTo==null && other.getRecordTo()==null) || 
             (this.recordTo!=null &&
              this.recordTo.equals(other.getRecordTo())));
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
        if (getDateFrom() != null) {
            _hashCode += getDateFrom().hashCode();
        }
        if (getDateTo() != null) {
            _hashCode += getDateTo().hashCode();
        }
        if (getRecordFrom() != null) {
            _hashCode += getRecordFrom().hashCode();
        }
        if (getRecordTo() != null) {
            _hashCode += getRecordTo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AccountStatement.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "AccountStatement"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "DateFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "DateTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "RecordFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "RecordTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
