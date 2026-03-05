package dev.amineis.gameadmin.controller;

import dev.amineis.gameadmin.dto.request.GameCreateRequest;
import dev.amineis.gameadmin.dto.request.GameUpdateRequest;
import dev.amineis.gameadmin.dto.response.GameResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.enums.Genre;
import dev.amineis.gameadmin.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(summary = "List all games", description = "Returns a paginated list of games.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
  public ResponseEntity<PagedResponse<GameResponse>> getAllGames(Pageable pageable) {
    return ResponseEntity.ok(gameService.getAllGames(pageable));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get game by ID", description = "Returns a single game by id.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden"),
    @ApiResponse(responseCode = "404", description = "Game not found")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
  public ResponseEntity<GameResponse> getGameById(@PathVariable Long id) {
    return ResponseEntity.ok(gameService.getGameById(id));
  }

  @GetMapping("/search")
  @Operation(
      summary = "Search games by name",
      description = "Returns games whose name contains the given string (paginated).")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
  public ResponseEntity<PagedResponse<GameResponse>> searchByName(
      @RequestParam String name, Pageable pageable) {
    return ResponseEntity.ok(gameService.searchByName(name, pageable));
  }

  @GetMapping("/by-genre")
  @Operation(
      summary = "List games by genre",
      description = "Returns games filtered by genre (FPS, MOBA, RPG, etc.).")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
  public ResponseEntity<PagedResponse<GameResponse>> getGamesByGenre(
      @RequestParam Genre genre, Pageable pageable) {
    return ResponseEntity.ok(gameService.getGamesByGenre(genre, pageable));
  }

  @PostMapping
  @Operation(summary = "Create game", description = "Creates a new game. Requires ADMIN.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Created"),
    @ApiResponse(responseCode = "400", description = "Validation error"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<GameResponse> createGame(@Valid @RequestBody GameCreateRequest request) {
    return ResponseEntity.ok(gameService.createGame(request));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update game", description = "Updates an existing game by id.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "400", description = "Validation error"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden"),
    @ApiResponse(responseCode = "404", description = "Game not found")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<GameResponse> updateGame(
      @PathVariable Long id, @Valid @RequestBody GameUpdateRequest request) {
    return ResponseEntity.ok(gameService.updateGame(id, request));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete game", description = "Deletes a game by id. Returns 204 No Content.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Deleted"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden"),
    @ApiResponse(responseCode = "404", description = "Game not found")
  })
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
    gameService.deleteGame(id);
    return ResponseEntity.noContent().build();
  }
}
