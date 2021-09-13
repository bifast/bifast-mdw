package bifast.inbound.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="INBOUND_MESSAGE")
public class InboundMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="BIZMSGID")
	private String bizMsgIdr;
	
	@Column(name="MSG_NAME")
	private String messageName;
	
	@Column(name="ORGN_BANK")
	private String frFinId;
	
	@Column(name="COPYDUPL")
	private String copyDupl;

	private String respStatus;
	private String respRejectMsg;
	
	@Column (name="RESP_BIZMSGIDR")
	private String respBizMsgIdr;

	private LocalDateTime cihubRequestTime;
	private LocalDateTime cihubResponseTime;
	
	@Column(name="FULL_REQUEST_MSG", length=4000)
	private String fullRequestMessage;

	@Column(name="FULL_RESPONSE_MSG", length=4000)
	private String fullResponseMsg;

	public InboundMessage() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getBizMsgIdr() {
		return bizMsgIdr;
	}


	public void setBizMsgIdr(String bizMsgIdr) {
		this.bizMsgIdr = bizMsgIdr;
	}


	public String getMessageName() {
		return messageName;
	}


	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}


	public String getFrFinId() {
		return frFinId;
	}


	public void setFrFinId(String frFinId) {
		this.frFinId = frFinId;
	}

	public String getCopyDupl() {
		return copyDupl;
	}


	public void setCopyDupl(String copyDupl) {
		this.copyDupl = copyDupl;
	}


	public String getRespStatus() {
		return respStatus;
	}


	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}


	public String getRespRejectMsg() {
		return respRejectMsg;
	}


	public void setRespRejectMsg(String respRejectMsg) {
		this.respRejectMsg = respRejectMsg;
	}


	public String getRespBizMsgIdr() {
		return respBizMsgIdr;
	}


	public void setRespBizMsgIdr(String respBizMsgIdr) {
		this.respBizMsgIdr = respBizMsgIdr;
	}


	public LocalDateTime getCihubRequestTime() {
		return cihubRequestTime;
	}


	public void setCihubRequestTime(LocalDateTime cihubRequestTime) {
		this.cihubRequestTime = cihubRequestTime;
	}


	public LocalDateTime getCihubResponseTime() {
		return cihubResponseTime;
	}


	public void setCihubResponseTime(LocalDateTime cihubResponseTime) {
		this.cihubResponseTime = cihubResponseTime;
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

	
}
