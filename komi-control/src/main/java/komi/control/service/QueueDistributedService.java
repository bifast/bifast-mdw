package komi.control.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import komi.control.model.Event;
import komi.control.model.QueueDistributed;
import komi.control.repository.EventRepository;
import komi.control.repository.QueueDistributedRepository;

@Service
public class QueueDistributedService {
	
	@Autowired
	private QueueDistributedRepository eventTobeDistributedRepository;

    
	public List<QueueDistributed> getListByTypeNotifAndStatus (String typeNotif) {
	  
		return eventTobeDistributedRepository.getListByTypeNotifAndStatus(typeNotif);
	}
	
	public QueueDistributed getListByEventIdAndEventSubriberId (String event,String subcriber) {
		  
		return eventTobeDistributedRepository.getListByEventIdAndEventSubriberId(event,subcriber);
	}
	
	public QueueDistributed save (QueueDistributed eventDistributed) {
		  
		return eventTobeDistributedRepository.save(eventDistributed);
	}
	
}
