package bifast.inbound.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.inbound.model.InboundMessage;


@Repository
public interface InboundMessageRepository extends JpaRepository<InboundMessage, Long> {

	public List<InboundMessage> findByBizMsgIdr (String bizmsgid);
	public List<InboundMessage> findByFrFinId (String frFindId);
	
}
