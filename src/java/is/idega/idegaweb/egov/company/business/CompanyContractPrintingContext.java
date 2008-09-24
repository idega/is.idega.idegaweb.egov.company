/*
 * $Id: CompanyContractPrintingContext.java,v 1.1 2008/09/24 10:28:50 anton Exp $
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
import com.idega.xml.XMLDocument;
import com.idega.xml.XMLElement;
import com.idega.xml.XMLOutput;

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
		Map props = new HashMap();
		Company company = ((CompanyApplication)application).getCompany();
		Commune commune = company.getWorkingArea();
		if (commune == null) {
			commune = getCommuneBusiness(iwac).getDefaultCommune();
		}

		props.put("iwb", getBundle(iwac));
		props.put("iwrb", getResourceBundle(iwac, locale));

		props.put("name", company.getName());
		props.put("personalID", company.getPersonalID());

		setFileName(getResourceBundle(iwac, locale).getLocalizedString("contract.file_name", "contract") + "_" + String.valueOf(application.getPrimaryKey()));
		addDocumentProperties(props);
		setResourceDirectory(new File(getResourcRealPath(getBundle(iwac), locale)));
		try {
			setTemplateStream(getTemplateUrlAsStream(getBundle(iwac), locale, "company_contract_template.xml", true));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String getResourcRealPath(IWBundle iwb, Locale locale) {
		String printFolder = iwb.getApplication().getSettings().getProperty("egov.print_template_folder", "/print/");

		if (locale != null) {
			return iwb.getResourcesRealPath(locale) + printFolder;
		}
		else {
			return iwb.getResourcesRealPath() + printFolder;
		}
	}

	protected FileInputStream getTemplateUrlAsStream(IWBundle iwb, Locale locale, String name, boolean createIfNotExists) throws IOException {
		File template = new File(getTemplateUrl(iwb, locale, name));
		if (!template.exists() && createIfNotExists) {
			createTemplateFile(template);
		}
		return new FileInputStream(template);
	}

	protected String getTemplateUrl(IWBundle iwb, Locale locale, String name) {
		return getResourcRealPath(iwb, locale) + name;
	}

	private void createTemplateFile(File file) throws IOException {
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);

		XMLOutput xmlOutput = new XMLOutput("  ", true);
		xmlOutput.setLineSeparator(System.getProperty("line.separator"));
		xmlOutput.setTextNormalize(true);
		xmlOutput.setEncoding("UTF-8");
		XMLDocument doc = getTemplateXMLDocument();
		xmlOutput.output(doc, fos);
		fos.close();
	}

	protected XMLDocument getBasicXMLDocument() {
		XMLElement document = new XMLElement("document");
		document.setAttribute("size", "A4");
		document.setAttribute("margin-left", "25");
		document.setAttribute("margin-right", "25");
		document.setAttribute("margin-top", "25");
		document.setAttribute("margin-bottom", "25");
		XMLDocument doc = new XMLDocument(document);

		return doc;
	}

	protected XMLDocument getTemplateXMLDocument() {
		XMLDocument doc = getBasicXMLDocument();
		XMLElement document = doc.getRootElement();
		XMLElement subject = new XMLElement("paragraph");
		subject.addContent("${msg.subject}");
		document.addContent(subject);
		XMLElement body = new XMLElement("paragraph");
		body.setAttribute("halign", "justified");
		body.addContent("${msg.body}");
		document.addContent(body);
		return doc;
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