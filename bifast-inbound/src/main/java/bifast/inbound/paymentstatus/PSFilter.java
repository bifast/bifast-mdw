package bifast.inbound.paymentstatus;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class PSFilter {

	public boolean timeIsDue (Exchange exchange) {
		CTQryDTO ct = exchange.getProperty("pr_psrequest", CTQryDTO.class);
		LocalDateTime orgnlTime = ct.getOrgnlDateTime();
		Duration duration = Duration.between(orgnlTime, LocalDateTime.now());
		
		if (ct.getPsCounter()==0) 
			return duration.getSeconds() > 60;
		
		else if (ct.getPsCounter()==1) 
			return duration.getSeconds() > 180;
		
		else if (ct.getPsCounter()==2) 
			return duration.getSeconds() > 360;
		
		else if (ct.getPsCounter()==3) 
			return duration.getSeconds() > 600;
		
		else 
			return duration.getSeconds() > 900;
		
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
