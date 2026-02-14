package dev.amineis.gameadmin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body to create a new player")
public class PlayerCreateRequest {

    @Schema(description = "Unique username", example = "player123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @Schema(description = "2-letter ISO country code", example = "FR")
    @Size(min = 2, max = 2, message = "Country must be a 2-letter ISO code")
    private String country;
}
