package com.idega.company.companyregister.business;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.CreateException;
import com.idega.business.IBOServiceBean;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyHome;
import com.idega.data.IDOLookup;

public class CompanyRegisterBusinessBean extends IBOServiceBean implements CompanyRegisterBusiness{

	private static final long serialVersionUID = 1014401288441001288L;
	private static Logger logger = Logger.getLogger(CompanyRegisterBusinessBean.class.getName());
	
	public boolean updateEntry(
			String symbol,
			String ssn,
			String dateOfDeath,
			String name,
			String street,
			String commune,
			String gender,
			String maritialStatus,
			String spouseSSN) {
				
			try {
				Company company_registry = null;
				
				if (company_registry == null)
					company_registry = getCompanyRegisterHome().create();
				
				//deceasedReg.setSymbol(symbol);
				
				company_registry.store();
			}
			catch (CreateException e) {
				
				logger.log(Level.SEVERE, "Exception while creating company register entry", e);
				return false;
			}

			return true;
		}
	
	protected CompanyHome getCompanyRegisterHome() {
		CompanyHome home = null;
		try {
			home = (CompanyHome) IDOLookup.getHome(Company.class);
		}
		catch (RemoteException e) {
			logger.log(Level.SEVERE, "Exception while retrieving company BMP bean home", e);
		}

		return home;
	}
}
