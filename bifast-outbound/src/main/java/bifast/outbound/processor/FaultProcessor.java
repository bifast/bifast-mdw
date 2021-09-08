package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.model.FaultClass;
import bifast.library.repository.FaultClassRepository;
import bifast.outbound.pojo.ChannelFaultResponse;

@Component
public class FaultProcessor implements Processor {

	@Autowired 
	private FaultClassRepository faultClassRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		System.out.println("start FaultProcessor");
		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

		FaultClass fault = faultClassRepo.findByExceptionClass(e.getClass().getName()).orElse(new FaultClass());
		
		String reason = "General error";
		if (!(null==fault.getId()))
			reason = fault.getReason();
				
		ChannelFaultResponse reject = new ChannelFaultResponse();
		reject.setReason(reason);
		
		String hdrLocation = exchange.getMessage().getHeader("hdr_errorlocation", String.class);
		
		if (null==hdrLocation)
			reject.setLocation("komi-outbound");
		else
			reject.setLocation(hdrLocation);
		
		reject.setDescription(e.getMessage());

//		Object chnRequest = exchange.getMessage().getHeader("hdr_channelRequest", Object.class);
//		String refId;
//				
//			Method getOrignReffId = chnRequest.getClass().getMethod("getChannelRefId", null);
//			if (!(null==getOrignReffId)) {
//				refId = (String) getOrignReffId.invoke(chnRequest, null);
//				reject.setChannelRefId(refId);
//			}
		
		System.out.println("Cek 453");
				
		exchange.getMessage().setHeader("hdr_fault", reject);
		exchange.getMessage().setBody(reject, ChannelFaultResponse.class);
	}

}
