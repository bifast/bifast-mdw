package bifast.outbound.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="M_BIC")
public class BankCode {

	@Id
	private String bankCode;
	private String bicCode;
	private String bankName;
	private LocalDateTime lastUpdateDate;
	private LocalDateTime createdDate;
	private String changeWho;
	
	public BankCode () {}
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBicCode() {
		return bicCode;
	}
	public void setBicCode(String bicCode) {
		this.bicCode = bicCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public String getChangeWho() {
		return changeWho;
	}
	public void setChangeWho(String changeWho) {
		this.changeWho = changeWho;
	}
	
	
}
