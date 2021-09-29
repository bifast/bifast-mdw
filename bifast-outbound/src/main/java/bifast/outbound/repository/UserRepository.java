package bifast.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUsername(String userName);
	
}
