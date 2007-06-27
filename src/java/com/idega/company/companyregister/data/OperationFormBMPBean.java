package com.idega.company.companyregister.data;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.IDOQuery;

public class OperationFormBMPBean extends GenericEntity implements
		OperationForm {
	
	protected static final String ENTITY_NAME = "com_operation_form";
	
	protected static final String CODE = "code";

	protected static final String DESCRIPTION = "description";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		addAttribute(getIDColumnName());
		addAttribute(CODE, "Code", true, true, java.lang.String.class, 2);
		addAttribute(DESCRIPTION, "Description", true, true, java.lang.String.class);
		
		addIndex(CODE);
		setUnique(CODE, true);
	}

	public String getDescription() {
		return getStringColumnValue(DESCRIPTION);
	}

	public String getCode() {
		return getStringColumnValue(CODE);
	}

	public void setDescription(String description) {
		setColumn(DESCRIPTION, description);
	}

	public void setCode(String code) {
		setColumn(CODE, code);
	}
	
	public Collection ejbFindAllUnregisterTypes() throws FinderException, RemoteException {
		return super.idoFindAllIDsBySQL();
	}
	
	public Integer ejbFindUnregisterTypeByUniqueCode(String uniqueId) throws FinderException {
		IDOQuery query = idoQueryGetSelect();
		query.appendWhereEqualsQuoted(getIDColumnName(), uniqueId);

		return (Integer) idoFindOnePKByQuery(query);
	}

}
