package dev.amineis.gameadmin.service;

import dev.amineis.gameadmin.dto.request.MatchCreateRequest;
import dev.amineis.gameadmin.dto.request.MatchPlayerRequest;
import dev.amineis.gameadmin.dto.request.MatchUpdateRequest;
import dev.amineis.gameadmin.dto.response.MatchPlayerResponse;
import dev.amineis.gameadmin.dto.response.MatchResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.entity.Game;
import dev.amineis.gameadmin.entity.GameMatch;
import dev.amineis.gameadmin.entity.MatchPlayer;
import dev.amineis.gameadmin.entity.Player;
import dev.amineis.gameadmin.enums.MatchStatus;
import dev.amineis.gameadmin.exception.BusinessRuleException;
import dev.amineis.gameadmin.exception.ResourceNotFoundException;
import dev.amineis.gameadmin.mapper.GameMatchMapper;
import dev.amineis.gameadmin.mapper.MatchPlayerMapper;
import dev.amineis.gameadmin.repository.GameMatchRepository;
import dev.amineis.gameadmin.repository.GameRepository;
import dev.amineis.gameadmin.repository.MatchPlayerRepository;
import dev.amineis.gameadmin.repository.PlayerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {

  private final GameMatchRepository gameMatchRepository;
  private final GameRepository gameRepository;
  private final MatchPlayerRepository matchPlayerRepository;
  private final PlayerRepository playerRepository;
  private final GameMatchMapper gameMatchMapper;
  private final MatchPlayerMapper matchPlayerMapper;

  @Transactional(readOnly = true)
  public PagedResponse<MatchResponse> getAllMatches(Pageable pageable) {
    return toPagedResponse(gameMatchRepository.findAll(pageable));
  }

  @Transactional(readOnly = true)
  public MatchResponse getMatchById(Long id) {
    GameMatch match =
        gameMatchRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Match", id));
    return toMatchResponse(match);
  }

  @Transactional(readOnly = true)
  public PagedResponse<MatchResponse> getMatchesByGameId(Long gameId, Pageable pageable) {
    return toPagedResponse(gameMatchRepository.findByGameId(gameId, pageable));
  }

  @Transactional(readOnly = true)
  public PagedResponse<MatchResponse> getMatchesByStatus(MatchStatus status, Pageable pageable) {
    return toPagedResponse(gameMatchRepository.findByStatus(status, pageable));
  }

  @Transactional(readOnly = true)
  public PagedResponse<MatchResponse> getMatchesByRegion(String region, Pageable pageable) {
    return toPagedResponse(gameMatchRepository.findByRegion(region, pageable));
  }

  @Transactional
  public MatchResponse createMatch(MatchCreateRequest request) {
    Game game =
        gameRepository
            .findById(request.getGameId())
            .orElseThrow(() -> new ResourceNotFoundException("Game", request.getGameId()));

    GameMatch match = gameMatchMapper.toEntity(request);
    match.setGame(game);
    match.setStatus(MatchStatus.CREATED);

    GameMatch saved = gameMatchRepository.save(match);
    return toMatchResponse(saved);
  }

  @Transactional
  public MatchResponse updateMatch(Long id, MatchUpdateRequest request) {
    GameMatch match =
        gameMatchRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Match", id));

    if (request.getRegion() != null) {
      match.setRegion(request.getRegion());
    }
    if (request.getStatus() != null) {
      match.setStatus(request.getStatus());
    }

    GameMatch updated = gameMatchRepository.save(match);
    return toMatchResponse(updated);
  }

  @Transactional
  public void deleteMatch(Long id) {
    if (!gameMatchRepository.existsById(id)) {
      throw new ResourceNotFoundException("Match", id);
    }
    gameMatchRepository.deleteById(id);
  }

  @Transactional
  public MatchPlayerResponse addPlayerToMatch(Long matchId, MatchPlayerRequest request) {
    GameMatch match =
        gameMatchRepository
            .findById(matchId)
            .orElseThrow(() -> new ResourceNotFoundException("Match", matchId));
    Player player =
        playerRepository
            .findById(request.getPlayerId())
            .orElseThrow(() -> new ResourceNotFoundException("Player", request.getPlayerId()));

    if (matchPlayerRepository.existsByMatchIdAndPlayerId(matchId, request.getPlayerId())) {
      throw new BusinessRuleException("Player already exists in this match");
    }

    MatchPlayer matchPlayer = matchPlayerMapper.toEntity(request);
    matchPlayer.setMatch(match);
    matchPlayer.setPlayer(player);

    MatchPlayer saved = matchPlayerRepository.save(matchPlayer);
    return matchPlayerMapper.toResponse(saved);
  }

  @Transactional(readOnly = true)
  public List<MatchPlayerResponse> getPlayersByMatch(Long matchId) {
    if (!gameMatchRepository.existsById(matchId)) {
      throw new ResourceNotFoundException("Match", matchId);
    }
    return matchPlayerRepository.findByMatchId(matchId).stream()
        .map(matchPlayerMapper::toResponse)
        .toList();
  }

  @Transactional
  public void removePlayerFromMatch(Long matchId, Long playerId) {
    if (!gameMatchRepository.existsById(matchId)) {
      throw new ResourceNotFoundException("Match", matchId);
    }
    if (!playerRepository.existsById(playerId)) {
      throw new ResourceNotFoundException("Player", playerId);
    }
    if (!matchPlayerRepository.existsByMatchIdAndPlayerId(matchId, playerId)) {
      throw new ResourceNotFoundException(
          "MatchPlayer not found for matchId " + matchId + " and playerId " + playerId);
    }
    matchPlayerRepository.deleteByMatchIdAndPlayerId(matchId, playerId);
  }

  private MatchResponse toMatchResponse(GameMatch match) {
    MatchResponse response = gameMatchMapper.toResponse(match);
    List<MatchPlayerResponse> players =
        matchPlayerRepository.findByMatchId(match.getId()).stream()
            .map(matchPlayerMapper::toResponse)
            .toList();
    response.setPlayers(players);
    return response;
  }

  private PagedResponse<MatchResponse> toPagedResponse(Page<GameMatch> page) {
    return PagedResponse.<MatchResponse>builder()
        .content(page.getContent().stream().map(this::toMatchResponse).toList())
        .page(page.getNumber())
        .size(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .last(page.isLast())
        .build();
  }
}
