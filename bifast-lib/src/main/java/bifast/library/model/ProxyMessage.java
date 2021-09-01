package bifast.library.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="PROXY_MESSAGE")
public class ProxyMessage {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long logMessageId;
	
	private String operationType;
	private String proxyType;
	private String proxyValue;
	private String displayName;
	private String accountNumber;
	private String accountType;
	private String accountName;
	private String scndIdType;
	private String scndValue;
	private String customerType;
	private String customerId;
	private String residentStatus;
	private String townName;
	
	public ProxyMessage() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLogMessageId() {
		return logMessageId;
	}

	public void setLogMessageId(Long logMessageId) {
		this.logMessageId = logMessageId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}

	public String getProxyValue() {
		return proxyValue;
	}

	public void setProxyValue(String proxyValue) {
		this.proxyValue = proxyValue;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getScndIdType() {
		return scndIdType;
	}

	public void setScndIdType(String scndIdType) {
		this.scndIdType = scndIdType;
	}

	public String getScndValue() {
		return scndValue;
	}

	public void setScndValue(String scndValue) {
		this.scndValue = scndValue;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getResidentStatus() {
		return residentStatus;
	}

	public void setResidentStatus(String residentStatus) {
		this.residentStatus = residentStatus;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	
}
