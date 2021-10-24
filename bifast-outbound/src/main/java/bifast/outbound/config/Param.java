package bifast.outbound.config;

import java.math.BigDecimal;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import bifast.outbound.model.Parameter;
import bifast.outbound.repository.ParameterRepository;

@Configuration
public class Param {
	
	@Autowired 
	private ParameterRepository paramRepo;
	
	private long slaChannelTrns;
	private long slaChannelEnqr;
	
	private BigDecimal limitDailyAmount;
	private long limitDailyFrequency;
	private BigDecimal limitTrnsAmount;
	
	@PostConstruct
	public void init() {

		Optional<Parameter> oSlaChannelTrns = paramRepo.findByModuleAndCode("OUTBOUND", "SLA.CHNL.TRANSACTION");
		this.slaChannelTrns = Integer.valueOf(oSlaChannelTrns.get().getValue());

		Optional<Parameter> oSlaChannelEnqr =	paramRepo.findByModuleAndCode("OUTBOUND", "SLA.CHNL.ENQUIRY");
		this.slaChannelEnqr = Integer.valueOf(oSlaChannelEnqr.get().getValue());
		
		Optional<Parameter> oLimitDailyAmount = paramRepo.findByModuleAndCode("OUTBOUND", "LIMIT.DAILY.AMOUNT");
		this.limitDailyAmount = new BigDecimal(oLimitDailyAmount.get().getValue());
		
		Optional<Parameter> oLimitDailyFreq = paramRepo.findByModuleAndCode("OUTBOUND", "LIMIT.DAILY.FREQ");
		this.limitDailyFrequency = Long.parseLong(oLimitDailyFreq.get().getValue());
			
		Optional<Parameter> oLimitTransAmount = paramRepo.findByModuleAndCode("OUTBOUND", "LIMIT.TRNS.AMOUNT");
		this.limitTrnsAmount = new BigDecimal(oLimitTransAmount.get().getValue());

	}
	
	public Param() {}

	public ParameterRepository getParamRepo() {
		return paramRepo;
	}

	public void setParamRepo(ParameterRepository paramRepo) {
		this.paramRepo = paramRepo;
	}

	public long getSlaChannelTrns() {
		return slaChannelTrns;
	}

	public void setSlaChannelTrns(long slaChannelTrns) {
		this.slaChannelTrns = slaChannelTrns;
	}

	public long getSlaChannelEnqr() {
		return slaChannelEnqr;
	}

	public void setSlaChannelEnqr(long slaChannelEnqr) {
		this.slaChannelEnqr = slaChannelEnqr;
	}


	public BigDecimal getLimitDailyAmount() {
		return limitDailyAmount;
	}

	public void setLimitDailyAmount(BigDecimal limitDailyAmount) {
		this.limitDailyAmount = limitDailyAmount;
	}

	public long getLimitDailyFrequency() {
		return limitDailyFrequency;
	}

	public void setLimitDailyFrequency(long limitDailyFrequency) {
		this.limitDailyFrequency = limitDailyFrequency;
	}

	public BigDecimal getLimitTrnsAmount() {
		return limitTrnsAmount;
	}

	public void setLimitTrnsAmount(BigDecimal limitTrnsAmount) {
		this.limitTrnsAmount = limitTrnsAmount;
	}
	

	
}
