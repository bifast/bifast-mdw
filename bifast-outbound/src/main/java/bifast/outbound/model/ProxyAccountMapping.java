package bifast.outbound.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class ProxyAccountMapping {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String accountNumber;
	private String accountType;
	private String accountName;
	
	private String proxyType;
	private String proxyValue;	
	private String displayName;
	
	private String registId;
	private LocalDateTime registDt;

	private String customerType;
	private String customerId;
	private String customerIdType;
	private String scndCustomerId;
	private String scndCustomerIdType;
	
	private String proxyRegAgtId;
	
	private String proxyStatus;
	private String residentStatus;
	private String customerTownName;
	
	private LocalDateTime updateDt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRegistId() {
		return registId;
	}

	public void setRegistId(String registId) {
		this.registId = registId;
	}

	public LocalDateTime getRegistDt() {
		return registDt;
	}

	public void setRegistDt(LocalDateTime registDt) {
		this.registDt = registDt;
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

	public String getCustomerIdType() {
		return customerIdType;
	}

	public void setCustomerIdType(String customerIdType) {
		this.customerIdType = customerIdType;
	}

	public String getScndCustomerId() {
		return scndCustomerId;
	}

	public void setScndCustomerId(String scndCustomerId) {
		this.scndCustomerId = scndCustomerId;
	}

	public String getScndCustomerIdType() {
		return scndCustomerIdType;
	}

	public void setScndCustomerIdType(String scndCustomerIdType) {
		this.scndCustomerIdType = scndCustomerIdType;
	}

	public String getProxyRegAgtId() {
		return proxyRegAgtId;
	}

	public void setProxyRegAgtId(String proxyRegAgtId) {
		this.proxyRegAgtId = proxyRegAgtId;
	}

	public String getProxyStatus() {
		return proxyStatus;
	}

	public void setProxyStatus(String proxyStatus) {
		this.proxyStatus = proxyStatus;
	}

	public String getResidentStatus() {
		return residentStatus;
	}

	public void setResidentStatus(String residentStatus) {
		this.residentStatus = residentStatus;
	}

	public String getCustomerTownName() {
		return customerTownName;
	}

	public void setCustomerTownName(String customerTownName) {
		this.customerTownName = customerTownName;
	}

	public LocalDateTime getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(LocalDateTime updateDt) {
		this.updateDt = updateDt;
	}
	
	
	
	
}
