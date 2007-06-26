package com.idega.company.companyregister.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHomeImpl;

public class CompanyRegisterFileImportHandlerHomeImpl extends IBOHomeImpl implements CompanyRegisterFileImportHandlerHome {

	public Class getBeanInterfaceClass() {
		return CompanyRegisterFileImportHandler.class;
	}

	public CompanyRegisterFileImportHandler create() throws CreateException {
		return (CompanyRegisterFileImportHandler) super.createIBO();
	}
}