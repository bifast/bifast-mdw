package fransmz.bifast.outbound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("bifast.library.persist")
@EnableJpaRepositories(basePackages = "bifast.library.persist")
public class BifastOutboundApplication {

	public static void main(String[] args) {
		SpringApplication.run(BifastOutboundApplication.class, args);
		System.out.println("start outbound");
	}

}
