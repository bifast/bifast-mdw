package komi.control.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import komi.control.model.Event;
import komi.control.model.EventCategory;
import komi.control.model.EventSubcriber;
import komi.control.repository.EventCategoryRepository;
import komi.control.repository.EventRepository;
import komi.control.repository.EventSubcriberRepository;

@Service
public class EventCategoryService {
	
	@Autowired
	private EventCategoryRepository eventCategoryRepository;

  
	
	public EventCategory getById (Integer id) {
		  
		return eventCategoryRepository.getById(id.longValue());
	}
	
	
}
