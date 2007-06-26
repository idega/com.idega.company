/*
 * $Id: CompanyRegisterBusinessBean.java,v 1.2 2007/06/26 15:26:04 civilis Exp $
 * Created on Nov 21, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.company.companyregister.business;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.business.IBOServiceBean;
import com.idega.company.companyregister.data.CompanyRegister;
import com.idega.company.companyregister.data.CompanyRegisterHome;
import com.idega.data.IDOLookup;

public class CompanyRegisterBusinessBean extends IBOServiceBean implements CompanyRegisterBusiness{
	
	public CompanyRegister getEntryBySSN(String ssn) {
		try {
			Collection c = getNationalRegisterDeceasedHome().findAllBySSN(ssn);

			if (c != null) {
				Iterator it = c.iterator();
				if (it.hasNext()) {
					return (CompanyRegister) it.next();
				}
			}
		}
		catch (RemoteException e) {
			e.printStackTrace(System.err);
		}
		catch (FinderException e) {
			e.printStackTrace(System.err);
		}

		return null;
	}
	
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
				CompanyRegister deceasedReg = getEntryBySSN(ssn);
				if (deceasedReg == null) {
					deceasedReg = getNationalRegisterDeceasedHome().create();
				} 

				deceasedReg.setSymbol(symbol);
				deceasedReg.setSSN(ssn);
				deceasedReg.setDateOfDeath(dateOfDeath);
				deceasedReg.setName(name);
				deceasedReg.setStreet(street);
				deceasedReg.setCommune(commune);
				deceasedReg.setGender(gender);
				deceasedReg.setMaritalStatus(maritialStatus);
				deceasedReg.setSpouseSSN(spouseSSN);
				
				deceasedReg.store();
			}
			catch (CreateException e) {
				e.printStackTrace();
				return false;
			}

			return true;
		}
	
	protected CompanyRegisterHome getNationalRegisterDeceasedHome() {
		CompanyRegisterHome home = null;
		try {
			home = (CompanyRegisterHome) IDOLookup.getHome(CompanyRegister.class);
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}

		return home;
	}
}
