package bifast.inbound.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class AccountEnquiry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
	@SequenceGenerator(name="seq_generator", sequenceName = "table_seq_generator", allocationSize=1)
	private Long id;
	
//	private Long chnlTrxId;
	private String komiTrnsId;
	
	@Column(length=20)
	private String intrRefId;
	
	@Column(name="BIZMSGID", length=50)
	private String bizMsgIdr;

	@Column(name="RESP_BIZMSGID", length=50)
	private String respBizMsgIdr;

	@Column(name="ORIGN_BANK", length=20)
	private String originatingBank;
	@Column(name="RECPT_BANK", length=20)
	private String recipientBank;

	private BigDecimal amount;
	
	@Column(length=50)
	private String accountNo;

//	private LocalDateTime creDt;

	@Column(length=20)
	private String responseStatus;

	@Column(name="CIHUB_REQ_TIME")
	private LocalDateTime cihubRequestDT;
	
//	@Column(name="CIHUB_RESP_TIME")
//	private LocalDateTime cihubResponseDT;	
	
	private Long cihubElapsedTime;
	
	@Column(name="FULL_REQUEST_MSG", length=4000)
	private String fullRequestMessage;

	@Column(name="FULL_RESPONSE_MSG", length=4000)
	private String fullResponseMsg;
	
	private String callStatus;
	
	@Column(length=400)
	private String errorMessage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKomiTrnsId() {
		return komiTrnsId;
	}

	public void setKomiTrnsId(String komiTrnsId) {
		this.komiTrnsId = komiTrnsId;
	}

	public String getIntrRefId() {
		return intrRefId;
	}

	public void setIntrRefId(String intrRefId) {
		this.intrRefId = intrRefId;
	}

	public String getBizMsgIdr() {
		return bizMsgIdr;
	}

	public void setBizMsgIdr(String bizMsgIdr) {
		this.bizMsgIdr = bizMsgIdr;
	}

	public String getRespBizMsgIdr() {
		return respBizMsgIdr;
	}

	public void setRespBizMsgIdr(String respBizMsgIdr) {
		this.respBizMsgIdr = respBizMsgIdr;
	}

	public String getOriginatingBank() {
		return originatingBank;
	}

	public void setOriginatingBank(String originatingBank) {
		this.originatingBank = originatingBank;
	}

	public String getRecipientBank() {
		return recipientBank;
	}

	public void setRecipientBank(String recipientBank) {
		this.recipientBank = recipientBank;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public LocalDateTime getCihubRequestDT() {
		return cihubRequestDT;
	}

	public void setCihubRequestDT(LocalDateTime cihubRequestDT) {
		this.cihubRequestDT = cihubRequestDT;
	}

//	public LocalDateTime getCihubResponseDT() {
//		return cihubResponseDT;
//	}
//
//	public void setCihubResponseDT(LocalDateTime cihubResponseDT) {
//		this.cihubResponseDT = cihubResponseDT;
//	}

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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


}
