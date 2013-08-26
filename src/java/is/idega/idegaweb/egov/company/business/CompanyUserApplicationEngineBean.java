package is.idega.idegaweb.egov.company.business;

import java.util.logging.Level;

import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.user.bean.UserDataBean;
import com.idega.user.business.UserApplicationEngineBean;
import com.idega.user.business.UserBusiness;
import com.idega.user.business.UserConstants;
import com.idega.user.data.User;
import com.idega.util.CoreUtil;
import com.idega.util.StringUtil;

public class CompanyUserApplicationEngineBean extends UserApplicationEngineBean {

	private static final long serialVersionUID = 1198909285509307140L;

	@Override
	public UserDataBean getUserByPersonalId(String personalId) {
		if (StringUtil.isEmpty(personalId)) {
			return null;
		}

		IWContext iwc = CoreUtil.getIWContext();
		if (iwc == null) {
			return null;
		}

		UserBusiness userBusiness = getUserBusiness(iwc);
		if (userBusiness == null) {
			return null;
		}

		UserDataBean info = null;

		User user = null;
		try {
			user = userBusiness.getUser(personalId);
		} catch (Exception e) {}
		if (user == null) {
			logger.log(Level.WARNING, "User by was not found by provided personal ID ('" + personalId + "'), trying to find company");
		}
		else {
			info = getUserInfo(user);
		}

		if (info == null && getCompanyHelper() != null) {
			try {
				info = getCompanyHelper().getCompanyInfo(personalId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "Error getting company information by ID: " + personalId, e);
			}
		}

		if (info == null) {
			IWResourceBundle iwrb = iwc.getIWMainApplication().getBundle(UserConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(iwc);
			info = new UserDataBean();
			String errorMessage = new StringBuilder(iwrb.getLocalizedString("no_user_found_by_provided_personal_id", "No user found by provided ID"))
													.append(": ").append(personalId).toString();
			info.setErrorMessage(errorMessage);
		}

		return info;
	}
}
