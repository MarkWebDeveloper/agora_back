package de.stella.agora_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.stella.agora_web.text.persistence"})
public class AgoraWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgoraWebApplication.class, args);
	}

}
