package library.persist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CreditTransferRepository extends JpaRepository<CreditTransfer, Long> {
	
	public Optional<CreditTransfer> findByCrdtTrnRequestBisMsgId (String msgId);

}
