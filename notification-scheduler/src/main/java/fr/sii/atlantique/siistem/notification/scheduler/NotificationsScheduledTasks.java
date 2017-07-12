package fr.sii.atlantique.siistem.notification.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.sii.atlantique.siistem.sender.mail.model.Mail;

@Component
public class NotificationsScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(NotificationsScheduledTasks.class);

	private String msg = "Notification passing through RabbitMQ";
	private String from = "no-reply-nte@nantes.sii.fr";
	private String to = "apena@sii.fr";
	private String subject = "notification test";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Scheduled(fixedRate = 10000)
	public void readNotifications() {
		log.info("Notifications : <" + msg + ">");

		rabbitTemplate.convertAndSend(NotificationSchedulerApplication.mailQueueName, new Mail(from, to, subject, msg));
		log.info("message sent.");
	}
}