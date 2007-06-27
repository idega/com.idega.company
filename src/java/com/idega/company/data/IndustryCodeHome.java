package com.idega.company.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface IndustryCodeHome extends IDOHome {
	public IndustryCode create() throws CreateException;

	public IndustryCode findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAllUnregisterTypes() throws FinderException, RemoteException;
}