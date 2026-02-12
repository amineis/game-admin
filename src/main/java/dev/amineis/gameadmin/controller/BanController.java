package dev.amineis.gameadmin.controller;

import dev.amineis.gameadmin.dto.request.BanCreateRequest;
import dev.amineis.gameadmin.dto.request.BanUpdateRequest;
import dev.amineis.gameadmin.dto.response.BanResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.service.BanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
@RequestMapping("/api/v1/bans")
@Tag(name = "Ban Controller", description = "API docs for ban management")
@RequiredArgsConstructor
public class BanController {

    private final BanService banService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<BanResponse>> getAllBans(Pageable pageable) {
        return ResponseEntity.ok(banService.getAllBans(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<BanResponse> getBanById(@PathVariable Long id) {
        return ResponseEntity.ok(banService.getBanById(id));
    }

    @GetMapping("/by-game")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<BanResponse>> getBansByGameId(@RequestParam Long gameId, Pageable pageable) {
        return ResponseEntity.ok(banService.getBansByGameId(gameId, pageable));
    }

    @GetMapping("/by-moderator")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<BanResponse>> getBansByModeratorId(@RequestParam Long userId, Pageable pageable) {
        return ResponseEntity.ok(banService.getBansByModeratorId(userId, pageable));
    }

    @PostMapping("/players/{playerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<BanResponse> createBan(
            @PathVariable Long playerId,
            @Valid @RequestBody BanCreateRequest request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok(banService.createBan(playerId, request, username));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<BanResponse> updateBan(@PathVariable Long id, @Valid @RequestBody BanUpdateRequest request) {
        return ResponseEntity.ok(banService.updateBan(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<Void> deleteBan(@PathVariable Long id) {
        banService.deleteBan(id);
        return ResponseEntity.noContent().build();
    }
}
