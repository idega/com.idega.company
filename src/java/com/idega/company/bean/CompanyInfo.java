package com.idega.company.bean;

import java.io.Serializable;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.dwr.business.DWRAnnotationPersistance;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@DataTransferObject
public class CompanyInfo implements Serializable, DWRAnnotationPersistance {

	private static final long serialVersionUID = -6096607835856030348L;

	@RemoteProperty
	private String name;
	@RemoteProperty
	private String personalId;
	@RemoteProperty
	private String address;
	@RemoteProperty
	private String postalCode;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	
}
