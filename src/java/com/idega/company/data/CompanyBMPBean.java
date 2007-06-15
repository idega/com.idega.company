/*
 * $Id$ Created on May 30, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package com.idega.company.data;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.idega.company.CompanyConstants;
import com.idega.data.GenericEntity;
import com.idega.data.IDOLookupException;
import com.idega.data.IDORuntimeException;
import com.idega.data.IDOStoreException;
import com.idega.data.query.MatchCriteria;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.user.data.Group;
import com.idega.user.data.GroupHome;

public class CompanyBMPBean extends GenericEntity implements Company {

	private static final String ENTITY_NAME = "ic_company";

	private static final String COLUMN_TYPE = "company_type";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_PERSONAL_ID = "personal_id";
	private static final String COLUMN_WEB_PAGE = "web_page";
	private static final String COLUMN_BANK_ACCOUNT = "bank_account";
	private static final String COLUMN_IS_VALID = "is_valid";

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
		return getBooleanColumnValue(COLUMN_IS_VALID, true);
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

	private Group getGeneralGroup() {
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
	}
}