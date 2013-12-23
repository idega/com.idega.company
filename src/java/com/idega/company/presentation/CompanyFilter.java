package com.idega.company.presentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponentBase;

import org.springframework.beans.factory.annotation.Autowired;

import com.idega.company.business.CompanyService;
import com.idega.company.data.Company;
import com.idega.core.contact.data.Email;
import com.idega.presentation.IWContext;
import com.idega.presentation.PresentationObjectContainer;
import com.idega.presentation.filter.FilterList;
import com.idega.presentation.ui.CheckBox;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

/**
 *
 * <p>Presentation component, which shows {@link CheckBox} list
 * of companies.</p>
 * <p>You can report about problems to:
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Mar 5, 2013
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public class CompanyFilter extends FilterList<Company> {

	private List<String> selectedUsers = null;

	private List<String> roles = null;

	private String selectedUsersInputName = null;
	
	private HashMap<Company, User> companyUsers = null;
	
	private Map<Company,String> representationMap = null;

	@Autowired
	private CompanyService companyService;

	protected CompanyService getCompanyService() {
		if (this.companyService == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.companyService;
	}

	protected List<Company> getCompanies() {
		return getCompanyService().getCompaniesByOwnerRoles(getRoles());
	}

	protected List<String> getCompaniesIDs() {
		Collection<Company> companies = getCompanies();
		if (ListUtil.isEmpty(companies)) {
			return getCompanyService().getIDsOfCompanies(
					getCompanyService().getCompaniesByUserIDs(getSelectedUsers())
			);
		}

		return getCompanyService().getIDsOfCompanies(companies);
	}

	protected List<Company> getSelectedCompanies() {
		return getCompanyService().getCompaniesByUserIDs(getSelectedUsers());
	}

	protected List<String> getSelectedCompaniesIDs() {
		Collection<Company> companies = getSelectedCompanies();
		if (companies == null) {
			return null;
		}

		return getCompanyService().getIDsOfCompanies(companies);
	}

	private User getCompanyUser(Company company){
		HashMap<Company, User> companyUsers = getCompanyUsers();
		User user = companyUsers.get(company);
		if(user != null){
			return user;
		}
		ArrayList<Company> search = new ArrayList<Company>(1);
		search.add(0, company);
		List<User> owners = getCompanyService().getOwnersByCompanies(search);
		if(ListUtil.isEmpty(owners)){
			return null;
		}
		user = owners.get(0);
		companyUsers.put(company, user);
		return user;
	}
	@Override
	protected Collection<UIComponentBase> getEntityFields(Company company){
		ArrayList<UIComponentBase> components = new ArrayList<UIComponentBase>();
		
		PresentationObjectContainer userSpan = getCell();
		components.add(userSpan);
		userSpan.setStyleClass("company-span");
		
		PresentationObjectContainer emailSpan = getCell();
		components.add(emailSpan);
		emailSpan.setStyleClass("company-span");
		
		PresentationObjectContainer companySpan = getCell();
		components.add(companySpan);
		companySpan.setStyleClass("company-span");
		companySpan.add(company.getName());
		
		User owner = getCompanyUser(company);
		if(owner == null){
			return components;
		}
		
		userSpan.add(owner.getName());
		
		Collection<Email> emails = owner.getEmails();
		String userEmails;
		if(ListUtil.isEmpty(emails)){
			userEmails = CoreConstants.EMPTY;
		}else{
			StringBuilder builder = new StringBuilder();
			Iterator<Email> iter = emails.iterator();
			while(iter.hasNext()){
				Email email = iter.next();
				String addresss = email.getEmailAddress();
				if(StringUtil.isEmpty(addresss)){
					continue;
				}
				builder.append(addresss);
				if(iter.hasNext()){
					builder.append(", ");
				}
			}
			userEmails = builder.toString();
		}
//		UserApplicationEngine userApplicationEngine = ELUtil.getInstance().getBean(UserApplicationEngine.SPRING_BEAN_IDENTIFIER);
		emailSpan.add(userEmails);
		
//		emailSpan.add(userApplicationEngine.getUserInfo(owner).getEmail());
		
		return components;
	}
	
	@Override
	protected String getRepresentation(Company entity){
		Map<Company, String> representationMap = getRepresentationMap();
		String representation = representationMap.get(entity);
		if(representation != null){
			return representation;
		}
		User user = getCompanyUser(entity);
		Collection<Email> emails = user.getEmails();
		String userEmails;
		if(ListUtil.isEmpty(emails)){
			userEmails = CoreConstants.EMPTY;
		}else{
			StringBuilder builder = new StringBuilder();
			Iterator<Email> iter = emails.iterator();
			while(iter.hasNext()){
				Email email = iter.next();
				String addresss = email.getEmailAddress();
				if(StringUtil.isEmpty(addresss)){
					continue;
				}
				builder.append(addresss);
				if(iter.hasNext()){
					builder.append(", ");
				}
			}
			userEmails = builder.toString();
		}
		representation =  user.getName() + CoreConstants.SPACE + userEmails + CoreConstants.SPACE + entity.getName();
		representationMap.put(entity, representation);
		return representation;
	}
	
	@Override
	public void main(IWContext iwc) throws Exception {
		setEntities(getCompanies());
		setSelectedEntities(getSelectedCompanies());
		setParameterName(getSelectedUserInputName());
		super.main(iwc);
	}

	public void setSelectedUsers(List<String> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public List<String> getSelectedUsers() {
		return selectedUsers;
	}

	public void setRoles(List<String> asList) {
		this.roles = asList;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setSelectedUserInputName(String selectedUsersInputName) {
		this.selectedUsersInputName = selectedUsersInputName;
	}

	public String getSelectedUserInputName() {
		return this.selectedUsersInputName;
	}

	@Override
	public Class<Company> getRepresentableClass() {
		return Company.class;
	}

	@Override
	public String getRepresentationMethodName() {
		return "getName";
	}

	@Override
	public void handleKeepStatus(IWContext iwc) {}

	@Override
	public boolean isContainer() {
		return false;
	}

	public HashMap<Company, User> getCompanyUsers() {
		if(companyUsers == null){
			companyUsers = new HashMap<Company, User>();
		}
		return companyUsers;
	}

	public Map<Company, String> getRepresentationMap() {
		if(representationMap == null){
			representationMap = new HashMap<Company, String>();
		}
		return representationMap;
	}

}