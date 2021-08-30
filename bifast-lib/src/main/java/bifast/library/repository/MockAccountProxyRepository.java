package bifast.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.library.model.MockAccountProxy;

@Repository
public interface MockAccountProxyRepository extends JpaRepository<MockAccountProxy, Long> {

}
