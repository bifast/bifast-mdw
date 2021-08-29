package bifast.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.library.model.InboundMessage;

@Repository
public interface InboundMessageRepository extends JpaRepository<InboundMessage, Long> {

	public Optional<InboundMessage> findByBizMsgIdr (String bizmsgid);
	public List<InboundMessage> findByFrFinId (String frFindId);
	
}
