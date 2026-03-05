package dev.amineis.gameadmin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "match_player",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"match_id", "player_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchPlayer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "match_id", nullable = false)
  private GameMatch match;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_id", nullable = false)
  private Player player;

  @Column(length = 20)
  private String team;

  @Builder.Default
  @Column(nullable = false)
  private Integer score = 0;
}
