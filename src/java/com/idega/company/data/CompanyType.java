package com.idega.company.data;


import java.util.Locale;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.data.IDOEntity;

public interface CompanyType extends IDOEntity {

	/**
	 * @see com.idega.company.data.CompanyTypeBMPBean#getPrimaryKeyClass
	 */
	public Class getPrimaryKeyClass();

	/**
	 * @see com.idega.company.data.CompanyTypeBMPBean#getName
	 */
	public String getName();

	/**
	 * @see com.idega.company.data.CompanyTypeBMPBean#getDescription
	 */
	public String getDescription();

	/**
	 * @see com.idega.company.data.CompanyTypeBMPBean#getLocalizedKey
	 */
	public String getLocalizedKey();

	/**
	 * @see com.idega.company.data.CompanyTypeBMPBean#getLocalizedName
	 */
	public String getLocalizedName(IWApplicationContext iwac, Locale locale);

	/**
	 * @see com.idega.company.data.CompanyTypeBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see com.idega.company.data.CompanyTypeBMPBean#setDescription
	 */
	public void setDescription(String description);

	/**
	 * @see com.idega.company.data.CompanyTypeBMPBean#setLocalizedKey
	 */
	public void setLocalizedKey(String localizedKey);
}