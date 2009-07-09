package com.idega.company.presentation;

import is.idega.idegaweb.egov.citizen.presentation.CitizenAccountPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.idega.block.web2.business.JQuery;
import com.idega.block.web2.business.Web2Business;
import com.idega.business.IBOLookup;
import com.idega.company.CompanyConstants;
import com.idega.company.business.CompanyBusiness;
import com.idega.company.business.CompanyProvider;
import com.idega.company.data.Company;
import com.idega.company.data.CompanyHome;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.AddressHome;
import com.idega.core.location.data.PostalCode;
import com.idega.core.location.data.PostalCodeHome;
import com.idega.data.IDOLookup;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.TextInput;
import com.idega.user.data.MetadataConstants;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;
import com.idega.util.PresentationUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

public class EmployeeAccountPreferences extends CitizenAccountPreferences {
	
	private static final String PARAMETER_NAME = "cmp_name";
	private static final String PARAMETER_SSN = "cmp_ssn";
	private static final String PARAMETER_ADDRESS = "cmp_address";
	private static final String PARAMETER_POSTAL_CODE = "cmp_postal_code";
	
	@Autowired
	private JQuery jQuery;
	
	@Autowired
	private Web2Business web2;
	
	@Override
	public void main(IWContext iwc) {
		ELUtil.getInstance().autowire(this);
		
		PresentationUtil.addJavaScriptSourcesLinesToHeader(iwc, Arrays.asList(
				CoreConstants.DWR_UTIL_SCRIPT,
				CoreConstants.DWR_ENGINE_SCRIPT,
				"/dwr/interface/" + CompanyProvider.DWR_OBJECT + ".js",
				getBundle(iwc).getVirtualPathWithFileNameString("javascript/CompanyProviderHelper.js"),
				jQuery.getBundleURIToJQueryLib(),
				web2.getBundleUriToHumanizedMessagesScript()
		));
		PresentationUtil.addStyleSheetToHeader(iwc, web2.getBundleUriToHumanizedMessagesStyleSheet());
		
		super.main(iwc);
	}
	
	@Override
	protected void viewForm(IWContext iwc) throws Exception {
		IWResourceBundle iwrb = getResourceBundle(iwc);
		
		Form form = getPreferencesForm(iwc);
		Layer content = (Layer) form.getChildren().get(form.getChildren().size() - 1);
		Layer section = (Layer) content.getChild(0);
		
		section.add(getHeader(iwrb.getLocalizedString("company_preferences", "Company information")));
		
		Company company = getUserCompany();
		
		TextInput name = new TextInput(PARAMETER_NAME, company == null ? null : company.getName());
		createFormItem(iwrb.getLocalizedString("company_name", "Name"), name, section);
		
		TextInput ssn = new TextInput(PARAMETER_SSN, company == null ? null : company.getPersonalID());
		createFormItem(iwrb.getLocalizedString("company_ssn", "SSN"), ssn, section);
		
		TextInput addressInput = new TextInput(PARAMETER_ADDRESS);
		createFormItem(iwrb.getLocalizedString("company_address", "Address"), addressInput, section);
		
		TextInput postalCodeInput = new TextInput(PARAMETER_POSTAL_CODE);
		createFormItem(iwrb.getLocalizedString("company_postal_code", "Postal code"), postalCodeInput, section);
		
		String mainParameters = new StringBuilder("nameId: '").append(name.getId()).append("', ssnId: '").append(ssn.getId()).append("', addressId: '")
			.append(addressInput.getId()).append("', postalCodeId: '").append(postalCodeInput.getId()).append("', loadingMessage: '")
			.append(iwrb.getLocalizedString("loading", "Loading...")).append("', failureMessage: '")
			.append(iwrb.getLocalizedString("company_not_found", "No company found")).append("'").toString();
		name.setOnKeyUp(new StringBuilder("CompanyProviderHelper.getCompanyByName({id: '").append(name.getId()).append("', ").append(mainParameters).append("});")
				.toString());
		ssn.setOnKeyUp(new StringBuilder("CompanyProviderHelper.getCompanyByPersonalId({id: '").append(ssn.getId()).append("', ").append(mainParameters)
				.append("});").toString());
		
		if (company != null) {
			Address address = company.getAddress();
			if (address != null) {
				addressInput.setContent(address.getStreetName());
				
				PostalCode postalCode = address.getPostalCode();
				if (postalCode != null) {
					postalCodeInput.setContent(postalCode.getPostalCode());
				}
			}
		}
		
		add(form);
	}
	
	private Company getUserCompany() throws Exception {
		User user = getUser();
		if (user == null) {
			return null;
		}
		
		String companyId = user.getMetaData(MetadataConstants.USER_REAL_COMPANY_META_DATA_KEY);
		if (StringUtil.isEmpty(companyId)) {
			return null;
		}
		
		CompanyHome companyHome = (CompanyHome) IDOLookup.getHome(Company.class);
		return companyHome.findByPrimaryKey(companyId);
	}
	
	@Override
	protected boolean updatePreferences(IWContext iwc) throws Exception {
		if (super.updatePreferences(iwc)) {
			IWResourceBundle iwrb = getResourceBundle(iwc);
			Collection<String> errors = new ArrayList<String>();
			
			String name = iwc.getParameter(PARAMETER_NAME);
			String ssn = iwc.getParameter(PARAMETER_SSN);
			String address = iwc.getParameter(PARAMETER_ADDRESS);
			String postalCode = iwc.getParameter(PARAMETER_POSTAL_CODE);
			
			Company company = getCompany(name, ssn);
			if (company == null) {
				if (!removeCompany()) {
					errors.add(iwrb.getLocalizedString("error_removing_company_for_user", "Error occurred removing employee from a company"));
				}
			} else {
				//	Name
				try {	
					if (!StringUtil.isEmpty(name) && !name.equals(company.getName())) {
						company.setName(name);
						company.store();
					}
				} catch(Exception e) {
					e.printStackTrace();
					errors.add(iwrb.getLocalizedString("error_changing_name_for_company", "Error occurred setting new name for company"));
				}
				
				//	Address
				Address coAddress = null;
				if (!StringUtil.isEmpty(address)) {
					coAddress = getAddressAndCreateIfDoNotExist(company, address);
					if (coAddress == null) {
						errors.add(iwrb.getLocalizedString("error_creating_address", "Error occurred creating address for a company"));
					} else if (!address.equals(coAddress.getStreetName())) {
						try {
							coAddress.setStreetName(address);
							coAddress.store();
						} catch(Exception e) {
							e.printStackTrace();
							errors.add(iwrb.getLocalizedString("error_setting_address", "Error occurred setting address for a company"));
						}
					}
				}
				
				//	Postal code
				if (!StringUtil.isEmpty(postalCode)) {
					if (coAddress == null) {
						coAddress = getAddressAndCreateIfDoNotExist(company, null);
					}
					if (coAddress == null) {
						errors.add(iwrb.getLocalizedString("error_creating_address", "Error occurred creating address for a company"));
					} else {
						PostalCode coPostalCode = coAddress.getPostalCode();
						if (coPostalCode == null) {
							try {
								PostalCodeHome postalCodeHome = (PostalCodeHome) IDOLookup.getHome(PostalCode.class);
								try {
									coPostalCode = postalCodeHome.findByPostalCode(postalCode);
								} catch(Exception e) {}
								if (coPostalCode == null) {
									coPostalCode = postalCodeHome.create();
									coPostalCode.setPostalCode(postalCode);
									coPostalCode.store();
								}
								coAddress.setPostalCode(coPostalCode);
								coAddress.store();
							} catch(Exception e) {
								e.printStackTrace();
								errors.add(iwrb.getLocalizedString("error_creating_postal_code", "Error occurred creating postal code for a company"));
							}
						} else if (!postalCode.equals(coPostalCode.getPostalCode())) {
							try {
								coPostalCode.setPostalCode(postalCode);
								coPostalCode.store();
							} catch(Exception e) {
								e.printStackTrace();
								errors.add(iwrb.getLocalizedString("error_setting_postal_code", "Error occurred setting postal code for a company"));
							}
						}
					}
				}
				
				//	Adding company
				try {
					User user = getUser();
					user.setMetaData(MetadataConstants.USER_REAL_COMPANY_META_DATA_KEY, company.getPrimaryKey().toString());
					user.store();
				} catch(Exception e) {
					e.printStackTrace();
					errors.add(iwrb.getLocalizedString("unable_set_company_for_user", "Error occurred adding employee to a company"));
				}
			}
			
			if (ListUtil.isEmpty(errors)) {
				return true;
			} else {
				showErrors(iwc, errors);
				viewForm(iwc);
			}
		}
		
		return false;
	}
	
	private Address getAddressAndCreateIfDoNotExist(Company company, String address) {
		Address coAddress = company.getAddress();
		if (coAddress != null) {
			return coAddress;
		}
		
		try {
			AddressHome addressHome = (AddressHome) IDOLookup.getHome(Address.class);
			coAddress = addressHome.create();
			coAddress.setAddressType(addressHome.getAddressType1());
			if (address != null) {
				coAddress.setStreetName(address);
			}
			coAddress.store();
			company.setAddress(coAddress);
			company.store();
			
			return coAddress;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private boolean removeCompany() {
		User user = getUser();
		if (user == null) {
			return false;
		}
		
		try {
			user.removeMetaData(MetadataConstants.USER_REAL_COMPANY_META_DATA_KEY);
			user.store();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private Company getCompany(String name, String personalId) throws Exception {
		if (StringUtil.isEmpty(name) && StringUtil.isEmpty(personalId)) {
			return null;
		}
		
		Company company = null;
		CompanyBusiness companyBusiness = IBOLookup.getServiceInstance(getIWApplicationContext(), CompanyBusiness.class);
		if (!StringUtil.isEmpty(personalId)) {
			company = companyBusiness.getCompany(personalId);
		}
		if (company == null && !StringUtil.isEmpty(name)) {
			company = companyBusiness.getCompanyByName(name);
		}
		
		return company;
	}
	
	@Override
	public String getBundleIdentifier() {
		return CompanyConstants.IW_BUNDLE_IDENTIFIER;
	}
}
