package bifast.outbound.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="SETTLEMENT")
public class Settlement {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
	@SequenceGenerator(name="seq_generator", sequenceName = "table_seq_generator", allocationSize=1)
	private Long id;
	
	@Column(length=20)
	private String orignBank;
	@Column(length=20)
	private String recptBank;
	
	@Column(name="SETTL_CONF_BIZMSGID", length=50)
	private String settlConfBizMsgId;
	@Column(name="ORGNL_CRDT_TRN_BIZMSGID", length=50)
	private String orgnlCrdtTrnReqBizMsgId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getSettlConfBizMsgId() {
		return settlConfBizMsgId;
	}
	public void setSettlConfBizMsgId(String settlConfBizMsgId) {
		this.settlConfBizMsgId = settlConfBizMsgId;
	}
	public String getOrgnlCrdtTrnReqBizMsgId() {
		return orgnlCrdtTrnReqBizMsgId;
	}
	public void setOrgnlCrdtTrnReqBizMsgId(String orgnlCrdtTrnReqBizMsgId) {
		this.orgnlCrdtTrnReqBizMsgId = orgnlCrdtTrnReqBizMsgId;
	}
	
	
	
	

	
}
