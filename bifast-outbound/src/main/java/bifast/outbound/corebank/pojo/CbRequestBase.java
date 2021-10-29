package bifast.outbound.corebank.pojo;

public class CbRequestBase {

	private String transactionId;
	private String trnsDt;
	private String dateTime;
	private String merchantType;
	private String terminalId;
	private String noRef;
	private String originalNoRef;
	private String originalDateTime;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
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
	public String getNoRef() {
		return noRef;
	}
	public void setNoRef(String noRef) {
		this.noRef = noRef;
	}
	public String getTrnsDt() {
		return trnsDt;
	}
	public void setTrnsDt(String trnsDt) {
		this.trnsDt = trnsDt;
	}
	public String getOriginalNoRef() {
		return originalNoRef;
	}
	public void setOriginalNoRef(String originalNoRef) {
		this.originalNoRef = originalNoRef;
	}
	public String getOriginalDateTime() {
		return originalDateTime;
	}
	public void setOriginalDateTime(String originalDateTime) {
		this.originalDateTime = originalDateTime;
	}

	
}
