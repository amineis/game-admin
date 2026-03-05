package dev.amineis.gameadmin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Player response")
public class PlayerResponse {

  @Schema(description = "Player ID")
  private Long id;

  @Schema(description = "Username", example = "player123")
  private String username;

  @Schema(description = "2-letter country code", example = "FR")
  private String country;

  @Schema(description = "Creation timestamp")
  private Instant createdAt;
}
