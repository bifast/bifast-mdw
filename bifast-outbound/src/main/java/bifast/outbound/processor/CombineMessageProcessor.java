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
import bifast.outbound.accountenquiry.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferRequestPojo;
import bifast.outbound.paymentstatus.ChnlPaymentStatusRequestPojo;
import bifast.outbound.pojo.CombinedMessage;
import bifast.outbound.proxyregistration.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.reversect.ChnlReverseCTRequestPojo;

@Component
public class CombineMessageProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CombinedMessage fullMesg = new CombinedMessage();
		
		String msgType = exchange.getMessage().getHeader("rcv_msgType", String.class);
		String fileName = "";
		
		if (msgType.equals("AccountEnquiry")) {
			ChnlAccountEnquiryRequestPojo accEn = exchange.getMessage().getHeader("req_channelReq", ChnlAccountEnquiryRequestPojo.class);
			fullMesg.setAccountEnquiryRequest(accEn);

			fileName = "pacs.008.";
		}
		
		else if (msgType.equals("CreditTransfer")) {
			fileName = "pacs.008.";
			ChnlCreditTransferRequestPojo crdtTrnReq = exchange.getMessage().getHeader("req_channelReq", ChnlCreditTransferRequestPojo.class);
			fullMesg.setCreditTransferRequest(crdtTrnReq);			
		}

		else if (msgType.equals("FICreditTransfer")) {
			fileName = "pacs.009.";
			ChnlFICreditTransferRequestPojo fiCrdtTrnReq = exchange.getMessage().getHeader("req_channelReq", ChnlFICreditTransferRequestPojo.class);
			fullMesg.setFiCreditTransferRequest(fiCrdtTrnReq);			
		}

		else if (msgType.equals("PaymentStatus")) {
			fileName = "pacs.028.";
			ChnlPaymentStatusRequestPojo pymtSts = exchange.getMessage().getHeader("req_channelReq", ChnlPaymentStatusRequestPojo.class);
			fullMesg.setPaymentStatusRequest(pymtSts);
		}

		else if (msgType.equals("ReverseCreditTransfer")) {
			fileName = "pacs.008.";
			ChnlReverseCTRequestPojo revCT = exchange.getMessage().getHeader("req_channelReq", ChnlReverseCTRequestPojo.class);
			fullMesg.setReverseCreditTransferRequest(revCT);
		}

		else if (msgType.equals("prxyrgst")) {
			fileName = "prxy.001.";
			ChnlProxyRegistrationRequestPojo prxReg = exchange.getMessage().getHeader("req_channelReq", ChnlProxyRegistrationRequestPojo.class);
			fullMesg.setProxyRegistrationRequest(prxReg);
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
