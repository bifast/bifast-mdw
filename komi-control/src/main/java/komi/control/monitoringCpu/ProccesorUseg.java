package komi.control.monitoringCpu;

import java.util.List;

public class ProccesorUseg {
	
	private String name;
	private String description;
	List<Measurements> measurements;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Measurements> getMeasurements() {
		return measurements;
	}
	public void setMeasurements(List<Measurements> measurements) {
		this.measurements = measurements;
	}

}
