package com.service.register;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

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
	
	
	
	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		//Connection Properties
		 Properties appProps = new Properties();
		 InputStream in = RegistrationServer.class.getResourceAsStream("/application.properties");
		 try {
			appProps.load(in);
		} catch (IOException e) {
			Log
			.forContext("MemberName", "RabbitClient:Constructor")
			.forContext("Service", "web-service")
			.error(e,"{date} IOException");
		}
		 
		// Tell server to look for registration.properties or registration.yml
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(appProps.getProperty("LOGSERVER_ADDR"),appProps.getProperty("LOGSERVER_APIKEY"), null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
		System.setProperty("spring.config.name", "registration-server");
		SpringApplication.run(RegistrationServer.class, args);
		Log
		.forContext("Service", "register-service")
		.information("Registration Service : Started");
	}
}