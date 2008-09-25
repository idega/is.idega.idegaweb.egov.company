package is.idega.idegaweb.egov.company.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.query.Column;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 26, 2008 by Author: Anton 
 *
 */

public class EmployeeFieldBMPBean extends GenericEntity implements EmployeeField {

	private static final long serialVersionUID = 3479036403896318456L;

	private static final String ENTITY_NAME = "COMPANY_EMPLOYEE_FIELDS";

	private static final String COLUMN_SERVICE_CODE = "service_code";
	private static final String COLUMN_SERVICE_DESCRIPTION = "service_description";
	
	@Override
	public String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(COLUMN_SERVICE_CODE, "service code", String.class, 50);
		addAttribute(COLUMN_SERVICE_DESCRIPTION, "service description", String.class);
	}

	public String getServiceCode() {
		return (String) getColumnValue(COLUMN_SERVICE_CODE);
	}

	public String getServiceDescription() {
		return (String) getColumnValue(COLUMN_SERVICE_DESCRIPTION);
	}

	public void setServiceCode(String serviceCode) {
		setColumn(COLUMN_SERVICE_CODE, serviceCode);
	}

	public void setServiceDescription(String serviceDesription) {
		setColumn(COLUMN_SERVICE_CODE, serviceDesription);
		
	}
	
	public Object ejbFindByServiceCode(String code) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_SERVICE_DESCRIPTION), MatchCriteria.EQUALS, code));

		return idoFindOnePKByQuery(query);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<EmployeeField> ejbFindAll() throws FinderException {
		Table table = new Table(this);
		SelectQuery query = new SelectQuery(table);
		query.addColumn(new Column(table, getIDColumnName()));
		query.addOrder(table, COLUMN_SERVICE_CODE, true);
		return this.idoFindPKsByQuery(query);
	}
}
