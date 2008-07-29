/*
 * $Id: DivisionBMPBean.java,v 1.1 2008/07/29 12:57:42 anton Exp $
 * Created on Jun 17, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.data;

import is.idega.idegaweb.egov.company.FSKConstants;

import java.util.Collection;

import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.idega.company.data.Company;
import com.idega.company.data.CompanyHome;
import com.idega.data.IDOLookupException;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.user.data.Group;
import com.idega.user.data.GroupBMPBean;

public class DivisionBMPBean extends GroupBMPBean implements Division, Group {

	private static final String METADATA_IS_APPROVED = "is_approved";

	private Company iCompany;

	public Boolean isApproved() {
		String useChip = this.getMetaData(METADATA_IS_APPROVED);
		if (useChip != null) {
			return new Boolean(useChip);
		}
		return null;
	}

	public void setApproved(boolean approved) {
		setMetaData(METADATA_IS_APPROVED, String.valueOf(approved), "java.lang.Boolean");
	}

	public void remove() throws RemoveException {
		removeAllMetaData();
		store();
		super.remove();
	}

	public Company getCompany() {
		if (iCompany == null) {
			Group parentGroup = (Group) this.getParentNode();
			if (parentGroup != null) {
				Group companyGroup = (Group) parentGroup.getParentNode();

				try {
					CompanyHome home = (CompanyHome) getIDOHome(Company.class);
					iCompany = home.findByPrimaryKey(companyGroup.getPrimaryKey());
				}
				catch (IDOLookupException e) {
					e.printStackTrace();
				}
				catch (FinderException e) {
					e.printStackTrace();
				}
			}
		}

		return iCompany;
	}

	public Collection ejbFindAll() throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(getGroupTypeColumnName()), MatchCriteria.EQUALS, FSKConstants.GROUP_TYPE_DIVISION));

		return idoFindPKsByQuery(query);
	}
}