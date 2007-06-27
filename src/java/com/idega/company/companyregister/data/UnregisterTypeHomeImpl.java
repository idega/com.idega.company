package com.idega.company.companyregister.data;


import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class UnregisterTypeHomeImpl extends IDOFactory implements UnregisterTypeHome {
	public Class getEntityInterfaceClass() {
		return UnregisterType.class;
	}

	public UnregisterType create() throws CreateException {
		return (UnregisterType) super.createIDO();
	}

	public UnregisterType findByPrimaryKey(Object pk) throws FinderException {
		return (UnregisterType) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAllUnregisterTypes() throws FinderException, RemoteException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((UnregisterTypeBMPBean) entity).ejbFindAllUnregisterTypes();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public UnregisterType findUnregisterTypeByUniqueCode(String uniqueId) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((UnregisterTypeBMPBean) entity).ejbFindUnregisterTypeByUniqueCode(uniqueId);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}