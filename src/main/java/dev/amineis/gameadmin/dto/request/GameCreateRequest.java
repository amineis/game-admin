package dev.amineis.gameadmin.dto.request;

import dev.amineis.gameadmin.enums.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body to create a new game")
public class GameCreateRequest {

  @Schema(
      description = "Game name",
      example = "League of Legends",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "Game name is required")
  @Size(max = 100, message = "Game name must not exceed 100 characters")
  private String name;

  @Schema(
      description = "Genre: FPS, MOBA, RPG, STRATEGY, SPORTS",
      example = "MOBA",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "Genre is required")
  private Genre genre;
}
