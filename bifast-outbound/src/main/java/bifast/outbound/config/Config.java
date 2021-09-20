package bifast.outbound.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration  // so  Sprint creates bean in application context
@ConfigurationProperties(prefix = "komi")
public class Config {

	private String bankcode;
	private String bicode;
	
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getBicode() {
		return bicode;
	}
	public void setBicode(String bicode) {
		this.bicode = bicode;
	}
	
}
