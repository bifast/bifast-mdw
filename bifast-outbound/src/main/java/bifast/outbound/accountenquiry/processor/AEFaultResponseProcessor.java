package bifast.outbound.accountenquiry.processor;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.FaultClass;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.repository.FaultClassRepository;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class AEFaultResponseProcessor implements Processor {
	@Autowired private FaultClassRepository faultClassRepo;
	@Autowired private StatusReasonRepository statusReasonRepo;
	
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void process(Exchange exchange) throws Exception {
		Object exception = (Object) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);


		String faultClassName = exception.getClass().getName();
		FaultClass fc = faultClassRepo.findByExceptionClass(faultClassName).orElse(new FaultClass());
		
//		System.out.println("faultClass: " + faultClassName);
//		System.out.println("REASON: " + fc.getReason());
		
		String reason = "";
		try {
			Method mth = exception.getClass().getMethod("getMessage");
			reason = (String) mth.invoke(exception);
		}
		catch(NoSuchMethodException nsme) {
			reason = (statusReasonRepo.findById(fc.getReason()).orElse(new StatusReason())).getDescription();
		}
		if (reason.length()> 140)
			reason = reason.substring(0, 139);
				
		ChannelResponseWrapper responseWr = new ChannelResponseWrapper();
		responseWr.setResponseCode("KSTS");
		responseWr.setReasonCode(fc.getReason());
		responseWr.setReasonMessage(reason);
		responseWr.setDate(LocalDateTime.now().format(dateformatter));
		responseWr.setTime(LocalDateTime.now().format(timeformatter));
		responseWr.setResponses(new ArrayList<>());

		exchange.getMessage().setBody(responseWr);

		ResponseMessageCollection respColl = exchange.getProperty("prop_response_list",ResponseMessageCollection.class );
		FaultPojo fault = new FaultPojo();
		fault.setCallStatus("REJECT");
		fault.setErrorMessage(fc.getReason() + ": " + reason);
		fault.setResponseCode("KSTS");
		respColl.setFault(fault);
		
		exchange.setProperty("prop_response_list", respColl);
		
	}

}
