/*
 * $Id$ Created on Jun 12, 2007
 * 
 * Copyright (C) 2007 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to license terms.
 */
package com.idega.company;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWBundleStartable;
import com.idega.user.business.GroupBusiness;
import com.idega.user.data.GroupType;

public class IWBundleStarter implements IWBundleStartable {
	
	public static final String IW_BUNDLE_IDENTIFIER = "com.idega.company";

	public void start(IWBundle starterBundle) {
		updateData(starterBundle.getApplication().getIWApplicationContext());
	}

	public void stop(IWBundle starterBundle) {
	}

	private void updateData(IWApplicationContext iwac) {
		insertGroupType(iwac, CompanyConstants.GROUP_TYPE_COMPANY);
	}

	private void insertGroupType(IWApplicationContext iwac, String groupType) {
		try {
			GroupBusiness business = (GroupBusiness) IBOLookup.getServiceInstance(iwac, GroupBusiness.class);
			GroupType type;
			try {
				type = business.getGroupTypeFromString(groupType);
			}
			catch (FinderException e) {
				type = business.getGroupTypeHome().create();
				type.setType(groupType);
				type.setVisibility(true);
				type.store();
			}
		}
		catch (IBOLookupException e) {
			e.printStackTrace();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		catch (CreateException e) {
			e.printStackTrace();
		}
	}
}
