package com.idega.company.data.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = CompanyType.TABLE_NAME)
public class CompanyType implements Serializable {

	private static final long serialVersionUID = 2992924476808368833L;

	public static final String COLUMN_TYPE = "company_type";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_LOCALIZED_KEY = "localized_key";
	public static final String COLUMN_ORDER = "type_order";

	public static final String TABLE_NAME="com_company_type";
	public static final String idProp = TABLE_NAME + "_" + "id";

	@Id
    @Column(name = COLUMN_TYPE)
    private String type;

	@Column(name = COLUMN_NAME)
    private String name;

	@Column(name = COLUMN_DESCRIPTION)
    private String description;

	@Column(name = COLUMN_LOCALIZED_KEY)
    private String localizedKey;

	@Column(name = COLUMN_ORDER)
    private Integer order;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocalizedKey() {
		return localizedKey;
	}

	public void setLocalizedKey(String localizedKey) {
		this.localizedKey = localizedKey;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
