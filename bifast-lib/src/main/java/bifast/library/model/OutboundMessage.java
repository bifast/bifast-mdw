package bifast.library.model;

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
	
	@Column(name="TRX_TYPE")
	private String transactionType;
	
	@Column(name="MESSAGE_NAME")
	private String msgDefIdr;
	
	@Column(name="RECPT_BANK")
	private String toFinId;
	
	@Column(name="RESP_BIZMSGID")
	private String respBizMsgId;
	
	private String respStatus;

	@Column(name="ERROR_MSG", length=500)
	private String errorMessage;
	
	@Column(name="LOGFILE_NAME")
	private String logfileName;

	@Column(name="CHNL_REQ_TIME")
	private LocalDateTime channelRequestDT;
	
	@Column(name="CHNL_RESP_TIME")
	private LocalDateTime channelResponseDT;	

	@Column(name="CIHUB_REQ_TIME")
	private LocalDateTime cihubRequestDT;
	
	@Column(name="CIHUB_RESP_TIME")
	private LocalDateTime cihubResponseDT;	

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

	public String getLogfileName() {
		return logfileName;
	}

	public void setLogfileName(String logfileName) {
		this.logfileName = logfileName;
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


	
}
