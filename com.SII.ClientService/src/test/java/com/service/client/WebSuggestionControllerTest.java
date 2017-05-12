package com.service.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


//@Component
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader=AnnotationConfigWebContextLoader.class)
//@Import(EventConfiguration.class)
@WebIntegrationTest({"server.port:0", "eureka.client.enabled:false","spring.application.name:web-service"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebServer.class)
public class WebSuggestionControllerTest {

	
	@Autowired
	private WebSuggestionController WebSuggestionController;
		
	@Test
	public void contexLoads() throws Exception{
		assertNotEquals(WebSuggestionController, null);
	}
	

}