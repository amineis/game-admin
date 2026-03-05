package dev.amineis.gameadmin.repository;

import dev.amineis.gameadmin.entity.AppUser;
import dev.amineis.gameadmin.enums.Role;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findByUsername(String username);

  Optional<AppUser> findByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  Page<AppUser> findByRole(Role role, Pageable pageable);
}
