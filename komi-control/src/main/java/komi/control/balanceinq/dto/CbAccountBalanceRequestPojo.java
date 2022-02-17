package komi.control.balanceinq.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountBalanceRequest")
public class CbAccountBalanceRequestPojo {
	public String komiTrnsId;
	public String accountNumber;
	
	public String getKomiTrnsId() {
		return komiTrnsId;
	}
	public void setKomiTrnsId(String komiTrnsId) {
		this.komiTrnsId = komiTrnsId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
}
