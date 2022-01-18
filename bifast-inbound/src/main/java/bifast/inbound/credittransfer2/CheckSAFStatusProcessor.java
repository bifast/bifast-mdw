package bifast.inbound.credittransfer2;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.repository.CreditTransferRepository;

@Component
public class CheckSAFStatusProcessor implements Processor{
	@Autowired private CreditTransferRepository ctRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		FlatPacs008Pojo flat = (FlatPacs008Pojo) processData.getBiRequestFlat();
		
		String saf = "NO";
		String cbSts = "";
		if ((null != flat.getCpyDplct()) && (flat.getCpyDplct().equals("DUPL")))
			saf =  "YES";
		
		if (saf.equals("YES")) {
			saf = "NEW";
			List<CreditTransfer> lCT = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flat.getBizMsgIdr());
			for (CreditTransfer ct : lCT) {
				if (ct.getReasonCode().equals("U149"))
					continue;
				if (ct.getResponseCode().equals("ACTC")) {
					cbSts = "ACTC";
					saf = "OLD";
					break;
				}
				else {
					cbSts = "RJCT";
					saf = "OLD";
				}
			}
		}				
		exchange.getMessage().setHeader("ct_saf", saf);
		exchange.getMessage().setHeader("ct_cbsts", cbSts);
		exchange.getMessage().setBody(flat);
				
	}

	
}
