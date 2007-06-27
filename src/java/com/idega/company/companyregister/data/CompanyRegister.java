package com.idega.company.companyregister.data;


import com.idega.core.location.data.Address;
import com.idega.core.location.data.Commune;
import com.idega.user.data.User;
import com.idega.data.IDOEntity;

public interface CompanyRegister extends IDOEntity {
	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getRecipient
	 */
	public User getRecipient();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setRecipient
	 */
	public void setRecipient(User recipient);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getBanMarking
	 */
	public String getBanMarking();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setBanMarking
	 */
	public void setBanMarking(String banMarking);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getCEO
	 */
	public User getCEO();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setCEO
	 */
	public void setCEO(User ceo);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getIndustryCode
	 */
	public IndustryCode getIndustryCode();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setIndustryCode
	 */
	public void setIndustryCode(IndustryCode industryCode);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getLastChange
	 */
	public String getLastChange();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setLastChange
	 */
	public void setLastChange(String lastChange);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getLegalCommune
	 */
	public Commune getLegalCommune();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setLegalCommune
	 */
	public void setLegalCommune(Commune legalCommune);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getOperation
	 */
	public String getOperation();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setOperation
	 */
	public void setOperation(String operation);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getOperationForm
	 */
	public OperationForm getOperationForm();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setOperationForm
	 */
	public void setOperationForm(OperationForm operationForm);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getOrderAreaForName
	 */
	public String getOrderAreaForName();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setOrderAreaForName
	 */
	public void setOrderAreaForName(String orderAreaForName);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getRegisterDate
	 */
	public String getRegisterDate();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setRegisterDate
	 */
	public void setRegisterDate(String registerDate);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getUnregisterDate
	 */
	public String getUnregisterDate();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setUnregisterDate
	 */
	public void setUnregisterDate(String unregisterDate);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getUnregisterType
	 */
	public UnregisterType getUnregisterType();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setUnregisterType
	 */
	public void setUnregisterType(UnregisterType unregisterType);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getVATNumber
	 */
	public String getVATNumber();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setVATNumber
	 */
	public void setVATNumber(String number);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#getWorkingArea
	 */
	public Commune getWorkingArea();

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setWorkingArea
	 */
	public void setWorkingArea(Commune workingArea);

	/**
	 * @see com.idega.company.companyregister.data.CompanyRegisterBMPBean#setAddress
	 */
	public void setAddress(Address address);
}