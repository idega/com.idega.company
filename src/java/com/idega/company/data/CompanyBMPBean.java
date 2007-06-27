/*
 * $Id$ Created on May 30, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package com.idega.company.data;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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

public class CompanyBMPBean extends GenericEntity implements Company {

	private static final String ENTITY_NAME = "ic_company";

	private static final String COLUMN_TYPE = "company_type";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_PERSONAL_ID = "personal_id";
	private static final String COLUMN_WEB_PAGE = "web_page";
	private static final String COLUMN_BANK_ACCOUNT = "bank_account";
	private static final String COLUMN_IS_VALID = "is_valid";
	private static final String COLUMN_IS_OPEN = "is_open";
	
	protected static final String VAT_NUMBER = "vat_number";
	
	protected static final String CEO_ID = "ceo_id";

	protected static final String WORKING_AREA = "working_area";

	protected static final String ORDER_AREA_FOR_NAME = "order_area_for_name";

	protected static final String LAST_CHANGE = "last_change";

	protected static final String OPERATION_FORM = "operation_form";

	protected static final String LEGAL_COMMUNE = "legal_commune";

	protected static final String REGISTER_DATE = "register_date";

	protected static final String RECIPIENT_ID = "recipient_id";

	protected static final String OPERATION = "operation";
	
	protected static final String INDUSTRY_CODE = "industry_code";
	
	protected static final String UNREGISTER_TYPE = "unregister_type";
	
	protected static final String UNREGISTER_DATE = "unregister_date";
	
	protected static final String BAN_MARKING = "ban_marking";

	private Group iGroup;

	protected boolean doInsertInCreate() {
		return true;
	}

	public String getEntityName() {
		return ENTITY_NAME;
	}

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
		
		addAttribute(VAT_NUMBER, "VAT Number", true, true, java.lang.String.class, 5);
		addAttribute(OPERATION, "Operation", true, true, java.lang.String.class, 10);
		addAttribute(UNREGISTER_DATE, "Unregister Date", true, true, String.class, 21);
		addAttribute(ORDER_AREA_FOR_NAME, "Order Area For Name", true, true, java.lang.String.class, 31);
		addAttribute(LAST_CHANGE, "Last Change", true, true, java.lang.String.class, 21);
		addAttribute(REGISTER_DATE, "Register Date", true, true, java.lang.String.class, 21);
		addAttribute(BAN_MARKING, "Ban Marking", true, true, java.lang.String.class, 1);
		addManyToOneRelationship(LEGAL_COMMUNE, "Legal Commune", Commune.class);
		addManyToOneRelationship(OPERATION_FORM, "Operation form", OperationForm.class);
		addManyToOneRelationship(CEO_ID, "CEO ID", User.class);
		addManyToOneRelationship(WORKING_AREA, "Working area", Commune.class);
		addManyToOneRelationship(INDUSTRY_CODE, "Industry Code", IndustryCode.class);
		addManyToOneRelationship(RECIPIENT_ID, "Recipient ID", User.class);
		addManyToOneRelationship(UNREGISTER_TYPE, "Unregister Type", UnregisterType.class);
	}

	// Getters
	public Group getGroup() {
		return (Group) getColumnValue(getIDColumnName());
	}

	public CompanyType getType() {
		return (CompanyType) getColumnValue(COLUMN_TYPE);
	}

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

	public boolean isValid() {
		return getBooleanColumnValue(COLUMN_IS_VALID, false);
	}

	public boolean isOpen() {
		return getBooleanColumnValue(COLUMN_IS_OPEN, false);
	}

	public Address getAddress() {
		try {
			AddressHome home = (AddressHome) getIDOHome(Address.class);
			Collection addresses = getGeneralGroup().getAddresses(home.getAddressType1());
			Iterator iterator = addresses.iterator();
			while (iterator.hasNext()) {
				return (Address) iterator.next();
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

	public Phone getPhone() {
		Collection collection = getGeneralGroup().getPhones();
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			Phone phone = (Phone) iterator.next();
			if (phone.getPhoneTypeId() == PhoneBMPBean.getHomeNumberID()) {
				return phone;
			}
		}

		return null;
	}

	public Phone getFax() {
		Collection collection = getGeneralGroup().getPhones();
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			Phone phone = (Phone) iterator.next();
			if (phone.getPhoneTypeId() == PhoneBMPBean.getFaxNumberID()) {
				return phone;
			}
		}

		return null;
	}

	public Email getEmail() {
		Collection emails = getGeneralGroup().getEmails();
		Iterator iterator = emails.iterator();
		while (iterator.hasNext()) {
			return (Email) iterator.next();
		}

		return null;
	}

	// Setters
	public void setGroup(Group group) {
		setPrimaryKey(group.getPrimaryKey());
	}

	public void setType(CompanyType type) {
		setColumn(COLUMN_TYPE, type);
	}

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

	public void setValid(boolean valid) {
		setColumn(COLUMN_IS_VALID, valid);
	}

	public void setOpen(boolean open) {
		setColumn(COLUMN_IS_OPEN, open);
	}

	// Finders and creators
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

	public Collection ejbFindAll(Boolean valid) throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		if (valid != null) {
			query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_VALID), MatchCriteria.EQUALS, valid));
		}

		return idoFindPKsByQuery(query);
	}

	// General methods
	public void store() throws IDOStoreException {
		getGeneralGroup().store();
		super.store();
	}

	public void remove() throws RemoveException {
		super.remove();
		getGeneralGroup().remove();
	}

	protected Group getGeneralGroup() {
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

	public void setDefaultValues() {
		setValid(true);
		setOpen(false);
	}
	
	public User getRecipient() {
		return (User) getColumnValue(RECIPIENT_ID);
	}
	
	public void setRecipient(User recipient) {
		setColumn(RECIPIENT_ID, recipient);
	}
	public String getBanMarking() {
		return getStringColumnValue(BAN_MARKING);
	}
	
	public void setBanMarking(String banMarking) {
		setColumn(BAN_MARKING, banMarking);
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
}