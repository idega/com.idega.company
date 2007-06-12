package com.idega.company.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;

public interface CompanyTypeHome extends IDOHome {

	public CompanyType create() throws CreateException;

	public CompanyType findByPrimaryKey(Object pk) throws FinderException;

	public Collection findAll() throws FinderException;
}