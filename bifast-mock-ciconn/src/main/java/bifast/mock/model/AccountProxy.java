package bifast.mock.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AccountProxy {

	@Id
	public Long id;

	private String bic;
	private String accountNumber;
	private String accountName;
	private String accountType;

	private String proxyType;
	private String proxyValue;
	private String proxyDisplayName;

	private String customerName;
	private String accountStatus;
	private String customerIdType;
	private String customerIdNumber;
	private String customerType;
	private String customerResidentStatus;
	private String customerTown;
	
	public AccountProxy() {}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
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
	public String getProxyDisplayName() {
		return proxyDisplayName;
	}
	public void setProxyDisplayName(String proxyDisplayName) {
		this.proxyDisplayName = proxyDisplayName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getCustomerIdType() {
		return customerIdType;
	}
	public void setCustomerIdType(String customerIdType) {
		this.customerIdType = customerIdType;
	}
	public String getCustomerIdNumber() {
		return customerIdNumber;
	}
	public void setCustomerIdNumber(String customerIdNumber) {
		this.customerIdNumber = customerIdNumber;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerResidentStatus() {
		return customerResidentStatus;
	}
	public void setCustomerResidentStatus(String customerResidentStatus) {
		this.customerResidentStatus = customerResidentStatus;
	}
	public String getCustomerTown() {
		return customerTown;
	}
	public void setCustomerTown(String customerTown) {
		this.customerTown = customerTown;
	}

	
}
