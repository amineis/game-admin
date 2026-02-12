package dev.amineis.gameadmin.service;

import dev.amineis.gameadmin.dto.request.GameCreateRequest;
import dev.amineis.gameadmin.dto.request.GameUpdateRequest;
import dev.amineis.gameadmin.dto.response.GameResponse;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.entity.Game;
import dev.amineis.gameadmin.enums.Genre;
import dev.amineis.gameadmin.exception.BusinessRuleException;
import dev.amineis.gameadmin.exception.ResourceNotFoundException;
import dev.amineis.gameadmin.mapper.GameMapper;
import dev.amineis.gameadmin.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Transactional(readOnly = true)
    public PagedResponse<GameResponse> getAllGames(Pageable pageable) {
        return toPagedResponse(gameRepository.findAll(pageable));
    }

    @Transactional(readOnly = true)
    public GameResponse getGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game", id));
        return gameMapper.toResponse(game);
    }

    @Transactional(readOnly = true)
    public PagedResponse<GameResponse> searchByName(String name, Pageable pageable) {
        return toPagedResponse(gameRepository.findByNameContainingIgnoreCase(name, pageable));
    }

    @Transactional(readOnly = true)
    public PagedResponse<GameResponse> getGamesByGenre(Genre genre, Pageable pageable) {
        return toPagedResponse(gameRepository.findByGenre(genre, pageable));
    }

    @Transactional
    public GameResponse createGame(GameCreateRequest request) {
        if (gameRepository.existsByName(request.getName())) {
            throw new BusinessRuleException("Game with name '" + request.getName() + "' already exists");
        }
        Game saved = gameRepository.save(gameMapper.toEntity(request));
        return gameMapper.toResponse(saved);
    }

    @Transactional
    public GameResponse updateGame(Long id, GameUpdateRequest request) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game", id));

        if (request.getName() != null && !request.getName().equals(game.getName())) {
            if (gameRepository.existsByName(request.getName())) {
                throw new BusinessRuleException("Game with name '" + request.getName() + "' already exists");
            }
            game.setName(request.getName());
        }

        if (request.getGenre() != null) {
            game.setGenre(request.getGenre());
        }

        return gameMapper.toResponse(gameRepository.save(game));
    }

    @Transactional
    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new ResourceNotFoundException("Game", id);
        }
        gameRepository.deleteById(id);
    }

    private PagedResponse<GameResponse> toPagedResponse(Page<Game> page) {
        return PagedResponse.<GameResponse>builder()
                .content(page.getContent().stream().map(gameMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
