package com.idega.company.companyregister.business;


import com.idega.business.IBOService;
import com.idega.company.companyregister.data.CompanyRegister;

public interface CompanyRegisterBusiness extends IBOService {
	
	/**
	 * @see is.idega.block.nationalregister.business.NationalRegisterBusinessBean#getEntryBySSN
	 */
	public CompanyRegister getEntryBySSN(String ssn) throws java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.NationalRegisterBusinessBean#updateEntry
	 */
	public boolean updateEntry(String symbol, String ssn, String dateOfDeath, String name,
			String street, String commune, String gender, String maritialStatus, String spouseSSN)
			throws java.rmi.RemoteException;

}