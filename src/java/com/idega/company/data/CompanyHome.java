package com.idega.company.data;

import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;

public interface CompanyHome extends IDOHome {

	public Company create() throws CreateException;

	public Company findByPrimaryKey(Object pk) throws FinderException;

	public Company findByPersonalID(String personalID) throws FinderException;
}