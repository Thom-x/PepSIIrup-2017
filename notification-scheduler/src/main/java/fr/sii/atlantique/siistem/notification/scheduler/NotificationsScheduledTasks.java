package fr.sii.atlantique.siistem.notification.scheduler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.sii.atlantique.siistem.notification.scheduler.model.notification.Mail;
import fr.sii.atlantique.siistem.notification.scheduler.model.notification.Notification;
import fr.sii.atlantique.siistem.notification.scheduler.model.notification.SMS;

@Component
public class NotificationsScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(NotificationsScheduledTasks.class);
	private static final String ENCODE = "UTF-8";
	private static final String EXCHANGE = "exc.event";

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
	public void sendNotifications() {
		readNotifications();

		log.info("Notifications : <" + msg + ">");

		if (mailNotif) {
			rabbitTemplate.convertAndSend(NotificationSchedulerApplication.mailQueueName,
					new Mail(mailFrom, mailTo, mailSubject, msg));
			log.info("mail notification sent.");
		} else if (smsNotif) {
			// TODO prévoir d'envoyer un SMS contenant une liste de numéro pour envoyer
			rabbitTemplate.convertAndSend(NotificationSchedulerApplication.smsQueueName, new SMS(smsTo, msg));
			log.info("sms notification sent.");
		}

	}

	private List<Notification> readNotifications() {
		List<Notification> res = new ArrayList<Notification>();
		try {
			String response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange("10".getBytes(ENCODE),
					"getUpcommingEvents");
			log.info(response);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}