package com.idega.company.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface OperationFormHome extends IDOHome {
	public OperationForm create() throws CreateException;

	public OperationForm findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAllOperationForms() throws FinderException, RemoteException;

	public OperationForm findOperationFormByUniqueCode(String uniqueId) throws FinderException;
}