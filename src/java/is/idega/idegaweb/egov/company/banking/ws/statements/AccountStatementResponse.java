/**
 * AccountStatementResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.statements;

public class AccountStatementResponse  implements java.io.Serializable {
    /* The ID of the account. */
    private java.lang.String account;

    /* The currency of the account. */
    private java.lang.String currency;

    /* Overdraft permission. */
    private java.math.BigDecimal overdraft;

    /* The balance of the account. */
    private java.math.BigDecimal balance;

    /* The amount available to the account holder. */
    private java.math.BigDecimal availableAmount;

    /* The status of the account (open/closed). */
    private is.idega.idegaweb.egov.company.banking.ws.common.Status status;

    /* The total amount of transactions waiting that exceeded amount
     * available. */
    private java.math.BigDecimal totalAmountWaiting;

    /* IBAN Number */
    private java.lang.String IBAN;

    /* PersonID of the account owner. */
    private java.lang.String accountOwnerID;

    /* Name set by account owner */
    private java.lang.String customAccountName;

    /* Extra account information */
    private java.lang.String accountInformation;

    private is.idega.idegaweb.egov.company.banking.ws.statements.AccountTransaction[] transactions;

    public AccountStatementResponse() {
    }

    public AccountStatementResponse(
           java.lang.String account,
           java.lang.String currency,
           java.math.BigDecimal overdraft,
           java.math.BigDecimal balance,
           java.math.BigDecimal availableAmount,
           is.idega.idegaweb.egov.company.banking.ws.common.Status status,
           java.math.BigDecimal totalAmountWaiting,
           java.lang.String IBAN,
           java.lang.String accountOwnerID,
           java.lang.String customAccountName,
           java.lang.String accountInformation,
           is.idega.idegaweb.egov.company.banking.ws.statements.AccountTransaction[] transactions) {
           this.account = account;
           this.currency = currency;
           this.overdraft = overdraft;
           this.balance = balance;
           this.availableAmount = availableAmount;
           this.status = status;
           this.totalAmountWaiting = totalAmountWaiting;
           this.IBAN = IBAN;
           this.accountOwnerID = accountOwnerID;
           this.customAccountName = customAccountName;
           this.accountInformation = accountInformation;
           this.transactions = transactions;
    }


    /**
     * Gets the account value for this AccountStatementResponse.
     * 
     * @return account   * The ID of the account.
     */
    public java.lang.String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this AccountStatementResponse.
     * 
     * @param account   * The ID of the account.
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }


    /**
     * Gets the currency value for this AccountStatementResponse.
     * 
     * @return currency   * The currency of the account.
     */
    public java.lang.String getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this AccountStatementResponse.
     * 
     * @param currency   * The currency of the account.
     */
    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }


    /**
     * Gets the overdraft value for this AccountStatementResponse.
     * 
     * @return overdraft   * Overdraft permission.
     */
    public java.math.BigDecimal getOverdraft() {
        return overdraft;
    }


    /**
     * Sets the overdraft value for this AccountStatementResponse.
     * 
     * @param overdraft   * Overdraft permission.
     */
    public void setOverdraft(java.math.BigDecimal overdraft) {
        this.overdraft = overdraft;
    }


    /**
     * Gets the balance value for this AccountStatementResponse.
     * 
     * @return balance   * The balance of the account.
     */
    public java.math.BigDecimal getBalance() {
        return balance;
    }


    /**
     * Sets the balance value for this AccountStatementResponse.
     * 
     * @param balance   * The balance of the account.
     */
    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
    }


    /**
     * Gets the availableAmount value for this AccountStatementResponse.
     * 
     * @return availableAmount   * The amount available to the account holder.
     */
    public java.math.BigDecimal getAvailableAmount() {
        return availableAmount;
    }


    /**
     * Sets the availableAmount value for this AccountStatementResponse.
     * 
     * @param availableAmount   * The amount available to the account holder.
     */
    public void setAvailableAmount(java.math.BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }


    /**
     * Gets the status value for this AccountStatementResponse.
     * 
     * @return status   * The status of the account (open/closed).
     */
    public is.idega.idegaweb.egov.company.banking.ws.common.Status getStatus() {
        return status;
    }


    /**
     * Sets the status value for this AccountStatementResponse.
     * 
     * @param status   * The status of the account (open/closed).
     */
    public void setStatus(is.idega.idegaweb.egov.company.banking.ws.common.Status status) {
        this.status = status;
    }


    /**
     * Gets the totalAmountWaiting value for this AccountStatementResponse.
     * 
     * @return totalAmountWaiting   * The total amount of transactions waiting that exceeded amount
     * available.
     */
    public java.math.BigDecimal getTotalAmountWaiting() {
        return totalAmountWaiting;
    }


    /**
     * Sets the totalAmountWaiting value for this AccountStatementResponse.
     * 
     * @param totalAmountWaiting   * The total amount of transactions waiting that exceeded amount
     * available.
     */
    public void setTotalAmountWaiting(java.math.BigDecimal totalAmountWaiting) {
        this.totalAmountWaiting = totalAmountWaiting;
    }


    /**
     * Gets the IBAN value for this AccountStatementResponse.
     * 
     * @return IBAN   * IBAN Number
     */
    public java.lang.String getIBAN() {
        return IBAN;
    }


    /**
     * Sets the IBAN value for this AccountStatementResponse.
     * 
     * @param IBAN   * IBAN Number
     */
    public void setIBAN(java.lang.String IBAN) {
        this.IBAN = IBAN;
    }


    /**
     * Gets the accountOwnerID value for this AccountStatementResponse.
     * 
     * @return accountOwnerID   * PersonID of the account owner.
     */
    public java.lang.String getAccountOwnerID() {
        return accountOwnerID;
    }


    /**
     * Sets the accountOwnerID value for this AccountStatementResponse.
     * 
     * @param accountOwnerID   * PersonID of the account owner.
     */
    public void setAccountOwnerID(java.lang.String accountOwnerID) {
        this.accountOwnerID = accountOwnerID;
    }


    /**
     * Gets the customAccountName value for this AccountStatementResponse.
     * 
     * @return customAccountName   * Name set by account owner
     */
    public java.lang.String getCustomAccountName() {
        return customAccountName;
    }


    /**
     * Sets the customAccountName value for this AccountStatementResponse.
     * 
     * @param customAccountName   * Name set by account owner
     */
    public void setCustomAccountName(java.lang.String customAccountName) {
        this.customAccountName = customAccountName;
    }


    /**
     * Gets the accountInformation value for this AccountStatementResponse.
     * 
     * @return accountInformation   * Extra account information
     */
    public java.lang.String getAccountInformation() {
        return accountInformation;
    }


    /**
     * Sets the accountInformation value for this AccountStatementResponse.
     * 
     * @param accountInformation   * Extra account information
     */
    public void setAccountInformation(java.lang.String accountInformation) {
        this.accountInformation = accountInformation;
    }


    /**
     * Gets the transactions value for this AccountStatementResponse.
     * 
     * @return transactions
     */
    public is.idega.idegaweb.egov.company.banking.ws.statements.AccountTransaction[] getTransactions() {
        return transactions;
    }


    /**
     * Sets the transactions value for this AccountStatementResponse.
     * 
     * @param transactions
     */
    public void setTransactions(is.idega.idegaweb.egov.company.banking.ws.statements.AccountTransaction[] transactions) {
        this.transactions = transactions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AccountStatementResponse)) return false;
        AccountStatementResponse other = (AccountStatementResponse) obj;
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
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            ((this.overdraft==null && other.getOverdraft()==null) || 
             (this.overdraft!=null &&
              this.overdraft.equals(other.getOverdraft()))) &&
            ((this.balance==null && other.getBalance()==null) || 
             (this.balance!=null &&
              this.balance.equals(other.getBalance()))) &&
            ((this.availableAmount==null && other.getAvailableAmount()==null) || 
             (this.availableAmount!=null &&
              this.availableAmount.equals(other.getAvailableAmount()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.totalAmountWaiting==null && other.getTotalAmountWaiting()==null) || 
             (this.totalAmountWaiting!=null &&
              this.totalAmountWaiting.equals(other.getTotalAmountWaiting()))) &&
            ((this.IBAN==null && other.getIBAN()==null) || 
             (this.IBAN!=null &&
              this.IBAN.equals(other.getIBAN()))) &&
            ((this.accountOwnerID==null && other.getAccountOwnerID()==null) || 
             (this.accountOwnerID!=null &&
              this.accountOwnerID.equals(other.getAccountOwnerID()))) &&
            ((this.customAccountName==null && other.getCustomAccountName()==null) || 
             (this.customAccountName!=null &&
              this.customAccountName.equals(other.getCustomAccountName()))) &&
            ((this.accountInformation==null && other.getAccountInformation()==null) || 
             (this.accountInformation!=null &&
              this.accountInformation.equals(other.getAccountInformation()))) &&
            ((this.transactions==null && other.getTransactions()==null) || 
             (this.transactions!=null &&
              java.util.Arrays.equals(this.transactions, other.getTransactions())));
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
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        if (getOverdraft() != null) {
            _hashCode += getOverdraft().hashCode();
        }
        if (getBalance() != null) {
            _hashCode += getBalance().hashCode();
        }
        if (getAvailableAmount() != null) {
            _hashCode += getAvailableAmount().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getTotalAmountWaiting() != null) {
            _hashCode += getTotalAmountWaiting().hashCode();
        }
        if (getIBAN() != null) {
            _hashCode += getIBAN().hashCode();
        }
        if (getAccountOwnerID() != null) {
            _hashCode += getAccountOwnerID().hashCode();
        }
        if (getCustomAccountName() != null) {
            _hashCode += getCustomAccountName().hashCode();
        }
        if (getAccountInformation() != null) {
            _hashCode += getAccountInformation().hashCode();
        }
        if (getTransactions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTransactions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTransactions(), i);
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
        new org.apache.axis.description.TypeDesc(AccountStatementResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "AccountStatementResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("overdraft");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Overdraft"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Balance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availableAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "AvailableAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "Status"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalAmountWaiting");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "TotalAmountWaiting"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IBAN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "IBAN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountOwnerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "AccountOwnerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customAccountName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "CustomAccountName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountInformation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "AccountInformation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Transactions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "AccountTransaction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/StatementTypes", "Transaction"));
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
