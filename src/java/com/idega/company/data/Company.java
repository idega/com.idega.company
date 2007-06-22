package com.idega.company.data;


import com.idega.core.location.data.Address;
import com.idega.user.data.Group;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.Email;
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
	 * @see com.idega.company.data.CompanyBMPBean#isValid
	 */
	public boolean isValid();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#isOpen
	 */
	public boolean isOpen();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getAddress
	 */
	public Address getAddress();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getPhone
	 */
	public Phone getPhone();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getFax
	 */
	public Phone getFax();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getEmail
	 */
	public Email getEmail();

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
	 * @see com.idega.company.data.CompanyBMPBean#setValid
	 */
	public void setValid(boolean valid);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setOpen
	 */
	public void setOpen(boolean open);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#store
	 */
	public void store() throws IDOStoreException;
}