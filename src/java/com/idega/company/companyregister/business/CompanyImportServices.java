package com.idega.company.companyregister.business;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import org.directwebremoting.annotations.Param;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.core.business.DefaultSpringBean;
import com.idega.dwr.business.DWRAnnotationPersistance;
import com.idega.presentation.IWContext;
import com.idega.util.CoreUtil;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

@Service(CompanyImportServices.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
@RemoteProxy(creator=SpringCreator.class, creatorParams={
	@Param(name="beanName", value=CompanyImportServices.BEAN_NAME),
	@Param(name="javascript", value="CompanyImportServices")
}, name="CompanyImportServices")
public class CompanyImportServices extends DefaultSpringBean implements DWRAnnotationPersistance {

	static final String BEAN_NAME = "iwCompanyImportServices";

	@RemoteMethod
	public boolean doImportCompanies(String filePath) {
		if (StringUtil.isEmpty(filePath)) {
			getLogger().warning("File is not provided");
			return false;
		}

		try {
			IWContext iwc = CoreUtil.getIWContext();
			if (!iwc.isLoggedOn() || !iwc.isSuperAdmin()) {
				getLogger().warning("Do not have permission");
				return false;
			}

			CompanyRegisterFileImportHandler handler = new CompanyRegisterFileImportHandlerBean();
			CompanyRegisterImportFile file = new CompanyRegisterImportFile();
			file.setFile(new File(filePath));
			handler.setImportFile(file);
			handler.handleRecords();
			List<?> failedRecords = handler.getFailedRecords();
			List<?> succeededRecords = handler.getSuccessRecords();

			if (!ListUtil.isEmpty(succeededRecords)) {
				getLogger().info("Succeeded records: " + succeededRecords);
			}
			if (!ListUtil.isEmpty(failedRecords)) {
				getLogger().warning("Failed records: " + failedRecords);
				return false;
			}

			return true;
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error importing companies from " + filePath);
		}
		return false;
	}

}