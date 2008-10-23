/*
 * $Id: CompanyContractPrintingContext.java,v 1.7 2008/10/23 12:29:52 valdas Exp $
 * Created on Jun 14, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.company.business;

import is.idega.idegaweb.egov.application.data.Application;
import is.idega.idegaweb.egov.company.EgovCompanyConstants;
import is.idega.idegaweb.egov.company.data.CompanyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.idega.block.pdf.business.PrintingContext;
import com.idega.block.pdf.business.PrintingContextImpl;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.core.location.business.CommuneBusiness;
import com.idega.core.location.data.Commune;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.util.FileUtil;

public class CompanyContractPrintingContext extends PrintingContextImpl {

	protected IWBundle iwb;
	protected IWResourceBundle iwrb;

	public CompanyContractPrintingContext(IWApplicationContext iwac, Application application, Locale locale) {
		try {
			init(iwac, application, locale);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private void init(IWApplicationContext iwac, Application application, Locale locale) throws RemoteException {
		Map<String, Object> props = new HashMap<String, Object>();
		Company company = ((CompanyApplication)application).getCompany();
		Commune commune = company.getWorkingArea();
		if (commune == null) {
			commune = getCommuneBusiness(iwac).getDefaultCommune();
		}

		props.put(PrintingContext.IW_BUNDLE_ROPERTY_NAME, getBundle(iwac));
		props.put("iwrb", getResourceBundle(iwac, locale));

		props.put("name", company.getName());
		props.put("personalID", company.getPersonalID());

		setFileName(getResourceBundle(iwac, locale).getLocalizedString("contract.file_name", "contract") + "_" + String.valueOf(application.getPrimaryKey()));
		addDocumentProperties(props);

		String baseDirectory = getResourceRealPath(getBundle(iwac), locale);
		try {
			setResourceDirectory(FileUtil.getFileAndCreateRecursiveIfNotExists(baseDirectory));
			File file = FileUtil.getFileFromWorkspace(baseDirectory + "company_contract_template.xml");
			setTemplateStream(new FileInputStream(file));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String getResourceRealPath(IWBundle iwb, Locale locale) {
		String printFolder = iwb.getApplication().getSettings().getProperty("company_portal.print_contract_folder", "/print/contract/");

		if (locale != null) {
			return iwb.getResourcesRealPath(locale) + printFolder;
		}
		else {
			return iwb.getResourcesRealPath() + printFolder;
		}
	}

	protected FileInputStream getTemplateUrlAsStream(IWBundle iwb, Locale locale, String name, boolean createIfNotExists) throws IOException {
		File template = new File(getTemplateUrl(iwb, locale, name));
		return new FileInputStream(template);
	}

	protected String getTemplateUrl(IWBundle iwb, Locale locale, String name) {
		return getResourceRealPath(iwb, locale) + name;
	}

	private String getBundleIdentifier() {
		return EgovCompanyConstants.IW_BUNDLE_IDENTIFIER;
	}

	private IWBundle getBundle(IWApplicationContext iwac) {
		if (this.iwb == null) {
			this.iwb = iwac.getIWMainApplication().getBundle(getBundleIdentifier());
		}
		return this.iwb;
	}

	private IWResourceBundle getResourceBundle(IWApplicationContext iwac, Locale locale) {
		if (this.iwrb == null) {
			this.iwrb = getBundle(iwac).getResourceBundle(locale);
		}
		return this.iwrb;
	}

	private CommuneBusiness getCommuneBusiness(IWApplicationContext iwac) {
		try {
			return (CommuneBusiness) IBOLookup.getServiceInstance(iwac, CommuneBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}