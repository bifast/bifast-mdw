package bifast.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.library.model.CreditTransfer;

@Repository
public interface CreditTransferRepository extends JpaRepository<CreditTransfer, Long> {

	public Optional<CreditTransfer> findByCrdtTrnRequestBizMsgIdr (String msgId);

}
