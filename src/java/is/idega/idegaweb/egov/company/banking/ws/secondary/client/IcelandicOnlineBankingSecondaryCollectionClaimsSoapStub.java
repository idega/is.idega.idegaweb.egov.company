/**
 * IcelandicOnlineBankingSecondaryCollectionClaimsSoapStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.company.banking.ws.secondary.client;

public class IcelandicOnlineBankingSecondaryCollectionClaimsSoapStub extends org.apache.axis.client.Stub implements is.idega.idegaweb.egov.company.banking.ws.secondary.client.IcelandicOnlineBankingSecondaryCollectionClaimsSoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[11];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("AlterClaims");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "claims"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimCollection"), is.idega.idegaweb.egov.company.banking.ws.claims.Claim[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "OperationID"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "AlterClaimsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CancelClaims");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "keys"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimKeyCollection"), is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "OperationID"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "CancelClaimsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("AlterClaim");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "claim"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Claim"), is.idega.idegaweb.egov.company.banking.ws.claims.Claim.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimOperationResult"));
        oper.setReturnClass(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "AlterClaimResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CancelClaim");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimKey"), is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimOperationResult"));
        oper.setReturnClass(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "CancelClaimResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetClaimOperationResult");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "OperationID"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimOperationResult"));
        oper.setReturnClass(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "GetClaimOperationResultResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryClaims");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "query"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimsQuery"), is.idega.idegaweb.egov.company.banking.ws.claims.ClaimsQuery.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "QueryClaimsResult"));
        oper.setReturnClass(is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "QueryClaimsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryClaim");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimKey"), is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimInfo"));
        oper.setReturnClass(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "QueryClaimResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPayments");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "query"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "PaymentsQuery"), is.idega.idegaweb.egov.company.banking.ws.claims.PaymentsQuery.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "QueryPaymentsResult"));
        oper.setReturnClass(is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "QueryPaymentsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QuerySecondaryCollectionPayments");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "query"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "SecondaryCollectionPaymentsQuery"), is.idega.idegaweb.egov.company.banking.ws.claims.SecondaryCollectionPaymentsQuery.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "QueryPaymentsResult"));
        oper.setReturnClass(is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "QuerySecondaryCollectionPaymentsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QuerySecondaryCollectionClaims");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "query"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "SecondaryCollectionClaimsQuery"), is.idega.idegaweb.egov.company.banking.ws.claims.SecondaryCollectionClaimsQuery.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "QueryClaimsResult"));
        oper.setReturnClass(is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "QuerySecondaryCollectionClaimsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SecondaryCollectionReturnClaim");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimKey"), is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "SecondaryCollectionReturnClaimResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

    }

    public IcelandicOnlineBankingSecondaryCollectionClaimsSoapStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public IcelandicOnlineBankingSecondaryCollectionClaimsSoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public IcelandicOnlineBankingSecondaryCollectionClaimsSoapStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", ">BillPresentmentSystem>Parameters");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", ">BillPresentmentSystem>Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", ">CurrencyInformation>PaymentRate");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyInformationPaymentRate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", ">CurrencyInformation>ReferenceRate");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyInformationReferenceRate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", ">DefaultInterest>SpecialCode");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", ">ItemRow>Text");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", ">Payment>PaymentType");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.PaymentPaymentType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "BatchStatus");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.BatchStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "BillPresentmentSystem");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.BillPresentmentSystem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Claim");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.Claim.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimCollection");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.Claim[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Claim");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimError");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ClaimError.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimInfo");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ClaimInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimKey");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimKeyCollection");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimKey");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimOperationResult");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimResult");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ClaimResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimsQuery");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ClaimsQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimsQueryDateSpan");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ClaimsQueryDateSpan.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ClaimStatus");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ClaimStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Comment");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CurrencyExchangeRate");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyExchangeRate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CurrencyInformation");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.CurrencyInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "CustomerNumber");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DateRestrictedCharge");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.DateRestrictedCharge.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DateSpanReferenceDate");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.DateSpanReferenceDate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultInterest");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.DefaultInterest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DefaultInterestRule");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.DefaultInterestRule.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DiscountCharge");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.DiscountCharge.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "DiscountOrDefaultChargeBase");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.DiscountOrDefaultChargeBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Identifier");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ItemRow");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ItemRow.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "NoticeAndPaymentFee");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.NoticeAndPaymentFee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Payment");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.Payment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "PaymentsQuery");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.PaymentsQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "PrintInformation");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.PrintInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Printing");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.Printing.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "QueryClaimsResult");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "QueryPaymentsResult");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "Reference");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "ReferenceDate");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.ReferenceDate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "SecondaryCollectionClaimsQuery");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.SecondaryCollectionClaimsQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/ClaimTypes", "SecondaryCollectionPaymentsQuery");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.claims.SecondaryCollectionPaymentsQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "AccountID");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "Address");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.common.Address.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "AddressLine");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "Amount");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "Bank");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "BatchNumber");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "BillNumber");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "CategoryCode");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "CountryCode");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "CurrencyAmount");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "CurrencyCode");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "Error");
            cachedSerQNames.add(qName);
            cls = is.idega.idegaweb.egov.company.banking.ws.common.Error.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "ErrorCode");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "ErrorMessage");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "OperationID");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "Percentage4");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "PersonID");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/CommonTypes", "PostCode");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public java.lang.String alterClaims(is.idega.idegaweb.egov.company.banking.ws.claims.Claim[] claims) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/AlterClaims");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "AlterClaims"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {claims});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String cancelClaims(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey[] keys) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/CancelClaims");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "CancelClaims"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {keys});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult alterClaim(is.idega.idegaweb.egov.company.banking.ws.claims.Claim claim) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/AlterClaim");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "AlterClaim"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {claim});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult) org.apache.axis.utils.JavaUtils.convert(_resp, is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult cancelClaim(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/CancelClaim");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "CancelClaim"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {key});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult) org.apache.axis.utils.JavaUtils.convert(_resp, is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult getClaimOperationResult(java.lang.String id) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/GetClaimOperationResult");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "GetClaimOperationResult"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {id});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult) org.apache.axis.utils.JavaUtils.convert(_resp, is.idega.idegaweb.egov.company.banking.ws.claims.ClaimOperationResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult queryClaims(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimsQuery query) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/QueryClaims");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "QueryClaims"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult) org.apache.axis.utils.JavaUtils.convert(_resp, is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public is.idega.idegaweb.egov.company.banking.ws.claims.ClaimInfo queryClaim(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/QueryClaim");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "QueryClaim"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {key});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.ClaimInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.ClaimInfo) org.apache.axis.utils.JavaUtils.convert(_resp, is.idega.idegaweb.egov.company.banking.ws.claims.ClaimInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult queryPayments(is.idega.idegaweb.egov.company.banking.ws.claims.PaymentsQuery query) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/QueryPayments");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "QueryPayments"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult) org.apache.axis.utils.JavaUtils.convert(_resp, is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult querySecondaryCollectionPayments(is.idega.idegaweb.egov.company.banking.ws.claims.SecondaryCollectionPaymentsQuery query) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/QuerySecondaryCollectionPayments");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "QuerySecondaryCollectionPayments"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult) org.apache.axis.utils.JavaUtils.convert(_resp, is.idega.idegaweb.egov.company.banking.ws.claims.QueryPaymentsResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult querySecondaryCollectionClaims(is.idega.idegaweb.egov.company.banking.ws.claims.SecondaryCollectionClaimsQuery query) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/QuerySecondaryCollectionClaims");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "QuerySecondaryCollectionClaims"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult) org.apache.axis.utils.JavaUtils.convert(_resp, is.idega.idegaweb.egov.company.banking.ws.claims.QueryClaimsResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String secondaryCollectionReturnClaim(is.idega.idegaweb.egov.company.banking.ws.claims.ClaimKey key) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionReturnClaim");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://IcelandicOnlineBanking/2005/12/01/SecondaryCollectionClaims", "SecondaryCollectionReturnClaim"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {key});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
