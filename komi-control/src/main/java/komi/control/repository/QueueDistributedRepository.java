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
public interface QueueDistributedRepository extends JpaRepository<QueueDistributed, Long> {
	
	
	@Transactional
	@Modifying
	@Query("select etd FROM QueueDistributed etd where etd.typeNotification =:typeNotification and (etd.status <> 1 or etd.status is null)")
	List<QueueDistributed> getListByTypeNotifAndStatus (@Param("typeNotification") String typeNotification);
	
	@Query("select etd FROM QueueDistributed etd where etd.eventId =:eventId and etd.eventSubcriberId =:eventSubcriberId ")
	QueueDistributed getListByEventIdAndEventSubriberId (@Param("eventId") String eventId,@Param("eventSubcriberId") String eventSubcriberId);
}
