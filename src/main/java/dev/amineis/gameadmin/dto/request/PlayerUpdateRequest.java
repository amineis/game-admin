package dev.amineis.gameadmin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body to update an existing player (all fields optional)")
public class PlayerUpdateRequest {

  @Schema(description = "Username", example = "player456")
  @Size(max = 50, message = "Username must not exceed 50 characters")
  private String username;

  @Schema(description = "2-letter ISO country code", example = "DE")
  @Size(min = 2, max = 2, message = "Country must be a 2-letter ISO code")
  private String country;
}
