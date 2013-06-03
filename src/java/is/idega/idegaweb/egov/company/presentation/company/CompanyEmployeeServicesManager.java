package is.idega.idegaweb.egov.company.presentation.company;

import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.application.data.ApplicationHome;
import is.idega.idegaweb.egov.company.data.CompanyEmployee;
import is.idega.idegaweb.egov.company.data.CompanyEmployeeHome;
import is.idega.idegaweb.egov.company.data.EmployeeField;
import is.idega.idegaweb.egov.company.data.EmployeeFieldHome;
import is.idega.idegaweb.egov.company.presentation.CompanyBlock;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.faces.component.html.HtmlMessages;

import com.idega.data.IDOLookup;
import com.idega.presentation.CSSSpacer;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.ListItem;
import com.idega.presentation.text.Lists;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.BackButton;
import com.idega.presentation.ui.CheckBox;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.SubmitButton;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.ArrayUtil;
import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

public class CompanyEmployeeServicesManager extends CompanyBlock {

	private static final String SERVICES_INPUT = "services";
	private static final String RVK_FIELDS_INPUT = "fields";
	
	private static final String EMPLOYEE_ID_PARAMETER = "prm_employee_id";
	private static final String USER_ID_PARAMETER = "prm_user_id";
	
	private static final String PARAMETER_ACTION = "prm_action";
	private static final int ACTION_VIEW = 1;
	private static final int ACTION_EDIT = 2;
	private static final int ACTION_SAVE = 4;
	private static final int ACTION_DELETE = 5;
	
	@Override
	protected void present(IWContext iwc) throws Exception {
		super.present(iwc);
		
		if (!getCompanyBusiness().isCompanyAdministrator(iwc)) {
			showInsufficientRightsMessage(iwrb.getLocalizedString("insufficient_rights_to_manage_accounts",
					"You have insufficient rights to manage accounts!"));
			return;
		}
		
		if (group == null) {
			group = getGroupThatIsCompanyForCurrentUser(iwc);
		}
		if (getGroup() == null) {
			showMessage(iwrb.getLocalizedString("no_user_group_selected", "There's no user group selected"));
			return;
		}
		
		String employeeId = iwc.getParameter(EMPLOYEE_ID_PARAMETER);
		String userId = iwc.getParameter(USER_ID_PARAMETER);
		
		switch (parseAction(iwc)) {
		case ACTION_VIEW:
			showCompanyUserList(iwc);
			break;

		case ACTION_EDIT:
			if(!StringUtil.isEmpty(employeeId)) {
				showCompanyEmployeeEditForm(iwc, employeeId);
				break;
			} else {
				showCompanyEmployeeCreateForm(iwc, userId);
				break;
			}

		case ACTION_SAVE:
			saveEmployee(iwc);
			showCompanyUserList(iwc);
			break;

		case ACTION_DELETE:
			removeEmployee(iwc);
			showCompanyUserList(iwc);
			break;
			
		default:
			showCompanyUserList(iwc);
		}
	}
	
	private int parseAction(IWContext iwc) {
		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_VIEW;
	}

	
	private void showCompanyUserList(IWContext iwc) {
		Layer container = new Layer();
		add(container);
		
		Layer accountsManagerInContainer = listExistingCompanyGroupUsers(iwc);
		container.add(accountsManagerInContainer);
	}
	
	private void showCompanyEmployeeEditForm(IWContext iwc, String employeeId) {
		Layer container = new Layer();
		add(container);
		
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
	
	private Layer listExistingCompanyGroupUsers(IWContext iwc) {
		Collection<User> companyUsers = getCompanyPortalBusiness().getAllCompanyUsers(getGroup());
		
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
		cell.add(new Text(iwrb.getLocalizedString("nr", "Nr.")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("employeeName");
		cell.add(new Text(iwrb.getLocalizedString("employeeName", "Name")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("employeePersonalId");
		cell.add(new Text(iwrb.getLocalizedString("employeePersonalId", "Personal ID")));
		
		cell = row.createHeaderCell();
		cell.setStyleClass("edit");
		String servicesLocalized = iwrb.getLocalizedString("assign_services_for_employee", "Assign services");
		cell.add(new Text(servicesLocalized));

		cell = row.createHeaderCell();
		cell.setStyleClass("remove");
		cell.setStyleClass("lastColumn");
		String removeServicesLocalized = iwrb.getLocalizedString("remove_all_selected_services", "Remove services");
		cell.add(new Text(removeServicesLocalized));

		group = table.createBodyRowGroup();
		int iRow = 1;
		if (!ListUtil.isEmpty(companyUsers)) {
			String removeAction = new StringBuilder("if (window.confirm('").append(iwrb.getLocalizedString("are_you_sure", "Are you sure?"))
									.append("')) { return true; } else return false;").toString();
			for (User usr : companyUsers) {
				CompanyEmployee emp = null;
				try {
					emp = getEmployeeHome().findByUser(usr);
				} catch (RemoteException e) {
				} catch (FinderException e) {
				}

				row = table.createRow();
				
				Link edit = new Link(bundle.getImage("images/edit.png", servicesLocalized));
				edit.addParameter(PARAMETER_ACTION, ACTION_EDIT);
				if (emp == null) {
					edit.addParameter(USER_ID_PARAMETER, usr.getPrimaryKey().toString());
				}
				else {
					edit.addParameter(EMPLOYEE_ID_PARAMETER, emp.getPrimaryKey().toString());
				}
				
				Link delete = new Link(bundle.getImage("images/delete.png", removeServicesLocalized));
				delete.addParameter(PARAMETER_ACTION, ACTION_DELETE);
				if (emp != null) {
					delete.addParameter(EMPLOYEE_ID_PARAMETER, emp.getPrimaryKey().toString());
				}
				delete.setOnClick(removeAction);
	
				if (iRow % 2 == 0) {
					row.setStyleClass("evenRow");
				}
				else {
					row.setStyleClass("oddRow");
				}
	
				cell = row.createCell();
				cell.setStyleClass("firstColumn");
				cell.setStyleClass("number");
				cell.add(new Text(String.valueOf(iRow)));
				
				cell = row.createCell();
				cell.setStyleClass("employeeFullName");
				cell.add(new Text(usr.getName()));
				
				cell = row.createCell();
				cell.setStyleClass("employeePersonalId");
				cell.add(new Text(usr.getPersonalID() == null ? iwrb.getLocalizedString("unkown_pesonal_id", "Unknown") : usr.getPersonalID()));
				
				cell = row.createCell();
				cell.setStyleClass("edit");
				cell.add(edit);
	
				cell = row.createCell();
				cell.setStyleClass("lastColumn");
				cell.setStyleClass("remove");
				cell.add((emp != null && !ListUtil.isEmpty(emp.getServices())) ? delete :
					new Text(iwrb.getLocalizedString("no_services_assigned_yet", "There are no services assigned yet")));
					
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
		if (employeeInfoObject instanceof CompanyEmployee) {
			employee = (CompanyEmployee) employeeInfoObject;
			user = employee.getUser();
			form.add(new HiddenInput(EMPLOYEE_ID_PARAMETER, employee.getPrimaryKey().toString()));
		} else if (employeeInfoObject instanceof User) {
			user = (User) employeeInfoObject;
			form.add(new HiddenInput(USER_ID_PARAMETER, user.getPrimaryKey().toString()));
		} else {
			throw new RuntimeException(CompanyEmployee.class + " or " + User.class + " are expected");
		}
		
		form.add(getSmallMessage(new StringBuilder(iwrb.getLocalizedString("services_for_employee", "Services for employee")).append(CoreConstants.SPACE)
				.append(user.getName()).append(CoreConstants.COMMA).append(CoreConstants.SPACE)
				.append(iwrb.getLocalizedString("employee_personal_id_small", "personal ID")).append(CoreConstants.COLON).append(CoreConstants.SPACE)
				.append(StringUtil.isEmpty(user.getPersonalID()) ? iwrb.getLocalizedString("unknown_personal_id_small", "unkown") : user.getPersonalID())
				.toString(), "companyEmployeeManagerEmployeeServicesAssigner"));
		
		Collection<Application> userApplications = null;
		try {
			userApplications = getCompanyBusiness().getUserApplications(iwc, user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Collection<Application> assignedServices = employee == null ? null : employee.getServices();
		
		Layer servicesContainer = new Layer();
		servicesContainer.setStyleClass("servicesFormItem servicesFormValues");
		if (ListUtil.isEmpty(userApplications)) {
			servicesContainer.add(getMessage(iwrb.getLocalizedString("company_portal.there_are_no_services_available", "There are no services available yet"),
					"noServicesAvailableForCompanyEmployees", true));
		}
		else {
			Lists list = new Lists();
			list.setStyleClass("servicesList");
			servicesContainer.add(list);
			Locale locale = iwc.getCurrentLocale();
			for (Application app : userApplications) {
				ListItem item = new ListItem();
				list.add(item);
				
				Layer serviceContainer = new Layer();
				item.add(serviceContainer);
				
				CheckBox checkService = new CheckBox(SERVICES_INPUT, app.getPrimaryKey().toString());
				checkService.setChecked(!ListUtil.isEmpty(assignedServices) && assignedServices.contains(app));
				serviceContainer.add(checkService);
				serviceContainer.add(getCompanyBusiness().getApplicationName(app, locale));
			}
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
		Layer label = new Layer();
		label.setStyleClass("servicesFormItem formLabel");
		label.add(iwrb.getLocalizedString("services", "Services"));
		formItem.add(label);
		formItem.add(servicesContainer);
		formItem.add(new CSSSpacer());
		layer.add(formItem);
		
		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
			
		layer.add(clearLayer);
		
		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);
		
		BackButton back = new BackButton(iwrb.getLocalizedString("back", "Back"));
		buttonLayer.add(back);
		
		SubmitButton save = new SubmitButton(iwrb.getLocalizedString("save", "Save"), PARAMETER_ACTION, String.valueOf(ACTION_SAVE));
		buttonLayer.add(save);
		
		add(form);
		
		return container;
	}
	
	private void saveEmployee(IWContext iwc) {
		String[] rvkFields = iwc.getParameterValues(RVK_FIELDS_INPUT);
		Object[] services = iwc.getParameterValues(SERVICES_INPUT);
		
		CompanyEmployee emp = null;
		
		User selectedUser = null;
		try {
			if (iwc.isParameterSet(EMPLOYEE_ID_PARAMETER)) {
				String employeeId = iwc.getParameter(EMPLOYEE_ID_PARAMETER);
				emp = getEmployeeHome().findByPrimaryKey(employeeId);
				selectedUser = emp.getUser();
			} else if(iwc.isParameterSet(USER_ID_PARAMETER)){
				String userId = iwc.getParameter(USER_ID_PARAMETER);
				emp = getEmployeeHome().create();
				selectedUser = getUserBusiness(iwc).getUser(Integer.parseInt(userId));
				emp.setUser(selectedUser);
				emp.store();
			} else {
				throw new RuntimeException("No employee to update or create");
			}
			
			Collection<EmployeeField> fields = ArrayUtil.isEmpty(rvkFields) ? null : getEmployeeFieldHome().findByMultiplePrimaryKey(Arrays.asList(rvkFields));
			if (ListUtil.isEmpty(fields)) {
				emp.removeAllFields();
			}
			else {
				emp.setFieldsInRvk(fields);
			}

			Collection<Application> apps = ArrayUtil.isEmpty(services) ? null : getApplicationHome().findByMultiplePrimaryKey(Arrays.asList(services));
			if (ListUtil.isEmpty(apps)) {
				emp.removeAllServices();
			}
			else {
				emp.setServices(apps);
			}
			emp.store();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (CreateException e) {
			e.printStackTrace();
		}	
	}
	
	private void removeEmployee(IWContext iwc) {
		String employeeId = iwc.getParameter(EMPLOYEE_ID_PARAMETER);
		try {
			CompanyEmployee emp = getEmployeeHome().findByPrimaryKey(employeeId);
			emp.removeAllFields();
			emp.removeAllServices();
			emp.remove();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (EJBException e) {
			e.printStackTrace();
		} catch (RemoveException e) {
			e.printStackTrace();
		}
	}
	
	private CompanyEmployeeHome getEmployeeHome() throws RemoteException {
		return (CompanyEmployeeHome) IDOLookup.getHome(CompanyEmployee.class);
	}
	
	private EmployeeFieldHome getEmployeeFieldHome() throws RemoteException {
		return (EmployeeFieldHome) IDOLookup.getHome(EmployeeField.class);
	}
	
	private ApplicationHome getApplicationHome() throws RemoteException {
		return (ApplicationHome) IDOLookup.getHome(Application.class);
	}
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
}
