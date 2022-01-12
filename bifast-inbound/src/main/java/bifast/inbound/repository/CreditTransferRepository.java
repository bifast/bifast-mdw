package bifast.inbound.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.inbound.model.CreditTransfer;


@Repository
public interface CreditTransferRepository extends JpaRepository<CreditTransfer, Long> {

	public List<CreditTransfer> findAllByCrdtTrnRequestBizMsgIdr (String msgId);
	public List<CreditTransfer> findAllByCrdtTrnRequestBizMsgIdrAndResponseCode (String msgId, String responseCode);
	
//	public Optional<CreditTransfer> findByCrdtTrnRequestBizMsgIdr (String msgId);

}
