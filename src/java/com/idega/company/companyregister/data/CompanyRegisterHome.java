package com.idega.company.companyregister.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface CompanyRegisterHome extends IDOHome {
	public CompanyRegister create() throws CreateException;

	public CompanyRegister findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAll() throws FinderException, RemoteException;
}