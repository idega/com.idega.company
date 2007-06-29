package com.idega.company.companyregister.business;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.FinderException;

import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.business.IBOServiceBean;
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
import com.idega.core.location.data.Commune;
import com.idega.core.location.data.CommuneHome;
import com.idega.core.location.data.PostalCode;
import com.idega.core.location.data.PostalCodeHome;
import com.idega.data.IDOLookup;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.User;
import com.idega.user.data.UserHome;

public class CompanyRegisterBusinessBean extends IBOServiceBean implements CompanyRegisterBusiness{

	private static final long serialVersionUID = 1014401288441001288L;
	private static Logger logger = Logger.getLogger(CompanyRegisterBusinessBean.class.getName());
	
	public static final String RECORDS_DATE_FORMAT = "ddMMyy";
	
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
			String banMarking) {
		
			Company company_registry = getEntryByPersonalId(personal_id);
				
			try {
				if (company_registry == null) {
					company_registry = getCompanyRegisterHome().create();
				}
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Exception while creating company register entry", re);
				return false;
			}
				
			if(banMarking != null && !banMarking.trim().equals("")) {
				company_registry.setBanMarking(banMarking.trim());
			}
			if(operation != null && !operation.trim().equals("")) {
				company_registry.setOperation(operation.trim());
			}
			if(orderAreaForName != null && !orderAreaForName.trim().equals("")) {
				company_registry.setOrderAreaForName(orderAreaForName.trim());
			}
			if(vatNumber != null && !vatNumber.trim().equals("")) {
				company_registry.setVATNumber(vatNumber.trim());
			}
			if(name != null && !name.trim().equals("")) {
				company_registry.setName(name.trim());
			}
					
			UserBusiness userBusiness = getUserBusiness();
			if(userBusiness == null) {
				logger.log(Level.SEVERE, "Could not retrieve UserBusiness service bean");
				return false;
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
				logger.log(Level.SEVERE, "Could not find the user", re);
			}
			if(ceo == null) {
				try {
					ceo = getUserHome().create();
				} catch(Exception re) {
					logger.log(Level.SEVERE, "Exception while creating a new user entry", re);
					return false;
				}
			}
			company_registry.setCEO(ceo);
			
			User recipient = null;
			try {
				if(recipientPersonalId != null) {
					recipientPersonalId = recipientPersonalId.trim();
					if(!recipientPersonalId.equals("")) {
						recipient = userBusiness.getUser(recipientPersonalId);
					}
				}
			} catch(FinderException re) {
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Could not find the user", re);
			}
			if(recipient == null) {
				try {
					recipient = getUserHome().create();
				} catch(Exception re) {
					logger.log(Level.SEVERE, "Exception while creating a new user entry", re);
					return false;
				}
			}
			recipient.setName(recipientName);
			company_registry.setRecipient(recipient);			
					

			Address addressBean = company_registry.getAddress();
			if(addressBean == null) {
				try {
					addressBean = getAddressHome().create();
				} catch(Exception re) {
					logger.log(Level.SEVERE, "Exception while creating a new address entry", re);
					return false;
				}
			}
					
			AddressBusiness addressBusiness = getAddressBusiness();
			if(addressBusiness == null) {
				logger.log(Level.SEVERE, "Could not retrieve AddressBusiness service bean");
				return false;
			}
			try {
				if(address != null) {
					address = address.trim();
					if(!address.equals("")) {
						addressBean.setStreetName(addressBusiness.getStreetNameFromAddressString(address));
						addressBean.setStreetNumber(addressBusiness.getStreetNumberFromAddressString(address));
					}
				}
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Exception while handling company address", re);
			}
			
			Commune communeBean = addressBean.getCommune();
			if(communeBean == null) {
				try {
					if(commune != null) {
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
			//communeBean.setCommuneCode(commune);
			addressBean.setCommune(communeBean);
			company_registry.setLastChange(CompanyRegisterImportFile.getSQLDateFromStringFormat(dateOfLastChange.trim(), logger, RECORDS_DATE_FORMAT));
			company_registry.setRegisterDate(CompanyRegisterImportFile.getSQLDateFromStringFormat(registerDate.trim(), logger, RECORDS_DATE_FORMAT));
			company_registry.setUnregisterDate(CompanyRegisterImportFile.getSQLDateFromStringFormat(unregistrationDate.trim(), logger, RECORDS_DATE_FORMAT));
					
			PostalCode postalCodeBean = addressBean.getPostalCode();
			if(postalCode == null) {
				try {
					if(postalCode != null) {
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
//			postalCodeBean.setPostalCode(postalCode);
			addressBean.setPostalCode(postalCodeBean);
//			addressBean.setP
			addressBean.store();
			company_registry.setAddress(addressBean);

			Commune legalCommune = company_registry.getLegalCommune();
			if(legalCommune == null) {
				try {
					if(legalAddress != null) {
						legalAddress = legalAddress.trim();
						if(!legalAddress.equals("")) {
							legalCommune = getCommuneHome().findByCommuneCode(legalAddress);
						}
					}
				} catch(FinderException re) {
				} catch(Exception re) {
					logger.log(Level.SEVERE, "Exception while finding a commune entry", re);
				}
			}
//			legalCommune.setCommuneCode(legalAddress);
			company_registry.setLegalCommune(legalCommune);
			
			Commune workCommune = company_registry.getWorkingArea();
			if(workCommune == null) {
				try {
					if(workingArea != null) {
						workingArea = workingArea.trim();
						if(!workingArea.equals("")) {
							workCommune = getCommuneHome().findByCommuneCode(workingArea);
						}
					}
				} catch(FinderException re) {
				} catch(Exception re) {
					logger.log(Level.SEVERE, "Exception while finding a commune entry", re);
				}
			}
//			legalCommune.setCommuneCode(legalAddress);
			company_registry.setWorkingArea(workCommune);
					
			try {
				OperationForm operationFormBean = ((OperationFormHome) IDOLookup.getHome(OperationForm.class)).findOperationFormByUniqueCode(operationForm);
				if(operationFormBean != null) {
					company_registry.setOperationForm(operationFormBean);
				}
			} catch(FinderException re) {
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Exception while finding a OperationForm entry", re);
			}
				
			try {
				IndustryCode industryCodeBean = ((IndustryCodeHome) IDOLookup.getHome(IndustryCode.class)).findByPrimaryKey(industryCode);
				if(industryCodeBean != null) {
					company_registry.setIndustryCode(industryCodeBean);
				}
			} catch(FinderException re) {
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Exception while finding a IndustryCode entry", re);
			}
			
			try {
				
				UnregisterType unregisterTypeBean = ((UnregisterTypeHome) IDOLookup.getHome(UnregisterType.class)).findUnregisterTypeByUniqueCode(unregistrationType);
				
				if(unregisterTypeBean != null) {
					company_registry.setUnregisterType(unregisterTypeBean);
				}
			} catch (FinderException e) {
			} catch(Exception re) {
				logger.log(Level.SEVERE, "Exception while finding a UnregisterType entry", re);
			}
			company_registry.store();

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
			return (UserBusiness) getServiceInstance(UserBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
	
	protected AddressBusiness getAddressBusiness() {
		try {
			return (AddressBusiness) getServiceInstance(AddressBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}
