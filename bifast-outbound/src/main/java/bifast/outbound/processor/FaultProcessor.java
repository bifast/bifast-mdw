package bifast.outbound.processor;

import java.lang.reflect.Method;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.FaultClass;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.repository.FaultClassRepository;

@Component
public class FaultProcessor implements Processor {
	@Autowired 
	private FaultClassRepository faultClassRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		Object objException = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Object.class);
		
		int statusCode = 500;
		exchange.getMessage().setHeader("hdr_error_status", "ERROR-CIHUB");
		
		String exceptionClassName = objException.getClass().getSimpleName();
		System.out.println("Exception caught: " + exceptionClassName);
		
		try {
			Method getStatusCode = objException.getClass().getMethod("getStatusCode");
			statusCode = (int) getStatusCode.invoke(objException);
		} catch(NoSuchMethodException noMethodE) {}
		
		if (exceptionClassName.equals("SocketTimeoutException"))
			statusCode = 504;
		
		FaultClass faultClass = faultClassRepo.findByExceptionClass(objException.getClass().getName()).orElse(new FaultClass());

		String reason = "General error.";
		try {
			Method mtdGetCause = objException.getClass().getMethod("getCause");
			Object objCause = mtdGetCause.invoke(objException);
			if (null == objCause) 
				reason = objException.getClass().getName();
			else
				reason = objCause.getClass().getName();
		}
		catch(NoSuchMethodException nsme) {
			reason = objException.getClass().getName();
		}
		
		String description = "Check error log";
		try {
			Method getMessage = objException.getClass().getMethod("getMessage");
			description = (String) getMessage.invoke(objException);
		}
		catch(NoSuchMethodException noMethodE) {
			if (null != faultClass.getReason())
				description = faultClass.getReason();
		}
		
		String chRefId = exchange.getMessage().getHeader("hdr_chnlRefId", String.class);

		ChnlFailureResponsePojo fault = new ChnlFailureResponsePojo();
		fault.setReferenceId(chRefId);
		if (statusCode == 504) 
			fault.setErrorCode("TIMEOUT-CIHUB");
		else
			fault.setErrorCode("ERROR-KM");

		fault.getErrorCode();
		fault.setReason(reason);
		fault.setDescription(description);
		
		exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, statusCode);
		
		if (statusCode == 504) 
			exchange.getMessage().setHeader("hdr_error_status", "TIMEOUT-CIHUB");
		else
			exchange.getMessage().setHeader("hdr_error_status", "ERROR-CIHUB");
			
		exchange.getMessage().setBody(fault, ChnlFailureResponsePojo.class);

	}

}
