package fr.sii.atlantique.siistem.notification.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotificationSchedulerApplication {


	public static String mailQueueName = "mails";

	public static void main(String[] args) {
		SpringApplication.run(NotificationSchedulerApplication.class);
	}

}
