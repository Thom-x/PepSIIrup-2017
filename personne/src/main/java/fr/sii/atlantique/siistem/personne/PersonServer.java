package fr.sii.atlantique.siistem.personne;

import java.time.Duration;

import fr.sii.atlantique.siistem.personne.service.Constants;
import fr.sii.atlantique.siistem.personne.service.PersonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

@EnableAutoConfiguration
@EnableDiscoveryClient
@PropertySource("classpath:application.properties")
@Import(PersonConfiguration.class)
public class PersonServer {
	
    public static void main(String[] args) {
        // Will configure using accounts-server.yml
        System.setProperty("spring.config.name", "person-server");
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))
		.createLogger());
        SpringApplication.run(PersonServer.class, args);
		Log
		.forContext("Service", "person-service")
		.information("Person Service : Started");
    }
}