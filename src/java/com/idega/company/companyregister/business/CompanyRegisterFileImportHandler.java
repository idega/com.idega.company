package com.idega.company.companyregister.business;


import java.rmi.RemoteException;

import com.idega.block.importer.business.ImportFileHandler;
import com.idega.block.importer.data.ImportFile;
import com.idega.business.IBOService;
import com.idega.user.data.Group;

public interface CompanyRegisterFileImportHandler extends IBOService, ImportFileHandler {

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#handleRecords
	 */
	@Override
	public boolean handleRecords() throws RemoteException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#printFailedRecords
	 */
	public void printFailedRecords() throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#setImportFile
	 */
	@Override
	public void setImportFile(ImportFile file) throws RemoteException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#setRootGroup
	 */
	@Override
	public void setRootGroup(Group rootGroup) throws RemoteException, RemoteException;
}