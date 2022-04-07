package bifast.outbound.fraud.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FdsResponseDAO {

	@JsonProperty("Transaction")
	private FdsTransactionResponseDAO transaction;
	@JsonProperty("Role")
	private String role;
	
	public FdsTransactionResponseDAO getTransaction() {
		return transaction;
	}
	public void setTransaction(FdsTransactionResponseDAO transaction) {
		this.transaction = transaction;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
