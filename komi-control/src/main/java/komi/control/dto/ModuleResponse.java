package komi.control.dto;

public class ModuleResponse {

	private String moduleName;
	private String moduleType;
	private ModuleDetail moduleDetail;
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public ModuleDetail getModuleDetail() {
		return moduleDetail;
	}
	public void setModuleDetail(ModuleDetail moduleDetail) {
		this.moduleDetail = moduleDetail;
	}
	
	
}
