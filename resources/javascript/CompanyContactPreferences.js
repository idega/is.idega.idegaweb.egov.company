jQuery.noConflict();

jQuery(document).ready(function() {
	jQuery('#userSSNInput').keyup(function() {
		var ssn = jQuery(this).val();
		if (ssn.length == 10) {
			showLoadingMessage('Loading...');
			CompanyApplicationBusiness.getUser(ssn, {
				callback: function(userInfo) {
					closeAllLoadingMessages();

					if (userInfo != null) {
						jQuery('#userNameInput').val(userInfo.userName);
						jQuery('#userNameHidden').val(userInfo.userName);
						jQuery('input[name="cap_email"]').val(userInfo.userEmail);
						jQuery('input[name="cap_phn_w"]').val(userInfo.userWorkPhone);
						jQuery('input[name="cap_phn_m"]').val(userInfo.userMobilePhone);
					}
					else {
						jQuery('#userNameInput').val('');
						jQuery('#userNameHidden').val('');
						jQuery('input[name="cap_email"]').val('');
						jQuery('input[name="cap_phn_w"]').val('');
						jQuery('input[name="cap_phn_m"]').val('');
					}
				}
			});
		}
	});
});