package bifast.outbound.pojo.chnlresponse;

import java.util.List;

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
	@JsonProperty("DebtorId")
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
		return noRef;
	}
	public void setNoRef(String noRef) {
		this.noRef = noRef;
	}
	public String getDebtorName() {
		return debtorName;
	}
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}
	public String getDebtorType() {
		return debtorType;
	}
	public void setDebtorType(String debtorType) {
		this.debtorType = debtorType;
	}
	public String getDebtorId() {
		return debtorId;
	}
	public void setDebtorId(String debtorId) {
		this.debtorId = debtorId;
	}
	public String getDebtorIdType() {
		return debtorIdType;
	}
	public void setDebtorIdType(String debtorIdType) {
		this.debtorIdType = debtorIdType;
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
	public String getResidentialStatus() {
		return residentialStatus;
	}
	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}
	public String getTownName() {
		return townName;
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
