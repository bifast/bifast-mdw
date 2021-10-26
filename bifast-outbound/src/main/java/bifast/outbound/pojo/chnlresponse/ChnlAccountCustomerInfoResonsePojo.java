package bifast.outbound.pojo.chnlresponse;

import java.sql.Array;

public class ChnlAccountCustomerInfoResonsePojo {
	
	public String noRef;
	public String DebtorName;
	public String DebtorType;
	public String DebtorId;
	public String AccountNumber;
	public String AccountType;
	public String ResidentialStatus;
	public String TownName;
	public Array EmailAddress;
	public Array PhoneNumber;
	
	public String getNoRef() {
		return noRef;
	}
	public void setNoRef(String noRef) {
		this.noRef = noRef;
	}
	public String getDebtorName() {
		return DebtorName;
	}
	public void setDebtorName(String debtorName) {
		DebtorName = debtorName;
	}
	public String getDebtorType() {
		return DebtorType;
	}
	public void setDebtorType(String debtorType) {
		DebtorType = debtorType;
	}
	public String getDebtorId() {
		return DebtorId;
	}
	public void setDebtorId(String debtorId) {
		DebtorId = debtorId;
	}
	public String getAccountNumber() {
		return AccountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}
	public String getAccountType() {
		return AccountType;
	}
	public void setAccountType(String accountType) {
		AccountType = accountType;
	}
	public String getResidentialStatus() {
		return ResidentialStatus;
	}
	public void setResidentialStatus(String residentialStatus) {
		ResidentialStatus = residentialStatus;
	}
	public String getTownName() {
		return TownName;
	}
	public void setTownName(String townName) {
		TownName = townName;
	}
	public Array getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(Array emailAddress) {
		EmailAddress = emailAddress;
	}
	public Array getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(Array phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	
	

}
