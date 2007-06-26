package com.idega.company.companyregister.business;

import com.idega.business.IBOHomeImpl;


/**
 * @author Joakim
 *
 */
public class CompanyRegisterFileImportHandlerHomeImpl extends IBOHomeImpl implements
		CompanyRegisterFileImportHandlerHome {

	protected Class getBeanInterfaceClass() {
		return CompanyRegisterFileImportHandler.class;
	}

	public CompanyRegisterFileImportHandler create() throws javax.ejb.CreateException {
		return (CompanyRegisterFileImportHandler) super.createIBO();
	}
}
