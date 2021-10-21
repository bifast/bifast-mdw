package bifast.outbound.processor;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.exception.DuplicateIdException;
import bifast.outbound.exception.NoRefNullException;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.DomainCode;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.DomainCodeRepository;

@Component
public class ValidateProcessor implements Processor  {

	@Autowired
	private DomainCodeRepository domainCodeRepo;
	@Autowired
	private ChannelTransactionRepository chnlTrnsRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		// yg mesti divalidas
		String noref = rmw.getRequestId();
		String channelid = rmw.getChannelId(); 

		//noRef tidak boleh kosong
		if (null == noref) 
			throw new NoRefNullException("Nomor RefId tidak ada.");

		// noRef tidak boleh duplikat
		List<ChannelTransaction> lChnlTrns = chnlTrnsRepo.findByChannelIdAndChannelRefId(channelid, noref); 
		if (lChnlTrns.size()>0) 
			throw new DuplicateIdException("Nomor RefId duplikat");


		//TODO nilai transaksi harian
		//TODO frekuensi transaksi harian
		//TODO mandatori input parameter
		//TODO format data input
		//TODO sesuai domaincode, daftar bank
		
		Object objRequest = exchange.getIn().getBody(Object.class);
		
		Boolean validateChannel = false;
		Boolean validateBank = false;
		Boolean validatePurpose = false;

		String msgType = rmw.getMsgName();
		
		if (msgType.equals("AEReq")) {
//			validateChannel = true;
//			validateBank = true;
//			validatePurpose = true;
		}
		
		else if (msgType.equals("CTReq")) {
//			validateChannel = true;
//			validateBank = true;
//			validatePurpose = true;
		}

		else if (msgType.equals("PSReq")) {
//			validateChannel = true;
		}
		
		else if (msgType.equals("PrxRegnReq")) {
//			validateChannel = true;
		}

		else if (msgType.equals("PrxReslReq")) {
//			validateChannel = true;
		}

		if (validateChannel) {
			Method getChannel = objRequest.getClass().getMethod("getChannel");
			String channel = (String) getChannel.invoke(objRequest);
			DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CHANNEL.TYPE", channel).orElseThrow();

			Method setChannel = objRequest.getClass().getMethod("setChannel", String.class);
			setChannel.invoke(objRequest, channelDC.getKey());
		}

		
		if (validatePurpose) {
			Method getPurpose = objRequest.getClass().getMethod("getCategoryPurpose");
			Method setPurpose = objRequest.getClass().getMethod("setCategoryPurpose", String.class);
			String purpose = (String) getPurpose.invoke(objRequest);
			DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CATEGORY.PURPOSE", purpose).orElseThrow();
			setPurpose.invoke(objRequest, channelDC.getKey());
		}
		
			
	}

}
