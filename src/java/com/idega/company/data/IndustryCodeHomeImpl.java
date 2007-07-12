package com.idega.company.data;


import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class IndustryCodeHomeImpl extends IDOFactory implements IndustryCodeHome {
	public Class getEntityInterfaceClass() {
		return IndustryCode.class;
	}

	public IndustryCode create() throws CreateException {
		return (IndustryCode) super.createIDO();
	}

	public IndustryCode findByPrimaryKey(Object pk) throws FinderException {
		return (IndustryCode) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAllIndustryCodes() throws FinderException, RemoteException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((IndustryCodeBMPBean) entity).ejbFindAllIndustryCodes();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public IndustryCode findIndustryByUniqueCode(String uniqueId) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((IndustryCodeBMPBean) entity).ejbFindIndustryByUniqueCode(uniqueId);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}