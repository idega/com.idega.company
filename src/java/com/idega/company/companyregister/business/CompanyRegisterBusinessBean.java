package com.idega.company.companyregister.business;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.FinderException;

import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOServiceBean;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyHome;
import com.idega.company.data.IndustryCode;
import com.idega.company.data.IndustryCodeHome;
import com.idega.company.data.OperationForm;
import com.idega.company.data.OperationFormHome;
import com.idega.company.data.UnregisterType;
import com.idega.company.data.UnregisterTypeHome;
import com.idega.core.location.business.AddressBusiness;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.AddressHome;
import com.idega.core.location.data.AddressType;
import com.idega.core.location.data.AddressTypeHome;
import com.idega.core.location.data.Commune;
import com.idega.core.location.data.CommuneHome;
import com.idega.core.location.data.PostalCode;
import com.idega.core.location.data.PostalCodeHome;
import com.idega.data.IDOLookup;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.User;
import com.idega.user.data.UserHome;
import com.idega.util.StringUtil;
import com.idega.util.text.Name;

public class CompanyRegisterBusinessBean extends IBOServiceBean implements CompanyRegisterBusiness{

	private static final long serialVersionUID = 1014401288441001288L;
	private static Logger logger = Logger.getLogger(CompanyRegisterBusinessBean.class.getName());

	public static final String RECORDS_DATE_FORMAT = "ddMMyy";

	@Override
	public boolean updateEntry(
			String personal_id,
			String commune,
			String postalCode,
			String workingArea,
			String orderAreaForName,
			String name,
			String address,
			String ceoId,
			String dateOfLastChange,
			String operationForm,
			String vatNumber,
			String legalAddress,
			String registerDate,
			String operation,
			String recipientPersonalId,
			String recipientName,
			String industryCode,
			String unregistrationType,
			String unregistrationDate,
			String banMarking
	) {
		Company company = getEntryByPersonalId(personal_id);
		try {
			if (company == null) {
				CompanyBusiness companyBusiness = getServiceInstance(CompanyBusiness.class);
				company = companyBusiness.storeCompany(name, personal_id);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception while creating company with personal ID " + personal_id + " and name " + name, e);
			return false;
		}

		if (company == null) {
			return false;
		}

		if (company.getPersonalID() == null || company.getPersonalID().equals("")) {
			company.setPersonalID(personal_id);
		}

		if (banMarking != null && !banMarking.trim().equals("")) {
			company.setBanMarking(banMarking.trim());
		}
		if (operation != null && !operation.trim().equals("")) {
			company.setOperation(operation.trim());
		}
		if (orderAreaForName != null && !orderAreaForName.trim().equals("")) {
			company.setOrderAreaForName(orderAreaForName.trim());
		}
		if (vatNumber != null && !vatNumber.trim().equals("")) {
			company.setVATNumber(vatNumber.trim());
		}
		if (name != null && !name.trim().equals("")) {
			company.setName(name.trim());
		}

		UserBusiness userBusiness = getUserBusiness();
		if (userBusiness == null) {
			logger.log(Level.SEVERE, "Could not retrieve UserBusiness service bean");
			return false;
		}

		if (!StringUtil.isEmpty(personal_id) && !StringUtil.isEmpty(name)) {
			User companyUser = null;
			try {
				companyUser = userBusiness.getUser(personal_id);
			} catch (Exception e) {}
			if (companyUser != null) {
				companyUser.setFirstName(name);
				companyUser.setMiddleName(null);
				companyUser.setLastName(null);
				companyUser.setDisplayName(name);
				companyUser.store();

				try {
					userBusiness.updateUsersMainAddressByFullAddressString(companyUser, address);
				} catch (Exception e) {
					getLogger().log(Level.WARNING, "Error updating main address for company user " + companyUser + ": " + address, e);
				}
				try {
					AddressTypeHome addressTypeHome = (AddressTypeHome) IDOLookup.getHome(AddressType.class);
					AddressType workAddressType = addressTypeHome.findAddressType2();
					userBusiness.updateUsersAddressByFullAddressString(companyUser, address, workAddressType);
				} catch (Exception e) {
					getLogger().log(Level.WARNING, "Error updating work address for company user " + companyUser + ": " + address, e);
				}
			}
		}

		User ceo = null;
		try {
			if(ceoId != null) {
				ceoId = ceoId.trim();
				if(!ceoId.equals("")) {
					ceo = userBusiness.getUser(ceoId);
				}
			}
		} catch(FinderException re) {
		} catch(Exception re) {
			logger.log(Level.SEVERE, "Could not find the user by ID " + ceoId + " for company's (" + personal_id + ") CEO", re);
		}
		boolean createNewCeo = false;
		if (ceo != null) {
			if (!ceoId.equals(ceo.getPersonalID())) {
				createNewCeo = true;
			}
		}
		if (ceo == null || createNewCeo) {
			String ceoName = null;
			try {
				ceoId = StringUtil.isEmpty(ceoId) ? personal_id : ceoId;
				ceoName = StringUtil.isEmpty(name) ? ceoId : name.trim();

				Name nameUtil = new Name(ceoName);
				ceo = getUserBusiness().createUser(nameUtil.getFirstName(), nameUtil.getMiddleName(), nameUtil.getLastName(), ceoId);
				if (ceo == null) {
					logger.severe("Failed to create CEO (" + ceoName + ") with personal ID " + ceoId);
					return false;
				}
				company.setCEO(ceo);
			} catch (Exception re) {
				logger.log(Level.SEVERE, "Exception while creating a new user entry, CEO (" + ceoName + ") personal ID: " + ceoId, re);
				return false;
			}
		}

		if (recipientPersonalId != null && !recipientPersonalId.trim().equals("")) {
			company.setRecipientId(recipientPersonalId);
		}
		if (recipientName != null && !recipientName.trim().equals("")) {
			company.setRecipientName(recipientName);
		}

		Address addressBean = company.getAddress();
		if (addressBean == null) {
			try {
				addressBean = getAddressHome().create();
				addressBean.setAddressType(getAddressHome().getAddressType1());
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Exception while creating a new address entry", re);
				return false;
			}
		}

		AddressBusiness addressBusiness = getAddressBusiness();
		if (addressBusiness == null) {
			logger.log(Level.SEVERE, "Could not retrieve AddressBusiness service bean");
			return false;
		}
		try {
			if (address != null) {
				address = address.trim();
				if (!address.equals("")) {
					addressBean.setStreetName(addressBusiness.getStreetNameFromAddressString(address));
					addressBean.setStreetNumber(addressBusiness.getStreetNumberFromAddressString(address));
				}
			}
		} catch(Exception re) {
			logger.log(Level.SEVERE, "Exception while handling company address", re);
		}

		Commune communeBean = addressBean.getCommune();
		if (communeBean == null) {
			try {
				if (commune != null) {
					commune = commune.trim();
					if(!commune.equals("")) {
						communeBean = getCommuneHome().findByCommuneCode(commune);
					}
				}
			} catch(FinderException re) {
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Exception while finding commune entry", re);
			}
		}
		addressBean.setCommune(communeBean);
		company.setLastChange(CompanyRegisterImportFile.getSQLDateFromStringFormat(dateOfLastChange.trim(), logger, RECORDS_DATE_FORMAT));
		if (registerDate != null) {
			company.setRegisterDate(CompanyRegisterImportFile.getSQLDateFromStringFormat(registerDate.trim(), logger, RECORDS_DATE_FORMAT));
		}
		if (unregistrationDate != null) {
			company.setUnregisterDate(CompanyRegisterImportFile.getSQLDateFromStringFormat(unregistrationDate.trim(), logger, RECORDS_DATE_FORMAT));
		}

		PostalCode postalCodeBean = addressBean.getPostalCode();
		if (postalCodeBean == null) {
			try {
				if (postalCode != null) {
					postalCode = postalCode.trim();
					if(!postalCode.equals("")) {
						postalCodeBean = getPostalCodeHome().findByPostalCode(postalCode);
					}
				}
			} catch(FinderException re) {
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Exception while finding a postal code entry", re);
			}
		}
		addressBean.setPostalCode(postalCodeBean);
		addressBean.store();
		try {
			Collection<Address> addreses = company.getGeneralGroup().getAddresses(getAddressHome().getAddressType1());
			String pk1 = ((Integer) addressBean.getPrimaryKey()).toString();
			boolean addressSet = false;
			for (Iterator<Address> it = addreses.iterator(); it.hasNext(); ) {
				Address adr = it.next();
				String pk2 = ((Integer) adr.getPrimaryKey()).toString();
				if (pk1.equals(pk2)) {
					addressSet = true;
					break;
				}
			}
			if (!addressSet) {
				company.setAddress(addressBean);
			}
		} catch(Exception e) {
			logger.log(Level.SEVERE, "Exception while all address entries for a company", e);
		}

		Commune legalCommune = company.getLegalCommune();
		if (legalCommune == null) {
			try {
				if (legalAddress != null) {
					legalAddress = legalAddress.trim();
					if (!legalAddress.equals("")) {
						legalCommune = getCommuneHome().findByCommuneCode(legalAddress);
					}
				}
			} catch(FinderException re) {
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Exception while finding a commune entry", re);
			}
		}
		company.setLegalCommune(legalCommune);

		Commune workCommune = company.getWorkingArea();
		if (workCommune == null) {
			try {
				if (workingArea != null) {
					workingArea = workingArea.trim();
					if (!workingArea.equals("")) {
						workCommune = getCommuneHome().findByCommuneCode(workingArea);
					}
				}
			} catch(FinderException re) {
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Exception while finding a commune entry", re);
			}
		}
		company.setWorkingArea(workCommune);

		try {
			OperationForm operationFormBean = ((OperationFormHome) IDOLookup.getHome(OperationForm.class)).findOperationFormByUniqueCode(operationForm);
			if (operationFormBean != null) {
				company.setOperationForm(operationFormBean);
			}
		} catch(FinderException re) {
		} catch(Exception re) {
			logger.log(Level.SEVERE, "Exception while finding a OperationForm entry", re);
		}

		try {
			IndustryCode industryCodeBean = ((IndustryCodeHome) IDOLookup.getHome(IndustryCode.class)).findIndustryByUniqueCode(industryCode);
			if (industryCodeBean != null) {
				company.setIndustryCode(industryCodeBean);
			}
		} catch(FinderException re) {
		} catch(Exception re) {
			logger.log(Level.SEVERE, "Exception while finding a IndustryCode entry", re);
		}

		try {
			UnregisterType unregisterTypeBean = ((UnregisterTypeHome) IDOLookup.getHome(UnregisterType.class)).findUnregisterTypeByUniqueCode(unregistrationType);

			if (unregisterTypeBean != null) {
				company.setUnregisterType(unregisterTypeBean);
			}
		} catch (FinderException e) {
		} catch(Exception re) {
			logger.log(Level.SEVERE, "Exception while finding a UnregisterType entry", re);
		}
		company.store();

		return true;
	}

	protected CompanyHome getCompanyRegisterHome() throws RemoteException {

		return (CompanyHome) IDOLookup.getHome(Company.class);
	}

	protected UserHome getUserHome() throws RemoteException {
		return (UserHome) IDOLookup.getHome(User.class);
	}

	protected AddressHome getAddressHome() throws RemoteException {
		return (AddressHome) IDOLookup.getHome(Address.class);
	}

	protected CommuneHome getCommuneHome() throws RemoteException {
		return (CommuneHome) IDOLookup.getHome(Commune.class);
	}

	protected PostalCodeHome getPostalCodeHome() throws RemoteException {
		return (PostalCodeHome) IDOLookup.getHome(PostalCode.class);
	}

	@Override
	public Company getEntryByPersonalId(String personal_id) {

		try {

			return getCompanyRegisterHome().findByPersonalID(personal_id);

		} catch (FinderException e) {
			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception while retrieving company BMP bean by personal id", e);
			return null;
		}
	}

	protected UserBusiness getUserBusiness() {
		try {
			return getServiceInstance(UserBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	protected AddressBusiness getAddressBusiness() {
		try {
			return getServiceInstance(AddressBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}
