package bifast.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.library.model.OutboundMessage;

@Repository
public interface OutboundMessageRepository extends JpaRepository<OutboundMessage, Long> {

}
