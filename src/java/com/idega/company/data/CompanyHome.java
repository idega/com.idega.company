package com.idega.company.data;


import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.idega.data.IDOHome;
import com.idega.user.data.User;

public interface CompanyHome extends IDOHome {
	public Company findByPrimaryKey(Object pk) throws FinderException;

	public Company create() throws CreateException;

	public Company findByPersonalID(String personalID) throws FinderException;

	public Company findByName(String name) throws FinderException;

	public Collection<Company> findAll(Boolean valid) throws FinderException;

	public Collection<Company> findAllWithOpenStatus() throws FinderException;

	public Collection<Company> findAllActiveWithOpenStatus()
			throws FinderException;

	/**
	 *
	 * <p>Searches {@link Company}s, where given {@link User} ir CEO.</p>
	 * @param user - {@link Company#getCEO()}.
	 * @return {@link List} of {@link Company}s, where user is CEO or
	 * <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Collection<Company> findAll(User user);

	public Company findByUniqueId(String uniqueId) throws FinderException;

}