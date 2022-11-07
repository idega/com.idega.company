/*
 * $Id$ Created on May 30, 2007
 *
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package com.idega.company.data;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.idega.business.IBOLookup;
import com.idega.company.CompanyConstants;
import com.idega.core.company.bean.GeneralCompany;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.EmailHome;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.PhoneBMPBean;
import com.idega.core.location.business.AddressBusiness;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.AddressHome;
import com.idega.core.location.data.AddressType;
import com.idega.core.location.data.Commune;
import com.idega.core.location.data.PostalCode;
import com.idega.data.GenericEntity;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOCompositePrimaryKeyException;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORuntimeException;
import com.idega.data.IDOStoreException;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.idegaweb.IWMainApplication;
import com.idega.user.data.Group;
import com.idega.user.data.GroupHome;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.EmailValidator;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

public class CompanyBMPBean extends GenericEntity implements Company, GeneralCompany {

	private static final long serialVersionUID = 5902685982267772143L;
	private static final String ENTITY_NAME = "ic_company";

	private static final String COLUMN_TYPE = "company_type";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_PERSONAL_ID = "personal_id";
	private static final String COLUMN_WEB_PAGE = "web_page";
	private static final String COLUMN_BANK_ACCOUNT = "bank_account";
	private static final String COLUMN_IS_VALID = "is_valid";
	private static final String COLUMN_IS_OPEN = "is_open";
	private static final String COLUMN_EXTRA_INFO = "extra_info";

	protected static final String VAT_NUMBER = "vat_number";

	protected static final String CEO_ID = "ceo_id";

	protected static final String WORKING_AREA = "working_area";

	protected static final String ORDER_AREA_FOR_NAME = "order_area_for_name";

	protected static final String LAST_CHANGE = "last_change";

	protected static final String OPERATION_FORM = "operation_form";

	protected static final String LEGAL_COMMUNE = "legal_commune";

	protected static final String REGISTER_DATE = "register_date";

	protected static final String RECIPIENT_ID = "recipient_id";

	protected static final String RECIPIENT_NAME = "recipient_name";

	protected static final String OPERATION = "operation";

	protected static final String INDUSTRY_CODE = "industry_code";

	protected static final String UNREGISTER_TYPE = "unregister_type";

	protected static final String UNREGISTER_DATE = "unregister_date";

	protected static final String BAN_MARKING = "ban_marking";

	protected static final String COLUMN_UNIQUE_ID = "unique_id";

	private Group iGroup;

	@Override
	protected boolean doInsertInCreate() {
		return true;
	}

	@Override
	public String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	public void initializeAttributes() {
		addOneToOneRelationship(getIDColumnName(), Group.class);
		setAsPrimaryKey(getIDColumnName(), true);

		addManyToOneRelationship(COLUMN_TYPE, CompanyType.class);

		addAttribute(COLUMN_NAME, "Name", String.class);
		addAttribute(COLUMN_PERSONAL_ID, "Personal ID", String.class);
		addAttribute(COLUMN_WEB_PAGE, "Web page", String.class);
		addAttribute(COLUMN_BANK_ACCOUNT, "Bank account", String.class);
		addAttribute(COLUMN_IS_VALID, "Is valid", Boolean.class);
		addAttribute(COLUMN_IS_OPEN, "Is open", Boolean.class);
		addAttribute(COLUMN_EXTRA_INFO, "Extra info", String.class);

		addAttribute(VAT_NUMBER, "VAT Number", java.lang.String.class);
		addAttribute(OPERATION, "Operation", java.lang.String.class);
		addAttribute(UNREGISTER_DATE, "Unregister Date", java.sql.Date.class);
		addAttribute(ORDER_AREA_FOR_NAME, "Order Area For Name", java.lang.String.class);
		addAttribute(LAST_CHANGE, "Last Change", java.sql.Date.class);
		addAttribute(REGISTER_DATE, "Register Date", java.sql.Date.class);
		addAttribute(BAN_MARKING, "Ban Marking", java.lang.String.class);
		addAttribute(RECIPIENT_ID, "Recipient ID", java.lang.String.class);
		addAttribute(RECIPIENT_NAME, "Recipient Name", java.lang.String.class);
		addAttribute(COLUMN_UNIQUE_ID, "Unique id", java.lang.String.class);
		addManyToOneRelationship(LEGAL_COMMUNE, "Legal Commune", Commune.class);
		addManyToOneRelationship(OPERATION_FORM, "Operation form", OperationForm.class);
		addManyToOneRelationship(CEO_ID, "CEO ID", User.class);
		addManyToOneRelationship(WORKING_AREA, "Working area", Commune.class);
		addManyToOneRelationship(INDUSTRY_CODE, "Industry Code", IndustryCode.class);
		//		addManyToOneRelationship(RECIPIENT_ID, "Recipient ID", User.class);
		addManyToOneRelationship(UNREGISTER_TYPE, "Unregister Type", UnregisterType.class);

		setUnique(COLUMN_PERSONAL_ID, true);
	}

	// Getters
	@Override
	public Group getGroup() {
		return (Group) getColumnValue(getIDColumnName());
	}

	@Override
	public CompanyType getType() {
		return (CompanyType) getColumnValue(COLUMN_TYPE);
	}

	@Override
	public String getName() {
		return getStringColumnValue(COLUMN_NAME);
	}

	@Override
	public String getPersonalID() {
		return getStringColumnValue(COLUMN_PERSONAL_ID);
	}

	@Override
	public String getWebPage() {
		return getStringColumnValue(COLUMN_WEB_PAGE);
	}

	@Override
	public String getBankAccount() {
		return getStringColumnValue(COLUMN_BANK_ACCOUNT);
	}

	@Override
	public String getExtraInfo() {
		return getStringColumnValue(COLUMN_EXTRA_INFO);
	}

	@Override
	public boolean isValid() {
		return getBooleanColumnValue(COLUMN_IS_VALID, false);
	}

	@Override
	public boolean isOpen() {
		return getBooleanColumnValue(COLUMN_IS_OPEN, false);
	}

	@Override
	public Address getAddress() {
		try {
			AddressHome home = (AddressHome) getIDOHome(Address.class);
			Collection<Address> addresses = getGeneralGroup().getAddresses(home.getAddressType1());
			if (!ListUtil.isEmpty(addresses)) {
				return addresses.iterator().next();
			}
		}
		catch (IDOLookupException e) {
			e.printStackTrace();
		}
		catch (IDOCompositePrimaryKeyException e) {
			e.printStackTrace();
		}
		catch (IDORelationshipException e) {
			e.printStackTrace();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Phone getPhone() {
		return getPhoneByType(getGeneralGroup().getPhones(), PhoneBMPBean.getHomeNumberID());
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.company.data.Company#getMobilePhone()
	 */
	@Override
	public Phone getMobilePhone() {
		return getPhoneByType(getGeneralGroup().getPhones(), PhoneBMPBean.getMobileNumberID());
	}

	private Phone getPhoneByType(Collection<Phone> phones, int type) {
		if (ListUtil.isEmpty(phones)) {
			return null;
		}

		for (Phone phone: phones) {
			if (phone.getPhoneTypeId() == type) {
				return phone;
			}
		}

		return null;
	}

	@Override
	public void updatePhone(Phone newPhone) {
		Phone phone = getPhone();
		if(phone == null) {
			try {
				newPhone.store();
				getGeneralGroup().addPhone(newPhone);
			} catch (IDOAddRelationshipException e) {
				e.printStackTrace();
			}
		} else {
			if(phone.getNumber() == null || !phone.getNumber().equals(newPhone.getNumber())) {
				phone.setNumber(newPhone.getNumber());
				phone.store();
			}
		}
	}

	@Override
	public Phone getFax() {
		return getPhoneByType(getGeneralGroup().getPhones(), PhoneBMPBean.getFaxNumberID());
	}

	@Override
	public void updateFax(Phone newFax) {
		Phone fax = getFax();
		if(fax == null) {
			try {
				newFax.store();
				getGeneralGroup().addPhone(newFax);
			} catch (IDOAddRelationshipException e) {
				e.printStackTrace();
			}
		} else {
			if(fax.getNumber() == null || !fax.getNumber().equals(newFax.getNumber())) {
				fax.setNumber(newFax.getNumber());
				fax.store();
			}
		}
	}

	@Override
	public Collection<Email> getEmails() {
		return getGeneralGroup().getEmails();
	}

	@Override
	public Email getEmail() {
		Collection<Email> emails = getEmails();
		if (ListUtil.isEmpty(emails)) {
			return null;
		}
		return emails.iterator().next();
	}

	@Override
	public Email addEmail(String newEmailAddress) {
		if (!EmailValidator.getInstance().isValid(newEmailAddress)) {
			return null;
		}

		Collection<Email> emails = getEmails();
		Email emailToUpdate = null;
		if (!ListUtil.isEmpty(emails)) {
			for (Iterator<Email> iter = emails.iterator(); (emailToUpdate == null && iter.hasNext());) {
				emailToUpdate = iter.next();
				if (StringUtil.isEmpty(emailToUpdate.getEmailAddress()) || !emailToUpdate.getEmailAddress().equals(newEmailAddress)) {
					emailToUpdate = null;
				}
			}
		}

		if (emailToUpdate == null) {
			try {
				EmailHome emailHome = (EmailHome) IDOLookup.getHome(Email.class);
				Email newEmail = emailHome.create();
				newEmail.setEmailAddress(newEmailAddress);
				newEmail.store();
				getGeneralGroup().addEmail(newEmail);
				return newEmail;
			} catch (Exception e) {
				getLogger().log(Level.WARNING, "Error adding email " + newEmailAddress + " for company " + this);
			}

		} else {
			if (emailToUpdate.getEmailAddress() == null || !emailToUpdate.getEmailAddress().equals(newEmailAddress)) {
				emailToUpdate.setEmailAddress(newEmailAddress);
				emailToUpdate.store();
			}
		}
		return emailToUpdate;
	}

	@Override
	public Email updateEmail(String newEmailAddress) {
		if (!EmailValidator.getInstance().isValid(newEmailAddress)) {
			return null;
		}

		Collection<Email> emails = getEmails();
		Email emailToUpdate = null;
		if (!ListUtil.isEmpty(emails)) {
			if (emails.size() == 1) {
				emailToUpdate = emails.iterator().next();
			} else {
				for (Iterator<Email> iter = emails.iterator(); (emailToUpdate == null && iter.hasNext());) {
					emailToUpdate = iter.next();
					if (StringUtil.isEmpty(emailToUpdate.getEmailAddress()) || !emailToUpdate.getEmailAddress().equals(newEmailAddress)) {
						emailToUpdate = null;
					}
				}
			}
		}
		if (emailToUpdate == null) {
			return addEmail(newEmailAddress);
		} else {
			if (emailToUpdate.getEmailAddress() == null || !emailToUpdate.getEmailAddress().equals(newEmailAddress)) {
				emailToUpdate.setEmailAddress(newEmailAddress);
				emailToUpdate.store();
			}
		}
		return emailToUpdate;
	}

	@Override
	public Email updateEmail(Email newEmail) {
		return updateEmail(newEmail == null ? null : newEmail.getEmailAddress());
	}

	// Setters
	@Override
	public void setGroup(Group group) {
		setPrimaryKey(group.getPrimaryKey());
	}

	@Override
	public void setType(CompanyType type) {
		setColumn(COLUMN_TYPE, type);
	}

	@Override
	public void setName(String name) {
		getGeneralGroup().setName(name);
		setColumn(COLUMN_NAME, name);
	}

	@Override
	public void setPersonalID(String personalID) {
		setColumn(COLUMN_PERSONAL_ID, personalID);
	}

	@Override
	public void setWebPage(String webPage) {
		setColumn(COLUMN_WEB_PAGE, webPage);
	}

	@Override
	public void setBankAccount(String bankAccount) {
		setColumn(COLUMN_BANK_ACCOUNT, bankAccount);
	}

	@Override
	public void setExtraInfo(String extraInfo) {
		setColumn(COLUMN_EXTRA_INFO, extraInfo);
	}

	@Override
	public void setValid(boolean valid) {
		setColumn(COLUMN_IS_VALID, valid);
	}

	@Override
	public void setOpen(boolean open) {
		setColumn(COLUMN_IS_OPEN, open);
	}

	// Finders and creators
	@Override
	public Object ejbCreate() throws CreateException {
		this.iGroup = this.getGroupHome().create();
		this.iGroup.setGroupType(CompanyConstants.GROUP_TYPE_COMPANY);
		this.iGroup.store();
		this.setPrimaryKey(this.iGroup.getPrimaryKey());
		return super.ejbCreate();
	}

	public Object ejbFindByPersonalID(String personalID) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_PERSONAL_ID), MatchCriteria.EQUALS, personalID));

		return idoFindOnePKByQuery(query);
	}

	public Object ejbFindByName(String name) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_NAME), MatchCriteria.EQUALS, name));

		Object result = null;
		try {
			result = idoFindOnePKByQuery(query);
		} catch (Exception e) {}
		return result == null ? ejbFindBySimilarName(name) : result;
	}

	private Object ejbFindBySimilarName(String name) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_NAME), MatchCriteria.LIKE, true, name));

		return idoFindOnePKByQuery(query);
	}

	public Object ejbFindByUniqueId(String uniqueId) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_UNIQUE_ID), MatchCriteria.EQUALS, uniqueId));

		return idoFindOnePKByQuery(query);
	}

	public Collection<Company> ejbFindAll(Boolean valid) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		if (valid != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_VALID), MatchCriteria.EQUALS, valid.booleanValue()));
		}
		query.addOrder(table, COLUMN_NAME, true);

		return idoFindPKsByQuery(query);
	}

	public Collection<Company> ejbFindAllWithOpenStatus() throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_OPEN), MatchCriteria.EQUALS, true));
		query.addOrder(table, COLUMN_NAME, true);

		return idoFindPKsByQuery(query);
	}

	public Collection<Company> ejbFindAllActiveWithOpenStatus() throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_VALID), MatchCriteria.EQUALS, true));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_OPEN), MatchCriteria.EQUALS, true));
		query.addOrder(table, COLUMN_NAME, true);

		return idoFindPKsByQuery(query);
	}

	/**
	 *
	 * <p>Finds all active and valid {@link Company}s by
	 * {@link Company#getCEO()}.</p>
	 * @param user - {@link Company#getCEO()}.
	 * @return {@link Collection} of {@link Company}s or <code>null</code>
	 * on failure.
	 * @throws FinderException - if unable to find {@link Company}.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Collection<Company> ejbFindAll(User user) throws FinderException {
		if (user == null) {
			log("Given " + User.class + " is: " + user);
			return null;
		}

		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_VALID), MatchCriteria.EQUALS, true));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_OPEN), MatchCriteria.EQUALS, true));
		query.addCriteria(new MatchCriteria(table.getColumn(CEO_ID), MatchCriteria.EQUALS, user.getPrimaryKey().toString()));
		query.addOrder(table, COLUMN_NAME, true);

		return idoFindPKsByQuery(query);
	}

	// General methods
	@Override
	public void store() throws IDOStoreException {
		getGeneralGroup().store();
		/*User ceo = getCEO();
		if(ceo != null) {
			ceo.store();
		}*/
		super.store();
	}

	@Override
	public void remove() throws RemoveException {
		super.remove();
		getGeneralGroup().remove();
	}

	@Override
	public Group getGeneralGroup() {
		if (this.iGroup == null) {
			try {
				this.iGroup = getGroupHome().findByPrimaryKey(this.getPrimaryKey());
			}
			catch (FinderException fe) {
				fe.printStackTrace();
				throw new EJBException(fe.getMessage());
			}
		}
		return this.iGroup;
	}

	private GroupHome getGroupHome() {
		try {
			return (GroupHome) getIDOHome(Group.class);
		}
		catch (IDOLookupException e) {
			throw new IDORuntimeException(e.getMessage());
		}
	}

	@Override
	public void setDefaultValues() {
		setValid(true);
	}

	@Override
	public String getRecipientId() {
		return getStringColumnValue(RECIPIENT_ID);
	}

	@Override
	public String getRecipientName() {
		return getStringColumnValue(RECIPIENT_NAME);
	}

	@Override
	public void setRecipientId(String recipient) {
		setColumn(RECIPIENT_ID, recipient);
	}

	@Override
	public void setRecipientName(String recipient) {
		setColumn(RECIPIENT_NAME, recipient);
	}

	@Override
	public String getBanMarking() {
		return getStringColumnValue(BAN_MARKING);
	}

	@Override
	public User getCEO() {
		return (User) this.getColumnValue(CEO_ID);
	}

	@Override
	public void setCEO(User ceo) {
		setColumn(CEO_ID, ceo);
	}

	@Override
	public IndustryCode getIndustryCode() {
		return (IndustryCode) getColumnValue(INDUSTRY_CODE);
	}

	@Override
	public void setIndustryCode(IndustryCode industryCode) {
		setColumn(INDUSTRY_CODE, industryCode);
	}

	@Override
	public Date getLastChange() {
		return (Date) getColumnValue(LAST_CHANGE);
	}

	@Override
	public void setLastChange(Date lastChange) {
		setColumn(LAST_CHANGE, lastChange);
	}

	@Override
	public Commune getLegalCommune() {
		return (Commune) getColumnValue(LEGAL_COMMUNE);
	}

	@Override
	public void setLegalCommune(Commune legalCommune) {
		setColumn(LEGAL_COMMUNE, legalCommune);
	}

	@Override
	public String getOperation() {
		return getStringColumnValue(OPERATION);
	}

	@Override
	public void setOperation(String operation) {
		setColumn(OPERATION, operation);
	}

	@Override
	public OperationForm getOperationForm() {
		return (OperationForm) getColumnValue(OPERATION_FORM);
	}

	@Override
	public void setOperationForm(OperationForm operationForm) {
		setColumn(OPERATION_FORM, operationForm);
	}

	@Override
	public String getOrderAreaForName() {
		return getStringColumnValue(ORDER_AREA_FOR_NAME);
	}

	@Override
	public void setOrderAreaForName(String orderAreaForName) {
		setColumn(ORDER_AREA_FOR_NAME, orderAreaForName);
	}

	@Override
	public Date getRegisterDate() {
		return (Date) getColumnValue(REGISTER_DATE);
	}

	@Override
	public void setRegisterDate(Date registerDate) {
		setColumn(REGISTER_DATE, registerDate);
	}

	@Override
	public Date getUnregisterDate() {
		return (Date) getColumnValue(UNREGISTER_DATE);
	}

	@Override
	public void setUnregisterDate(Date unregisterDate) {
		setColumn(UNREGISTER_DATE, unregisterDate);
	}

	@Override
	public UnregisterType getUnregisterType() {
		return (UnregisterType) getColumnValue(UNREGISTER_TYPE);
	}

	@Override
	public void setUnregisterType(UnregisterType unregisterType) {
		setColumn(UNREGISTER_TYPE, unregisterType);
	}

	@Override
	public String getVATNumber() {
		return getStringColumnValue(VAT_NUMBER);
	}

	@Override
	public void setVATNumber(String number) {
		setColumn(VAT_NUMBER, number);
	}

	@Override
	public Commune getWorkingArea() {
		return (Commune) getColumnValue(WORKING_AREA);
	}

	@Override
	public void setWorkingArea(Commune workingArea) {
		setColumn(WORKING_AREA, workingArea);
	}

	@Override
	public void setAddress(Address address) {
		try {
			getGeneralGroup().addAddress(address);
		}
		catch (IDOAddRelationshipException e) {
			throw new IDORuntimeException(e.getMessage());
		}
	}

	@Override
	public Address updateAddress(String street, String commune, String postalCode, String city) {
		Address address = getAddress();
		if (address == null) {
			try {
				AddressHome addressHome = (AddressHome) IDOLookup.getHome(Address.class);
				address = addressHome.create();
				AddressType mainAddressType = addressHome.getAddressType1();
				address.setAddressType(mainAddressType);
				address.store();
				setAddress(address);
			} catch (Exception e) {
				getLogger().log(Level.WARNING, "Error setting address for company " + this, e);
			}
		}

		if (address == null) {
			return null;
		}

		try {
			AddressBusiness addressBusiness = IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), AddressBusiness.class);
			String streetName = addressBusiness.getStreetNameFromAddressString(street);
			String streetNumber = addressBusiness.getStreetNumberFromAddressString(street);
			address.setStreetName(streetName);
			address.setStreetNumber(StringUtil.isEmpty(streetNumber) ? CoreConstants.EMPTY : streetNumber);

			address.setCity(city);

			Commune communeEntity = addressBusiness.getCommuneAndCreateIfDoesNotExist(commune, null);
			address.setCommune(communeEntity);

			PostalCode postalCodeEntity = addressBusiness.getPostalCodeAndCreateIfDoesNotExist(postalCode, null);
			address.setPostalCode(postalCodeEntity);

			address.store();
			return address;
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Error updating address for company " + this, e);
		}

		return null;
	}

	@Override
	public void setBanMarking(String banMarking) {
		setColumn(BAN_MARKING, banMarking);
	}

	@Override
	public String getUniqueId() {
		return getStringColumnValue(COLUMN_UNIQUE_ID);
	}

	@Override
	public void setUniqueId(String uniqueId) {
		setColumn(COLUMN_UNIQUE_ID, uniqueId);
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(CoreConstants.NEWLINE)
		.append("getType(): ").append(getType()).append(CoreConstants.NEWLINE)
		.append("getName(): ").append(getName()).append(CoreConstants.NEWLINE)
		.append("getPersonalID(): ").append(getPersonalID()).append(CoreConstants.NEWLINE)
		.append("getWebPage(): ").append(getWebPage()).append(CoreConstants.NEWLINE)
		.append("getBankAccount(): ").append(getBankAccount()).append(CoreConstants.NEWLINE)
		.append("getExtraInfo(): ").append(getExtraInfo()).append(CoreConstants.NEWLINE)
		.append("isValid(): ").append(isValid()).append(CoreConstants.NEWLINE)
		.append("isOpen(): ").append(isOpen()).append(CoreConstants.NEWLINE)
		.append("getAddress(): ").append(getAddress()).append(CoreConstants.NEWLINE)
		.append("getPhone(): ").append(getPhone()).append(CoreConstants.NEWLINE)
		.append("getFax(): ").append(getFax()).append(CoreConstants.NEWLINE)
		.append("getEmail(): ").append(getEmail()).append(CoreConstants.NEWLINE)
		.append("getGeneralGroup(): ").append(getGeneralGroup()).append(CoreConstants.NEWLINE)
		.append("getRecipientId(): ").append(getRecipientId()).append(CoreConstants.NEWLINE)
		.append("getRecipientName(): ").append(getRecipientName()).append(CoreConstants.NEWLINE)
		.append("getBanMarking(): ").append(getBanMarking()).append(CoreConstants.NEWLINE)
		.append("getCEO(): {").append(getCEO()).append("}").append(CoreConstants.NEWLINE)
		.append("getIndustryCode(): ").append(getIndustryCode()).append(CoreConstants.NEWLINE)
		.append("getLastChange(): ").append(getLastChange()).append(CoreConstants.NEWLINE)
		.append("getLegalCommune(): ").append(getLegalCommune()).append(CoreConstants.NEWLINE)
		.append("getOperation(): ").append(getOperation()).append(CoreConstants.NEWLINE)
		.append("getOperationForm(): ").append(getOperationForm()).append(CoreConstants.NEWLINE)
		.append("getOrderAreaForName(): ").append(getOrderAreaForName()).append(CoreConstants.NEWLINE)
		.append("getRegisterDate(): ").append(getRegisterDate()).append(CoreConstants.NEWLINE)
		.append("getUnregisterDate(): ").append(getUnregisterDate()).append(CoreConstants.NEWLINE)
		.append("getUnregisterType(): ").append(getUnregisterType()).append(CoreConstants.NEWLINE)
		.append("getVATNumber(): ").append(getVATNumber()).append(CoreConstants.NEWLINE)
		.append("getUniqueId(): ").append(getUniqueId()).append(CoreConstants.NEWLINE)
		.append("getWorkingArea(): ").append(getWorkingArea()).append(CoreConstants.NEWLINE);
		return sb.toString();
	}
}