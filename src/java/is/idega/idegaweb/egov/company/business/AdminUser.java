/*
 * $Id: AdminUser.java,v 1.1 2008/07/29 12:57:44 anton Exp $
 * Created on Jun 13, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business;

public class AdminUser {

	private String userPK;
	private String userName;
	private String userPersonalID;
	private String userWorkPhone;
	private String userMobilePhone;
	private String userEmail;

	public String getUserPK() {
		return this.userPK;
	}

	public void setPK(String userPK) {
		this.userPK = userPK;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setName(String userName) {
		this.userName = userName;
	}

	public String getUserPersonalID() {
		return this.userPersonalID;
	}

	public void setPersonalID(String userPersonalID) {
		this.userPersonalID = userPersonalID;
	}

	public String getUserWorkPhone() {
		return this.userWorkPhone;
	}

	public void setWorkPhone(String userWorkPhone) {
		this.userWorkPhone = userWorkPhone;
	}

	public String getUserMobilePhone() {
		return this.userMobilePhone;
	}

	public void setMobilePhone(String userMobilePhone) {
		this.userMobilePhone = userMobilePhone;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}