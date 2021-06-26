package library.persist;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="REVERSE_CREDIT_TRANFER")
public class ReverseCreditTransfer {
    
    @Id
    private Long id;

    private String settlementBizMsgId;
    private String originatingBank;
    private String crdtAccountNumber;
    private String dbtrAccountNumber;
    private String dbtrAccountName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSettlementBizMsgId() {
		return settlementBizMsgId;
	}
	public void setSettlementBizMsgId(String settlementBizMsgId) {
		this.settlementBizMsgId = settlementBizMsgId;
	}
	public String getOriginatingBank() {
		return originatingBank;
	}
	public void setOriginatingBank(String originatingBank) {
		this.originatingBank = originatingBank;
	}
	public String getCrdtAccountNumber() {
		return crdtAccountNumber;
	}
	public void setCrdtAccountNumber(String crdtAccountNumber) {
		this.crdtAccountNumber = crdtAccountNumber;
	}
	public String getDbtrAccountNumber() {
		return dbtrAccountNumber;
	}
	public void setDbtrAccountNumber(String dbtrAccountNumber) {
		this.dbtrAccountNumber = dbtrAccountNumber;
	}
	public String getDbtrAccountName() {
		return dbtrAccountName;
	}
	public void setDbtrAccountName(String dbtrAccountName) {
		this.dbtrAccountName = dbtrAccountName;
	}
    
    
}
