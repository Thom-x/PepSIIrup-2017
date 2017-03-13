package com.service.person;

import java.util.List;
<<<<<<< HEAD

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
=======
>>>>>>> 30f03f2a5443ae3e301f0da190dbd58c3b781f67

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
/**
 * A RESTFul controller 
 */
@RestController
@Component
public class PersonController {
	
	@Autowired 
	private PersonRepository repository;
	


<<<<<<< HEAD
	@Autowired 
	private PersonRepository repository;
	
    private static final String TEMPLATE = "Hello, %s!";

    @RequestMapping(value="/greeting", produces="application/json")
    public Person greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return null;
    }
    
    @RequestMapping(value="/getAllPerson", produces="application/json")
    public String getAllPerson(){
    	List<Person> persons = repository.findAll();
=======
	@RabbitListener(queues = "#{personQueue.name}")
	 public String getAllPerson(int id) throws InterruptedException {
		System.out.println(id);
		List<Person> persons = repository.findAll();
>>>>>>> 30f03f2a5443ae3e301f0da190dbd58c3b781f67
    	String response = "Il y a actuellement " + persons.size() + " personnes:\n";
    	for (Person person : persons){
    		response = response + person.getPseudo() + " de type " + person.getJob() + ".\n";
    	}
<<<<<<< HEAD
    	return response;
    }
=======
    	System.out.println(response);
    	return response;
	}
    
>>>>>>> 30f03f2a5443ae3e301f0da190dbd58c3b781f67
}
