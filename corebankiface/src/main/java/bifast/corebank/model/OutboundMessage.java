package bifast.corebank.model;

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
	@Column(name="MESSAGE_NAME")
	private String msgDefIdr;
	
	@Column(name="RECPT_BANK")
	private String toFinId;

	@Column(length=5000)
	private String fullMessage;


	@Column(name="SEND_TIME")
	private LocalDateTime sendDt;

	@Column(name="SAF_COUNTER")
	private Integer safCounter;

	@Column(name="HTTP_RESPONSE_CODE")
	private String httpResponse;

	@Column(name="RESP_BIZMSGID")
	private String respBizMsgId;
	
	@Column(name="RESP_MSG", length=5000)
	private String responseMessage;
	

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

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(String httpResponse) {
		this.httpResponse = httpResponse;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}

	public LocalDateTime getSendDt() {
		return sendDt;
	}

	public void setSendDt(LocalDateTime sendDt) {
		this.sendDt = sendDt;
	}

	public String getRespBizMsgId() {
		return respBizMsgId;
	}

	public void setRespBizMsgId(String respBizMsgId) {
		this.respBizMsgId = respBizMsgId;
	}

	public Integer getSafCounter() {
		return safCounter;
	}

	public void setSafCounter(Integer safCounter) {
		this.safCounter = safCounter;
	}
	

	
}
