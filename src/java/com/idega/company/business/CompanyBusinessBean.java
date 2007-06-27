/*
 * $Id$ Created on Jun 4, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package com.idega.company.business;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.business.IBOServiceBean;
import com.idega.company.CompanyConstants;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyHome;
import com.idega.company.data.CompanyType;
import com.idega.company.data.CompanyTypeHome;
import com.idega.data.IDORuntimeException;
import com.idega.user.data.Group;

public class CompanyBusinessBean extends IBOServiceBean implements CompanyBusiness {

	private static final long serialVersionUID = -2466677771702218426L;

	private CompanyHome getCompanyHome() {
		try {
			return (CompanyHome) getIDOHome(Company.class);
		}
		catch (RemoteException re) {
			throw new IDORuntimeException(re.getMessage());
		}
	}

	private CompanyTypeHome getCompanyTypeHome() {
		try {
			return (CompanyTypeHome) getIDOHome(CompanyType.class);
		}
		catch (RemoteException re) {
			throw new IDORuntimeException(re.getMessage());
		}
	}

	// Company
	public Company getCompany(String personalID) throws FinderException {
		return getCompanyHome().findByPersonalID(personalID);
	}

	public Company getCompany(Object pk) throws FinderException {
		return getCompanyHome().findByPrimaryKey(new Integer(pk.toString()));
	}

	public Company getCompany(Group group) throws FinderException {
		if (group.getGroupType().equals(CompanyConstants.GROUP_TYPE_COMPANY)) {
			return getCompany(group.getPrimaryKey());
		}
		return null;
	}

	public Collection getCompanies() {
		return getCompanies(null);
	}

	public Collection getValidCompanies(boolean valid) {
		return getCompanies(new Boolean(valid));
	}

	private Collection getCompanies(Boolean valid) {
		try {
			return getCompanyHome().findAll(valid);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public Company storeCompany(String name, String personalID) throws CreateException {
		Company company = getCompanyHome().create();
		company.setName(name);
		company.setPersonalID(personalID);
		company.store();

		return company;
	}

	// Company type
	public CompanyType getCompanyType(Object pk) throws FinderException {
		return getCompanyTypeHome().findByPrimaryKey(pk);
	}

	public Collection getTypes() {
		try {
			return getCompanyTypeHome().findAll();
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList();
		}
	}

	public void storeCompanyType(String type, String name, String description, int order) {
		try {
			CompanyType companyType = getCompanyTypeHome().create();
			companyType.setType(type);
			companyType.setName(name);
			companyType.setLocalizedKey("company_type." + type);
			companyType.setDescription(description);
			companyType.setOrder(order);
			companyType.store();
		}
		catch (CreateException e) {
			e.printStackTrace();
		}
	}
}