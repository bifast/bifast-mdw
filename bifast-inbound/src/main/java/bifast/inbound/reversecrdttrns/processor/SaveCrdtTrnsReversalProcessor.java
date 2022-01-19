package bifast.inbound.reversecrdttrns.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;

@Component
public class SaveCrdtTrnsReversalProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		FlatPacs008Pojo flatReq = (FlatPacs008Pojo)processData.getBiRequestFlat();

		CreditTransfer ct = new CreditTransfer();
		ct.setKomiTrnsId(processData.getKomiTrnsId());

	}

}
