package bifast.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.ChannelTransaction;

@Repository
public interface ChannelTransactionRepository extends JpaRepository<ChannelTransaction, Long> {

}
