package com.idega.company.companyregister.business;


import com.idega.business.IBOService;

public interface CompanyRegisterBusiness extends IBOService {
	
	public boolean updateEntry(String symbol, String ssn, String dateOfDeath, String name,
			String street, String commune, String gender, String maritialStatus, String spouseSSN)
			throws java.rmi.RemoteException;
}