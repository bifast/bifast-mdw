package library.persist;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="INBOUND_MESSAGE")
public class InboundMessage {

	@Id
	private String frFinId;
	private String bizMsgIdr;
	private String refId;
	private String msgDefIdr;
	private String bizSvc;
	private String cpyDplct;
	private String pssblDplct;
	private LocalDateTime receiveDt;
	private String acknowledge;
	
	@Column(length=4000)
	private String fullMessage;

	public String getFrFinId() {
		return frFinId;
	}

	public void setFrFinId(String frFinId) {
		this.frFinId = frFinId;
	}

	public String getBizMsgIdr() {
		return bizMsgIdr;
	}

	public void setBizMsgIdr(String bizMsgIdr) {
		this.bizMsgIdr = bizMsgIdr;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getMsgDefIdr() {
		return msgDefIdr;
	}

	public void setMsgDefIdr(String msgDefIdr) {
		this.msgDefIdr = msgDefIdr;
	}

	public String getBizSvc() {
		return bizSvc;
	}

	public void setBizSvc(String bizSvc) {
		this.bizSvc = bizSvc;
	}

	public String getCpyDplct() {
		return cpyDplct;
	}

	public void setCpyDplct(String cpyDplct) {
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

	public String getAcknowledge() {
		return acknowledge;
	}

	public void setAcknowledge(String acknowledge) {
		this.acknowledge = acknowledge;
	}

	public String getFullMessage() {
		return fullMessage;
	}

	public void setFullMessage(String fullMessage) {
		this.fullMessage = fullMessage;
	}

	

	
}
