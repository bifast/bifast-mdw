package bifast.outbound.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
	"transactionId"
	,"dateTime"
	,"merchantType"
	,"terminalId"
	,"komiNoRef"
	,"originalNoRef"
	,"originalDateTime"
	})
public class CbRequestBase {

	private String transactionId;
	private String dateTime;
	private String merchantType;
	private String terminalId;
	
	@JsonProperty("noRef")
	private String komiNoRef;
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
	public String getKomiNoRef() {
		return komiNoRef;
	}
	public void setKomiNoRef(String komiNoRef) {
		this.komiNoRef = komiNoRef;
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
