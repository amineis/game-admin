package dev.amineis.gameadmin.repository;

import dev.amineis.gameadmin.entity.Game;
import dev.amineis.gameadmin.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByName(String name);

    boolean existsByName(String name);

    Page<Game> findByGenre(Genre genre, Pageable pageable);

    Page<Game> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
