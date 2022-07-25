package bifast.outbound;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.pojo.ChannelResponseWrapper;

@Service
public class TestUtils {

	public String serializeBusinessMessage (BusinessMessage bm) throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new JaxbAnnotationModule());
	    mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
	    mapper.setSerializationInclusion(Include.NON_NULL);
		String str = mapper.writeValueAsString(bm);
		return str;
	}

	
	public BusinessMessage deSerializeBusinessMessage (String str) throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new JaxbAnnotationModule());
	    mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
	    mapper.setSerializationInclusion(Include.NON_NULL);
	    BusinessMessage bm = mapper.readValue(str, BusinessMessage.class);
		return bm;
	}

	public String serializeChnlAccountEnquiry (ChnlAccountEnquiryRequestPojo chnlReq) throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new JaxbAnnotationModule());
	    mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
	    mapper.setSerializationInclusion(Include.NON_NULL);
		String str = mapper.writeValueAsString(chnlReq);
		return str;
	}

	public ChannelResponseWrapper deserializeChannelResponse (String str) throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(new JaxbAnnotationModule());
	    mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
	    mapper.setSerializationInclusion(Include.NON_NULL);
	    ChannelResponseWrapper resp = mapper.readValue(str, ChannelResponseWrapper.class);
		return resp;
	}

}
