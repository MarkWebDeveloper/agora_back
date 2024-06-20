package de.stella.agora_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class AgoraWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgoraWebApplication.class, args);
	}



public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String springProfilesActive = dotenv.get("SPRING_PROFILES_ACTIVE");
        int serverPort = Integer.parseInt(dotenv.get("SERVER_PORT"));
        // ...
    }
}}
