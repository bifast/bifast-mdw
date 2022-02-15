package bifast.outbound.corebank;

import java.lang.reflect.Method;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.FaultClass;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.repository.FaultClassRepository;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class CbFaultExceptionProcessor implements Processor {
	@Autowired 
	private FaultClassRepository faultClassRepo;
	@Autowired StatusReasonRepository statusReasonRepo;

	private static Logger logger = LoggerFactory.getLogger(CbFaultExceptionProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		Object objException = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Object.class);
		String exceptionClassName = objException.getClass().getName();
		Optional<FaultClass> oFaultClass = faultClassRepo.findByExceptionClass(exceptionClassName);
			
		FaultPojo fault = new FaultPojo();
		
		fault.setLocation("COREBANK");
	
		ResponseMessageCollection responseCol = exchange.getProperty("prop_response_list", ResponseMessageCollection.class);
		
		System.out.println(responseCol.getCallStatus());
		logger.debug("response: " + responseCol.getCallStatus());
		
		int statusCode = 500;
		fault.setCallStatus("ERROR-CB");

		try {
			Method getStatusCode = objException.getClass().getMethod("getStatusCode");
			statusCode = (int) getStatusCode.invoke(objException);
		} catch(NoSuchMethodException noMethodE) {}
		
		if (exceptionClassName.equals("java.net.SocketTimeoutException"))
			fault.setCallStatus("TIMEOUT");
		else if (statusCode == 504) {
			fault.setCallStatus("TIMEOUT");
		}
		else 
			fault.setCallStatus("ERROR");

		String description = "Check error log";
		try {
			Method getMessage = objException.getClass().getMethod("getMessage");
			description = (String) getMessage.invoke(objException);
			description = objException.getClass().getSimpleName() + ": " + description;
			if (description.length()>250)
				description = description.substring(0,249);
		}
		catch(NoSuchMethodException noMethodE) {
			description = "Check error log";
		}
		fault.setErrorMessage(description);

		if (statusCode == 504) {
			fault.setResponseCode("KSTS");
			fault.setReasonCode("K000"); 
			fault.setReasonMessage(statusReasonRepo.findById("K000").orElse(new StatusReason()).getDescription());
		}
		else if (oFaultClass.isPresent()) {
			
			fault.setResponseCode("KSTS");
			String reasonCode = oFaultClass.get().getReason();
			fault.setReasonCode(reasonCode);
			fault.setReasonMessage(statusReasonRepo.findById(reasonCode).orElse(new StatusReason()).getDescription());
		}
		else {
			fault.setResponseCode("RJCT");
			fault.setReasonCode("U220");
			fault.setReasonCode(statusReasonRepo.findById("U220").orElse(new StatusReason()).getDescription());
		}


		responseCol.setFault(fault);
		responseCol.setLastError(description);
		exchange.setProperty("prop_response_list", responseCol);

		exchange.getMessage().setBody(fault, FaultPojo.class);
	}

}
