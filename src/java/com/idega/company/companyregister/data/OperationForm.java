package com.idega.company.companyregister.data;


import com.idega.data.IDOEntity;

public interface OperationForm extends IDOEntity {
	/**
	 * @see com.idega.company.companyregister.data.OperationFormBMPBean#getDescription
	 */
	public String getDescription();

	/**
	 * @see com.idega.company.companyregister.data.OperationFormBMPBean#getCode
	 */
	public String getCode();

	/**
	 * @see com.idega.company.companyregister.data.OperationFormBMPBean#setDescription
	 */
	public void setDescription(String description);

	/**
	 * @see com.idega.company.companyregister.data.OperationFormBMPBean#setCode
	 */
	public void setCode(String code);
}