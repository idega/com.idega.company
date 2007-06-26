package com.idega.company.companyregister.data;

import com.idega.data.IDOEntity;


/**
 * @author Joakim
 *
 */
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
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setOldId
	 */
	public void setOldId(String id);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getOldId
	 */
	public String getOldId();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setSSN
	 */
	public void setSSN(String ssn);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getSSN
	 */
	public String getSSN();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setFamilyId
	 */
	public void setFamilyId(String id);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getFamilyId
	 */
	public String getFamilyId();

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
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setBuilding
	 */
	public void setBuilding(String building);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getBuilding
	 */
	public String getBuilding();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setFloor
	 */
	public void setFloor(String floor);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getFloor
	 */
	public String getFloor();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setSex
	 */
	public void setSex(String sex);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getSex
	 */
	public String getSex();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setMaritalStatus
	 */
	public void setMaritalStatus(String status);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getMaritalStatus
	 */
	public String getMaritalStatus();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setProhibitMarking
	 */
	public void setProhibitMarking(String marking);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getProhibitMarking
	 */
	public String getProhibitMarking();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setNationality
	 */
	public void setNationality(String nationality);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getNationality
	 */
	public String getNationality();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setPlaceOfBirth
	 */
	public void setPlaceOfBirth(String pob);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getPlaceOfBirth
	 */
	public String getPlaceOfBirth();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setSpouseSSN
	 */
	public void setSpouseSSN(String ssn);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getSpouseSSN
	 */
	public String getSpouseSSN();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setFate
	 */
	public void setFate(String fate);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getFate
	 */
	public String getFate();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setParish
	 */
	public void setParish(String parish);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getParish
	 */
	public String getParish();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setPO
	 */
	public void setPO(String po);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getPO
	 */
	public String getPO();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setAddress
	 */
	public void setAddress(String address);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getAddress
	 */
	public String getAddress();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setAddressCode
	 */
	public void setAddressCode(String code);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getAddressCode
	 */
	public String getAddressCode();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setDateOfModification
	 */
	public void setDateOfModification(String date);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getDateOfModification
	 */
	public String getDateOfModification();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setPlacementCode
	 */
	public void setPlacementCode(String code);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getPlacementCode
	 */
	public String getPlacementCode();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setDateOfCreation
	 */
	public void setDateOfCreation(String date);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getDateOfCreation
	 */
	public String getDateOfCreation();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setLastDomesticAddress
	 */
	public void setLastDomesticAddress(String address);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getLastDomesticAddress
	 */
	public String getLastDomesticAddress();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setAgentSSN
	 */
	public void setAgentSSN(String ssn);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getAgentSSN
	 */
	public String getAgentSSN();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setIsNew
	 */
	public void setIsNew(String isNew);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getIsNew
	 */
	public String getIsNew();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setAddressName
	 */
	public void setAddressName(String addressName);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getAddressName
	 */
	public String getAddressName();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setDateOfDeletion
	 */
	public void setDateOfDeletion(String date);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getDateOfDeletion
	 */
	public String getDateOfDeletion();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setNewSsnOrName
	 */
	public void setNewSsnOrName(String ssnOrName);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getNewSsnOrName
	 */
	public String getNewSsnOrName();

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#setDateOfBirth
	 */
	public void setDateOfBirth(String date);

	/**
	 * @see is.idega.block.nationalregister.data.CompanyRegisterBMPBean#getDateOfBirth
	 */
	public String getDateOfBirth();
}
