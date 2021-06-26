package bifast.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.library.model.SettlementProc;

@Repository
public interface SettlementProcRepository extends JpaRepository<SettlementProc, Long> {

}
