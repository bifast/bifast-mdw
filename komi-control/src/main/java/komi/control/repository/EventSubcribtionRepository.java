package komi.control.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import komi.control.model.Event;
import komi.control.model.EventSubcribtion;
import komi.control.model.QueueDistributed;

@Repository
public interface EventSubcribtionRepository extends JpaRepository<EventSubcribtion, Long> {
	
	@Transactional
	@Modifying
	@Query("select etd FROM EventSubcribtion etd where etd.eventSubcriberId =:subcriberId ")
	List<EventSubcribtion> getListEventSubcriberId (@Param("subcriberId") String subcriberId );
	
	@Transactional
	@Modifying
	@Query("select etd FROM EventSubcribtion etd where etd.eventCategoryId =:eventCategoryId ")
	List<EventSubcribtion> getListCategoryId (@Param("eventCategoryId") String eventCategoryId );
}
