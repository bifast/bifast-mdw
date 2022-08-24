package bifast.outbound;

import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.repository.ChannelTransactionRepository;

@ActiveProfiles("lcl")
@CamelSpringBootTest
@EnableAutoConfiguration
@SpringBootTest
public class HelloTest {
    @Autowired
    ProducerTemplate template;
	@Autowired private ChannelTransactionRepository channelTransactionRepo;

    @EndpointInject("mock:test")
    MockEndpoint mockEndpoint;

    @Configuration
    static class TestConfig {

        @Bean
        RoutesBuilder route() {
            return new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:test").to("mock:test");
                }
            };
        }
    }

	@WithUserDetails("MB")
    @Test
    public void shouldInjectEndpoint() throws InterruptedException {
    	String str = sampleData1();
        template.sendBody("direct:service", str);
    }
    
	private String sampleData1 () {
		List<ChannelTransaction> lchnt = channelTransactionRepo.findByChannelRefId("TEST220725080728");
		for (ChannelTransaction ct : lchnt) channelTransactionRepo.delete(ct);
		String str = "{\"AccountEnquiryRequest\":{\"RecipientBank\":\"BRINIDJA\",\"CategoryPurpose\":\"02\",\"SenderAccountNumber\":\"02456754\",\"Amount\":\"210000.00\",\"NoRef\":\"TEST220725080728\",\"RecipientAccountNumber\":\"017801018027502\"}}"; 
		return str;
	}


}
