package komi.control.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import komi.control.model.Event;
import komi.control.model.EventSubcriber;
import komi.control.model.EventSubcribtion;
import komi.control.repository.EventRepository;
import komi.control.repository.EventSubcriberRepository;
import komi.control.repository.EventSubcribtionRepository;

@Service
public class EventSubcribtionService {
	
	@Autowired
	private EventSubcribtionRepository eventSubcribtionRepository;

    
	public List<EventSubcribtion> getListEventSubcriberId (String subcriberId) {
	  
		return eventSubcribtionRepository.getListEventSubcriberId(subcriberId);
	}
	
	public List<EventSubcribtion> getListCategoryId (String getListCategoryId) {
		  
		return eventSubcribtionRepository.getListCategoryId(getListCategoryId);
	}
	
	public EventSubcribtion save (EventSubcribtion notifTask) {
		  
		return eventSubcribtionRepository.save(notifTask);
	}
	
}
