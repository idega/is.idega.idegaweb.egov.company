package is.idega.idegaweb.egov.company.data;

import is.idega.idegaweb.egov.application.data.Application;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDORelationshipException;
import com.idega.data.query.Column;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.ListUtil;


/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 27, 2008 by Author: Anton 
 *
 */

public class CompanyEmployeeBMPBean extends GenericEntity implements CompanyEmployee {
	
	private static final String ENTITY_NAME = "COMPANY_EMPLOYEE";
	
	private static final String EMPLOYEE_USER = "employee_user";
	private static final String RVK_FIELDS = "rvk_fields";
	private static final String SERVICES = "employee_services";
	private static final String COMPANY_ADMINISTRATOR = "company_administrator";
	
	@Override
	public String getEntityName() {
		return ENTITY_NAME;
	}
	
	@Override
	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addOneToOneRelationship(EMPLOYEE_USER, "Employee user", User.class);
		addManyToManyRelationShip(EmployeeField.class, RVK_FIELDS);
		addManyToManyRelationShip(Application.class, SERVICES);
		addAttribute(COMPANY_ADMINISTRATOR,"Is company administrator",true,true,Boolean.class);
	}
	
	public Collection<EmployeeField> getFieldsInRvk() {
		try {
		    Collection<EmployeeField> fields = super.idoGetRelatedEntities(EmployeeField.class);
		    
		   return fields;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in getFieldsInRvk() : " + e.getMessage());
		}
	}
	
	public Collection<String> getFieldsInRvkPKs() {
		try {
		    Collection<String> fieldPKs = super.idoGetRelatedEntityPKs(EmployeeField.class);
		    
		   return fieldPKs;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in getFieldsInRvkPKs() : " + e.getMessage());
		}
	}
	
	public void setFieldsInRvk(Collection<EmployeeField> fields) {
		if (ListUtil.isEmpty(fields)) {
			return;
		}
		for(EmployeeField field : fields) {
			addFieldInRvk(field);
		}
	}
	
	public void addFieldInRvk(EmployeeField field) {
		try {
			this.idoAddTo(field);
		} catch (IDOAddRelationshipException e) {
			e.printStackTrace();
		}
	}
	
	public Collection<Application> getServices() {
		try {
		    Collection<Application> services = super.idoGetRelatedEntities(Application.class);
		    
		   return services;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in getServices() : " + e.getMessage());
		}
	}
	
	public Collection<String> getServicesPKs() {
		try {
		    Collection<String> servicePKs = super.idoGetRelatedEntityPKs(Application.class);
		    
		   return servicePKs;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in getFieldsInRvkPKs() : " + e.getMessage());
		}
	}
	
	public void setServices(Collection<Application> services) {
		if (ListUtil.isEmpty(services)) {
			return;
		}
		
		for (Application app : services) {
			addService(app);
		}
	}
	
	public void addService(Application app) {
		try {
			this.idoAddTo(app);
		} catch (IDOAddRelationshipException e) {
			e.printStackTrace();
		}
	}

	public User getUser() {
		return (User) getColumnValue(EMPLOYEE_USER);
	}
	
	public void setUser(User user) {
		setColumn(EMPLOYEE_USER, user);
	}

	public boolean isCompanyAdministrator() {
		return (Boolean) getColumnValue(COMPANY_ADMINISTRATOR);
	}

	public void setCompanyAdministrator(boolean companyAdmin) {
		setColumn(COMPANY_ADMINISTRATOR, companyAdmin);
		
	}
	
	public Object ejbFindByUser(User user) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(EMPLOYEE_USER), MatchCriteria.EQUALS, user));

		return idoFindOnePKByQuery(query);
	}
	
	public Collection ejbFindByGroup(Group userGroup) throws FinderException {
		Table employees = new Table(this);
		Table users = new Table(User.class);
		
		SelectQuery query = new SelectQuery(users);
		query.addColumn(users.getColumn(getIDColumnName()));
		try {
			query.addJoin(employees, users);
		}
		catch (IDORelationshipException e) {
			e.printStackTrace();
			throw new FinderException(e.getMessage());
		}
		
		query.addCriteria(new MatchCriteria(new Column(users, "Primary group"), MatchCriteria.EQUALS, userGroup.getId()));
		
		return idoFindPKsByQuery(query);
	}
	
	public Collection ejbFindAll() throws FinderException {
		Table table = new Table(this);
		SelectQuery query = new SelectQuery(table);
		query.addColumn(new Column(table, getIDColumnName()));
		query.addOrder(table, EMPLOYEE_USER, true);
		return this.idoFindPKsByQuery(query);
	}
}
