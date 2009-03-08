if (CompanyApplicationCreator == null) var CompanyApplicationCreator = {};

CompanyApplicationCreator.canGetInfo = function(event, id) {
	if (isEnterEvent(event)) {
		return true;
	}
	
	var element = document.getElementById(id);
	if (element == null) {
		return false;
	}
	var value = dwr.util.getValue(id);
	if (value == null || value == '') {
		return false;
	}
	return value.length == 10 ? true : false;
}

CompanyApplicationCreator.getCompanyInfo = function(event, id, errorMessage) {
	if (CompanyApplicationCreator.canGetInfo(event, id)) {
		IWCORE.stopEventBubbling(event);
		closeAllLoadingMessages();
		
		var value = dwr.util.getValue(id);
		var phone = dwr.util.getValue('companyPhone');
		var fax = dwr.util.getValue('companyFax');
		var email = dwr.util.getValue('companyEmail');
		var webpage = dwr.util.getValue('companyWebPage');
		var bankAccount = dwr.util.getValue('companyBankAccount');
	
		CompanyApplicationBusiness.getCompany(value, phone, fax, email, webpage, bankAccount, {
			callback: function(companyInfo) {
				if (companyInfo == null) {
					CompanyApplicationCreator.showHumanizedMessage(errorMessage, id);
					CompanyApplicationCreator.clearCompanyFormValues();
					return false;
				}
				
				dwr.util.setValues(companyInfo);
			}
		});
	}
	else {
		CompanyApplicationCreator.clearCompanyFormValues();
	}
}

CompanyApplicationCreator.clearCompanyFormValues = function() {
	dwr.util.setValue('companyName', '');
	dwr.util.setValue('companyAddress', '');
	dwr.util.setValue('companyPostalCode', '');
	dwr.util.setValue('companyCity', '');
	dwr.util.setValue('companyPhone', '');
	dwr.util.setValue('companyFax', '');
	dwr.util.setValue('companyEmail', '');
	dwr.util.setValue('companyWebPage', '');
	dwr.util.setValue('companyBankAccount', '');
}

CompanyApplicationCreator.getContactPersonInformation = function(event, id, errorMessage) {
	if (CompanyApplicationCreator.canGetInfo(event, id)) {
		IWCORE.stopEventBubbling(event);
		closeAllLoadingMessages();
		
		CompanyApplicationBusiness.getUser(dwr.util.getValue(id), {
			callback: function(userInfo) {
				if (userInfo == null) {
					CompanyApplicationCreator.showHumanizedMessage(errorMessage, id);
					CompanyApplicationCreator.clearUserFormValues();
					return false;
				}
				
				dwr.util.setValues(userInfo);
			}
		});
	}
	else {
		CompanyApplicationCreator.clearUserFormValues();
	}
}

CompanyApplicationCreator.clearUserFormValues = function() {
	dwr.util.setValue('userPK', '');
	dwr.util.setValue('userName', '');
	dwr.util.setValue('userWorkPhone', '');
	dwr.util.setValue('userMobilePhone', '');
	dwr.util.setValue('userEmail', '');
}

CompanyApplicationCreator.showHumanizedMessage = function(message, inputId) {
	if (inputId != null) {
		document.getElementById(inputId).focus();
	}
	humanMsg.displayMsg(message);
}