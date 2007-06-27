package com.idega.company.data;


import com.idega.core.location.data.Address;
import com.idega.user.data.Group;
import com.idega.core.location.data.Commune;
import com.idega.core.contact.data.Phone;
import java.sql.Date;
import com.idega.user.data.User;
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

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getRecipient
	 */
	public User getRecipient();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setRecipient
	 */
	public void setRecipient(User recipient);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getBanMarking
	 */
	public String getBanMarking();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getCEO
	 */
	public User getCEO();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setCEO
	 */
	public void setCEO(User ceo);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getIndustryCode
	 */
	public IndustryCode getIndustryCode();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setIndustryCode
	 */
	public void setIndustryCode(IndustryCode industryCode);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getLastChange
	 */
	public Date getLastChange();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setLastChange
	 */
	public void setLastChange(Date lastChange);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getLegalCommune
	 */
	public Commune getLegalCommune();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setLegalCommune
	 */
	public void setLegalCommune(Commune legalCommune);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getOperation
	 */
	public String getOperation();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setOperation
	 */
	public void setOperation(String operation);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getOperationForm
	 */
	public OperationForm getOperationForm();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setOperationForm
	 */
	public void setOperationForm(OperationForm operationForm);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getOrderAreaForName
	 */
	public String getOrderAreaForName();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setOrderAreaForName
	 */
	public void setOrderAreaForName(String orderAreaForName);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getRegisterDate
	 */
	public Date getRegisterDate();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setRegisterDate
	 */
	public void setRegisterDate(Date registerDate);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getUnregisterDate
	 */
	public Date getUnregisterDate();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setUnregisterDate
	 */
	public void setUnregisterDate(Date unregisterDate);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getUnregisterType
	 */
	public UnregisterType getUnregisterType();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setUnregisterType
	 */
	public void setUnregisterType(UnregisterType unregisterType);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getVATNumber
	 */
	public String getVATNumber();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setVATNumber
	 */
	public void setVATNumber(String number);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#getWorkingArea
	 */
	public Commune getWorkingArea();

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setWorkingArea
	 */
	public void setWorkingArea(Commune workingArea);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setAddress
	 */
	public void setAddress(Address address);

	/**
	 * @see com.idega.company.data.CompanyBMPBean#setBanMarking
	 */
	public void setBanMarking(String banMarking);
}