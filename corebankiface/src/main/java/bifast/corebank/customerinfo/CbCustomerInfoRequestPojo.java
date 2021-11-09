package bifast.corebank.customerinfo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("CustomerInfoRequest")
public class CbCustomerInfoRequestPojo {

	private String KomiTrnsId;
	private String noRef;
	private String merchantType;
	private String terminalId;
	private String accountNumber;
	
	public String getKomiTrnsId() {
		return KomiTrnsId;
	}
	public void setKomiTrnsId(String komiTrnsId) {
		KomiTrnsId = komiTrnsId;
	}
	public String getNoRef() {
		return noRef;
	}
	public void setNoRef(String noRef) {
		this.noRef = noRef;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
