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
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.idega.company.CompanyConstants;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.PhoneBMPBean;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.AddressHome;
import com.idega.core.location.data.Commune;
import com.idega.data.GenericEntity;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDOCompositePrimaryKeyException;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORelationshipException;
import com.idega.data.IDORuntimeException;
import com.idega.data.IDOStoreException;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.user.data.Group;
import com.idega.user.data.GroupHome;
import com.idega.user.data.User;
import com.idega.util.ListUtil;

public class CompanyBMPBean extends GenericEntity implements Company {

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
	public Group getGroup() {
		return (Group) getColumnValue(getIDColumnName());
	}

	public CompanyType getType() {
		return (CompanyType) getColumnValue(COLUMN_TYPE);
	}

	@Override
	public String getName() {
		return getStringColumnValue(COLUMN_NAME);
	}

	public String getPersonalID() {
		return getStringColumnValue(COLUMN_PERSONAL_ID);
	}

	public String getWebPage() {
		return getStringColumnValue(COLUMN_WEB_PAGE);
	}

	public String getBankAccount() {
		return getStringColumnValue(COLUMN_BANK_ACCOUNT);
	}

	public String getExtraInfo() {
		return getStringColumnValue(COLUMN_EXTRA_INFO);
	}

	public boolean isValid() {
		return getBooleanColumnValue(COLUMN_IS_VALID, false);
	}

	public boolean isOpen() {
		return getBooleanColumnValue(COLUMN_IS_OPEN, false);
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public Phone getPhone() {
		return getPhoneByType(getGeneralGroup().getPhones(), PhoneBMPBean.getHomeNumberID());
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

	@SuppressWarnings("unchecked")
	public Phone getFax() {
		return getPhoneByType(getGeneralGroup().getPhones(), PhoneBMPBean.getFaxNumberID());
	}
	
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

	@SuppressWarnings("unchecked")
	public Email getEmail() {
		Collection<Email> emails = getGeneralGroup().getEmails();
		if (ListUtil.isEmpty(emails)) {
			return null;
		}
		return emails.iterator().next();
	}
	
	public void updateEmail(Email newEmail) {
		Email email = getEmail();
		if(email == null) {
			try {
				newEmail.store();
				getGeneralGroup().addEmail(newEmail);
			} catch (IDOAddRelationshipException e) {
				e.printStackTrace();
			}
		} else {
			if(email.getEmailAddress() == null || !email.getEmailAddress().equals(newEmail.getEmailAddress())) {
				email.setEmailAddress(newEmail.getEmailAddress());
				email.store();
			}
		}
	}

	// Setters
	public void setGroup(Group group) {
		setPrimaryKey(group.getPrimaryKey());
	}

	public void setType(CompanyType type) {
		setColumn(COLUMN_TYPE, type);
	}

	@Override
	public void setName(String name) {
		getGeneralGroup().setName(name);
		setColumn(COLUMN_NAME, name);
	}

	public void setPersonalID(String personalID) {
		setColumn(COLUMN_PERSONAL_ID, personalID);
	}

	public void setWebPage(String webPage) {
		setColumn(COLUMN_WEB_PAGE, webPage);
	}

	public void setBankAccount(String bankAccount) {
		setColumn(COLUMN_BANK_ACCOUNT, bankAccount);
	}

	public void setExtraInfo(String extraInfo) {
		setColumn(COLUMN_EXTRA_INFO, extraInfo);
	}

	public void setValid(boolean valid) {
		setColumn(COLUMN_IS_VALID, valid);
	}

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

		return idoFindOnePKByQuery(query);
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public Collection<Company> ejbFindAllWithOpenStatus() throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_OPEN), true));
		query.addOrder(table, COLUMN_NAME, true);

		return idoFindPKsByQuery(query);
	}

	@SuppressWarnings("unchecked")
	public Collection<Company> ejbFindAllActiveWithOpenStatus() throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_VALID), MatchCriteria.EQUALS, true));
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_OPEN), MatchCriteria.EQUALS, true));
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

	public String getRecipientId() {
		return getStringColumnValue(RECIPIENT_ID);
	}

	public String getRecipientName() {
		return getStringColumnValue(RECIPIENT_NAME);
	}

	public void setRecipientId(String recipient) {
		setColumn(RECIPIENT_ID, recipient);
	}

	public void setRecipientName(String recipient) {
		setColumn(RECIPIENT_NAME, recipient);
	}

	public String getBanMarking() {
		return getStringColumnValue(BAN_MARKING);
	}

	public User getCEO() {
		return (User) this.getColumnValue(CEO_ID);
	}

	public void setCEO(User ceo) {
		setColumn(CEO_ID, ceo);
	}

	public IndustryCode getIndustryCode() {
		return (IndustryCode) getColumnValue(INDUSTRY_CODE);
	}

	public void setIndustryCode(IndustryCode industryCode) {
		setColumn(INDUSTRY_CODE, industryCode);
	}

	public Date getLastChange() {
		return (Date) getColumnValue(LAST_CHANGE);
	}

	public void setLastChange(Date lastChange) {
		setColumn(LAST_CHANGE, lastChange);
	}

	public Commune getLegalCommune() {
		return (Commune) getColumnValue(LEGAL_COMMUNE);
	}

	public void setLegalCommune(Commune legalCommune) {
		setColumn(LEGAL_COMMUNE, legalCommune);
	}

	public String getOperation() {
		return getStringColumnValue(OPERATION);
	}

	public void setOperation(String operation) {
		setColumn(OPERATION, operation);
	}

	public OperationForm getOperationForm() {
		return (OperationForm) getColumnValue(OPERATION_FORM);
	}

	public void setOperationForm(OperationForm operationForm) {
		setColumn(OPERATION_FORM, operationForm);
	}

	public String getOrderAreaForName() {
		return getStringColumnValue(ORDER_AREA_FOR_NAME);
	}

	public void setOrderAreaForName(String orderAreaForName) {
		setColumn(ORDER_AREA_FOR_NAME, orderAreaForName);
	}

	public Date getRegisterDate() {
		return (Date) getColumnValue(REGISTER_DATE);
	}

	public void setRegisterDate(Date registerDate) {
		setColumn(REGISTER_DATE, registerDate);
	}

	public Date getUnregisterDate() {
		return (Date) getColumnValue(UNREGISTER_DATE);
	}

	public void setUnregisterDate(Date unregisterDate) {
		setColumn(UNREGISTER_DATE, unregisterDate);
	}

	public UnregisterType getUnregisterType() {
		return (UnregisterType) getColumnValue(UNREGISTER_TYPE);
	}

	public void setUnregisterType(UnregisterType unregisterType) {
		setColumn(UNREGISTER_TYPE, unregisterType);
	}

	public String getVATNumber() {
		return getStringColumnValue(VAT_NUMBER);
	}

	public void setVATNumber(String number) {
		setColumn(VAT_NUMBER, number);
	}

	public Commune getWorkingArea() {
		return (Commune) getColumnValue(WORKING_AREA);
	}

	public void setWorkingArea(Commune workingArea) {
		setColumn(WORKING_AREA, workingArea);
	}

	public void setAddress(Address address) {
		try {
			getGeneralGroup().addAddress(address);
		}
		catch (IDOAddRelationshipException e) {
			throw new IDORuntimeException(e.getMessage());
		}
	}

	public void setBanMarking(String banMarking) {
		setColumn(BAN_MARKING, banMarking);
	}
}