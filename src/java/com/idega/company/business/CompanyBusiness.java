package com.idega.company.business;


import com.idega.user.data.Group;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.business.IBOService;
import com.idega.company.data.CompanyType;
import java.rmi.RemoteException;
import com.idega.company.data.Company;

public interface CompanyBusiness extends IBOService {

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompany
	 */
	public Company getCompany(String personalID) throws FinderException, RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompany
	 */
	public Company getCompany(Object pk) throws FinderException, RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompany
	 */
	public Company getCompany(Group group) throws FinderException, RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompanies
	 */
	public Collection<Company> getCompanies() throws RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getValidCompanies
	 */
	public Collection<Company> getValidCompanies(boolean valid) throws RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getActiveCompanies
	 */
	public Collection<Company> getActiveCompanies() throws RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#storeCompany
	 */
	public Company storeCompany(String name, String personalID) throws CreateException, RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getCompanyType
	 */
	public CompanyType getCompanyType(Object pk) throws FinderException, RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#getTypes
	 */
	public Collection<CompanyType> getTypes() throws RemoteException;

	/**
	 * @see com.idega.company.business.CompanyBusinessBean#storeCompanyType
	 */
	public void storeCompanyType(String type, String name, String description, int order) throws RemoteException;
	
	public Company getCompanyByName(String name) throws FinderException, RemoteException;
}