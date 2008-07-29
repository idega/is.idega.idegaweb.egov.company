/*
 * $Id: ApplicationAttachments.java,v 1.1 2008/07/29 10:48:18 anton Exp $
 * Created on Jun 16, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation;

import is.idega.idegaweb.egov.fsk.FSKConstants;
import is.idega.idegaweb.egov.fsk.business.ApplicationSession;
import is.idega.idegaweb.egov.fsk.data.Application;
import is.idega.idegaweb.egov.fsk.data.ApplicationFiles;
import is.idega.idegaweb.egov.fsk.data.Season;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.faces.component.UIComponent;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.company.data.Company;
import com.idega.core.file.data.ICFile;
import com.idega.core.file.data.ICFileHome;
import com.idega.data.IDOLookup;
import com.idega.idegaweb.IWUserContext;
import com.idega.io.UploadFile;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Span;
import com.idega.presentation.text.Heading1;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.ListItem;
import com.idega.presentation.text.Lists;
import com.idega.presentation.text.Paragraph;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.FileInput;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.RadioButton;
import com.idega.presentation.ui.util.SelectorUtility;
import com.idega.util.FileUtil;
import com.idega.util.IWTimestamp;

public class ApplicationAttachments extends FSKBlock {

	private static final String PARAMETER_ACTION = "prm_action";
	private static final String PARAMETER_FILE_TYPE = "prm_file_type";
	private static final String PARAMETER_SEASON_PK = "prm_season_pk";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_SAVE = 2;

	private boolean iHasErrors = false;
	private Map iErrors;
	private boolean showSeasons = false;

	public void present(IWContext iwc) {
		if (!iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY_ADMIN, iwc)) {
			showNoPermission(iwc);
			return;
		}

		try {
			Company company = getCompany(iwc.getCurrentUser());
			if (company != null) {
				Application application = getBusiness().getApplication(company);
				if (application != null) {
					switch (parseAction(iwc)) {
						case ACTION_VIEW:
							showUploadForm(iwc, application);
							break;

						case ACTION_SAVE:
							save(iwc, application);
							break;
					}
				}
				else {
					showReceipt(iwc, company, getResourceBundle().getLocalizedString("attachments.uploader", "Attachments uploader"), getResourceBundle().getLocalizedString("attachments.no_application_found", "No application found"), getResourceBundle().getLocalizedString("attachments.no_application_associated_with_company", "No application was found for the company."));
				}
			}
			else {
				showReceipt(iwc, null, getResourceBundle().getLocalizedString("attachments.uploader", "Attachments uploader"), getResourceBundle().getLocalizedString("attachments.no_company_found", "No company found"), getResourceBundle().getLocalizedString("attachments.no_company_associated_with_user", "The logged in user is not associated with any company."));
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private int parseAction(IWContext iwc) {
		UploadFile uploadFile = iwc.getUploadedFile();
		if (uploadFile != null && uploadFile.getName() != null && uploadFile.getName().length() > 0) {
			try {
				if (!getSession(iwc).contains(uploadFile.getName())) {
					String fileType = iwc.getParameter(PARAMETER_FILE_TYPE);
					if (fileType != null) {
						FileInputStream input = new FileInputStream(uploadFile.getRealPath());

						ICFile attachment = ((ICFileHome) IDOLookup.getHome(ICFile.class)).create();
						attachment.setName(uploadFile.getName());
						attachment.setMimeType(uploadFile.getMimeType());
						attachment.setFileValue(input);
						attachment.setFileSize((int) uploadFile.getSize());
						attachment.store();

						getSession(iwc).addFile(attachment, fileType);

						uploadFile.setId(((Integer) attachment.getPrimaryKey()).intValue());
					}
					else {
						setError(PARAMETER_FILE_TYPE, getResourceBundle().getLocalizedString("application_error.must_select_file_type", "You have to select a file type"));
					}
				}

				try {
					FileUtil.delete(uploadFile);
				}
				catch (Exception ex) {
					System.err.println("MediaBusiness: deleting the temporary file at " + uploadFile.getRealPath() + " failed.");
				}
			}
			catch (RemoteException e) {
				e.printStackTrace(System.err);
				uploadFile.setId(-1);
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (CreateException ce) {
				ce.printStackTrace();
			}
		}

		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_VIEW;
	}

	private void showUploadForm(IWContext iwc, Application application) throws RemoteException {
		Form form = new Form();
		form.addParameter(PARAMETER_ACTION, ACTION_VIEW);
		form.setMultiPart();

		addErrors(iwc, form);

		Company company = application.getCompany();
		form.add(getCompanyInfo(iwc, company));

		Heading1 heading = new Heading1(getResourceBundle().getLocalizedString("application.application_overview", "Application overview"));
		heading.setStyleClass("subHeader");
		heading.setStyleClass("topSubHeader");
		form.add(heading);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		Label label = new Label();
		label.add(getResourceBundle().getLocalizedString("application_number", "Application number"));
		Span span = new Span(new Text(application.getPrimaryKey().toString()));
		formItem.add(label);
		formItem.add(span);
		section.add(formItem);

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label();
		label.add(getResourceBundle().getLocalizedString("created_date", "Created date"));
		span = new Span(new Text(new IWTimestamp(application.getCreated()).getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT)));
		formItem.add(label);
		formItem.add(span);
		section.add(formItem);

		if (showSeasons) {
			SelectorUtility util = new SelectorUtility();
			DropdownMenu seasons = (DropdownMenu) util.getSelectorFromIDOEntities(new DropdownMenu(PARAMETER_SEASON_PK), getBusiness().getAllSeasons());
			seasons.keepStatusOnAction(true);

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			label = new Label(getResourceBundle().getLocalizedString("season", "Season"), seasons);
			formItem.add(label);
			formItem.add(seasons);
			section.add(formItem);
		}

		heading = new Heading1(getResourceBundle().getLocalizedString("application.choose_attachments", "Choose attachments"));
		heading.setStyleClass("subHeader");
		form.add(heading);

		section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(getResourceBundle().getLocalizedString("application.attachment_uploader_help", "Please select all the attachments you want to send.")));
		section.add(helpLayer);

		FileInput fileInput = new FileInput();

		formItem = new Layer(Layer.DIV);
		formItem.setStyleClass("formItem");
		label = new Label(getResourceBundle().getLocalizedString("attachment", "Attachment"), fileInput);
		formItem.add(label);
		formItem.add(fileInput);
		section.add(formItem);

		Collection types = getBusiness().getFileTypes();
		Iterator iterator = types.iterator();
		while (iterator.hasNext()) {
			String type = (String) iterator.next();

			RadioButton radio = new RadioButton(PARAMETER_FILE_TYPE, type);
			radio.setStyleClass("radiobutton");

			formItem = new Layer(Layer.DIV);
			formItem.setStyleClass("formItem");
			formItem.setStyleClass("radioButtonItem");
			label = new Label(getResourceBundle().getLocalizedString("file_type." + type, type), radio);
			formItem.add(radio);
			formItem.add(label);
			section.add(formItem);
		}

		Layer editLayer = new Layer(Layer.DIV);
		editLayer.setStyleClass("editLayer");
		section.add(editLayer);

		Link editLink = new Link(new Span(new Text(getResourceBundle().getLocalizedString("more_files", "More files"))));
		editLink.setToFormSubmit(form);
		editLayer.add(editLink);

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");
		section.add(clearLayer);

		Map files = getSession(iwc).getFiles();
		if (!files.isEmpty()) {
			heading = new Heading1(getResourceBundle().getLocalizedString("application.uploaded_attachments_info", "Uploaded attachments information"));
			heading.setStyleClass("subHeader");
			form.add(heading);

			section = new Layer(Layer.DIV);
			section.setStyleClass("formSection");
			form.add(section);

			iterator = files.keySet().iterator();
			while (iterator.hasNext()) {
				ICFile file = (ICFile) iterator.next();
				String fileType = (String) files.get(file);
				IWTimestamp created = new IWTimestamp(file.getCreationDate());

				Link link = new Link(file.getName());
				link.setFile(file);
				link.setTarget(Link.TARGET_BLANK_WINDOW);

				formItem = new Layer(Layer.DIV);
				formItem.setStyleClass("formItem");
				label = new Label();
				label.add(getResourceBundle().getLocalizedString("file_type_info." + fileType, fileType));
				formItem.add(label);
				section.add(formItem);

				span = new Span();
				span.add(link);
				span.add(Text.getNonBrakingSpace());
				span.add("(" + created.getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT) + ")");
				formItem.add(span);
			}

			section.add(clearLayer);
		}

		Collection applicationFiles = application.getFiles();
		if (!applicationFiles.isEmpty()) {
			heading = new Heading1(getResourceBundle().getLocalizedString("application.already_uploaded_attachments_info", "Already uploaded attachments information"));
			heading.setStyleClass("subHeader");
			form.add(heading);

			section = new Layer(Layer.DIV);
			section.setStyleClass("formSection");
			form.add(section);

			iterator = applicationFiles.iterator();
			while (iterator.hasNext()) {
				ApplicationFiles applicationFile = (ApplicationFiles) iterator.next();
				ICFile file = applicationFile.getFile();
				String fileType = applicationFile.getType();
				IWTimestamp created = new IWTimestamp(file.getCreationDate());
				Season season = applicationFile.getSeason();

				Link link = new Link(file.getName());
				link.setFile(file);
				link.setTarget(Link.TARGET_BLANK_WINDOW);

				formItem = new Layer(Layer.DIV);
				formItem.setStyleClass("formItem");
				label = new Label();
				label.add(getResourceBundle().getLocalizedString("file_type_info." + fileType, fileType));
				if (season != null) {
					label.add(" (" + season.getName() + ")");
				}
				formItem.add(label);
				section.add(formItem);

				span = new Span();
				span.add(link);
				span.add(Text.getNonBrakingSpace());
				span.add("(" + created.getLocaleDateAndTime(iwc.getCurrentLocale(), IWTimestamp.SHORT, IWTimestamp.SHORT) + ")");
				formItem.add(span);
			}

			section.add(clearLayer);
		}

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link next = getButtonLink(getResourceBundle().getLocalizedString("next", "Next"));
		next.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_SAVE));
		next.setToFormSubmit(form);
		bottom.add(next);

		if (getBackPage() != null) {
			Link back = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
			back.setPage(getBackPage());
			bottom.add(back);
		}

		add(form);
	}

	private void save(IWContext iwc, Application application) throws RemoteException {
		if (this.iHasErrors) {
			showUploadForm(iwc, application);
			return;
		}

		try {
			getBusiness().attachFiles(application, iwc.isParameterSet(PARAMETER_SEASON_PK) ? iwc.getParameter(PARAMETER_SEASON_PK) : getBusiness().getCurrentSeason().getPrimaryKey(), getSession(iwc).getFiles());
		}
		catch (CreateException e) {
			e.printStackTrace();
		}
		showReceipt(iwc, application.getCompany(), "receipt", "receiptImage", getResourceBundle().getLocalizedString("attachments.uploader", "Attachments uploader"), getResourceBundle().getLocalizedString("attachments.attachments_saved_subject", "Attachments saved"), getResourceBundle().getLocalizedString("attachments.attachments_saved_text", "All the attachments have been successfully saved."));
	}

	private void showReceipt(IWContext iwc, Company company, String title, String subject, String body) {
		showReceipt(iwc, company, "stop", "stopImage", title, subject, body);
	}

	private void showReceipt(IWContext iwc, Company company, String styleClass, String imageStyleClass, String title, String subject, String body) {
		Form form = new Form();
		add(form);

		form.add(getCompanyInfo(iwc, company));

		Layer layer = new Layer(Layer.DIV);
		layer.setStyleClass(styleClass);
		form.add(layer);

		Layer image = new Layer(Layer.DIV);
		image.setStyleClass(imageStyleClass);
		layer.add(image);

		Heading1 heading = new Heading1(subject);
		layer.add(heading);

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Text(body));
		layer.add(paragraph);

		Layer bottom = new Layer(Layer.DIV);
		bottom.setStyleClass("bottom");
		form.add(bottom);

		Link home = getButtonLink(getResourceBundle().getLocalizedString("back", "Back"));
		home.setStyleClass("buttonHome");
		if (getBackPage() != null) {
			home.setPage(getBackPage());
		}
		bottom.add(home);
	}

	private void setError(String parameter, String error) {
		if (this.iErrors == null) {
			this.iErrors = new LinkedHashMap();
		}

		this.iHasErrors = true;
		this.iErrors.put(parameter, error);
	}

	private void addErrors(IWContext iwc, UIComponent parent) {
		if (this.iHasErrors) {
			Layer layer = new Layer(Layer.DIV);
			layer.setStyleClass("errorLayer");

			Layer image = new Layer(Layer.DIV);
			image.setStyleClass("errorImage");
			layer.add(image);

			Heading1 heading = new Heading1(getResourceBundle(iwc).getLocalizedString("application_errors_occured", "There was a problem with the following items"));
			layer.add(heading);

			Lists list = new Lists();
			layer.add(list);

			Iterator iter = this.iErrors.values().iterator();
			while (iter.hasNext()) {
				String element = (String) iter.next();
				ListItem item = new ListItem();
				item.add(new Text(element));

				list.add(item);
			}

			parent.getChildren().add(layer);
		}
	}

	protected ApplicationSession getSession(IWUserContext iwuc) {
		try {
			return (ApplicationSession) IBOLookup.getSessionInstance(iwuc, ApplicationSession.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	public void showSeasons(boolean showSeasons) {
		this.showSeasons = showSeasons;
	}
}