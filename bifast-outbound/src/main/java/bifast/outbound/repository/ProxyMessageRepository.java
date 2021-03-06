package bifast.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.ProxyMessage;

@Repository
public interface ProxyMessageRepository extends JpaRepository<ProxyMessage, String> {

}
