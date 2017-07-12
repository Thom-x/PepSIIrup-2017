package fr.sii.atlantique.siistem.event.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import serilogj.Log;

/**
 * Constant of the Client Service
 * @author pgaultier
 *
 */
public class Constants {
	
	private static Constants instance = new Constants();
	private String logserverAddr;
	private String logserverApikey;
	private String rabbitmqserverAddr;
	private String rabbitmqUsername;
	private String rabbitmqPassword;
	
	private Constants(){
		//Connection Properties
		 Properties appProps = new Properties();
		 InputStream in = this.getClass().getResourceAsStream("/application.properties");
		 try {
			appProps.load(in);
		} catch (IOException e) {
			Log
			.forContext("MemberName", "RabbitClient:Constructor")
			.forContext("Service", "web-service")
			.error(e,"{date} IOException");
		}
		 this.logserverAddr=appProps.getProperty("LOGSERVER_ADDR");
		 this.logserverApikey=appProps.getProperty("LOGSERVER_APIKEY");
		 this.rabbitmqserverAddr=appProps.getProperty("RABBITMQSERVER_ADDR");
		 this.rabbitmqUsername=appProps.getProperty("RABBITMQ_USERNAME");
		 this.rabbitmqPassword=appProps.getProperty("RABBITMQ_PASSWORD");
	}

	public static Constants getINSTANCE() {
		if (instance == null)
		{ 	instance = new Constants();	
		}
		return instance;
	}

	public String getLogserverAddr() {
		return logserverAddr;
	}

	public String getLogserverApikey() {
		return logserverApikey;
	}

	public String getRabbitmqserverAddr() {
		return rabbitmqserverAddr;
	}

	public String getRabbitmqUsername() {
		return rabbitmqUsername;
	}

	public String getRabbitmqPassword() {
		return rabbitmqPassword;
	}

}
