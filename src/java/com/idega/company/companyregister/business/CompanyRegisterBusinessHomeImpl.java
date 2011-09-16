package com.idega.company.companyregister.business;


import javax.ejb.CreateException;

import com.idega.business.IBOHomeImpl;

public class CompanyRegisterBusinessHomeImpl extends IBOHomeImpl implements CompanyRegisterBusinessHome {
	
	private static final long serialVersionUID = 1647408867154141683L;

	@Override
	public Class<CompanyRegisterBusiness> getBeanInterfaceClass() {
		return CompanyRegisterBusiness.class;
	}

	public CompanyRegisterBusiness create() throws CreateException {
		return (CompanyRegisterBusiness) super.createIBO();
	}
}