package bifast.corebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bifast.corebank.model.Users;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUsername(String userName);
	
}
