package bifast.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.Channel;

@Repository
public interface ClientRepository extends JpaRepository<Channel, String> {

}
