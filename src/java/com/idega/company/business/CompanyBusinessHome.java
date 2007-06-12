package com.idega.company.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHome;
import java.rmi.RemoteException;

public interface CompanyBusinessHome extends IBOHome {

	public CompanyBusiness create() throws CreateException, RemoteException;
}