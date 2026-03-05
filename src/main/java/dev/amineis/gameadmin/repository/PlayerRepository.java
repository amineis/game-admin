package dev.amineis.gameadmin.repository;

import dev.amineis.gameadmin.entity.Player;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

  Optional<Player> findByUsername(String username);

  boolean existsByUsername(String username);

  Page<Player> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

  Page<Player> findByCountry(String country, Pageable pageable);
}
