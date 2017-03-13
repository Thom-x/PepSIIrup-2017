package com.service.client;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


/**
 * Client controller, fetches Account info from the microservice via
 */
@RestController
@Component
public class WebPersonController {


		private ConnectionFactory connectionFactory;
		private Connection connection;
		private Channel channel;
		private String replyQueueName;
		
		public WebPersonController(){
			this.connectionFactory = new ConnectionFactory();
			connectionFactory.setHost("10.10.1.155");
			connectionFactory.setUsername("BugsBunny");
			connectionFactory.setPassword("Koi29Dr");
			try{
				this.connection = connectionFactory.newConnection();
				this.channel = this.connection.createChannel();
				channel.exchangeDeclare("eureka.rpc", "direct",true);
				replyQueueName = channel.queueDeclare().getQueue();
				channel.queueBind(replyQueueName, "eureka.rpc", "person");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	    @RequestMapping("/getPerson")
	    public String getPerson(@RequestParam(value="id", defaultValue="1") String id) throws InterruptedException{
			final String corrId = UUID.randomUUID().toString();
			AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();   	
			final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);
			try {
				channel.basicPublish("eureka.rpc", "person", props, id.getBytes("UTF-8"));
				channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
				    @Override
				    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				        if (properties.getCorrelationId().equals(corrId)) {
				            response.offer(new String(body, "UTF-8"));
				        }
				    }
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		        return response.take();
		}
	
	
	/*@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange direct;
	
	

    @RequestMapping("/getPerson")
    public String getPerson(@RequestParam(value="id", defaultValue="1") String id) {
		String response = (String) template.convertSendAndReceive(direct.getName(), "person",id);
		System.out.println(response);
		return response;
    }
	*/
}
