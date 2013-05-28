package com.idega.company.business;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.FinderException;

import org.directwebremoting.annotations.Param;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.business.IBOLookup;
import com.idega.company.bean.CompanyInfo;
import com.idega.company.data.Company;
import com.idega.core.accesscontrol.business.LoginSession;
import com.idega.core.business.DefaultSpringBean;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.PostalCode;
import com.idega.dwr.business.DWRAnnotationPersistance;
import com.idega.idegaweb.IWMainApplication;
import com.idega.user.data.MetadataConstants;
import com.idega.user.data.User;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

@Scope(BeanDefinition.SCOPE_SINGLETON)
@Service(CompanyProvider.BEAN_NAME)
@RemoteProxy(creator=SpringCreator.class, creatorParams={
		@Param(name="beanName", value=CompanyProvider.BEAN_NAME),
		@Param(name="javascript", value=CompanyProvider.DWR_OBJECT)
	}, name=CompanyProvider.DWR_OBJECT)
public class CompanyProvider extends DefaultSpringBean implements DWRAnnotationPersistance, Serializable {

	private static final long serialVersionUID = -5517414374982486474L;

	private static final Logger LOGGER = Logger.getLogger(CompanyProvider.class.getName());

	static final String BEAN_NAME = "companyInfoProvider";
	public static final String DWR_OBJECT = "CompanyProvider";

	@RemoteMethod
	public CompanyInfo getCompanyInfoByName(String name) {
		if (StringUtil.isEmpty(name)) {
			return null;
		}

		Company company = null;
		try {
			company = getCompanyBusiness().getCompanyByName(name);
		} catch(FinderException e) {
			LOGGER.warning("Unable to find company by name: " + name);
		} catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error getting company by name: " + name, e);
		}

		return getCompanyInfo(company);
	}

	@RemoteMethod
	public CompanyInfo getCompanyInfoByPersonalId(String personalId) {
		if (StringUtil.isEmpty(personalId)) {
			return null;
		}

		Company company = null;
		try {
			company = getCompanyBusiness().getCompany(personalId);
		} catch(FinderException e) {
			LOGGER.warning("Unable to find company by personalId: " + personalId);
		} catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error getting company by SSN: " + personalId, e);
		}

		return getCompanyInfo(company);
	}

	private CompanyInfo getCompanyInfo(Company company) {
		if (company == null) {
			return null;
		}

		CompanyInfo info = new CompanyInfo();
		info.setName(company.getName());
		info.setPersonalId(company.getPersonalID());

		Address address = company.getAddress();
		if (address != null) {
			info.setAddress(address.getStreetName());

			PostalCode postalCode = address.getPostalCode();
			if (postalCode != null) {
				info.setPostalCode(postalCode.getPostalCode());
			}
		}

		return info;
	}

	private CompanyBusiness getCompanyBusiness() {
		try {
			return IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CompanyBusiness.class);
		} catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error getting CompanyBusiness", e);
		}
		return null;
	}

	public String getCompanyPersonalIdForCurrentUser() {
		LoginSession loginSession = null;
		try {
			loginSession = ELUtil.getInstance().getBean(LoginSession.class);
		} catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error getting " + LoginSession.class, e);
		}
		if (loginSession == null) {
			return null;
		}

		User currentUser = null;
		try {
			currentUser = loginSession.getUser();
		} catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error getting current user", e);
		}
		if (currentUser == null) {
			return null;
		}

		String companyId = currentUser.getMetaData(MetadataConstants.USER_REAL_COMPANY_META_DATA_KEY);
		if (StringUtil.isEmpty(companyId)) {
			return null;
		}

		Company company = null;
		try {
			company = getCompanyBusiness().getCompany(companyId);
		} catch(Exception e) {
			LOGGER.log(Level.WARNING, "Company was not found by ID: " + companyId, e);
		}

		return company == null ? null : company.getPersonalID();
	}
}