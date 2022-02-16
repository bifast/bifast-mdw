package komi.control.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import komi.control.model.Event;
import komi.control.model.EventSubcriber;
import komi.control.repository.EventRepository;
import komi.control.repository.EventSubcriberRepository;

@Service
public class EventSubcriberService {
	
	@Autowired
	private EventSubcriberRepository eventSubcriberRepository;

    
	public List<EventSubcriber> getAll () {
	  
		return eventSubcriberRepository.findAll();
	}
	
	public EventSubcriber getById (Integer id) {
		  
		return eventSubcriberRepository.getById(id.longValue());
	}
	
	public EventSubcriber save (EventSubcriber notifTask) {
		  
		return eventSubcriberRepository.save(notifTask);
	}
	
}
