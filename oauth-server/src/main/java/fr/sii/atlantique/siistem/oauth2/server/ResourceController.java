package fr.sii.atlantique.siistem.oauth2.server;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("user")
    public String helloUser() {
        return "hello user";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin")
    public String helloAdmin() {
        return "hello admin";
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("client")
    public String helloClient() {
        return "hello user authenticated by normal client";
    }

    @PreAuthorize("hasRole('ROLE_TRUSTED_CLIENT')")
    @GetMapping("trusted_client")
    public String helloTrustedClient() {
        return "hello user authenticated by trusted client";
    }

    @GetMapping("principal")
    public Object getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }

    @GetMapping("roles")
    public Object getRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

}
