package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.BusinessMessageWrap;
import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;
import bifast.outbound.ficredittransfer.ChannelFICreditTransferReq;
import bifast.outbound.paymentstatus.ChannelPaymentStatusRequest;
import bifast.outbound.pojo.CombinedMessage;
import bifast.outbound.reversect.ChannelReverseCreditTransferRequest;

@Component
public class CombineMessageProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CombinedMessage fullMesg = new CombinedMessage();
		
		String msgType = exchange.getMessage().getHeader("req_msgType", String.class);
		String fileName = "";
		
		if (msgType.equals("AccountEnquiry")) {
			ChannelAccountEnquiryReq accEn = exchange.getMessage().getHeader("req_channelReq", ChannelAccountEnquiryReq.class);
			fullMesg.setAccountEnquiryRequest(accEn);

			fileName = "pacs.008.";
		}
		
		else if (msgType.equals("CreditTransfer")) {
			fileName = "pacs.008.";
			ChannelCreditTransferRequest crdtTrnReq = exchange.getMessage().getHeader("req_channelReq", ChannelCreditTransferRequest.class);
			fullMesg.setCreditTransferRequest(crdtTrnReq);			
		}

		else if (msgType.equals("FICreditTransfer")) {
			fileName = "pacs.009.";
			ChannelFICreditTransferReq fiCrdtTrnReq = exchange.getMessage().getHeader("req_channelReq", ChannelFICreditTransferReq.class);
			fullMesg.setFiCreditTransferRequest(fiCrdtTrnReq);			
		}

		else if (msgType.equals("PaymentStatus")) {
			fileName = "pacs.028.";
			ChannelPaymentStatusRequest pymtSts = exchange.getMessage().getHeader("req_channelReq", ChannelPaymentStatusRequest.class);
			fullMesg.setPaymentStatusRequest(pymtSts);
		}

		else if (msgType.equals("ReverseCreditTransfer")) {
			fileName = "pacs.008.";
			ChannelReverseCreditTransferRequest revCT = exchange.getMessage().getHeader("req_channelReq", ChannelReverseCreditTransferRequest.class);
			fullMesg.setReverseCreditTransferRequest(revCT);
		}
		
		BusinessMessage outboundMesg = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);
		BusinessMessageWrap outbMesgWr = new BusinessMessageWrap(outboundMesg);
		BusinessMessage responseMesg = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);
		BusinessMessageWrap inbMesgWr = new BusinessMessageWrap(responseMesg);

		
		fullMesg.setOutboundMessage(outbMesgWr);
		fullMesg.setResponseMessage(inbMesgWr);

		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JaxbAnnotationModule() );
		mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		
		String strMesg = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fullMesg);
		
		exchange.getIn().setBody(strMesg);

		fileName = fileName + outboundMesg.getAppHdr().getBizMsgIdr() + ".arch";
		exchange.getMessage().setHeader("req_fileName", fileName);

	}

}
