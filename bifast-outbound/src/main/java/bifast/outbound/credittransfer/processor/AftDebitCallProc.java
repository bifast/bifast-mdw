package bifast.outbound.credittransfer.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferResponsePojo;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlRequestWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.StatusReasonRepository;
import bifast.outbound.service.CallRouteService;

@Component
public class AftDebitCallProc implements Processor{
	@Autowired private StatusReasonRepository statusReasonRepo;
	@Autowired private CallRouteService routeService;

    private static Logger logger = LoggerFactory.getLogger(AftDebitCallProc.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        Object body = exchange.getMessage().getBody(Object.class);
        String bodyClass = body.getClass().getSimpleName();
        RequestMessageWrapper requestWrapper = exchange.getProperty("prop_request_list", RequestMessageWrapper.class )

        if (bodyClass.equals("FaultPojo")) {
            //
            logger.debug("["+requestWrapper.getMsgName() + ":" + requestWrapper.getRequestId() + "] check status-1: " + body);

            FaultPojo fault = (FaultPojo) body;
            if (fault.getReasonCode().equals("U900")) {
                logger.debug("["+requestWrapper.getMsgName() + ":" + requestWrapper.getRequestId() + "] akan debitreversal.");
                routeService.callRoute(exchange, "direct:debitreversal");
            }

            ChannelResponseWrapper response = debitRejectResponse (exchange);
            exchange.getMessage().setBody(response);
            routeService.callRoute(exchange, "seda:logportal?exchangePattern=InOnly");
        }

    }
    
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

    public ChannelResponseWrapper debitRejectResponse (Exchange exchange) {
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);		
		ChnlCreditTransferRequestPojo chnlReq = rmw.getChnlCreditTransferRequest();
		
		ChnlCreditTransferResponsePojo resp = new ChnlCreditTransferResponsePojo();
		resp.setOrignReffId(chnlReq.getChannelRefId());
		resp.setAccountNumber(chnlReq.getCrdtAccountNo());

		FaultPojo fault = exchange.getMessage().getBody(FaultPojo.class);
				
		ChannelResponseWrapper responseWr = new ChannelResponseWrapper();
		responseWr.setResponseCode(fault.getResponseCode());
		responseWr.setReasonCode(fault.getReasonCode());
		
		String reason = (statusReasonRepo.findById(fault.getReasonCode()).orElse(new StatusReason())).getDescription();
		
		responseWr.setReasonMessage(reason);
		responseWr.setDate(LocalDateTime.now().format(dateformatter));
		responseWr.setTime(LocalDateTime.now().format(timeformatter));
		responseWr.setResponses(new ArrayList<>());
		
		responseWr.getResponses().add(resp);

		exchange.getMessage().setBody(responseWr);

        return responseWr;
    }
}
