package com.idega.company.companyregister.data;


import com.idega.data.IDOEntity;

public interface CompanyRegister extends IDOEntity {

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setSymbol
	 */
	public void setSymbol(String symbol);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getSymbol
	 */
	public String getSymbol();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setSSN
	 */
	public void setSSN(String ssn);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getSSN
	 */
	public String getSSN();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setDateOfDeath
	 */
	public void setDateOfDeath(String date);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getDateOfDeath
	 */
	public String getDateOfDeath();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setName
	 */
	public void setName(String name);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getName
	 */
	public String getName();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setCommune
	 */
	public void setCommune(String commune);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getCommune
	 */
	public String getCommune();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setStreet
	 */
	public void setStreet(String street);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getStreet
	 */
	public String getStreet();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setGender
	 */
	public void setGender(String gender);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getGender
	 */
	public String getGender();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setMaritalStatus
	 */
	public void setMaritalStatus(String status);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getMaritalStatus
	 */
	public String getMaritalStatus();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setSpouseSSN
	 */
	public void setSpouseSSN(String ssn);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getSpouseSSN
	 */
	public String getSpouseSSN();
}