/*
 * $Id: ContractPrintingContext.java,v 1.1 2008/07/29 10:48:19 anton Exp $
 * Created on Jun 14, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.business;

import is.idega.idegaweb.egov.fsk.FSKConstants;
import is.idega.idegaweb.egov.fsk.data.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import com.idega.block.pdf.business.PrintingContextImpl;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.core.location.business.CommuneBusiness;
import com.idega.core.location.data.Commune;
import com.idega.core.location.data.PostalCode;
import com.idega.data.IDORelationshipException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.xml.XMLDocument;
import com.idega.xml.XMLElement;
import com.idega.xml.XMLOutput;

public class ContractPrintingContext extends PrintingContextImpl {

	protected IWBundle iwb;
	protected IWResourceBundle iwrb;

	public ContractPrintingContext(IWApplicationContext iwac, Application application, Locale locale) {
		try {
			init(iwac, application, locale);
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private void init(IWApplicationContext iwac, Application application, Locale locale) throws RemoteException {
		Map props = new HashMap();
		Company company = application.getCompany();
		CompanyType type = company.getType();
		Commune commune = company.getWorkingArea();
		if (commune == null) {
			commune = getCommuneBusiness(iwac).getDefaultCommune();
		}

		props.put("iwb", getBundle(iwac));
		props.put("iwrb", getResourceBundle(iwac, locale));

		props.put("name", company.getName());
		props.put("personalID", company.getPersonalID());
		props.put("type", iwrb.getLocalizedString("company_type." + type.getLocalizedKey(), type.getName()));
		props.put("commune", commune.getCommuneName());

		StringBuffer buffer = new StringBuffer();
		try {
			Collection codes = application.getPostalCodes();
			Iterator iterator = codes.iterator();
			while (iterator.hasNext()) {
				PostalCode code = (PostalCode) iterator.next();
				buffer.append(code.getPostalCode());
				if (iterator.hasNext()) {
					buffer.append(", ");
				}
			}
		}
		catch (IDORelationshipException e1) {
			e1.printStackTrace();
		}
		props.put("postalCodes", buffer.toString());

		setFileName(getResourceBundle(iwac, locale).getLocalizedString("contract.file_name", "contract"));
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
		return FSKConstants.IW_BUNDLE_IDENTIFIER;
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