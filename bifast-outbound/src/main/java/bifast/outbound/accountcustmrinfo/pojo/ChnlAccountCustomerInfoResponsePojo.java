package bifast.outbound.accountcustmrinfo.pojo;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
	"noRef"
	,"debtorName"
	,"debtorType"
	,"debtorId"
	,"debtorIdType"
	,"accountNumber"
	,"accountType"
	,"residentialStatus"
	,"townName"
	,"emailAddressList"
	,"phoneNumberList"
	})
public class ChnlAccountCustomerInfoResponsePojo {

	@JsonProperty("NoRef")
	private String noRef;
	
	@JsonProperty("DebtorName")
	private String debtorName;
	@JsonProperty("DebtorType")
	private String debtorType;
	@JsonProperty(value="DebtorId", required=true)
	private String debtorId;
	@JsonProperty("DebtorIdType")
	private String debtorIdType;
	
	@JsonProperty("AccountNumber")
	private String accountNumber;
	@JsonProperty("AccountType")
	private String accountType;
	@JsonProperty("ResidentialStatus")
	private String residentialStatus;
	@JsonProperty("TownName")
	private String townName;
	
	@JsonProperty("EmailAddress")
	private List<String> emailAddressList;
	@JsonProperty("PhoneNumber")
	private List<String> phoneNumberList;
	
	public String getNoRef() {
		return Optional.ofNullable(noRef).orElse("");
//		return noRef;
	}
	public void setNoRef(String noRef) {
		this.noRef = noRef;
	}
	public String getDebtorName() {
		return Optional.ofNullable(debtorName).orElse("");
//		return debtorName;
	}
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
	public String getDebtorType() {
		return Optional.ofNullable(debtorType).orElse("");
//		return debtorType;
	}
	public void setDebtorType(String debtorType) {
		this.debtorType = debtorType;
	}
	public String getDebtorId() {
		return Optional.ofNullable(debtorId).orElse("");
//		return debtorId;
	}
	public void setDebtorId(String debtorId) {
		this.debtorId = debtorId;
	}
	public String getDebtorIdType() {
		return Optional.ofNullable(debtorIdType).orElse("");
//		return debtorIdType;
	}
	public void setDebtorIdType(String debtorIdType) {
		this.debtorIdType = debtorIdType;
	}
	public String getAccountNumber() {
		return Optional.ofNullable(accountNumber).orElse("");
//		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return Optional.ofNullable(accountType).orElse("");
//		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getResidentialStatus() {
		return Optional.ofNullable(residentialStatus).orElse("");
//		return residentialStatus;
	}
	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}
	public String getTownName() {
		return Optional.ofNullable(townName).orElse("");
//		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public List<String> getEmailAddressList() {
		return emailAddressList;
	}
	public void setEmailAddressList(List<String> emailAddressList) {
		this.emailAddressList = emailAddressList;
	}
	public List<String> getPhoneNumberList() {
		return phoneNumberList;
	}
	public void setPhoneNumberList(List<String> phoneNumberList) {
		this.phoneNumberList = phoneNumberList;
	}



	
	
}
