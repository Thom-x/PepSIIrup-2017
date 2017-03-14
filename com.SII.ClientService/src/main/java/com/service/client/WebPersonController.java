package com.service.client;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;
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
import com.service.person.Person;


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
		private final BlockingQueue<String> response;
		private String corrId;
		
		private static final String ENCODE = "UTF-8";
		
		public WebPersonController(){
			this.connectionFactory = new ConnectionFactory();
			connectionFactory.setHost("10.10.1.155");
			connectionFactory.setUsername("BugsBunny");
			connectionFactory.setPassword("Koi29Dr");
			this.response = new ArrayBlockingQueue<String>(1);
			try{
				this.connection = connectionFactory.newConnection();
				this.channel = this.connection.createChannel();
				channel.exchangeDeclare("eureka.rpc", "direct",true);
				replyQueueName = channel.queueDeclare().getQueue();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	    @RequestMapping("/getPerson")
	    public String getPerson(@RequestParam(value="id", defaultValue="1") String id) throws InterruptedException, UnsupportedEncodingException{
	    	return rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getPerson");
		}
		
	    @RequestMapping("/getAllPerson")
	    public String getAllPerson(@RequestParam(value="id", defaultValue="1") String id) throws InterruptedException, UnsupportedEncodingException{
	    	return rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getAllPerson");
		}
	    
	    @RequestMapping("/addPerson")
	    public String addPerson(@RequestParam(value="name", defaultValue="Dorian") String name, @RequestParam(value="job", defaultValue="stagiaire") String job) throws InterruptedException{
	    	Person p = new Person(name,job);
	    	return rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(p),"addPerson");
		}
	    
	    
	    private String rabbitRPCRoutingKeyExchange(byte[] data, String routingKey){
	    	this.corrId = UUID.randomUUID().toString();
			AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(this.corrId).replyTo(replyQueueName).build();   	
			try {
				channel.basicPublish("eureka.rpc", routingKey, props, data);
				channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
					@Override
				    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				        if (properties.getCorrelationId().equals(corrId)) {
				            response.offer(new String(body, ENCODE));
				        }
				    }
				});
		        return response.take();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	    }
	      
	    @RequestMapping("/close")
	    public void closeConnection(){
	    	try {
				this.channel.close();
		    	this.connection.close();
			} catch (IOException | TimeoutException e) {
				e.printStackTrace();
			}

	    }
}
