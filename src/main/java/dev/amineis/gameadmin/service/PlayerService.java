package dev.amineis.gameadmin.service;

import dev.amineis.gameadmin.dto.request.PlayerCreateRequest;
import dev.amineis.gameadmin.dto.request.PlayerUpdateRequest;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.dto.response.PlayerResponse;
import dev.amineis.gameadmin.entity.Player;
import dev.amineis.gameadmin.exception.BusinessRuleException;
import dev.amineis.gameadmin.exception.ResourceNotFoundException;
import dev.amineis.gameadmin.mapper.PlayerMapper;
import dev.amineis.gameadmin.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Transactional(readOnly = true)
    public PagedResponse<PlayerResponse> getAllPlayers(Pageable pageable) {
        Page<Player> page = playerRepository.findAll(pageable);
        return toPagedResponse(page);
    }

    @Transactional(readOnly = true)
    public PlayerResponse getPlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player", id));
        return playerMapper.toResponse(player);
    }

    @Transactional(readOnly = true)
    public PagedResponse<PlayerResponse> searchByUsername(String username, Pageable pageable) {
        Page<Player> page = playerRepository.findByUsernameContainingIgnoreCase(username, pageable);
        return toPagedResponse(page);
    }

    @Transactional(readOnly = true)
    public PagedResponse<PlayerResponse> getPlayersByCountry(String country, Pageable pageable) {
        Page<Player> page = playerRepository.findByCountry(country, pageable);
        return toPagedResponse(page);
    }

    @Transactional
    public PlayerResponse createPlayer(PlayerCreateRequest request) {
        if (playerRepository.existsByUsername(request.getUsername())) {
            throw new BusinessRuleException("Player with username '" + request.getUsername() + "' already exists");
        }
        Player player = playerMapper.toEntity(request);
        Player saved = playerRepository.save(player);
        return playerMapper.toResponse(saved);
    }

    @Transactional
    public PlayerResponse updatePlayer(Long id, PlayerUpdateRequest request) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player", id));

        if (request.getUsername() != null && !request.getUsername().equals(player.getUsername())) {
            if (playerRepository.existsByUsername(request.getUsername())) {
                throw new BusinessRuleException("Player with username '" + request.getUsername() + "' already exists");
            }
            player.setUsername(request.getUsername());
        }

        if (request.getCountry() != null) {
            player.setCountry(request.getCountry());
        }

        Player updated = playerRepository.save(player);
        return playerMapper.toResponse(updated);
    }

    @Transactional
    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Player", id);
        }
        playerRepository.deleteById(id);
    }

    private PagedResponse<PlayerResponse> toPagedResponse(Page<Player> page) {
        return PagedResponse.<PlayerResponse>builder()
                .content(page.getContent().stream().map(playerMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
