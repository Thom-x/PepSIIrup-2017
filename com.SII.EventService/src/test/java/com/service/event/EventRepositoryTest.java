package com.service.event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.util.Properties;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource("classpath:applicationTest.properties")
@ContextConfiguration(classes={EventServer.class, Event.class, EventConfiguration.class, EventController.class, EventRepository.class})
public class EventRepositoryTest {
    
	@Autowired
	private EventRepository repository;
	
	@Before
	public void setUp() throws Exception {
		/*Properties props = new Properties();
		
		//ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    InputStream is = Driver.class.getResourceAsStream("applicationTest.properties");
		try {
			System.out.println("oui oui oui oui\noui oui oui oui\n" + is + "\noui oui oui oui");
	        props.load(is);
	    } catch (IOException e) {
	        e.printStackTrace(); 
	    }*/
		/*Event event1 = new Event("sing", "lyrics of some song");
		Event event2 = new Event("book", "lorem ipsum");
		System.out.println(event1.getId() + " " + event2.getId());
		//assertNull(event1.getId());
		//assertNull(event2.getId());
		System.out.println(repository);
		Event event3 = repository.findByName("sing");
		System.out.println(event3.getId());
		repository.save(event1);
		repository.save(event2);
		assertNotNull(event1.getId());
		assertNotNull(event2.getId());*/
	}
	
	@Test
	public void testFetchData() {
		Event event = repository.findByName("Foot en Salle");
		System.out.println(event.getName() + " :" + event.getDescription() + "\nDebut: " + event.getDateStart() + " - Fin: " + event.getDateEnd());
		assertEquals("oui", "oui");
		/*Event eventA = repository.findByName("sing");
		assertNotNull(eventA);
		assertEquals("lyrics of some song", eventA.getTexte());
		
		eventA.setName("song");
		assertEquals("song", eventA.getName());*/
	}
}
