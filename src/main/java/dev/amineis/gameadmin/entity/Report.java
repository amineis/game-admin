package dev.amineis.gameadmin.entity;

import dev.amineis.gameadmin.enums.ReportStatus;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_player_id", nullable = false)
  private Player reportedPlayer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_player_id", nullable = false)
  private Player reporterPlayer;

  @Column(nullable = false)
  private String reason;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(length = 30, nullable = false)
  private ReportStatus status = ReportStatus.PENDING;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}
