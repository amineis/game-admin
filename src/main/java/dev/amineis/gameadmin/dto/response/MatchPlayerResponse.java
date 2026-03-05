package dev.amineis.gameadmin.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchPlayerResponse {

  private Long id;
  private Long playerId;
  private String playerUsername;
  private String team;
  private Integer score;
}
