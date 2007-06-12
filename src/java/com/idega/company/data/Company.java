package com.idega.company.data;


import com.idega.user.data.Group;
import com.idega.data.IDOStoreException;
import com.idega.data.IDOEntity;

public interface Company extends IDOEntity {

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getGroup
	 */
	public Group getGroup();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getType
	 */
	public CompanyType getType();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getName
	 */
	public String getName();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getPersonalID
	 */
	public String getPersonalID();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getWebPage
	 */
	public String getWebPage();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getBankAccount
	 */
	public String getBankAccount();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setGroup
	 */
	public void setGroup(Group group);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setType
	 */
	public void setType(CompanyType type);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setPersonalID
	 */
	public void setPersonalID(String personalID);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setWebPage
	 */
	public void setWebPage(String webPage);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setBankAccount
	 */
	public void setBankAccount(String bankAccount);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#store
	 */
	public void store() throws IDOStoreException;
}