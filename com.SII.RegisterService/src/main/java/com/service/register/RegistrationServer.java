package com.service.register;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

/**
 * Eureka Server for PepSIIRup
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistrationServer {
	
	static final String LOGSERVER_ADDR = "http://10.10.192.33:5341";
	static final String LOGSERVER_SERVICE_APIKEY = "V6PqGZAimeclWbKJjkE3";
	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 */
	public static void main(String[] args) {
		// Tell server to look for registration.properties or registration.yml
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(LOGSERVER_ADDR,LOGSERVER_SERVICE_APIKEY, null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
		System.setProperty("spring.config.name", "registration-server");
		SpringApplication.run(RegistrationServer.class, args);
		Log
		.forContext("Service", "register-service")
		.information("Registration Service : Started");
	}
}