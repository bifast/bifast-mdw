package bifast.outbound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.Settlement;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

	Optional<Settlement> findByOrgnlCTBizMsgId (String bizMsgId);
	
}
