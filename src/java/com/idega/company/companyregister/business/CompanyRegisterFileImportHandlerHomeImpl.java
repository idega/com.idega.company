package com.idega.company.companyregister.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHomeImpl;

public class CompanyRegisterFileImportHandlerHomeImpl extends IBOHomeImpl implements CompanyRegisterFileImportHandlerHome {

	private static final long serialVersionUID = -8932702155122430819L;

	public Class getBeanInterfaceClass() {
		return CompanyRegisterFileImportHandler.class;
	}

	public CompanyRegisterFileImportHandler create() throws CreateException {
		return (CompanyRegisterFileImportHandler) super.createIBO();
	}
}