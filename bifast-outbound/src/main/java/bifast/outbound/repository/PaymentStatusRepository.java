package bifast.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.PaymentStatus;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {

}
