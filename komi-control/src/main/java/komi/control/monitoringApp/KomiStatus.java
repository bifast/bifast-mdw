package komi.control.monitoringApp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KomiStatus {
	
	@JsonProperty("InboundService")
	private String inboundService;
	
	@JsonProperty("OutboundService")
	private String outboundService;
	
	@JsonProperty("IsoAdapter")
	private String isoAdapter;
	
	@JsonProperty("Database")
	private String database;
	
	@JsonProperty("CiConnector")
	private String ciConnector;
	
	@JsonProperty("CoreBankSystem")
	private String coreBankSystem;
	
	public String getInboundService() {
		return inboundService;
	}
	public void setInboundService(String inboundService) {
		this.inboundService = inboundService;
	}
	public String getOutboundService() {
		return outboundService;
	}
	public void setOutboundService(String outboundService) {
		this.outboundService = outboundService;
	}
	public String getIsoAdapter() {
		return isoAdapter;
	}
	public void setIsoAdapter(String isoAdapter) {
		this.isoAdapter = isoAdapter;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getCiConnector() {
		return ciConnector;
	}
	public void setCiConnector(String ciConnector) {
		this.ciConnector = ciConnector;
	}
	public String getCoreBankSystem() {
		return coreBankSystem;
	}
	public void setCoreBankSystem(String coreBankSystem) {
		this.coreBankSystem = coreBankSystem;
	}
	
	
}
