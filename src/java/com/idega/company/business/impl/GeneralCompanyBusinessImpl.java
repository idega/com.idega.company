package com.idega.company.business.impl;

import java.util.Collection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.company.business.CompanyBusiness;
import com.idega.core.business.DefaultSpringBean;
import com.idega.core.business.GeneralCompanyBusiness;
import com.idega.core.company.bean.GeneralCompany;
import com.idega.idegaweb.IWMainApplication;
import com.idega.user.data.User;

@Service(GeneralCompanyBusiness.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class GeneralCompanyBusinessImpl extends DefaultSpringBean implements GeneralCompanyBusiness{
	
	private CompanyBusiness companyBusiness = null;
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<GeneralCompany> getJBPMCompaniesForUser(User user){
		Collection companies = getCompanyBusiness().getCompaniesForUser(user);
		return companies;
	}


	public CompanyBusiness getCompanyBusiness() {
		if(companyBusiness == null){
			try {
				companyBusiness = IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CompanyBusiness.class);
			} catch (IBOLookupException e) {
				throw new RuntimeException("failed getting CompanyBusiness",e);
			}
		}
		return companyBusiness;
	}

}
