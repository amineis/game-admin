package dev.amineis.gameadmin.controller;

import dev.amineis.gameadmin.dto.request.AppUserCreateRequest;
import dev.amineis.gameadmin.dto.request.AppUserUpdateRequest;
import dev.amineis.gameadmin.dto.response.AppUserResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.enums.Role;
import dev.amineis.gameadmin.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "List all app users", description = "Returns a paginated list of app users. Requires ADMIN.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PagedResponse<AppUserResponse>> getAllAppUsers(Pageable pageable) {
        return ResponseEntity.ok(appUserService.getAllAppUsers(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get app user by ID", description = "Returns a single app user by id.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "User not found") })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AppUserResponse> getAppUserById(@PathVariable Long id) {
        return ResponseEntity.ok(appUserService.getAppUserById(id));
    }

    @GetMapping("/by-role")
    @Operation(summary = "List app users by role", description = "Returns app users filtered by role (ADMIN, MODERATOR, SUPPORT).")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PagedResponse<AppUserResponse>> getAppUsersByRole(@RequestParam Role role, Pageable pageable) {
        return ResponseEntity.ok(appUserService.getAppUsersByRole(role, pageable));
    }

    @PostMapping
    @Operation(summary = "Create app user", description = "Creates a new app user. Requires ADMIN.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Created"), @ApiResponse(responseCode = "400", description = "Validation error"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AppUserResponse> createAppUser(@Valid @RequestBody AppUserCreateRequest request) {
        return ResponseEntity.ok(appUserService.createAppUser(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update app user", description = "Updates an existing app user by id.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Validation error"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "User not found") })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AppUserResponse> updateAppUser(@PathVariable Long id, @Valid @RequestBody AppUserUpdateRequest request) {
        return ResponseEntity.ok(appUserService.updateAppUser(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete app user", description = "Deletes an app user by id. Returns 204 No Content.")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Deleted"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "User not found") })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAppUser(@PathVariable Long id) {
        appUserService.deleteAppUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Returns the current authenticated user's username and email from JWT. Any authenticated user.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized") })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("preferred_username");
        String email = jwt.getClaim("email");
        return ResponseEntity.ok("Hello, " + username + " (" + email + ")");
    }
}

