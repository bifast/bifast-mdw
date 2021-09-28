package bifast.corebank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "CB_ACCOUNT")
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "intr_ref_id")
	private String intrRefId;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "saldo")
	private BigDecimal saldo;
	
	@Column(name = "account_type")
	private String accountType;
	
	@Column(name = "account_no")
	private String accountNo;
	
	@Column(name = "additional_info")
	private String additionalInfo;
	
	@Column(name = "creditor_status")
	private String creditorStatus;  // ACTIVE, INACTIVE, PENDING
	
	@Column(name = "creditor_account_number")
	private String creditorAccountNumber;
	
	@Column(name = "creditor_account_type")
	private String creditorAccountType;
	
	@Column(name = "creditor_name")
	private String creditorName;
	
	@Column(name = "creditor_id")
	private String creditorId;
	
	@Column(name = "creditor_type")
	private String creditorType;
	
	@Column(name = "creditor_resident_status")
	private String creditorResidentStatus;
	
	@Column(name = "creditor_town_name")
	private String creditorTownName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIntrRefId() {
		return intrRefId;
	}

	public void setIntrRefId(String intrRefId) {
		this.intrRefId = intrRefId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getCreditorStatus() {
		return creditorStatus;
	}

	public void setCreditorStatus(String creditorStatus) {
		this.creditorStatus = creditorStatus;
	}

	public String getCreditorAccountNumber() {
		return creditorAccountNumber;
	}

	public void setCreditorAccountNumber(String creditorAccountNumber) {
		this.creditorAccountNumber = creditorAccountNumber;
	}

	public String getCreditorAccountType() {
		return creditorAccountType;
	}

	public void setCreditorAccountType(String creditorAccountType) {
		this.creditorAccountType = creditorAccountType;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}

	public String getCreditorType() {
		return creditorType;
	}

	public void setCreditorType(String creditorType) {
		this.creditorType = creditorType;
	}

	public String getCreditorResidentStatus() {
		return creditorResidentStatus;
	}

	public void setCreditorResidentStatus(String creditorResidentStatus) {
		this.creditorResidentStatus = creditorResidentStatus;
	}

	public String getCreditorTownName() {
		return creditorTownName;
	}

	public void setCreditorTownName(String creditorTownName) {
		this.creditorTownName = creditorTownName;
	}
	
	
	
	
}
