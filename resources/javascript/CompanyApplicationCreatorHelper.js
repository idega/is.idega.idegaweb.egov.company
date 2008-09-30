if (CompanyApplicationCreator == null) var CompanyApplicationCreator = {};

CompanyApplicationCreator.canGetInfo = function(event, id) {
	if (isEnterEvent(event)) {
		return true;
	}
	
	var element = document.getElementById(id);
	if (element == null) {
		return false;
	}
	var value = DWRUtil.getValue(id);
	if (value == null || value == '') {
		return false;
	}
	return value.length == 10 ? true : false;
}

CompanyApplicationCreator.getCompanyInfo = function(event, id, errorMessage) {
	if (CompanyApplicationCreator.canGetInfo(event, id)) {
		IWCORE.stopEventBubbling(event);
		closeAllLoadingMessages();
		
		var value = DWRUtil.getValue(id);
		var phone = DWRUtil.getValue('companyPhone');
		var fax = DWRUtil.getValue('companyFax');
		var email = DWRUtil.getValue('companyEmail');
		var webpage = DWRUtil.getValue('companyWebPage');
		var bankAccount = DWRUtil.getValue('companyBankAccount');
	
		CompanyApplicationBusiness.getCompany(value, phone, fax, email, webpage, bankAccount, {
			callback: function(companyInfo) {
				if (companyInfo == null) {
					CompanyApplicationCreator.showHumanizedMessage(errorMessage, id);
					CompanyApplicationCreator.clearCompanyFormValues();
					return false;
				}
				
				DWRUtil.setValues(companyInfo);
			}
		});
	}
	else {
		CompanyApplicationCreator.clearCompanyFormValues();
	}
}

CompanyApplicationCreator.clearCompanyFormValues = function() {
	DWRUtil.setValue('companyName', '');
	DWRUtil.setValue('companyAddress', '');
	DWRUtil.setValue('companyPostalCode', '');
	DWRUtil.setValue('companyCity', '');
	DWRUtil.setValue('companyPhone', '');
	DWRUtil.setValue('companyFax', '');
	DWRUtil.setValue('companyEmail', '');
	DWRUtil.setValue('companyWebPage', '');
	DWRUtil.setValue('companyBankAccount', '');
}

CompanyApplicationCreator.getContactPersonInformation = function(event, id, errorMessage) {
	if (CompanyApplicationCreator.canGetInfo(event, id)) {
		IWCORE.stopEventBubbling(event);
		closeAllLoadingMessages();
		
		CompanyApplicationBusiness.getUser(DWRUtil.getValue(id), {
			callback: function(userInfo) {
				if (userInfo == null) {
					CompanyApplicationCreator.showHumanizedMessage(errorMessage, id);
					CompanyApplicationCreator.clearUserFormValues();
					return false;
				}
				
				DWRUtil.setValues(userInfo);
			}
		});
	}
	else {
		CompanyApplicationCreator.clearUserFormValues();
	}
}

CompanyApplicationCreator.clearUserFormValues = function() {
	DWRUtil.setValue('userPK', '');
	DWRUtil.setValue('userName', '');
	DWRUtil.setValue('userWorkPhone', '');
	DWRUtil.setValue('userMobilePhone', '');
	DWRUtil.setValue('userEmail', '');
}

CompanyApplicationCreator.showHumanizedMessage = function(message, inputId) {
	if (inputId != null) {
		document.getElementById(inputId).focus();
	}
	humanMsg.displayMsg(message);
}