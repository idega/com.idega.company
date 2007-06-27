package com.idega.company.data;


import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class OperationFormHomeImpl extends IDOFactory implements OperationFormHome {
	
	private static final long serialVersionUID = -354152141433766244L;

	public Class getEntityInterfaceClass() {
		return OperationForm.class;
	}

	public OperationForm create() throws CreateException {
		return (OperationForm) super.createIDO();
	}

	public OperationForm findByPrimaryKey(Object pk) throws FinderException {
		return (OperationForm) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAllOperationForms() throws FinderException, RemoteException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((OperationFormBMPBean) entity).ejbFindAllOperationForms();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public OperationForm findOperationFormByUniqueCode(String uniqueId) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((OperationFormBMPBean) entity).ejbFindOperationFormByUniqueCode(uniqueId);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}