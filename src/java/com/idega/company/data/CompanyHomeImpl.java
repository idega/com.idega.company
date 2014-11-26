package com.idega.company.data;


import java.util.Collection;
import java.util.logging.Level;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOCreateException;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;
import com.idega.user.data.User;

public class CompanyHomeImpl extends IDOFactory implements CompanyHome {

	private static final long serialVersionUID = -3167879639586671567L;

	@Override
	public Class getEntityInterfaceClass() {
		return Company.class;
	}

	@Override
	public Company findByPrimaryKey(Object pk) throws FinderException {
		if (pk instanceof String) {
			try {
				pk = Integer.valueOf((String) pk);
			} catch (Exception e) {
				return null;
			}
		}
		return (Company) super.findByPrimaryKeyIDO(pk);
	}

	@Override
	public Company create() throws CreateException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CompanyBMPBean) entity).ejbCreate();
		((CompanyBMPBean) entity).ejbPostCreate();
		this.idoCheckInPooledEntity(entity);
		try {
			return findByPrimaryKey(pk);
		} catch (FinderException fe) {
			throw new IDOCreateException(fe);
		} catch (Exception e) {
			throw new IDOCreateException(e);
		}
	}

	@Override
	public Company findByPersonalID(String personalID) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CompanyBMPBean) entity).ejbFindByPersonalID(personalID);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	@Override
	public Company findByName(String name) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CompanyBMPBean) entity).ejbFindByName(name);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

	@Override
	public Collection<Company> findAll(Boolean valid) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Company> ids = ((CompanyBMPBean) entity).ejbFindAll(valid);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Company> findAllWithOpenStatus() throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Company> ids = ((CompanyBMPBean) entity)
				.ejbFindAllWithOpenStatus();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Company> findAllActiveWithOpenStatus()
			throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Company> ids = ((CompanyBMPBean) entity)
				.ejbFindAllActiveWithOpenStatus();
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	@Override
	public Collection<Company> findAll(User user) {
		if (user == null) {
			java.util.logging.Logger.getLogger(getClass().getName()).log(
					Level.WARNING, User.class +
					" must be not null.");
			return null;
		}

		IDOEntity entity = this.idoCheckOutPooledEntity();
		Collection<Company> ids = null;
		try {
			ids = ((CompanyBMPBean) entity).ejbFindAll(user);
		} catch (FinderException e) {
			java.util.logging.Logger.getLogger(getClass().getName()).log(
					Level.WARNING, "Unable to find companies.", e);
		}
		this.idoCheckInPooledEntity(entity);
		try {
			return this.getEntityCollectionForPrimaryKeys(ids);
		} catch (FinderException e) {
			java.util.logging.Logger.getLogger(getClass().getName()).log(
					Level.WARNING, "Unable to find companies.", e);
			return null;
		}
	}

	@Override
	public Company findByUniqueId(String uniqueId) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((CompanyBMPBean) entity).ejbFindByUniqueId(uniqueId);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}

}