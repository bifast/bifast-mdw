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

		Parameter pSlaChannelTrns =	paramRepo.findByModuleAndCode("OUTBOUND", "SLA.CHNL.TRANSACTION").orElse(new Parameter());
		this.slaChannelTrns = Integer.valueOf(pSlaChannelTrns.getValue());

		Parameter pSlaChannelEnqr =	paramRepo.findByModuleAndCode("OUTBOUND", "SLA.CHNL.ENQUIRY").orElse(new Parameter());
		this.slaChannelEnqr = Integer.valueOf(pSlaChannelEnqr.getValue());
		
		Optional<Parameter> oLimitDailyAmount = paramRepo.findByModuleAndCode("OUTBOUND", "LIMIT.DAILY.AMOUNT");
		if (oLimitDailyAmount.isPresent())
			this.limitDailyAmount = new BigDecimal(oLimitDailyAmount.get().getValue());
		else 
			this.limitDailyAmount = new BigDecimal(0);
		
		Optional<Parameter> oLimitDailyFreq = paramRepo.findByModuleAndCode("OUTBOUND", "LIMIT.DAILY.FREQ");
		if (oLimitDailyFreq.isPresent())
			this.limitDailyFrequency = Long.parseLong(oLimitDailyFreq.get().getValue());
		else 
			this.limitDailyFrequency = 0;
		
		Optional<Parameter> oLimitTransAmount = paramRepo.findByModuleAndCode("OUTBOUND", "LIMIT.TRNS.AMOUNT");
		if (oLimitTransAmount.isPresent())
			this.limitTrnsAmount = new BigDecimal(oLimitTransAmount.get().getValue());
		else 
			this.limitTrnsAmount = new BigDecimal(0);

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
