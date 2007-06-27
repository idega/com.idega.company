/*
 * $Id: CompanyRegisterBMPBean.java,v 1.3 2007/06/27 09:43:47 alexis Exp $
 * Created on Nov 20, 2006
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.company.companyregister.data;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.company.data.CompanyBMPBean;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.Commune;
import com.idega.user.data.User;


public class CompanyRegisterBMPBean extends CompanyBMPBean implements CompanyRegister {

	protected static final String ENTITY_NAME = "ic_company";
	
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

	public String getEntityName() {
		return ENTITY_NAME;
	}

	public void initializeAttributes() {
		super.initializeAttributes();
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
	
	
	
	public Collection ejbFindAll() throws FinderException, RemoteException {
		return super.idoFindAllIDsBySQL();
	}

//	public Collection ejbFindAllBySSN(String ssn) throws FinderException, RemoteException {
//		StringBuffer query = new StringBuffer("select "+ getIDColumnName() +" from ");
//		query.append(this.getEntityName());
//		query.append(" where ");
//		query.append(SSN);
//		query.append(" = '");
//		query.append(ssn);
//		query.append("'");
//		
//		return idoFindPKsBySQL(query.toString());
//	}
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
		return (IndustryCode) getColumnValue(RECIPIENT_ID);
	}
	public void setIndustryCode(IndustryCode industryCode) {
		setColumn(CEO_ID, industryCode);
	}
	public String getLastChange() {
		return getStringColumnValue(BAN_MARKING);
	}
	public void setLastChange(String lastChange) {
		setColumn(CEO_ID, lastChange);
	}
	public Commune getLegalCommune() {
		return (Commune) getColumnValue(RECIPIENT_ID);
	}
	public void setLegalCommune(Commune legalCommune) {
		setColumn(CEO_ID, legalCommune);
	}
	public String getOperation() {
		return getStringColumnValue(BAN_MARKING);
	}
	public void setOperation(String operation) {
		setColumn(CEO_ID, operation);
	}
	public OperationForm getOperationForm() {
		return (OperationForm) getColumnValue(RECIPIENT_ID);
	}
	public void setOperationForm(OperationForm operationForm) {
		setColumn(CEO_ID, operationForm);
	}
	public String getOrderAreaForName() {
		return getStringColumnValue(BAN_MARKING);
	}
	public void setOrderAreaForName(String orderAreaForName) {
		setColumn(CEO_ID, orderAreaForName);
	}
	public String getRegisterDate() {
		return getStringColumnValue(BAN_MARKING);
	}
	public void setRegisterDate(String registerDate) {
		setColumn(CEO_ID, registerDate);
	}
	public String getUnregisterDate() {
		return getStringColumnValue(BAN_MARKING);
	}
	public void setUnregisterDate(String unregisterDate) {
		setColumn(CEO_ID, unregisterDate);
	}
	public UnregisterType getUnregisterType() {
		return (UnregisterType) getColumnValue(RECIPIENT_ID);
	}
	public void setUnregisterType(UnregisterType unregisterType) {
		setColumn(CEO_ID, unregisterType);
	}
	public String getVATNumber() {
		return getStringColumnValue(BAN_MARKING);
	}
	public void setVATNumber(String number) {
		setColumn(CEO_ID, number);
	}
	public Commune getWorkingArea() {
		return (Commune) getColumnValue(RECIPIENT_ID);
	}
	public void setWorkingArea(Commune workingArea) {
		
	}
	public void setAddress(Address address) {
		
	}
}
