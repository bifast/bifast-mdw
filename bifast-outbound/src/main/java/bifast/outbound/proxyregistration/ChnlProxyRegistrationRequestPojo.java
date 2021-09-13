package bifast.outbound.proxyregistration;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyRegistrationRequest")
public class ChnlProxyRegistrationRequestPojo {
	
	private String orignReffId;

	private String channel; 
	
	private String proxyTp;
	private String proxyVal;
	
	private String regnTp;
	private String regnId;
	private String displayName;
    
	private String accNumber;
	private String accType;
	private String accName;
    
	private String scndIdTp;
	private String scndIdVal;
    
	private String cstmrId;
	private String cstmrTp;
	private String residentialStatus;
	private String townName;

    public String getOrignReffId() {
		return orignReffId;
	}
	public void setOrignReffId(String orignReffId) {
		this.orignReffId = orignReffId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
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
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
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
	public String getResidentialStatus() {
		return residentialStatus;
	}
	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
    
}
