if (CompanyApplicationCreator == null) var CompanyApplicationCreator = {};

CompanyApplicationCreator.getContactPersonInformation = function(event) {
	if (isEnterEvent(event)) {
		IWCORE.stopEventBubbling(event);
		closeAllLoadingMessages();
		
		CompanyApplicationBusiness.getUser(DWRUtil.getValue('userPersonalID'), {
			callback: function(userInfo) {
				DWRUtil.setValues(userInfo);
			}
		});
	}
	else {
		DWRUtil.setValue('userPK', '');
		DWRUtil.setValue('userName', '');
		DWRUtil.setValue('userWorkPhone', '');
		DWRUtil.setValue('userMobilePhone', '');
		DWRUtil.setValue('userEmail', '');
	}
}

CompanyApplicationCreator.getCompanyInfo = function(event) {
	if (isEnterEvent(event)) {
		IWCORE.stopEventBubbling(event);
		closeAllLoadingMessages();
		
		var value = DWRUtil.getValue('companyPersonalID');
		var phone = DWRUtil.getValue('companyPhone');
		var fax = DWRUtil.getValue('companyFax');
		var email = DWRUtil.getValue('companyEmail');
		var webpage = DWRUtil.getValue('companyWebPage');
		var bankAccount = DWRUtil.getValue('companyBankAccount');
	
		CompanyApplicationBusiness.getCompany(value, phone, fax, email, webpage, bankAccount, {
			callback: function(companyInfo) {
				DWRUtil.setValues(companyInfo);
			}
		});
	}
	else {
		DWRUtil.setValue('companyName', '');
		DWRUtil.setValue('companyAddress', '');
		DWRUtil.setValue('companyPostalCode', '');
		DWRUtil.setValue('companyCity', '');
		DWRUtil.setValue('companyPhone', phone);
		DWRUtil.setValue('companyFax', fax);
		DWRUtil.setValue('companyEmail', email);
		DWRUtil.setValue('companyWebPage', webpage);
		DWRUtil.setValue('companyBankAccount', bankAccount);
	}
}