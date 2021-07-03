package bifast.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.library.model.Settlement;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

}
