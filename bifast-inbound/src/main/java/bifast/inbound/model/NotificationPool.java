package bifast.inbound.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class NotificationPool {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String eventCtgr;    // business/system event
	private String notifLevel;  // ALERT-ERROR-INFO
	private String urgency;     // HIGH-MEDIUM-LOW
	private String eventGrp;   // receive Credit / CT Success-pending-reject RJCT-CIHUB/TIMEOUT-CIHUB/ERROR-CIHUB
	private String refId;      // channelRefId-IntrnRefId-bizmsgidr
	private String messageDesc;
	private String destination;
	private String distributionChannel;    //
	private String customerId;
	private String customerAccount;
	private String customerName;
	private String emailAddr;
	private String phoneNo;
	
	private LocalDateTime creDt;
	private String ackStatus;
	private LocalDateTime ackDt;

	
	
}
