package com.idega.company;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.company.data.Company;
import com.idega.company.data.CompanyHome;
import com.idega.core.business.DefaultSpringBean;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.AddressHome;
import com.idega.core.location.data.PostalCode;
import com.idega.core.location.data.PostalCodeHome;
import com.idega.data.IDOLookup;
import com.idega.event.CompanyCreatedEvent;
import com.idega.user.data.MetadataConstants;
import com.idega.user.data.User;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class NewCompanyRegistrationServices extends DefaultSpringBean implements ApplicationListener<CompanyCreatedEvent> {

	@Override
	public void onApplicationEvent(CompanyCreatedEvent event) {
		Company company = null;
		try {
			company = getCreatedCompany(event);
		} catch (Exception e) {
			throw new RuntimeException("Error creating company!", e);
		}
		if (company == null) {
			throw new RuntimeException("Company was not found nor created");
		}

		bindUserAndCompany(event.getUser(), company);
	}

	public void bindUserAndCompany(User user, Company company) {
		user.setMetaData(MetadataConstants.USER_REAL_COMPANY_META_DATA_KEY, company.getPrimaryKey().toString());
		user.store();
	}

	private Company getCreatedCompany(CompanyCreatedEvent data) throws Exception {
		CompanyHome companyHome = (CompanyHome) IDOLookup.getHome(Company.class);
		Company company = null;

		try {
			company = companyHome.findByPersonalID(data.getSsn());
		} catch(Exception e) {}
		if (company != null) {
			getLogger().info("Company with entered SSN already exists: " + company.getName() + ", " + company.getPersonalID());
			return company;
		}

		//	Company
		company = companyHome.create();
		company.setName(data.getName());
		company.setPersonalID(data.getSsn());
		company.store();

		//	Address
		AddressHome addressHome = (AddressHome) IDOLookup.getHome(Address.class);
		Address companyAddress = addressHome.create();
		companyAddress.setAddressType(addressHome.getAddressType1());
		companyAddress.setStreetName(data.getAddress());
		companyAddress.store();

		//	Postal code
		PostalCodeHome postalCodeHome = (PostalCodeHome) IDOLookup.getHome(PostalCode.class);
		PostalCode postalCode = null;
		try {
			postalCode = postalCodeHome.findByPostalCode(data.getPostalCode());
		} catch(Exception e) {}
		if (postalCode == null) {
			postalCode = postalCodeHome.create();
			postalCode.setPostalCode(data.getPostalCode());
			postalCode.store();
		}

		//	Storing
		companyAddress.setPostalCode(postalCode);
		companyAddress.store();
		company.setAddress(companyAddress);
		company.store();

		return company;
	}

}