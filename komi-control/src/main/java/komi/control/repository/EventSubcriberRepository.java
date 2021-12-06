package komi.control.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import komi.control.model.EventSubcriber;

@Repository
public interface EventSubcriberRepository extends JpaRepository<EventSubcriber, Long> {
	
	@Query("select subcriber FROM EventSubcriber subcriber where subcriber.id =:id")
	EventSubcriber getById (@Param("id") Long id);
}
