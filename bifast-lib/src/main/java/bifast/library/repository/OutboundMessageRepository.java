package bifast.library.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bifast.library.model.OutboundMessage;

@Repository
public interface OutboundMessageRepository extends JpaRepository<OutboundMessage, Long> {

	public Optional<OutboundMessage> findByBizMsgIdr (String bizMsgIdr);
	public List<OutboundMessage> findByToFinId (String toFinId);
	
//	public List<OutboundMessage> findBySendDtGreaterThanAndSendDtLessThanEqual (LocalDateTime date1, LocalDateTime date2);

	public List<OutboundMessage> findAllBySendDtBetween (LocalDateTime date1, LocalDateTime date2);
	public List<OutboundMessage> findAllByTransactionTypeAndSendDtBetween (String trxType, LocalDateTime date1, LocalDateTime date2);

	
}
