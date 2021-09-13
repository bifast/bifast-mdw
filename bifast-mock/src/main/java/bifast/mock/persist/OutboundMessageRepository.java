package bifast.mock.persist;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboundMessageRepository extends JpaRepository<OutboundMessage, Long> {

	public List<OutboundMessage> findAllByBizMsgIdr (String bizMsgIdr);
	public List<OutboundMessage> findByRecipientBank (String recipientBank);
	
	public List<OutboundMessage> findAllByChannelRequestDTBetween (LocalDateTime date1, LocalDateTime date2);
	public List<OutboundMessage> findAllByMessageNameAndChannelRequestDTBetween (String messageName, LocalDateTime date1, LocalDateTime date2);

	public List<OutboundMessage> findAllByInternalReffId (String internalReffId);
}
