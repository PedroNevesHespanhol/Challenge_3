package br.com.pedrohespanhol.challengethreeuol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ChallengeThreeUolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeThreeUolApplication.class, args);
	}

}
