package bifast.outbound.accountcustmrinfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoResponsePojo;
import bifast.outbound.corebank.pojo.AccountCustInfoResponseDTO;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class ACIResponseProc implements Processor{
    @Autowired private StatusReasonRepository statusReasonRepo;
    
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

//  private static Logger logger = LoggerFactory.getLogger(PrepACIRequestProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		
		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
		channelResponseWr.setResponses(new ArrayList<>());

		ChnlAccountCustomerInfoResponsePojo chnlResp = new ChnlAccountCustomerInfoResponsePojo();
		chnlResp.setNoRef(rmw.getRequestId());

		String response = exchange.getProperty("pr_response", String.class);
							
		if (response.equals("ACTC")) {
			AccountCustInfoResponseDTO cbResponse = exchange.getMessage().getBody(AccountCustInfoResponseDTO.class);
			
			channelResponseWr.setResponseCode(cbResponse.getStatus());
			channelResponseWr.setReasonCode(cbResponse.getReason());
			Optional<StatusReason> optStatusReason = statusReasonRepo.findById(cbResponse.getReason());
			if (optStatusReason.isPresent()) {
				String desc = optStatusReason.get().getDescription();
				channelResponseWr.setReasonMessage(desc);
			}	
			chnlResp.setAccountNumber(cbResponse.getAccountNumber());
			chnlResp.setAccountType(cbResponse.getAccountType());
			chnlResp.setDebtorId(cbResponse.getCustomerId());
			chnlResp.setDebtorIdType(cbResponse.getCustomerIdType());
			chnlResp.setDebtorName(cbResponse.getCustomerName());
			chnlResp.setDebtorType(cbResponse.getCustomerType());
			chnlResp.setEmailAddressList(cbResponse.getEmailAddressList());
			chnlResp.setPhoneNumberList(cbResponse.getPhoneNumberList());
			chnlResp.setResidentialStatus(cbResponse.getResidentStatus());
			chnlResp.setTownName(cbResponse.getTownName());
		}

		else {
			channelResponseWr.setResponseCode(exchange.getProperty("pr_response", String.class));
			channelResponseWr.setReasonCode(exchange.getProperty("pr_reason", String.class));

			Optional<StatusReason> oStatusReason = statusReasonRepo.findById(channelResponseWr.getReasonCode());
			if (oStatusReason.isPresent())
				channelResponseWr.setReasonMessage(oStatusReason.get().getDescription());
			else
				channelResponseWr.setReasonMessage("General Error");
		}
		
		channelResponseWr.getResponses().add(chnlResp);
		exchange.getMessage().setBody(channelResponseWr);

	}
}
