package bifast.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.MessageCounter;


@Repository
public interface MessageCounterRepository extends JpaRepository<MessageCounter, Integer> {


}
