package dev.amineis.gameadmin.controller;

import dev.amineis.gameadmin.dto.request.MatchCreateRequest;
import dev.amineis.gameadmin.dto.request.MatchPlayerRequest;
import dev.amineis.gameadmin.dto.request.MatchUpdateRequest;
import dev.amineis.gameadmin.dto.response.MatchPlayerResponse;
import dev.amineis.gameadmin.dto.response.MatchResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.enums.MatchStatus;
import dev.amineis.gameadmin.service.MatchService;
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
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<MatchResponse>> getAllMatches(Pageable pageable) {
        return ResponseEntity.ok(matchService.getAllMatches(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<MatchResponse> getMatchById(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @GetMapping("/by-game")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<MatchResponse>> getMatchesByGameId(@RequestParam Long gameId, Pageable pageable) {
        return ResponseEntity.ok(matchService.getMatchesByGameId(gameId, pageable));
    }

    @GetMapping("/by-status")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<MatchResponse>> getMatchesByStatus(@RequestParam MatchStatus status, Pageable pageable) {
        return ResponseEntity.ok(matchService.getMatchesByStatus(status, pageable));
    }

    @GetMapping("/by-region")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<MatchResponse>> getMatchesByRegion(@RequestParam String region, Pageable pageable) {
        return ResponseEntity.ok(matchService.getMatchesByRegion(region, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<MatchResponse> createMatch(@Valid @RequestBody MatchCreateRequest request) {
        return ResponseEntity.ok(matchService.createMatch(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<MatchResponse> updateMatch(@PathVariable Long id, @Valid @RequestBody MatchUpdateRequest request) {
        return ResponseEntity.ok(matchService.updateMatch(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{matchId}/players")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<List<MatchPlayerResponse>> getPlayersByMatch(@PathVariable Long matchId) {
        return ResponseEntity.ok(matchService.getPlayersByMatch(matchId));
    }

    @PostMapping("/{matchId}/players")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<MatchPlayerResponse> addPlayerToMatch(
            @PathVariable Long matchId,
            @Valid @RequestBody MatchPlayerRequest request
    ) {
        return ResponseEntity.ok(matchService.addPlayerToMatch(matchId, request));
    }

    @DeleteMapping("/{matchId}/players/{playerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<Void> removePlayerFromMatch(@PathVariable Long matchId, @PathVariable Long playerId) {
        matchService.removePlayerFromMatch(matchId, playerId);
        return ResponseEntity.noContent().build();
    }
}
