package bifast.mock.processor;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class PaymentStatusResponseProcessor implements Processor{


	@Override
	public void process(Exchange exchange) throws Exception {

		String str = "{\n"
				+ "  \"BusMsg\" : {\n"
				+ "    \"AppHdr\" : {\n"
				+ "      \"Fr\" : {\n"
				+ "        \"Fiid\" : {\n"
				+ "          \"FinInstnId\" : {\n"
				+ "            \"Othr\" : {\n"
				+ "              \"Id\" : \"FASTIDJA\"\n"
				+ "            }\n"
				+ "          }\n"
				+ "        }\n"
				+ "      },\n"
				+ "      \"To\" : {\n"
				+ "        \"Fiid\" : {\n"
				+ "          \"FinInstnId\" : {\n"
				+ "            \"Othr\" : {\n"
				+ "              \"Id\" : \"INDOIDJA\"\n"
				+ "            }\n"
				+ "          }\n"
				+ "        }\n"
				+ "      },\n"
				+ "      \"BizMsgIdr\" : \"20210301FASTIDJA010HRB12345678\",\n"
				+ "      \"MsgDefIdr\" : \"pacs.002.001.10\",\n"
				+ "      \"BizSvc\" : \"CTRESPONSE\",\n"
				+ "      \"CreDt\" : \"2021-03-01T20:00:00\"\n"
				+ "    },\n"
				+ "    \"Document\" : {\n"
				+ "      \"FitoFIPmtStsRpt\" : {\n"
				+ "        \"GrpHdr\" : {\n"
				+ "          \"MsgId\" : \"20210301FASTIDJA01012345678\",\n"
				+ "          \"CreDtTm\" : \"2021-03-01T19:00:00\"\n"
				+ "        },\n"
				+ "        \"OrgnlGrpInfAndSts\" : [ {\n"
				+ "          \"OrgnlMsgId\" : \"20210301INDOIDJA01012345678\",\n"
				+ "          \"OrgnlMsgNmId\" : \"pacs.008.001.08\"\n"
				+ "        } ],\n"
				+ "        \"TxInfAndSts\" : [ {\n"
				+ "          \"OrgnlEndToEndId\" : \"20210301INDOIDJA010ORB12345678\",\n"
				+ "          \"OrgnlTxId\" : \"20210301INDOIDJA01012345678\",\n"
				+ "          \"TxSts\" : \"ACTC\",\n"
				+ "          \"StsRsnInf\" : [ {\n"
				+ "            \"Rsn\" : {\n"
				+ "              \"Prtry\" : \"U000\"\n"
				+ "            },\n"
				+ "            \"AddtlInf\" : [ \"O 1234\" ]\n"
				+ "          } ],\n"
				+ "          \"OrgnlTxRef\" : {\n"
				+ "            \"IntrBkSttlmDt\" : \"2021-03-01T00:00:00\",\n"
				+ "            \"Cdtr\" : {\n"
				+ "              \"Pty\" : {\n"
				+ "                \"Nm\" : \"JOHN SMITH\"\n"
				+ "              }\n"
				+ "            }\n"
				+ "          },\n"
				+ "          \"SplmtryData\" : [ {\n"
				+ "            \"Envlp\" : {\n"
				+ "              \"Cdtr\" : {\n"
				+ "                \"Tp\" : \"01\",\n"
				+ "                \"Id\" : \"0102030405060708\",\n"
				+ "                \"RsdntSts\" : \"01\",\n"
				+ "                \"TwnNm\" : \"0300\"\n"
				+ "              }\n"
				+ "            }\n"
				+ "          } ]\n"
				+ "        } ]\n"
				+ "      }\n"
				+ "    }\n"
				+ "  }\n"
				+ "}"
			;

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JaxbAnnotationModule() );
		mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);

		BusinessMessage resp = mapper.readValue(str, BusinessMessage.class);
		exchange.getIn().setBody(resp);

	}


}
