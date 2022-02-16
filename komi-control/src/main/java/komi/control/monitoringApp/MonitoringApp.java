package komi.control.monitoringApp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonitoringApp {
	
	@JsonProperty("KomiStatus")
	KomiStatus komiStatus;

	public KomiStatus getKomiStatus() {
		return komiStatus;
	}

	public void setKomiStatus(KomiStatus komiStatus) {
		this.komiStatus = komiStatus;
	}
	
	
	
}
