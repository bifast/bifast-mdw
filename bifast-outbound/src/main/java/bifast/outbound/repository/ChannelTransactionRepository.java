package bifast.outbound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.ChannelTransaction;

@Repository
public interface ChannelTransactionRepository extends JpaRepository<ChannelTransaction, Long> {

	Optional<ChannelTransaction> findByKomiTrnsId (String komiTrnsId);
	
}
