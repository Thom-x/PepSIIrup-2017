package fr.sii.atlantique.siistem.client.security.config;

import fr.sii.atlantique.siistem.client.security.jwt.JwtAuthenticationEntryPoint;
import fr.sii.atlantique.siistem.client.security.jwt.JwtAuthenticationFilter;
import fr.sii.atlantique.siistem.client.security.jwt.JwtLoginFilter;
import fr.sii.atlantique.siistem.client.security.jwt.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.url.login:/login}")
    private String urlLogin;

    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    private final TokenAuthenticationService tokenAuthenticationService;

    public WebSecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, TokenAuthenticationService tokenAuthenticationService) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/**").authenticated()
                .and()
                .formLogin()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .and()
                .addFilterBefore(new JwtLoginFilter(urlLogin, authenticationManager(), tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();
    }

}
