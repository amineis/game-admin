package dev.amineis.gameadmin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/app-users")
@RequiredArgsConstructor
public class AppUserController {

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> getAllAppUsers() {
        return ResponseEntity.ok("hello admin");
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("preferred_username");
        String email = jwt.getClaim("email");
        return ResponseEntity.ok("Hello, " + username + " (" + email + ")");
    }
}

