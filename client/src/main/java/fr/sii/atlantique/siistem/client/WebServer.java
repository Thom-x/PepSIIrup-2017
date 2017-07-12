package fr.sii.atlantique.siistem.client;

import fr.sii.atlantique.siistem.client.service.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

import java.time.Duration;

/**
 * Client Server
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WebServer {
	
	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * @param args Program arguments - ignored.
	 */
	public static void main(String[] args) {
		// Tell server to look for web-server.properties or web-server.yml
		System.setProperty("spring.config.name", "web-server");
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))
		.createLogger());
		SpringApplication.run(WebServer.class, args);
		Log
		.forContext("Service", "web-service")
		.information("Client Service : Started");
	}
}
