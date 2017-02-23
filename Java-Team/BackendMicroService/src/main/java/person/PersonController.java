package person;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RESTFul controller 
 */
@RestController
public class PersonController {

    private static final String template = "Hello, %s!";

    @RequestMapping(value="/greeting", produces="application/json")
    public Person greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Person(String.format(template, name));
    }
}
