package com.idega.company.data.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = IndustryCode.TABLE_NAME,
		uniqueConstraints = {@UniqueConstraint(columnNames={IndustryCode.CODE})}
)
public class IndustryCode implements Serializable{

	private static final long serialVersionUID = -1210312225376487940L;

	public static final String TABLE_NAME = "com_industry_code_isat";
	
	public static final String CODE = "code";

	public static final String DESCRIPTION = "description";
	
	public static final String idProp = TABLE_NAME + "_" + "id";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = TABLE_NAME + "_" + "ID")
	private Long id;
	 
	public static final String codeProp = TABLE_NAME + "_" + CODE;
	@Column(name = CODE)
	private String ISATCode;
	
	
	public static final String descriptionProp = TABLE_NAME + "_" + CODE;
	@Column(name = DESCRIPTION)
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getISATCode() {
		return ISATCode;
	}
	public void setISATCode(String iSATCode) {
		ISATCode = iSATCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
