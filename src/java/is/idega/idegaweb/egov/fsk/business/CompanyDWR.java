/*
 * $Id: CompanyDWR.java,v 1.1 2008/07/29 10:48:19 anton Exp $
 * Created on Jun 13, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.business;

public class CompanyDWR {

	private String companyPK;
	private String companyName;
	private String companyPersonalID;
	private String companyAddress;
	private String companyPostalCode;
	private String companyCity;
	private String companyPhone;
	private String companyFax;
	private String companyEmail;
	private String companyWebPage;
	private String companyBankAccount;

	public String getCompanyPK() {
		return this.companyPK;
	}

	public void setPK(String pk) {
		this.companyPK = pk;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setName(String name) {
		this.companyName = name;
	}

	public String getCompanyPersonalID() {
		return this.companyPersonalID;
	}

	public void setPersonalID(String personalID) {
		this.companyPersonalID = personalID;
	}

	public String getCompanyPhone() {
		return this.companyPhone;
	}

	public void setPhone(String phone) {
		this.companyPhone = phone;
	}

	public String getCompanyFax() {
		return this.companyFax;
	}

	public void setFax(String fax) {
		this.companyFax = fax;
	}

	public String getCompanyEmail() {
		return this.companyEmail;
	}

	public void setEmail(String email) {
		this.companyEmail = email;
	}

	public String getCompanyAddress() {
		return this.companyAddress;
	}

	public void setAddress(String address) {
		this.companyAddress = address;
	}

	public String getCompanyPostalCode() {
		return this.companyPostalCode;
	}

	public void setPostalCode(String postalCode) {
		this.companyPostalCode = postalCode;
	}

	public String getCompanyCity() {
		return this.companyCity;
	}

	public void setCity(String city) {
		this.companyCity = city;
	}

	public String getCompanyWebPage() {
		return this.companyWebPage;
	}

	public void setWebPage(String webPage) {
		this.companyWebPage = webPage;
	}

	public String getCompanyBankAccount() {
		return this.companyBankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.companyBankAccount = bankAccount;
	}
}