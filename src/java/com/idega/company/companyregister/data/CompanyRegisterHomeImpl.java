package com.idega.company.companyregister.data;


import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class CompanyRegisterHomeImpl extends IDOFactory implements CompanyRegisterHome {
	public Class getEntityInterfaceClass() {
		return CompanyRegister.class;
	}

	public CompanyRegister create() throws CreateException {
		return (CompanyRegister) super.createIDO();
	}

	public CompanyRegister findByPrimaryKey(Object pk) throws FinderException {
		return (CompanyRegister) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAll() throws FinderException, RemoteException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CompanyRegisterBMPBean) entity).ejbFindAll();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}