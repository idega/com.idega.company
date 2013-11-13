/**
 * @(#)CompanyServiceImpl.java    1.0.0 3:40:26 PM
 *
 * Idega Software hf. Source Code Licence Agreement x
 *
 * This agreement, made this 10th of February 2006 by and between
 * Idega Software hf., a business formed and operating under laws
 * of Iceland, having its principal place of business in Reykjavik,
 * Iceland, hereinafter after referred to as "Manufacturer" and Agura
 * IT hereinafter referred to as "Licensee".
 * 1.  License Grant: Upon completion of this agreement, the source
 *     code that may be made available according to the documentation for
 *     a particular software product (Software) from Manufacturer
 *     (Source Code) shall be provided to Licensee, provided that
 *     (1) funds have been received for payment of the License for Software and
 *     (2) the appropriate License has been purchased as stated in the
 *     documentation for Software. As used in this License Agreement,
 *       Licensee   shall also mean the individual using or installing
 *     the source code together with any individual or entity, including
 *     but not limited to your employer, on whose behalf you are acting
 *     in using or installing the Source Code. By completing this agreement,
 *     Licensee agrees to be bound by the terms and conditions of this Source
 *     Code License Agreement. This Source Code License Agreement shall
 *     be an extension of the Software License Agreement for the associated
 *     product. No additional amendment or modification shall be made
 *     to this Agreement except in writing signed by Licensee and
 *     Manufacturer. This Agreement is effective indefinitely and once
 *     completed, cannot be terminated. Manufacturer hereby grants to
 *     Licensee a non-transferable, worldwide license during the term of
 *     this Agreement to use the Source Code for the associated product
 *     purchased. In the event the Software License Agreement to the
 *     associated product is terminated; (1) Licensee's rights to use
 *     the Source Code are revoked and (2) Licensee shall destroy all
 *     copies of the Source Code including any Source Code used in
 *     Licensee's applications.
 * 2.  License Limitations
 *     2.1 Licensee may not resell, rent, lease or distribute the
 *         Source Code alone, it shall only be distributed as a
 *         compiled component of an application.
 *     2.2 Licensee shall protect and keep secure all Source Code
 *         provided by this this Source Code License Agreement.
 *         All Source Code provided by this Agreement that is used
 *         with an application that is distributed or accessible outside
 *         Licensee's organization (including use from the Internet),
 *         must be protected to the extent that it cannot be easily
 *         extracted or decompiled.
 *     2.3 The Licensee shall not resell, rent, lease or distribute
 *         the products created from the Source Code in any way that
 *         would compete with Idega Software.
 *     2.4 Manufacturer's copyright notices may not be removed from
 *         the Source Code.
 *     2.5 All modifications on the source code by Licencee must
 *         be submitted to or provided to Manufacturer.
 * 3.  Copyright: Manufacturer's source code is copyrighted and contains
 *     proprietary information. Licensee shall not distribute or
 *     reveal the Source Code to anyone other than the software
 *     developers of Licensee's organization. Licensee may be held
 *     legally responsible for any infringement of intellectual property
 *     rights that is caused or encouraged by Licensee's failure to abide
 *     by the terms of this Agreement. Licensee may make copies of the
 *     Source Code provided the copyright and trademark notices are
 *     reproduced in their entirety on the copy. Manufacturer reserves
 *     all rights not specifically granted to Licensee.
 *
 * 4.  Warranty & Risks: Although efforts have been made to assure that the
 *     Source Code is correct, reliable, date compliant, and technically
 *     accurate, the Source Code is licensed to Licensee as is and without
 *     warranties as to performance of merchantability, fitness for a
 *     particular purpose or use, or any other warranties whether
 *     expressed or implied. Licensee's organization and all users
 *     of the source code assume all risks when using it. The manufacturers,
 *     distributors and resellers of the Source Code shall not be liable
 *     for any consequential, incidental, punitive or special damages
 *     arising out of the use of or inability to use the source code or
 *     the provision of or failure to provide support services, even if we
 *     have been advised of the possibility of such damages. In any case,
 *     the entire liability under any provision of this agreement shall be
 *     limited to the greater of the amount actually paid by Licensee for the
 *     Software or 5.00 USD. No returns will be provided for the associated
 *     License that was purchased to become eligible to receive the Source
 *     Code after Licensee receives the source code.
 */
package com.idega.company.business.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import javax.ejb.EJBException;
import javax.ejb.FinderException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.company.business.CompanyBusiness;
import com.idega.company.business.CompanyService;
import com.idega.company.data.Company;
import com.idega.core.accesscontrol.business.AccessController;
import com.idega.core.business.DefaultSpringBean;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.Commune;
import com.idega.core.location.data.PostalCode;
import com.idega.user.business.GroupBusiness;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Gender;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.CoreUtil;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;

/**
 * @see CompanyService
 * <p>You can report about problems to:
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Dec 4, 2012
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Service(CompanyService.BEAN_IDENTIFIER)
public class CompanyServiceImpl extends DefaultSpringBean implements CompanyService {

	private CompanyBusiness companyBusiness;

	private CompanyBusiness getCompanyBusiness() {
		if (this.companyBusiness == null) {
			this.companyBusiness = getServiceInstance(CompanyBusiness.class);
		}

		return this.companyBusiness;
	}

	@Override
	public String getName(String personalID) {
		Company company = getCompany(personalID);
		if (company == null) {
			return CoreConstants.EMPTY;
		}

		return company.getName();
	}

	@Override
	public Address getAddress(String personalID) {
		Company company = getCompany(personalID);
		if (company == null) {
			return null;
		}

		return company.getAddress();
	}

	@Override
	public String getStreetAddress(String personalID) {
		Address address = getAddress(personalID);
		if (address == null) {
			return CoreConstants.EMPTY;
		}

		return address.getStreetAddress();
	}

	@Override
	public Email getEmail(String personalID) {
		Company company = getCompany(personalID);
		if (company == null) {
			return null;
		}

		return company.getEmail();
	}

	@Override
	public String getEmailAddress(String personalID) {
		Email email = getEmail(personalID);
		if (email == null) {
			return CoreConstants.EMPTY;
		}

		return email.getEmailAddress();
	}

	@Override
	public PostalCode getPostalCode(String personalID) {
		Address address = getAddress(personalID);
		if (address == null) {
			return null;
		}

		return address.getPostalCode();
	}

	@Override
	public String getPostalCodeString(String personalID) {
		PostalCode postalCode = getPostalCode(personalID);
		if (postalCode == null) {
			return CoreConstants.EMPTY;
		}

		return postalCode.getPostalCode();
	}

	@Override
	public Commune getWorkingArea(String personalID) {
		Company company = getCompany(personalID);
		if (company == null) {
			return null;
		}

		return company.getWorkingArea();
	}

	@Override
	public String getWorkingAreaString(String personalID) {
		Commune workingArea = getWorkingArea(personalID);
		if (workingArea == null) {
			return CoreConstants.EMPTY;
		}

		return workingArea.getCommuneName();
	}

	@Override
	public Phone getPhone(String personalID) {
		Company company = getCompany(personalID);
		if (company == null) {
			return null;
		}

		return company.getPhone();
	}

	@Override
	public String getPhoneNumber(String personalID) {
		Phone phone = getPhone(personalID);
		if (phone == null) {
			return CoreConstants.EMPTY;
		}

		return phone.getNumber();
	}

	@Override
	public Phone getMobilePhone(String personalID) {
		Company company = getCompany(personalID);
		if (company == null) {
			return null;
		}

		return company.getMobilePhone();
	}

	@Override
	public String getMobilePhoneNumber(String personalID) {
		Phone phone = getMobilePhone(personalID);
		if (phone == null) {
			return CoreConstants.EMPTY;
		}

		return phone.getNumber();
	}

	@Override
	public Phone getFax(String personalID) {
		Company company = getCompany(personalID);
		if (company == null) {
			return null;
		}

		return company.getFax();
	}

	@Override
	public String getFaxNumber(String personalID) {
		 Phone fax = getFax(personalID);
		if (fax == null) {
			return CoreConstants.EMPTY;
		}

		return fax.getNumber();
	}

	@Override
	public User getCEO(String personalID) {
		Company company = getCompany(personalID);
		if (company == null) {
			return null;
		}

		return company.getCEO();
	}

	@Override
	public String getCEOName(String personalID) {
		User ceo = getCEO(personalID);
		if (ceo == null) {
			return CoreConstants.EMPTY;
		}

		return ceo.getName();
	}

	@Override
	public String getCEOPersonalID(String personalID) {
		User ceo = getCEO(personalID);
		if (ceo == null) {
			return CoreConstants.EMPTY;
		}

		return ceo.getPersonalID();
	}

	@Override
	public String getRole(String personalID, String employeePersonalID) {
		if (StringUtil.isEmpty(personalID) || StringUtil.isEmpty(employeePersonalID))
			return null;

		Company company = getCompany(personalID);
		if (company == null)
			return null;

		User ceo = company.getCEO();
		if (ceo != null && employeePersonalID.equals(ceo.getPersonalID()))
			return "CEO";

		if (personalID.equals(employeePersonalID))
			return "CEO";

		getLogger().warning("Unknown employee's (personal ID: " + employeePersonalID + ") role in company " + company);
		return null;
	}

	@Override
	public Company getCompany(String personalID) {
		if (StringUtil.isEmpty(personalID)) {
			return null;
		}

		try {
			return getCompanyBusiness().getCompany(personalID);
		} catch (FinderException e) {
			getLogger().log(Level.WARNING,
					"Unable to find company by personal ID: " + personalID);
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to search database: ", e);
		}

		try {
			return getCompanyBusiness().getCompany((Object) personalID);
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to find company by personal ID: " + personalID, e);
		} catch (FinderException e) {
			getLogger().log(Level.WARNING,
					"Unable to find company by primary key: " + personalID);
		}

		return null;
	}

	public Gender getCEOGender(String personalID) {
		User ceo = getCEO(personalID);
		if (ceo == null) {
			return null;
		}

		return ceo.getGender();
	}

	@Override
	public String getCEOGenderID(String personalID) {
		Gender gender = getCEOGender(personalID);
		if (gender == null) {
			return "3";
		}

		return gender.getPrimaryKey().toString();
	}

	@Override
	public Company getCompany(User user) {
		if (user == null) {
			getLogger().warning("User is not provided, unable to find company");
			return null;
		}

		Collection<Company> companies = getCompanyBusiness().getCompaniesForUser(user);
		if (ListUtil.isEmpty(companies)) {
			return null;
		}

		return companies.iterator().next();
	}

	@Override
	public ArrayList<Company> getCompanies(Collection<User> users) {
		if (ListUtil.isEmpty(users)) {
			return null;
		}

		ArrayList<Company> companies = new ArrayList<Company>();
		Collection<Company> companiesCollection = null;
		for (User user: users) {
			companiesCollection = getCompanyBusiness().getCompaniesForUser(user);
			if (!ListUtil.isEmpty(companiesCollection)) {
				companies.addAll(companiesCollection);
			}
		}

		return companies;
	}

	@Override
	public ArrayList<String> getIDsOfCompanies(Collection<Company> companies) {
		if (ListUtil.isEmpty(companies)) {
			return null;
		}

		ArrayList<String> primaryKeys = new ArrayList<String>();
		for (Company company: companies) {
			primaryKeys.add(company.getPrimaryKey().toString());
		}

		return primaryKeys;
	}

	@Override
	public ArrayList<Company> getCompaniesByUserIDs(Collection<String> users) {
		if (ListUtil.isEmpty(users)) {
			return null;
		}

		return getCompanies(getUsers(users));
	}

	protected Collection<User> getUsers(Collection<String> ids) {
		if (ListUtil.isEmpty(ids)) {
			return null;
		}

		try {
			return getUserBusiness().getUsers(ids.toArray(new String[ids.size()]));
		} catch (EJBException e) {
			getLogger().log(Level.WARNING, "Unable to get " + User.class, e);
		} catch (RemoteException e) {
			getLogger().log(Level.WARNING, "Unable to get " + User.class, e);
		}

		return null;
	}

	protected UserBusiness getUserBusiness() {
		return getServiceInstance(UserBusiness.class);
	}

	protected GroupBusiness getGroupBusiness() {
		return getServiceInstance(GroupBusiness.class);
	}

	@Override
	public ArrayList<Company> getCompaniesByOwnerRoles(Collection<String> roles) {
		return getCompanies(getUsersByRoles(roles));
	}

	protected ArrayList<User> getUsersByRoles(Collection<String> roles) {
		if (ListUtil.isEmpty(roles)) {
			getLogger().warning("Roles are not provided");
			return null;
		}

		ArrayList<User> users = new ArrayList<User>();
		GroupBusiness groupBusiness = getGroupBusiness();
		if (groupBusiness == null) {
			return null;
		}

		UserBusiness userBusiness = getUserBusiness();
		if (userBusiness == null) {
			return null;
		}

		AccessController accessController = CoreUtil.getIWContext().getAccessController();
		for (String roleKey: roles) {
			Collection<Group> groupsByRole = accessController.getAllGroupsForRoleKeyLegacy(roleKey, CoreUtil.getIWContext());
			if (ListUtil.isEmpty(groupsByRole)) {
				continue;
			}

			for (Group group: groupsByRole) {
				if (StringUtil.isEmpty(group.getName())) {
					try {
						User user = userBusiness.getUser(Integer.valueOf(group.getId()));
						if (accessController.hasRole(user, roleKey) && !users.contains(user)) {
							users.add(user);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
					}
				}

				Collection<User> usersInGroup = null;
				try {
					usersInGroup = groupBusiness.getUsers(group);
				} catch (FinderException e) {
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (!ListUtil.isEmpty(usersInGroup)) {
					for (User user: usersInGroup) {
						if (!users.contains(user)) {
							users.add(user);
						}
					}
				}
			}
		}

		if (ListUtil.isEmpty(users)) {
			getLogger().warning("There are no users by role(s): " + roles);
		}
		return users;
	}

	@Override
	public ArrayList<User> getOwnersByCompanies(Collection<Company> companies) {
		Collection<User> users = getCompanyBusiness().getOwnersForCompanies(companies);
		if (ListUtil.isEmpty(users)) {
			return null;
		}

		return new ArrayList<User>(users);
	}

	@Override
	public ArrayList<String> getOwnersIDsByCompanies(Collection<Company> companies) {
		Collection<String> users = getCompanyBusiness().getOwnersIDsForCompanies(companies);
		if (ListUtil.isEmpty(users)) {
			return null;
		}

		return new ArrayList<String>(users);
	}

	@Override
	public ArrayList<String> getOwnersIDsForCompaniesByIDs(Collection<String> companiesIDs) {
		Collection<String> ids = getCompanyBusiness().getOwnersIDsForCompaniesByIDs(companiesIDs);
		if (ListUtil.isEmpty(ids)) {
			return null;
		}

		return new ArrayList<String>(ids);
	}

	@Override
	public String getName(User user) {
		if (user == null) {
			return null;
		}

		Company company = getCompany(user);
		if (company == null) {
			return null;
		}

		return company.getName();
	}
}
