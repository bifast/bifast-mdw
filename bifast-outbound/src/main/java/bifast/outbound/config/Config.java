package bifast.outbound.config;

import java.math.BigDecimal;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import bifast.outbound.model.Parameter;
import bifast.outbound.repository.ParameterRepository;

@Configuration  // so  Sprint creates bean in application context
@ConfigurationProperties(prefix = "komi")
public class Config {
	@Autowired private ParameterRepository paramRepo;

	private String bankcode;
	private String bicode;
	
	private long slaChannelTrns;
	private long slaChannelEnqr;
	
	private BigDecimal limitDailyAmount;
	private BigDecimal limitTrnsAmount;
	
	private int maxRetryBeforeNotif;

	@PostConstruct
	public void init() {

		Optional<Parameter> oSlaChannelTrns = paramRepo.findByParamName("KOMI_CORE_SLA_CT");
		this.slaChannelTrns = Integer.valueOf(oSlaChannelTrns.get().getParamValue());

		Optional<Parameter> oSlaChannelEnqr =	paramRepo.findByParamName("KOMI_CORE_SLA_AE");
		this.slaChannelEnqr = Integer.valueOf(oSlaChannelEnqr.get().getParamValue());

		Optional<Parameter> oMaxRetry =	paramRepo.findByParamName("KOMI_CORE_MAX_RETRY_BEFORE_NOTIF");
		this.maxRetryBeforeNotif = Integer.valueOf(oMaxRetry.get().getParamValue());

		Optional<Parameter> oMaxTrnsAmt = paramRepo.findByParamName("KOMI_CORE_MAX_TRX_LIMIT_AMOUNT");
		this.limitTrnsAmount = new BigDecimal(oMaxTrnsAmt.get().getParamValue());

	}
	
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getBicode() {
		return bicode;
	}
	public void setBicode(String bicode) {
		this.bicode = bicode;
	}

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

	public BigDecimal getLimitTrnsAmount() {
		return limitTrnsAmount;
	}

	public void setLimitTrnsAmount(BigDecimal limitTrnsAmount) {
		this.limitTrnsAmount = limitTrnsAmount;
	}

	public int getMaxRetryBeforeNotif() {
		return maxRetryBeforeNotif;
	}

	public void setMaxRetryBeforeNotif(int maxRetryBeforeNotif) {
		this.maxRetryBeforeNotif = maxRetryBeforeNotif;
	}
	
}
