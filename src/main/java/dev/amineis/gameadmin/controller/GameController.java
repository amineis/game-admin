package dev.amineis.gameadmin.controller;

import dev.amineis.gameadmin.dto.request.GameCreateRequest;
import dev.amineis.gameadmin.dto.request.GameUpdateRequest;
import dev.amineis.gameadmin.dto.response.GameResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.enums.Genre;
import dev.amineis.gameadmin.service.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/v1/games")
@Tag(name = "Game Controller", description = "API docs for game management")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<GameResponse>> getAllGames(Pageable pageable) {
        return ResponseEntity.ok(gameService.getAllGames(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<GameResponse> getGameById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<GameResponse>> searchByName(@RequestParam String name, Pageable pageable) {
        return ResponseEntity.ok(gameService.searchByName(name, pageable));
    }

    @GetMapping("/by-genre")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<GameResponse>> getGamesByGenre(@RequestParam Genre genre, Pageable pageable) {
        return ResponseEntity.ok(gameService.getGamesByGenre(genre, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GameResponse> createGame(@Valid @RequestBody GameCreateRequest request) {
        return ResponseEntity.ok(gameService.createGame(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GameResponse> updateGame(@PathVariable Long id, @Valid @RequestBody GameUpdateRequest request) {
        return ResponseEntity.ok(gameService.updateGame(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
