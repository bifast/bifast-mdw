package bifast.inbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.pojo.CombineMessage;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.BusinessMessageWrap;

@Component
public class CombineLogMessageProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CombineMessage combMesg = new CombineMessage();

		BusinessMessage inbMesg = exchange.getMessage().getHeader("rcv_bi", BusinessMessage.class);
		BusinessMessageWrap inbMesgWr = new BusinessMessageWrap(inbMesg);
		
		combMesg.setInboundMessage(inbMesgWr);
		
		if (!(null == exchange.getMessage().getHeader("resp_bi"))) {
			
			BusinessMessage outbMesg = exchange.getMessage().getHeader("resp_bi", BusinessMessage.class);
			BusinessMessageWrap outbMesgWr = new BusinessMessageWrap(outbMesg);

			combMesg.setResponseMessage(outbMesgWr);			
		}
			
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JaxbAnnotationModule() );
		mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);

		String strMesg = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(combMesg);
		
		exchange.getIn().setBody(strMesg);

		String fileName = inbMesg.getAppHdr().getMsgDefIdr().substring(0,8) + "." + inbMesg.getAppHdr().getBizMsgIdr() + ".arch";
		exchange.getMessage().setHeader("rcv_fileName", fileName);

	}

}
