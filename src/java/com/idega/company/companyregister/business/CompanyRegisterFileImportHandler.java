package com.idega.company.companyregister.business;

import is.idega.block.family.business.FamilyLogic;
import java.rmi.RemoteException;
import java.util.List;
import com.idega.block.importer.data.ImportFile;
import com.idega.user.data.Group;


/**
 * @author Joakim
 *
 */
public interface CompanyRegisterFileImportHandler {

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#handleRecords
	 */
	public boolean handleRecords() throws RemoteException, java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#printFailedRecords
	 */
	public void printFailedRecords() throws java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#setImportFile
	 */
	public void setImportFile(ImportFile file) throws RemoteException, java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#setRootGroup
	 */
	public void setRootGroup(Group rootGroup) throws RemoteException, java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#getFailedRecords
	 */
	public List getFailedRecords() throws RemoteException, java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterFileImportHandlerBean#getMemberFamilyLogic
	 */
	public FamilyLogic getMemberFamilyLogic() throws RemoteException, java.rmi.RemoteException;
}
