package bifast.outbound.pojo;

import java.math.BigDecimal;

import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.pojo.flat.FlatPacs008Pojo;

public class EventPojo {

	
	private String eventCategory;    // Business, System
	private String eventType;      // log, notif
	private String urgency;        // low, medium, high
	private String transactionId;    // chnlReqId atau bizmsgidr
	private String transactionGrpId;   // samadengan channel request id
	private FlatPacs008Pojo pacs008RequestMesg;
	private FlatPacs002Pojo pacs002ResponseMesg;
	
	private String transactionType;
	private String accountNumber;
	private String customerId;
	private BigDecimal amount;
	
	private String eventDt;

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionGrpId() {
		return transactionGrpId;
	}

	public void setTransactionGrpId(String transactionGrpId) {
		this.transactionGrpId = transactionGrpId;
	}

	public FlatPacs008Pojo getPacs008RequestMesg() {
		return pacs008RequestMesg;
	}

	public void setPacs008RequestMesg(FlatPacs008Pojo pacs008RequestMesg) {
		this.pacs008RequestMesg = pacs008RequestMesg;
	}

	public FlatPacs002Pojo getPacs002ResponseMesg() {
		return pacs002ResponseMesg;
	}

	public void setPacs002ResponseMesg(FlatPacs002Pojo pacs002ResponseMesg) {
		this.pacs002ResponseMesg = pacs002ResponseMesg;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getEventDt() {
		return eventDt;
	}

	public void setEventDt(String eventDt) {
		this.eventDt = eventDt;
	}
	
	
}
