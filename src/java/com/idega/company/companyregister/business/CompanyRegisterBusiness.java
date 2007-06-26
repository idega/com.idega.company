/*
 * $Id: CompanyRegisterBusiness.java,v 1.1 2007/06/26 09:30:45 alexis Exp $
 * Created on 14.9.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.company.companyregister.business;

import is.idega.block.family.business.FamilyLogic;
import com.idega.company.companyregister.data.CompanyRegister;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import com.idega.business.IBOLookupException;
import com.idega.business.IBOService;
import com.idega.core.location.data.Country;
import com.idega.core.location.data.PostalCode;
import com.idega.presentation.PresentationObject;
import com.idega.user.business.UserBusiness;
import com.idega.user.business.UserGroupPlugInBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;


/**
 * 
 *  Last modified: $Date: 2007/06/26 09:30:45 $ by $Author: alexis $
 * 
 * @author <a href="mailto:Joakim@idega.com">Joakim</a>
 * @version $Revision: 1.1 $
 */
public interface CompanyRegisterBusiness extends IBOService , UserGroupPlugInBusiness {

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#getEntryBySSN
	 */
	public CompanyRegister getEntryBySSN(String ssn) throws java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#updateEntry
	 */
	public boolean updateEntry(String symbol, String oldId, String ssn, String familyId, String name, String commune,
			String street, String building, String floor, String sex, String maritialStatus, String empty,
			String prohibitMarking, String nationality, String placeOfBirth, String spouseSSN, String fate,
			String parish, String po, String address, String addressCode, String dateOfModification,
			String placementCode, String dateOfCreation, String lastDomesticAddress, String agentSsn, String sNew,
			String addressName, String dateOfDeletion, String newSsnOrName, String dateOfBirth, Group citizenGroup)
			throws java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#updateUserPersonalID
	 */
	public void updateUserPersonalID(String oldPersonalID, String newPersonalID) throws IBOLookupException,
			java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#updateUserOldID
	 */
	public void updateUserOldID(String oldID, String personalID) throws IBOLookupException, java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#updateUserAddress
	 */
	public void updateUserAddress(User user, UserBusiness userBiz, String address, String po, Country country, String city, Integer communeID) throws RemoteException,
			CreateException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#getPostalCode
	 */
	public PostalCode getPostalCode(String po) throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#beforeUserRemove
	 */
	public void beforeUserRemove(User user, Group parentGroup) throws RemoveException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#afterUserCreate
	 */
	public void afterUserCreateOrUpdate(User user, Group parentGroup) throws CreateException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#beforeGroupRemove
	 */
	public void beforeGroupRemove(Group group, Group parentGroup) throws RemoveException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#afterGroupCreate
	 */
	public void afterGroupCreateOrUpdate(Group group, Group parentGroup) throws CreateException, RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#getPresentationObjectClass
	 */
	public Class getPresentationObjectClass() throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#instanciateEditor
	 */
	public PresentationObject instanciateEditor(Group group) throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#instanciateViewer
	 */
	public PresentationObject instanciateViewer(Group group) throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#getUserPropertiesTabs
	 */
	public List getUserPropertiesTabs(User user) throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#getGroupPropertiesTabs
	 */
	public List getGroupPropertiesTabs(Group group) throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#getMainToolbarElements
	 */
	public List getMainToolbarElements() throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#getGroupToolbarElements
	 */
	public List getGroupToolbarElements(Group group) throws RemoteException;     

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#getListViewerFields
	 */
	public Collection getListViewerFields() throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#findGroupsByFields
	 */
	public Collection findGroupsByFields(Collection listViewerFields, Collection finderOperators,
			Collection listViewerFieldValues) throws RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#isUserAssignableFromGroupToGroup
	 */
	public String isUserAssignableFromGroupToGroup(User user, Group sourceGroup, Group targetGroup)
			throws java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#isUserSuitedForGroup
	 */
	public String isUserSuitedForGroup(User user, Group targetGroup) throws java.rmi.RemoteException;

	/**
	 * @see is.idega.block.nationalregister.business.CompanyRegisterBusinessBean#getFamilyLogic
	 */
	public FamilyLogic getFamilyLogic() throws RemoteException;
}
