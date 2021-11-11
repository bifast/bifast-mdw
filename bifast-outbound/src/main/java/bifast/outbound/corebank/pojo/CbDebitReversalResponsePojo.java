package bifast.outbound.corebank.pojo;

public class CbDebitReversalResponsePojo {

	private String komiTrnsId;
	private String orgnlKomiTrnsId;
	private String status;
	private String reason;
	private String accountNumber;
	
	public String getKomiTrnsId() {
		return komiTrnsId;
	}
	public void setKomiTrnsId(String komiTrnsId) {
		this.komiTrnsId = komiTrnsId;
	}
	public String getOrgnlKomiTrnsId() {
		return orgnlKomiTrnsId;
	}
	public void setOrgnlKomiTrnsId(String orgnlKomiTrnsId) {
		this.orgnlKomiTrnsId = orgnlKomiTrnsId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	
}
