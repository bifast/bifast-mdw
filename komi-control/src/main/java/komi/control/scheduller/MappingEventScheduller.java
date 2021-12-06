package komi.control.scheduller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import komi.control.model.Event;
import komi.control.model.EventCategory;
import komi.control.model.EventSubcriber;
import komi.control.model.EventSubcribtion;
import komi.control.model.QueueDistributed;
import komi.control.repository.EventCategoryRepository;
import komi.control.repository.QueueDistributedRepository;
import komi.control.service.EventCategoryService;
import komi.control.service.EventService;
import komi.control.service.EventSubcriberService;
import komi.control.service.EventSubcribtionService;
import komi.control.service.QueueDistributedService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

@Component
public class MappingEventScheduller {

    static final Logger log= LoggerFactory.getLogger(MappingEventScheduller.class);

    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    EventService eventService;
    
    @Autowired
    EventSubcriberService  eventSubcriberService;
    
    @Autowired
    EventSubcribtionService  subribtionService;
    
    @Autowired
    QueueDistributedService tobeDistributedService;
    
    @Autowired
    EventCategoryService  eventCategoryService;

    //@Scheduled(cron = "0 */1 * * * ?")
    public void mappingEventSch() throws IOException, MessagingException {

        log.info("================= MAPPING EVENT "+ new Date().toString()+"======================");
        this.mappingEvent();
    }
    
    //@Scheduled(cron = "0 */1 * * * ?")
    public void mappingEventAutoSch() throws IOException, MessagingException {

        log.info("================= MAPPING EVENT AUTO SUBCRIB "+ new Date().toString()+"======================");
        this.mappingEventAuto();
    }
    
	public void mappingEventAuto() {
	    	
		List<Event> eventList = eventService.getByStatusAndTypeSubcribe("AUTO_SUBCRIBE");
		for(Event ev:eventList) {

			QueueDistributed eventTobeDistributed = new QueueDistributed();
			EventCategory eventCategory = eventCategoryService.getById(Integer.valueOf(ev.getCategoryEvent()));
			
			eventTobeDistributed.setContent(eventCategory.getDefaultMessage());
			eventTobeDistributed.setSendTo(ev.getAccountNUmber());
			eventTobeDistributed.setSubject(eventCategory.getName());
			eventTobeDistributed.setEventId(ev.getId().toString());
			eventTobeDistributed.setTypeNotification("SMS");
			tobeDistributedService.save(eventTobeDistributed);
			
			ev.setStatusDistributed("DONE");
			eventService.save(ev);
		}
	    
	}
    
    public void mappingEvent() {
    	
    	
    	List<Event> eventList = eventService.getByStatusAndTypeSubcribe("SUBCRIB");
		for(Event ev:eventList) {
			List<EventSubcribtion> subcribtionList = subribtionService.getListCategoryId(ev.getCategoryEvent());
			for(EventSubcribtion subcrib:subcribtionList) {
				
				EventSubcriber eventSubcriber = eventSubcriberService.getById(Integer.parseInt(subcrib.getEventSubcriberId()));
				if(eventSubcriber != null ) {
					
					EventCategory eventCategory = eventCategoryService.getById(Integer.valueOf(ev.getCategoryEvent()));
					
					if(eventSubcriber.getPrimaryDistribution().equals("EMAIL") || eventSubcriber.getSecondaryDistribution().equals("EMAIL")) {
						QueueDistributed eventTobeDistributed = tobeDistributedService.getListByEventIdAndEventSubriberId(ev.getId().toString(),eventSubcriber.getId().toString());
	    			
	    				if(eventTobeDistributed == null) {
	    					eventTobeDistributed = new QueueDistributed();
	    					eventTobeDistributed.setContent(eventCategory.getDefaultMessage());
	        				eventTobeDistributed.setSendTo(eventSubcriber.getEmail());
	        				eventTobeDistributed.setSubject(eventCategory.getName());;
	        				eventTobeDistributed.setEventId(ev.getId().toString());
	        				eventTobeDistributed.setEventSubcriberId(eventSubcriber.getId().toString());
	        				eventTobeDistributed.setTypeNotification("EMAIL");
	        				tobeDistributedService.save(eventTobeDistributed);
	    				}
					}
					
					if(eventSubcriber.getPrimaryDistribution().equals("SMS") || eventSubcriber.getSecondaryDistribution().equals("SMS")) {
						QueueDistributed eventTobeDistributed = tobeDistributedService.getListByEventIdAndEventSubriberId(ev.getId().toString(),eventSubcriber.getId().toString());
	    				
	    				if(eventTobeDistributed == null) {
	    					eventTobeDistributed = new QueueDistributed();
	    					eventTobeDistributed.setContent(eventCategory.getDefaultMessage());
	        				eventTobeDistributed.setSendTo(eventSubcriber.getPhoneNumber());
	        				eventTobeDistributed.setSubject(eventCategory.getName());
	        				eventTobeDistributed.setEventId(ev.getId().toString());
	        				eventTobeDistributed.setEventSubcriberId(eventSubcriber.getId().toString());
	        				eventTobeDistributed.setTypeNotification("SMS");
	        				tobeDistributedService.save(eventTobeDistributed);
	    				}
					}
					
					if(eventSubcriber.getPrimaryDistribution().equals("PORTAL") || eventSubcriber.getSecondaryDistribution().equals("PORTAL")) {
						QueueDistributed eventTobeDistributed = tobeDistributedService.getListByEventIdAndEventSubriberId(ev.getId().toString(),eventSubcriber.getId().toString());
	    				
	    				if(eventTobeDistributed == null) {
	    					eventTobeDistributed = new QueueDistributed();
	    					eventTobeDistributed.setContent(eventCategory.getDefaultMessage());
	        				eventTobeDistributed.setSendTo(eventSubcriber.getPhoneNumber());
	        				eventTobeDistributed.setSubject(eventCategory.getName());
	        				eventTobeDistributed.setEventId(ev.getId().toString());
	        				eventTobeDistributed.setEventSubcriberId(eventSubcriber.getId().toString());
	        				eventTobeDistributed.setTypeNotification("PORTAL");
	        				tobeDistributedService.save(eventTobeDistributed);
	    				}
					}
				}
			}
			
			ev.setStatusDistributed("DONE");
			eventService.save(ev);
		}
		
    }
    
}


