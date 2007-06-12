/*
 * $Id$ Created on Jun 9, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package com.idega.company.data;

import java.util.Collection;
import java.util.Locale;

import javax.ejb.FinderException;

import com.idega.company.CompanyConstants;
import com.idega.data.GenericEntity;
import com.idega.data.query.SelectQuery;
import com.idega.data.query.Table;
import com.idega.idegaweb.IWApplicationContext;

public class CompanyTypeBMPBean extends GenericEntity implements CompanyType {

	private static final String ENTITY_NAME = "com_company_type";

	private static final String COLUMN_TYPE = "company_type";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_DESCRIPTION = "description";
	private static final String COLUMN_LOCALIZED_KEY = "localized_key";
	private static final String COLUMN_ORDER = "type_order";

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public String getIDColumnName() {
		return COLUMN_TYPE;
	}

	public Class getPrimaryKeyClass() {
		return String.class;
	}

	public void initializeAttributes() {
		addAttribute(COLUMN_TYPE, "Code", String.class, 20);
		this.setAsPrimaryKey(COLUMN_TYPE, true);

		addAttribute(COLUMN_NAME, "Name", String.class);
		addAttribute(COLUMN_DESCRIPTION, "Description", String.class);
		addAttribute(COLUMN_LOCALIZED_KEY, "Localized key", String.class);
		addAttribute(COLUMN_ORDER, "Order", Integer.class);
	}

	// Getters
	public String getName() {
		return getStringColumnValue(COLUMN_NAME);
	}

	public String getDescription() {
		return getStringColumnValue(COLUMN_DESCRIPTION);
	}

	public String getLocalizedKey() {
		return getStringColumnValue(COLUMN_LOCALIZED_KEY);
	}

	public String getLocalizedName(IWApplicationContext iwac, Locale locale) {
		String key = getLocalizedKey();
		if (key != null) {
			return iwac.getIWMainApplication().getBundle(CompanyConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(locale).getLocalizedString(key, getName());
		}
		return getName();
	}

	// Setters
	public void setName(String name) {
		setColumn(COLUMN_NAME, name);
	}

	public void setDescription(String description) {
		setColumn(COLUMN_DESCRIPTION, description);
	}

	public void setLocalizedKey(String localizedKey) {
		setColumn(COLUMN_LOCALIZED_KEY, localizedKey);
	}

	public void getLocalizedName(IWApplicationContext iwac, Locale locale, String localizedKey, String name) {
		setLocalizedKey(localizedKey);
		iwac.getIWMainApplication().getBundle(CompanyConstants.IW_BUNDLE_IDENTIFIER).getResourceBundle(locale).setLocalizedString(localizedKey, name);
	}

	public void setOrder(int order) {
		setColumn(COLUMN_ORDER, order);
	}

	// Finders
	public Collection ejbFindAll() throws FinderException {
		Table table = new Table(this);

		SelectQuery query = new SelectQuery(table);
		query.addColumn(table.getColumn(getIDColumnName()));
		query.addOrder(table, COLUMN_ORDER, true);
		query.addOrder(table, COLUMN_NAME, true);

		return idoFindPKsByQuery(query);
	}
}