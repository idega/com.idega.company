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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;

import com.idega.business.IBOLookupException;
import com.idega.business.IBOServiceBean;
import com.idega.company.CompanyConstants;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyHome;
import com.idega.company.data.CompanyType;
import com.idega.company.data.CompanyTypeHome;
import com.idega.data.IDORuntimeException;
import com.idega.data.SimpleQuerier;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.MetadataConstants;
import com.idega.user.data.User;
import com.idega.util.ArrayUtil;
import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;


public class CompanyBusinessBean extends IBOServiceBean implements CompanyBusiness{



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
	@Override
	public Company getCompany(String personalID) throws FinderException {
		if (StringUtil.isEmpty(personalID)) {
			return null;
		}

		return getCompanyHome().findByPersonalID(personalID);
	}

	@Override
	public Company getCompany(Object pk) throws FinderException {
		try {
			return getCompanyHome().findByPrimaryKey(pk);
		} catch (Exception e) {}

		return null;
	}

	@Override
	public Company getCompany(Group group) throws FinderException {
		if (group.getGroupType().equals(CompanyConstants.GROUP_TYPE_COMPANY)) {
			return getCompany(group.getPrimaryKey());
		}
		return null;
	}

	@Override
	public Collection<Company> getCompanies() {
		return getCompanies(null);
	}

	@Override
	public Collection<Company> getValidCompanies(boolean valid) {
		return getCompanies(new Boolean(valid));
	}

	private Collection<Company> getCompanies(Boolean valid) {
		try {
			return getCompanyHome().findAll(valid);
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public Collection<Company> getActiveCompanies() {
		try {
			return getCompanyHome().findAllWithOpenStatus();
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public Collection<Company> getActiveAndOpenCompanies() {
		try {
			return getCompanyHome().findAllActiveWithOpenStatus();
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public Company storeCompany(String name, String personalID) throws CreateException {
		Company company = getCompanyHome().create();
		company.setName(name);
		company.setPersonalID(personalID);
		company.store();

		return company;
	}

	// Company type
	@Override
	public CompanyType getCompanyType(Object pk) throws FinderException {
		return getCompanyTypeHome().findByPrimaryKey(pk);
	}

	@Override
	public Collection<CompanyType> getTypes() {
		try {
			return getCompanyTypeHome().findAll();
		}
		catch (FinderException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
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

	@Override
	public Company getCompanyByName(String name) throws FinderException, RemoteException {
		if (StringUtil.isEmpty(name)) {
			return null;
		}

		return getCompanyHome().findByName(name);
	}

	@Override
	public Collection<Company> getCompaniesForUser(User user) {
		Collection<Company> companies = new ArrayList<>();
		Collection<Company> managedCompanies = getCompanyHome().findAll(user);
		if (!ListUtil.isEmpty(managedCompanies)) {
			companies.addAll(managedCompanies);
		}

		Set<String> companiesIds = new HashSet<>();

		Collection<Group> groups = null;
		try {
			groups = getUserBusiness().getUserGroupsDirectlyRelated(user);
			if (!ListUtil.isEmpty(groups)) {
				for (Group group: groups) {
					String type = group == null ? null : group.getType();
					if (!StringUtil.isEmpty(type) && CompanyConstants.GROUP_TYPE_COMPANY.equals(type)) {
						companiesIds.add(group.getId());
					}
				}
			}
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error while checking if " + user + " is in any company's group. User's groups: " + groups, e);
		}

		String ownedCompanyID = user.getMetaData(MetadataConstants.USER_REAL_COMPANY_META_DATA_KEY);
		if (!StringUtil.isEmpty(ownedCompanyID)) {
			companiesIds.add(ownedCompanyID);
		}

		if (ListUtil.isEmpty(companiesIds)) {
			return companies;
		}

		for (String companyId: companiesIds) {
			Company ownedCompany = null;
			try {
				ownedCompany = getCompanyHome().findByPrimaryKey(companyId);
			} catch (FinderException e) {
				getLogger().log(Level.WARNING, "Unable to find " + Company.class + " by primary key: " + ownedCompanyID);
			}
			if (ownedCompany != null) {
				companies.add(ownedCompany);
			}
		}

		return companies;
	}

	protected UserBusiness getUserBusiness() {
		try {
			return getServiceInstance(UserBusiness.class);
		} catch (IBOLookupException e) {
			getLogger().log(Level.WARNING, "Unable to get " + UserBusiness.class, e);
		}

		return null;
	}

	@Override
	public Collection<User> getOwnersForCompanies(Collection<Company> companies) {
		Collection<String> ids = getOwnersIDsForCompanies(companies);
		try {
			return getUserBusiness().getUsers(ids.toArray(new String[ids.size()]));
		} catch (EJBException e) {
			getLogger().log(Level.WARNING, "Unable to get " + User.class, e);
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to get " + User.class, e);
		}
		return null;
	}

	@Override
	public Collection<String> getOwnersIDsForCompanies(Collection<Company> companies) {
		if (ListUtil.isEmpty(companies)) {
			return null;
		}

		ArrayList<String> idsOfCompanies = new ArrayList<>();
		for (Company company: companies) {
			idsOfCompanies.add(company.getPrimaryKey().toString());
		}

		return getOwnersIDsForCompaniesByIDs(idsOfCompanies);
	}

	@Override
	public Collection<String> getOwnersIDsForCompaniesByIDs(Collection<String> companiesIDs) {
		if (ListUtil.isEmpty(companiesIDs)) {
			return null;
		}

		StringBuilder companiesIDsString = new StringBuilder();
		for (Iterator<String> iterator = companiesIDs.iterator(); iterator.hasNext();) {
			companiesIDsString.append(CoreConstants.QOUTE_SINGLE_MARK)
			.append(iterator.next())
			.append(CoreConstants.QOUTE_SINGLE_MARK);

			if (iterator.hasNext()) {
				companiesIDsString.append(CoreConstants.COMMA)
				.append(CoreConstants.SPACE);
			}
		}

		StringBuilder sb = new StringBuilder("SELECT a.IC_USER_ID FROM ");
		sb.append("ic_metadata_ic_user a, ")
		.append("ic_metadata b ")
		.append("WHERE b.METADATA_VALUE in (")
		.append(companiesIDsString.toString()).append(") ")
		.append("AND b.METADATA_NAME='")
		.append(MetadataConstants.USER_REAL_COMPANY_META_DATA_KEY).append("' ")
		.append("AND a.IC_METADATA_ID=b.IC_METADATA_ID");

		String[] ownerIDs = null;
		try {
			ownerIDs = SimpleQuerier.executeStringQuery(sb.toString());
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Unable to find owners of companies: ", e);
		}

		if (ArrayUtil.isEmpty(ownerIDs)) {
			return null;
		}

		return Arrays.asList(ownerIDs);
	}

	@Override
	public Company getCompanyByUniqueId(String uniqueId) throws FinderException, RemoteException {
		if (StringUtil.isEmpty(uniqueId)) {
			return null;
		}

		return getCompanyHome().findByUniqueId(uniqueId);
	}

}