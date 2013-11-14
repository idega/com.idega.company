package com.idega.company.business.impl;

import java.util.Collection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.company.business.CompanyBusiness;
import com.idega.core.business.DefaultSpringBean;
import com.idega.idegaweb.IWMainApplication;
import com.idega.jbpm.bean.JBPMCompany;
import com.idega.jbpm.business.JBPMCompanyBusiness;
import com.idega.user.data.User;

@Service(JBPMCompanyBusiness.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class JBPMCompanyBusinessImpl extends DefaultSpringBean implements JBPMCompanyBusiness{
	
	private CompanyBusiness companyBusiness = null;
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<JBPMCompany> getJBPMCompaniesForUser(User user){
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
