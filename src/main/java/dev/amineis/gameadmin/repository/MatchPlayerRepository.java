package dev.amineis.gameadmin.repository;

import dev.amineis.gameadmin.entity.MatchPlayer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long> {

  List<MatchPlayer> findByMatchId(Long matchId);

  List<MatchPlayer> findByPlayerId(Long playerId);

  Optional<MatchPlayer> findByMatchIdAndPlayerId(Long matchId, Long playerId);

  boolean existsByMatchIdAndPlayerId(Long matchId, Long playerId);

  void deleteByMatchIdAndPlayerId(Long matchId, Long playerId);
}
