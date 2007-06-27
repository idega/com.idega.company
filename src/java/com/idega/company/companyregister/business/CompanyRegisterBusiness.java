package com.idega.company.companyregister.business;


import com.idega.business.IBOService;
import com.idega.company.data.Company;
import java.rmi.RemoteException;

public interface CompanyRegisterBusiness extends IBOService {
	/**
	 * @see com.idega.company.companyregister.business.CompanyRegisterBusinessBean#updateEntry
	 */
	public boolean updateEntry(String personal_id, String commune, String postalCode, String workingArea, String orderAreaForName, String name, String address, String ceoId, String dateOfLastChange, String operationForm, String vatNumber, String legalAddress, String registerDate, String operation, String recipientPersonalId, String recipientName, String industryCode, String unregistrationType, String unregistrationDate, String banMarking) throws RemoteException;

	/**
	 * @see com.idega.company.companyregister.business.CompanyRegisterBusinessBean#getEntryByPersonalId
	 */
	public Company getEntryByPersonalId(String personal_id) throws RemoteException;
}