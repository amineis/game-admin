package dev.amineis.gameadmin.service;

import dev.amineis.gameadmin.dto.request.BanCreateRequest;
import dev.amineis.gameadmin.dto.request.BanUpdateRequest;
import dev.amineis.gameadmin.dto.response.BanResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.entity.AppUser;
import dev.amineis.gameadmin.entity.Ban;
import dev.amineis.gameadmin.entity.Game;
import dev.amineis.gameadmin.entity.Player;
import dev.amineis.gameadmin.exception.BusinessRuleException;
import dev.amineis.gameadmin.exception.ResourceNotFoundException;
import dev.amineis.gameadmin.mapper.BanMapper;
import dev.amineis.gameadmin.repository.AppUserRepository;
import dev.amineis.gameadmin.repository.BanRepository;
import dev.amineis.gameadmin.repository.GameRepository;
import dev.amineis.gameadmin.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BanService {

  private final BanRepository banRepository;
  private final PlayerRepository playerRepository;
  private final GameRepository gameRepository;
  private final AppUserRepository appUserRepository;
  private final BanMapper banMapper;

  @Transactional(readOnly = true)
  public PagedResponse<BanResponse> getAllBans(Pageable pageable) {
    return toPagedResponse(banRepository.findAll(pageable));
  }

  @Transactional(readOnly = true)
  public BanResponse getBanById(Long id) {
    Ban ban =
        banRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ban", id));
    return banMapper.toResponse(ban);
  }

  @Transactional(readOnly = true)
  public PagedResponse<BanResponse> getBansByGameId(Long gameId, Pageable pageable) {
    return toPagedResponse(banRepository.findByGameId(gameId, pageable));
  }

  @Transactional(readOnly = true)
  public PagedResponse<BanResponse> getBansByModeratorId(Long userId, Pageable pageable) {
    return toPagedResponse(banRepository.findByBannedByUserId(userId, pageable));
  }

  @Transactional
  public BanResponse createBan(Long playerId, BanCreateRequest request, String moderatorUsername) {
    Player player =
        playerRepository
            .findById(playerId)
            .orElseThrow(() -> new ResourceNotFoundException("Player", playerId));
    Game game =
        gameRepository
            .findById(request.getGameId())
            .orElseThrow(() -> new ResourceNotFoundException("Game", request.getGameId()));
    AppUser moderator =
        appUserRepository
            .findByUsername(moderatorUsername)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "AppUser not found for username: " + moderatorUsername));

    if (banRepository.existsByPlayerIdAndGameId(playerId, request.getGameId())) {
      throw new BusinessRuleException("Player is already banned for this game");
    }

    Ban ban = banMapper.toEntity(request);
    ban.setPlayer(player);
    ban.setGame(game);
    ban.setBannedByUser(moderator);

    Ban saved = banRepository.save(ban);
    return banMapper.toResponse(saved);
  }

  @Transactional
  public BanResponse updateBan(Long id, BanUpdateRequest request) {
    Ban ban =
        banRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ban", id));

    if (request.getReason() != null) {
      ban.setReason(request.getReason());
    }

    Ban updated = banRepository.save(ban);
    return banMapper.toResponse(updated);
  }

  @Transactional
  public void deleteBan(Long id) {
    if (!banRepository.existsById(id)) {
      throw new ResourceNotFoundException("Ban", id);
    }
    banRepository.deleteById(id);
  }

  private PagedResponse<BanResponse> toPagedResponse(Page<Ban> page) {
    return PagedResponse.<BanResponse>builder()
        .content(page.getContent().stream().map(banMapper::toResponse).toList())
        .page(page.getNumber())
        .size(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .last(page.isLast())
        .build();
  }
}
