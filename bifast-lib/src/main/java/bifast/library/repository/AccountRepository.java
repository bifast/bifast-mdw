package bifast.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.library.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
