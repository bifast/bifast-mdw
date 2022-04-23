package komi.control.rekon;

import java.math.BigDecimal;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import komi.control.repository.CorebankTransactionRepository;

@Component
public class RekonProcessor implements Processor {
	@Autowired CorebankTransactionRepository corebankRepo;

	
	@Override
	public void process(Exchange exchange) throws Exception {

		String tgl = exchange.getMessage().getHeader("tanggal", String.class);
        StringBuffer sb = new StringBuffer();
        sb.append("NOREF, END2ENDID, NOREK, NAMA, TRANSACTIONTYPE, CREDITAMT, DEBITAMT, COUNTERPTBANK, REASON, RESPONSE, SETTLSTATUS\n");
        
		List<Object[]> o = corebankRepo.getCoreBankTransaction(tgl);
		for (Object[] data : o) {
			RekonDAO rekonData = new RekonDAO();
			String komId = (String) data[0];
			rekonData.setKomiTrnsId(komId);
			rekonData.setEndToEndId((String) data[1]);
			rekonData.setCstmAccountNo((String) data[2] );
			rekonData.setCstmAccountName((String) data[3]);			
			rekonData.setTransactionType((String) data[4]);

			BigDecimal amt = (BigDecimal)data[5];
			if (null != amt)
				rekonData.setCreditAmount(amt.toString());
			
			amt = (BigDecimal)data[6];
			if (null != amt)
				rekonData.setDebitAmount(amt.toString());

			rekonData.setCounterpartBank((String) data[7]);
			if (rekonData.getCounterpartBank().equals("SIHBIDJ1"))
				rekonData.setCounterpartBank((String) data[8]);

			rekonData.setReason((String) data[9]);
			rekonData.setResponse((String) data[10]);
			
			rekonData.setSttlStatus((String) data[11]);
			if (rekonData.getSttlStatus().equals("WAITING"))
				rekonData.setSttlStatus("-");
			else
				rekonData.setSttlStatus("SETTLED");
				
//			sb.append(rekonData);
			sb.append(rekonData.getCsv());
		}
		
		exchange.getMessage().setBody(sb);
	}

}
