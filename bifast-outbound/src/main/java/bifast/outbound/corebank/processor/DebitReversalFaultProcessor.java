package bifast.outbound.corebank.processor;

import java.lang.reflect.Method;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.DebitReversalResponsePojo;
import bifast.outbound.model.FaultClass;
import bifast.outbound.model.StatusReason;
import bifast.outbound.repository.FaultClassRepository;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class DebitReversalFaultProcessor implements Processor{
	@Autowired private FaultClassRepository faultClassRepo;
	@Autowired private StatusReasonRepository statusReasonRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Object objException = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Object.class);
		String exceptionClassName = objException.getClass().getName();
		Optional<FaultClass> oFaultClass = faultClassRepo.findByExceptionClass(exceptionClassName);

		int statusCode = 500;
		try {
			Method getStatusCode = objException.getClass().getMethod("getStatusCode");
			statusCode = (int) getStatusCode.invoke(objException);
		} catch(NoSuchMethodException noMethodE) {}

		DebitReversalResponsePojo resp = new DebitReversalResponsePojo();
//		resp.setNoRef(null);
//		resp.setAccountNumber(null);			

		if (statusCode == 504) {
			resp.setStatus("KSTS");
			resp.setReason("K000");
			resp.setAdditionalInfo(statusReasonRepo.findById("K000").orElse(new StatusReason()).getDescription());
		}

		else if (oFaultClass.isPresent()) {
			resp.setStatus("KSTS");
			resp.setReason (oFaultClass.get().getReason());
			resp.setAdditionalInfo(statusReasonRepo.findById(oFaultClass.get().getReason()).orElse(new StatusReason()).getDescription());

		}
		else {
			resp.setStatus("RJCT");
			resp.setReason("U220");
			resp.setAdditionalInfo(statusReasonRepo.findById("U220").orElse(new StatusReason()).getDescription());
		}

		exchange.getMessage().setBody(resp, DebitReversalResponsePojo.class);

	}

}
