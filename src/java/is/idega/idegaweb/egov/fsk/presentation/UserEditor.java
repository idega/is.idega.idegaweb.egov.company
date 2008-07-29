/*
 * $Id: UserEditor.java,v 1.1 2008/07/29 10:48:18 anton Exp $
 * Created on Jul 22, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package is.idega.idegaweb.egov.fsk.presentation;

import is.idega.idegaweb.egov.fsk.FSKConstants;

import java.rmi.RemoteException;

import com.idega.core.builder.business.BuilderServiceFactory;
import com.idega.presentation.IWContext;
import com.idega.user.app.SimpleUserApp;

public class UserEditor extends FSKBlock {

	public void present(IWContext iwc) {
		if (!iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc) && !iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_COMPANY_ADMIN, iwc)) {
			showNoPermission(iwc);
			return;
		}

		SimpleUserApp app = new SimpleUserApp();
		try {
			app.setInstanceId(BuilderServiceFactory.getBuilderService(iwc).getInstanceId(this));
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		app.setGroupTypes("iw_company");
		app.setGroupTypesForChildGroups("general, permission, fsk_division");
		if (iwc.getAccessController().hasRole(FSKConstants.ROLE_KEY_FSK_ADMIN, iwc)) {
			app.setUseChildrenOfTopNodesAsParentGroups(true);
		}
		add(app);
	}
}