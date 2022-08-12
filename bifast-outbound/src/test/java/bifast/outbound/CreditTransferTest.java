package bifast.outbound;

import java.util.List;
import java.util.Optional;

import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CorebankTransaction;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CorebankTransactionRepository;
import bifast.outbound.repository.CreditTransferRepository;

@ActiveProfiles("lcl")
@CamelSpringBootTest
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CreditTransferTest {
	@Autowired private TestUtils utilService;
	@Autowired ProducerTemplate producerTemplate;
	@Autowired CorebankTransactionRepository cbRepo;
	@Autowired CreditTransferRepository ctRepo;
	@Autowired ChannelTransactionRepository channelTrnsRepo;

	@WithUserDetails("MB")
	@Test
    @Order(1)    
	public void postCT() throws Exception {
		String str = prepareData1();
		
		Object ret = producerTemplate.sendBody("direct:service", ExchangePattern.InOut, str);

		Assertions.assertDoesNotThrow(() -> { utilService.deserializeChannelResponse((String) ret); });
		
		ChannelResponseWrapper chnlResp = utilService.deserializeChannelResponse((String) ret);
		Assertions.assertEquals("ACTC", chnlResp.getResponseCode());
		Assertions.assertEquals("U000", chnlResp.getReasonCode());

		List<ChannelTransaction> lct = channelTrnsRepo.findByChannelRefId("MOB09097942");
		Assertions.assertEquals(1, lct.size());
		ChannelTransaction chnlTrns = lct.get(0);
		Assertions.assertEquals("SUCCESS", chnlTrns.getCallStatus());
		Assertions.assertEquals("ACTC", chnlTrns.getResponseCode());
		Assertions.assertEquals("CTReq", chnlTrns.getMsgName());
		Assertions.assertNotNull(chnlTrns.getTextMessage());

		Optional<CreditTransfer> oct = ctRepo.findByKomiTrnsId(chnlTrns.getKomiTrnsId());
		Assertions.assertFalse(oct.isEmpty(), "Table CreditTransfer kosong");
		
		CreditTransfer ct = oct.get();
		Assertions.assertEquals("U000", ct.getReasonCode());
		Assertions.assertEquals("DONE", ct.getCbStatus());
		
		List<CorebankTransaction> lcb = cbRepo.findAllByTransactionTypeAndKomiTrnsId("Debit", chnlTrns.getKomiTrnsId());
		Assertions.assertEquals(1, lcb.size());

	}

	@WithUserDetails("MB")
	@Test
    @Order(2)    
	public void postCTRjct() throws Exception {
		String str = prepareData2();
		
		Object ret = producerTemplate.sendBody("direct:service", ExchangePattern.InOut, str);

		Assertions.assertDoesNotThrow(() -> { utilService.deserializeChannelResponse((String) ret);	});
		
		ChannelResponseWrapper chnlResp = utilService.deserializeChannelResponse((String) ret);
		Assertions.assertEquals("RJCT", chnlResp.getResponseCode());
		Assertions.assertEquals("U102", chnlResp.getReasonCode());

		List<ChannelTransaction> lct = channelTrnsRepo.findByChannelRefId("MOB09097943");
		Assertions.assertEquals(1, lct.size());
		ChannelTransaction chnlTrns = lct.get(0);
		Assertions.assertEquals("SUCCESS", chnlTrns.getCallStatus());
		Assertions.assertEquals("RJCT", chnlTrns.getResponseCode());
		Assertions.assertEquals("CTReq", chnlTrns.getMsgName());
		Assertions.assertNotNull(chnlTrns.getTextMessage());

		Optional<CreditTransfer> oct = ctRepo.findByKomiTrnsId(chnlTrns.getKomiTrnsId());
		Assertions.assertFalse(oct.isEmpty(), "Table CreditTransfer kosong");
		
		CreditTransfer ct = oct.get();
		Assertions.assertEquals("RJCT", ct.getResponseCode());
		Assertions.assertEquals("U102", ct.getReasonCode());
		Assertions.assertEquals("DONE", ct.getCbStatus());
		
		List<CorebankTransaction> lcb = cbRepo.findAllByTransactionTypeAndKomiTrnsId("Debit", chnlTrns.getKomiTrnsId());
		Assertions.assertEquals(1, lcb.size());
		List<CorebankTransaction> lcb2 = cbRepo.findAllByTransactionTypeAndKomiTrnsId("DebitReversal", chnlTrns.getKomiTrnsId());
		Assertions.assertEquals(1, lcb2.size());

	}

	private String prepareData1() {
		List<ChannelTransaction> lChnlTrns = channelTrnsRepo.findByChannelRefId("MOB09097942");
		for (ChannelTransaction chnlt : lChnlTrns) {
			Optional<CreditTransfer> oct = ctRepo.findByKomiTrnsId(chnlt.getKomiTrnsId());
			if (oct.isPresent()) 
				ctRepo.delete(oct.get());
			channelTrnsRepo.delete(chnlt);
		}
		String str = "{\"CreditTransferRequest\":{\"NoRef\":\"MOB09097942\",\"CategoryPurpose\":\"99\",\"TerminalId\":\"MOBILE0001\",\"RecipientBank\":\"CENAIDJA\",\"FeeTransfer\":\"2500.00\",\"DebtorId\":\"3174080609620004\",\"Amount\":\"45000.00\",\"CreditorName\":\"ANDRIAN S\",\"CreditorProxyType\":\"\",\"CreditorProxyId\":\"\",\"CreditorAccountType\":\"CACC\",\"CreditorAccountNumber\":\"112211333\",\"DebtorName\":\"ROSYID HIDAYAT\",\"DebtorAccountType\":\"SVGS\",\"DebtorAccountNumber\":\"2012107552576\",\"DebtorResidentialStatus\":\"01\",\"DebtorType\":\"01\",\"DebtorTownName\":\"0394\",\"PaymentInformation\":\"\"}}";
		return str;
	}
	
	private String prepareData2() {
		List<ChannelTransaction> lChnlTrns = channelTrnsRepo.findByChannelRefId("MOB09097943");
		for (ChannelTransaction chnlt : lChnlTrns) {
			Optional<CreditTransfer> oct = ctRepo.findByKomiTrnsId(chnlt.getKomiTrnsId());
			if (oct.isPresent()) 
				ctRepo.delete(oct.get());
			channelTrnsRepo.delete(chnlt);
		}
		String str = "{\"CreditTransferRequest\":{\"NoRef\":\"MOB09097943\",\"CategoryPurpose\":\"99\",\"TerminalId\":\"MOBILE0001\",\"RecipientBank\":\"BRINIDJA\",\"FeeTransfer\":\"2500.00\",\"DebtorId\":\"3174080609620004\",\"Amount\":\"45000.00\",\"CreditorName\":\"ANDRIAN S\",\"CreditorProxyType\":\"\",\"CreditorProxyId\":\"\",\"CreditorAccountType\":\"CACC\",\"CreditorAccountNumber\":\"112211777\",\"DebtorName\":\"ROSYID HIDAYAT\",\"DebtorAccountType\":\"SVGS\",\"DebtorAccountNumber\":\"2012107552576\",\"DebtorResidentialStatus\":\"01\",\"DebtorType\":\"01\",\"DebtorTownName\":\"0394\",\"PaymentInformation\":\"\"}}";
		return str;
	}
}
