package bifast.outbound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.FICreditTransfer;


@Repository
public interface FICreditTransferRepository extends JpaRepository<FICreditTransfer, Long> {

	public Optional<FICreditTransfer> findByRequestBizMsgIdr (String bizMsgIdr);

}
