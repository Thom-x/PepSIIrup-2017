package com.service.event;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.service.event.EventConfiguration;
import com.service.event.EventController;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;


//@Component
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader=AnnotationConfigWebContextLoader.class)
//@Import(EventConfiguration.class)
@WebIntegrationTest({"server.port:0", "eureka.client.enabled:false"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EventServer.class)
public class EventControllerTest {
	
	
	private EventController eventController;
	
    @Before
    public void init(){
    	eventController = new EventController();
    }
	
	@Test
	public void getAllEvent() throws Exception{
		String result = eventController.getAllEvent(1);
		assertEquals("Afterwork hangar a bananes, Foot en salle urban soccer, dej tech big data", result);
	}
	
	@Test
	public void coucou() throws ParseException{
		Event event = eventController.coucou();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/hh/mm");
		Date ds = sdf.parse("12/03/2016/12/00");
		Date df = sdf.parse("12/03/2016/14/00");
		Event eventRes = new Event("Foot en salle", ds ,df, "x45dr84ww64vg", "Seance intensive, venez motivé!", 0, 1);
		assertEquals(event, eventRes);
	}
}
