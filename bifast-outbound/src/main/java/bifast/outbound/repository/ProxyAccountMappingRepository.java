package bifast.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.ProxyAccountMapping;

@Repository
public interface ProxyAccountMappingRepository extends JpaRepository<ProxyAccountMapping, Long> {

}
