package fr.sii.atlantique.siistem.sender.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import fr.sii.atlantique.siistem.sender.mail.model.Mail;

@Component
public class Receiver {

	private static final Logger log = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	public JavaMailSender emailSender;

	@RabbitListener(queues = SenderMailApplication.queueName)
	public void receiveMessage(final Mail mail) {
		sendMail(mail);
		log.info("Mail sent <" + mail.toString() + ">");
	}

	private void sendMail(Mail mail) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(mail.getFrom());
		mailMessage.setTo(mail.getTo());
		mailMessage.setSubject(mail.getSubject());
		mailMessage.setText(mail.getBody());
		emailSender.send(mailMessage);
	}
}