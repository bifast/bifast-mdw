package bifast.corebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="SETTLEMENT_PROC")
public class SettlementProc {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String orignBank;
	private String recptBank;
	
	@Column(name="SETTL_CONF_BIZMSGID")
	private String settlConfBizMsgId;
	@Column(name="SETTL_CONF_MSG_NAME")
	private String settlConfMesgName;
	@Column(name="ORGNL_CRDT_TRN_BIZMSGID")
	private String orgnlCrdtTrnReqBizMsgId;

	private String ack;
	private String forReversal;
	
	@Column(name="REVERSAL_BIZMSGID")
	private String reversalBizMsgId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSettlConfBizMsgId() {
		return settlConfBizMsgId;
	}

	public String getOrignBank() {
		return orignBank;
	}

	public void setOrignBank(String orignBank) {
		this.orignBank = orignBank;
	}

	public String getRecptBank() {
		return recptBank;
	}

	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}

	public void setSettlConfBizMsgId(String settlConfBizMsgId) {
		this.settlConfBizMsgId = settlConfBizMsgId;
	}

	public String getSettlConfMesgName() {
		return settlConfMesgName;
	}

	public void setSettlConfMesgName(String settlConfMesgName) {
		this.settlConfMesgName = settlConfMesgName;
	}

	public String getOrgnlCrdtTrnReqBizMsgId() {
		return orgnlCrdtTrnReqBizMsgId;
	}

	public void setOrgnlCrdtTrnReqBizMsgId(String orgnlCrdtTrnReqBizMsgId) {
		this.orgnlCrdtTrnReqBizMsgId = orgnlCrdtTrnReqBizMsgId;
	}

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getForReversal() {
		return forReversal;
	}

	public void setForReversal(String forReversal) {
		this.forReversal = forReversal;
	}

	public String getReversalBizMsgId() {
		return reversalBizMsgId;
	}

	public void setReversalBizMsgId(String reversalBizMsgId) {
		this.reversalBizMsgId = reversalBizMsgId;
	}
	
	
}
