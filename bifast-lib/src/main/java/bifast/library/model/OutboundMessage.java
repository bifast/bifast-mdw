package bifast.library.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="OUTBOUND_MESSAGE")
public class OutboundMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@Column(name="BIZMSGID")
	private String bizMsgIdr;
	
	@Column(name="TRX_TYPE")
	private String transactionType;
	
	@Column(name="MESSAGE_NAME")
	private String msgDefIdr;
	
	@Column(name="RECPT_BANK")
	private String toFinId;
	
//	@Column(name="AMOUNT")
//	private BigDecimal amount;

	@Column(name="SEND_TIME")
	private LocalDateTime sendDt;

	@Column(name="SAF_COUNTER")
	private Integer safCounter;

	@Column(name="RESP_BIZMSGID")
	private String respBizMsgId;
	
	private String respStatus;

	@Column(name="REJCT_MSG")
	private String failureMessage;

	public OutboundMessage() {}


	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getBizMsgIdr() {
		return bizMsgIdr;
	}


	public void setBizMsgIdr(String bizMsgIdr) {
		this.bizMsgIdr = bizMsgIdr;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public String getMsgDefIdr() {
		return msgDefIdr;
	}


	public void setMsgDefIdr(String msgDefIdr) {
		this.msgDefIdr = msgDefIdr;
	}


	public String getToFinId() {
		return toFinId;
	}


	public void setToFinId(String toFinId) {
		this.toFinId = toFinId;
	}


//	public BigDecimal getAmount() {
//		return amount;
//	}
//
//
//	public void setAmount(BigDecimal amount) {
//		this.amount = amount;
//	}


	public LocalDateTime getSendDt() {
		return sendDt;
	}


	public void setSendDt(LocalDateTime sendDt) {
		this.sendDt = sendDt;
	}


	public Integer getSafCounter() {
		return safCounter;
	}


	public void setSafCounter(Integer safCounter) {
		this.safCounter = safCounter;
	}


	public String getRespBizMsgId() {
		return respBizMsgId;
	}


	public void setRespBizMsgId(String respBizMsgId) {
		this.respBizMsgId = respBizMsgId;
	}


	public String getRespStatus() {
		return respStatus;
	}


	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}


	public String getFailureMessage() {
		return failureMessage;
	}


	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	
}
