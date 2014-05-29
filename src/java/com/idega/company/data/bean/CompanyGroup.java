package com.idega.company.data.bean;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.idega.company.CompanyConstants;
import com.idega.user.data.bean.Group;

@Entity
@DiscriminatorValue(CompanyConstants.GROUP_TYPE_COMPANY)
public class CompanyGroup extends Group {

	private static final long serialVersionUID = -327378673042228102L;

}