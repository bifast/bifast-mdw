package bifast.outbound.corebank;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.exception.PSNotFoundException;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CorebankTransaction;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CorebankTransactionRepository;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class DebitSAFRoute extends RouteBuilder {
	@Autowired DebitReversalRequestProcessor debitReversalRequestProc;
	@Autowired JacksonDataFormatService jdfService;
	@Autowired ChannelTransactionRepository chnlTrnsRepo;
	@Autowired CorebankTransactionRepository cbRepo;
	
	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat chnRequestJdf = jdfService.unwrapRoot(ChnlCreditTransferRequestPojo.class);
		
		onException(PSNotFoundException.class).routeId("dbsaf.onException")
			.handled(true)
			.to("controlbus:route?routeId=komi.saf.debitcb&action=stop&async=true")
		;

		from("sql:select cbt.id cbt_id, cbt.orgnl_chnl_noref, cbt.komi_trns_id, "
				+ "cbt.full_text_request cb_msg, chn.text_message chn_msg "
				+ "from kc_corebank_transaction cbt "
				+ "join kc_channel_transaction chn on chn.komi_trns_id = cbt.komi_trns_id "
				+ "where cbt.transaction_type = 'DebitReversal' "
				+ "and cbt.reason = 'K000' "
				+ "?delay=10000"
				+ "&sendEmptyMessageWhenIdle=true")
		.routeId("komi.saf.debitcb")
		
			.log("[DbSAF] Started.")
			// selesai dan matikan router jika tidak ada lagi SAF
			.filter().simple("${body} == null")
				.throwException(PSNotFoundException.class, "Debit SAF Selesai.")			  
			.end()	

			.setHeader("debit_qryresult", simple("${body}"))
//			.setHeader("debit_komi_id", simple("${body[KOMI_TRNS_ID]}"))
			
			.setBody(simple("${header.debit_qryresult[CHN_MSG]}"))
			.unmarshal().base64().unmarshal().zipDeflater()
			.unmarshal(chnRequestJdf)
			.setHeader("debit_chreq", simple("${body}"))
			
			
			.log("[DbSAF:${header.debit_qryresult[ORGNL_CHNL_NOREF]}]")

			// siapkan data debit account
			.process(new Processor() {
				@Override
				public void process(Exchange exchange) throws Exception {
					ChnlCreditTransferRequestPojo chnReq = exchange.getMessage().getHeader("debit_chreq", ChnlCreditTransferRequestPojo.class);
					RequestMessageWrapper rmw = new RequestMessageWrapper();
					rmw.setChnlCreditTransferRequest(chnReq);
					exchange.getMessage().setHeader("request_message_list", rmw);
				}
			})
			
			// kirim ke corebank
			.to("seda:debitreversal")

			// evaluasi hasilnya
			// jika sukses update status channel_transaction = 'SUCCESS'
			// jika gagal update kolom retry, lastupdate
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					HashMap<String, Object> arr = exchange.getMessage().getHeader("debit_qryresult", HashMap.class);
					Long cbTrnsId = Long.valueOf((String) arr.get("cbt_id"));
					String komiId = String.valueOf(arr.get("komi_trns_id"));
					Object objResponse = exchange.getMessage().getBody(Object.class);

					String response = "ACTC";
					String reason = "U000";
					CorebankTransaction cbTrns = cbRepo.findById(cbTrnsId).orElse(new CorebankTransaction());
					if (objResponse.getClass().getSimpleName().equals("FaultPojo")) {
						FaultPojo fault = (FaultPojo)objResponse;
						response = fault.getResponseCode();
						reason = fault.getReasonCode();
					}
					
					cbTrns.setRetryCounter(cbTrns.getRetryCounter()+1);
					cbTrns.setUpdateTime(LocalDateTime.now());
					cbTrns.setResponse(response);
					cbTrns.setReason(reason);
					cbRepo.save(cbTrns);
					
					if (response.equals("ACTC")){
						ChannelTransaction chnlTrns = chnlTrnsRepo.findByKomiTrnsId(komiId).orElse(new ChannelTransaction());
						chnlTrns.setCallStatus("Success");
						chnlTrns.setResponseCode("ACTC");
						Long dur = ChronoUnit.MILLIS.between(chnlTrns.getRequestTime(), LocalDateTime.now());
						chnlTrns.setElapsedTime(dur);
						chnlTrnsRepo.save(chnlTrns);
					}
				}
			})
			
//			.throwException(PSNotFoundException.class, "Debit SAF Selesai.")			  

		;
	}

}
