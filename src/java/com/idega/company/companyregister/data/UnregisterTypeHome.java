package com.idega.company.companyregister.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface UnregisterTypeHome extends IDOHome {
	public UnregisterType create() throws CreateException;

	public UnregisterType findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAllUnregisterTypes() throws FinderException, RemoteException;

	public UnregisterType findUnregisterTypeByUniqueCode(String uniqueId) throws FinderException;
}