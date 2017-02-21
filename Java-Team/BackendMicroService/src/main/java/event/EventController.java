package event;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

	@RequestMapping(value = "/coucou", method = RequestMethod.GET)
	public String coucou(){
		return "coucou depuis l'autre côté!";
	}
}
