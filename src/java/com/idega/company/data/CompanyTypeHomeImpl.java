package com.idega.company.data;


import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class CompanyTypeHomeImpl extends IDOFactory implements CompanyTypeHome {

	public Class getEntityInterfaceClass() {
		return CompanyType.class;
	}

	public CompanyType create() throws CreateException {
		return (CompanyType) super.createIDO();
	}

	public CompanyType findByPrimaryKey(Object pk) throws FinderException {
		return (CompanyType) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findAll() throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection ids = ((CompanyTypeBMPBean) entity).ejbFindAll();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}
}