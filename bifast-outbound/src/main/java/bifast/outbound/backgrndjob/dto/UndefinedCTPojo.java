package bifast.outbound.backgrndjob.dto;

import java.time.LocalDateTime;

public class UndefinedCTPojo {

	public String id;
	public String reqBizmsgid;
	public String KomiTrnsId;
	public String channelType;
	public String channelNoref;
	public String endToEndId;
	public String recipientBank;
	public String ctFullText;
	public int psCounter;
	
	public LocalDateTime orgnlDateTime;
	
	public String psStatus;
	public String responseCode;
	public String reasonCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReqBizmsgid() {
		return reqBizmsgid;
	}
	public void setReqBizmsgid(String reqBizmsgid) {
		this.reqBizmsgid = reqBizmsgid;
	}
	public String getKomiTrnsId() {
		return KomiTrnsId;
	}
	public void setKomiTrnsId(String komiTrnsId) {
		KomiTrnsId = komiTrnsId;
	}
	public String getRecipientBank() {
		return recipientBank;
	}
	public void setRecipientBank(String recipientBank) {
		this.recipientBank = recipientBank;
	}
	public String getCtFullText() {
		return ctFullText;
	}
	public void setCtFullText(String ctFullText) {
		this.ctFullText = ctFullText;
	}
	public String getPsStatus() {
		return psStatus;
	}
	public void setPsStatus(String psStatus) {
		this.psStatus = psStatus;
	}
	public int getPsCounter() {
		return psCounter;
	}
	public void setPsCounter(int psCounter) {
		this.psCounter = psCounter;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getEndToEndId() {
		return endToEndId;
	}
	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}
	public LocalDateTime getOrgnlDateTime() {
		return orgnlDateTime;
	}
	public void setOrgnlDateTime(LocalDateTime orgnlDateTime) {
		this.orgnlDateTime = orgnlDateTime;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getChannelNoref() {
		return channelNoref;
	}
	public void setChannelNoref(String channelNoref) {
		this.channelNoref = channelNoref;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	
	
}
