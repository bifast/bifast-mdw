package bifast.outbound.corebank.processor;

import java.lang.reflect.Method;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.ResponseMessageCollection;

@Component
public class CbCallFaultProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		Object objException = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Object.class);
		String exceptionClassName = objException.getClass().getName();		
			
		String response = "";
		String reason = "";
		String errMsg = "Check error log";
		
		int statusCode = 500;
		
		try {
			Method getStatusCode = objException.getClass().getMethod("getStatusCode");
			statusCode = (int) getStatusCode.invoke(objException);
		} catch(NoSuchMethodException noMethodE) {}

		if ((exceptionClassName.equals("java.net.SocketTimeoutException")) || 
				(statusCode == 504)) {
			response = "RJCT";
			reason = "U900";
		}
		else {
			response = "RJCT";
			reason = "U901";
		}

		try {
			Method getMessage = objException.getClass().getMethod("getMessage");
			errMsg = (String) getMessage.invoke(objException);
			errMsg = objException.getClass().getSimpleName() + ": " + errMsg;
			if (errMsg.length()>250)
				errMsg = errMsg.substring(0,249);
		}
		catch(NoSuchMethodException noMethodE) {
			errMsg = "Check error log";
		}

		FaultPojo fault = new FaultPojo("CB-ERROR", response, reason);
		fault.setErrorMessage(errMsg);
		exchange.getMessage().setBody(fault);

		ResponseMessageCollection respColl = exchange.getProperty("prop_response_list",ResponseMessageCollection.class);
		respColl.setFault(fault);
		
		exchange.setProperty("pr_response", fault.getResponseCode());
		exchange.setProperty("pr_reason", fault.getReasonCode());
			
	} 				


}
