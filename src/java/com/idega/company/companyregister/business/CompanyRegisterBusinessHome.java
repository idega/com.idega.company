/*
 * $Id: CompanyRegisterBusinessHome.java,v 1.1 2007/06/26 09:30:45 alexis Exp $
 * Created on 14.9.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.company.companyregister.business;

import com.idega.business.IBOHome;


/**
 * 
 *  Last modified: $Date: 2007/06/26 09:30:45 $ by $Author: alexis $
 * 
 * @author <a href="mailto:Joakim@idega.com">Joakim</a>
 * @version $Revision: 1.1 $
 */
public interface CompanyRegisterBusinessHome extends IBOHome {

	public CompanyRegisterBusiness create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
