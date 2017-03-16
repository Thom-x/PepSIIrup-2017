package com.service.client;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitClient {
	
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Channel channel;
	private String replyQueueName;
	private String corrId;
	private BlockingQueue<String> response;
	
	private static final String ENCODE = "UTF-8";
	
	public RabbitClient(){
		this.connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("10.10.1.155");
		connectionFactory.setUsername("BugsBunny");
		connectionFactory.setPassword("Koi29Dr");
		response = new ArrayBlockingQueue<String>(1);
		try{
			this.connection = connectionFactory.newConnection();
			this.channel = this.connection.createChannel();
			
			channel.exchangeDeclare("eureka.rpc", "direct",true);
			replyQueueName = channel.queueDeclare().getQueue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
    public String rabbitRPCRoutingKeyExchange(byte[] data, String routingKey){
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
}
