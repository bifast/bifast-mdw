package bifast.mock.iso20022.service;

import bifast.mock.iso20022.prxy003.ProxyLookUpType1Code;

public class Proxy003Seed {
	
	private String trnType;
	
	private ProxyLookUpType1Code lookupType;
	private String id;
	private String proxyType;
	private String proxyValue;
	
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
	

    
    
}
