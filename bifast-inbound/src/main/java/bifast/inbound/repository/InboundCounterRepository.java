package bifast.inbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.inbound.model.InboundCounter;


@Repository
public interface InboundCounterRepository extends JpaRepository<InboundCounter, Integer> {

}
