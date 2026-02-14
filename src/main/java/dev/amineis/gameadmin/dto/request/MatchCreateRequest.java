package dev.amineis.gameadmin.dto.request;

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
@Schema(description = "Request body to create a new match")
public class MatchCreateRequest {

    @Schema(description = "Game ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Game ID is required")
    private Long gameId;

    @Schema(description = "Match region", example = "EU-WEST", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Region is required")
    @Size(max = 30, message = "Region must not exceed 30 characters")
    private String region;
}
