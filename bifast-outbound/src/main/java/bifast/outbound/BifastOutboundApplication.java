package bifast.outbound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EntityScan("bifast.library.model")
//@EnableJpaRepositories(basePackages = "bifast.library.repository")
public class BifastOutboundApplication {

	public static void main(String[] args) {
		SpringApplication.run(BifastOutboundApplication.class, args);
	}

}
