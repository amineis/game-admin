package dev.amineis.gameadmin.controller;

import dev.amineis.gameadmin.dto.request.BanCreateRequest;
import dev.amineis.gameadmin.dto.request.BanUpdateRequest;
import dev.amineis.gameadmin.dto.response.BanResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.service.BanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(summary = "List all bans", description = "Returns a paginated list of bans.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
  public ResponseEntity<PagedResponse<BanResponse>> getAllBans(Pageable pageable) {
    return ResponseEntity.ok(banService.getAllBans(pageable));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get ban by ID", description = "Returns a single ban by id.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden"),
    @ApiResponse(responseCode = "404", description = "Ban not found")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
  public ResponseEntity<BanResponse> getBanById(@PathVariable Long id) {
    return ResponseEntity.ok(banService.getBanById(id));
  }

  @GetMapping("/by-game")
  @Operation(
      summary = "List bans by game",
      description = "Returns bans for the given game id (paginated).")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
  public ResponseEntity<PagedResponse<BanResponse>> getBansByGameId(
      @RequestParam Long gameId, Pageable pageable) {
    return ResponseEntity.ok(banService.getBansByGameId(gameId, pageable));
  }

  @GetMapping("/by-moderator")
  @Operation(
      summary = "List bans by moderator",
      description = "Returns bans issued by the given app user id (paginated).")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
  public ResponseEntity<PagedResponse<BanResponse>> getBansByModeratorId(
      @RequestParam Long userId, Pageable pageable) {
    return ResponseEntity.ok(banService.getBansByModeratorId(userId, pageable));
  }

  @PostMapping("/players/{playerId}")
  @Operation(
      summary = "Create ban",
      description =
          "Bans a player for a game. Requires ADMIN or MODERATOR. Moderator is taken from JWT.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Created"),
    @ApiResponse(responseCode = "400", description = "Validation error"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden"),
    @ApiResponse(responseCode = "404", description = "Player or game not found")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
  public ResponseEntity<BanResponse> createBan(
      @PathVariable Long playerId,
      @Valid @RequestBody BanCreateRequest request,
      @AuthenticationPrincipal Jwt jwt) {
    String username = jwt.getClaimAsString("preferred_username");
    return ResponseEntity.ok(banService.createBan(playerId, request, username));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update ban", description = "Updates an existing ban by id.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "400", description = "Validation error"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden"),
    @ApiResponse(responseCode = "404", description = "Ban not found")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
  public ResponseEntity<BanResponse> updateBan(
      @PathVariable Long id, @Valid @RequestBody BanUpdateRequest request) {
    return ResponseEntity.ok(banService.updateBan(id, request));
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete ban (unban)",
      description = "Removes a ban by id. Returns 204 No Content.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Deleted"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Forbidden"),
    @ApiResponse(responseCode = "404", description = "Ban not found")
  })
  @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
  public ResponseEntity<Void> deleteBan(@PathVariable Long id) {
    banService.deleteBan(id);
    return ResponseEntity.noContent().build();
  }
}
