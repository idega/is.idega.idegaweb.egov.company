/*
 * $Id: CompanyHolder.java,v 1.1 2008/07/29 12:57:44 anton Exp $
 * Created on Nov 21, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business;

import com.idega.company.data.Company;

public class CompanyHolder {

	private Company company;
	private int allocationAmount = 0;
	private int allocationCount = 0;
	private int costsAmount = 0;
	private int costsCount = 0;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getAllocationAmount() {
		return this.allocationAmount;
	}

	public void setAllocationAmount(int allocationAmount) {
		this.allocationAmount = allocationAmount;
	}

	public int getAllocationCount() {
		return this.allocationCount;
	}

	public void setAllocationCount(int allocationCount) {
		this.allocationCount = allocationCount;
	}

	public int getCostsAmount() {
		return this.costsAmount;
	}

	public void setCostsAmount(int costsAmount) {
		this.costsAmount = costsAmount;
	}

	public int getCostsCount() {
		return this.costsCount;
	}

	public void setCostsCount(int costsCount) {
		this.costsCount = costsCount;
	}
}