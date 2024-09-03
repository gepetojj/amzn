package application.amzn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AmznApplication {
	public static void main(String[] args) {
		SpringApplication.run(AmznApplication.class, args);
	}
}
