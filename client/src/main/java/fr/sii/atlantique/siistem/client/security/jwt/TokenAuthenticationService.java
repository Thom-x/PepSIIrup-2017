package fr.sii.atlantique.siistem.client.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenAuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationService.class);

    private static final String ROLE_KEY = "role";

    private static final String ROLE_DELIMITER = ",";

    @Value("${security.jwt.expirationTime:60}")
    private long expirationTime;

    @Value("${security.jwt.secret:secret}")
    private String secret;

    @Value("${security.jwt.header:Authorization}")
    private String headerString;

    @Value("${security.jwt.header.startWith:Bearer }")
    private String headerStartWith;

    private ObjectMapper mapper;

    public TokenAuthenticationService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    void addAuthentication(HttpServletResponse response, String username, Collection<? extends GrantedAuthority> authorities) throws IOException {
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Claims claims = Jwts.claims()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 60 * 1000));
        claims.put(ROLE_KEY, roles.stream().collect(Collectors.joining(ROLE_DELIMITER)));

        String JWT = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        response.addHeader(headerString, headerStartWith + JWT);

        JwtAuthenticatedUser user = new JwtAuthenticatedUser(username, roles);

        PrintWriter printWriter = response.getWriter();
        printWriter.print(mapper.writeValueAsString(user));
        printWriter.flush();
    }

    Authentication getAuthentication(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(headerString))
                .filter(s -> s.startsWith(headerStartWith))
                .map(s -> s.replaceFirst(headerStartWith, ""))
                .map(this::parseToken)
                .orElse(null);
    }

    private JwtAuthenticatedUser parseToken(String token) {
        JwtAuthenticatedUser user = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            user = new JwtAuthenticatedUser(body.getSubject(), Arrays.asList(body.get(ROLE_KEY).toString().split(ROLE_DELIMITER)));
        } catch (ExpiredJwtException e) {
            LOGGER.warn("Error token expired", e);
        }
        return user;
    }
}
