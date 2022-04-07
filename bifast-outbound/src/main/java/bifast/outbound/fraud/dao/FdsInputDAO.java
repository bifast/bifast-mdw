package bifast.outbound.fraud.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FdsInputDAO {

	@JsonProperty("Channel")
	private FdsChannelInputDAO channel;

	@JsonProperty("Transaction")
	private FdsTransactionInputDAO transaction;
	
	public FdsChannelInputDAO getChannel() {
		return channel;
	}
	public void setChannel(FdsChannelInputDAO channel) {
		this.channel = channel;
	}
	public FdsTransactionInputDAO getTransaction() {
		return transaction;
	}
	public void setTransaction(FdsTransactionInputDAO transaction) {
		this.transaction = transaction;
	}
	
}
