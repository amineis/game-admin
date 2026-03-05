package dev.amineis.gameadmin.entity;

import dev.amineis.gameadmin.enums.Genre;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100, unique = true, nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(length = 30, nullable = false)
  private Genre genre;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}
