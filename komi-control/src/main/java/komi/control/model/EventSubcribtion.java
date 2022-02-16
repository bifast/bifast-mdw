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
@Table(name="KE_EVENT_SUBCRIBTION")
public class EventSubcribtion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "EVENT_SUBCRIBER_ID")
    private String eventSubcriberId;
	
	@Column(name = "EVENT_CATEGORY_ID")
    private String eventCategoryId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEventSubcriberId() {
		return eventSubcriberId;
	}

	public void setEventSubcriberId(String eventSubcriberId) {
		this.eventSubcriberId = eventSubcriberId;
	}

	public String getEventCategoryId() {
		return eventCategoryId;
	}

	public void setEventCategoryId(String eventCategoryId) {
		this.eventCategoryId = eventCategoryId;
	}

	
}
