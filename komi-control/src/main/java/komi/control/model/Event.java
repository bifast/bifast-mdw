package komi.control.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="KE_EVENT")
public class Event {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "SOURCE")
    private String source;
	
	@Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "USER_ID")
    private String userId;
    
    @Column(name = "TYPE_SUBCRIB")
    private String typeSubcribe;
    
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNUmber;
    
    @Column(name = "TRANSACTION_NUMBER")
    private String transactionNumber;
    
    @Column(name = "REFERENSE_NUMBER")
    private String referenseNumber;
    
    @Column(name = "ACCOUNT_NUMBER_RECIPIENT")
    private String accountNumberRecipient;
    
    @Column(name = "BANK_RECIPIENT")
    private String bankRecipient;
    
    @Column(name = "CATEGORY_EVENT")
    private String categoryEvent;
    
    @Column(name = "STATUS_DISTRIBUTED")
    private String statusDistributed;
    
    @Column(name = "CREATE_DT")
    private LocalDateTime createDt;
    
    @Column(name = "MODIF_DT")
	private LocalDateTime modifDt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTypeSubcribe() {
		return typeSubcribe;
	}

	public void setTypeSubcribe(String typeSubcribe) {
		this.typeSubcribe = typeSubcribe;
	}

	public String getAccountNUmber() {
		return accountNUmber;
	}

	public void setAccountNUmber(String accountNUmber) {
		this.accountNUmber = accountNUmber;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getReferenseNumber() {
		return referenseNumber;
	}

	public void setReferenseNumber(String referenseNumber) {
		this.referenseNumber = referenseNumber;
	}

	public String getAccountNumberRecipient() {
		return accountNumberRecipient;
	}

	public void setAccountNumberRecipient(String accountNumberRecipient) {
		this.accountNumberRecipient = accountNumberRecipient;
	}

	public String getBankRecipient() {
		return bankRecipient;
	}

	public void setBankRecipient(String bankRecipient) {
		this.bankRecipient = bankRecipient;
	}

	public String getCategoryEvent() {
		return categoryEvent;
	}

	public void setCategoryEvent(String categoryEvent) {
		this.categoryEvent = categoryEvent;
	}

	public String getStatusDistributed() {
		return statusDistributed;
	}

	public void setStatusDistributed(String statusDistributed) {
		this.statusDistributed = statusDistributed;
	}

	public LocalDateTime getCreateDt() {
		return createDt;
	}

	public void setCreateDt(LocalDateTime createDt) {
		this.createDt = createDt;
	}

	public LocalDateTime getModifDt() {
		return modifDt;
	}

	public void setModifDt(LocalDateTime modifDt) {
		this.modifDt = modifDt;
	}

}
