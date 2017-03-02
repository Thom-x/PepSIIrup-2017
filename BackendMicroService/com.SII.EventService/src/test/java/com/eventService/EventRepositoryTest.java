package com.eventService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.eventService.*;

import static org.junit.Assert.*;

@EnableAutoConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class EventRepositoryTest {
    
	@Autowired
	private EventRepository repository;
	
	@Before
	public void setUp() throws Exception {
		Event event1 = new Event("sing", "lyrics of some song");
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
		assertNotNull(event2.getId());
	}
	
	@Test
	public void testFetchData() {
		Event eventA = repository.findByName("sing");
		assertNotNull(eventA);
		assertEquals("lyrics of some song", eventA.getTexte());
		
		eventA.setName("song");
		assertEquals("song", eventA.getName());
	}
}
