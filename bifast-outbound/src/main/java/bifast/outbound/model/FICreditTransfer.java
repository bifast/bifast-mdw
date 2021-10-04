package bifast.outbound.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity(name="FI_CREDIT_TRANSFER")
public class FICreditTransfer {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="INTR_REF_ID")
	private String intrRefId;


	@Column(name="ORIGN_BANK")
	private String originatingBank;
	
	private String debtorBic;
	private String creditBic;
	
	private BigDecimal amount;	
//	private String status;

	private Long chnlTrxId;

	private LocalDateTime creDt;

	@Column(name="REQ_BIZMSGID", length=50)
	private String requestBizMsgIdr;
	@Column(name="RESP_BIZMSGID", length=50)
	private String responseBizMsgIdr;
	
	@Column(name="SETTLCONF_BIZMSGID", length=50)
	private String SettlementBizMsgId;
	
	@Column(length=20)
	private String saf;
	private Integer safCounter;
		
	@Column(name="CIHUB_REQ_TIME")
	private LocalDateTime cihubRequestDT;
	
	@Column(name="CIHUB_RESP_TIME")
	private LocalDateTime cihubResponseDT;	
	
	private Long cihubElapsedTime;
	
	@Column(name="FULL_REQUEST_MSG", length=4000)
	private String fullRequestMessage;

	@Column(name="FULL_RESPONSE_MSG", length=4000)
	private String fullResponseMsg;

	@Column(length=20)
	private String callStatus;
	@Column(length=20)
	private String responseStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIntrRefId() {
		return intrRefId;
	}
	public void setIntrRefId(String intrRefId) {
		this.intrRefId = intrRefId;
	}
	public String getOriginatingBank() {
		return originatingBank;
	}
	public void setOriginatingBank(String originatingBank) {
		this.originatingBank = originatingBank;
	}
	public String getDebtorBic() {
		return debtorBic;
	}
	public void setDebtorBic(String debtorBic) {
		this.debtorBic = debtorBic;
	}
	public String getCreditBic() {
		return creditBic;
	}
	public void setCreditBic(String creditBic) {
		this.creditBic = creditBic;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getChnlTrxId() {
		return chnlTrxId;
	}
	public void setChnlTrxId(Long chnlTrxId) {
		this.chnlTrxId = chnlTrxId;
	}
	public LocalDateTime getCreDt() {
		return creDt;
	}
	public void setCreDt(LocalDateTime creDt) {
		this.creDt = creDt;
	}
	public String getRequestBizMsgIdr() {
		return requestBizMsgIdr;
	}
	public void setRequestBizMsgIdr(String requestBizMsgIdr) {
		this.requestBizMsgIdr = requestBizMsgIdr;
	}
	public String getResponseBizMsgIdr() {
		return responseBizMsgIdr;
	}
	public void setResponseBizMsgIdr(String responseBizMsgIdr) {
		this.responseBizMsgIdr = responseBizMsgIdr;
	}
	public String getSettlementBizMsgId() {
		return SettlementBizMsgId;
	}
	public void setSettlementBizMsgId(String settlementBizMsgId) {
		SettlementBizMsgId = settlementBizMsgId;
	}
	public String getSaf() {
		return saf;
	}
	public void setSaf(String saf) {
		this.saf = saf;
	}
	public Integer getSafCounter() {
		return safCounter;
	}
	public void setSafCounter(Integer safCounter) {
		this.safCounter = safCounter;
	}
	public LocalDateTime getCihubRequestDT() {
		return cihubRequestDT;
	}
	public void setCihubRequestDT(LocalDateTime cihubRequestDT) {
		this.cihubRequestDT = cihubRequestDT;
	}
	public LocalDateTime getCihubResponseDT() {
		return cihubResponseDT;
	}
	public void setCihubResponseDT(LocalDateTime cihubResponseDT) {
		this.cihubResponseDT = cihubResponseDT;
	}
	public Long getCihubElapsedTime() {
		return cihubElapsedTime;
	}
	public void setCihubElapsedTime(Long cihubElapsedTime) {
		this.cihubElapsedTime = cihubElapsedTime;
	}
	public String getFullRequestMessage() {
		return fullRequestMessage;
	}
	public void setFullRequestMessage(String fullRequestMessage) {
		this.fullRequestMessage = fullRequestMessage;
	}
	public String getFullResponseMsg() {
		return fullResponseMsg;
	}
	public void setFullResponseMsg(String fullResponseMsg) {
		this.fullResponseMsg = fullResponseMsg;
	}
	public String getCallStatus() {
		return callStatus;
	}
	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	
	
}
