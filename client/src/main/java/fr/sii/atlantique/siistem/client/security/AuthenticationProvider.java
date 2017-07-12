package fr.sii.atlantique.siistem.client.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sii.atlantique.siistem.client.model.Person;
import fr.sii.atlantique.siistem.client.service.RabbitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@Configuration
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationProvider.class);

    private static final String EXCHANGE = "exc.person";

    private static final String ENCODE = "UTF-8";

    private final ObjectMapper objectMapper;

    public AuthenticationProvider(ObjectMapper objectMapper)  {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        try {
            String personneString = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(username.getBytes(ENCODE),"getPersonByEmail");
            Person person = objectMapper.readValue(personneString, Person.class);
            return new User(person.getPersonEmail(), person.getPassword(), Collections.emptyList());
        } catch (Exception e) {
            LOGGER.error("Error while getting user {}", username, e);
            return null;
        }
    }
}
