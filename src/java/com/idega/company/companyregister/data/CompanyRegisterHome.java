package com.idega.company.companyregister.data;


import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;

public interface CompanyRegisterHome extends IDOHome {

	public CompanyRegister create() throws CreateException;

	public CompanyRegister findByPrimaryKey(Object pk) throws FinderException;
	
	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#ejbFindAll
	 */
	public Collection findAll() throws FinderException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#ejbFindAllBySSN
	 */
	public Collection findAllBySSN(String ssn) throws FinderException, RemoteException;

}