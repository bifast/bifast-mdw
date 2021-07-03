package bifast.library.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import bifast.library.iso20022.head001.CopyDuplicate1Code;

@Entity(name="INBOUND_MESSAGE")
public class InboundMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name="BIZMSGID")
	private String bizMsgIdr;
	
	@Column(name="MSG_NAME")
	private String msgDefIdr;
	
	@Column(name="ORGN_BANK")
	private String frFinId;
	
	@Column(name="BIZSVC")
	private String bizSvc;
	@Column(name="COPY_MSG")
	private CopyDuplicate1Code cpyDplct;
	@Column(name="DUPL")
	private String pssblDplct;
	@Column(name="RECEIVE_DT")
	private LocalDateTime receiveDt;
	
	@Column (name="RESP_BIZMSGIDR")
	private String respBizMsgIdr;
	private String respStatus;
	
	public InboundMessage() {}
	
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

	public String getFrFinId() {
		return frFinId;
	}

	public void setFrFinId(String frFinId) {
		this.frFinId = frFinId;
	}

	public String getBizSvc() {
		return bizSvc;
	}

	public void setBizSvc(String bizSvc) {
		this.bizSvc = bizSvc;
	}

	public CopyDuplicate1Code getCpyDplct() {
		return cpyDplct;
	}

	public void setCpyDplct(CopyDuplicate1Code cpyDplct) {
		this.cpyDplct = cpyDplct;
	}

	public String getPssblDplct() {
		return pssblDplct;
	}

	public void setPssblDplct(String pssblDplct) {
		this.pssblDplct = pssblDplct;
	}

	public LocalDateTime getReceiveDt() {
		return receiveDt;
	}

	public void setReceiveDt(LocalDateTime receiveDt) {
		this.receiveDt = receiveDt;
	}

//	public String getFullMessage() {
//		return fullMessage;
//	}
//
//	public void setFullMessage(String fullMessage) {
//		this.fullMessage = fullMessage;
//	}
//
//	public String getResponseMessage() {
//		return responseMessage;
//	}
//
//	public void setResponseMessage(String responseMessage) {
//		this.responseMessage = responseMessage;
//	}

	public String getRespBizMsgIdr() {
		return respBizMsgIdr;
	}

	public void setRespBizMsgIdr(String respBizMsgIdr) {
		this.respBizMsgIdr = respBizMsgIdr;
	}

	public String getRespStatus() {
		return respStatus;
	}

	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}

	@Override
	public String toString() {
		return "InboundMessage [bizMsgIdr=" + bizMsgIdr + ", msgDefIdr=" + msgDefIdr + ", frFinId=" + frFinId
				+ ", bizSvc=" + bizSvc + "]";
	}
	

	
}
