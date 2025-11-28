package ch.mabaka.dht22_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Dht22ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Dht22ServerApplication.class, args);
	}

}