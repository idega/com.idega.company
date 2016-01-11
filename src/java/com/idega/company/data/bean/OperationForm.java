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
@Table(name = OperationForm.TABLE_NAME,
		uniqueConstraints = {@UniqueConstraint(columnNames={OperationForm.CODE})}
)
public class OperationForm implements Serializable{
	private static final long serialVersionUID = 7413821173371518884L;
	public static final String TABLE_NAME = "com_operation_form";
	public static final String CODE = "code";
	public static final String DESCRIPTION = "description";
	
	public static final String idProp = TABLE_NAME + "_" + "id";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = TABLE_NAME + "_" + "ID")
	private Long id;
	 
	public static final String codeProp = TABLE_NAME + "_" + CODE;
	@Column(name = CODE)
	private String code;
	
	
	public static final String descriptionProp = TABLE_NAME + "_" + CODE;
	@Column(name = DESCRIPTION)
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}