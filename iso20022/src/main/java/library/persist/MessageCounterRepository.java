package library.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageCounterRepository extends JpaRepository<MessageCounter, Integer> {


}
