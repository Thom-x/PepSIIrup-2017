package fr.sii.atlantique.siistem.sender.sms;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fr.sii.atlantique.siistem.sender.sms.model.SMS;

@Component
public class Receiver {

	private static final Logger log = LoggerFactory.getLogger(Receiver.class);

	private static final String SERVICE_VERSION = "9.0";
	private static final String SERVICE_URL = "https://api.allmysms.com/http/" + SERVICE_VERSION
			+ "/sendSms/?login={0}&apiKey={1}&smsData={2}";
	private static final String SERVICE_SMS_DATA = "<DATA><MESSAGE><![CDATA[{0}]]></MESSAGE><SMS><MOBILEPHONE>{1}</MOBILEPHONE></SMS></DATA>";

	@Value("${sms.login}")
	private String smsLogin;
	@Value("${sms.apikey}")
	private String smsApikey;

	private final RestTemplate restTemplate;

	public Receiver(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@RabbitListener(queues = SenderSmsApplication.queueName)
	public void receiveMessage(final SMS sms) {
		try {
			sendSms(sms);
			log.info("SMS sent <" + sms.toString() + ">");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendSms(SMS sms) throws UnsupportedEncodingException {
		final String smsDataMapped = MessageFormat.format(SERVICE_SMS_DATA, sms.getMessage(), sms.getTo());
		final String strUrl = MessageFormat.format(SERVICE_URL, smsLogin, smsApikey, smsDataMapped);
		log.info(strUrl);
		this.restTemplate.getForObject(strUrl, String.class);
	}
}