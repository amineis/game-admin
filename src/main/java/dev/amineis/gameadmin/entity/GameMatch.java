package dev.amineis.gameadmin.entity;

import dev.amineis.gameadmin.enums.MatchStatus;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "game_match")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameMatch {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "game_id", nullable = false)
  private Game game;

  @Column(length = 30, nullable = false)
  private String region;

  @Enumerated(EnumType.STRING)
  @Column(length = 30, nullable = false)
  private MatchStatus status;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}
