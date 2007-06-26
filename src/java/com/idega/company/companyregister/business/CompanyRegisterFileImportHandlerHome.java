package com.idega.company.companyregister.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHome;
import java.rmi.RemoteException;

public interface CompanyRegisterFileImportHandlerHome extends IBOHome {

	public CompanyRegisterFileImportHandler create() throws CreateException, RemoteException;
}