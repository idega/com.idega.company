
package com.idega.company.business;


import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.business.IBOService;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyType;
import com.idega.user.data.Group;
import com.idega.user.data.User;

public interface CompanyBusiness extends IBOService {
	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompany
	 */
	public Company getCompany(String personalID) throws FinderException,
			RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompany
	 */
	public Company getCompany(Object pk) throws FinderException,
			RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompany
	 */
	public Company getCompany(Group group) throws FinderException,
			RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompanies
	 */
	public Collection<Company> getCompanies() throws RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getValidCompanies
	 */
	public Collection<Company> getValidCompanies(boolean valid)
			throws RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getActiveCompanies
	 */
	public Collection<Company> getActiveCompanies() throws RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getActiveAndOpenCompanies
	 */
	public Collection<Company> getActiveAndOpenCompanies()
			throws RemoteException;

	/**
	 *
	 * <p>Searches {@link Company}s, where given {@link User} is CEO.</p>
	 * @param user - {@link Company#getCEO()}.
	 * @return {@link List} of {@link Company}s, where user is CEO or
	 * <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Collection<Company> getCompaniesForUser(User user);

	/**
	 *
	 * <p>Searches database for owners of given companies</p>
	 * @param companies - {@link List} of {@link Company}
	 * to search by, not <code>null</code>;
	 * @return {@link List} of {@link User}s, who are owners
	 * of given {@link Company}s, <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Collection<User> getOwnersForCompanies(Collection<Company> companies);

	/**
	 *
	 * <p>Searches database for owners of given companies</p>
	 * @param companies - {@link List} of {@link Company}
	 * to search by, not <code>null</code>;
	 * @return {@link List} of {@link User#getPrimaryKey()}, who are owners
	 * of given {@link Company}s, <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Collection<String> getOwnersIDsForCompanies(Collection<Company> companies);

	/**
	 *
	 * <p>Searches database for owners of given companies</p>
	 * @param companiesIDs - {@link List} of {@link Company#getPrimaryKey()}
	 * to search by, not <code>null</code>;
	 * @return {@link List} of {@link User#getPrimaryKey()}, who are owners
	 * of given {@link Company}s, <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Collection<String> getOwnersIDsForCompaniesByIDs(Collection<String> companiesIDs);

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#storeCompany
	 */
	public Company storeCompany(String name, String personalID)
			throws CreateException, RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompanyType
	 */
	public CompanyType getCompanyType(Object pk) throws FinderException,
			RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getTypes
	 */
	public Collection<CompanyType> getTypes() throws RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#storeCompanyType
	 */
	public void storeCompanyType(String type, String name, String description,
			int order) throws RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompanyByName
	 */
	public Company getCompanyByName(String name) throws FinderException,
			RemoteException, RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompanyByUniqueId
	 */
	public Company getCompanyByUniqueId(String uniqueId) throws FinderException,
			RemoteException, RemoteException;
}