package com.idega.company.companyregister.data;


import com.idega.data.IDOEntity;

public interface UnregisterType extends IDOEntity {
	/**
	 * @see com.idega.company.companyregister.data.UnregisterTypeBMPBean#getDescription
	 */
	public String getDescription();

	/**
	 * @see com.idega.company.companyregister.data.UnregisterTypeBMPBean#getCode
	 */
	public String getCode();

	/**
	 * @see com.idega.company.companyregister.data.UnregisterTypeBMPBean#setDescription
	 */
	public void setDescription(String description);

	/**
	 * @see com.idega.company.companyregister.data.UnregisterTypeBMPBean#setCode
	 */
	public void setCode(String code);
}