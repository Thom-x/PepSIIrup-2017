package com.service.client;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Rabbit Client to send routed messages to other services
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
public class RabbitClient {
	
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Channel channel;
	private String replyQueueName;
	private String corrId;
	private BlockingQueue<String> response;
	public static final String RABBITIP = "10.10.192.33";
	private static final String ENCODE = "UTF-8";
	private String exchange;
	private static final Logger LOGGER = Logger.getLogger(RabbitClient.class.getName());
	
	/**
	 * Settings for rabbit
	 * @param exchangeName
	 */
	public RabbitClient(String exchangeName){
		this.connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(RABBITIP);
		connectionFactory.setUsername("BugsBunny");
		connectionFactory.setPassword("Koi29Dr");
		response = new ArrayBlockingQueue<>(1);
		this.exchange = exchangeName;
		try{
			this.connection = connectionFactory.newConnection();
			this.channel = this.connection.createChannel();
			channel.exchangeDeclare(exchange, "direct",true);
			replyQueueName = channel.queueDeclare().getQueue();
		} catch (Exception e) {
			LOGGER.log( Level.SEVERE, "an exception was thrown", e);
		}
	}

	/**
 	 * Method to exchange a message to another service
 	 * @param data
 	 * @param routingKey
 	 * @return
 	 */
     public String rabbitRPCRoutingKeyExchange(byte[] data, String routingKey){
     	this.corrId = UUID.randomUUID().toString();
 		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(this.corrId).replyTo(replyQueueName).build();   	
 		try {
 			channel.basicPublish(this.exchange, routingKey, props, data);
 			channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
 				@Override
 			    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
 			        if (properties.getCorrelationId().equals(corrId)) {
 			            boolean b = response.offer(new String(body, ENCODE));
 			            LOGGER.log( Level.FINE, "rabbit message handled status :", b);
 			        }
 			    }
 			});
 	        return response.take();
 		} catch (Exception e) {
 			LOGGER.log( Level.SEVERE, "an exception was thrown", e);
 		}
 		return null;
     }
 }