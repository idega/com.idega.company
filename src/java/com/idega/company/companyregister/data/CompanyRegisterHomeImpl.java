package com.idega.company.companyregister.data;


import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
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
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CompanyRegisterBMPBean) entity).ejbFindAll();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllBySSN(String ssn) throws FinderException, RemoteException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((CompanyRegisterBMPBean) entity).ejbFindAllBySSN(ssn);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}