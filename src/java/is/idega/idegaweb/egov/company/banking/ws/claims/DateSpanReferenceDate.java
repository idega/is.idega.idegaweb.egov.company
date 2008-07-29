/**
 * DateSpanReferenceDate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.claims;

public class DateSpanReferenceDate implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DateSpanReferenceDate(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _DueDate = "DueDate";
    public static final java.lang.String _FinalDueDate = "FinalDueDate";
    public static final java.lang.String _CancellationDate = "CancellationDate";
    public static final java.lang.String _CreationDate = "CreationDate";
    public static final DateSpanReferenceDate DueDate = new DateSpanReferenceDate(_DueDate);
    public static final DateSpanReferenceDate FinalDueDate = new DateSpanReferenceDate(_FinalDueDate);
    public static final DateSpanReferenceDate CancellationDate = new DateSpanReferenceDate(_CancellationDate);
    public static final DateSpanReferenceDate CreationDate = new DateSpanReferenceDate(_CreationDate);
    public java.lang.String getValue() { return _value_;}
    public static DateSpanReferenceDate fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DateSpanReferenceDate enumeration = (DateSpanReferenceDate)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DateSpanReferenceDate fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DateSpanReferenceDate.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DateSpanReferenceDate"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
