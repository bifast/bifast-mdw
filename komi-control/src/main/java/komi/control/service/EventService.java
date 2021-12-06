package komi.control.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import komi.control.model.Event;
import komi.control.repository.EventRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Service
public class EventService {
	
	@Autowired
	private EventRepository notificationTaskRepository;

    
	public List<Event> getByCategory (String code) {
	  
		return notificationTaskRepository.getByCategory(code);
	}
	
	public List<Event> getByCategoryAndTypeSubcribe (String code,String typeSubcribe) {
		  
		return notificationTaskRepository.getByCategoryAndTypeSubcribe(code,typeSubcribe);
	}
	
	public List<Event> getByStatusAndTypeSubcribe (String typeSubcribe) {
		  
		return notificationTaskRepository.getByCategoryAndTypeSubcribe(typeSubcribe);
	}
	
	public List<Event> getByTypeSubcribe (String typeSubcribe) {
		  
		return notificationTaskRepository.getByTypeSubcribe(typeSubcribe);
	}
	
	/*
	public List<Event> getLastEventByCategory (String category) {
		
		Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "updatedAt"));
		return notificationTaskRepository.get(category,pageable);
	}*/
	
	public Event save (Event notifTask) {
		  
		return notificationTaskRepository.save(notifTask);
	}
	
}
