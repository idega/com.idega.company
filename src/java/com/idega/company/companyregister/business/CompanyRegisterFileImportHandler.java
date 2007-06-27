package com.idega.company.companyregister.business;


import com.idega.user.data.Group;
import com.idega.business.IBOService;
import java.util.List;
import com.idega.block.importer.business.ImportFileHandler;
import java.rmi.RemoteException;
import com.idega.block.importer.data.ImportFile;

public interface CompanyRegisterFileImportHandler extends IBOService, ImportFileHandler {

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#getFailedRecords
	 */
	public List getFailedRecords() throws RemoteException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#handleRecords
	 */
	public boolean handleRecords() throws RemoteException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#printFailedRecords
	 */
	public void printFailedRecords() throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#setImportFile
	 */
	public void setImportFile(ImportFile file) throws RemoteException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#setRootGroup
	 */
	public void setRootGroup(Group rootGroup) throws RemoteException, RemoteException;
}