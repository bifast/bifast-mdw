package komi.control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import komi.control.model.EventCategory;

@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {
	
	@Query("select category FROM EventCategory category where category.id =:id")
	EventCategory getById (@Param("id") Long id);
}
