package dev.amineis.gameadmin.dto.request;

import dev.amineis.gameadmin.enums.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body to update an existing game (all fields optional)")
public class GameUpdateRequest {

  @Schema(description = "Game name", example = "League of Legends 2")
  @Size(max = 100, message = "Game name must not exceed 100 characters")
  private String name;

  @Schema(description = "Genre: FPS, MOBA, RPG, STRATEGY, SPORTS", example = "MOBA")
  private Genre genre;
}
