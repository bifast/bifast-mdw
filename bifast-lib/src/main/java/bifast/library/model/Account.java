package bifast.library.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	private String accountNumber;
	private String accountName;
	private String accountType;
	private String customerName;
	private String accountStatus;
	private String balance;
	private String customerIdType;
	private String customerIdNumber;
	private String customerType;
	private String customerResidentStatus;
	private String customerTown;
	
	private String proxyType;
	private String proxyValue;
	private String proxyDisplayName;
	
	public Account() {}
	
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
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
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
	
	
	
}
