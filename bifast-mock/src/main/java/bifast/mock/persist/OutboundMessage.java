package bifast.mock.persist;

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

	@Column(name="INTRN_REF_ID")
	private String internalReffId;
	
	private String channel;
	
	@Column(name="BIZMSGID")
	private String bizMsgIdr;
	
	@Column(name="MESSAGE_NAME")
	private String messageName;
	
	@Column(name="RECPT_BANK")
	private String recipientBank;
	
	@Column(name="RESP_BIZMSGID")
	private String respBizMsgId;
	
	private String respStatus;

	@Column(name="ERROR_MSG", length=500)
	private String errorMessage;
	
	@Column(name="CHNL_REQ_TIME")
	private LocalDateTime channelRequestDT;
	
	@Column(name="CHNL_RESP_TIME")
	private LocalDateTime channelResponseDT;	

	@Column(name="CIHUB_REQ_TIME")
	private LocalDateTime cihubRequestDT;
	
	@Column(name="CIHUB_RESP_TIME")
	private LocalDateTime cihubResponseDT;	
	
	@Column(name="FULL_REQUEST_MSG", length=4000)
	private String fullRequestMessage;

	@Column(name="FULL_RESPONSE_MSG", length=4000)
	private String fullResponseMsg;

	public OutboundMessage() {}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getInternalReffId() {
		return internalReffId;
	}

	public void setInternalReffId(String internalReffId) {
		this.internalReffId = internalReffId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getRecipientBank() {
		return recipientBank;
	}

	public void setRecipientBank(String recipientBank) {
		this.recipientBank = recipientBank;
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getChannelRequestDT() {
		return channelRequestDT;
	}

	public void setChannelRequestDT(LocalDateTime channelRequestDT) {
		this.channelRequestDT = channelRequestDT;
	}

	public LocalDateTime getChannelResponseDT() {
		return channelResponseDT;
	}

	public void setChannelResponseDT(LocalDateTime channelResponseDT) {
		this.channelResponseDT = channelResponseDT;
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
