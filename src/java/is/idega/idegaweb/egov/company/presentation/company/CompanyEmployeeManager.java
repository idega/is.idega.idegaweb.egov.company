package is.idega.idegaweb.egov.company.presentation.company;

import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.application.data.ApplicationHome;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.data.CompanyEmployee;
import is.idega.idegaweb.egov.company.data.CompanyEmployeeHome;
import is.idega.idegaweb.egov.company.data.EmployeeField;
import is.idega.idegaweb.egov.company.data.EmployeeFieldHome;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.faces.component.html.HtmlMessages;

import com.idega.business.IBOLookup;
import com.idega.company.CompanyConstants;
import com.idega.core.accesscontrol.business.StandardRoles;
import com.idega.data.IDOLookup;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SelectOption;
import com.idega.presentation.ui.SelectPanel;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextInput;
import com.idega.user.app.SimpleUserApp;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.ArrayUtil;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

/**
 *
 * 
 * @author <a href="anton@idega.com">Anton Makarov</a>
 * @version Revision: 1.0 
 *
 * Last modified: Aug 27, 2008 by Author: Anton 
 *
 */

public class CompanyEmployeeManager extends CompanyBlock {
	
	private static final String PARAMETER_LIST_COMPANY_EMPLOYEES = "prm_list_company_employees";
	private static final String PARAMETER_MANAGE_COMPANY_EMPLOYEE_USER = "prm_manage_company_employees_user";
	private static final String PARAMETER_EDIT_COMPANY_EMPLOYEE = "prm_edit_company_employee";
	private static final String PARAMETER_DELETE_COMPANY_EMPLOYEE = "prm_delete_company_employee";
	private static final String PARAMETER_SAVE_COMPANY_EMPLOYEE = "prm_save_company_employee";
	
	private static final String NAME_INPUT = "name";
	private static final String SURNAME_INPUT = "surname";
	private static final String PERSONAL_ID_INPUT = "personalId";
	private static final String GROUP_INPUT = "group";
	private static final String SERVICES_INPUT = "services";
	private static final String RVK_FIELDS_INPUT = "fields";
	private static final String ADMIN_BOX_INPUT = "admin";
	private static final String ADMIN_BOX_INPUT_VALUE = "checked";
	
	public static final String EMPLOYEE_ID_PARAMETER = "prm_employee_id";
	public static final String USER_ID_PARAMETER = "prm_user_id";
	
	public static final String ACTION = "prm_action";
	
	private Group group;	
	private IWBundle iwb;
	private String roleTypes = null;
	
	@Override
	protected void present(IWContext iwc) throws Exception {
		super.present(iwc);
		
		iwb = getBundle(iwc);
		
		if (!getCompanyBusiness().isCompanyAdministrator(iwc)) {
			showInsufficientRightsMessage(iwrb.getLocalizedString("insufficient_rights_to_manage_accounts",
					"You have insufficient rights to manage accounts!"));
			return;
		}
		
		String employeeId = iwc.getParameter(EMPLOYEE_ID_PARAMETER);
		String userId = iwc.getParameter(USER_ID_PARAMETER);
		String action = iwc.getParameter(ACTION);
		
		if(!StringUtil.isEmpty(action)) {
			if (action.equals(PARAMETER_MANAGE_COMPANY_EMPLOYEE_USER)) {
				showUserForm(iwc);
				return;
			} else if (action.equals(PARAMETER_LIST_COMPANY_EMPLOYEES)) {
				showCompanyUserList(iwc);
				return;
			} else if (action.equals(PARAMETER_EDIT_COMPANY_EMPLOYEE)) {
				if(!StringUtil.isEmpty(employeeId)) {
					showCompanyEmployeeEditForm(iwc, employeeId);
					return;
				} else {
					showCompanyEmployeeCreateForm(iwc, userId);
					return;
				}
			} else if(action.equals(PARAMETER_SAVE_COMPANY_EMPLOYEE)) {
				saveEmployee(iwc);
				showCompanyUserList(iwc);
				return;
			} else if (action.equals(PARAMETER_DELETE_COMPANY_EMPLOYEE)) {
				removeEmployee(iwc);
//				try {
//					CompanyEmployee emp = getEmployeeHome().findByPrimaryKey(new Integer(employeeId));
//					emp.remove();
//				}
//				catch (FinderException f) {
//					f.printStackTrace();
//				}
				
				showCompanyUserList(iwc);
				return;
			}
		}
		
			
		//	By default showing company employees 
		if(getGroup() != null) {
			showCompanyUserList(iwc);
		} else {
			showMessage(iwrb.getLocalizedString("no_user_group_selected",
			"There's no user group selected"));
		}
		return;
	}
	
	private void showCompanyUserList(IWContext iwc) {
		Layer container = new Layer();
		add(container);
		container.add(getLink(iwrb.getLocalizedString("manage_employee_account", "Edit company employee user account"), ACTION, PARAMETER_MANAGE_COMPANY_EMPLOYEE_USER));
		
		Layer accountsManagerInContainer = listExistingCompanyGroupUsers(iwc);
		container.add(accountsManagerInContainer);
	}
	
	private void showUserForm(IWContext iwc) {
		Layer container = new Layer();
		add(container);
		container.add(getLink(iwrb.getLocalizedString("show_employee_list", "Show employees list"), ACTION, PARAMETER_LIST_COMPANY_EMPLOYEES));
		
		Layer employeeUserForm = getEmployeeAccountManager(iwc, 
				                                           ListUtil.convertListOfStringsToCommaseparatedString(Arrays.asList(new String [] {
				                                        		   CompanyConstants.GROUP_TYPE_COMPANY, 
				                                        		   EgovCompanyConstants.GROUP_TYPE_COMPANY_DIVISIONS, 
				                                        		   EgovCompanyConstants.GROUP_TYPE_COMPANY_COURSE,
				                                        		   EgovCompanyConstants.GROUP_TYPE_COMPANY_DIVISION, 
				                                        		   EgovCompanyConstants.GROUP_TYPE_COMPANY_SUB_GROUP
				                                        	})));
		add(employeeUserForm);
	}
	
	private void showCompanyEmployeeEditForm(IWContext iwc, String employeeId) {
		Layer container = new Layer();
		add(container);
		container.add(getLink(iwrb.getLocalizedString("manage_employee_account", "Edit company employee user account"), ACTION, PARAMETER_MANAGE_COMPANY_EMPLOYEE_USER));
		container.add(getLink(iwrb.getLocalizedString("show_employee_list", "Show employees list"), ACTION, PARAMETER_LIST_COMPANY_EMPLOYEES));
		
		CompanyEmployee employee = null;
		try {
			employee = getEmployeeHome().findByPrimaryKey(employeeId);
		} catch (RemoteException e) {
		} catch (FinderException e) {
		}
		
		Layer employeeEditForm = getEmployeeEditForm(iwc, employee);
		add(employeeEditForm);
	}
	
	private void showCompanyEmployeeCreateForm(IWContext iwc, String userId) {
		Layer container = new Layer();
		add(container);
		container.add(getLink(iwrb.getLocalizedString("manage_employee_account", "Edit company employee user account"), ACTION, PARAMETER_MANAGE_COMPANY_EMPLOYEE_USER));
		container.add(getLink(iwrb.getLocalizedString("show_employee_list", "Show employees list"), ACTION, PARAMETER_LIST_COMPANY_EMPLOYEES));
		
		User user = null;
		try {
			user = getUserBusiness(iwc).getUser(Integer.parseInt(userId));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Layer employeeEditForm = getEmployeeEditForm(iwc, user);
		add(employeeEditForm);
	}
	
	private Layer getEmployeeAccountManager(IWContext iwc, String groupTypesForChildrenGroups) {
		Layer container = new Layer();
		container.setStyleClass("usersContainerStyle");
		
		SimpleUserApp sua = new SimpleUserApp();
		container.add(sua);
		sua.setParentGroup(group);
		//sua.setUseChildrenOfTopNodesAsParentGroups(!juridicalPerson);
		sua.setGroupTypes(StandardRoles.ROLE_KEY_COMPANY);
		sua.setGroupTypesForChildGroups(groupTypesForChildrenGroups);
		sua.setRoleTypesForChildGroups(getRoleTypesForChildGroups());
		//sua.setJuridicalPerson(juridicalPerson);
		sua.setAllowEnableDisableAccount(true);
		sua.setAllFieldsEditable(true);
		sua.setSendMailToUser(true);
		sua.setChangePasswordNextTime(true);
		
		return container;
	}
	
	private String getRoleTypesForChildGroups() {
		if (roleTypes == null) {
			List<String> roles = new ArrayList<String>();
			roles.add(StandardRoles.ROLE_KEY_AUTHOR);
			roles.add(StandardRoles.ROLE_KEY_EDITOR);
			roles.addAll(Arrays.asList(new String[] {EgovCompanyConstants.COMPANY_ADMIN_ROLE, EgovCompanyConstants.COMPANY_EMPLOYEE_ROLE,
													StandardRoles.ROLE_KEY_COMPANY}));
			roleTypes = ListUtil.convertListOfStringsToCommaseparatedString(roles);
		}
		return roleTypes;
	}

	private Layer listExistingCompanyGroupUsers(IWContext iwc) {
		Collection<User> companyUsers;
		try {
			companyUsers = getUserBusiness(iwc).getUsersInPrimaryGroup(getGroup());
		} catch (RemoteException e) {
			companyUsers = null;
			e.printStackTrace();
		} 
		
		Layer container = new Layer();
		container.setStyleClass("usersContainerStyle");
		
		Table2 table = new Table2();
		table.setWidth("100%");
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setStyleClass("ruler");
		table.setStyleClass("employeeTable");
		container.add(table);
		
		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.add(new Text(""));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("employeeName");
		cell.add(new Text(this.iwrb.getLocalizedString("employeeName", "Name")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("employeeSurname");
		cell.add(new Text(this.iwrb.getLocalizedString("employeeSurname", "Surname")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("employeeGroup");
		cell.add(new Text(this.iwrb.getLocalizedString("employeeGroup", "Group")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("employeeAvailability");
		cell.add(new Text(this.iwrb.getLocalizedString("employee", "Company employee")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("edit");
		cell.add(Text.getNonBrakingSpace());

		cell = row.createHeaderCell();
		cell.setStyleClass("remove");
		cell.setStyleClass("lastColumn");
		cell.add(Text.getNonBrakingSpace());

		group = table.createBodyRowGroup();
		int iRow = 1;
		
		if (!ListUtil.isEmpty(companyUsers)) {
			for (User usr : companyUsers) {
				CompanyEmployee emp = null;
				try {
					emp = getEmployeeHome().findByUser(usr);
				} catch (RemoteException e) {
				} catch (FinderException e) {
				}

				row = table.createRow();
				
				Link edit = new Link(this.iwb.getImage("images/edit.png", this.iwrb.getLocalizedString("edit", "Edit")));
				edit.addParameter(ACTION, PARAMETER_EDIT_COMPANY_EMPLOYEE);
				if(emp != null)
					edit.addParameter(EMPLOYEE_ID_PARAMETER, emp.getPrimaryKey().toString());
				else 
					edit.addParameter(USER_ID_PARAMETER, usr.getPrimaryKey().toString());
				
				Link delete = new Link(this.iwb.getImage("images/delete.png", this.iwrb.getLocalizedString("remove", "Remove")));
				delete.addParameter(PARAMETER_DELETE_COMPANY_EMPLOYEE, "true");
				if(emp != null)
					delete.addParameter(EMPLOYEE_ID_PARAMETER, emp.getPrimaryKey().toString());
				
	
				if (iRow % 2 == 0) {
					row.setStyleClass("evenRow");
				}
				else {
					row.setStyleClass("oddRow");
				}
	
				cell = row.createCell();
				cell.setStyleClass("firstColumn");
				cell.setStyleClass("number");
				cell.add(new Text(iRow + ""));
				
				cell = row.createCell();
				cell.setStyleClass("employeeName");
				cell.add(new Text(usr.getFirstName()));
				
				cell = row.createCell();
				cell.setStyleClass("employeeSurname");
				cell.add(new Text(usr.getLastName()));
	
				cell = row.createCell();
				cell.setStyleClass("employeeGroup");
				cell.add(new Text(getGroup().getName()));
				
				cell = row.createCell();
				cell.setStyleClass("employeeAvailability");
				//TODO change values 
				cell.add(new Text((emp == null) ? "not created" : "created"));
				
				cell = row.createCell();
				cell.setStyleClass("edit");
				cell.add(edit);
	
				cell = row.createCell();
				cell.setStyleClass("lastColumn");
				cell.setStyleClass("remove");
				cell.add(delete);
					
				iRow++;
			}
		}
		return container;
	}
	
	private Layer getEmployeeEditForm(IWContext iwc, Object employeeInfoObject) {
		Layer container = new Layer();
		
		Form form = new Form();
		form.setID("employeeCreator");
		form.setStyleClass("employeeForm");
		
		CompanyEmployee employee = null;
		User user = null;
		if(employeeInfoObject instanceof CompanyEmployee) {
			employee = (CompanyEmployee)employeeInfoObject;
			user = employee.getUser();
			form.add(new HiddenInput(EMPLOYEE_ID_PARAMETER, employee.getPrimaryKey().toString()));
		} else if (employeeInfoObject instanceof User) {
			user = (User)employeeInfoObject;
			form.add(new HiddenInput(USER_ID_PARAMETER, user.getPrimaryKey().toString()));
		} else {
			throw new RuntimeException(CompanyEmployee.class + " or " + User.class + " are expected");
		}
		
		
		TextInput userPersonalId = new TextInput("personalId");
		userPersonalId.setId(PERSONAL_ID_INPUT);
		userPersonalId.setValue(user.getPersonalID());
		userPersonalId.setReadOnly(true);
		
		TextInput userName = new TextInput("name");
		userName.setId(NAME_INPUT);
		userName.setValue(user.getFirstName());
		userName.setReadOnly(true);
		
		TextInput userSurname = new TextInput("surname");
		userSurname.setId(SURNAME_INPUT);
		userSurname.setValue(user.getLastName());
		userSurname.setReadOnly(true);
		
		TextInput group = new TextInput("group");
		group.setId(GROUP_INPUT);
		group.setValue(getGroup().getName());
		group.setReadOnly(true);
		
		Collection<Application> userApplications = null;
		try {
			userApplications = getCompanyBusiness().getUserApplications(user);
		} catch (RemoteException e) {
			userApplications = new ArrayList<Application>();
			e.printStackTrace();
		}
		SelectPanel applicationSelect = new SelectPanel(SERVICES_INPUT);
		
		for(Application app : userApplications) {
			applicationSelect.addOption(new SelectOption(app.getName(), app.getPrimaryKey() + ""));
		}

		SelectPanel fieldsSelect = new SelectPanel(RVK_FIELDS_INPUT);
		
		Collection<EmployeeField> userFieldsInRvk = null;
		
		try {
			userFieldsInRvk = getEmployeeFieldHome().findAll();
		} catch (RemoteException e) {
			userFieldsInRvk = new ArrayList<EmployeeField>();
			e.printStackTrace();
		} catch (FinderException e) {
			userFieldsInRvk = new ArrayList<EmployeeField>();
			e.printStackTrace();
		}
		
		for(EmployeeField field : userFieldsInRvk) {
			fieldsSelect.addOption(new SelectOption(field.getServiceDescription(), field.getPrimaryKey() + ""));
		}

		CheckBox adminCheckBox = new CheckBox(ADMIN_BOX_INPUT, "true");
		
		if(employee != null) {
			fieldsSelect.setSelectedElements((String[])employee.getFieldsInRvkPKs().toArray());
			applicationSelect.setSelectedElements((String[])employee.getServicesPKs().toArray());
			adminCheckBox.setChecked(employee.isCompanyAdministrator());
		}
		
		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass("formSection");
		form.add(layer);
		
		Layer formItem = new Layer(Layer.DIV);
		Layer errorItem = new Layer(Layer.SPAN);
		errorItem.setStyleClass("error");
		
		formItem.setStyleClass("errors");
		HtmlMessages msgs = (HtmlMessages)iwc.getApplication().createComponent(HtmlMessages.COMPONENT_TYPE);
		formItem.add(msgs);
		layer.add(formItem);
		
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label(this.iwrb.getLocalizedString("user_personal_id", "User personal Id"), userPersonalId);
		formItem.add(label);
		formItem.add(userPersonalId);
		layer.add(formItem);
		
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(this.iwrb.getLocalizedString("user_name", "User name"), userName);
		formItem.add(label);
		formItem.add(userName);
		layer.add(formItem);
		
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(this.iwrb.getLocalizedString("user_surname", "User surname"), userSurname);
		formItem.add(label);
		formItem.add(userSurname);
		layer.add(formItem);
		
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(this.iwrb.getLocalizedString("group", "Group"), group);
		formItem.add(label);
		formItem.add(group);
		layer.add(formItem);
		
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(this.iwrb.getLocalizedString("rvkFields", "fields in RVK"), fieldsSelect);
		formItem.add(label);
		formItem.add(fieldsSelect);
		layer.add(formItem);		
		
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(this.iwrb.getLocalizedString("applications", "Applications"), applicationSelect);
		formItem.add(label);
		formItem.add(applicationSelect);
		layer.add(formItem);
		
		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(this.iwrb.getLocalizedString("admin", "Administrator"), applicationSelect);
		formItem.add(label);
		formItem.add(adminCheckBox);
		layer.add(formItem);
		
		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
			
		layer.add(clearLayer);
		
		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);
		
		SubmitButton back = new SubmitButton(this.iwrb.getLocalizedString("back", "Back"), ACTION, PARAMETER_MANAGE_COMPANY_EMPLOYEE_USER);
		buttonLayer.add(back);
		
		SubmitButton save = new SubmitButton(this.iwrb.getLocalizedString("save", "Save"), ACTION, PARAMETER_SAVE_COMPANY_EMPLOYEE);
		buttonLayer.add(save);
		
		add(form);
		
		return container;
	}
	
	private void saveEmployee(IWContext iwc) {
		String[] rvkFields = iwc.getParameterValues(RVK_FIELDS_INPUT);
		String[] services = iwc.getParameterValues(SERVICES_INPUT);
		
		boolean isAdmin = iwc.isParameterSet(ADMIN_BOX_INPUT);
		
		CompanyEmployee emp = null;
		
		User selectedUser = null;
		try {
			if(iwc.isParameterSet(EMPLOYEE_ID_PARAMETER)) {
				String employeeId = iwc.getParameter(EMPLOYEE_ID_PARAMETER);
				emp = getEmployeeHome().findByPrimaryKey(employeeId);			
			} else if(iwc.isParameterSet(USER_ID_PARAMETER)){
				String userId = iwc.getParameter(USER_ID_PARAMETER);
				emp = getEmployeeHome().create();
				selectedUser = getUserBusiness(iwc).getUser(Integer.parseInt(userId));
				emp.setUser(selectedUser);
			} else {
				throw new RuntimeException("No employee to update or create");
			}
			
			Collection<EmployeeField> fields = ArrayUtil.isEmpty(rvkFields) ? null : getEmployeeFieldHome().findByMultiplePrimaryKey(Arrays.asList(rvkFields));
			if (ListUtil.isEmpty(fields)) {
				//	TODO: remove existing fields
			}
			else {
				emp.setFieldsInRvk(fields);
			}

			Collection<Application> apps = ArrayUtil.isEmpty(services) ? null : getApplicationHome().findByPrimaryKeyCollection(Arrays.asList(services));
			if (ListUtil.isEmpty(apps)) {
				//	TODO: remove existing apps
			}
			else {
				emp.setServices(apps);
			}
			
			emp.setCompanyAdministrator(isAdmin);
			
			emp.store();
		
			boolean result = isAdmin ? getCompanyBusiness().makeUserCommonEmployee(selectedUser, getGroup()) :
				getCompanyBusiness().makeUserCommonEmployee(selectedUser, getGroup());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		}	
	}
	
	private void removeEmployee(IWContext iwc) {
		
	}
	
	private Layer getLink(String message, String parameterNameToMaintain, String parameterValueToMaintain) {
		Layer switcher = new Layer();
		Link switchLink = new Link(message);
		switcher.add(switchLink);
		switchLink.addParameter(parameterNameToMaintain, parameterValueToMaintain);
		return switcher;
	}
		
	protected CompanyEmployeeHome getEmployeeHome() throws RemoteException {
		return (CompanyEmployeeHome) IDOLookup.getHome(CompanyEmployee.class);
	}
	
	protected EmployeeFieldHome getEmployeeFieldHome() throws RemoteException {
		return (EmployeeFieldHome) IDOLookup.getHome(EmployeeField.class);
	}
	
	protected ApplicationHome getApplicationHome() throws RemoteException {
		return (ApplicationHome) IDOLookup.getHome(ApplicationHome.class);
	}
	
	protected UserBusiness getUserService(IWApplicationContext iwac) throws RemoteException {
		return (UserBusiness) IBOLookup.getServiceInstance(iwac, UserBusiness.class);
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}