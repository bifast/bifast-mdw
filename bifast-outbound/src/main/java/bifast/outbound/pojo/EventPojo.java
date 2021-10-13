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
	
	
}
