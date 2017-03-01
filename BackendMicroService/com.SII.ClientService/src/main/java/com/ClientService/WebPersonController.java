package com.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Client controller, fetches Account info from the microservice via
 * {@link WebAccountsService}.
 
 */
@Controller
public class WebPersonController {

	@Autowired
	protected WebPersonService personService;


	public WebPersonController(WebPersonService personService) {
		this.personService = personService;
	}

	@RequestMapping("/personne")
	public String goHome() {
		return "personne";
	}
	
	@RequestMapping("/greeting")
	public String getMessage(Model model) {
		Person pers = personService.greeting();
		model.addAttribute("person",pers);
		return "personneWithJson";
	}
	
}
