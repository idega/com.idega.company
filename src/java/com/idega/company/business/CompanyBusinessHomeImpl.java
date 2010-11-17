package com.idega.company.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHomeImpl;

public class CompanyBusinessHomeImpl extends IBOHomeImpl implements
		CompanyBusinessHome {
	public Class getBeanInterfaceClass() {
		return CompanyBusiness.class;
	}

	public CompanyBusiness create() throws CreateException {
		return (CompanyBusiness) super.createIBO();
	}
}