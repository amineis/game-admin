package dev.amineis.gameadmin.repository;

import dev.amineis.gameadmin.entity.GameMatch;
import dev.amineis.gameadmin.enums.MatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameMatchRepository extends JpaRepository<GameMatch, Long> {

    Page<GameMatch> findByGameId(Long gameId, Pageable pageable);

    Page<GameMatch> findByStatus(MatchStatus status, Pageable pageable);

    Page<GameMatch> findByRegion(String region, Pageable pageable);

    List<GameMatch> findByGameIdAndStatus(Long gameId, MatchStatus status);
}
