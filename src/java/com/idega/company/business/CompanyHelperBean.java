package com.idega.company.business;

import java.rmi.RemoteException;
import java.util.logging.Level;

import javax.ejb.FinderException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.company.data.Company;
import com.idega.core.business.DefaultSpringBean;
import com.idega.core.location.data.Address;
import com.idega.idegaweb.IWMainApplication;
import com.idega.user.bean.UserDataBean;
import com.idega.user.business.CompanyHelper;
import com.idega.user.business.UserApplicationEngine;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

@Scope(BeanDefinition.SCOPE_SINGLETON)
@Service(CompanyHelper.SPRING_BEAN_IDENTIFIER)
public class CompanyHelperBean extends DefaultSpringBean implements CompanyHelper {

	//	It's a Spring bean!
	private CompanyHelperBean() {}

	@Override
	public UserDataBean getCompanyInfo(String companyPersonalId) {
		Company company = getCompany(companyPersonalId);
		if (company == null) {
			return null;
		}

		UserDataBean companyInfo = new UserDataBean();

		companyInfo.setName(company.getName());
		companyInfo.setPersonalId(company.getPersonalID());
		companyInfo.setGroupId((Integer) company.getPrimaryKey());

		try {
			getUserAppEngine().fillUserInfo(companyInfo, company.getPhone(), company.getEmail(), company.getAddress());
		} catch(Exception e) {
			getLogger().log(Level.WARNING, "Error filling company (personal ID: " + companyPersonalId + ") info!", e);
		}

		return companyInfo;
	}

	private UserApplicationEngine getUserAppEngine() {
		return ELUtil.getInstance().getBean(UserApplicationEngine.SPRING_BEAN_IDENTIFIER);
	}

	@Override
	public Address getCompanyAddress(String companyPersonalId) {
		Company company = getCompany(companyPersonalId);
		if (company == null) {
			return null;
		}
		return company.getAddress();
	}

	private Company getCompany(String companyPersonalId) {
		if (StringUtil.isEmpty(companyPersonalId)) {
			return null;
		}

		CompanyBusiness companyBusiness = null;
		try {
			companyBusiness = IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CompanyBusiness.class);
		} catch (IBOLookupException e) {
			e.printStackTrace();
		}
		if (companyBusiness == null) {
			return null;
		}

		try {
			return companyBusiness.getCompany(companyPersonalId);
		} catch (RemoteException e) {
		} catch (FinderException e) {}

		getLogger().warning("Company was not found by provided ID: " + companyPersonalId);
		return null;
	}

}