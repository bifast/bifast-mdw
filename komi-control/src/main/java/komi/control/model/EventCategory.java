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
@Table(name="KE_EVENT_CATEGORY")
public class EventCategory {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME")
    private String name;
	
	@Column(name = "DEFAULT_MESSAGE")
    private String defaultMessage;
	
	@Column(name = "TIME_LIMIT_SENDING")
    private String timeLimitSending;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public String getTimeLimitSending() {
		return timeLimitSending;
	}

	public void setTimeLimitSending(String timeLimitSending) {
		this.timeLimitSending = timeLimitSending;
	}
	
	
}
