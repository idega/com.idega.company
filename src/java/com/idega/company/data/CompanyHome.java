package com.idega.company.data;


import java.util.Collection;
import javax.ejb.CreateException;
import com.idega.data.IDOHome;
import javax.ejb.FinderException;

public interface CompanyHome extends IDOHome {

	public Company findByPrimaryKey(Object pk) throws FinderException;

	public Company create() throws CreateException;

	public Company findByPersonalID(String personalID) throws FinderException;
	
	public Company findByName(String name) throws FinderException;

	public Collection<Company> findAll(Boolean valid) throws FinderException;

	public Collection<Company> findAllWithOpenStatus() throws FinderException;
}