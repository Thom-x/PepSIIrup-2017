package com.service.client;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

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
	private static final String ENCODE = "UTF-8";
	private String exchange;
	
	/**
	 * Settings for rabbit
	 * @param exchangeName
	 */
	public RabbitClient(String exchangeName){
			
		//Logger
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
		
		//Rabbit settings
		this.connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(Constants.getINSTANCE().getRabbitmqserverAddr());
		connectionFactory.setUsername(Constants.getINSTANCE().getRabbitmqUsername());
		connectionFactory.setPassword(Constants.getINSTANCE().getRabbitmqPassword());
		response = new ArrayBlockingQueue<>(1);
		this.exchange = exchangeName;
		try{
			this.connection = connectionFactory.newConnection();
			this.channel = this.connection.createChannel();
			channel.exchangeDeclare(exchange, "direct",true);
			replyQueueName = channel.queueDeclare().getQueue();
		} catch (Exception e) {
			Log
			.forContext("MemberName", "RabbitClient:Constructor")
			.forContext("Service", "web-service")
			.error(e,"Exception");
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
 			            Log
 			            .forContext("responseStatus",b)
 			            .forContext("MemberName", "getPerson")
 			            .forContext("Service", "web-service")
 			            .information("rabbit message handled status ");
 			        }
 			    }
 			});
 	        return response.take();
 		} catch (Exception e) {
			Log
			.forContext("MemberName", "rabbitRPCRoutingKeyExchange")
			.forContext("Service", "web-service")
			.error(e,"Exception");
 		}
 		return null;
     }
 }