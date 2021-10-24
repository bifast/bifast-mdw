package bifast.outbound.processor;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import bifast.outbound.model.FaultClass;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.repository.FaultClassRepository;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class ExceptionProcessor implements Processor {
	@Autowired
	private FaultClassRepository faultClassRepo;
	@Autowired
	private StatusReasonRepository statusReasonRepo;
	
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void process(Exchange exchange) throws Exception {
		Object exception = (Object) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
		
		String faultClassName = exception.getClass().getName();
		FaultClass fc = faultClassRepo.findByExceptionClass(faultClassName).orElse(new FaultClass());
		
		String reason = "";
		try {
			Method mth = exception.getClass().getMethod("getMessage");
			reason = (String) mth.invoke(exception);
		}
		catch(NoSuchMethodException nsme) {
			reason = (statusReasonRepo.findById(fc.getReason()).orElse(new StatusReason())).getDescription();
		}
			
				
		ChannelResponseWrapper responseWr = new ChannelResponseWrapper();
		responseWr.setResponseCode("KSTS");
		responseWr.setReasonCode(fc.getReason());
		responseWr.setReasonMessage(reason);
		responseWr.setDate(LocalDateTime.now().format(dateformatter));
		responseWr.setTime(LocalDateTime.now().format(timeformatter));
		responseWr.setResponses(new ArrayList<>());

		exchange.getMessage().setBody(responseWr);
	}

}
