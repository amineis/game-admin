package dev.amineis.gameadmin.repository;

import dev.amineis.gameadmin.entity.Ban;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BanRepository extends JpaRepository<Ban, Long> {

    List<Ban> findByPlayerId(Long playerId);

    Optional<Ban> findByPlayerIdAndGameId(Long playerId, Long gameId);

    Page<Ban> findByGameId(Long gameId, Pageable pageable);

    Page<Ban> findByBannedByUserId(Long userId, Pageable pageable);

    boolean existsByPlayerIdAndGameId(Long playerId, Long gameId);

    boolean existsByPlayerId(Long playerId);
}
