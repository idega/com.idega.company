package com.idega.company.companyregister.data;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;

public class IndustryCodeBMPBean extends GenericEntity implements IndustryCode {

	protected static final String ENTITY_NAME = "com_industry_code_isat";
	
	protected static final String CODE = "code";

	protected static final String DESCRIPTION = "description";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(CODE, "ISAT Code", true, true, java.lang.String.class, 5);
		addAttribute(DESCRIPTION, "Description", true, true, java.lang.String.class);
		
		addIndex(CODE);
		setUnique(CODE, true);
	}

	public String getISATCode() {
		return getStringColumnValue(CODE);
	}

	public String getISATDescription() {
		return getStringColumnValue(DESCRIPTION);
	}

	public void setISATCode(String code) {
		setColumn(CODE, code);
	}

	public void setISATDescription(String description) {
		setColumn(DESCRIPTION, description);
	}
	
	public Collection ejbFindAllUnregisterTypes() throws FinderException, RemoteException {
		return super.idoFindAllIDsBySQL();
	}

}
