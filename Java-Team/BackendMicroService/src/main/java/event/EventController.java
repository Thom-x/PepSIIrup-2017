package event;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
	/*@Autowired
	private EventService eventService;*/

	@RequestMapping(value = "/coucou", method = RequestMethod.GET)
	public Event coucou(){
		Event event = new Event(1, "coucou depuis l'autre côté!");
		return event;
	}
}
