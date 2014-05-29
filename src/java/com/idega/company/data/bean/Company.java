package com.idega.company.data.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.idega.company.CompanyConstants;
import com.idega.core.location.data.bean.Commune;
import com.idega.user.data.bean.Group;
import com.idega.user.data.bean.User;

@DiscriminatorValue(CompanyConstants.GROUP_TYPE_COMPANY)
@Entity
@Table(name = Company.TABLE_NAME,
		uniqueConstraints = {@UniqueConstraint(columnNames={Company.COLUMN_PERSONAL_ID})}
)
public class Company extends Group{

	private static final long serialVersionUID = -3326560174514011692L;
	public static final String TABLE_NAME = "ic_company";

	public static final String COLUMN_TYPE = "company_type";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PERSONAL_ID = "personal_id";
	public static final String COLUMN_WEB_PAGE = "web_page";
	public static final String COLUMN_BANK_ACCOUNT = "bank_account";
	public static final String COLUMN_IS_VALID = "is_valid";
	public static final String COLUMN_IS_OPEN = "is_open";
	public static final String COLUMN_EXTRA_INFO = "extra_info";
	public static final String VAT_NUMBER = "vat_number";
	public static final String CEO_ID = "ceo_id";
	public static final String WORKING_AREA = "working_area";
	public static final String ORDER_AREA_FOR_NAME = "order_area_for_name";
	public static final String LAST_CHANGE = "last_change";
	public static final String OPERATION_FORM = "operation_form";
	public static final String LEGAL_COMMUNE = "legal_commune";
	public static final String REGISTER_DATE = "register_date";
	public static final String RECIPIENT_ID = "recipient_id";
	public static final String RECIPIENT_NAME = "recipient_name";
	public static final String OPERATION = "operation";
	public static final String INDUSTRY_CODE = "industry_code";
	public static final String UNREGISTER_TYPE = "unregister_type";
	public static final String UNREGISTER_DATE = "unregister_date";
	public static final String BAN_MARKING = "ban_marking";
	
	
    public static final String nameProp = TABLE_NAME + "_" + COLUMN_NAME;
    @Column(name = COLUMN_NAME,insertable =  false, updatable = false)
    private String name;
    
    public static final String personalIdProp = TABLE_NAME + "_" + COLUMN_PERSONAL_ID;
    @Column(name = COLUMN_PERSONAL_ID)
    private String personalId;
    
    public static final String webPageProp = TABLE_NAME + "_" + COLUMN_WEB_PAGE;
    @Column(name = COLUMN_WEB_PAGE)
    private String webPage;
    
    public static final String bankAccountProp = TABLE_NAME + "_" + COLUMN_BANK_ACCOUNT;
    @Column(name = COLUMN_BANK_ACCOUNT)
    private String bankAccount;
    
    @Column(name = COLUMN_IS_VALID)
    @Type(type="yes_no")
	private Boolean isValid;
    
    @Column(name = COLUMN_IS_OPEN)
    @Type(type="yes_no")
	private Boolean isOpen;
    
    @Column(name = COLUMN_EXTRA_INFO,insertable =  false, updatable = false)
    private String extraInfo;
    
    @Column(name = VAT_NUMBER)
    private String vatNumber;
    
    @Column(name = OPERATION)
    private String operation;
    
    @Column(name = UNREGISTER_DATE)
    private Date unregisterDate;
    
    @Column(name = ORDER_AREA_FOR_NAME)
    private String orderAreaForName;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = LAST_CHANGE)
    private Date lastChange;
    
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;
    
    @Column(name = REGISTER_DATE)
    private Date registerDate;
    
    @Column(name = BAN_MARKING)
    private String banMarking;
    
    @Column(name = RECIPIENT_NAME)
    private String recipientName;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = LEGAL_COMMUNE)
    private Commune legalCommune;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = OPERATION_FORM)
    private OperationForm operationForm;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = CEO_ID)
    private User CEO;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = WORKING_AREA)
    private Commune workingArea;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = INDUSTRY_CODE)
    private IndustryCode industryCode;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = UNREGISTER_TYPE)
    private UnregisterType unregisterType;
    

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public boolean isValid() {
		return isValid == null ? Boolean.FALSE : isValid;
	}

	public void setValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public boolean isOpen() {
		return isOpen == null ? Boolean.FALSE : isOpen;
	}

	public void setOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	@Override
	public String getExtraInfo() {
		return extraInfo;
	}

	@Override
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getUnregisterDate() {
		return unregisterDate;
	}

	public void setUnregisterDate(Date unregisterDate) {
		this.unregisterDate = unregisterDate;
	}

	public String getOrderAreaForName() {
		return orderAreaForName;
	}

	public void setOrderAreaForName(String orderAreaForName) {
		this.orderAreaForName = orderAreaForName;
	}

	public Date getLastChange() {
		return lastChange;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getBanMarking() {
		return banMarking;
	}

	public void setBanMarking(String banMarking) {
		this.banMarking = banMarking;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public Commune getLegalCommune() {
		return legalCommune;
	}

	public void setLegalCommune(Commune legalCommune) {
		this.legalCommune = legalCommune;
	}

	public OperationForm getOperationForm() {
		return operationForm;
	}

	public void setOperationForm(OperationForm operationForm) {
		this.operationForm = operationForm;
	}

	public User getCEO() {
		return CEO;
	}

	public void setCEO(User cEO) {
		CEO = cEO;
	}

	public Commune getWorkingArea() {
		return workingArea;
	}

	public void setWorkingArea(Commune workingArea) {
		this.workingArea = workingArea;
	}

	public IndustryCode getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(IndustryCode industryCode) {
		this.industryCode = industryCode;
	}

	public UnregisterType getUnregisterType() {
		return unregisterType;
	}

	public void setUnregisterType(UnregisterType unregisterType) {
		this.unregisterType = unregisterType;
	}


	public Date getCreatedOn() {
		return createdOn;
	}

	@SuppressWarnings("unused")
	@PrePersist
	private void prePersist() {
		Date currentDate = new Date();
		this.createdOn = currentDate;
		this.lastChange = currentDate;
	}
	
	@SuppressWarnings("unused")
	@PreUpdate
	private void preUpdate() {
		this.lastChange = new Date();
	}
}
