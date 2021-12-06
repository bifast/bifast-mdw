package komi.control.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import komi.control.model.Event;
import komi.control.model.QueueDistributed;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	
	
	@Transactional
	@Modifying
	@Query("select event FROM Event event where event.categoryEvent =:categoryEvent ")
	List<Event> getByCategory (@Param("categoryEvent") String categoryEvent);
	
	@Transactional
	@Modifying
	@Query("select event FROM Event event where event.categoryEvent =:categoryEvent and event.typeSubcribe =:typeSubcribe")
	List<Event> getByCategoryAndTypeSubcribe (@Param("categoryEvent") String categoryEvent,@Param("typeSubcribe") String typeSubcribe);
	
	@Transactional
	@Modifying
	@Query("select event FROM Event event where event.typeSubcribe =:typeSubcribe")
	List<Event> getByTypeSubcribe (@Param("typeSubcribe") String typeSubcribe);
	
	@Transactional
	@Modifying
	@Query("select event FROM Event event where event.statusDistributed is null and event.typeSubcribe =:typeSubcribe")
	List<Event> getByCategoryAndTypeSubcribe (@Param("typeSubcribe") String typeSubcribe);
	
	/*
	@Transactional
	@Modifying
	@Query("select event FROM Event event where event.statusDistributed is null and event.typeSubcribe =:typeSubcribe")
	List<Event> getByCategoryAndTypeSubcribe (@Param("typeSubcribe") String typeSubcribe);*/
}
