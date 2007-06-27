package com.idega.company.companyregister.business;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
				
			try {
				
					Company company_registry = getEntryByPersonalId(personal_id);
					
					if (company_registry == null)
						company_registry = getCompanyRegisterHome().create();
					
					company_registry.setBanMarking(banMarking);
					UserBusiness userBusiness = (UserBusiness) getServiceInstance(UserBusiness.class);
					if(userBusiness != null) {
						User ceo = userBusiness.getUser(ceoId);
						if(ceo == null) {
							ceo = getUserHome().create();
						}
						company_registry.setCEO(ceo);
						User recipient = userBusiness.getUser(recipientPersonalId);
						if(recipient == null) {
							recipient = getUserHome().create();
						}
						recipient.setName(recipientName);
						company_registry.setRecipient(recipient);
					}
					company_registry.setOperation(operation);
					company_registry.setOrderAreaForName(orderAreaForName);
					company_registry.setVATNumber(vatNumber);
					company_registry.setName(name);

					Address addressBean = company_registry.getAddress();
					if(addressBean == null) {
						addressBean = getAddressHome().create();
					}
					
					AddressBusiness address_business = (AddressBusiness) getServiceInstance(AddressBusiness.class);
					
					addressBean.setStreetName(address_business.getStreetNameFromAddressString(address));
					addressBean.setStreetNumber(address_business.getStreetNumberFromAddressString(address));
					
					Commune communeBean = addressBean.getCommune();
					if(communeBean == null) {
						communeBean = getCommuneHome().create();
					}
					communeBean.setCommuneCode(commune);
					addressBean.setCommune(communeBean);
					
					PostalCode postalCodeBean = addressBean.getPostalCode();
					if(postalCode == null) {
						postalCodeBean = getPostalCodeHome().create();
					}
					postalCodeBean.setPostalCode(postalCode);
					addressBean.setPostalCode(postalCodeBean);
					company_registry.setAddress(addressBean);

					Commune legalCommune = company_registry.getLegalCommune();
					if(legalCommune == null) {
						legalCommune = getCommuneHome().create();
					}
					legalCommune.setCommuneCode(legalAddress);
					company_registry.setLegalCommune(legalCommune);
					
					Commune workCommune = company_registry.getWorkingArea();
					if(workCommune == null) {
						workCommune = getCommuneHome().create();
					}
					workCommune.setCommuneCode(workingArea);
					company_registry.setWorkingArea(workCommune);
					
					OperationForm operationFormBean = ((OperationFormHome) IDOLookup.getHome(OperationForm.class)).findOperationFormByUniqueCode(operationForm);
					if(operationFormBean != null) {
						company_registry.setOperationForm(operationFormBean);
					}
					
					IndustryCode industryCodeBean = ((IndustryCodeHome) IDOLookup.getHome(IndustryCode.class)).findByPrimaryKey(industryCode);
					if(industryCodeBean != null) {
						company_registry.setIndustryCode(industryCodeBean);
					}
					
					UnregisterType unregisterTypeBean = ((UnregisterTypeHome) IDOLookup.getHome(UnregisterType.class)).findUnregisterTypeByUniqueCode(unregistrationType);
					if(unregisterTypeBean != null) {
						company_registry.setUnregisterType(unregisterTypeBean);
					}
					
					company_registry.setLastChange(getSqlDateFromTimestampString(dateOfLastChange));
					company_registry.setRegisterDate(getSqlDateFromTimestampString(registerDate));
					company_registry.setUnregisterDate(getSqlDateFromTimestampString(unregistrationDate));
				
					company_registry.store();
			}
			catch (Exception e) {
				
				logger.log(Level.SEVERE, "Exception while creating/updating company register entry", e);
				return false;
			}

			return true;
		}
	
	protected CompanyHome getCompanyRegisterHome() throws RemoteException {
		
		return (CompanyHome) IDOLookup.getHome(Company.class);
	}
	
	public Date getSqlDateFromTimestampString(String timestamp_str) {
		
		try {
			
			return new Date(Long.parseLong(timestamp_str));
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception while parsing long string and creating sql date object", e);
			return null;
		}
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
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Exception while retrieving company BMP bean by personal id", e);
			return null;
		}
	}
}
