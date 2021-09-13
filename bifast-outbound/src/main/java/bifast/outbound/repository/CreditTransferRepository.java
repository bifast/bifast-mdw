package bifast.outbound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.CreditTransfer;


@Repository
public interface CreditTransferRepository extends JpaRepository<CreditTransfer, Long> {

	public Optional<CreditTransfer> findByCrdtTrnRequestBizMsgIdr (String msgId);

}
