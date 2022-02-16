package komi.control.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="KE_QUEUE_DISTRIBUTED")
public class QueueDistributed implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "SUBJECT")
    private String subject;
	
	@Column(name = "SEND_TO")
    private String sendTo;

    @Column(name = "CONTENT" , columnDefinition = "text")
    private String content;

    @Column(name = "NUM_ERROR")
    private Integer numError;

    @Column(name = "SENT_ON")
    private Date sentOn;

    @Column(name = "STATUS")
    // 1 = ok... -1 = error
    private Integer status;

    @Column(name = "ERROR_MSG")
    private String errorMsg;

    @Column(name = "EVENT_CODE")
    private String eventCode;
    
    @Column(name = "TYPE_NOTIFICATION")
    private String typeNotification;
    
    @Column(name = "EVENT_ID")
    private String eventId;
    
    @Column(name = "EVENT_SUBCRIBER_ID")
    private String eventSubcriberId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getNumError() {
		return numError;
	}

	public void setNumError(Integer numError) {
		this.numError = numError;
	}

	public Date getSentOn() {
		return sentOn;
	}

	public void setSentOn(Date sentOn) {
		this.sentOn = sentOn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getTypeNotification() {
		return typeNotification;
	}

	public void setTypeNotification(String typeNotification) {
		this.typeNotification = typeNotification;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventSubcriberId() {
		return eventSubcriberId;
	}

	public void setEventSubcriberId(String eventSubcriberId) {
		this.eventSubcriberId = eventSubcriberId;
	}

}	
