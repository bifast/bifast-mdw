package bifast.outbound.backgrndjob.processor;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import bifast.outbound.backgrndjob.dto.UndefinedCTPojo;

@Component
public class PSFilter {

	public boolean timeIsDue (Exchange exchange) {
		UndefinedCTPojo ct = exchange.getProperty("pr_psrequest", UndefinedCTPojo.class);
		LocalDateTime orgnlTime = ct.getOrgnlDateTime();
		Duration duration = Duration.between(orgnlTime, LocalDateTime.now());
		
		if (ct.getPsCounter()==0) 
			return duration.getSeconds() > 0;
		
		else if (ct.getPsCounter()==1) 
			return duration.getSeconds() > 30;
		
		else if (ct.getPsCounter()==2) 
			return duration.getSeconds() > 90;
		
		else if (ct.getPsCounter()==3) 
			return duration.getSeconds() > 180;
		
		else 
			return duration.getSeconds() > 300;
		
	}
	
	public boolean sttlIsNotFound (Exchange exchange) {
		UndefinedCTPojo ct = exchange.getMessage().getBody(UndefinedCTPojo.class);
		return ct.getPsStatus() == "STTL_NOTFOUND";
	}

	public boolean sttlIsFound (Exchange exchange) {
		UndefinedCTPojo ct = exchange.getMessage().getBody(UndefinedCTPojo.class);
		return ct.getPsStatus() == "STTL_FOUND";
	}

}
