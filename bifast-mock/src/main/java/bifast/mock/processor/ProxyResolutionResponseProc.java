package bifast.mock.processor;

import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.Random;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.prxy004.ProxyLookUpResponseV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Proxy004MessageService;
import bifast.library.iso20022.service.Proxy004Seed;
import bifast.mock.persist.AccountProxy;
import bifast.mock.persist.AccountProxyRepository;


@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class ProxyResolutionResponseProc implements Processor{

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Proxy004MessageService proxy004MessageService;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	AccountProxyRepository accountProxyRepository;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);

		String bizMsgId = utilService.genRfiBusMsgId("610", "01", "INDOIDJA");
		String msgId = utilService.genMessageId("610", "INDOIDJA");

		Proxy004Seed seed = new Proxy004Seed();
		seed.setMsgId(msgId);
		
		String proxyType = msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getPrxyRtrvl().getTp();
		String proxyVal =  msg.getDocument().getPrxyLookUp().getLookUp().getPrxyOnly().getPrxyRtrvl().getVal();

		seed.setOrgnlPrxyRqstrTp(proxyType);
		seed.setOrgnlPrxyRqstrVal(proxyVal);
		seed.setOrgnlMsgId(msg.getAppHdr().getBizMsgIdr());
		seed.setOrgnlMsgNmId(msg.getAppHdr().getMsgDefIdr());

		Optional<AccountProxy> oAccountProxy = accountProxyRepository.findByProxyTypeAndProxyVal(proxyType, proxyVal);
		
		if (oAccountProxy.isEmpty()) {
			seed.setStatus("RJCT");
			seed.setReason("U804");

		}
		
		else {
			AccountProxy proxy = oAccountProxy.get();
			
			if (proxy.getAccountStatus().equals("ACTV")) {
				seed.setStatus("ACTC");
				seed.setReason("U000");
			}
			else {
				seed.setStatus("RJCT");
				seed.setReason("U805");
			}
			
			seed.setAccountName(proxy.getAccountName());
			seed.setAccountNumber(proxy.getAccountNumber());
			seed.setAccountType(proxy.getAccountType());
			seed.setDisplayName(proxy.getDisplayName());
			seed.setRegisterBank(proxy.getRegisterBank());
			seed.setPrxyRtrvlTp(proxy.getProxyType());
			seed.setPrxyRtrvlVal(proxy.getProxyVal());
			seed.setCstmrId(proxy.getCstmrId());
			seed.setCstmrTp(proxy.getCstmrTp());
			seed.setCstmrRsdntSts(proxy.getCstmrRsdntSts());
			seed.setCstmrTwnNm(proxy.getCstmrTwnNm());

			GregorianCalendar gcal = new GregorianCalendar();
			XMLGregorianCalendar xcal;
			try {

				xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
				seed.setOrgnlCreDtTm(xcal);
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		ProxyLookUpResponseV01 response = proxy004MessageService.proxyResolutionResponse(seed, msg);
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"prxy.004.001.01", bizMsgId);
		
		Document doc = new Document();
		doc.setPrxyLookUpRspn(response);
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);
		
	}


}
