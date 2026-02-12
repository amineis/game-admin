package dev.amineis.gameadmin.controller;

import dev.amineis.gameadmin.dto.request.PlayerCreateRequest;
import dev.amineis.gameadmin.dto.request.PlayerUpdateRequest;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.dto.response.PlayerResponse;
import dev.amineis.gameadmin.service.PlayerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/players")
@Tag(name = "Players Controller", description = "API docs for the players controller")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<PlayerResponse>> getAllPlayers(Pageable pageable) {
        return ResponseEntity.ok(playerService.getAllPlayers(pageable));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<PlayerResponse>> searchByUsername(@RequestParam String username, Pageable pageable) {
        return ResponseEntity.ok(playerService.searchByUsername(username, pageable));
    }

    @GetMapping("/by-country")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<PlayerResponse>> getPlayersByCountry(@RequestParam String country, Pageable pageable) {
        return ResponseEntity.ok(playerService.getPlayersByCountry(country, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable Long id) {
        return  ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<PlayerResponse> createPlayer(@Valid @RequestBody PlayerCreateRequest request) {
        return ResponseEntity.ok(playerService.createPlayer(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<PlayerResponse> updatePlayer(@PathVariable Long id, @Valid @RequestBody PlayerUpdateRequest request) {
        return ResponseEntity.ok(playerService.updatePlayer(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
    
}
