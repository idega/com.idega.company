package com.idega.company.data;


import com.idega.data.IDOEntity;

public interface IndustryCode extends IDOEntity {
	/**
	 * @see com.idega.company.data.IndustryCodeBMPBean#getISATCode
	 */
	public String getISATCode();

	/**
	 * @see com.idega.company.data.IndustryCodeBMPBean#getISATDescription
	 */
	public String getISATDescription();

	/**
	 * @see com.idega.company.data.IndustryCodeBMPBean#setISATCode
	 */
	public void setISATCode(String code);

	/**
	 * @see com.idega.company.data.IndustryCodeBMPBean#setISATDescription
	 */
	public void setISATDescription(String description);
}