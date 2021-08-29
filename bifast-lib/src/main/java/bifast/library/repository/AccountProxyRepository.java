package bifast.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.library.model.AccountProxy;

@Repository
public interface AccountProxyRepository extends JpaRepository<AccountProxy, String> {

}
