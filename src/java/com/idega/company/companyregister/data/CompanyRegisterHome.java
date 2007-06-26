package com.idega.company.companyregister.data;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.FinderException;
import com.idega.data.IDOHome;


/**
 * @author Joakim
 *
 */
public interface CompanyRegisterHome extends IDOHome {

	public CompanyRegister create() throws javax.ejb.CreateException, java.rmi.RemoteException;

	public CompanyRegister findByPrimaryKey(Object pk) throws javax.ejb.FinderException, java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#ejbFindAll
	 */
	public Collection findAll() throws FinderException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#ejbFindAllBySSN
	 */
	public Collection findAllBySSN(String ssn) throws FinderException, RemoteException;
}
