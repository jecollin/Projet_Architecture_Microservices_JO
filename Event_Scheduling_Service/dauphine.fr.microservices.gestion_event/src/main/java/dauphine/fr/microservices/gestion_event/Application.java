package dauphine.fr.microservices.gestion_event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "dauphine.fr.microservices.gestion_event")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
