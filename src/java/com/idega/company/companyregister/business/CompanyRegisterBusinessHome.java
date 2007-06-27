package com.idega.company.companyregister.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHome;
import java.rmi.RemoteException;

public interface CompanyRegisterBusinessHome extends IBOHome {
	public CompanyRegisterBusiness create() throws CreateException, RemoteException;
}