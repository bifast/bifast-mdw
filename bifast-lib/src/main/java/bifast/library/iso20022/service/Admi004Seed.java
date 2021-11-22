package bifast.library.iso20022.service;

import java.util.List;

public class Admi004Seed {

	private String eventCode;
	private List<String> eventParamList;   
	private String eventDesc; 
	private String eventTime;
	
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public List<String> getEventParamList() {
		return eventParamList;
	}
	public void setEventParamList(List<String> eventParamList) {
		this.eventParamList = eventParamList;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	

	
}
