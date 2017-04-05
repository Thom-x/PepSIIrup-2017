package com.service.event;

import java.time.Duration;

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

/**
 * Event Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@PropertySource("classpath:application.properties")
@Import(EventConfiguration.class)
public class EventServer {
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "event-server");
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
		SpringApplication.run(EventServer.class, args);
		Log
		.forContext("Service", "event-service")
		.information("Event Service : Started");
		
	}
}
