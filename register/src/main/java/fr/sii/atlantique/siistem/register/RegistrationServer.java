package fr.sii.atlantique.siistem.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.io.IOException;

/**
 * Eureka Server for PepSIIRup
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistrationServer {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(RegistrationServer.class);
	}
}