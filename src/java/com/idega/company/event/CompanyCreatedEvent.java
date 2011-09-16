package com.idega.company.event;

import com.idega.company.data.Company;
import com.idega.event.UserCreatedEvent;
import com.idega.user.data.User;

public class CompanyCreatedEvent extends UserCreatedEvent {

	private static final long serialVersionUID = -7439546891158797572L;

	private Company company;
	
	public CompanyCreatedEvent(Object source, User user, Company company) {
		super(source, user);
		
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}
}