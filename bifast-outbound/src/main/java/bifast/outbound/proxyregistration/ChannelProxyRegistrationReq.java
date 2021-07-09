package bifast.outbound.proxyregistration;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyRegistrationRequest")
public class ChannelProxyRegistrationReq {
	
	private String recptBank; 
	private String channel; 
	
	private String trnType; 
	private String bizMsgId;
	
	private String proxyTp;
	private String proxyVal;
	
	protected String regnTp;
    protected String regnId;
    protected String dsplNm;
    
    protected String accNumber;
    protected String accTpPrtry;
    protected String accName;
    
    protected String scndIdTp;
    protected String scndIdVal;
    
    protected String cstmrId;
    protected String cstmrTp;
    protected String cstmrRsdntSts;
    protected String cstmrTwnNm;
    
	public String getRecptBank() {
		return recptBank;
	}
	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTrnType() {
		return trnType;
	}
	public void setTrnType(String trnType) {
		this.trnType = trnType;
	}
	public String getBizMsgId() {
		return bizMsgId;
	}
	public void setBizMsgId(String bizMsgId) {
		this.bizMsgId = bizMsgId;
	}
	public String getProxyTp() {
		return proxyTp;
	}
	public void setProxyTp(String proxyTp) {
		this.proxyTp = proxyTp;
	}
	public String getProxyVal() {
		return proxyVal;
	}
	public void setProxyVal(String proxyVal) {
		this.proxyVal = proxyVal;
	}
	public String getRegnTp() {
		return regnTp;
	}
	public void setRegnTp(String regnTp) {
		this.regnTp = regnTp;
	}
	public String getRegnId() {
		return regnId;
	}
	public void setRegnId(String regnId) {
		this.regnId = regnId;
	}
	public String getDsplNm() {
		return dsplNm;
	}
	public void setDsplNm(String dsplNm) {
		this.dsplNm = dsplNm;
	}
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	public String getAccTpPrtry() {
		return accTpPrtry;
	}
	public void setAccTpPrtry(String accTpPrtry) {
		this.accTpPrtry = accTpPrtry;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getScndIdTp() {
		return scndIdTp;
	}
	public void setScndIdTp(String scndIdTp) {
		this.scndIdTp = scndIdTp;
	}
	public String getScndIdVal() {
		return scndIdVal;
	}
	public void setScndIdVal(String scndIdVal) {
		this.scndIdVal = scndIdVal;
	}
	public String getCstmrId() {
		return cstmrId;
	}
	public void setCstmrId(String cstmrId) {
		this.cstmrId = cstmrId;
	}
	public String getCstmrTp() {
		return cstmrTp;
	}
	public void setCstmrTp(String cstmrTp) {
		this.cstmrTp = cstmrTp;
	}
	public String getCstmrRsdntSts() {
		return cstmrRsdntSts;
	}
	public void setCstmrRsdntSts(String cstmrRsdntSts) {
		this.cstmrRsdntSts = cstmrRsdntSts;
	}
	public String getCstmrTwnNm() {
		return cstmrTwnNm;
	}
	public void setCstmrTwnNm(String cstmrTwnNm) {
		this.cstmrTwnNm = cstmrTwnNm;
	}
    
}
