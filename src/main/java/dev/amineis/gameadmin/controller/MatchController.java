package dev.amineis.gameadmin.controller;

import dev.amineis.gameadmin.dto.request.MatchCreateRequest;
import dev.amineis.gameadmin.dto.request.MatchPlayerRequest;
import dev.amineis.gameadmin.dto.request.MatchUpdateRequest;
import dev.amineis.gameadmin.dto.response.MatchPlayerResponse;
import dev.amineis.gameadmin.dto.response.MatchResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.enums.MatchStatus;
import dev.amineis.gameadmin.service.MatchService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@Tag(name = "Match Controller", description = "API docs for match management")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    @Operation(summary = "List all matches", description = "Returns a paginated list of matches.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<MatchResponse>> getAllMatches(Pageable pageable) {
        return ResponseEntity.ok(matchService.getAllMatches(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get match by ID", description = "Returns a single match by id.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Match not found") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<MatchResponse> getMatchById(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @GetMapping("/by-game")
    @Operation(summary = "List matches by game", description = "Returns matches for a given game id (paginated).")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<MatchResponse>> getMatchesByGameId(@RequestParam Long gameId, Pageable pageable) {
        return ResponseEntity.ok(matchService.getMatchesByGameId(gameId, pageable));
    }

    @GetMapping("/by-status")
    @Operation(summary = "List matches by status", description = "Returns matches filtered by status (CREATED, STARTED, FINISHED, CANCELLED).")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<MatchResponse>> getMatchesByStatus(@RequestParam MatchStatus status, Pageable pageable) {
        return ResponseEntity.ok(matchService.getMatchesByStatus(status, pageable));
    }

    @GetMapping("/by-region")
    @Operation(summary = "List matches by region", description = "Returns matches filtered by region (paginated).")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<MatchResponse>> getMatchesByRegion(@RequestParam String region, Pageable pageable) {
        return ResponseEntity.ok(matchService.getMatchesByRegion(region, pageable));
    }

    @PostMapping
    @Operation(summary = "Create match", description = "Creates a new match. Requires ADMIN or MODERATOR.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Created"), @ApiResponse(responseCode = "400", description = "Validation error"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<MatchResponse> createMatch(@Valid @RequestBody MatchCreateRequest request) {
        return ResponseEntity.ok(matchService.createMatch(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update match", description = "Updates an existing match by id.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Validation error"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Match not found") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<MatchResponse> updateMatch(@PathVariable Long id, @Valid @RequestBody MatchUpdateRequest request) {
        return ResponseEntity.ok(matchService.updateMatch(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete match", description = "Deletes a match by id. Returns 204 No Content.")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Deleted"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Match not found") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{matchId}/players")
    @Operation(summary = "Get players in match", description = "Returns the list of players assigned to a match.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Match not found") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<List<MatchPlayerResponse>> getPlayersByMatch(@PathVariable Long matchId) {
        return ResponseEntity.ok(matchService.getPlayersByMatch(matchId));
    }

    @PostMapping("/{matchId}/players")
    @Operation(summary = "Add player to match", description = "Adds a player to an existing match with team and score.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Validation error"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Match or player not found") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<MatchPlayerResponse> addPlayerToMatch(
            @PathVariable Long matchId,
            @Valid @RequestBody MatchPlayerRequest request
    ) {
        return ResponseEntity.ok(matchService.addPlayerToMatch(matchId, request));
    }

    @DeleteMapping("/{matchId}/players/{playerId}")
    @Operation(summary = "Remove player from match", description = "Removes a player from a match. Returns 204 No Content.")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Removed"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Match or player not found") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<Void> removePlayerFromMatch(@PathVariable Long matchId, @PathVariable Long playerId) {
        matchService.removePlayerFromMatch(matchId, playerId);
        return ResponseEntity.noContent().build();
    }
}
