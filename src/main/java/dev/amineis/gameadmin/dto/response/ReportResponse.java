package dev.amineis.gameadmin.dto.response;

import dev.amineis.gameadmin.enums.ReportStatus;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponse {

  private Long id;
  private Long reportedPlayerId;
  private String reportedPlayerUsername;
  private Long reporterPlayerId;
  private String reporterPlayerUsername;
  private String reason;
  private ReportStatus status;
  private Instant createdAt;
}
