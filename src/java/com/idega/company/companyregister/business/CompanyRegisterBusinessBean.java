package com.idega.company.companyregister.business;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.idega.business.IBOServiceBean;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyHome;
import com.idega.data.IDOLookup;

public class CompanyRegisterBusinessBean extends IBOServiceBean implements CompanyRegisterBusiness{

	private static final long serialVersionUID = 1014401288441001288L;
	private static Logger logger = Logger.getLogger(CompanyRegisterBusinessBean.class.getName());
	
	public boolean updateEntry(
			String symbol,
			String personal_id,
			String dateOfDeath,
			String name,
			String street,
			String commune,
			String gender,
			String maritialStatus,
			String spouseSSN) {
				
			try {
				
				Company company_registry = getEntryByPersonalId(personal_id);
				
				if (company_registry == null)
					company_registry = getCompanyRegisterHome().create();
				
				//deceasedReg.setSymbol(symbol);
				
				company_registry.store();
			}
			catch (Exception e) {
				
				logger.log(Level.SEVERE, "Exception while creating/updating company register entry", e);
				return false;
			}

			return true;
		}
	
	protected CompanyHome getCompanyRegisterHome() throws RemoteException {
		
		return (CompanyHome) IDOLookup.getHome(Company.class);
	}
	
	public Company getEntryByPersonalId(String personal_id) {
		
		try {
			
			return getCompanyRegisterHome().findByPersonalID(personal_id);
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception while retrieving company BMP bean by personal id", e);
			return null;
		}
	}
}
