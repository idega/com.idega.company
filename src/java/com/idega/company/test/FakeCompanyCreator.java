/**
 * @(#)CompanyFakeDataCreator.java    1.0.0 1:23:47 PM
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
package com.idega.company.test;

import java.rmi.RemoteException;
import java.util.logging.Level;

import javax.ejb.CreateException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.company.companyregister.business.CompanyRegisterBusiness;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyHome;
import com.idega.core.business.DefaultSpringBean;
import com.idega.core.contact.data.Email;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.PhoneTypeBMPBean;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.AddressHome;
import com.idega.core.location.data.Commune;
import com.idega.core.location.data.CommuneHome;
import com.idega.core.location.data.PostalCode;
import com.idega.core.location.data.PostalCodeHome;
import com.idega.data.IDOLookup;
import com.idega.data.IDOLookupException;
import com.idega.data.IDOStoreException;
import com.idega.hibernate.TestConstants;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.CoreUtil;

/**
 * <p>Creates fake data for company.</p>
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Dec 5, 2012
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Service(FakeCompanyCreator.BEAN_IDENTIFIER)
public class FakeCompanyCreator extends DefaultSpringBean {
	public static final String BEAN_IDENTIFIER = "fakeCompanyCreator";
	
	protected UserBusiness userBusiness;
	
	protected UserBusiness getUserBusiness() {
		if (this.userBusiness == null) {
			try {
				this.userBusiness = (UserBusiness) IBOLookup.getServiceInstance(
						CoreUtil.getIWContext(), 
						UserBusiness.class);
			}
			catch (IBOLookupException e) {
				getLogger().log(Level.WARNING, "unable to get user business: ", e);
			}
		}
		
		return this.userBusiness;
	}
	
	protected CompanyRegisterBusiness companyRegisterBusiness;
	
	protected CompanyRegisterBusiness getCompanyRegisterBusiness() {
		if (this.companyRegisterBusiness == null) {
			try {
				this.companyRegisterBusiness = (CompanyRegisterBusiness) IBOLookup
						.getServiceInstance(
								CoreUtil.getIWContext(), 
								CompanyRegisterBusiness.class
						);
			} catch (IBOLookupException ile) {
				getLogger().log(Level.WARNING, "Unable to get: " + 
						CompanyRegisterBusiness.class, ile);
			}
		}
		
		return this.companyRegisterBusiness;
	}

	protected CompanyHome companyHome;
	
	protected CompanyHome getCompanyHome() {
		if (this.companyHome == null) {
			try {
				this.companyHome = (CompanyHome) IDOLookup.getHome(Company.class);
			} catch (IDOLookupException e) {
				getLogger().log(Level.WARNING, "Unable to get " + 
						CompanyHome.class);
			}
		}

		return this.companyHome;
	}
	
	protected AddressHome addressHome;
	
	protected AddressHome getAddressHome() throws RemoteException {
		if (this.addressHome == null) {
			this.addressHome = (AddressHome) IDOLookup.getHome(Address.class);
		}
		
		return this.addressHome;
	}
	
	protected CommuneHome communeHome;
	
	protected CommuneHome getCommuneHome() throws RemoteException {
		if (this.communeHome == null) {
			this.communeHome = (CommuneHome) IDOLookup.getHome(Commune.class);
		}
		
		return this.communeHome;
	}
	
	protected PostalCodeHome postalCodeHome;
	
	protected PostalCodeHome getPostalCodeHome() throws RemoteException {
		if (this.postalCodeHome == null) {
			this.postalCodeHome = (PostalCodeHome) IDOLookup.getHome(PostalCode.class);
		}
		
		return this.postalCodeHome;
	}
	
	public Company create(User user, String commune,
			String postalCode, String address, String bankAccount, Group group,
			java.sql.Date lastChanged,
			String name, Boolean isOpen, String personalID, Boolean isValid,
			String workingArea, String orderAreaForName, String operationForm,
			String vatNumber, String legalAddress, java.sql.Date registrationDate,
			String operation, String recipientPersonalId, String recipientName,
			String industryCode, String unregistrationType,
			java.sql.Date unregistrationDate, String banMarking) {

		Company fakeCompany = null;
		try {
			fakeCompany = getCompanyHome().create();
		} catch (CreateException e) {
			getLogger().log(Level.WARNING, "Unable to create fake: " + 
					Company.class, e);
			
			return null;
		}
		
		fakeCompany.setCEO(user);
		
		if (StringUtil.isEmpty(personalID)) {
			personalID = RandomStringUtils.randomNumeric(10);
		}
		
		fakeCompany.setPersonalID(personalID);
		
		if (StringUtil.isEmpty(address))
			address = RandomStringUtils.random(20, TestConstants.setOfChars);
		
		Address addressBean = fakeCompany.getAddress();
		if(addressBean == null) {
			try {
				addressBean = getAddressHome().create();
				addressBean.setAddressType(getAddressHome().getAddressType1());
				addressBean.setStreetName(address);
//				addressBean.setAddressTypeID(RandomUtils.nextInt(10));
				addressBean.setCity("Vilnius");
				addressBean.store();
			} catch(Exception re) {
				getLogger().log(Level.SEVERE, "Exception while creating a new address entry", re);
				return null;
			}
		}
		
		fakeCompany.setAddress(addressBean);
		
		if (StringUtil.isEmpty(commune))
			commune = RandomStringUtils.random(20, TestConstants.setOfChars);
		
		Commune communeBean = addressBean.getCommune();
		if(communeBean == null) {
			try {
				communeBean = getCommuneHome().create();
				communeBean.setCommuneCode(RandomStringUtils.randomNumeric(10));
				communeBean.setCommuneName(commune);
				communeBean.setCommuneWebsiteURL("http://www."+RandomStringUtils.random(20, TestConstants.setOfChars)+".is");
				communeBean.store();
			} catch(Exception re) {
				getLogger().log(Level.SEVERE, "Exception while finding commune entry", re);
			}
		}
		
		addressBean.setCommune(communeBean);
		
		if (StringUtil.isEmpty(postalCode))
			postalCode = RandomStringUtils.randomNumeric(5);
		
		PostalCode postalCodeBean = addressBean.getPostalCode();
		if(postalCodeBean == null) {
			try {
				postalCodeBean = getPostalCodeHome().create();
				postalCodeBean.setPostalCode(postalCode);
				postalCodeBean.store();
			} catch(Exception re) {
				getLogger().log(Level.SEVERE, "Exception while finding a postal code entry", re);
			}
		}
		
		addressBean.setPostalCode(postalCodeBean);
		addressBean.store();
		
		if (StringUtil.isEmpty(workingArea))
			workingArea = RandomStringUtils.random(20, TestConstants.setOfChars);
		
		fakeCompany.setWorkingArea(communeBean);
		
		if (StringUtil.isEmpty(orderAreaForName))
			orderAreaForName = RandomStringUtils.random(20, TestConstants.setOfChars);
		
		
		
		if (StringUtil.isEmpty(name))
			name = RandomStringUtils.random(20, TestConstants.setOfChars);
		
		fakeCompany.setName(name);
		
		if (StringUtil.isEmpty(personalID)) {
			personalID = RandomStringUtils.randomNumeric(10);
		}
		
		fakeCompany.setPersonalID(personalID);
		
		if (lastChanged == null)
			lastChanged = new java.sql.Date(System.currentTimeMillis());
		
		fakeCompany.setLastChange(lastChanged);
		
		if (StringUtil.isEmpty(operationForm)) 
			operationForm = RandomStringUtils.random(20, TestConstants.setOfChars);
		
		// TODO fakeCompany.setOperationForm(operationForm)
		
		if (StringUtil.isEmpty(vatNumber))
			vatNumber = RandomStringUtils.randomNumeric(10);
		
		fakeCompany.setVATNumber(vatNumber);
		
		if (StringUtil.isEmpty(legalAddress))
			legalAddress = RandomStringUtils.random(20, TestConstants.setOfChars);
		
		// TODO fakeCompany.setLegalCommune(legalCommune)
		
		if (registrationDate == null)
			registrationDate = new java.sql.Date(System.currentTimeMillis() - (RandomUtils.nextInt() % 10000));
		
		fakeCompany.setRegisterDate(registrationDate);
		
		if (StringUtil.isEmpty(operation))
			operation = RandomStringUtils.random(20, TestConstants.setOfChars);
		
		fakeCompany.setOperation(operation);
		
		if (StringUtil.isEmpty(recipientPersonalId))
			recipientPersonalId = RandomStringUtils.randomNumeric(10);
		
		fakeCompany.setRecipientId(recipientPersonalId);
		
		if (StringUtil.isEmpty(recipientName))
			recipientName = RandomStringUtils.random(20, TestConstants.setOfChars);
		
		fakeCompany.setRecipientName(recipientName);
		
		if (StringUtil.isEmpty(industryCode))
			industryCode = RandomStringUtils.randomNumeric(10);
		
		// TODO fakeCompany.setIndustryCode(industryCode)
		
		if (StringUtil.isEmpty(unregistrationType))
			unregistrationType = RandomStringUtils.random(20, TestConstants.setOfChars);
		
		// TODO fakeCompany.setUnregisterType(unregisterType)
		
		if (unregistrationDate == null)
			unregistrationDate = new java.sql.Date(System.currentTimeMillis() - (RandomUtils.nextInt() % 10000));
		
		fakeCompany.setUnregisterDate(unregistrationDate);
		
		if (StringUtil.isEmpty(banMarking))
			banMarking = RandomStringUtils.randomNumeric(10);
		
		fakeCompany.setBanMarking(banMarking);
		
		fakeCompany.setOpen(RandomUtils.nextBoolean());
		
		try {
			fakeCompany.store();
		} catch (IDOStoreException e) {
			getLogger().log(Level.WARNING, "Unable to create fake: " + 
					Company.class, e);
			return null;
		}
		
		try {
			Phone phone = getUserBusiness().getPhoneHome().create();
			phone.setPhoneTypeId(PhoneTypeBMPBean.HOME_PHONE_ID);
			phone.setNumber(RandomStringUtils.randomNumeric(7));
			fakeCompany.updatePhone(phone);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			Phone fax = getUserBusiness().getPhoneHome().create();
			fax.setPhoneTypeId(PhoneTypeBMPBean.FAX_NUMBER_ID);
			fax.setNumber(RandomStringUtils.randomNumeric(7));
			fakeCompany.updateFax(fax);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			Email email = getUserBusiness().getEmailHome().create();
			email.setEmailAddress(RandomStringUtils.random(20, TestConstants.setOfChars)+ "@idega.is");
			fakeCompany.updateEmail(email);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			fakeCompany.store();
		} catch (IDOStoreException e) {
			getLogger().log(Level.WARNING, "Unable to create fake: " + 
					Company.class, e);
			return null;
		}
		
		return fakeCompany;
	}
}
