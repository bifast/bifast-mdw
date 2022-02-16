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
@Table(name="KE_EVENT_SUBCRIBER")
public class EventSubcriber {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "PHONE_NUMBER")
    private String phoneNumber;
	
	@Column(name = "EMAIL")
    private String email;

    @Column(name = "USER_ID")
    private String userId;
    
    @Column(name = "PRIMARY_DISTRIBUTION")
    private String primaryDistribution;
    
    @Column(name = "SECONDARY_DISTRIBUTION")
    private String secondaryDistribution;
    
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPrimaryDistribution() {
		return primaryDistribution;
	}

	public void setPrimaryDistribution(String primaryDistribution) {
		this.primaryDistribution = primaryDistribution;
	}

	public String getSecondaryDistribution() {
		return secondaryDistribution;
	}

	public void setSecondaryDistribution(String secondaryDistribution) {
		this.secondaryDistribution = secondaryDistribution;
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
