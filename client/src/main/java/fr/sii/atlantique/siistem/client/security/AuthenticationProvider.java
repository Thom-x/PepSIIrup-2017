package fr.sii.atlantique.siistem.client.security;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;

@Configuration
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final EurekaClient client;

    private final RestTemplate restTemplate;

    public AuthenticationProvider(EurekaClient client, RestTemplate restTemplate) {
        this.client = client;
        this.restTemplate = restTemplate;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken token) throws AuthenticationException {
        InstanceInfo instance = client.getNextServerFromEureka("PERSONNE-SERVICE", false);
        String url = UriComponentsBuilder.fromHttpUrl(instance.getHomePageUrl()).queryParam("username", username).build().getPath();
        ResponseEntity<Person> response = restTemplate.getForEntity(url, Person.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            Person person = response.getBody();
            return new User(person.getEmail(), person.getPassword(),
                    person.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        } else {
            return null;
        }
    }
}
