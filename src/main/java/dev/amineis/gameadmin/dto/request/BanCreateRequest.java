package dev.amineis.gameadmin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body to create a ban for a player")
public class BanCreateRequest {

    @Schema(description = "Game ID the ban applies to", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Game ID is required")
    private Long gameId;

    @Schema(description = "Reason for the ban", example = "Repeated toxic behavior", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Ban reason is required")
    private String reason;
}
