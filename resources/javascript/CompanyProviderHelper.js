CompanyProviderHelper = {};

CompanyProviderHelper.getCompanyByName = function(parameters) {
	if (parameters == null) {
		return false;
	}
	
	showLoadingMessage(parameters.loadingMessage);
	CompanyProvider.getCompanyInfoByName(dwr.util.getValue(parameters.id), {
		callback: function(info) {
			CompanyProviderHelper.receiveCompanyInfo(info, parameters);
		}
	});
}

CompanyProviderHelper.getCompanyByPersonalId = function(parameters) {
	if (parameters == null) {
		return false;
	}
	
	showLoadingMessage(parameters.loadingMessage);
	CompanyProvider.getCompanyInfoByPersonalId(dwr.util.getValue(parameters.id), {
		callback: function(info) {
			CompanyProviderHelper.receiveCompanyInfo(info, parameters);
		}
	});
}

CompanyProviderHelper.receiveCompanyInfo = function(info, parameters) {
	closeAllLoadingMessages();
	if (info == null) {
		if (parameters.id != parameters.nameId) {
			dwr.util.setValue(parameters.nameId, '');
		}
		if (parameters.id != parameters.ssnId) {
			dwr.util.setValue(parameters.ssnId, '');
		}
		dwr.util.setValue(parameters.addressId, '');
		dwr.util.setValue(parameters.postalCodeId, '');
	
		return false;
	}

	dwr.util.setValue(parameters.nameId, info.name);
	dwr.util.setValue(parameters.ssnId, info.personalId);
	dwr.util.setValue(parameters.addressId, info.address);
	dwr.util.setValue(parameters.postalCodeId, info.postalCode);
}