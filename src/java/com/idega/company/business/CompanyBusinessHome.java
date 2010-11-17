package com.idega.company.business;


import javax.ejb.CreateException;
import java.rmi.RemoteException;
import com.idega.business.IBOHome;

public interface CompanyBusinessHome extends IBOHome {
	public CompanyBusiness create() throws CreateException, RemoteException;
}