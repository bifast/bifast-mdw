package bifast.mock.persist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MockPacs002Repository extends JpaRepository<MockPacs002, String> {
    
	List<MockPacs002> findByOrgnlEndToEndId (String orgnlEndToEndId);
}
