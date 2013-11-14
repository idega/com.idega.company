package com.idega.company.presentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.idega.company.business.CompanyService;
import com.idega.company.data.Company;
import com.idega.presentation.IWContext;
import com.idega.presentation.filter.FilterList;
import com.idega.presentation.ui.CheckBox;
import com.idega.user.data.User;
import com.idega.util.ListUtil;
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

	@Autowired
	private CompanyService companyService;

	protected CompanyService getCompanyService() {
		if (this.companyService == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.companyService;
	}

	protected List<Company> getCompanies() {
		List<Company> companyList =  getCompanyService().getCompaniesByOwnerRoles(getRoles());
		
		ArrayList<Company> search = new ArrayList<Company>(1);
		search.add(null);
		for(Company company: companyList){
			search.set(0, company);
			List<User> owners = getCompanyService().getOwnersByCompanies(search);
			if(ListUtil.isEmpty(owners)){
				continue;
			}
			company.setName(owners.get(0).getName() + " - " + company.getName());
		}
		
		return companyList;
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
}