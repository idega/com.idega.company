package com.idega.company.business;


import javax.ejb.CreateException;
import com.idega.business.IBOHomeImpl;

public class CompanyBusinessHomeImpl extends IBOHomeImpl implements CompanyBusinessHome {

	private static final long serialVersionUID = 1089566462882467926L;

	@Override
	public Class<CompanyBusiness> getBeanInterfaceClass() {
		return CompanyBusiness.class;
	}

	public CompanyBusiness create() throws CreateException {
		return (CompanyBusiness) super.createIBO();
	}
}