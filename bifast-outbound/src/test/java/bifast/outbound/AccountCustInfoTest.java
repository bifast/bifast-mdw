package bifast.outbound;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.camel.ExchangePattern;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bifast.outbound.accountcustmrinfo.CbAccountInfoRoute;

public class AccountCustInfoTest extends CamelTestSupport {
	
    @Test
    public void testMock() throws Exception {
        getMockEndpoint("mock:result").expectedBodiesReceived("Hello World");

        Object obj = template.sendBody("direct:accinfo", ExchangePattern.InOut, "Hello World");
        System.out.println((String) obj);
        
        assertMockEndpointsSatisfied();
    }

    



	@Override
	public void setUp() throws Exception {
		super.setUp();

		ObjectMapper objectmapper = new ObjectMapper();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        InputStream stream = loader.getResourceAsStream("test.json");
//        testJson = new JSONObject(objectmapper.readValue(stream, Map.class));

	}





	@Override
    protected RoutesBuilder createRouteBuilder() {
		return new CbAccountInfoRoute();
//        return new RouteBuilder() {
//            @Override
//            public void configure() {
//                from("direct:accinfo").log("HAHAHAHA -> ${body}").to("mock:result");
//            }
//        };
    }

}


