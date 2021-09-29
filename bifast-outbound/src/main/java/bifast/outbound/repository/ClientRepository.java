package bifast.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

}
