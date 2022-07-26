package bifast.outbound;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.pojo.ChannelResponseWrapper;

@CamelSpringBootTest
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AccountEnquiryTest {
	@Autowired private TestUtils utilService;
	@Autowired ProducerTemplate producerTemplate;

    @WithMockUser(username="MB")
	@Test
    @Order(1)    
	public void postAE() throws Exception {
		ChnlAccountEnquiryRequestPojo aeReq = buildAERequest();
		String str = utilService.serializeChnlAccountEnquiry(aeReq);
		
		Exchange ex = new DefaultExchange(producerTemplate.getCamelContext());
		ex.getMessage().setHeader("Content-Type", "application/json");
		ex.getMessage().setHeader("Authorization", "Basic MB:mb2022");
		ex.getMessage().setBody(str);

		Exchange ex1 = producerTemplate.send("direct:service", ex);
		System.out.println(ex1.getMessage().getBody().getClass().getSimpleName());
		str = ex1.getMessage().getBody(String.class);
		
//		Object ret = producerTemplate.sendBody("direct:service", ExchangePattern.InOut, str);
//		System.out.println(ret.getClass().getSimpleName());

		System.out.println(str);
		ChannelResponseWrapper chnlResp = utilService.deserializeChannelResponse((String) str);

		Assertions.assertInstanceOf(String.class, str);

//		Assertions.assertInstanceOf(ChannelResponseWrapper.class, chnlResp);
//		Assertions.assertNotNull(bm.getDocument().getFiToFIPmtStsRpt());
	}


	private ChnlAccountEnquiryRequestPojo buildAERequest()  {
		ChnlAccountEnquiryRequestPojo aeRequest = new ChnlAccountEnquiryRequestPojo();
		aeRequest.setChannelRefId("TEST220725080728");
		aeRequest.setCategoryPurpose("01");
		aeRequest.setSenderAccountNumber("02456754");
		aeRequest.setAmount("200000"); 
		aeRequest.setRecptBank("BRINIDJA"); 
		aeRequest.setCreditorAccountNumber("017801018027502") ; 
		aeRequest.setProxyId(""); 
		aeRequest.setProxyType(""); 

		return aeRequest;
	}

}
