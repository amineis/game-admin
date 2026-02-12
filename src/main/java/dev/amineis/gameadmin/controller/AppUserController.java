package dev.amineis.gameadmin.controller;

import dev.amineis.gameadmin.dto.request.AppUserCreateRequest;
import dev.amineis.gameadmin.dto.request.AppUserUpdateRequest;
import dev.amineis.gameadmin.dto.response.AppUserResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.enums.Role;
import dev.amineis.gameadmin.service.AppUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/app-users")
@Tag(name = "App User Controller", description = "Controller for managing app users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PagedResponse<AppUserResponse>> getAllAppUsers(Pageable pageable) {
        return ResponseEntity.ok(appUserService.getAllAppUsers(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AppUserResponse> getAppUserById(@PathVariable Long id) {
        return ResponseEntity.ok(appUserService.getAppUserById(id));
    }

    @GetMapping("/by-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PagedResponse<AppUserResponse>> getAppUsersByRole(@RequestParam Role role, Pageable pageable) {
        return ResponseEntity.ok(appUserService.getAppUsersByRole(role, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AppUserResponse> createAppUser(@Valid @RequestBody AppUserCreateRequest request) {
        return ResponseEntity.ok(appUserService.createAppUser(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AppUserResponse> updateAppUser(@PathVariable Long id, @Valid @RequestBody AppUserUpdateRequest request) {
        return ResponseEntity.ok(appUserService.updateAppUser(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAppUser(@PathVariable Long id) {
        appUserService.deleteAppUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("preferred_username");
        String email = jwt.getClaim("email");
        return ResponseEntity.ok("Hello, " + username + " (" + email + ")");
    }
}

