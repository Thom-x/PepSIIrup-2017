package com.service.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RESTFul controller 
 */
@RestController
@Component
public class PersonController {

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
    	String response = "Il y a actuellement " + persons.size() + " personnes:\n";
    	for (Person person : persons){
    		response = response + person.getPseudo() + " de type " + person.getJob() + ".\n";
    	}
    	return response;
    }
}
