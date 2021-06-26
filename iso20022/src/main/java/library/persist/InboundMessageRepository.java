package library.persist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundMessageRepository extends JpaRepository <InboundMessage, String> {

	public List<InboundMessage> findByRefId (String refId);

}
