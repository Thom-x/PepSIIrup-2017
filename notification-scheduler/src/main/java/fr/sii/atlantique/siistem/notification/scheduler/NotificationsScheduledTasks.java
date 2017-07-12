package fr.sii.atlantique.siistem.notification.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.sii.atlantique.siistem.notification.scheduler.model.Mail;
import fr.sii.atlantique.siistem.notification.scheduler.model.SMS;

@Component
public class NotificationsScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(NotificationsScheduledTasks.class);

	private String msg = "Notification passing through RabbitMQ";
	private String mailFrom = "no-reply-nte@nantes.sii.fr";
	private String mailTo = "apena@sii.fr";
	private String mailSubject = "notification test";
	private String smsTo = "0642081559";

	private boolean mailNotif = true;
	private boolean smsNotif = true;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Scheduled(fixedRate = 10000)
	public void readNotifications() {
		log.info("Notifications : <" + msg + ">");

		if (mailNotif) {
			rabbitTemplate.convertAndSend(NotificationSchedulerApplication.mailQueueName,
					new Mail(mailFrom, mailTo, mailSubject, msg));
			log.info("mail notification sent.");
		} else if (smsNotif) {
			rabbitTemplate.convertAndSend(NotificationSchedulerApplication.smsQueueName, new SMS(smsTo, msg));
			log.info("sms notification sent.");
		}

	}
}