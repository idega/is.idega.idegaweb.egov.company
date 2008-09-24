function readUser() {
	var value = DWRUtil.getValue("userPersonalID");
	if (value.length == 10) {
		FSKDWRUtil.getUser(value, "IS", fillUser);
	}
	else {
		DWRUtil.setValue("userPK", "");
		DWRUtil.setValue("userName", "");
		DWRUtil.setValue("userWorkPhone", "");
		DWRUtil.setValue("userMobilePhone", "");
		DWRUtil.setValue("userEmail", "");
	}
}

function fillUser(auser) {
	DWRUtil.setValues(auser);
}

function readCompany() {
	var value = DWRUtil.getValue("companyPersonalID");
	var phone = DWRUtil.getValue("companyPhone");
	var fax = DWRUtil.getValue("companyFax");
	var email = DWRUtil.getValue("companyEmail");
	var webpage = DWRUtil.getValue("companyWebPage");
	var bankAccount = DWRUtil.getValue("companyBankAccount");
	
	if (value.length == 10) {
		FSKDWRUtil.getCompanyInfo(value, phone, fax, email, webpage, bankAccount, "IS", fillCompany);
	}
	else {
		DWRUtil.setValue("companyName", "");
		DWRUtil.setValue("companyAddress", "");
		DWRUtil.setValue("companyPostalCode", "");
		DWRUtil.setValue("companyCity", "");
		DWRUtil.setValue("companyPhone", phone);
		DWRUtil.setValue("companyFax", fax);
		DWRUtil.setValue("companyEmail", email);
		DWRUtil.setValue("companyWebPage", webpage);
		DWRUtil.setValue("companyBankAccount", bankAccount);
	}
}

function fillCompany(company) {
	DWRUtil.setValues(company);
}