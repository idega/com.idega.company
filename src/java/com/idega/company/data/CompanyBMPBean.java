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
import com.idega.jbpm.bean.JBPMCompany;
import com.idega.user.data.Group;
import com.idega.user.data.GroupHome;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;

public class CompanyBMPBean extends GenericEntity implements Company, JBPMCompany {

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

	@Override
	@SuppressWarnings("unchecked")
	public Phone getPhone() {
		return getPhoneByType(getGeneralGroup().getPhones(), PhoneBMPBean.getHomeNumberID());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.company.data.Company#getMobilePhone()
	 */
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
	public Email getEmail() {
		Collection<Email> emails = getGeneralGroup().getEmails();
		if (ListUtil.isEmpty(emails)) {
			return null;
		}
		return emails.iterator().next();
	}
	
	@Override
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
		query.addCriteria(new MatchCriteria(table.getColumn(COLUMN_IS_OPEN), MatchCriteria.EQUALS, true));
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
	@SuppressWarnings("unchecked")
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
	public void setBanMarking(String banMarking) {
		setColumn(BAN_MARKING, banMarking);
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
		.append("getWorkingArea(): ").append(getWorkingArea()).append(CoreConstants.NEWLINE);
		return sb.toString();
	}
}