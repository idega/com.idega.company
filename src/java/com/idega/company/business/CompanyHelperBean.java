package com.idega.company.business;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.FinderException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.company.data.Company;
import com.idega.idegaweb.IWMainApplication;
import com.idega.user.bean.UserDataBean;
import com.idega.user.business.CompanyHelper;
import com.idega.user.business.UserApplicationEngine;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

@Scope("singleton")
@Service(CompanyHelper.SPRING_BEAN_IDENTIFIER)
public class CompanyHelperBean implements CompanyHelper {

	private static final Logger logger = Logger.getLogger(CompanyHelperBean.class.getName());
	
	//	It's a Spring bean!
	private CompanyHelperBean() {}
	
	public UserDataBean getCompanyInfo(String companyPersonalId) {
		if (StringUtil.isEmpty(companyPersonalId)) {
			return null;
		}
		
		CompanyBusiness companyBusiness = null;
		try {
			companyBusiness = (CompanyBusiness) IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CompanyBusiness.class);
		} catch (IBOLookupException e) {
			e.printStackTrace();
		}
		if (companyBusiness == null) {
			return null;
		}
		
		Company company = null;
		try {
			company = companyBusiness.getCompany(companyPersonalId);
		} catch (RemoteException e) {
		} catch (FinderException e) {}
		if (company == null) {
			logger.log(Level.WARNING, "Company was not found by provided ID: " + companyPersonalId);
			return null;
		}
		
		UserDataBean companyInfo = new UserDataBean();
		
		companyInfo.setName(company.getName());
		companyInfo.setPersonalId(company.getPersonalID());
		
		try {
			getUserAppEngine().fillUserInfo(companyInfo, company.getPhone(), company.getEmail(), company.getAddress());
		} catch(Exception e) {
			logger.log(Level.WARNING, "Error filling company info!", e);
		}
		
		return companyInfo;
	}

	private UserApplicationEngine getUserAppEngine() {
		return ELUtil.getInstance().getBean(UserApplicationEngine.SPRING_BEAN_IDENTIFIER);
	}

}
