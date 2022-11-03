package hacathon.hacathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HacathonApplication {

	public static void main(String[] args) {
		SpringApplication.run(HacathonApplication.class, args);
	}

}
