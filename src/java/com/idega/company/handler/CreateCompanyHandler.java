package com.idega.company.handler;

import java.util.logging.Logger;

import org.jbpm.graph.exe.ExecutionContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.business.IBOLookup;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyHome;
import com.idega.company.event.CompanyCreatedEvent;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.AddressHome;
import com.idega.core.location.data.PostalCode;
import com.idega.core.location.data.PostalCodeHome;
import com.idega.data.IDOLookup;
import com.idega.idegaweb.IWMainApplication;
import com.idega.jbpm.identity.CompanyData;
import com.idega.jbpm.identity.UserPersonalData;
import com.idega.jbpm.identity.authentication.CreateUserHandler;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.MetadataConstants;
import com.idega.user.data.User;
import com.idega.util.expression.ELUtil;

@Service(CreateCompanyHandler.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CreateCompanyHandler extends CreateUserHandler {

	private static final long serialVersionUID = 6987169880677900872L;
	private static final Logger LOGGER = Logger.getLogger(CreateCompanyHandler.class.getName());

	public static final String BEAN_NAME = "createCompanyHandler";

	private UserPersonalData userData;
	private CompanyData companyData;

	@Override
	public void execute(ExecutionContext context) throws Exception {
		if (getUserData() == null || getCompanyData() == null) {
			throw new RuntimeException("Not enough data to create company!");
		}

		setPublishEvent(Boolean.FALSE);
		try {
			super.execute(context);
		} catch(Exception e) {
			throw new RuntimeException("Error creating user!", e);
		}

		User createdUser = null;
		try {
			createdUser = getUser();
		} catch(Exception e) {
			throw new RuntimeException("User was not found by personal id: " + getUserData().getPersonalId(), e);
		}
		if (createdUser == null) {
			throw new RuntimeException("User was not found by personal id: " + getUserData().getPersonalId());
		}

		Company company = null;
		try {
			company = getCreatedCompany();
		} catch(Exception e) {
			throw new RuntimeException("Error creating company!", e);
		}
		if (company == null) {
			throw new RuntimeException("Company was not found nor created");
		}

		bindUserAndCompany(createdUser, company);

		ELUtil.getInstance().publishEvent(new CompanyCreatedEvent(this, createdUser, company));
	}

	public void bindUserAndCompany(User user, Company company) {
		user.setMetaData(MetadataConstants.USER_REAL_COMPANY_META_DATA_KEY, company.getPrimaryKey().toString());
		user.store();
	}

	private Company getCreatedCompany() throws Exception {
		CompanyHome companyHome = (CompanyHome) IDOLookup.getHome(Company.class);
		Company company = null;

		try {
			company = companyHome.findByPersonalID(getCompanyData().getSsn());
		} catch(Exception e) {}
		if (company != null) {
			LOGGER.info("Company with entered SSN already exists: " + company.getName() + ", " + company.getPersonalID());
			return company;
		}

		//	Company
		company = companyHome.create();
		company.setName(getCompanyData().getName());
		company.setPersonalID(getCompanyData().getSsn());
		company.store();

		//	Address
		AddressHome addressHome = (AddressHome) IDOLookup.getHome(Address.class);
		Address companyAddress = addressHome.create();
		companyAddress.setAddressType(addressHome.getAddressType1());
		companyAddress.setStreetName(getCompanyData().getAddress());
		companyAddress.store();

		//	Postal code
		PostalCodeHome postalCodeHome = (PostalCodeHome) IDOLookup.getHome(PostalCode.class);
		PostalCode postalCode = null;
		try {
			postalCode = postalCodeHome.findByPostalCode(getCompanyData().getPostalCode());
		} catch(Exception e) {}
		if (postalCode == null) {
			postalCode = postalCodeHome.create();
			postalCode.setPostalCode(getCompanyData().getPostalCode());
			postalCode.store();
		}

		//	Storing
		companyAddress.setPostalCode(postalCode);
		companyAddress.store();
		company.setAddress(companyAddress);
		company.store();

		return company;
	}

	private User getUser() throws Exception {
		UserBusiness userBusiness = IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), UserBusiness.class);
		return userBusiness.getUser(getUserData().getPersonalId());
	}

	@Override
	public UserPersonalData getUserData() {
		return userData;
	}

	@Override
	public void setUserData(UserPersonalData userData) {
		this.userData = userData;
	}

	public CompanyData getCompanyData() {
		return companyData;
	}

	public void setCompanyData(CompanyData companyData) {
		this.companyData = companyData;
	}
}