package com.idega.company.companyregister.business;

import com.idega.business.IBOHome;


/**
 * @author Joakim
 *
 */
public interface CompanyRegisterFileImportHandlerHome extends IBOHome {

	public CompanyRegisterFileImportHandler create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
