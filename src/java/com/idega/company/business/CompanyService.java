/**
 * @(#)CompanyService.java    1.0.0 3:28:00 PM
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
package com.idega.company.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.idega.block.process.business.ExternalEntityInterface;
import com.idega.company.data.Company;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.Commune;
import com.idega.core.location.data.PostalCode;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;

/**
 * <p>Helps {@link Company} interact with {@link XForm}s.</p>
 * <p>You can report about problems to:
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Dec 4, 2012
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public interface CompanyService extends ExternalEntityInterface {

	public static final String BEAN_IDENTIFIER = "companyService";
	public static final String JAVASCRIPT_NAME = "CompanyService";

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Company#getName()} or {@link CoreConstants#EMPTY} on
	 * failure.
	 */
	public String getName(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Company#getAddress()} or {@link CoreConstants#EMPTY} on
	 * failure.
	 */
	public String getStreetAddress(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Company#getEmail()} or {@link CoreConstants#EMPTY} on
	 * failure.
	 */
	public String getEmailAddress(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link PostalCode} of {@link Company#getAddress()} or
	 * {@link CoreConstants#EMPTY} on failure.
	 */
	public String getPostalCodeString(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Company#getWorkingArea()} or {@link CoreConstants#EMPTY}
	 * on failure.
	 */
	public String getWorkingAreaString(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Company#getPhone()} or {@link CoreConstants#EMPTY} on
	 * failure.
	 */
	public String getPhoneNumber(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Company#getMobilePhone()} or {@link CoreConstants#EMPTY} on
	 * failure.
	 */
	public String getMobilePhoneNumber(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Company#getPhone()} or {@link CoreConstants#EMPTY} on
	 * failure.
	 */
	public String getFaxNumber(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return Name of {@link Company#getCEO()} or {@link CoreConstants#EMPTY}
	 * on failure.
	 */
	public String getCEOName(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link User#getPersonalID()} of {@link Company#getCEO()} or
	 * {@link CoreConstants#EMPTY} on failure.
	 */
	public String getCEOPersonalID(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not
	 * <code>null</code>.
	 * @param employeePersonalID of employee in given {@link Company}.
	 * @return Role name of man working at {@link Company} or
	 * {@link CoreConstants#EMPTY} on failure.
	 */
	public String getRole(String personalID, String employeePersonalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not
	 * <code>null</code>.
	 * @return {@link User#getGender()} of {@link Company#getCEO()} or
	 * <code>null</code> on failure.
	 */
	public String getCEOGenderID(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Company} by given id or <code>null</code> on failure.
	 */
	public Company getCompany(String personalID);

	/**
	 *
	 * <p>Searches {@link Company} for given {@link User}. If there is more
	 * than one, returns first one.</p>
	 * @param user - {@link Company#getCEO()}.
	 * @return {@link Company}, where {@link User} is CEO or <code>null</code>
	 * on failure.
	 */
	public Company getCompany(User user);

	/**
	 * TODO Optimize;
	 * <p>Gets {@link Company}s by given {@link User}s, who are owners of
	 * companies.</p>
	 * @param users who own companies, not empty;
	 * @return owned {@link Company}s by {@link User}s or
	 * <code>null</code> on failure.
	 */
	public ArrayList<Company> getCompanies(Collection<User> users);

	/**
	 *
	 * @param users ids to search by, not empty.
	 * @return owned {@link Company}s by {@link User}s or
	 * <code>null</code> on failure.
	 */
	public ArrayList<Company> getCompaniesByUserIDs(Collection<String> users);

	/**
	 *
	 * @param roles assigned to {@link Group}s, which defines permissions and etc.,
	 * for example: "bpm_cases_manager". Roles are defined via
	 * Lucid->Users->Edit group->Roles.
	 * @return {@link List} of {@link Company}s, which belongs to {@link User}s
	 * who are in {@link Group}s having specified roles, <code>null</code>
	 * on failure.
	 */
	public ArrayList<Company> getCompaniesByOwnerRoles(Collection<String> roles);

	/**
	 *
	 * <p>Searches database for owners of given companies</p>
	 * @param companies - {@link List} of {@link Company}
	 * to search by, not <code>null</code>;
	 * @return {@link List} of {@link User}s, who are owners
	 * of given {@link Company}s, <code>null</code> on failure.
	 */
	public ArrayList<User> getOwnersByCompanies(Collection<Company> companies);

	/**
	 *
	 * <p>Searches database for owners of given companies</p>
	 * @param companies - {@link List} of {@link Company}
	 * to search by, not <code>null</code>;
	 * @return {@link List} of {@link User#getPrimaryKey()}, who are owners
	 * of given {@link Company}s, <code>null</code> on failure.
	 */
	public ArrayList<String> getOwnersIDsByCompanies(Collection<Company> companies);

	/**
	 *
	 * <p>Searches database for owners of given companies</p>
	 * @param companiesIDs - {@link List} of {@link Company#getPrimaryKey()}
	 * to search by, not <code>null</code>;
	 * @return {@link List} of {@link User#getPrimaryKey()}, who are owners
	 * of given {@link Company}s, <code>null</code> on failure.
	 */
	public ArrayList<String> getOwnersIDsForCompaniesByIDs(Collection<String> companiesIDs);

	/**
	 *
	 * @param companies to get primary keys, not empty;
	 * @return {@link Company#getPrimaryKey()} of each {@link Company} or
	 * <code>null</code> on failure.
	 */
	public ArrayList<String> getIDsOfCompanies(Collection<Company> companies);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Address} of {@link Company} or <code>null</code>
	 * on failure.
	 */
	public Address getAddress(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Email} of {@link Company} or <code>null</code> on failure.
	 */
	public Email getEmail(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link PostalCode} of {@link Company} or <code>null</code>
	 * on failure.
	 */
	public PostalCode getPostalCode(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Commune} of {@link Company} or <code>null</code> on
	 * failure.
	 */
	public Commune getWorkingArea(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Phone} of {@link Company} or <code>null</code>
	 * on failure.
	 */
	public Phone getPhone(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Phone} of {@link Company} or <code>null</code>
	 * on failure.
	 */
	public Phone getMobilePhone(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Company#getCEO()} or <code>null</code> on failure.
	 */
	public User getCEO(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()}, not <code>null</code>.
	 * @return {@link Company#getFax()} or <code>null</code> on failure.
	 */
	public Phone getFax(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()} or
	 * {@link User#getPersonalID()}, not <code>null</code>;
	 * @return {@link Company#getName()} or {@link User#getName()} or
	 * {@link CoreConstants#EMPTY} on failure.
	 */
	String getNameOfUserOrCompany(String personalID);

	/**
	 *
	 * @param personalID - {@link Company#getPersonalID()} or
	 * {@link User#getPersonalID()}, not <code>null</code>;
	 * @return {@link Company#getEmail()} or {@link User#getUsersEmail()} or
	 * {@link CoreConstants#EMPTY} on failure.
	 */
	String getMailOfUserOrCompany(String personalID);
}
