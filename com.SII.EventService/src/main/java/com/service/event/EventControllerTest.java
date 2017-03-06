package com.service.event;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.service.event.EventConfiguration;
import com.service.event.EventController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Component
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@Import(EventConfiguration.class)
public class EventControllerTest {
	
    /*@Configuration
    static class ContextConfiguration {

        // this bean will be injected into the EventControllerTest class
        @Bean
        public EventController eventController() {
        	EventController eventController = new EventController();
            // set properties, etc.
            return eventController;
        }
    }*/

    /*@Autowired
    private EventController eventController;*/
	
	@Autowired
    private WebApplicationContext wac;
    
	private MockMvc mockMvc;
	
    @Before
    public void init(){
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        /*MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(eventController)
                .build();*/
    }
	
	@Test
	public void coucou() throws Exception{
		mockMvc.perform(get("/coucou")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(0))
				.andExpect(jsonPath("$.texte").value("coucou depuis l'autre côté!"));
	}
}
