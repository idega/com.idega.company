package com.idega.company.data;

import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.data.IDOCreateException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class CompanyHomeImpl extends IDOFactory implements CompanyHome {

	public Class getEntityInterfaceClass() {
		return Company.class;
	}

	public Company findByPrimaryKey(Object pk) throws FinderException {
		return (Company) super.findByPrimaryKeyIDO(pk);
	}

	public Company create() throws CreateException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CompanyBMPBean) entity).ejbCreate();
		((CompanyBMPBean) entity).ejbPostCreate();
		this.idoCheckInPooledEntity(entity);
		try {
			return findByPrimaryKey(pk);
		}
		catch (FinderException fe) {
			throw new IDOCreateException(fe);
		}
		catch (Exception e) {
			throw new IDOCreateException(e);
		}
	}

	public Company findByPersonalID(String personalID) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CompanyBMPBean) entity).ejbFindByPersonalID(personalID);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	public Collection findAll(Boolean valid) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CompanyBMPBean) entity).ejbFindAll(valid);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public Collection findAllWithOpenStatus() throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CompanyBMPBean) entity).ejbFindAllWithOpenStatus();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}