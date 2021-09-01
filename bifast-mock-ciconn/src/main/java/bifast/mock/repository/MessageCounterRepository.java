package bifast.mock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.mock.model.MessageCounter;


@Repository
public interface MessageCounterRepository extends JpaRepository<MessageCounter, Integer> {


}
