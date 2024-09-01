package work.javiermantilla.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ExampleDemoFinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleDemoFinanceApplication.class, args);
	}

}
