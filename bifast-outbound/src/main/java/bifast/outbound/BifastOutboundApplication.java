package bifast.outbound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("bifast.library.persist")
@EnableJpaRepositories(basePackages = "bifast.library.persist")
public class BifastOutboundApplication {

	public static void main(String[] args) {
		SpringApplication.run(BifastOutboundApplication.class, args);
	}

}
