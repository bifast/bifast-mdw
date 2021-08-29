package bifast.admin.pojo;

public class MessageHistoryPojo {

	String timestamp;
	String uuid;
	String source_bic;
	String source_account_number;
	String source_account_name;

	String destination_bic;
	String destination_account_number;
	String destination_account_name;

	String transaction_type;
	String amount;
	String status_message;
	String status_code;
	String currency;
	
	public MessageHistoryPojo () {}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSource_bic() {
		return source_bic;
	}
	public void setSource_bic(String source_bic) {
		this.source_bic = source_bic;
	}
	public String getSource_account_number() {
		return source_account_number;
	}
	public void setSource_account_number(String source_account_number) {
		this.source_account_number = source_account_number;
	}
	public String getSource_account_name() {
		return source_account_name;
	}
	public void setSource_account_name(String source_account_name) {
		this.source_account_name = source_account_name;
	}
	public String getDestination_bic() {
		return destination_bic;
	}
	public void setDestination_bic(String destination_bic) {
		this.destination_bic = destination_bic;
	}
	public String getDestination_account_number() {
		return destination_account_number;
	}
	public void setDestination_account_number(String destination_account_number) {
		this.destination_account_number = destination_account_number;
	}
	public String getDestination_account_name() {
		return destination_account_name;
	}
	public void setDestination_account_name(String destination_account_name) {
		this.destination_account_name = destination_account_name;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus_message() {
		return status_message;
	}
	public void setStatus_message(String status_message) {
		this.status_message = status_message;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	

	
}
