package dev.amineis.gameadmin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body to add a player to a match")
public class MatchPlayerRequest {

    @Schema(description = "Player ID to add", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Player ID is required")
    private Long playerId;

    @Schema(description = "Team name or identifier", example = "Team A")
    @Size(max = 20, message = "Team name must not exceed 20 characters")
    private String team;
}
