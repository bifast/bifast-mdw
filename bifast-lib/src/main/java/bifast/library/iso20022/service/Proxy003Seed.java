package bifast.library.iso20022.service;

import bifast.library.iso20022.prxy003.ProxyLookUpType1Code;

public class Proxy003Seed {
	
	private String msgId;
	
	private String trnType;
	
	private ProxyLookUpType1Code lookupType;
	private String id;
	private String proxyType;
	private String proxyValue;
	private String sndrAccountNumber;
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getTrnType() {
		return trnType;
	}
	public void setTrnType(String trnType) {
		this.trnType = trnType;
	}
	public ProxyLookUpType1Code getLookupType() {
		return lookupType;
	}
	public void setLookupType(ProxyLookUpType1Code lookupType) {
		this.lookupType = lookupType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProxyType() {
		return proxyType;
	}
	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}
	public String getProxyValue() {
		return proxyValue;
	}
	public void setProxyValue(String proxyValue) {
		this.proxyValue = proxyValue;
	}
	public String getSndrAccountNumber() {
		return sndrAccountNumber;
	}
	public void setSndrAccountNumber(String sndrAccountNumber) {
		this.sndrAccountNumber = sndrAccountNumber;
	}
	

    
    
}
