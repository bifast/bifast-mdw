package bifast.library.persist.corebank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountOrgnlRepository extends JpaRepository<AccountOrgnl, String> {

}
