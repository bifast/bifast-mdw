package bifast.outbound.proxyregistration.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;
import bifast.outbound.service.FlattenIsoMessageService;

@Component
public class ProxyResolutionFlatResponseProcessor implements Processor {

    @Autowired
    private FlattenIsoMessageService flattenBusinessMessage;

	@Override
	public void process(Exchange exchange) throws Exception {

		Object objBm = exchange.getMessage().getBody(Object.class);
		String className = objBm.getClass().getSimpleName();
		
		if (className.equals("BusinessMessage")) {
			
			BusinessMessage bm = (BusinessMessage)objBm;
			FlatPrxy004Pojo prxRsltResponse = flattenBusinessMessage.flatteningPrxy004(bm);
			exchange.getIn().setBody(prxRsltResponse);
		}
		

	
	}

}
